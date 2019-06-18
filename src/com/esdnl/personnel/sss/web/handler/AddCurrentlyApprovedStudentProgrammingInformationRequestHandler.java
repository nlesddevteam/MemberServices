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
import com.esdnl.personnel.sss.domain.bean.StudentProgrammingInfoBean;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class AddCurrentlyApprovedStudentProgrammingInformationRequestHandler extends RequestHandlerImpl {

	public AddCurrentlyApprovedStudentProgrammingInformationRequestHandler() {

		this.requiredPermissions = new String[] {
				"PERSONNEL-SSS-PROFILE-VIEW", "PERSONNEL-SSS-PROFILE-ADMIN"
		};

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("profile_id"), new RequiredFormElement("student_id"),
				new RequiredFormElement("student_name"), new RequiredFormElement("student_pervasive_category_id")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			SSSProfileSchoolInfoBean profile;

			try {
				profile = SSSProfileSchoolInfoManager.getSSSProfileSchoolInfoBean(form.getInt("profile_id"));

				StudentProgrammingInfoBean info = new StudentProgrammingInfoBean();

				info.setStudentId(form.get("student_id"));
				info.setSchool(profile.getSchool());
				info.setSchoolYear(profile.getCurrentSchoolYear());
				info.setStudentName(form.get("student_name"));
				info.setPervasiveCategory(form.get("student_pervasive_category_id"));

				if (!form.exists("student_staying")) {
					if (form.exists("transitioning_school_id") && form.getInt("transitioning_school_id") != -1)
						info.setTransitionSchool(SchoolDB.getSchool(form.getInt("transitioning_school_id")));

					info.setGraduating(form.exists("student_graduating"));
					info.setLeaving(form.exists("student_leaving"));
					info.setMoving(form.exists("student_moving"));
				}

				StudentProgrammingInfoManager.addCurrentlyApprovedStudentProgrammingInfoBean(info);

				request.setAttribute("msg", info.getStudentName() + " programming information added successfully.");
				request.setAttribute("schoolprofile", profile);
				request.setAttribute(
						"proginfos",
						StudentProgrammingInfoManager.getStudentProgrammingInfoBeans(profile.getSchool(),
								profile.getCurrentSchoolYear()));
			}
			catch (SSSException e) {
				e.printStackTrace();
			}
		}
		else {
			request.setAttribute("msg", validator.getErrorString());
			request.setAttribute("INVALID_FORM", form);
			try {
				request.setAttribute("schoolprofile",
						SSSProfileSchoolInfoManager.getSSSProfileSchoolInfoBean(form.getInt("profile_id")));
			}
			catch (SSSException e) {
				e.printStackTrace();
			}
		}

		return "schoolsummary_table4.jsp";
	}
}
