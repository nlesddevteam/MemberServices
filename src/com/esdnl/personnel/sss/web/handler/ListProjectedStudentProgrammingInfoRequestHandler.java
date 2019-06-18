package com.esdnl.personnel.sss.web.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.sss.dao.SSSProfileSchoolInfoManager;
import com.esdnl.personnel.sss.dao.StudentProgrammingInfoManager;
import com.esdnl.personnel.sss.domain.bean.SSSException;
import com.esdnl.personnel.sss.domain.bean.SSSProfileSchoolInfoBean;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class ListProjectedStudentProgrammingInfoRequestHandler extends RequestHandlerImpl {

	public ListProjectedStudentProgrammingInfoRequestHandler() {

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
			new RequiredFormElement("profile_id")
		});

		if (validate_form()) {
			try {
				SSSProfileSchoolInfoBean profile = SSSProfileSchoolInfoManager.getSSSProfileSchoolInfoBean(form.getInt("profile_id"));

				request.setAttribute("schoolprofile", profile);
				request.setAttribute(
						"proginfos",
						StudentProgrammingInfoManager.getStudentProgrammingInfoBeans(profile.getSchool(),
								profile.getProjectedSchoolYear()));

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
