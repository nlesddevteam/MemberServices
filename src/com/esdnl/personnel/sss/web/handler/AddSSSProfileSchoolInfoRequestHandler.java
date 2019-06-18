package com.esdnl.personnel.sss.web.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.SchoolDB;
import com.esdnl.personnel.sss.dao.SSSProfileSchoolInfoManager;
import com.esdnl.personnel.sss.dao.StudentProgrammingInfoManager;
import com.esdnl.personnel.sss.domain.bean.SSSException;
import com.esdnl.personnel.sss.domain.bean.SSSProfileSchoolInfoBean;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class AddSSSProfileSchoolInfoRequestHandler extends RequestHandlerImpl {

	public AddSSSProfileSchoolInfoRequestHandler() {

		this.requiredPermissions = new String[] {
				"PERSONNEL-SSS-PROFILE-VIEW", "PERSONNEL-SSS-PROFILE-ADMIN"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("school_id"), new RequiredFormElement("periodMinutes"),
				new RequiredFormElement("periodsPerDay"), new RequiredFormElement("daysInCycle"),
				new RequiredFormElement("schoolYear")
		});

		if (validate_form()) {
			try {
				SSSProfileSchoolInfoBean bean = null;
				if (form.exists("profile_id"))
					bean = SSSProfileSchoolInfoManager.getSSSProfileSchoolInfoBean(form.getInt("profileId"));
				else
					bean = new SSSProfileSchoolInfoBean();

				bean.setDaysInCycle(form.getInt("daysInCycle"));
				bean.setSchool(SchoolDB.getSchool(form.getInt("school_id")));
				bean.setEnteredBy(usr.getPersonnel());
				bean.setPeriodLength(form.getInt("periodMinutes"));
				bean.setPeriodsPerDay(form.getInt("periodsPerDay"));
				bean.setProjectedSchoolYear(form.get("schoolYear"));

				if (bean.getProfileId() > 0 && bean.isDirty())
					SSSProfileSchoolInfoManager.updateSSSProfileSchoolInfoBean(bean);
				else
					bean = SSSProfileSchoolInfoManager.addSSSProfileSchoolInfoBean(bean);

				request.setAttribute("schoolprofile", bean);
				request.setAttribute("proginfos", StudentProgrammingInfoManager.getStudentProgrammingInfoBeans(bean));

				path = "schoolsummary_table3.jsp";
			}
			catch (SSSException e) {
				request.setAttribute("msg", e.getMessage());

				path = "schoolsummary_index.jsp";
			}
		}
		else {
			request.setAttribute("msg", this.validator.getErrorString());
			path = "schoolsummary_index.jsp";
		}

		return path;
	}

}
