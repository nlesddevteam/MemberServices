package com.esdnl.fund3.handler;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.fund3.bean.ProjectDocumentsBean;
import com.esdnl.fund3.dao.ProjectDocumentManager;
import com.esdnl.servlet.RequestHandlerImpl;
public class GetProjectFilesRequestHandler extends RequestHandlerImpl{
	public GetProjectFilesRequestHandler() {

	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
		IOException {
		super.handleRequest(request, response);
		int projectid = form.getInt("pid");
		
		
			try {
					ArrayList<ProjectDocumentsBean> list = ProjectDocumentManager.getProjectFilesByProjectId(projectid);
					// generate XML for candidate details.
					String xml = null;
					StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
					sb.append("<DOCUMENTS>");
					if(! list.isEmpty()){
						for(ProjectDocumentsBean pdb:list) {
							sb.append("<DOCUMENT>");
							sb.append("<ADDEDBY>" + pdb.getAddedBy() + "</ADDEDBY>");
							sb.append("<DATEADDED>" + pdb.getDateAddedFormatted() + "</DATEADDED>");
							sb.append("<ID>" + pdb.getId() + "</ID>");
							sb.append("<FILENAME>" + pdb.getFileName() + "</FILENAME>");
							sb.append("<OFILENAME>" + pdb.getoFileName() + "</OFILENAME>");
							sb.append("<MESSAGE>LISTFOUND</MESSAGE>");
							sb.append("</DOCUMENT>");
						}
					}else{
						sb.append("<DOCUMENT>");
						sb.append("<MESSAGE>NOLIST</MESSAGE>");
						sb.append("</DOCUMENT>");
					}
					sb.append("</DOCUMENTS>");
					xml = sb.toString().replaceAll("&", "&amp;");
					System.out.println(xml);
					PrintWriter out = response.getWriter();
					response.setContentType("text/xml");
					response.setHeader("Cache-Control", "no-cache");
					out.write(xml);
					out.flush();
					out.close();
					path = null;
			}
			catch (Exception e) {
					e.printStackTrace();
					path = null;
			}
		return path;
		}
	
}
