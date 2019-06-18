package com.awsd.travel.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.common.Utils;
import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.bean.EmailException;
import com.awsd.personnel.Personnel;
import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.awsd.travel.TravelClaim;
import com.awsd.travel.TravelClaimDB;
import com.esdnl.sds.SDSInfo;
import com.esdnl.sds.SDSInfoDB;

public class PayTravelClaimRequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		HttpSession session = null;
		User usr = null;
		String path = "";

		TravelClaim claim = null;
		Personnel claim_owner = null;
		int id = -1;
		StringBuffer gl_acc = null;
		SDSInfo sds = null;

		session = request.getSession(false);
		if ((session != null) && (session.getAttribute("usr") != null)) {
			usr = (User) session.getAttribute("usr");
			if (!(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW"))) {
				throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
			}
		}
		else {
			throw new SecurityException("User login required.");
		}

		try {
			id = Integer.parseInt(request.getParameter("id"));
		}
		catch (NumberFormatException e) {
			id = -1;
		}

		if (id > 0) {
			claim = TravelClaimDB.getClaim(id);

			if (claim != null) {
				if (request.getParameter("op") != null) {
					if (request.getParameter("op").equalsIgnoreCase("CONFIRM")) {
						gl_acc = new StringBuffer(request.getParameter("gl_acc_part_1"));
						gl_acc.append(request.getParameter("gl_acc_part_2"));
						gl_acc.append(request.getParameter("gl_acc_part_3"));
						gl_acc.append(request.getParameter("gl_acc_part_4"));
						gl_acc.append(request.getParameter("gl_acc_part_5"));
						gl_acc.append(request.getParameter("gl_acc_part_6"));
						gl_acc.append(request.getParameter("gl_acc_part_7"));

						if (gl_acc.toString().trim().length() == 17) {
							sds = SDSInfoDB.getSDSInfo(claim.getPersonnel());
							if (sds == null) {
								SDSInfoDB.addSDSInfo(claim.getPersonnel(),
										new SDSInfo(request.getParameter("sds_ven_num"), gl_acc.toString()));
							}
							else if (((sds.getAccountCode() != null) && !sds.getAccountCode().equals(gl_acc.toString()))
									|| ((sds.getVendorNumber() != null) && !sds.getVendorNumber().equals(
											request.getParameter("sds_ven_num")))) {
								sds.setAccountCode(gl_acc.toString());
								sds.setVendorNumber(request.getParameter("sds_ven_num"));
								SDSInfoDB.updateSDSInfo(claim.getPersonnel(), sds);
							}

							if (TravelClaimDB.setClaimToPaid(claim, usr.getPersonnel(), gl_acc.toString().trim(),
									(request.getParameter("sds_tchr_par") != null) ? "Y" : "N")) {
								request.setAttribute("msg", "Claim status set to PAID.");
								request.setAttribute("RESULT", "SUCCESS");

								claim_owner = claim.getPersonnel();

								try {
									EmailBean email = new EmailBean();
									email.setTo(new String[] {
										claim_owner.getEmailAddress()
									});
									email.setSubject("Travel Claim sent for PAYMENT");
									email.setBody(claim_owner.getFirstName()
											+ ", <br><br>Your claim for "
											+ Utils.getMonthString(claim.getFiscalMonth())
											+ " "
											+ Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear())
											+ " has been sent for payment. "
											+ " To review this claim click the link below to login to Member Services and access the Travel Claim System.<br><br>"
											+ "<a href='http://www.nlesd.ca/MemberServices/Travel/viewTravelClaimSystem.html'><B>CLICK HERE</B></a><br><br>"
											+ "PLEASE DO NOT REPLY TO THIS MESSAGE.<br><br>" + "Member Services");
									email.send();
								}
								catch (EmailException e) {
									e.printStackTrace(System.err);
									request.setAttribute("msg",
											"Claim PAID, email could not be sent to " + claim_owner.getFullNameReverse() + ".");
								}
							}
							else {
								request.setAttribute("msg", "Claim status NOT set to PAID.");
								request.setAttribute("RESULT", "FAILED");
							}
						}
						else {
							request.setAttribute("msg", "GL ACCOUNT CODE INVALID.");
							request.setAttribute("RESULT", "FAILED");
						}
					}
					else {
						request.setAttribute("msg", "Invalid option.");
						request.setAttribute("RESULT", "FAILED");
					}
				}

				request.setAttribute("TRAVELCLAIM", claim);
				path = "pay_claim.jsp";
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