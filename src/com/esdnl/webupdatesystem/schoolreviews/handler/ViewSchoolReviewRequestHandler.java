package com.esdnl.webupdatesystem.schoolreviews.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.webupdatesystem.schoolreviews.dao.SchoolReviewManager;
import com.esdnl.webupdatesystem.schoolreviews.dao.SchoolReviewSchoolManager;
import com.esdnl.webupdatesystem.schoolreviews.dao.SchoolReviewSectionManager;

public class ViewSchoolReviewRequestHandler extends RequestHandlerImpl {
	public ViewSchoolReviewRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("rid", "Review Id is required.")
		});
		this.requiredRoles = new String[] {
				"ADMINISTRATOR","WEB DESIGNER", "SCHOOL-REVIEW-ADMIN"
			};
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {
		super.handleRequest(request, reponse);
	    try {
	    	if(validate_form()) {
	    		request.setAttribute("review", SchoolReviewManager.getSchoolReviewById(Integer.parseInt(request.getParameter("rid").toString())));
	    		request.setAttribute("schools", SchoolReviewSchoolManager.getSchoolReviewSchoolsById(Integer.parseInt(request.getParameter("rid").toString())));
	    		request.setAttribute("sectypes", SchoolReviewSectionManager.getSchoolReviewSchoolsById());
	    		request.setAttribute("reviewsecs", SchoolReviewSectionManager.getSchoolReviewSectionsList(Integer.parseInt(request.getParameter("rid").toString())));
	    	}else {
	    		request.setAttribute("msgERR", validator.getErrorString());
	    	}
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    path = "view_school_review_details.jsp";
	    return path;
	}
}
