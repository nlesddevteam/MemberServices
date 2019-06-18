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

public class ApproveDeclineTravelClaimAjaxRequestHandler extends RequestHandlerImpl {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		HttpSession session = null;
		User usr = null;

		TravelClaim claim = null;
		int id = -1;
		// SMTPAuthenticatedMail smtp = null;
		Personnel supervisor = null;
		Personnel claim_owner = null;
		StringBuffer gl_acc = null;
		boolean iserror = false;
		String errormessage="";

		session = request.getSession(false);
		if ((session != null) && (session.getAttribute("usr") != null)) {
			usr = (User) session.getAttribute("usr");
			if (!(usr.getUserPermissions().containsKey("TRAVEL-CLAIM-SUPERVISOR-VIEW"))) {
				iserror=true;
				errormessage="Illegal Access [" + usr.getLotusUserFullName() + "]";
				throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
			}
		}else {
			iserror=true;
			errormessage="User login required.";
			throw new SecurityException("User login required.");
		}
		try {
			id = Integer.parseInt(request.getParameter("id"));
		}catch (NumberFormatException e) {
			id = -1;
		}
		if (id > 0 && !(iserror)) {
			claim = TravelClaimDB.getClaim(id);
			if (claim != null) {
				if (usr.getPersonnel().getPersonnelID() == claim.getSupervisor().getPersonnelID()) {
					if (request.getParameter("op") != null) {
						if (request.getParameter("op").equalsIgnoreCase("APPROVED")) {
							request.setAttribute("op", "APPROVE");
							claim_owner = claim.getPersonnel();
							supervisor = claim.getSupervisor();
							if (TravelClaimDB.setCurrentStatus(claim, usr.getPersonnel(), TravelClaimStatus.APPROVED)) {
								iserror=false;
								errormessage="APPROVED";
								try {
									EmailBean email = new EmailBean();
									email.setTo(new String[] {
										claim_owner.getEmailAddress()
									});
									email.setSubject("Travel Claim APPROVED by " + supervisor.getFullNameReverse());
									HashMap<String, Object> model = new HashMap<String, Object>();
									model.put("ownerfirst",claim_owner.getFirstName());
									String claimtype = (!(claim instanceof PDTravelClaim) ? Utils.getMonthString(claim.getFiscalMonth()) + " "
											+ Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear())
											: " PD on " + new SimpleDateFormat("EEE MMM dd, yyyy").format(((PDTravelClaim) claim).getPD().getStartDate()));
									model.put("claimtype",claimtype);
									model.put("status", "APPROVED");
									model.put("alink","<a href='http://www.nlesd.ca/MemberServices/Travel/viewTravelClaimSystem.html'><B>CLICK HERE</B></a>");
									email.setBody(VelocityUtils.mergeTemplateIntoString("stafftravel/staff_travel_claim_appdec.vm", model));
									email.setFrom("ms@nlesd.ca");
									email.send();

								}catch (EmailException e) {
									e.printStackTrace(System.err);
									iserror=true;
									errormessage="Claim approved, email could not be sent to " + claim_owner.getFullNameReverse() + ".";
								}
								if (!StringUtils.isEmpty(request.getParameter("gl_acc"))) {
											gl_acc = new StringBuffer(request.getParameter("gl_acc"));
											if (gl_acc.toString().trim().length() == 17) {
													TravelClaimDB.setClaimGLAccount(claim, gl_acc.toString().trim());
													claim.setSDSGLAccountCode(gl_acc.toString().trim());
								}else {
										iserror=true;
										errormessage="Claim approved, GL Account could not be set.";
									}
								}
							}
							else {
								iserror=true;
								errormessage="Claim not be approved.";
							}
						}else if (request.getParameter("op").equalsIgnoreCase("DECLINED")) {
							claim_owner = claim.getPersonnel();
							supervisor = claim.getSupervisor();
							if (TravelClaimDB.setCurrentStatus(claim, usr.getPersonnel(), TravelClaimStatus.REJECTED)) {
								if ((request.getParameter("note") != null) && !request.getParameter("note").trim().equals("")) {
									TravelClaimNoteDB.addClaimNote(claim,
											new TravelClaimNote(usr.getPersonnel(), request.getParameter("note")));
									iserror=false;
									errormessage="DECLINED";
									try {
										EmailBean email = new EmailBean();
										email.setTo(new String[] {
											claim_owner.getEmailAddress()
										});
										email.setSubject("Travel Claim REJECTED by " + supervisor.getFullNameReverse());
										HashMap<String, Object> model = new HashMap<String, Object>();
										model.put("ownerfirst",claim_owner.getFirstName());
										String claimtype = Utils.getMonthString(claim.getFiscalMonth()) + " " + Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear());
										model.put("claimtype",claimtype);
										model.put("status", "REJECTED");
										model.put("alink","<a href='http://www.nlesd.ca/MemberServices/Travel/viewTravelClaimSystem.html'><B>CLICK HERE</B></a>");
										email.setBody(VelocityUtils.mergeTemplateIntoString("stafftravel/staff_travel_claim_appdec.vm", model));
										email.setFrom("ms@nlesd.ca");
										email.send();

									}
									catch (EmailException e) {
										e.printStackTrace(System.err);
										iserror=true;
										errormessage="Claim declined, email could not be sent to " + claim_owner.getFullNameReverse() + ".";
									}
								}else {
									iserror=false;
									errormessage="DECLINED";
									try {
										EmailBean email = new EmailBean();
										email.setTo(new String[] {
											claim_owner.getEmailAddress()
										});
										email.setSubject("Travel Claim REJECTED by " + supervisor.getFullNameReverse());
										HashMap<String, Object> model = new HashMap<String, Object>();
										model.put("ownerfirst",claim_owner.getFirstName());
										String claimtype = Utils.getMonthString(claim.getFiscalMonth()) + " " + Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear());
										model.put("claimtype",claimtype);
										model.put("status", "REJECTED");
										model.put("alink","<a href='http://www.nlesd.ca/MemberServices/Travel/viewTravelClaimSystem.html'><B>CLICK HERE</B></a>");
										email.setBody(VelocityUtils.mergeTemplateIntoString("stafftravel/staff_travel_claim_appdec.vm", model));
										email.setFrom("ms@nlesd.ca");
										email.send();
									}catch (EmailException e) {
										e.printStackTrace(System.err);
										iserror=true;
										errormessage="Claim declined, email could not be sent to " + claim_owner.getFullNameReverse() + ".";
									}
								}
							}else {
								iserror=true;
								errormessage="Claim not be declined.";
							}
					}else {
						iserror=true;
						errormessage="Invalid Operation.";
					}
				}else {
					iserror=true;
					errormessage="You do not have permission to approve/decline this travel claim.";
				}
			}else {
				iserror=true;
				errormessage="Claim cound not be found..";
			}
		}else{
			iserror=true;
			errormessage="Claim cound not be found.";
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
			
			
		}
		return null;
	}
}
