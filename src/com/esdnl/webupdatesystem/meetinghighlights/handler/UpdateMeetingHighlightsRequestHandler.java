package com.esdnl.webupdatesystem.meetinghighlights.handler;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.webupdatesystem.meetinghighlights.bean.MeetingHighlightsBean;
import com.esdnl.webupdatesystem.meetinghighlights.bean.MeetingHighlightsException;
import com.esdnl.webupdatesystem.meetinghighlights.dao.MeetingHighlightsManager;

public class UpdateMeetingHighlightsRequestHandler extends RequestHandlerImpl {

	public UpdateMeetingHighlightsRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("mh_title", "Meeting Highlights Title is required."),
				new RequiredFormElement("mh_date", "Meeting Highlights Date is required.")
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
		String filelocation = "/../../nlesdweb/WebContent/includes/files/highlights/doc/";
		String docfilename = "";
		boolean deletedocfile = false;
		String predocfilename = "";
		boolean deletepredocfile = false;
		String reldocfilename = "";
		boolean deletereldocfile = false;
		MeetingHighlightsBean mm = null;
		try {
			//get copy of original to use for file update and deletion
			mm = MeetingHighlightsManager.getMeetingHighlightsById(form.getInt("id"));

			//check mm doc file
			if (form.uploadFileExists("mh_doc")) {
				//save the file
				docfilename = save_file("mh_doc", filelocation);
				deletedocfile = true;

			}
			else {
				//no new file uploaded so we will retrieve the exisiting info
				docfilename = mm.getmHDoc();
			}
			//check mandatory fields
			if (validate_form()) {
				//now check the other two files to see if they changed
				if (form.uploadFileExists("mh_rel_pre_doc")) {
					predocfilename = save_file("mh_rel_pre_doc", filelocation);
					deletepredocfile = true;
				}
				else {
					predocfilename = mm.getmHRelDoc();
				}
				if (form.uploadFileExists("mh_rel_doc")) {
					reldocfilename = save_file("mh_rel_doc", filelocation);
					deletereldocfile = true;
				}
				else {
					reldocfilename = mm.getmHRelDoc();

				}
				//parse the fields
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				MeetingHighlightsBean mmb = new MeetingHighlightsBean();
				mmb.setmHTitle(form.get("mh_title").toString());
				mmb.setmHDate(sdf.parse(form.get("mh_date").toString()));
				mmb.setmHDoc(docfilename);
				mmb.setmHRelPreTitle(form.get("mh_rel_pre_title"));
				mmb.setmHRelPreDoc(predocfilename);
				mmb.setmHRelDocTitle(form.get("mh_rel_doc_title"));
				mmb.setmHRelDoc(reldocfilename);
				mmb.setAddedBy(usr.getPersonnel().getFullNameReverse());
				mmb.setId(form.getInt("id"));
				mmb.setmHMeetingVideo(form.get("mh_meeting_video"));
				MeetingHighlightsManager.updateMeetingHighlights(mmb);
				//delete old files if need
				if (deletedocfile) {
					delete_file(filelocation, mm.getmHDoc());
				}
				if (deletepredocfile) {
					delete_file(filelocation, mm.getmHRelPreDoc());
				}
				if (deletereldocfile) {
					delete_file(filelocation, mm.getmHRelDoc());
				}
				request.setAttribute("meetinghhighlights", MeetingHighlightsManager.getMeetingHighlightsById(mm.getId()));
				path = "view_meeting_highlights_details.jsp";
				request.setAttribute("msg", "Meeting Highlights has been updated");
			}
			else {

				request.setAttribute("msg", validator.getErrorString());

				request.setAttribute("meetinghighlights", MeetingHighlightsManager.getMeetingHighlightsById(mm.getId()));
				path = "view_meeting_highlights_details.jsp";
			}

		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			try {
				request.setAttribute("meetinghighlights", MeetingHighlightsManager.getMeetingHighlightsById(mm.getId()));
			}
			catch (NullPointerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (MeetingHighlightsException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			path = "view_meeting_highlights_details.jsp";

		}
		return path;
	}
}
