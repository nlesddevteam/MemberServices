package com.nlesd.bcs.handler;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.mail.bean.EmailBean;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.velocity.VelocityUtils;
import com.nlesd.bcs.bean.AuditTrailBean;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.bean.BussingContractorEmployeeBean;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorEmployeeManager;
import com.nlesd.bcs.dao.BussingContractorManager;
public class RejectContractorEmployeeAjaxRequestHandler extends RequestHandlerImpl {
	public RejectContractorEmployeeAjaxRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("cid")
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
				Integer pid =Integer.parseInt(form.get("cid"));
				String snotes = form.get("rnotes");
				//update contractor status
				BussingContractorEmployeeManager.rejectContractorEmployee(pid, usr.getPersonnel().getFullNameReverse(),snotes);
				BussingContractorEmployeeBean ebean = BussingContractorEmployeeManager.getBussingContractorEmployeeById(pid);
				AuditTrailBean atbean = new AuditTrailBean();
				atbean.setEntryType(EntryTypeConstant.CONTRACTOREMPLOYEEREJECTED);
				atbean.setEntryId(pid);
				atbean.setEntryTable(EntryTableConstant.CONTRACTOREMPLOYEE);
				atbean.setEntryNotes("Employee: " + ebean.getLastName() + "," + ebean.getFirstName() + " Has Not Been Approved By: " + usr.getPersonnel().getFullNameReverse() );
				atbean.setContractorId(ebean.getContractorId());
				AuditTrailManager.addAuditTrail(atbean);
				//send confirmation message with link and temporary password
				// 1 create temporary password and save record
				BussingContractorBean bcbean = BussingContractorManager.getBussingContractorById(ebean.getContractorId());;
				// 2 email contractor
				EmailBean email = new EmailBean();
				email.setTo(bcbean.getEmail());
				email.setFrom("bussingcontractorsystem@nlesd.ca");
				email.setSubject("NLESD Bussing Contractor Employee Has Not Been Approved");
				HashMap<String, Object> model = new HashMap<String, Object>();
				// set values to be used in template
				model.put("employee",ebean.getLastName() + "," + ebean.getFirstName() );
				model.put("rejnotes",snotes);
				email.setBody(VelocityUtils.mergeTemplateIntoString("bcs/employee_reject.vm", model));
				//email.send();
				// generate XML for candidate details.
				sb.append("<CONTRACTOR>");
				sb.append("<CONTRACTORSTATUS>");
				sb.append("<MESSAGE>SUCCESS</MESSAGE>");
				sb.append("</CONTRACTORSTATUS>");
				sb.append("</CONTRACTOR>");
			}
			catch (Exception e) {
				// generate XML for candidate details.
				sb.append("<CONTRACTOR>");
				sb.append("<CONTRACTORSTATUS>");
				sb.append("<MESSAGE>" + e.getMessage() + "</MESSAGE>");
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
			sb.append("<CONTRACTORSTATUS>");
			sb.append("<MESSAGE>" + com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()) + "</MESSAGE>");
			sb.append("</CONTRACTORSTATUS>");
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