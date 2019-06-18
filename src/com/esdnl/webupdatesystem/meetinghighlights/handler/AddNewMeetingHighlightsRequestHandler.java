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
import com.esdnl.webupdatesystem.meetinghighlights.dao.MeetingHighlightsManager;


public class AddNewMeetingHighlightsRequestHandler extends RequestHandlerImpl {
	public AddNewMeetingHighlightsRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("mh_title", "Meeting Highlights Title is required."),
				new RequiredFormElement("mh_date", "Meeting Highlights Date is required.")
		});
		this.requiredRoles = new String[] {
				"ADMINISTRATOR","WEB DESIGNER","WEBANNOUNCMENTS-POST"
			};
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {
		super.handleRequest(request, reponse);
		boolean fileok=false;
    	String filelocation="/../ROOT/includes/files/highlights/doc/";
    	String docfilename = "";
    	String predocfilename = "";
    	String reldocfilename = "";
		try {
			if(form.get("op") == null)
			{

				path = "add_meeting_highlights.jsp";
				
			}
			else{
				//check mandatory file
				if (form.uploadFileExists("mh_doc"))
				{
	                	//save the file
	                	
	                	docfilename = save_file("mh_doc", filelocation);
	                	fileok=true;
	                
				}
				else
				{
					request.setAttribute("msg", "Please Select Meeting Highlights File For Upload");
				}
				//check mandatory fields
				if (validate_form() && fileok) {
					//check for other two non mandatory files
					if (form.uploadFileExists("mh_pre_doc"))
					{
		              predocfilename = save_file("mh_pre_doc", filelocation);
		             }
					if (form.uploadFileExists("mh_r_doc"))
					{
		              reldocfilename = save_file("mh_r_doc", filelocation);
		             }
					//parse the fields
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					MeetingHighlightsBean mmb = new MeetingHighlightsBean();
					mmb.setmHTitle(form.get("mh_title").toString());
					mmb.setmHDate(sdf.parse(form.get("mh_date").toString()));
					mmb.setmHDoc(docfilename);
					mmb.setmHRelPreTitle(form.get("mh_pre_title"));
					mmb.setmHRelPreDoc(predocfilename);
					mmb.setmHRelDocTitle(form.get("mh_r_doc_title"));
					mmb.setmHRelDoc(reldocfilename);
					mmb.setAddedBy(usr.getPersonnel().getFullNameReverse());
					mmb.setmHMeetingVideo(form.get("mh_meeting_video"));
					MeetingHighlightsManager.addNewMeetingHighlights(mmb);

					path = "add_meeting_highlights.jsp";
					request.setAttribute("msg", "Meeting Highlights has been added");
				}else{

					if(! validate_form())
					{
						request.setAttribute("msg", validator.getErrorString());
					}
					path = "add_meeting_highlights.jsp";
				}
				path = "add_meeting_highlights.jsp";
			}
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "add_meeting_highlights.jsp";
		}
		return path;
	}
}
