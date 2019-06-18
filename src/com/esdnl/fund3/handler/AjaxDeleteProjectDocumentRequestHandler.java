package com.esdnl.fund3.handler;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.fund3.bean.AuditLogBean;
import com.esdnl.fund3.dao.AuditLogManager;
import com.esdnl.fund3.dao.ProjectDocumentManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class AjaxDeleteProjectDocumentRequestHandler extends RequestHandlerImpl {
	public AjaxDeleteProjectDocumentRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		try {
			Integer id =Integer.parseInt(form.get("id"));
			Integer pid =Integer.parseInt(form.get("pid"));
			String fid=form.get("fid");
			String fname=form.get("fname");
			//get list of files to delete from server directory
			String filelocation="/../MemberServices/Fund3/documents/";
			//get file name to use with audit log
			delete_file(filelocation, fid);
			ProjectDocumentManager.deleteProjectDocument(id);
			//now we update the audit log
			AuditLogBean albn = new AuditLogBean();
			albn.setUserName(usr.getPersonnel().getFullNameReverse());
			albn.setLogEntry("Project File Deleted: [" + fname + "]");
			albn.setProjectId(pid);
			AuditLogManager.addNewAuditLog(albn);
			
			// generate XML for candidate details.
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<DOCUMENTS>");
			sb.append("<DOCUMENT>");
			sb.append("<MESSAGE>FILEDELETED</MESSAGE>");
			sb.append("</DOCUMENT>");
			sb.append("</DOCUMENTS>");
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
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<DOCUMENTS>");
			sb.append("<DOCUMENT>");
			sb.append("<MESSAGE>ERROR DELETING FILE</MESSAGE>");
			sb.append("</DOCUMENT>");
			sb.append("</DOCUMENTS>");
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
