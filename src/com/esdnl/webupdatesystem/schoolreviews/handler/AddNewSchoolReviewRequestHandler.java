package com.esdnl.webupdatesystem.schoolreviews.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.webupdatesystem.schoolreviews.bean.SchoolReviewBean;
import com.esdnl.webupdatesystem.schoolreviews.bean.SchoolReviewFileBean;
import com.esdnl.webupdatesystem.schoolreviews.bean.SchoolReviewSchoolBean;
import com.esdnl.webupdatesystem.schoolreviews.dao.SchoolReviewManager;
import com.esdnl.webupdatesystem.schoolreviews.dao.SchoolReviewSchoolManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
public class AddNewSchoolReviewRequestHandler extends RequestHandlerImpl {

	public AddNewSchoolReviewRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("reviewname", "Review Name is required.")
		});
		this.requiredRoles = new String[] {
				"ADMINISTRATOR", "WEB DESIGNER"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
			IOException {

		super.handleRequest(request, reponse);
		String filelocation = "";
		String photofilename = "";
		try {
			if (form.get("op") == null) {
				path = "add_school_review.jsp";
				request.setAttribute("schools", SchoolReviewSchoolManager.getSchoolReviewSchoolsById(-1));
			}else {
				if (!(validate_form())) {
					path = "add_school_review.jsp";
					request.setAttribute("schools", SchoolReviewSchoolManager.getSchoolReviewSchoolsById(-1));
					request.setAttribute("msg", validator.getErrorString());
				}
				else {

					//save form objects
					SchoolReviewBean srb = new SchoolReviewBean();
					srb.setSrName(form.get("reviewname"));
					srb.setSrDescription(form.get("reviewdescription"));
					//check for photo
					if (form.uploadFileExists("reviewphoto")) {
						//save the file
						//need to be updated with live location
						filelocation = SchoolReviewFileBean.rootbasepath + "includes/files/schoolreview/photo/";
						photofilename = save_file("reviewphoto", filelocation);
						srb.setSrPhoto(photofilename);

					}

					srb.setAddedBy(usr.getLotusUserFullName());
					srb.setSrStatus(form.getInt("reviewstatus"));
					srb.setSrSchoolYear(form.get("reviewschoolyear"));
					int id = SchoolReviewManager.addSchoolReview(srb);
					srb.setId(id);
					//now save the schools
					int[] test = form.getIntArray("to");
					if(test != null) {
						for(int x : test) {
							SchoolReviewSchoolBean sb = new SchoolReviewSchoolBean();
							sb.setReviewId(id);
							sb.setSchoolId(x);
							SchoolReviewSchoolManager.addSchoolReviewSchool(sb);
						}
					}
					
					request.setAttribute("msg", "School Review added");
					//path = "add_school_review.jsp";
					request.setAttribute("review", srb);
					request.setAttribute("schools", SchoolReviewSchoolManager.getSchoolReviewSchoolsById(id));
					path = "view_school_review_details.jsp";
				}
			}
		}catch (Exception e) {
			e.printStackTrace(System.err);
			request.setAttribute("msg", e.getMessage());
			path = "add_school_review.jsp";
		}
		return path;
		}
}