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
import com.awsd.travel.TravelClaim;
import com.awsd.travel.TravelClaimDB;
import com.esdnl.sds.SDSInfo;
import com.esdnl.sds.SDSInfoDB;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.velocity.VelocityUtils;
import com.awsd.travel.PDTravelClaim;
public class PayTravelClaimAjaxRequestHandler extends RequestHandlerImpl {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		HttpSession session = null;
		User usr = null;
		TravelClaim claim = null;
		Personnel claim_owner = null;
		int id = -1;
		StringBuffer gl_acc = null;
		SDSInfo sds = null;
		boolean iserror = false;
		String errormessage="";
		session = request.getSession(false);
		if ((session != null) && (session.getAttribute("usr") != null)) {
			usr = (User) session.getAttribute("usr");
			if (!(usr.getUserPermissions().containsKey("TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW"))) {
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
					if (!StringUtils.isEmpty(request.getParameter("gl_acc"))) {
						gl_acc = new StringBuffer(request.getParameter("gl_acc"));
					}else{
						gl_acc = new StringBuffer("");
					}
					if (gl_acc.toString().trim().length() == 17) {
							TravelClaimDB.setClaimGLAccount(claim, gl_acc.toString().trim());
							claim.setSDSGLAccountCode(gl_acc.toString().trim());
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
							request.getParameter("sds_tchr_par"))) {
						iserror=true;
						errormessage="SUCCESS";

						claim_owner = claim.getPersonnel();

						try {
								EmailBean email = new EmailBean();
								email.setTo(new String[] {
									claim_owner.getEmailAddress()
								});
								email.setSubject("Travel Claim sent for PAYMENT");
								HashMap<String, Object> model = new HashMap<String, Object>();
								model.put("ownerfirst",claim_owner.getFirstName());
								String fiscalyear = (!(claim instanceof PDTravelClaim) ? Utils.getMonthString(claim.getFiscalMonth()) + " " + Utils.getYear(claim.getFiscalMonth(), claim.getFiscalYear())
										: " PD on " + new SimpleDateFormat("EEE MMM dd, yyyy").format(((PDTravelClaim) claim).getPD().getStartDate()));
								model.put("fiscalyear",fiscalyear);;
								model.put("alink","<a href='http://www.nlesd.ca/MemberServices/Travel/viewTravelClaimSystem.html'><B>CLICK HERE</B></a>");
								email.setBody(VelocityUtils.mergeTemplateIntoString("stafftravel/staff_travel_claim_payment.vm", model));
								email.setFrom("ms@nlesd.ca");
								email.send();
						}
						catch (EmailException e) {
							e.printStackTrace(System.err);
							iserror=true;
							errormessage="Claim PAID, email could not be sent to " + claim_owner.getFullNameReverse() + ".";
						}
					}else {
						iserror=true;
						errormessage="Claim status NOT set to PAID.";
					}
						}
				else {
					iserror=true;
					errormessage="GL ACCOUNT CODE INVALID.";
				}
					}
			else {
				iserror=true;
				errormessage="Invalid option.";
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