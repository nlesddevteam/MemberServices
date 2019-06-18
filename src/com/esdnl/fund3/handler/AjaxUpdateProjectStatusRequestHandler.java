package com.esdnl.fund3.handler;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.fund3.bean.AuditLogBean;
import com.esdnl.fund3.bean.ProjectBean;
import com.esdnl.fund3.dao.AuditLogManager;
import com.esdnl.fund3.dao.Fund3EmailManager;
import com.esdnl.fund3.dao.ProjectManager;
import com.esdnl.fund3.dao.ProjectRegionManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class AjaxUpdateProjectStatusRequestHandler extends RequestHandlerImpl {
	public AjaxUpdateProjectStatusRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		try {
			Integer pid =Integer.parseInt(form.get("pid"));
			Integer pstatus =Integer.parseInt(form.get("pstatus"));
			String snotes=form.get("snotes");
			ProjectManager.updateProjectStatus(pid, pstatus, snotes, usr.getPersonnel().getFullNameReverse());
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
			
			// generate XML for candidate details.
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<PROJECT>");
			sb.append("<PROJECTSTATUS>");
			sb.append("<MESSAGE>STATUSUPDATED</MESSAGE>");
			sb.append("</PROJECTSTATUS>");
			sb.append("</PROJECT>");
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
			sb.append("<PROJECT>");
			sb.append("<PROJECTSTATUS>");
			sb.append("<MESSAGE>ERROR SETTING PROJECT STATUS</MESSAGE>");
			sb.append("</PROJECTSTATUS>");
			sb.append("</PROJECT>");
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