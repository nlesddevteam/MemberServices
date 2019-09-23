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
import com.nlesd.bcs.bean.BussingContractorVehicleBean;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorVehicleManager;
public class ApproveContractorVehicleAjaxRequestHandler extends RequestHandlerImpl {
	public ApproveContractorVehicleAjaxRequestHandler() {
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
				//update contractor status
				BussingContractorVehicleManager.approveContractorVehicle(pid, usr.getPersonnel().getFullNameReverse());
				BussingContractorVehicleBean ebean = BussingContractorVehicleManager.getBussingContractorVehicleById(pid);
				AuditTrailBean atbean = new AuditTrailBean();
				atbean.setEntryType(EntryTypeConstant.CONTRACTORVEHICLEAPPROVED);
				atbean.setEntryId(pid);
				atbean.setEntryTable(EntryTableConstant.CONTRACTORVEHICLE);
				atbean.setEntryNotes("Vehicle: Plate Number:" + ebean.getvPlateNumber() + "(" + ebean.getBcBean().getCompany() + ") Approved By: " + usr.getPersonnel().getFullNameReverse() );
				atbean.setContractorId(ebean.getContractorId());
				AuditTrailManager.addAuditTrail(atbean);
				//send confirmation message with link and temporary password
				// 2 email contractor
				EmailBean email = new EmailBean();
				email.setTo(ebean.getBcBean().getEmail());
				email.setFrom("bussingcontractorsystem@nlesd.ca");
				email.setSubject("NLESD Bussing Contractor Vehicle Approval");
				HashMap<String, Object> model = new HashMap<String, Object>();
				// set values to be used in template
				model.put("pnumber",ebean.getvPlateNumber());
				model.put("snumber",ebean.getvSerialNumber());
				email.setBody(VelocityUtils.mergeTemplateIntoString("bcs/vehicle_approval.vm", model));
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
