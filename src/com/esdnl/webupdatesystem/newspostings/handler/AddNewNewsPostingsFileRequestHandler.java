package com.esdnl.webupdatesystem.newspostings.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.newspostings.bean.NewsPostingFileBean;
import com.esdnl.webupdatesystem.newspostings.dao.NewsPostingsFileManager;

public class AddNewNewsPostingsFileRequestHandler extends RequestHandlerImpl {

	public AddNewNewsPostingsFileRequestHandler() {

		this.requiredRoles = new String[] {
				"ADMINISTRATOR", "WEB DESIGNER", "WEBANNOUNCMENTS-POST"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			//get fields
			NewsPostingFileBean npfb = new NewsPostingFileBean();
			npfb.setNewId(form.getInt("npid"));
			npfb.setNfTitle(form.get("nptitle"));
			npfb.setAddedBy(usr.getPersonnel().getFullNameReverse());

			//now we save the file
			String filelocation = "/../../nlesdweb/WebContent/includes/files/news/doc/";
			String docfilename = save_file("npfile", filelocation);
			npfb.setNfDoc(docfilename);
			//save file to db
			NewsPostingsFileManager.addNewsFile(npfb);

			//send file list back
			// generate XML for candidate details.
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<NEWSPOSTINGS>");

			Iterator<?> i = NewsPostingsFileManager.getNewsFiles(form.getInt("npid")).iterator();
			while (i.hasNext()) {

				NewsPostingFileBean p = (NewsPostingFileBean) i.next();
				sb.append("<FILES>");
				sb.append("<MESSAGE>SUCCESS</MESSAGE>");
				sb.append("<ID>" + p.getId() + "</ID>");
				sb.append("<NPFTITLE>" + p.getNfTitle() + "</NPFTITLE>");
				sb.append("<NPFDOC>" + p.getNfDoc() + "</NPFDOC>");
				sb.append("<ADDEDBY>" + p.getAddedBy() + "</ADDEDBY>");
				sb.append("<DATEADDED>" + p.getDateAddedFormatted() + "</DATEADDED>");
				sb.append("<NPID>" + p.getNewId() + "</NPID>");
				sb.append("</FILES>");
			}

			sb.append("</NEWSPOSTINGS>");
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