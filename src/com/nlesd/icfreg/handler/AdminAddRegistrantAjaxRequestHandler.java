package com.nlesd.icfreg.handler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.icfreg.bean.IcfRegApplicantBean;
import com.nlesd.icfreg.constants.IcfApplicantHistoryTypesConstant;
import com.nlesd.icfreg.constants.IcfRegistrationStatusConstant;
import com.nlesd.icfreg.dao.IcfApplicantHistoryManager;
import com.nlesd.icfreg.dao.IcfApplicantManager;

public class AdminAddRegistrantAjaxRequestHandler extends RequestHandlerImpl{
	public AdminAddRegistrantAjaxRequestHandler() {
		this.requiredPermissions = new String[] {
				"ICF-REGISTRATION-ADMIN-ADD-NEW"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("txt_StudentName"),
				new RequiredFormElement("txt_GuardianName"),
				new RequiredFormElement("txt_ParentGuardianEmail"),
				new RequiredFormElement("txt_ContactNumber1"),
				new RequiredFormElement("selSchool"),
				new RequiredFormElement("pid"),
		});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
		IOException {
		super.handleRequest(request, response);
			String message="SUCCESS";
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			IcfRegApplicantBean updatebean = new IcfRegApplicantBean();
			
			if (validate_form()) {
				try {
					//get form fields
					
					updatebean.setIcfAppFullName(form.get("txt_StudentName"));
					updatebean.setIcfAppGuaFullName(form.get("txt_GuardianName"));
					updatebean.setIcfAppEmail(form.get("txt_ParentGuardianEmail"));
					updatebean.setIcfAppContact1(form.get("txt_ContactNumber1"));
					updatebean.setIcfAppContact2(form.get("txt_ContactNumber2"));
					//we use the users school
					if(form.getInt("selSchool") == -999) {
						if(usr.getPersonnel() != null) {
							if(usr.getPersonnel().getSchool()!= null) {
								updatebean.setIcfAppSchool(usr.getPersonnel().getSchool().getSchoolID());
							}								
						}
					}else {
						updatebean.setIcfAppSchool(form.getInt("selSchool"));
					}
					
					updatebean.setIcfAppRegPer(form.getInt("pid"));
					updatebean.setIcfAppStatus(IcfRegistrationStatusConstant.SUBMITTED.getValue());
					int id = IcfApplicantManager.adminAddIcfRegApplicantBean(updatebean);
					
					//save audit trail
					IcfApplicantHistoryManager.updateAuditTrailAddNewRegistant(id,usr.getLotusUserFullName(),IcfApplicantHistoryTypesConstant.ADDNEWREGISTRANT); 
							
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
				sb.append("<RSCHOOL>");
				sb.append("<MESSAGE>" + message + "</MESSAGE>");
				sb.append("</RSCHOOL>");
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