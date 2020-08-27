package com.nlesd.bcs.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.bcs.bean.AuditTrailBean;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorManager;

public class AdminUpdateContactorEmailAjaxRequestHandler extends RequestHandlerImpl{
	public AdminUpdateContactorEmailAjaxRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("cid"),
				new RequiredFormElement("emaila")
		});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
		IOException {
		super.handleRequest(request, response);
			String message="SUCCESS";
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			if (validate_form()) {
				int cid = form.getInt("cid");
				String emaila = form.get("emaila");
				BussingContractorBean cbean =BussingContractorManager.getBussingContractorById(cid);
				try {
					//first we check the email address is unique
					if(BussingContractorManager.checkContractorEmail(emaila)) {
						//if not used then we update it
						BussingContractorManager.updateContractorEmail(cid, emaila);
						//update audit trail
						AuditTrailBean atbean = new AuditTrailBean();
						atbean.setEntryType(EntryTypeConstant.CONTRACTOREMAILADDRESSUPDATED);
						atbean.setEntryId(cbean.getId());
						atbean.setEntryTable(EntryTableConstant.CONTRACTORS);
						DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
						atbean.setEntryNotes("Email Address Updated by:  (" +   usr.getPersonnel().getFullNameReverse() + " on " + dateTimeInstance.format(Calendar.getInstance().getTime()));
						atbean.setContractorId(cbean.getId());
						AuditTrailManager.addAuditTrail(atbean);
					}else {
						//already in system
						message="Email address already in use";
					}
					
	            }
				catch (Exception e) {
					message=e.getMessage();
					sb.append("<CONTRACTORS>");
					sb.append("<CONTRACTOR>");
					sb.append("<MESSAGE>" + message + "</MESSAGE>");
					sb.append("</CONTRACTOR>");
					sb.append("</CONTRACTORS>");
					xml = sb.toString().replaceAll("&", "&amp;");
					PrintWriter out = response.getWriter();
					response.setContentType("text/xml");
					response.setHeader("Cache-Control", "no-cache");
					out.write(xml);
					out.flush();
					out.close();
					return null;
					
				}
				sb.append("<CONTRACTORS>");
				sb.append("<CONTRACTOR>");
				sb.append("<MESSAGE>" + message + "</MESSAGE>");
				sb.append("</CONTRACTOR>");
				sb.append("</CONTRACTORS>");
			}else {
				sb.append("<CONTRACTORS>");
				sb.append("<CONTRACTOR>");
				sb.append("<MESSAGE>" + com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()) + "</MESSAGE>");
				sb.append("</CONTRACTOR>");
				sb.append("</CONTRACTORS>");
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
