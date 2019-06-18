package com.esdnl.criticalissues.site.handler;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.criticalissues.bean.ReportActionItemBean;
import com.esdnl.criticalissues.bean.ReportBean;
import com.esdnl.criticalissues.dao.ReportActionItemManager;
import com.esdnl.criticalissues.dao.ReportManager;
import com.esdnl.mrs.MaintenanceRequest;
import com.esdnl.mrs.MaintenanceRequestDB;
import com.esdnl.mrs.RequestType;
import com.esdnl.mrs.StatusCode;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class AddReportActionItemRequestHandler extends RequestHandlerImpl {

	public AddReportActionItemRequestHandler() {

		requiredPermissions = new String[] {
			"CIDB-ADMIN-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			if (form.exists("op") && form.hasValue("op", "add")) {
				validator = new FormValidator(new FormElement[] {
						new RequiredFormElement("report_id"), new RequiredFormElement("action_item")
				});

				if (validate_form()) {

					ReportBean report = ReportManager.getReportBean(form.getInt("report_id"));

					MaintenanceRequest mr = new MaintenanceRequest(new RequestType("CRITICAL ISSUE"), new StatusCode("UNASSIGNED"), form.get("action_item"), Calendar.getInstance().getTime(), null, usr.getPersonnel(), report.getSchool(), 1);

					mr.setRequestId(MaintenanceRequestDB.addMaintenanceRequest(mr));

					ReportActionItemManager.addReportActionItemBean(new ReportActionItemBean(report, mr));

					request.setAttribute("msg", "Action Item added successfully.");
					request.setAttribute("REPORT_BEAN", report);

					path = "add_action_item.jsp";
				}
				else {
					request.setAttribute("FORM", form);

					request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));

					path = "index.jsp";
				}
			}
			else {
				validator = new FormValidator(new FormElement[] {
					new RequiredFormElement("report_id")
				});

				if (validate_form()) {
					ReportBean report = ReportManager.getReportBean(form.getInt("report_id"));

					if (report != null) {
						request.setAttribute("REPORT_BEAN", report);
						path = "add_action_item.jsp";
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
		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());

			path = "index.jsp";
		}

		return path;
	}

}
