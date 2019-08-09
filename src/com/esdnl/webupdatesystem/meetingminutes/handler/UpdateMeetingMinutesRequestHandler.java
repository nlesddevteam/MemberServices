package com.esdnl.webupdatesystem.meetingminutes.handler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.webupdatesystem.meetingminutes.bean.MeetingMinutesBean;
import com.esdnl.webupdatesystem.meetingminutes.bean.MeetingMinutesException;
import com.esdnl.webupdatesystem.meetingminutes.constants.MeetingCategory;
import com.esdnl.webupdatesystem.meetingminutes.dao.MeetingMinutesManager;

public class UpdateMeetingMinutesRequestHandler extends RequestHandlerImpl {

	public UpdateMeetingMinutesRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("mm_title", "Meeting Mintues Title is required."),
				new RequiredFormElement("mm_date", "Meeting Minutes Date is required.")
		});
		this.requiredRoles = new String[] {
				"ADMINISTRATOR", "WEB DESIGNER", "WEBANNOUNCMENTS-POST"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {

		super.handleRequest(request, reponse);
		String filelocation = "/../../nlesdweb/WebContent/includes/files/minutes/doc/";
		String docfilename = "";
		boolean deletedocfile = false;
		String predocfilename = "";
		boolean deletepredocfile = false;
		String reldocfilename = "";
		boolean deletereldocfile = false;
		MeetingMinutesBean mm = null;
		try {
			//get copy of original to use for file update and deletion
			mm = MeetingMinutesManager.getMeetingMinutesById(form.getInt("id"));

			//check mm doc file
			if (form.uploadFileExists("mm_doc")) {
				//save the file
				docfilename = save_file("mm_doc", filelocation);
				deletedocfile = true;

			}
			else {
				//no new file uploaded so we will retrieve the exisiting info
				docfilename = mm.getmMDoc();
			}
			//check mandatory fields
			if (validate_form()) {
				//now check the other two files to see if they changed
				if (form.uploadFileExists("mm_rel_pre_doc")) {
					predocfilename = save_file("mm_rel_pre_doc", filelocation);
					deletepredocfile = true;
				}
				else {
					predocfilename = mm.getmMRelPreDoc();
				}
				if (form.uploadFileExists("mm_rel_doc")) {
					reldocfilename = save_file("mm_rel_doc", filelocation);
					deletereldocfile = true;
				}
				else {
					reldocfilename = mm.getmMRelDoc();

				}
				//parse the fields
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				MeetingMinutesBean mmb = new MeetingMinutesBean();
				mmb.setmMTitle(form.get("mm_title").toString());
				mmb.setmMDate(sdf.parse(form.get("mm_date").toString()));
				mmb.setmMDoc(docfilename);
				mmb.setmMRelPreTitle(form.get("mm_rel_pre_title"));
				mmb.setmMRelPreDoc(predocfilename);
				mmb.setmMRelDocTitle(form.get("mm_rel_doc_title"));
				mmb.setmMRelDoc(reldocfilename);
				mmb.setAddedBy(usr.getPersonnel().getFullNameReverse());
				mmb.setId(form.getInt("id"));
				mmb.setMeetingCategory(MeetingCategory.get(Integer.parseInt(form.get("meeting_category").toString())));
				mmb.setmMMeetingVideo(form.get("mm_meeting_video"));
				MeetingMinutesManager.updateMeetingMinutes(mmb);
				//delete old files if need
				if (deletedocfile) {
					delete_file(filelocation, mm.getmMDoc());
				}
				if (deletepredocfile) {
					delete_file(filelocation, mm.getmMRelPreDoc());
				}
				if (deletereldocfile) {
					delete_file(filelocation, mm.getmMRelDoc());
				}
				request.setAttribute("meetingminutes", MeetingMinutesManager.getMeetingMinutesById(mm.getId()));
				Map<Integer, String> categorylist = new HashMap<Integer, String>();
				for (MeetingCategory t : MeetingCategory.ALL) {
					categorylist.put(t.getValue(), t.getDescription());
				}
				request.setAttribute("categorylist", categorylist);
				path = "view_meeting_minutes_details.jsp";
				request.setAttribute("msg", "Meeting Minutes has been updated");
			}
			else {

				request.setAttribute("msg", validator.getErrorString());

				request.setAttribute("meetingminutes", MeetingMinutesManager.getMeetingMinutesById(mm.getId()));
				Map<Integer, String> categorylist = new HashMap<Integer, String>();
				for (MeetingCategory t : MeetingCategory.ALL) {
					categorylist.put(t.getValue(), t.getDescription());
				}
				request.setAttribute("categorylist", categorylist);
				path = "view_meeting_minutes_details.jsp";
			}

		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			try {
				request.setAttribute("meetingminutes", MeetingMinutesManager.getMeetingMinutesById(mm.getId()));
			}
			catch (NullPointerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (MeetingMinutesException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Map<Integer, String> categorylist = new HashMap<Integer, String>();
			for (MeetingCategory t : MeetingCategory.ALL) {
				categorylist.put(t.getValue(), t.getDescription());
			}
			request.setAttribute("categorylist", categorylist);
			path = "view_meeting_minutes_details.jsp";

		}
		return path;
	}
}
