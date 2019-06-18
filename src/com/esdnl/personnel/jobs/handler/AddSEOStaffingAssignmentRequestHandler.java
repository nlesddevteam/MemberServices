package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.personnel.PersonnelDB;
import com.awsd.school.SubjectDB;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.SEOStaffingAssignmentBean;
import com.esdnl.personnel.jobs.constants.CriteriaType;
import com.esdnl.personnel.jobs.constants.TrainingMethodConstant;
import com.esdnl.personnel.jobs.dao.SEOStaffingAssignmentManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.servlet.RequiredMultiSelectionFormElement;

public class AddSEOStaffingAssignmentRequestHandler extends RequestHandlerImpl {

	public AddSEOStaffingAssignmentRequestHandler() {

		this.requiredPermissions = new String[] {
			"PERSONNEL-ADMIN-VIEW"
		};

		this.validator = new FormValidator(new FormElement[] {
				new RequiredMultiSelectionFormElement("personnel_id"), new RequiredFormElement("criteria_type"),
				new RequiredMultiSelectionFormElement("criteria_id")
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
				
				if (form.hasValue("op", "confirm")) {
					
					request.setAttribute("displayMsgFnct", "$('#displayMsg').text('SUCCESS: SEO Staffing Assignment Added Successfully.').css('display','block').delay(5000).fadeOut();");
					SEOStaffingAssignmentBean bean = new SEOStaffingAssignmentBean();

					bean.setCriteriaType(CriteriaType.get(form.getInt("criteria_type")));

					for (int pid : form.getIntArray("personnel_id")) {
						bean.setPersonnel(PersonnelDB.getPersonnel(pid));

						for (int cid : form.getIntArray("criteria_id")) {
							bean.setCriteriaId(cid);

							SEOStaffingAssignmentManager.addSEOStaffingAssignmentBean(bean);
						}
					}
				} 
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
