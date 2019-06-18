package com.esdnl.personnel.jobs.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.TeacherAllocationBean;
import com.esdnl.personnel.jobs.bean.TeacherAllocationRedundantPositionBean;
import com.esdnl.personnel.jobs.dao.TeacherAllocationManager;
import com.esdnl.personnel.jobs.dao.TeacherAllocationRedundantPositionManager;
import com.esdnl.personnel.v2.database.sds.EmployeeManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormElementPattern;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.servlet.RequiredPatternFormElement;
import com.esdnl.servlet.RequiredSelectionFormElement;
import com.esdnl.util.StringUtils;

public class AddUpdateTeacherAllocationRedundantPositionAjaxRequestHandler extends RequestHandlerImpl {

	public AddUpdateTeacherAllocationRedundantPositionAjaxRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("allocationid", "Allocation ID missing."),
				new RequiredSelectionFormElement("empid", -1, "Teacher selection required."),
				new RequiredFormElement("rationale", "Rationale required."), new RequiredFormElement("unit", "Unit required."),
				new RequiredPatternFormElement("unit", FormElementPattern.TWO_DECIMALS_PATTERN, "Units invalid format (x.xx).")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			try {

				String msg = "";
				TeacherAllocationRedundantPositionBean position = null;

				if (!form.exists("positionid")) {
					position = new TeacherAllocationRedundantPositionBean();

					position.setAllocationId(form.getInt("allocationid"));
					position.setEmployee(EmployeeManager.getEmployeeBean(form.get("empid").trim()));
					position.setRationale(form.get("rationale"));
					position.setUnit(form.getDouble("unit"));

					TeacherAllocationRedundantPositionManager.addTeacherAllocationRedundantPositionBean(position);

					msg = "Redundant position added successfully.";
				}
				else {
					position = TeacherAllocationRedundantPositionManager.getTeacherAllocationRedundantPositionBean(form.getInt("positionid"));

					position.setEmployee(EmployeeManager.getEmployeeBean(form.get("empid").trim()));
					position.setRationale(form.get("rationale"));
					position.setUnit(form.getDouble("unit"));

					TeacherAllocationRedundantPositionManager.updateTeacherAllocationRedundantPositionBean(position);

					msg = "Redundant position updated successfully.";
				}

				TeacherAllocationBean allocation = TeacherAllocationManager.getTeacherAllocationBean(form.getInt("allocationid"));

				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<ADD-TEACHER-ALLOCATION-REDUNDANT-POSITION-RESPONSE msg='" + msg + "'>");
				if (allocation != null)
					sb.append(allocation.toXML());
				sb.append("</ADD-TEACHER-ALLOCATION-REDUNDANT-POSITION-RESPONSE>");

				xml = StringUtils.encodeXML(sb.toString());

				System.out.println(xml);

				PrintWriter out = response.getWriter();

				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			}
			catch (Exception e) {
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");

				sb.append("<ADD-TEACHER-ALLOCATION-REDUNDANT-POSITION-RESPONSE msg='Redundant position could not be added/updated.<br />"
						+ StringUtils.encodeHTML2(e.getMessage()) + "' />");

				xml = StringUtils.encodeXML(sb.toString());

				System.out.println(xml);

				PrintWriter out = response.getWriter();

				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			}
		}
		else {
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");

			sb.append("<ADD-TEACHER-ALLOCATION-REDUNDANT-POSITION-RESPONSE msg='" + this.validator.getErrorString() + "' />");

			xml = StringUtils.encodeXML(sb.toString());

			System.out.println(xml);

			PrintWriter out = response.getWriter();

			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
		}

		return null;
	}

}
