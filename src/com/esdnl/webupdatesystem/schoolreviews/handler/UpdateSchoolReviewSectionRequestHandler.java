package com.esdnl.webupdatesystem.schoolreviews.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.webupdatesystem.schoolreviews.bean.SchoolReviewSectionBean;
import com.esdnl.webupdatesystem.schoolreviews.dao.SchoolReviewSectionManager;

public class UpdateSchoolReviewSectionRequestHandler extends RequestHandlerImpl {
	public UpdateSchoolReviewSectionRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("id", "Section Id is required."),
				new RequiredFormElement("sectitle", "Section Title is required."),
				new RequiredFormElement("secstatus", "Section Status is required."),
				new RequiredFormElement("sectype", "Section Type is required."),
				new RequiredFormElement("secdescription", "Section Description is required."),
				new RequiredFormElement("secsortid", "Section Sort Order is required.")
		});
		this.requiredRoles = new String[] {
				"ADMINISTRATOR", "WEB DESIGNER", "SCHOOL-REVIEW-ADMIN"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
			IOException {

		super.handleRequest(request, reponse);
		try {
			
				if (!(validate_form())) {
					path = "view_school_review_section_details.jsp";
					request.setAttribute("msg", validator.getErrorString());
				}
				else {

					//save form objects
					SchoolReviewSectionBean srb = new SchoolReviewSectionBean();
					srb.setSecId(form.getInt("id"));
					srb.setSecReviewId(form.getInt("rid"));
					srb.setSecType(form.getInt("sectype"));
					srb.setSecTitle(form.get("sectitle"));
					srb.setSecStatus(form.getInt("secstatus"));
					srb.setSecDescription(form.get("secdescription"));
					srb.setSecSortId(form.getInt("secsortid"));
					SchoolReviewSectionManager.updateSchoolReviewSection(srb);
					request.setAttribute("msg", "School Review Section updated");
					request.setAttribute("sectypes", SchoolReviewSectionManager.getSchoolReviewSchoolsById());
		    		request.setAttribute("section", srb);
					path = "view_school_review_section_details.jsp";
				}
			
		}catch (Exception e) {
			e.printStackTrace(System.err);
			request.setAttribute("msg", e.getMessage());
			path = "view_school_review_section_details.jsp";
		}
		return path;
		}
}
