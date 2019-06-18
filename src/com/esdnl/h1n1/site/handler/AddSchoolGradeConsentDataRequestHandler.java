package com.esdnl.h1n1.site.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.esdnl.h1n1.bean.H1N1Exception;
import com.esdnl.h1n1.bean.SchoolGradeConsentDataBean;
import com.esdnl.h1n1.constant.Grade;
import com.esdnl.h1n1.manager.DailyReportManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class AddSchoolGradeConsentDataRequestHandler extends RequestHandlerImpl {

	public AddSchoolGradeConsentDataRequestHandler() {

		this.requiredPermissions = new String[] {
			"H1N1-PRINCIPAL-VIEW"
		};

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("school_id"), new RequiredFormElement("grade_id"),
				new RequiredFormElement("consented_total"), new RequiredFormElement("refused_total"),
				new RequiredFormElement("vaccinated_total")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			try {
				School school = SchoolDB.getSchool(form.getInt("school_id"));

				SchoolGradeConsentDataBean data = new SchoolGradeConsentDataBean();

				data.setSchool(school);
				data.setGrade(Grade.get(form.getInt("grade_id")));
				data.setConsented(form.getInt("consented_total"));
				data.setRefused(form.getInt("refused_total"));
				data.setVaccinated(form.getInt("vaccinated_total"));

				DailyReportManager.addSchoolGradeConsentDataBean(usr.getPersonnel(), data);

				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<ADD-CONSENT-DATA-REQUEST-RESPONSE>");
				sb.append("<RESPONSE-STATUS>COMPLETE</RESPONSE-STATUS>");
				sb.append("<RESPONSE-MSG>Consent data added/updated successfully.</RESPONSE-MSG>");
				sb.append("</ADD-CONSENT-DATA-REQUEST-RESPONSE>");
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
			System.out.println("AJAX REQUEST ERROR: " + this.getClass().getName() + " - " + this.validator.getErrorString());

		return null; // AJAX Request
	}

	public void auditRequest() {

		/*
		 * 
		 * ApplicationObjectAuditBean audit = new ApplicationObjectAuditBean();
		 * 
		 * audit.setActionType(ActionTypeConstant.UPDATE);
		 * audit.setAction("Change School Stats");
		 * audit.setApplication(ApplicationConstant.getH1N1());
		 * audit.setObjectId(Integer.toString(new_stats.getSchool().getSchoolID()));
		 * audit.setObjectType(new_stats.getClass().getName());
		 * audit.setWho(usr.getPersonnel().getFullName()); if (old_stats != null)
		 * audit.setOldData(old_stats.toXML()); audit.setNewData(new_stats.toXML());
		 * 
		 * audit.saveBean();
		 */
	}

}
