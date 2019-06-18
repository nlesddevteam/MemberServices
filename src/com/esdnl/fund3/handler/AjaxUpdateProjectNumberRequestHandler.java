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

public class AjaxUpdateProjectNumberRequestHandler extends RequestHandlerImpl {
	public AjaxUpdateProjectNumberRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		try {
			Integer pid =Integer.parseInt(form.get("pid"));
			String pnumber=form.get("pnumber");
			ProjectManager.updateProjectNumber(pid, pnumber);
			//now we update the audit log
			AuditLogBean albn = new AuditLogBean();
			albn.setUserName(usr.getPersonnel().getFullNameReverse());
			albn.setLogEntry("Project Number Set to : " + pnumber +  " by [" + usr.getPersonnel().getFullNameReverse() + "]");
			albn.setProjectId(pid);
			AuditLogManager.addNewAuditLog(albn);
			//set emails to regional managers
			ArrayList<String>emailregions = ProjectRegionManager.getProjectRegionsByProjectEmailList(pid);
			ProjectBean pb = ProjectManager.getProjectById(pid);
			Fund3EmailManager.sendProjectNumberEmails(emailregions, pb);
			// generate XML for candidate details.
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<PROJECT>");
			sb.append("<PROJECTNUMBER>");
			sb.append("<MESSAGE>NUMBERUPDATED</MESSAGE>");
			sb.append("</PROJECTNUMBER>");
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
			sb.append("<PROJECTNUMBER>");
			sb.append("<MESSAGE>ERROR SETTING PROJECT STATUS</MESSAGE>");
			sb.append("</PROJECTNUMBER>");
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
