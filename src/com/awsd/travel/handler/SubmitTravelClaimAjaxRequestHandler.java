package com.awsd.travel.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.mail.bean.EmailBean;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.profile.ProfileDB;
import com.awsd.travel.TravelClaim;
import com.awsd.travel.TravelClaimDB;
import com.awsd.travel.TravelClaimException;
import com.awsd.travel.TravelClaimStatus;
import com.awsd.travel.TravelClaimSummary;
import com.awsd.travel.bean.TravelBudget;
import com.awsd.travel.service.TravelBudgetService;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.velocity.VelocityUtils;

public class SubmitTravelClaimAjaxRequestHandler extends RequestHandlerImpl {

	public SubmitTravelClaimAjaxRequestHandler() {

		this.requiredPermissions = new String[] {
				"TRAVEL-EXPENSE-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		TravelClaim claim = null;
		int id = -1;
		boolean check = false;
		Personnel claim_owner = null;
		Personnel supervisor = null;
		boolean iserror = false;
		String errormessage = "";
		// SMTPAuthenticatedMail smtp = null;

		if (ProfileDB.getProfile(usr.getPersonnel()) == null) {
			iserror = true;
			errormessage = "No Profile Found";
		}
		try {
			id = Integer.parseInt(request.getParameter("id"));
		}
		catch (NumberFormatException e) {
			id = -1;
		}
		if (id < 1) {
			iserror = true;
			errormessage = "<<<<< CLAIM ID IS REQUIRED FOR SUBMIT OPERATION.  >>>>>";
			throw new TravelClaimException("<<<<< CLAIM ID IS REQUIRED FOR SUBMIT OPERATION.  >>>>>");
		}
		else {
			claim = TravelClaimDB.getClaim(id);
			if (claim.getPersonnel().getPersonnelID() != usr.getPersonnel().getPersonnelID()) {
				iserror = true;
				errormessage = "You do not have permission to submit this travel claim.";
			}
		}
		if (iserror) {
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<TRAVELCLAIMS>");
			sb.append("<TRAVELCLAIM>");
			sb.append("<MESSAGE>" + errormessage + "</MESSAGE>");
			sb.append("</TRAVELCLAIM>");
			sb.append("</TRAVELCLAIMS>");
			xml = sb.toString().replaceAll("&", "&amp;");
			System.out.println(xml);
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
		}
		else {
			// check of travel budget available funds.
			boolean bcheck = false;
			TravelClaimSummary summary = null;
			TravelBudget budget = TravelBudgetService.getTravelBudget(usr.getPersonnel(), claim.getFiscalYear());
			if (budget != null) {
				summary = claim.getSummaryTotals();
				BigDecimal diff = new BigDecimal(Double.toString(
						budget.getAmount() - budget.getAmountClaimed() - summary.getSummaryTotal()));
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
					iserror = true;
					errormessage = "SUCCESS";
					claim_owner = claim.getPersonnel();
					supervisor = claim.getSupervisor();
					try {
						EmailBean email = new EmailBean();
						email.setTo(new String[] {
								supervisor.getEmailAddress()
						});
						email.setSubject("Travel Claim Pending Approval for " + claim_owner.getFullNameReverse());
						HashMap<String, Object> model = new HashMap<String, Object>();
						model.put("superfirst", supervisor.getFirstName());
						model.put("ownerfull", claim_owner.getFullNameReverse());
						model.put("alink",
								"<a href='http://www.nlesd.ca/MemberServices/Travel/viewTravelClaimSystem.html'><B>CLICK HERE</B></a>");
						email.setBody(VelocityUtils.mergeTemplateIntoString("stafftravel/staff_travel_claim_approval.vm", model));
						email.setFrom("ms@nlesd.ca");
						email.send();
					}
					catch (Exception e) {
						iserror = true;
						errormessage = "Claim submitted, supervisory email not sent.";
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
							HashMap<String, Object> model = new HashMap<String, Object>();
							model.put("ownerfull", claim_owner.getFullNameReverse());
							model.put("fiscalyear", budget.getFiscalYear());
							model.put("budgetamount", df.format(budget.getAmount()));
							model.put("ytdbudget", df.format(budget.getAmountClaimed() + summary.getSummaryTotal()));
							model.put("available",
									df.format(budget.getAmount() - budget.getAmountClaimed() - summary.getSummaryTotal()));
							email.setBody(VelocityUtils.mergeTemplateIntoString("stafftravel/staff_travel_budget_warning.vm", model));
							email.setFrom("ms@nlesd.ca");
							//email.send();
							// send to admin for testing purposes.
							Personnel admins[] = PersonnelDB.getPersonnelByRole("ADMINISTRATOR");
							if (admins != null && admins.length > 0) {
								email = new EmailBean();
								email.setSubject("Travel budget less then 10% - " + claim_owner.getFullNameReverse());
								model = new HashMap<String, Object>();
								model.put("ownerfull", claim_owner.getFullNameReverse());
								model.put("fiscalyear", budget.getFiscalYear());
								model.put("budgetamount", df.format(budget.getAmount()));
								model.put("ytdbudget", df.format(budget.getAmountClaimed() + summary.getSummaryTotal()));
								model.put("available",
										df.format(budget.getAmount() - budget.getAmountClaimed() - summary.getSummaryTotal()));
								email.setBody(
										VelocityUtils.mergeTemplateIntoString("stafftravel/staff_travel_budget_warning.vm", model));
								email.setFrom("ms@nlesd.ca");
								email.setTo(admins);
								//email.send();
							}
						}
						catch (Exception e) {
							e.printStackTrace(System.err);
							iserror = true;
							errormessage = "Claim submitted, claim owner email not sent.";
						}
					}
				}
				else {
					iserror = true;
					errormessage = "Claim could not be submitted.";
				}
			}
			//send back result
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<TRAVELCLAIMS>");
			sb.append("<TRAVELCLAIM>");
			sb.append("<MESSAGE>" + errormessage + "</MESSAGE>");
			sb.append("</TRAVELCLAIM>");
			sb.append("</TRAVELCLAIMS>");
			xml = sb.toString().replaceAll("&", "&amp;");
			System.out.println(xml);
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
		}

		return null;
	}
}