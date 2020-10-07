package com.esdnl.personnel.jobs.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.mail.bean.EmailBean;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.ApplicantRefRequestBean;
import com.esdnl.personnel.jobs.dao.ApplicantRefRequestManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.PersonnelApplicationRequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;
import com.esdnl.velocity.VelocityUtils;

public class SendReferenceRequestAjaxRequestHandler extends PersonnelApplicationRequestHandlerImpl {

	public SendReferenceRequestAjaxRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("rid", "Request ID is required"),
				new RequiredFormElement("em", "Email is required"),
				new RequiredFormElement("rt", "Request Type is required"),
				new RequiredFormElement("opt", "Request Option is required")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
    {
		super.handleRequest(request, response);
		try {
				if (validate_form()) {
					//get the parameters
					String apprefid= request.getParameter("rid");
					String email= request.getParameter("em");
					String reftype= request.getParameter("rt");
					String opttype = request.getParameter("opt");
					ApplicantProfileBean profile = null;
					profile = (ApplicantProfileBean) request.getSession().getAttribute("APPLICANT");
					if(opttype.equals("new")){
						//we have to add the ref request first
						ApplicantRefRequestBean abean = new ApplicantRefRequestBean();
						abean.setEmailAddress(email);
						abean.setFkAppSup(Integer.parseInt(apprefid));
						abean.setApplicantId(profile.getSIN());
						ApplicantRefRequestManager.addApplicantRefRequestBean(abean);
						apprefid=String.valueOf(abean.getId());
						//update the status
						ApplicantRefRequestManager.sendApplicantRefRequest(abean.getId(), reftype, "Request Sent");
					}else{
						//update the status
						ApplicantRefRequestManager.sendApplicantRefRequest(Integer.parseInt(apprefid), reftype, "Request Sent");
					}
					
					
					
					//send the email with link to correct type and id to link completed reference back
					String refTypeUrl="";
					
					if(reftype.equals("A")){
						refTypeUrl="https://www.nlesd.ca/MemberServices/Personnel/addNLESDAdminReference.html?reftype=admin&refreq="+ apprefid;
					}else if(reftype.equals("G")){
						refTypeUrl="https://www.nlesd.ca/MemberServices/Personnel/addNLESDGuideReference.html?reftype=guide&refreq="+ apprefid;
					}else if(reftype.equals("T")){
						refTypeUrl="https://www.nlesd.ca/MemberServices/Personnel/addNLESDTeacherReference.html?reftype=teacher&refreq="+ apprefid;
					}else if(reftype.equals("E")){
						refTypeUrl="https://www.nlesd.ca/MemberServices/Personnel/externalNLESDReferenceCheckRequestApp.html?reftype=external&refreq="+ apprefid;
					}else if(reftype.equals("SS")){
						refTypeUrl="https://www.nlesd.ca/MemberServices/Personnel/externalNLESDReferenceCheckRequestApp.html?reftype=support&refreq="+ apprefid;
					}else if(reftype.equals("M")){
						refTypeUrl="https://www.nlesd.ca/MemberServices/Personnel/externalNLESDReferenceCheckRequestApp.html?reftype=manage&refreq="+ apprefid;
					}
					
					EmailBean ebean = new EmailBean();
					ebean.setTo(new String[] {
						email
					});
					ebean.setFrom("ms@nlesd.ca");
					ebean.setSubject("Reference Check Requested By " + profile.getFullName());
					HashMap<String, Object> model = new HashMap<String, Object>();
					model.put("appName", profile.getFullName());
					model.put("refUrl", "<a href='" + refTypeUrl + "'><B>CLICK HERE</B></a>");
					model.put("refUrlDecline", "<a href='http://www.nlesd.ca/MemberServices/Personnel/declineReferenceRequestApp.html?refreq=" + apprefid + "'><B>CLICK HERE</B></a>");
					ebean.setBody(VelocityUtils.mergeTemplateIntoString("personnel/send_applicant_ref_request.vm", model));
					//ebean.setBody(profile.getFullName() + " is requesting you to complete a reference check."
						//	+ "<BR><BR>" + " To submit a reference click the link below to view the Reference Check Form.<br><br>"
							//+ "<a href='" + refTypeUrl + "'><B>CLICK HERE</B></a><br><br>" + "PLEASE DO NOT REPLY TO THIS MESSAGE.<br><br>"
							//+ "Member Services");
					ebean.send();
					
					String xml = null;
					StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
					sb.append("<REFCHECK>");
					sb.append("<RCHECK>");
					sb.append("<MESSAGE>SUCCESS</MESSAGE>");
					sb.append("</RCHECK>");
					sb.append("</REFCHECK>");
					xml = StringUtils.encodeXML(sb.toString());
	
					PrintWriter out = response.getWriter();
	
					response.setContentType("text/xml");
					response.setHeader("Cache-Control", "no-cache");
					out.write(xml);
					out.flush();
					out.close();
				}else {
					String xml = null;
					StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
					sb.append("<REFCHECK>");
					sb.append("<RCHECK>");
					sb.append("<MESSAGE>" + StringUtils.encodeHTML2(this.validator.getErrorString()) + "</MESSAGE>");
					sb.append("</RCHECK>");
					sb.append("</REFCHECK>");
					xml = StringUtils.encodeXML(sb.toString());
					System.out.println(xml);
					PrintWriter out = response.getWriter();
					response.setContentType("text/xml");
					response.setHeader("Cache-Control", "no-cache");
					out.write(xml);
					out.flush();
					out.close();
				}

			}
			catch (Exception e) {
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<REFCHECK>");
				sb.append("<RCHECK>");
				sb.append("<MESSAGE>" + StringUtils.encodeHTML2(e.getMessage()) + "</MESSAGE>");
				sb.append("</RCHECK>");
				sb.append("</REFCHECK>");
				xml = StringUtils.encodeXML(sb.toString());
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
