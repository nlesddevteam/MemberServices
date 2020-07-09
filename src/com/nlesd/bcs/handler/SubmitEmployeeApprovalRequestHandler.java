package com.nlesd.bcs.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.mail.bean.EmailBean;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.velocity.VelocityUtils;
import com.nlesd.bcs.bean.AuditTrailBean;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.bean.BussingContractorEmployeeBean;
import com.nlesd.bcs.constants.EmployeeStatusConstant;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorEmployeeManager;

public class SubmitEmployeeApprovalRequestHandler extends BCSApplicationRequestHandlerImpl {
	public SubmitEmployeeApprovalRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("empid")
			});
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		BussingContractorEmployeeBean vbean =  new BussingContractorEmployeeBean();
		String message="SUBMITTED";
		if (validate_form()) {
			try {
					Integer vid = form.getInt("empid");
					BussingContractorEmployeeManager.updateContractorEmployeeStatus(vid, EmployeeStatusConstant.SUBMITTEDFORREVIEW.getValue());
					BussingContractorBean bcbean = (BussingContractorBean) request.getSession(false).getAttribute("CONTRACTOR");
					vbean = BussingContractorEmployeeManager.getBussingContractorEmployeeById(vid);
					//update audit trail
					AuditTrailBean atbean = new AuditTrailBean();
					atbean.setEntryType(EntryTypeConstant.EMPLOYEESUBMITTEDFORREVIEW);
					atbean.setEntryId(vbean.getId());
					atbean.setEntryTable(EntryTableConstant.CONTRACTOREMPLOYEE);
					DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
					atbean.setEntryNotes("Contractor employee (" + vbean.getLastName() + "," + vbean.getFirstName() + ") submitted for review on  " + dateTimeInstance.format(Calendar.getInstance().getTime()));
					atbean.setContractorId(vbean.getContractorId());
					AuditTrailManager.addAuditTrail(atbean);
					// now we send email to bussing that it has been submitted
					EmailBean email = new EmailBean();
					email.setTo("transportation@nlesd.ca");
					//email.setTo("rodneybatten@nlesd.ca");
					email.setFrom("bussingcontractorsystem@nlesd.ca");
					email.setSubject("NLESD Bussing Contractor Employee Submitted For Approval");
					HashMap<String, Object> model = new HashMap<String, Object>();
					// set values to be used in template
					model.put("ename",vbean.getLastName() + "," + vbean.getFirstName());
					model.put("cname",bcbean.getContractorName());
					email.setBody(VelocityUtils.mergeTemplateIntoString("bcs/employee_submitted.vm", model));
					email.send();

				
			}catch(Exception e){
					message = e.getMessage();
			}
		}else {
			message=com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString());
		}

		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		sb.append("<CONTRACTORS>");
		sb.append("<CONTRACTOR>");
		sb.append("<MESSAGE>" + message + "</MESSAGE>");
		sb.append("<VID>" + vbean.getId() + "</VID>");
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
}
