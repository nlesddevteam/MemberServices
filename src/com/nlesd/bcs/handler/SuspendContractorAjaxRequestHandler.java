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
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorManager;
public class SuspendContractorAjaxRequestHandler extends RequestHandlerImpl {
	public SuspendContractorAjaxRequestHandler() {

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
			BussingContractorManager.suspendContractorsStatus(pid);
			AuditTrailBean atbean = new AuditTrailBean();
			atbean.setEntryType(EntryTypeConstant.CONTRACTORSUSPENDED);
			atbean.setEntryId(pid);
			atbean.setEntryTable(EntryTableConstant.CONTRACTORS);
			atbean.setEntryNotes("Contractor Suspended By: " + usr.getPersonnel().getFullNameReverse() + " Notes: " + snotes );
			atbean.setContractorId(pid);
			AuditTrailManager.addAuditTrail(atbean);
			//send message to contractor with notes
			BussingContractorBean bcbean = BussingContractorManager.getBussingContractorById(pid);
			EmailBean email = new EmailBean();
			email.setTo(bcbean.getEmail());
			email.setFrom("bussingcontractorsystem@nlesd.ca");
			email.setSubject("NLESD Bussing Contractor System account suspended");
			HashMap<String, Object> model = new HashMap<String, Object>();
			// set values to be used in template
			model.put("notes", snotes);
			email.setBody(VelocityUtils.mergeTemplateIntoString("bcs/suspend_email.vm", model));
			//email.send();
			// generate XML for candidate details.
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<CONTRACTOR>");
			sb.append("<CONTRACTORSTATUS>");
			sb.append("<MESSAGE>STATUSUPDATED</MESSAGE>");
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
		return path;
	}

}