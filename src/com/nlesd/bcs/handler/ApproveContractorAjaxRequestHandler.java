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
public class ApproveContractorAjaxRequestHandler extends RequestHandlerImpl {
	public ApproveContractorAjaxRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		try {
			Integer pid =Integer.parseInt(form.get("cid"));
			//update contractor status
			BussingContractorManager.approveContractorsStatus(pid);
			AuditTrailBean atbean = new AuditTrailBean();
			atbean.setEntryType(EntryTypeConstant.CONTRACTORAPPROVED);
			atbean.setEntryId(pid);
			atbean.setEntryTable(EntryTableConstant.CONTRACTORS);
			atbean.setEntryNotes("Contractor Approved By: " + usr.getPersonnel().getFullNameReverse() );
			atbean.setContractorId(pid);
			AuditTrailManager.addAuditTrail(atbean);
			//send confirmation message with link and temporary password
			// 1 create temporary password and save record
			BussingContractorBean bcbean = BussingContractorManager.getBussingContractorById(pid);
			String tmppwd = BussingContractorManager.createTempoararyPassword();
			Integer sid = BussingContractorManager.addBussingContractorSecurity(pid,bcbean.getEmail(),tmppwd);
			// 2 email contractor
			EmailBean email = new EmailBean();
			email.setTo(bcbean.getEmail());
			email.setFrom("bussingcontractorsystem@nlesd.ca");
			email.setSubject("NLESD Bussing Contractor System Approval");
			HashMap<String, Object> model = new HashMap<String, Object>();
			// set values to be used in template
			model.put("password", tmppwd);
			model.put("link", "http://www.nlesd.ca/MemberServices/BCS/confirmNewAccount.html?cid=" + pid);
			email.setBody(VelocityUtils.mergeTemplateIntoString("bcs/approval_confirmation.vm", model));
			//email.send();
			//update audit trail
			//AuditTrailBean atbean = new AuditTrailBean();
			atbean.setEntryType(EntryTypeConstant.CONTRACTORSECURITYADDED);
			atbean.setEntryId(sid);
			atbean.setEntryTable(EntryTableConstant.CONTRACTORSECURITY);
			atbean.setEntryNotes("Contractor Security Information Sent To " + bcbean.getEmail());
			atbean.setContractorId(pid);
			AuditTrailManager.addAuditTrail(atbean);
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
