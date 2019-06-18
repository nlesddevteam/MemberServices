package com.esdnl.school.registration.kindergarten.site.handler.admin.school;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.School;
import com.esdnl.school.registration.bean.SchoolRegistrationException;
import com.esdnl.school.registration.kindergarten.bean.KindergartenRegistrantBean;
import com.esdnl.school.registration.kindergarten.bean.KindergartenRegistrationPeriodBean;
import com.esdnl.school.registration.kindergarten.service.KindergartenRegistrationManager;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.school.bean.SchoolZoneBean;

public class ViewKindergartenRegistrationRequestHandler extends RequestHandlerImpl {

	public ViewKindergartenRegistrationRequestHandler() {

		this.requiredPermissions = new String[] {
			"KINDERGARTEN-REGISTRATION-SCHOOL-VIEW"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		String returnURL = request.getRequestURI();

		try {
			School school = usr.getPersonnel().getSchool();
			request.setAttribute("sch", school);

			SchoolZoneBean zone = school.getZone();

			if (form.exists("ddl_SchoolYear")) {
				ArrayList<KindergartenRegistrantBean> registrants = null;

				request.setAttribute("sy", form.get("ddl_SchoolYear"));
				returnURL += ("?ddl_SchoolYear=" + form.get("ddl_SchoolYear"));

				if (form.exists("ddl_Stream")) {
					request.setAttribute("ss", KindergartenRegistrantBean.SCHOOLSTREAM.get(form.getInt("ddl_Stream")));
					returnURL += ("&ddl_Stream=" + form.get("ddl_Stream"));

					registrants = (ArrayList<KindergartenRegistrantBean>) KindergartenRegistrationManager.getKindergartenRegistrantBeans(
							form.get("ddl_SchoolYear"), school.getSchoolID(), form.getInt("ddl_Stream"));
				}
				else {
					registrants = (ArrayList<KindergartenRegistrantBean>) KindergartenRegistrationManager.getKindergartenRegistrantBeans(
							form.get("ddl_SchoolYear"), school.getSchoolID());
				}

				Collections.sort(registrants, new Comparator<KindergartenRegistrantBean>() {

					@Override
					public int compare(KindergartenRegistrantBean arg0, KindergartenRegistrantBean arg1) {

						return arg0.getStudentFullName().toLowerCase().compareTo(arg1.getStudentFullName().toLowerCase());
					}

				});

				request.setAttribute("registrants", registrants);

			}

			//school can't add if there is an active registration period
			KindergartenRegistrationPeriodBean ap = KindergartenRegistrationManager.getActiveKindergartenRegistrationPeriodBean();
			Collection<KindergartenRegistrationPeriodBean> fps = KindergartenRegistrationManager.getFutureKindergartenRegistrationPeriodBean();
			KindergartenRegistrationPeriodBean fp = null;

			if (fps != null && fps.size() > 0) {
				for (KindergartenRegistrationPeriodBean rp : fps) {
					if (rp.isAccessible(zone)) {
						fp = rp;
						break;
					}
				}
			}

			if ((ap == null || !ap.isAccessible(zone)) && fp == null) {
				Collection<KindergartenRegistrationPeriodBean> mrps = KindergartenRegistrationManager.getMostRecentKindergartenRegistrationPeriodBean();

				for (KindergartenRegistrationPeriodBean rp : mrps) {
					if (rp.isAccessible(zone)) {
						request.setAttribute("krp", rp);
						break;
					}
				}
			}
		}
		catch (SchoolRegistrationException e) {
			request.setAttribute("msg", e.getMessage());
		}

		session.setAttribute("ReturnURL", returnURL);

		this.path = "school_index.jsp";

		return this.path;
	}
}
