package com.esdnl.personnel.sss.web.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.School;
import com.esdnl.personnel.sss.dao.SSSProfileSchoolInfoManager;
import com.esdnl.personnel.sss.domain.bean.SSSException;
import com.esdnl.personnel.sss.domain.bean.SSSProfileSchoolInfoBean;
import com.esdnl.servlet.RequestHandlerImpl;

public class ViewSSSRequestHandler extends RequestHandlerImpl {

	public ViewSSSRequestHandler() {

		this.requiredPermissions = new String[] {
				"PERSONNEL-SSS-PROFILE-VIEW", "PERSONNEL-SSS-PROFILE-ADMIN"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (usr.checkPermission("PERSONNEL-SSS-PROFILE-VIEW") && usr.getPersonnel().getSchool() != null) {

			SSSProfileSchoolInfoBean profile = null;

			try {
				profile = SSSProfileSchoolInfoManager.getSSSProfileSchoolInfoBean(usr.getPersonnel().getSchool(),
						School.getNextSchoolYear());

				if (profile != null)
					request.setAttribute("schoolprofile", profile);
			}
			catch (SSSException e) {
				e.printStackTrace();
			}
		}

		this.path = "schoolsummary_schoolprofile.jsp";

		return this.path;
	}

}
