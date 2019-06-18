package com.awsd.travel.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.awsd.common.Utils;
import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.bean.EmailException;
import com.awsd.personnel.Personnel;
import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.travel.PDTravelClaim;
import com.awsd.travel.TravelClaim;
import com.awsd.travel.TravelClaimDB;
import com.awsd.travel.TravelClaimNote;
import com.awsd.travel.TravelClaimNoteDB;
import com.awsd.travel.TravelClaimStatus;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.velocity.VelocityUtils;

public class PaymentPendingTravelClaimAjaxRequestHandler extends RequestHandlerImpl {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		HttpSession session = null;
		User usr = null;
		TravelClaim claim = null;
		Personnel claim_owner = null;
		int id = -1;
		boolean iserror = false;
		String errormessage = "";
		session = request.getSession(false);
		if ((session != null) && (session.getAttribute("usr") != null)) {
			usr = (User) session.getAttribute("usr");
			if (!(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW"))) {
				iserror = true;
				errormessage = "Illegal Access [" + usr.getLotusUserFullName() + "]";
				throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
			}
		}
		else {
			iserror = true;
			errormessage = "User login required.";
			throw new SecurityException("User login required.");
		}
		try {
			id = Integer.parseInt(request.getParameter("id"));
		}
		catch (NumberFormatException e) {
			id = -1;
		}
		if (id > 0 && !(iserror)) {
			claim = TravelClaimDB.getClaim(id);
			if (claim != null) {
				request.setAttribute("TRAVELCLAIM", claim);
				if (TravelClaimDB.setCurrentStatus(claim, usr.getPersonnel(), TravelClaimStatus.PAYMENT_PENDING)) {
					claim_owner = claim.getPersonnel();
					if ((request.getParameter("note") != null) && !request.getParameter("note").trim().equals("")) {
						TravelClaimNoteDB.addClaimNote(claim, new TravelClaimNote(usr.getPersonnel(), request.getParameter("note")));
						iserror = false;
						errormessage = "SUCCESS";
						try {
							EmailBean email = new EmailBean();
							email.setTo(new String[] {
								claim_owner.getEmailAddress()
							});
							email.setCC(new String[] {
								usr.getPersonnel().getEmailAddress()
							});
							email.setSubject("NLESD TravelClaim System - PAYMENT PENDING - Futher Information Required.");
							HashMap<String, Object> model = new HashMap<String, Object>();
							model.put("ownerfirst", claim_owner.getFirstName());
							String fiscalyear = (!(claim instanceof PDTravelClaim) ? Utils.getMonthString(claim.getFiscalMonth())
									+ " " + Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear()) : " PD on "
									+ new SimpleDateFormat("EEE MMM dd, yyyy").format(((PDTravelClaim) claim).getPD().getStartDate()));
							//String fiscalyear = Utils.getMonthString(claim.getFiscalMonth()) + " " + Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear());
							model.put("fiscalyear", fiscalyear);
							String claimnote = StringUtils.isNotEmpty(request.getParameter("note")) ? request.getParameter("note").replace(
									"\r\n", "<br />")
									: "";
							model.put("claimnote", claimnote);
							model.put("alink",
									"<a href='http://www.nlesd.ca/MemberServices/Travel/viewTravelClaimSystem.html'><B>CLICK HERE</B></a>");
							String maillink = "<a href='mailto:" + usr.getPersonnel().getEmailAddress() + "'>"
									+ usr.getPersonnel().getFullNameReverse() + "</a>";
							model.put("maillink", maillink);
							email.setBody(VelocityUtils.mergeTemplateIntoString("stafftravel/staff_travel_claim_payment_pending.vm",
									model));
							email.setFrom("ms@nlesd.ca");
							email.send();

						}
						catch (EmailException e) {
							e.printStackTrace(System.err);
							iserror = true;
							errormessage = "Claim PENDING PAYMENT, email could not be sent to " + claim_owner.getFullNameReverse()
									+ ".";
						}
					}
					else {
						iserror = false;
						errormessage = "SUCCESS";
						try {
							EmailBean email = new EmailBean();
							email.setTo(new String[] {
								claim_owner.getEmailAddress()
							});
							email.setCC(new String[] {
							//usr.getPersonnel().getEmailAddress()
							});
							email.setSubject("NLESD TravelClaim System - PAYMENT PENDING - Futher Information Required.");
							HashMap<String, Object> model = new HashMap<String, Object>();
							model.put("ownerfirst", claim_owner.getFirstName());
							String fiscalyear = Utils.getMonthString(claim.getFiscalMonth()) + " "
									+ Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear());
							model.put("fiscalyear", fiscalyear);
							String claimnote = StringUtils.isNotEmpty(request.getParameter("note")) ? request.getParameter("note").replace(
									"\r\n", "<br />")
									: "";
							model.put("claimnote", claimnote);
							model.put("alink",
									"<a href='http://www.nlesd.ca/MemberServices/Travel/viewTravelClaimSystem.html'><B>CLICK HERE</B></a>");
							String maillink = "<a href='mailto:" + usr.getPersonnel().getEmailAddress() + "'>"
									+ usr.getPersonnel().getFullNameReverse() + "</a>";
							model.put("maillink", maillink);
							email.setBody(VelocityUtils.mergeTemplateIntoString("stafftravel/staff_travel_claim_payment_pending.vm",
									model));
							email.setFrom("ms@nlesd.ca");
							email.send();
						}
						catch (Exception e) {
							e.printStackTrace(System.err);
							iserror = true;
							errormessage = "Claim PENDING PAYMENT, email could not be sent to " + claim_owner.getFullNameReverse()
									+ ".";
						}
					}
				}
				else {
					iserror = true;
					errormessage = "Payment pending NOT set.";
				}
			}
			else {
				iserror = true;
				errormessage = "Invalid option.";
			}
		}

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
		return null;
	}
}