package com.esdnl.webupdatesystem.blogs.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.blogs.bean.BlogFileBean;
import com.esdnl.webupdatesystem.blogs.dao.BlogFileManager;

public class AddNewBlogFileRequestHandler extends RequestHandlerImpl {

	public AddNewBlogFileRequestHandler() {

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
			BlogFileBean bfb = new BlogFileBean();
			bfb.setBlogId(form.getInt("blogid"));
			bfb.setBfTitle(form.get("blogtitle"));
			bfb.setAddedBy(usr.getPersonnel().getFullNameReverse());

			//now we save the file
			String filelocation = "/../../nlesdweb/WebContent/includes/files/blogs/doc/";
			String docfilename = save_file("blogfile", filelocation);
			bfb.setBfDoc(docfilename);
			//save file to db
			BlogFileManager.addBlogFile(bfb);
			//send file list back
			// generate XML for candidate details.
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<BLOGS>");

			Iterator<?> i = BlogFileManager.getBlogsFiles(form.getInt("blogid")).iterator();
			while (i.hasNext()) {

				BlogFileBean p = (BlogFileBean) i.next();
				sb.append("<FILES>");
				sb.append("<MESSAGE>SUCCESS</MESSAGE>");
				sb.append("<ID>" + p.getId() + "</ID>");
				sb.append("<BFTITLE>" + p.getBfTitle() + "</BFTITLE>");
				sb.append("<BFDOC>" + p.getBfDoc() + "</BFDOC>");
				sb.append("<ADDEDBY>" + p.getAddedBy() + "</ADDEDBY>");
				sb.append("<DATEADDED>" + p.getDateAddedFormatted() + "</DATEADDED>");
				sb.append("<BLOGID>" + p.getBlogId() + "</BlogID>");
				sb.append("</FILES>");
			}

			sb.append("</BLOGS>");
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
