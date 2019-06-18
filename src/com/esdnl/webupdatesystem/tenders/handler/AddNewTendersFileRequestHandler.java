package com.esdnl.webupdatesystem.tenders.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.tenders.bean.TendersFileBean;
import com.esdnl.webupdatesystem.tenders.dao.TendersFileManager;

public class AddNewTendersFileRequestHandler extends RequestHandlerImpl{
	public AddNewTendersFileRequestHandler() {
		requiredPermissions = new String[] {
				"TENDER-ADMIN","TENDER-EDIT"
			};
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
		IOException {
		super.handleRequest(request, response);
			
			try {
				//get fields
				TendersFileBean tfb = new TendersFileBean();
				tfb.setTenderId(form.getInt("tid"));
				tfb.setTfTitle(form.get("ttitle"));
				tfb.setAddedBy(usr.getPersonnel().getFullNameReverse());
				tfb.setAddendumDate(form.getDate("tdate"));
				
				//now we save the file
            	String filelocation="/../ROOT/includes/files/tenders/doc/";
            	String docfilename = save_file("tfile", filelocation);
            	tfb.setTfDoc(docfilename);
            	//save file to db
            	TendersFileManager.addTendersFile(tfb);
            	
            	//send file list back
            	// generate XML for candidate details.
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<TENDERS>");
				
				Iterator<?> i = TendersFileManager.getTendersFiles(form.getInt("tid")).iterator();
				while(i.hasNext())
				{

					TendersFileBean p = (TendersFileBean)i.next();
					sb.append("<FILES>");
					sb.append("<MESSAGE>SUCCESS</MESSAGE>");
					sb.append("<ID>" + p.getId() + "</ID>");
					sb.append("<TFTITLE>" + p.getTfTitle() + "</TFTITLE>");
					sb.append("<TFDOC>" + p.getTfDoc() + "</TFDOC>");
					sb.append("<ADDEDBY>" + p.getAddedBy() + "</ADDEDBY>");
					sb.append("<DATEADDED>" + p.getDateAddedFormatted() + "</DATEADDED>");
					sb.append("<TID>" + p.getTenderId() + "</TID>");
					sb.append("<ADDENDUMDATE>" + p.getAddendumDateFormatted() + "</ADDENDUMDATE>");
					sb.append("</FILES>");
				}
				
				sb.append("</TENDERS>");
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
