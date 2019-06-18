package com.esdnl.h1n1.site.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.audit.bean.ApplicationObjectAuditBean;
import com.esdnl.audit.constant.ActionTypeConstant;
import com.esdnl.audit.constant.ApplicationConstant;
import com.esdnl.h1n1.bean.DailySchoolReportBean;
import com.esdnl.h1n1.bean.H1N1Exception;
import com.esdnl.h1n1.manager.DailyReportManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormElementPattern;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.servlet.RequiredPatternFormElement;

public class AddDailyReportRequestHandler extends RequestHandlerImpl {

	private DailySchoolReportBean report;

	public AddDailyReportRequestHandler() {

		this.requiredPermissions = new String[] {
			"H1N1-PRINCIPAL-VIEW"
		};

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("personnel_id"), new RequiredFormElement("school_id"),
				new RequiredFormElement("report_date"),
				new RequiredPatternFormElement("report_date", FormElementPattern.DATE_PATTERN),
				new RequiredFormElement("teacher_total"), new RequiredFormElement("support_staff_total"),
				new RequiredFormElement("student_total")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			try {

				report = new DailySchoolReportBean();

				report.setDateAdded(form.getDate("report_date"));
				report.setAdditionalComments(form.get("additional_comments"));
				report.setReportedById(form.getInt("personnel_id"));
				report.setSchoolId(form.getInt("school_id"));
				report.setStudentTotal(form.getDouble("student_total"));
				report.setSupportStaffTotal(form.getDouble("support_staff_total"));
				report.setTeacherTotal(form.getDouble("teacher_total"));

				report = DailyReportManager.addDailySchoolReportBean(report);

				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<ADD-DAILY-REPORT-REQUEST-RESPONSE>");
				sb.append("<RESPONSE-STATUS>COMPLETE</RESPONSE-STATUS>");
				sb.append("<RESPONSE-MSG>Daily report added successfully.</RESPONSE-MSG>");
				sb.append("</ADD-DAILY-REPORT-REQUEST-RESPONSE>");
				xml = sb.toString().replaceAll("&", "&amp;");

				System.out.println(xml);

				PrintWriter out = response.getWriter();

				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			}
			catch (H1N1Exception e) {
				e.printStackTrace();
			}
		}
		else
			System.out.println(validator.getErrorString());

		return null;
	}

	public void auditRequest() {

		ApplicationObjectAuditBean audit = new ApplicationObjectAuditBean();

		audit.setActionType(ActionTypeConstant.CREATE);
		audit.setAction("Add Daily Report");
		audit.setApplication(ApplicationConstant.getH1N1());
		audit.setObjectId(Integer.toString(report.getReportId()));
		audit.setObjectType(report.getClass().getName());
		audit.setWho(usr.getPersonnel().getFullName());
		audit.setNewData(report.toXML());

		audit.saveBean();
	}
}
