package com.esdnl.webupdatesystem.schoolreviews.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.webupdatesystem.schoolreviews.bean.SchoolReviewBean;
import com.esdnl.webupdatesystem.schoolreviews.bean.SchoolReviewFileBean;
import com.esdnl.webupdatesystem.schoolreviews.bean.SchoolReviewSchoolBean;
import com.esdnl.webupdatesystem.schoolreviews.dao.SchoolReviewManager;
import com.esdnl.webupdatesystem.schoolreviews.dao.SchoolReviewSchoolManager;
import com.esdnl.webupdatesystem.schoolreviews.dao.SchoolReviewSectionManager;

public class UpdateSchoolReviewRequestHandler extends RequestHandlerImpl {

	public UpdateSchoolReviewRequestHandler() {
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
			
				if (!(validate_form())) {
					path = "view_school_review_details.jsp";
					request.setAttribute("msg", validator.getErrorString());
				}
				else {

					//save form objects
					SchoolReviewBean srb = new SchoolReviewBean();
					srb.setId(form.getInt("id"));
					srb.setSrName(form.get("reviewname"));
					srb.setSrDescription(form.get("reviewdescription"));
					//check for photo
					if (form.uploadFileExists("reviewphoto")) {
						//save the file
						//need to be updated with live location
						filelocation = SchoolReviewFileBean.rootbasepath + "includes/files/schoolreview/photo/";
						photofilename = save_file("reviewphoto", filelocation);
						srb.setSrPhoto(photofilename);

					}else {
						if(form.exists("hidphoto")) {
							srb.setSrPhoto(form.get("hidphoto"));
						}
					}

					srb.setSrStatus(form.getInt("reviewstatus"));
					srb.setSrSchoolYear(form.get("reviewschoolyear"));
					SchoolReviewManager.updateSchoolReview(srb);
					
					//now save the schools
					int[] test = form.getIntArray("to");
					if(test != null) {
						//remove old ones
						SchoolReviewSchoolManager.deleteReviewSchools(srb.getId());
						//save new ones
						for(int x : test) {
							SchoolReviewSchoolBean sb = new SchoolReviewSchoolBean();
							sb.setReviewId(srb.getId());
							sb.setSchoolId(x);
							SchoolReviewSchoolManager.addSchoolReviewSchool(sb);
						}
					}
					
					request.setAttribute("msg", "School Review updated");
					//path = "add_school_review.jsp";
					request.setAttribute("review", SchoolReviewManager.getSchoolReviewById(srb.getId()));
					request.setAttribute("schools", SchoolReviewSchoolManager.getSchoolReviewSchoolsById(srb.getId()));
					request.setAttribute("sectypes", SchoolReviewSectionManager.getSchoolReviewSchoolsById());
		    		request.setAttribute("reviewsecs", SchoolReviewSectionManager.getSchoolReviewSectionsList(Integer.parseInt(request.getParameter("rid").toString())));
					
					path = "view_school_review_details.jsp";
				}
			
		}catch (Exception e) {
			e.printStackTrace(System.err);
			request.setAttribute("msg", e.getMessage());
			path = "view_school_review_details.jsp";
		}
		return path;
		}
}
