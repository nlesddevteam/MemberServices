package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.personnel.PersonnelDB;
import com.awsd.school.SubjectDB;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.constants.CriteriaType;
import com.esdnl.personnel.jobs.constants.TrainingMethodConstant;
import com.esdnl.personnel.jobs.dao.SEOStaffingAssignmentManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class DeleteSEOStaffingAssignmentRequestHandler extends RequestHandlerImpl {

	public DeleteSEOStaffingAssignmentRequestHandler() {

		this.requiredPermissions = new String[] {
			"PERSONNEL-ADMIN-VIEW"
		};

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("sid")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		path = "admin_add_seo_staffing_assignment.jsp";

		try {
			if (validate_form()) {
				request.setAttribute("displayMsgFnct", "$('#displayMsg').text('SUCCESS: SEO Staffing Assignment Deleted.').css('display','block').delay(5000).fadeOut();");
				SEOStaffingAssignmentManager.deleteSEOStaffingAssignmentBean(form.getInt("sid"));
			} 

			request.setAttribute("subjects", SubjectDB.getSubjects());
			request.setAttribute("trnmths", TrainingMethodConstant.ALL);
			request.setAttribute("ctypes", CriteriaType.ALL);
			request.setAttribute("seos", PersonnelDB.getPersonnelByRole("SENIOR EDUCATION OFFICIER"));
			request.setAttribute("assignments", SEOStaffingAssignmentManager.getSEOStaffingAssignmentBeans());
		}
		catch (JobOpportunityException e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
		}

		return path;
	}
}
