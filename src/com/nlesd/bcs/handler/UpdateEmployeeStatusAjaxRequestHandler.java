package com.nlesd.bcs.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.bcs.bean.AuditTrailBean;
import com.nlesd.bcs.bean.BussingContractorEmployeeBean;
import com.nlesd.bcs.constants.EmployeeStatusConstant;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorEmployeeManager;

public class UpdateEmployeeStatusAjaxRequestHandler extends RequestHandlerImpl {
	public UpdateEmployeeStatusAjaxRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-CHANGE-STATUS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("vid"),
				new RequiredFormElement("oldstatus"),
				new RequiredFormElement("newstatus"),
				new RequiredFormElement("rnotes")
		});
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		if (validate_form()) {
			try {
				Integer pid =Integer.parseInt(form.get("vid"));
				String oldstatus = form.get("oldstatus");
				Integer newstatus = form.getInt("newstatus");
				String rnotes = form.get("rnotes");
				//update contractor status
				BussingContractorEmployeeManager.updateContractorEmployeeStatus(pid, newstatus);
				BussingContractorEmployeeBean vbean = BussingContractorEmployeeManager.getBussingContractorEmployeeById(pid);
				AuditTrailBean atbean = new AuditTrailBean();
				atbean.setEntryType(EntryTypeConstant.CONTRACTOREMPLOYEESTATUSUPDATED);
				atbean.setEntryId(pid);
				atbean.setEntryTable(EntryTableConstant.CONTRACTOREMPLOYEE);
				atbean.setEntryNotes("Employee (PN:" + vbean.getFirstName() + " " +vbean.getLastName()  + ") Status Changed To " 
				+ EmployeeStatusConstant.get(newstatus).getDescription() + " from " + oldstatus +
						" By: " + usr.getPersonnel().getFullNameReverse() + " Notes: " + rnotes );
				atbean.setContractorId(pid);
				AuditTrailManager.addAuditTrail(atbean);
				//send message to contractor with notes
				/**
				BussingContractorBean bcbean = BussingContractorManager.getBussingContractorById(pid);
				EmailBean email = new EmailBean();
				email.setTo(bcbean.getEmail());
				email.setFrom("bussingcontractorsystem@nlesd.ca");
				email.setSubject("NLESD Bussing Contractor System account unsuspended");
				HashMap<String, Object> model = new HashMap<String, Object>();
				// set values to be used in template
				model.put("notes", snotes);
				email.setBody(VelocityUtils.mergeTemplateIntoString("bcs/unsuspend_email.vm", model));
				email.send();
				**/
				// generate XML for candidate details.
				sb.append("<CONTRACTOR>");
				sb.append("<CONTRACTORSTATUS>");
				sb.append("<MESSAGE>STATUSUPDATED</MESSAGE>");
				sb.append("</CONTRACTORSTATUS>");
				sb.append("</CONTRACTOR>");
			}
			catch (Exception e) {
				// generate XML for candidate details.
				sb.append("<CONTRACTOR>");
				sb.append("<CONTRACTORSTATUS>");
				sb.append("<MESSAGE>ERROR SETTING CONTRACTOR STATUS</MESSAGE>");
				sb.append("</CONTRACTORSTATUS>");
				sb.append("</CONTRACTOR>");
				xml = sb.toString().replaceAll("&", "&amp;");
				PrintWriter out = response.getWriter();
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
				path = null;

			}
		}else {
			sb.append("<CONTRACTOR>");
			sb.append("<MESSAGE>" + com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()) + "</MESSAGE>");
			sb.append("</CONTRACTOR>");
		}
		xml = sb.toString().replaceAll("&", "&amp;");
		PrintWriter out = response.getWriter();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		out.write(xml);
		out.flush();
		out.close();
		path = null;
		return path;
	}

}