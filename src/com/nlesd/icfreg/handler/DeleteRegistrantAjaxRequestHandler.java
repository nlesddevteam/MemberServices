package com.nlesd.icfreg.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.icfreg.bean.IcfRegApplicantBean;
import com.nlesd.icfreg.bean.IcfRegistrationPeriodBean;
import com.nlesd.icfreg.constants.IcfApplicantHistoryTypesConstant;
import com.nlesd.icfreg.dao.IcfApplicantHistoryManager;
import com.nlesd.icfreg.dao.IcfApplicantManager;
import com.nlesd.icfreg.dao.IcfRegistrationPeriodManager;

public class DeleteRegistrantAjaxRequestHandler extends RequestHandlerImpl{
	public DeleteRegistrantAjaxRequestHandler() {
		this.requiredPermissions = new String[] {
				"ICF-REGISTRATION-ADMIN-DELETE"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("rid"),
				new RequiredFormElement("ftype"),
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
			IcfRegistrationPeriodBean perbean = null;
			if (validate_form()) {
				try {
					
					//get a copy of old values to use with audit
					IcfRegApplicantBean origbean = IcfApplicantManager.getApplicantById(rid);
					perbean = IcfRegistrationPeriodManager.getRegistrationPeriodById(origbean.getIcfAppRegPer());
					//now we delete the registrant	
					IcfApplicantManager.deleteRegistrant(rid);
					//update audit trail
					IcfApplicantHistoryManager.updateAuditTrailDeleteRegistant(rid, usr.getLotusUserFullName(),IcfApplicantHistoryTypesConstant.REMOVED);
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
				ArrayList<IcfRegApplicantBean> alist= new ArrayList<>();
				if(form.get("ftype").equals("S")) {
					//school listIcf
					alist = IcfApplicantManager.getPeriodApplicantsBySchool(perbean.getIcfRegPerId(), form.getInt("sid"));
				}else {
					//reg list
					alist = IcfApplicantManager.getPeriodApplicants(perbean.getIcfRegPerId());
				}
				for(IcfRegApplicantBean b :alist) {
					sb.append("<RSCHOOL>");
					sb.append(b.toXml());
					sb.append("<MESSAGE>" + message + "</MESSAGE>");
					sb.append("</RSCHOOL>");
				}
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
