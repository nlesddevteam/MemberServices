package com.esdnl.criticalissues.site.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.criticalissues.constant.ReportTypeConstant;
import com.esdnl.criticalissues.dao.ReportManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class ViewReportsByTypeRequestHandler extends RequestHandlerImpl {

	public ViewReportsByTypeRequestHandler() {

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
				new RequiredFormElement("id")
			});

			if (validate_form()) {

				ReportTypeConstant type = ReportTypeConstant.get(form.getInt("id"));

				if (type != null) {

					request.setAttribute("REPORT_TYPE", type);
					request.setAttribute("REPORT_BEANS", ReportManager.getReportBeans(type));

					path = "reports_by_type.jsp";
				}
				else {
					request.setAttribute("msg", "Invalid report type.");
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
