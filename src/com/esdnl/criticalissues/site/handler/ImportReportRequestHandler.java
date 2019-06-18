package com.esdnl.criticalissues.site.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.SchoolDB;
import com.esdnl.criticalissues.bean.ReportBean;
import com.esdnl.criticalissues.constant.ReportTypeConstant;
import com.esdnl.criticalissues.dao.ReportManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFileFormElement;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class ImportReportRequestHandler extends RequestHandlerImpl {

	public ImportReportRequestHandler() {

		requiredPermissions = new String[] {
			"CIDB-ADMIN-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			if (form.exists("op") && form.hasValue("op", "import")) {
				validator = new FormValidator(new FormElement[] {
						new RequiredFormElement("report_date"), new RequiredFileFormElement("report_file", "pdf"),
						new RequiredFormElement("school_id"), new RequiredFormElement("report_type")
				});

				if (validate_form()) {

					ReportBean report = new ReportBean();

					report.setFilename(save_file("report_file", "/cidb/admin/reports/"));
					report.setReportDate(form.getDate("report_date"));
					report.setReportType(ReportTypeConstant.get(form.getInt("report_type")));
					report.setSchool(SchoolDB.getSchool(form.getInt("school_id")));

					report = ReportManager.addReportBean(report);

					request.setAttribute("msg", "Report saved successfully.");
					request.setAttribute("REPORT_BEAN", report);

					path = "add_action_item.jsp";
				}
				else {
					request.setAttribute("FORM", form);

					request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));

					path = "import_report.jsp";
				}
			}
			else {
				path = "import_report.jsp";
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());

			path = "index.jsp";
		}

		return path;
	}
}
