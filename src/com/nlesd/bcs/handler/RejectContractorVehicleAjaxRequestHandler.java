package com.nlesd.bcs.handler;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.mail.bean.EmailBean;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.velocity.VelocityUtils;
import com.nlesd.bcs.bean.AuditTrailBean;
import com.nlesd.bcs.bean.BussingContractorVehicleBean;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorVehicleManager;
public class RejectContractorVehicleAjaxRequestHandler extends RequestHandlerImpl {
	public RejectContractorVehicleAjaxRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		try {
			Integer pid =Integer.parseInt(form.get("cid"));
			String snotes = form.get("rnotes");
			//update contractor status
			BussingContractorVehicleManager.rejectContractorVehicle(pid, usr.getPersonnel().getFullNameReverse(),snotes);
			BussingContractorVehicleBean ebean = BussingContractorVehicleManager.getBussingContractorVehicleById(pid);
			AuditTrailBean atbean = new AuditTrailBean();
			atbean.setEntryType(EntryTypeConstant.CONTRACTORVEHICLEREJECTED);
			atbean.setEntryId(pid);
			atbean.setEntryTable(EntryTableConstant.CONTRACTORVEHICLE);
			atbean.setEntryNotes("Vehicle: Plate Number:" + ebean.getvPlateNumber() + "(" + ebean.getBcBean().getCompany() + ") Rejected By: " + usr.getPersonnel().getFullNameReverse() );
			atbean.setContractorId(ebean.getContractorId());
			AuditTrailManager.addAuditTrail(atbean);
			//send confirmation message with link and temporary password
			// 2 email contractor
			EmailBean email = new EmailBean();
			email.setTo(ebean.getBcBean().getEmail());
			email.setFrom("bussingcontractorsystem@nlesd.ca");
			email.setSubject("NLESD Bussing Contractor Vehicle Has Not Been Approved");
			HashMap<String, Object> model = new HashMap<String, Object>();
			// set values to be used in template
			model.put("pnumber",ebean.getvPlateNumber());
			model.put("snumber",ebean.getvSerialNumber());
			model.put("rejnotes",snotes);
			email.setBody(VelocityUtils.mergeTemplateIntoString("bcs/vehicle_reject.vm", model));
			//email.send();
			// generate XML for candidate details.
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<CONTRACTOR>");
			sb.append("<CONTRACTORSTATUS>");
			sb.append("<MESSAGE>SUCCESS</MESSAGE>");
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
		catch (Exception e) {
			// generate XML for candidate details.
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
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
		return path;
	}

}
