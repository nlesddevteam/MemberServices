package com.esdnl.fund3.handler;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.fund3.bean.ProjectBean;
import com.esdnl.fund3.bean.ProjectDocsRegsBean;
import com.esdnl.fund3.bean.ProjectDocumentsBean;
import com.esdnl.fund3.dao.Fund3EmailManager;
import com.esdnl.fund3.dao.ProjectDocsRegsManager;
import com.esdnl.fund3.dao.ProjectDocumentManager;
import com.esdnl.fund3.dao.ProjectManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class AddNewDocumentRequestHandler extends RequestHandlerImpl{
	public AddNewDocumentRequestHandler() {

	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
		IOException {
		super.handleRequest(request, response);
			
			try {
				//get fields
				ProjectBean projectbean = ProjectManager.getProjectById(form.getInt("projectid"));
				ProjectDocumentsBean pdb = new ProjectDocumentsBean();
				String testing = form.get("projectid");
				System.out.println(testing);
				pdb.setProjectId(form.getInt("projectid"));
				pdb.setFileName(form.get("documentname"));
				String filelocation="/../MemberServices/Fund3/documents/";
				String docfilename = save_file("newdocument", filelocation);
				pdb.setoFileName(docfilename);
				pdb.setAddedBy(usr.getPersonnel().getFullNameReverse());
				//save file to db
            	int fileid=ProjectDocumentManager.addNewProjectDocument(pdb);
            	//now we save the region for the document
            	String regions[] = form.get("regions").split(",");
            	for(String s : regions)
            	{
            		ProjectDocsRegsBean pb = new ProjectDocsRegsBean();
            		pb.setProjectId(pdb.getProjectId());
            		pb.setRegionId(Integer.parseInt(s));
            		pb.setDocumentId(fileid);
            		ProjectDocsRegsManager.addNewProjectDocsRegs(pb);
            		
            		
            	}
            	//check to see if provincial
            	ArrayList<String>regionmaillist = new ArrayList<String>();
            	if(Arrays.asList(regions).contains("19")){
            		regionmaillist.add("EAST");
            		regionmaillist.add("WEST");
            		regionmaillist.add("CENTRAL");
            		regionmaillist.add("LAB");
            	}else{
            		for(String ss : regions){
            			if(ss.equals("15")){
            				regionmaillist.add("EAST");
            			}
            			if(ss.equals("16")){
            				regionmaillist.add("CENTRAL");
            			}
            			if(ss.equals("17")){
            				regionmaillist.add("WEST");
            			}
            			if(ss.equals("18")){
            				regionmaillist.add("LAB");
            			}
            		}
            		
            	}
            	//now send the emails with attachment
            	Fund3EmailManager.sendNewDocumentEmails(regionmaillist, projectbean, docfilename);
            	//send file list back
            	// generate XML for candidate details.
				ArrayList<ProjectDocumentsBean> list = ProjectDocumentManager.getProjectFilesByProjectId(pdb.getProjectId());
				// generate XML for candidate details.
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<DOCUMENTS>");
				if(! list.isEmpty()){
					for(ProjectDocumentsBean pdbb:list) {
						sb.append("<DOCUMENT>");
						sb.append("<ADDEDBY>" + pdbb.getAddedBy() + "</ADDEDBY>");
						sb.append("<DATEADDED>" + pdbb.getDateAddedFormatted() + "</DATEADDED>");
						sb.append("<ID>" + pdbb.getId() + "</ID>");
						sb.append("<FILENAME>" + pdbb.getFileName() + "</FILENAME>");
						sb.append("<OFILENAME>" + pdbb.getoFileName() + "</OFILENAME>");
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
