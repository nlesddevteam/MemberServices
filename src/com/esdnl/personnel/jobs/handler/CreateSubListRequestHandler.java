package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.mail.bean.AlertBean;
import com.awsd.mail.bean.EmailException;
import com.awsd.school.GradeDB;
import com.awsd.school.SubjectDB;
import com.awsd.school.bean.RegionException;
import com.awsd.school.dao.RegionManager;
import com.esdnl.personnel.jobs.bean.ApplicantSubListAuditBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.SubListBean;
import com.esdnl.personnel.jobs.constants.SublistAuditTypeCostant;
import com.esdnl.personnel.jobs.constants.SubstituteListConstant;
import com.esdnl.personnel.jobs.dao.ApplicantSubListAuditManager;
import com.esdnl.personnel.jobs.dao.SubListManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormElementPattern;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.servlet.RequiredPatternFormElement;
import com.esdnl.util.StringUtils;

public class CreateSubListRequestHandler extends RequestHandlerImpl {

	public CreateSubListRequestHandler() {

		requiredPermissions = new String[] {
			"PERSONNEL-ADMIN-CREATE-SUBLIST"
		};
	}

	public String handleRequest(HttpServletRequest request,
															HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			validator = new FormValidator(new FormElement[] {
					new RequiredFormElement("list_type"),
					new RequiredFormElement("list_title"),
					new RequiredFormElement("list_region"),
					new RequiredFormElement("expiry_date"),
					new RequiredPatternFormElement("expiry_date", FormElementPattern.DATE_PATTERN)
			});

			if (validate_form()) {

				SubListBean list = new SubListBean();

				list.setType(SubstituteListConstant.get(form.getInt("list_type")));
				list.setTitle(form.get("list_title"));
				list.setRegion(RegionManager.getRegionBean(form.getInt("list_region")));

				String[] grades = form.getArray("list_grades");
				if ((grades != null) && (grades.length > 0)) {
					for (int i = 0; i < grades.length; i++) {
						if (!StringUtils.isEmpty(grades[i]))
							list.addGradeLevel(GradeDB.getGrade(Integer.parseInt(grades[i])));
					}
				}

				String[] subjects = form.getArray("list_subjects");
				if ((subjects != null) && (subjects.length > 0)) {
					for (int i = 0; i < subjects.length; i++) {
						if (!StringUtils.isEmpty(subjects[i])
								&& (Integer.parseInt(subjects[i]) != -1))
							list.addSubjectArea(SubjectDB.getSubject(Integer.parseInt(subjects[i])));
					}
				}

				list.setExpiryDate(form.getDate("expiry_date"));

				SubListManager.addSubListBean(list);
				request.setAttribute("msg", "Substitute list \" " + list.getTitle()
						+ "\" has been created.");
				
				//add audit trail entry for sub list creation
				ApplicantSubListAuditBean audbean = new ApplicantSubListAuditBean();
				audbean.setApplicantId("0");//no applicant sub list entry
				audbean.setSubListId(list.getId());
				audbean.setEntryType(SublistAuditTypeCostant.LISTCREATED);
				audbean.setEntryBy(usr.getPersonnel());
				audbean.setEntryNotes("List Created By: " + usr.getLotusUserFullName());
				ApplicantSubListAuditManager.addApplicantSubListAuditBean(audbean);
				path = "admin_create_sub_list.jsp";
			}
			else {
				request.setAttribute("FORM", form);

				request.setAttribute("msg",
						StringUtils.encodeHTML(validator.getErrorString()));

				path = "admin_create_sub_list.jsp";
			}
		}
		catch (RegionException e) {
			try {
				new AlertBean(e);
			}
			catch (EmailException ex) {}
			;

			request.setAttribute("msg", "Could not create sublist see administrator.");

			path = "admin_create_sub_list.jsp";
		}
		catch (JobOpportunityException e) {
			try {
				new AlertBean(e);
			}
			catch (EmailException ex) {}
			;

			request.setAttribute("msg", "Could not create sublist see administrator.");

			path = "admin_create_sub_list.jsp";
		}

		return path;
	}
}