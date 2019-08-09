package com.esdnl.webupdatesystem.meetingminutes.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.meetingminutes.bean.MeetingMinutesFileBean;
import com.esdnl.webupdatesystem.meetingminutes.dao.MeetingMinutesFileManager;

public class AddNewMeetingMinutesFileRequestHandler extends RequestHandlerImpl {

	public AddNewMeetingMinutesFileRequestHandler() {

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
			MeetingMinutesFileBean mmfb = new MeetingMinutesFileBean();
			mmfb.setMMId(form.getInt("mmid"));
			mmfb.setMMfTitle(form.get("mmtitle"));
			mmfb.setAddedBy(usr.getPersonnel().getFullNameReverse());

			//now we save the file
			String filelocation = "/../../nlesdweb/WebContent/includes/files/minutes/doc/";
			String docfilename = save_file("mmfile", filelocation);
			mmfb.setMMfDoc(docfilename);
			//save file to db
			MeetingMinutesFileManager.addMMFile(mmfb);

			//send file list back
			// generate XML for candidate details.
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<MEETINGMINUTES>");

			Iterator<?> i = MeetingMinutesFileManager.getMHFiles(form.getInt("mmid")).iterator();
			while (i.hasNext()) {

				MeetingMinutesFileBean p = (MeetingMinutesFileBean) i.next();
				sb.append("<FILES>");
				sb.append("<MESSAGE>SUCCESS</MESSAGE>");
				sb.append("<ID>" + p.getId() + "</ID>");
				sb.append("<MMFTITLE>" + p.getMMfTitle() + "</MMFTITLE>");
				sb.append("<MMFDOC>" + p.getMMfDoc() + "</MMFDOC>");
				sb.append("<ADDEDBY>" + p.getAddedBy() + "</ADDEDBY>");
				sb.append("<DATEADDED>" + p.getDateAddedFormatted() + "</DATEADDED>");
				sb.append("<MMID>" + p.getMMId() + "</MMID>");
				sb.append("</FILES>");
			}

			sb.append("</MEETINGMINUTES>");
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
