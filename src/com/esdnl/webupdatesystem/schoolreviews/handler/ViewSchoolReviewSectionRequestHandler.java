package com.esdnl.webupdatesystem.schoolreviews.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.webupdatesystem.schoolreviews.dao.SchoolReviewFileManager;
import com.esdnl.webupdatesystem.schoolreviews.dao.SchoolReviewManager;
import com.esdnl.webupdatesystem.schoolreviews.dao.SchoolReviewSectionManager;
import com.esdnl.webupdatesystem.schoolreviews.dao.SchoolReviewSectionOptionManager;

public class ViewSchoolReviewSectionRequestHandler extends RequestHandlerImpl {
	public ViewSchoolReviewSectionRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("sid", "Section Id is required.")
		});
		this.requiredRoles = new String[] {
				"ADMINISTRATOR","WEB DESIGNER"
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
	    		request.setAttribute("sectypes", SchoolReviewSectionManager.getSchoolReviewSchoolsById());
	    		request.setAttribute("section", SchoolReviewSectionManager.getSchoolReviewSectionById(Integer.parseInt(request.getParameter("sid").toString())));
	    		request.setAttribute("secfiles", SchoolReviewFileManager.getSchoolReviewFiles(Integer.parseInt(request.getParameter("sid").toString()), "S"));
	    		request.setAttribute("seclinks", SchoolReviewSectionOptionManager.getSchoolReviewSectionOptions(Integer.parseInt(request.getParameter("sid").toString()), "L"));
	    		request.setAttribute("secvideos", SchoolReviewSectionOptionManager.getSchoolReviewSectionOptions(Integer.parseInt(request.getParameter("sid").toString()), "V"));
	    		request.setAttribute("secmaps", SchoolReviewSectionOptionManager.getSchoolReviewSectionOptions(Integer.parseInt(request.getParameter("sid").toString()), "M"));
	    	}else {
	    		request.setAttribute("msg", validator.getErrorString());
	    	}
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    path = "view_school_review_section_details.jsp";
	    return path;
	}
}
