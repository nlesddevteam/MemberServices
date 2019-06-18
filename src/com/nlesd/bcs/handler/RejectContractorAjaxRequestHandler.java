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
public class RejectContractorAjaxRequestHandler extends RequestHandlerImpl {
	public RejectContractorAjaxRequestHandler() {

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
			BussingContractorManager.rejectContractorsStatus(pid);
			AuditTrailBean atbean = new AuditTrailBean();
			atbean.setEntryType(EntryTypeConstant.CONTRACTORREJECTED);
			atbean.setEntryId(pid);
			atbean.setEntryTable(EntryTableConstant.CONTRACTORS);
			atbean.setEntryNotes("Contractor Rejected By: " + usr.getPersonnel().getFullNameReverse() + " Notes: " + snotes );
			atbean.setContractorId(pid);
			AuditTrailManager.addAuditTrail(atbean);
			//send message to contractor with notes
			BussingContractorBean bcbean = BussingContractorManager.getBussingContractorById(pid);
			EmailBean email = new EmailBean();
			email.setTo(bcbean.getEmail());
			email.setFrom("bussingcontractorsystem@nlesd.ca");
			email.setSubject("NLESD Bussing Contractor System Rejection");
			HashMap<String, Object> model = new HashMap<String, Object>();
			// set values to be used in template
			model.put("notes", snotes);
			email.setBody(VelocityUtils.mergeTemplateIntoString("bcs/rejection_email.vm", model));
			//email.send();
			//update contractor status
			/*
			//now we update the audit log
			AuditLogBean albn = new AuditLogBean();
			albn.setUserName(usr.getPersonnel().getFullNameReverse());
			String status="";
			switch(pstatus)
			{
			case 1:
				status="Approved";
				break;
			case 2:
				status="Rejected";
				break;
			default:
				status="Committed";
				break;	
			}
			albn.setLogEntry("Project Status Set to : " + status +  " by [" + usr.getPersonnel().getFullNameReverse() + "]");
			albn.setProjectId(pid);
			AuditLogManager.addNewAuditLog(albn);
			if(status == "Approved"){
				//send emails to regional managers
				ProjectBean pb = ProjectManager.getProjectById(pid);
				ProjectRegionManager.getProjectRegionsByProject(pid);
				ArrayList<String>emailregions = ProjectRegionManager.getProjectRegionsByProjectEmailList(pid);
				Fund3EmailManager.sendApprovedProjectEmails(emailregions, pb);
				//next we send new project message to senior accountant
				Fund3EmailManager.sendApprovedProjectEmailSeniorAcct(pb);
			}
			*/
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
