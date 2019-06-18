package com.esdnl.h1n1.site.handler;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.esdnl.h1n1.bean.H1N1Exception;
import com.esdnl.h1n1.manager.DailyReportManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormElementPattern;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.servlet.RequiredPatternFormElement;

public class ViewAdminSchoolConsentReportRequestHandler extends RequestHandlerImpl {

	private School school;

	public ViewAdminSchoolConsentReportRequestHandler() {

		this.requiredPermissions = new String[] {
			"H1N1-ADMIN-VIEW"
		};

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("s"), new RequiredPatternFormElement("s", FormElementPattern.INTEGER_PATTERN)
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		path = "admin_school_consent_data.jsp";

		try {
			school = SchoolDB.getSchool(form.getInt("s"));

			request.setAttribute("SCHOOLGRADECONSENTDATABEANS", DailyReportManager.getConsentData(school));
			request.setAttribute("SCHOOLCONSENTSUMMARYBEAN", DailyReportManager.getSchoolConsentDataSummary(school));
			request.setAttribute("SCHOOLBEAN", school);
			request.setAttribute("TODAY", Calendar.getInstance().getTime());
		}
		catch (H1N1Exception e) {
			e.printStackTrace();
		}

		return path;
	}
}
