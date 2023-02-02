package com.nlesd.icfreg.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.bean.EmailException;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.velocity.VelocityUtils;
import com.nlesd.icfreg.bean.IcfRegApplicantBean;
import com.nlesd.icfreg.bean.IcfRegistrationPeriodBean;
import com.nlesd.icfreg.constants.IcfRegistrationStatusConstant;
import com.nlesd.icfreg.dao.IcfApplicantHistoryManager;
import com.nlesd.icfreg.dao.IcfApplicantManager;
import com.nlesd.icfreg.dao.IcfRegistrationPeriodManager;
import com.nlesd.icfreg.dao.IcfSchoolManager;

public class UpdateRegistrantStatusAjaxRequestHandler extends RequestHandlerImpl{
	public UpdateRegistrantStatusAjaxRequestHandler() {
		this.requiredPermissions = new String[] {
				"ICF-REGISTRATION-ADMIN-STATUS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("rid"),
				new RequiredFormElement("sid")
		});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
		IOException {
		super.handleRequest(request, response);
			String message="SUCCESS";
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			int rid = form.getInt("rid");
			int sid = form.getInt("sid");
			if (validate_form()) {
				try {
					//first we check to see if it is approving and then check limit
					if(sid == IcfRegistrationStatusConstant.APPROVED.getValue()) {
						if(!(IcfSchoolManager.checkLimits(form.getInt("scid"), form.getInt("pid")))) {
							message="School cap already reached";
						}
					}else {
						//get a copy of old values to use with audit
						IcfRegApplicantBean origbean = IcfApplicantManager.getApplicantById(rid);
						IcfRegistrationPeriodBean perbean = IcfRegistrationPeriodManager.getRegistrationPeriodById(origbean.getIcfAppRegPer());
						//update the status
						IcfApplicantManager.updateRegistrantStatus(rid, sid);
						//save audit trail
						IcfApplicantHistoryManager.updateAuditTrailStatus(usr.getLotusUserFullName(), origbean, sid);
						
						//send confirmation email.	
						HashMap<String, Object> model = new HashMap<String, Object>();
						model.put("studentname", origbean.getIcfAppFullName());
						model.put("schoolname", origbean.getIcfAppSchoolName());
						model.put("schoolyear", perbean.getIcfRegPerSchoolYear());
						model.put("regdate", origbean.getIcfAppDateSubmittedFormatted());
						try {
							EmailBean email = new EmailBean();
							email.setTo(origbean.getIcfAppEmail());
							if(sid == 2) {
								email.setSubject("Newfoundland and Labrador English School District - ICF Registration - Application Approval");
								email.setBody(VelocityUtils.mergeTemplateIntoString(
										"schools/icfregistration/acceptance_confirmation.vm", model));
								email.send();
							}else if( sid == 3) {
								email.setSubject("Newfoundland and Labrador English School District - ICF Registration - Application Not Approved");
								email.setBody(VelocityUtils.mergeTemplateIntoString(
										"schools/icfregistration/nonacceptance_confirmation.vm", model));
							}else if(sid == 4) {
								email.setSubject("Newfoundland and Labrador English School District - ICF Registration - Application Waitlisted");
								email.setBody(VelocityUtils.mergeTemplateIntoString(
										"schools/icfregistration/waitlisted_confirmation.vm", model));
								email.send();
							}
							
							
						}
						catch (EmailException e) {
							e.printStackTrace();
						}
					}
					
					
				}
				catch (Exception e) {
					message=e.getMessage();
					sb.append("<RSCHOOLS>");
					sb.append("<RSCHOOL>");
					sb.append("<MESSAGE>" + message + "</MESSAGE>");
					sb.append("</RSCHOOL>");
					sb.append("</RSCHOOLS>");
					xml = sb.toString().replaceAll("&", "&amp;");
					PrintWriter out = response.getWriter();
					response.setContentType("text/xml");
					response.setHeader("Cache-Control", "no-cache");
					out.write(xml);
					out.flush();
					out.close();
					return null;
				}
				
				sb.append("<RSCHOOLS>");
				//get all schools for period
				//ArrayList<IcfSchoolBean> alist=IcfSchoolManager.getRegistrationPeriodSchools(origbean.getIcfAppRegPer());
				//for(IcfSchoolBean b :alist) {
					sb.append("<RSCHOOL>");
					//sb.append(b.toXml());
					sb.append("<NEWSTATUS>" + IcfRegistrationStatusConstant.get(sid).getDescription() + "</NEWSTATUS>");
					sb.append("<MESSAGE>" + message + "</MESSAGE>");
					sb.append("</RSCHOOL>");
				//}
				sb.append("</RSCHOOLS>");
			}else {
				sb.append("<RSCHOOLS>");
				sb.append("<RSCHOOL>");
				sb.append("<MESSAGE>" + com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()) + "</MESSAGE>");
				sb.append("</RSCHOOL>");
				sb.append("</RSCHOOLS>");
			}

			xml = sb.toString().replaceAll("&", "&amp;");
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
			return null;
		}
	
}
