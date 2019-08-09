package com.esdnl.webupdatesystem.meetinghighlights.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.meetinghighlights.bean.MeetingHighlightsFileBean;
import com.esdnl.webupdatesystem.meetinghighlights.dao.MeetingHighlightsFileManager;

public class AddNewMeetingHighlightsFileRequestHandler extends RequestHandlerImpl {

	public AddNewMeetingHighlightsFileRequestHandler() {

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
			MeetingHighlightsFileBean mhfb = new MeetingHighlightsFileBean();
			mhfb.setMHId(form.getInt("mhid"));
			mhfb.setMHfTitle(form.get("mhtitle"));
			mhfb.setAddedBy(usr.getPersonnel().getFullNameReverse());

			//now we save the file
			String filelocation = "/../../nlesdweb/WebContent/includes/files/highlights/doc/";
			String docfilename = save_file("mhfile", filelocation);
			mhfb.setMHfDoc(docfilename);
			//save file to db
			MeetingHighlightsFileManager.addMHFile(mhfb);

			//send file list back
			// generate XML for candidate details.
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<MEETINGHIGHLIGHTS>");

			Iterator<?> i = MeetingHighlightsFileManager.getMHFiles(form.getInt("mhid")).iterator();
			while (i.hasNext()) {

				MeetingHighlightsFileBean p = (MeetingHighlightsFileBean) i.next();
				sb.append("<FILES>");
				sb.append("<MESSAGE>SUCCESS</MESSAGE>");
				sb.append("<ID>" + p.getId() + "</ID>");
				sb.append("<MHFTITLE>" + p.getMHfTitle() + "</MHFTITLE>");
				sb.append("<MHFDOC>" + p.getMHfDoc() + "</MHFDOC>");
				sb.append("<ADDEDBY>" + p.getAddedBy() + "</ADDEDBY>");
				sb.append("<DATEADDED>" + p.getDateAddedFormatted() + "</DATEADDED>");
				sb.append("<MHID>" + p.getMHId() + "</MHID>");
				sb.append("</FILES>");
			}

			sb.append("</MEETINGHIGHLIGHTS>");
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
