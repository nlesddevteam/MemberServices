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
import com.esdnl.webupdatesystem.meetingminutes.constants.MeetingCategory;
import com.esdnl.webupdatesystem.meetingminutes.dao.MeetingMinutesManager;

public class AddNewMeetingMinutesRequestHandler extends RequestHandlerImpl {

	public AddNewMeetingMinutesRequestHandler() {

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
		boolean fileok = false;
		String filelocation = "/../../nlesdweb/WebContent/includes/files/minutes/doc/";
		String docfilename = "";
		String predocfilename = "";
		String reldocfilename = "";
		try {
			if (form.get("op") == null) {
				Map<Integer, String> categorylist = new HashMap<Integer, String>();
				for (MeetingCategory t : MeetingCategory.ALL) {
					categorylist.put(t.getValue(), t.getDescription());
				}
				request.setAttribute("categorylist", categorylist);

				path = "add_meeting_minutes.jsp";

			}
			else {
				//check mandatory file
				if (form.uploadFileExists("mm_doc")) {
					//save the file

					docfilename = save_file("mm_doc", filelocation);
					fileok = true;

				}
				else {
					Map<Integer, String> categorylist = new HashMap<Integer, String>();
					for (MeetingCategory t : MeetingCategory.ALL) {
						categorylist.put(t.getValue(), t.getDescription());
					}
					request.setAttribute("categorylist", categorylist);
					request.setAttribute("msg", "Please Select Meeting Minutes File For Upload");
				}
				//check mandatory fields
				if (validate_form() && fileok) {
					//check for other two non mandatory files
					if (form.uploadFileExists("mm_rel_pre_doc")) {
						predocfilename = save_file("mm_rel_pre_doc", filelocation);
					}
					if (form.uploadFileExists("mm_rel_doc")) {
						reldocfilename = save_file("mm_rel_doc", filelocation);
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
					mmb.setMeetingCategory(MeetingCategory.get(Integer.parseInt(form.get("meeting_category").toString())));
					mmb.setmMMeetingVideo(form.get("mm_meeting_video"));
					MeetingMinutesManager.addNewMeetingMinutes(mmb);

					path = "add_meeting_minutes.jsp";
					request.setAttribute("msg", "Meeting Minutes has been added");
					Map<Integer, String> categorylist = new HashMap<Integer, String>();
					for (MeetingCategory t : MeetingCategory.ALL) {
						categorylist.put(t.getValue(), t.getDescription());
					}
					request.setAttribute("categorylist", categorylist);
				}
				else {

					if (!validate_form()) {
						request.setAttribute("msg", validator.getErrorString());
					}
					path = "add_meeting_minutes.jsp";
					Map<Integer, String> categorylist = new HashMap<Integer, String>();
					for (MeetingCategory t : MeetingCategory.ALL) {
						categorylist.put(t.getValue(), t.getDescription());
					}
					request.setAttribute("categorylist", categorylist);
				}
				path = "add_meeting_minutes.jsp";
			}
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "add_meeting_minutes.jsp";
			Map<Integer, String> categorylist = new HashMap<Integer, String>();
			for (MeetingCategory t : MeetingCategory.ALL) {
				categorylist.put(t.getValue(), t.getDescription());
			}
			request.setAttribute("categorylist", categorylist);
		}
		return path;
	}
}
