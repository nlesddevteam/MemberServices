package com.esdnl.h1n1.site.handler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.School;
import com.awsd.security.User;
import com.esdnl.audit.bean.ApplicationObjectAuditBean;
import com.esdnl.audit.constant.ActionTypeConstant;
import com.esdnl.audit.constant.ApplicationConstant;
import com.esdnl.h1n1.bean.H1N1Exception;
import com.esdnl.h1n1.constant.Grade;
import com.esdnl.h1n1.manager.DailyReportManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class ViewPrincipalMonthlyReportRequestHandler extends RequestHandlerImpl {

	private School school;
	private Date month;

	public ViewPrincipalMonthlyReportRequestHandler() {

		this.requiredPermissions = new String[] {
			"H1N1-PRINCIPAL-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		path = "principal_index.jsp";

		try {
			User usr = (User) session.getAttribute("usr");
			school = usr.getPersonnel().getSchool();

			if (form.exists("y") && form.exists("m")) {
				Calendar tmp = Calendar.getInstance();
				tmp.clear();
				tmp.set(Calendar.YEAR, form.getInt("y"));
				tmp.set(Calendar.MONTH, form.getInt("m"));

				month = tmp.getTime();

				request.setAttribute("MONTHLYDAILYREPORTBEANS", DailyReportManager.getDailySchoolReportBeans(school, month));
				request.setAttribute("MONTHLYSUMMARYBEAN", DailyReportManager.getMonthlySchoolSummaryBean(school, month));
			}
			else {

				month = Calendar.getInstance().getTime();

				request.setAttribute("MONTHLYDAILYREPORTBEANS", DailyReportManager.getDailySchoolReportBeans(school));
				request.setAttribute("MONTHLYSUMMARYBEAN", DailyReportManager.getMonthlySchoolSummaryBean(school));
			}

			request.setAttribute("SCHOOLBEAN", school);
			request.setAttribute("TODAY", month);
			request.setAttribute("GRADES", Grade.getList());
		}
		catch (H1N1Exception e) {
			e.printStackTrace();
		}

		return path;
	}

	public void auditRequest() {

		ApplicationObjectAuditBean audit = new ApplicationObjectAuditBean();

		audit.setAction("View Principal Monthly Report - " + new SimpleDateFormat("MMMM yyyy").format(month));
		audit.setActionType(ActionTypeConstant.VIEW);
		audit.setApplication(ApplicationConstant.getH1N1());
		audit.setObjectId(Integer.toString(school.getSchoolID()));
		audit.setObjectType(school.getClass().getName());
		audit.setWho(usr.getPersonnel().getFullName());

		audit.saveBean();
	}
}
