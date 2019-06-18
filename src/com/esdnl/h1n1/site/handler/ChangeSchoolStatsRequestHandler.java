package com.esdnl.h1n1.site.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.awsd.school.SchoolException;
import com.awsd.school.bean.SchoolStatsBean;
import com.awsd.school.dao.SchoolStatsManager;
import com.esdnl.audit.bean.ApplicationObjectAuditBean;
import com.esdnl.audit.constant.ActionTypeConstant;
import com.esdnl.audit.constant.ApplicationConstant;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class ChangeSchoolStatsRequestHandler extends RequestHandlerImpl {

	private SchoolStatsBean new_stats = null;
	private SchoolStatsBean old_stats = null;

	public ChangeSchoolStatsRequestHandler() {

		this.requiredPermissions = new String[] {
			"H1N1-PRINCIPAL-VIEW"
		};

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("school_id"), new RequiredFormElement("teacher_total"),
				new RequiredFormElement("support_staff_total"), new RequiredFormElement("student_total")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			try {
				School school = SchoolDB.getSchool(form.getInt("school_id"));

				old_stats = SchoolStatsManager.getSchoolStatsBean(school);

				new_stats = new SchoolStatsBean();
				new_stats.setNumberStudents(form.getDouble("student_total"));
				new_stats.setNumberSupportStaff(form.getDouble("support_staff_total"));
				new_stats.setNumberTeachers(form.getDouble("teacher_total"));
				new_stats.setGrade0Total(form.getDouble("grade_0"));
				new_stats.setGrade1Total(form.getDouble("grade_1"));
				new_stats.setGrade2Total(form.getDouble("grade_2"));
				new_stats.setGrade3Total(form.getDouble("grade_3"));
				new_stats.setGrade4Total(form.getDouble("grade_4"));
				new_stats.setGrade5Total(form.getDouble("grade_5"));
				new_stats.setGrade6Total(form.getDouble("grade_6"));
				new_stats.setGrade7Total(form.getDouble("grade_7"));
				new_stats.setGrade8Total(form.getDouble("grade_8"));
				new_stats.setGrade9Total(form.getDouble("grade_9"));
				new_stats.setGrade10Total(form.getDouble("grade_10"));
				new_stats.setGrade11Total(form.getDouble("grade_11"));
				new_stats.setGrade12Total(form.getDouble("grade_12"));
				new_stats.setSchool(school);

				SchoolStatsManager.addSchoolStatsBean(new_stats);

				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<CHANGE-SCHOOL-STATS-REQUEST-RESPONSE>");
				sb.append("<RESPONSE-STATUS>COMPLETE</RESPONSE-STATUS>");
				sb.append("<RESPONSE-MSG>School stats changed successfully.</RESPONSE-MSG>");
				sb.append(new_stats.toXML());
				sb.append("</CHANGE-SCHOOL-STATS-REQUEST-RESPONSE>");
				xml = sb.toString().replaceAll("&", "&amp;");

				System.out.println(xml);

				PrintWriter out = response.getWriter();

				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			}
			catch (SchoolException e) {
				e.printStackTrace();
			}
		}
		else
			System.out.println("AJAX REQUEST ERROR: " + this.getClass().getName() + " - " + this.validator.getErrorString());

		return null; // AJAX Request
	}

	public void auditRequest() {

		ApplicationObjectAuditBean audit = new ApplicationObjectAuditBean();

		audit.setActionType(ActionTypeConstant.UPDATE);
		audit.setAction("Change School Stats");
		audit.setApplication(ApplicationConstant.getH1N1());
		audit.setObjectId(Integer.toString(new_stats.getSchool().getSchoolID()));
		audit.setObjectType(new_stats.getClass().getName());
		audit.setWho(usr.getPersonnel().getFullName());
		if (old_stats != null)
			audit.setOldData(old_stats.toXML());
		audit.setNewData(new_stats.toXML());

		audit.saveBean();
	}

}
