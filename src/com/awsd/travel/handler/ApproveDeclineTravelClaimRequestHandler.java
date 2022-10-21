package com.awsd.travel.handler;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.awsd.common.Utils;
import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.bean.EmailException;
import com.awsd.personnel.Personnel;
import com.awsd.travel.PDTravelClaim;
import com.awsd.travel.TravelClaim;
import com.awsd.travel.TravelClaimDB;
import com.awsd.travel.TravelClaimNote;
import com.awsd.travel.TravelClaimNoteDB;
import com.awsd.travel.TravelClaimStatus;
import com.esdnl.servlet.RequestHandlerImpl;

public class ApproveDeclineTravelClaimRequestHandler extends RequestHandlerImpl {

	public ApproveDeclineTravelClaimRequestHandler() {

		this.requiredPermissions = new String[] {
				"TRAVEL-CLAIM-SUPERVISOR-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		TravelClaim claim = null;
		int id = -1;
		// SMTPAuthenticatedMail smtp = null;
		Personnel supervisor = null;
		Personnel claim_owner = null;
		StringBuffer gl_acc = null;

		try {
			id = Integer.parseInt(request.getParameter("id"));
		}
		catch (NumberFormatException e) {
			id = -1;
		}

		if (id > 0) {
			claim = TravelClaimDB.getClaim(id);

			if (claim != null) {
				if (usr.getPersonnel().getPersonnelID() == claim.getSupervisor().getPersonnelID()) {
					if (request.getParameter("op") != null) {
						request.setAttribute("TRAVELCLAIM", claim);
						request.setAttribute("SDSINFO", claim.getPersonnel().getSDSInfo());
						request.setAttribute("op", request.getParameter("op"));

						if (request.getParameter("op").equalsIgnoreCase("APPROVED")) {
							request.setAttribute("op", "APPROVE");

							claim_owner = claim.getPersonnel();
							supervisor = claim.getSupervisor();

							if (TravelClaimDB.setCurrentStatus(claim, usr.getPersonnel(), TravelClaimStatus.APPROVED)) {
								request.setAttribute("msg", "Claim approved.");
								request.setAttribute("RESULT", "SUCCESS");

								try {
									EmailBean email = new EmailBean();
									email.setTo(new String[] {
											claim_owner.getEmailAddress()
									});
									email.setSubject("Travel Claim APPROVED by " + supervisor.getFullNameReverse());
									email.setBody(claim_owner.getFirstName()
											+ ", <br><br>Your claim for "
											+ (!(claim instanceof PDTravelClaim)
													? Utils.getMonthString(claim.getFiscalMonth()) + " " + Utils.getYear(
															claim.getFiscalMonth(), claim.getFiscalYear())
													: " PD on " + new SimpleDateFormat("EEE MMM dd, yyyy").format(
															((PDTravelClaim) claim).getPD().getStartDate()))
											+ " has been APPROVED. "
											+ " To review this claim click the link below to login to Member Services and access the Travel Claim System.<br><br>"
											+ "<a href='http://www.nlesd.ca/MemberServices/Travel/viewTravelClaimSystem.html'><B>CLICK HERE</B></a><br><br>"
											+ "PLEASE DO NOT REPLY TO THIS MESSAGE.<br><br>" + "Member Services");
									email.send();

								}
								catch (EmailException e) {
									e.printStackTrace(System.err);
									request.setAttribute("msg",
											"Claim approved, email could not be sent to " + claim_owner.getFullNameReverse() + ".");
								}

								if (!StringUtils.isEmpty(request.getParameter("gl_acc_part_1"))
										&& !StringUtils.isEmpty(request.getParameter("gl_acc_part_2"))
										&& !StringUtils.isEmpty(request.getParameter("gl_acc_part_3"))
										&& !StringUtils.isEmpty(request.getParameter("gl_acc_part_4"))
										&& !StringUtils.isEmpty(request.getParameter("gl_acc_part_5"))
										&& !StringUtils.isEmpty(request.getParameter("gl_acc_part_6"))
										&& !StringUtils.isEmpty(request.getParameter("gl_acc_part_7"))) {

									gl_acc = new StringBuffer(request.getParameter("gl_acc_part_1"));
									gl_acc.append(request.getParameter("gl_acc_part_2"));
									gl_acc.append(request.getParameter("gl_acc_part_3"));
									gl_acc.append(request.getParameter("gl_acc_part_4"));
									gl_acc.append(request.getParameter("gl_acc_part_5"));
									gl_acc.append(request.getParameter("gl_acc_part_6"));
									gl_acc.append(request.getParameter("gl_acc_part_7"));

										TravelClaimDB.setClaimGLAccount(claim, gl_acc.toString().trim());
										claim.setSDSGLAccountCode(gl_acc.toString().trim());
									
								}
							}
							else {
								request.setAttribute("msg", "Claim not be approved.");
								request.setAttribute("RESULT", "FAILED");
							}
						}
						else if (request.getParameter("op").equalsIgnoreCase("DECLINED")) {
							request.setAttribute("op", "DECLINE");

							claim_owner = claim.getPersonnel();
							supervisor = claim.getSupervisor();

							if (TravelClaimDB.setCurrentStatus(claim, usr.getPersonnel(), TravelClaimStatus.REJECTED)) {
								if ((request.getParameter("note") != null) && !request.getParameter("note").trim().equals("")) {
									TravelClaimNoteDB.addClaimNote(claim,
											new TravelClaimNote(usr.getPersonnel(), request.getParameter("note")));

									request.setAttribute("msg", "Claim declined.");
									request.setAttribute("RESULT", "SUCCESS");

									try {
										EmailBean email = new EmailBean();
										email.setTo(new String[] {
												claim_owner.getEmailAddress()
										});
										email.setSubject("Travel Claim REJECTED by " + supervisor.getFullNameReverse());
										email.setBody(claim_owner.getFirstName() + ", <br><br>Your claim for "
												+ Utils.getMonthString(claim.getFiscalMonth()) + " "
												+ Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear()) + " has been REJECTED. "
												+ " To review this claim click the link below to login to Member Services and access the Travel Claim System.<br><br>"
												+ "<a href='http://www.nlesd.ca/MemberServices/Travel/viewTravelClaimSystem.html'><B>CLICK HERE</B></a><br><br>"
												+ "PLEASE DO NOT REPLY TO THIS MESSAGE.<br><br>" + "Member Services");
										email.send();
									}
									catch (EmailException e) {
										e.printStackTrace(System.err);
										request.setAttribute("msg",
												"Claim declined, email could not be sent to " + claim_owner.getFullNameReverse() + ".");
									}

								}
								else {
									request.setAttribute("msg", "Claim declined.");
									request.setAttribute("RESULT", "SUCCESS");

									try {
										EmailBean email = new EmailBean();
										email.setTo(new String[] {
												claim_owner.getEmailAddress()
										});
										email.setSubject("Travel Claim REJECTED by " + supervisor.getFullNameReverse());
										email.setBody(claim_owner.getFirstName() + ", <br><br>Your claim for "
												+ Utils.getMonthString(claim.getFiscalMonth()) + " "
												+ Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear()) + " has been REJECTED. "
												+ " To review this claim click the link below to login to Member Services and access the Travel Claim System.<br><br>"
												+ "<a href='http://www.nlesd.ca/MemberServices/Travel/viewTravelClaimSystem.html'><B>CLICK HERE</B></a><br><br>"
												+ "PLEASE DO NOT REPLY TO THIS MESSAGE.<br><br>" + "Member Services");
										email.send();
									}
									catch (EmailException e) {
										e.printStackTrace(System.err);
										request.setAttribute("msg",
												"Claim declined, email could not be sent to " + claim_owner.getFullNameReverse() + ".");
									}

								}
							}
							else {
								request.setAttribute("msg", "Claim not be declined.");
								request.setAttribute("RESULT", "FAILED");
							}
						}

						path = "approve_decline_claim.jsp";
					}
					else {
						path = "claim_error.jsp?msg=Invalid Operation.";
					}
				}
				else {
					path = "claim_error.jsp?msg=You do not have permission to approve/decline this travel claim.";
				}
			}
			else {
				path = "claim_error.jsp?msg=Claim cound not be found.";
			}
		}
		else
			path = "claim_error.jsp?msg=Claim cound not be found.";

		return path;
	}
}