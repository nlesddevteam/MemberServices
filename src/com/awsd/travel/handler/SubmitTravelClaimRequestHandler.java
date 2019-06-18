package com.awsd.travel.handler;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.awsd.common.Utils;
import com.awsd.mail.bean.EmailBean;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.profile.ProfileDB;
import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.awsd.travel.TravelClaim;
import com.awsd.travel.TravelClaimDB;
import com.awsd.travel.TravelClaimException;
import com.awsd.travel.TravelClaimStatus;
import com.awsd.travel.TravelClaimSummary;
import com.awsd.travel.bean.TravelBudget;
import com.awsd.travel.service.TravelBudgetService;

public class SubmitTravelClaimRequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		String path;
		HttpSession session = null;
		User usr = null;
		TravelClaim claim = null;
		String op = "";
		int id = -1;
		boolean check = false;
		Personnel claim_owner = null;
		Personnel supervisor = null;
		// SMTPAuthenticatedMail smtp = null;
		session = request.getSession(false);
		if ((session != null) && (session.getAttribute("usr") != null)) {
			usr = (User) session.getAttribute("usr");
			if (!(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-VIEW"))) {
				throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
			}
		}else {
			throw new SecurityException("User login required.");
		}
		if (ProfileDB.getProfile(usr.getPersonnel()) == null)
			path = "profile.jsp";
		else {
			try {
				id = Integer.parseInt(request.getParameter("id"));
			}catch (NumberFormatException e) {
				id = -1;
			}
			if (id < 1) {
				throw new TravelClaimException("<<<<< CLAIM ID IS REQUIRED FOR SUBMIT OPERATION.  >>>>>");
			}
			else {
				claim = TravelClaimDB.getClaim(id);
				request.setAttribute("TRAVELCLAIM", claim);
				if (claim.getPersonnel().getPersonnelID() != usr.getPersonnel().getPersonnelID()) {
					request.setAttribute("msg", "You do not have permission to submit this travel claim.");
					request.setAttribute("NOPERMISSION", new Boolean(true));
				}else {
					op = request.getParameter("op");
					if (op != null) {
						if (op.equalsIgnoreCase("CONFIRM")) {
							// check of travel budget available funds.
							boolean bcheck = false;
							TravelClaimSummary summary = null;
							TravelBudget budget = TravelBudgetService.getTravelBudget(usr.getPersonnel(), claim.getFiscalYear());
							if (budget != null) {
								summary = claim.getSummaryTotals();
								BigDecimal diff = new BigDecimal(Double.toString(budget.getAmount() - budget.getAmountClaimed()
										- summary.getSummaryTotal()));
								if (diff.setScale(2, RoundingMode.HALF_EVEN).doubleValue() >= 0)
									bcheck = true;
								else
									bcheck = false;
							}
							else
								bcheck = true;

							if (bcheck) {
								check = TravelClaimDB.setCurrentStatus(claim, usr.getPersonnel(), TravelClaimStatus.SUBMITTED);
								if (check) {
									request.setAttribute("RESULT", "SUCCESS");
									request.setAttribute("msg", "Claim submitted successfully.");
									claim_owner = claim.getPersonnel();
									supervisor = claim.getSupervisor();
									try {
										EmailBean email = new EmailBean();
										email.setTo(new String[] {
											supervisor.getEmailAddress()
										});
										email.setSubject("Travel Claim Pending Approval for " + claim_owner.getFullNameReverse());
										email.setBody(supervisor.getFirstName()
												+ ", <br><br>"
												+ claim_owner.getFullNameReverse()
												+ " has submitted a travel claim for your approval. "
												+ " To review this claim click the link below to login to Member Services and access the Travel Claim System.<br><br>"
												+ "<a href='http://www.nlesd.ca/MemberServices/Travel/viewTravelClaimSystem.html'><B>CLICK HERE</B></a><br><br>"
												+ "PLEASE DO NOT REPLY TO THIS MESSAGE.<br><br>" + "Member Services");
										email.setFrom("ms@nlesd.ca");
										//email.send();
									}
									catch (Exception e) {
										e.printStackTrace(System.err);
										request.setAttribute("msg", "Claim submitted, supervisory email not sent.");
									}
									// check if less then 10% budget remaining
									if ((budget != null)
											&& (((budget.getAmountClaimed() + summary.getSummaryTotal()) / budget.getAmount() * 100.0) >= 90.0)) {

										DecimalFormat df = new DecimalFormat("$#,##0.00");
										try {
											claim_owner = claim.getPersonnel();
											supervisor = claim.getSupervisor();
											EmailBean email = new EmailBean();
											email.setTo(new String[] {
												budget.getDivision().getAssistantDirector().getEmailAddress()
											});
											email.setCC(new String[] {
												supervisor.getEmailAddress()
											});
											email.setSubject("Travel budget less then 10% - " + claim_owner.getFullNameReverse());
											email.setBody(claim_owner.getFullNameReverse()
													+ " has less then 10% of approved travel budget remaining. See details below; <br /><br /> "
													+ budget.getFiscalYear() + " Approved Budget: " + df.format(budget.getAmount()) + "<br />"
													+ budget.getFiscalYear() + " Claimed YTD: "
													+ df.format(budget.getAmountClaimed() + summary.getSummaryTotal()) + "<br />"
													+ budget.getFiscalYear() + " Available Funds: "
													+ df.format(budget.getAmount() - budget.getAmountClaimed() - summary.getSummaryTotal())
													+ "<br /><br />" + "PLEASE DO NOT REPLY TO THIS MESSAGE.<br><br>" + "Member Services");
											email.setFrom("ms@nlesd.ca");
											//email.send();
											// send to admin for testing purposes.
											Personnel admins[] = PersonnelDB.getPersonnelByRole("ADMINISTRATOR");
											if (admins != null && admins.length > 0) {
												email = new EmailBean();
												email.setSubject("Travel budget less then 10% - " + claim_owner.getFullNameReverse());
												email.setBody(claim_owner.getFullNameReverse()
														+ " has less then 10% of approved travel budget remaining. See details below; <br /><br /> "
														+ budget.getFiscalYear() + " Approved Budget: " + df.format(budget.getAmount()) + "<br />"
														+ budget.getFiscalYear() + " Claimed YTD: "
														+ df.format(budget.getAmountClaimed() + summary.getSummaryTotal()) + "<br />"
														+ budget.getFiscalYear() + " Available Funds: "
														+ df.format(budget.getAmount() - budget.getAmountClaimed() - summary.getSummaryTotal())
														+ "<br /><br />" + "PLEASE DO NOT REPLY TO THIS MESSAGE.<br><br>" + "Member Services");
												email.setFrom("ms@nlesd.ca");
												email.setTo(admins);
												//email.send();
											}
										}
										catch (Exception e) {
											e.printStackTrace(System.err);
										}
									}
								}
								else {
									request.setAttribute("RESULT", "FAILED");
									request.setAttribute("msg", "Claim could not be submitted.");
								}
							}
							else {
								DecimalFormat df = new DecimalFormat("$#,##0.00");
								request.setAttribute(
										"msg",
										"Claim could not be submitted due to insufficient funds available in approved budget ["
												+ df.format(budget.getAmount()) + "]. Your supervisor has been notified via email.");
								request.setAttribute("RESULT", "FAILED");
								try {
									claim_owner = claim.getPersonnel();
									supervisor = claim.getSupervisor();
									EmailBean email = new EmailBean();
									email.setTo(new String[] {
											claim_owner.getEmailAddress(), supervisor.getEmailAddress()
									});
									email.setSubject("Travel claim suspended due to budget restrictions - "
											+ claim_owner.getFullNameReverse());
									email.setBody(claim_owner.getFullNameReverse()
											+ " has submitted a travel claim which has been suspended due to insufficient funds available in approved budget. See details below; <br /><br /> "
											+ budget.getFiscalYear() + " Approved Budget: " + df.format(budget.getAmount()) + "<br />"
											+ budget.getFiscalYear() + " Claimed YTD: " + df.format(budget.getAmountClaimed()) + "<br />"
											+ claim.getFiscalYear() + " " + Utils.getMonthString(claim.getFiscalMonth())
											+ " Claimed Amount: " + df.format(summary.getSummaryTotal()) + "<br />" + "Deficit: "
											+ df.format(budget.getAmount() - budget.getAmountClaimed() - summary.getSummaryTotal())
											+ "<br /><br />" + "PLEASE DO NOT REPLY TO THIS MESSAGE.<br><br>" + "Member Services");
									email.setFrom("ms@nlesd.ca");
									//email.send();
									// send to admin for testing purposes.
									Personnel admins[] = PersonnelDB.getPersonnelByRole("ADMINISTRATOR");
									if (admins != null && admins.length > 0) {
										email = new EmailBean();
										email.setSubject("Travel claim suspended due to budget restrictions - "
												+ claim_owner.getFullNameReverse());
										email.setBody(claim_owner.getFullNameReverse()
												+ " has submitted a travel claim which has been suspended due to insufficient funds available in approved budget. See details below; <br /><br /> "
												+ budget.getFiscalYear() + " Approved Budget: " + df.format(budget.getAmount()) + "<br />"
												+ budget.getFiscalYear() + " Claimed YTD: " + df.format(budget.getAmountClaimed()) + "<br />"
												+ claim.getFiscalYear() + " " + Utils.getMonthString(claim.getFiscalMonth())
												+ " Claimed Amount: " + df.format(summary.getSummaryTotal()) + "<br />" + "Deficit: "
												+ df.format(budget.getAmount() - budget.getAmountClaimed() - summary.getSummaryTotal())
												+ "<br /><br />" + "PLEASE DO NOT REPLY TO THIS MESSAGE.<br><br>" + "Member Services");
										email.setFrom("ms@nlesd.ca");
										email.setTo(admins);
										//email.send();
									}
								}
								catch (Exception e) {
									e.printStackTrace(System.err);
								}
							}

						}
						else {
							request.setAttribute("msg", "Invalid operation.");
						}
					}
				}
			}
			path = "submit_claim.jsp";
		}
		return path;
	}
}