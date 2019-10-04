package com.awsd.travel.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.awsd.common.Utils;
import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.bean.EmailException;
import com.awsd.personnel.Personnel;
import com.awsd.travel.TravelClaim;
import com.awsd.travel.TravelClaimDB;
import com.awsd.travel.TravelClaimNote;
import com.awsd.travel.TravelClaimNoteDB;
import com.awsd.travel.TravelClaimStatus;
import com.esdnl.servlet.RequestHandlerImpl;

public class PaymentPendingTravelClaimRequestHandler extends RequestHandlerImpl {

	public PaymentPendingTravelClaimRequestHandler() {

		this.requiredPermissions = new String[] {
				"TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		TravelClaim claim = null;
		Personnel claim_owner = null;
		int id = -1;

		try {
			id = Integer.parseInt(request.getParameter("id"));
		}
		catch (NumberFormatException e) {
			id = -1;
		}

		if (id > 0) {
			claim = TravelClaimDB.getClaim(id);

			if (claim != null) {
				request.setAttribute("TRAVELCLAIM", claim);

				if (request.getParameter("op") != null) {
					if (request.getParameter("op").equalsIgnoreCase("CONFIRM")) {
						if (TravelClaimDB.setCurrentStatus(claim, usr.getPersonnel(), TravelClaimStatus.PAYMENT_PENDING)) {
							claim_owner = claim.getPersonnel();

							if ((request.getParameter("note") != null) && !request.getParameter("note").trim().equals("")) {
								TravelClaimNoteDB.addClaimNote(claim,
										new TravelClaimNote(usr.getPersonnel(), request.getParameter("note")));
								request.setAttribute("msg", "Payment pending set.");
								request.setAttribute("RESULT", "SUCCESS");

								try {
									EmailBean email = new EmailBean();
									email.setTo(new String[] {
											claim_owner.getEmailAddress()
									});
									email.setCC(new String[] {
											usr.getPersonnel().getEmailAddress()
									});
									email.setSubject("NLESD TravelClaim System - PAYMENT PENDING - Futher Information Required.");
									email.setBody(claim_owner.getFirstName() + ", <br/><br/>Your claim for "
											+ Utils.getMonthString(claim.getFiscalMonth()) + " "
											+ Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear())
											+ " has been delayed pending the receival of further travel details; more specifically:<br/><br/>"
											+ (StringUtils.isNotEmpty(request.getParameter("note"))
													? request.getParameter("note").replace("\r\n", "<br />") + "<br /><br />"
													: "")
											+ "If you need to submit receipts; send originals to the attention of <a href='mailto:"
											+ usr.getPersonnel().getEmailAddress() + "'>" + usr.getPersonnel().getFullNameReverse()
											+ "</a>.<br/><br/> "
											+ " To review this claim click the link below to login to Member Services and access the Travel Claim System.<br/><br/>"
											+ "<a href='http://www.nlesd.ca/MemberServices/Travel/viewTravelClaimSystem.html'><B>CLICK HERE</B></a><br/><br/>"
											+ "Member Services");
									email.send();
								}
								catch (EmailException e) {
									e.printStackTrace(System.err);
									request.setAttribute("msg",
											"Claim PENDING PAYMENT, email could not be sent to " + claim_owner.getFullNameReverse() + ".");
								}
							}
							else {
								request.setAttribute("msg", "Payment pending set.");
								request.setAttribute("RESULT", "SUCCESS");

								try {
									EmailBean email = new EmailBean();
									email.setTo(new String[] {
											claim_owner.getEmailAddress()
									});
									email.setCC(new String[] {
											usr.getPersonnel().getEmailAddress()
									});
									email.setSubject("NLESD TravelClaim System - PAYMENT PENDING - Futher Information Required.");
									email.setBody(claim_owner.getFirstName() + ", <br><br>Your claim for "
											+ Utils.getMonthString(claim.getFiscalMonth()) + " "
											+ Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear())
											+ " has been delayed pending the receival of further travel details. <br/><br/>If you need to submit receipts; send originals to the attention of <a href='mailto:"
											+ usr.getPersonnel().getEmailAddress() + "'>" + usr.getPersonnel().getFullNameReverse()
											+ "</a>.<br /><br />"
											+ " To review this claim click the link below to login to Member Services and access the Travel Claim System.<br><br>"
											+ "<a href='http://www.nlesd.ca/MemberServices/Travel/viewTravelClaimSystem.html'><B>CLICK HERE</B></a><br><br>"
											+ "PLEASE DO NOT REPLY TO THIS MESSAGE.<br><br>" + "Member Services");
									email.send();
								}
								catch (Exception e) {
									e.printStackTrace(System.err);
									request.setAttribute("msg",
											"Claim PENDING PAYMENT, email could not be sent to " + claim_owner.getFullNameReverse() + ".");
								}
							}
						}
						else {
							request.setAttribute("msg", "Payment pending NOT set.");
							request.setAttribute("RESULT", "FAILED");
						}
					}
					else {
						request.setAttribute("msg", "Invalid option.");
						request.setAttribute("RESULT", "FAILED");
					}
				}

				path = "payment_pending_claim.jsp";
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