package com.esdnl.criticalissues.site.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.criticalissues.bean.ReportBean;
import com.esdnl.criticalissues.dao.ReportManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class DeleteReportRequestHandler extends RequestHandlerImpl {

	public DeleteReportRequestHandler() {

		requiredPermissions = new String[] {
			"CIDB-ADMIN-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {

			validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("report_id")
			});

			if (validate_form()) {

				ReportBean report = ReportManager.getReportBean(form.getInt("report_id"));

				if (report != null) {
					ReportManager.deleteReportBean(report);

					delete_file("/cidb/admin/reports/", report.getFilename());

					request.setAttribute("msg", "Report deleted successfully.");

					request.setAttribute("REPORT_TYPE", report.getReportType());
					request.setAttribute("REPORT_BEANS", ReportManager.getReportBeans(report.getReportType()));

					path = "reports_by_type.jsp";

				}
				else {
					request.setAttribute("msg", "Report with id=" + form.getInt("report_id") + " could not be found.");
					path = "index.jsp";
				}
			}
			else {
				request.setAttribute("FORM", form);

				request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));

				path = "index.jsp";
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
