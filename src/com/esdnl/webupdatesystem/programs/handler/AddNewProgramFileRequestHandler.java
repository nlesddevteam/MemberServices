package com.esdnl.webupdatesystem.programs.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.programs.bean.ProgramsFileBean;
import com.esdnl.webupdatesystem.programs.dao.ProgramsFileManager;

public class AddNewProgramFileRequestHandler extends RequestHandlerImpl{
	public AddNewProgramFileRequestHandler() {
		this.requiredRoles = new String[] {
				"ADMINISTRATOR","WEB DESIGNER","WEBANNOUNCMENTS-POST"
			};
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
		IOException {
		super.handleRequest(request, response);
			
			try {
				//get fields
				ProgramsFileBean pfb = new ProgramsFileBean();
				pfb.setProgramsId(form.getInt("programid"));
				pfb.setPfTitle(form.get("programtitle"));
				pfb.setAddedBy(usr.getPersonnel().getFullNameReverse());
				
				//now we save the file
            	String filelocation="/../ROOT/includes/files/programs/doc/";
            	String docfilename = save_file("programfile", filelocation);
            	pfb.setPfDoc(docfilename);
            	//save file to db
            	ProgramsFileManager.addProgramFile(pfb);
            	//send file list back
            	
				// generate XML for candidate details.
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<PROGRAMS>");
				
				Iterator i = ProgramsFileManager.getProgramsFiles(form.getInt("programid")).iterator();
				while(i.hasNext())
				{

					ProgramsFileBean p = (ProgramsFileBean)i.next();
					sb.append("<FILES>");
					sb.append("<MESSAGE>SUCCESS</MESSAGE>");
					sb.append("<ID>" + p.getId() + "</ID>");
					sb.append("<PFTITLE>" + p.getPfTitle() + "</PFTITLE>");
					sb.append("<PFDOC>" + p.getPfDoc() + "</PFDOC>");
					sb.append("<ADDEDBY>" + p.getAddedBy() + "</ADDEDBY>");
					sb.append("<DATEADDED>" + p.getDateAddedFormatted() + "</DATEADDED>");
					sb.append("<POLICYID>" + p.getProgramsId() + "</POLICYID>");
					sb.append("</FILES>");
				}
				
				sb.append("</PROGRAMS>");
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
