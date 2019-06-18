package com.esdnl.school.registration.kindergarten.site.handler.admin.district;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.SchoolDB;
import com.esdnl.school.registration.bean.SchoolRegistrationException;
import com.esdnl.school.registration.kindergarten.bean.KindergartenRegistrantBean;
import com.esdnl.school.registration.kindergarten.service.KindergartenRegistrationManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class ListKindergartenRegistrantsRequestHandler extends RequestHandlerImpl {

	public ListKindergartenRegistrantsRequestHandler() {

		this.requiredPermissions = new String[] {
			"KINDERGARTEN-REGISTRATION-ADMIN-VIEW"
		};

	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		String returnURL = "";

		if (validate_form()) {
			returnURL = (request.getRequestURI() + "?");
			try {

				if (form.exists("ddl_SchoolYear")) {
					request.setAttribute("sy", form.get("ddl_SchoolYear"));
					returnURL += ("ddl_SchoolYear=" + form.get("ddl_SchoolYear"));

					if (form.exists("ddl_School")) {
						request.setAttribute("sch", SchoolDB.getSchool(form.getInt("ddl_School")));
						returnURL += ("&ddl_School=" + form.get("ddl_School"));

						if (form.exists("ddl_Stream")) {
							request.setAttribute("ss", KindergartenRegistrantBean.SCHOOLSTREAM.get(form.getInt("ddl_Stream")));
							returnURL += ("&ddl_Stream=" + form.get("ddl_Stream"));

							request.setAttribute(
									"registrants",
									KindergartenRegistrationManager.getKindergartenRegistrantBeans(form.get("ddl_SchoolYear"),
											form.getInt("ddl_School"), form.getInt("ddl_Stream")));
						}
						else
							request.setAttribute(
									"registrants",
									KindergartenRegistrationManager.getKindergartenRegistrantBeans(form.get("ddl_SchoolYear"),
											form.getInt("ddl_School")));
					}
					else if (form.exists("ddl_Stream")) {
						request.setAttribute("ss", KindergartenRegistrantBean.SCHOOLSTREAM.get(form.getInt("ddl_Stream")));
						returnURL += ("&ddl_Stream=" + form.get("ddl_Stream"));

						request.setAttribute(
								"registrants",
								KindergartenRegistrationManager.getKindergartenRegistrantBeansByStream(form.get("ddl_SchoolYear"),
										form.getInt("ddl_Stream")));
					}
					else
						request.setAttribute("registrants",
								KindergartenRegistrationManager.getKindergartenRegistrantBeans(form.get("ddl_SchoolYear")));

				}
				else if (form.exists("txt_MCPNumber")) {
					returnURL += ("txt_MCPNumber=" + form.get("txt_MCPNumber"));
					request.setAttribute(
							"registrants",
							KindergartenRegistrationManager.getKindergartenRegistrantBeansByMCP(form.get("txt_MCPNumber").trim().replaceAll(
									" ", "")));
				}
				else if (form.exists("txt_StudentName")) {
					returnURL += ("txt_StudentName=" + form.get("txt_StudentName"));

					String[] names = form.get("txt_StudentName").trim().toLowerCase().split("\\s+");
					request.setAttribute("registrants",
							KindergartenRegistrationManager.getKindergartenRegistrantBeansByStudentName(names));
				}
			}
			catch (SchoolRegistrationException e) {
				request.setAttribute("msg", e.getMessage());
			}
		}
		else {

		}

		session.setAttribute("ReturnURL", returnURL);

		this.path = "district_period_registrants.jsp";

		return this.path;
	}
}
