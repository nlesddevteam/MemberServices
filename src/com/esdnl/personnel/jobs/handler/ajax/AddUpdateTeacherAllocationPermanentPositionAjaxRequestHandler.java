package com.esdnl.personnel.jobs.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.TeacherAllocationBean;
import com.esdnl.personnel.jobs.bean.TeacherAllocationPermanentPositionBean;
import com.esdnl.personnel.jobs.dao.TeacherAllocationManager;
import com.esdnl.personnel.jobs.dao.TeacherAllocationPermanentPositionManager;
import com.esdnl.personnel.v2.database.sds.EmployeeManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormElementPattern;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.servlet.RequiredPatternFormElement;
import com.esdnl.servlet.RequiredSelectionFormElement;
import com.esdnl.util.StringUtils;

public class AddUpdateTeacherAllocationPermanentPositionAjaxRequestHandler extends RequestHandlerImpl {

	public AddUpdateTeacherAllocationPermanentPositionAjaxRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("allocationid", "Allocation ID missing."),
				new RequiredSelectionFormElement("empid", -1, "Teacher selection required."),
				new RequiredFormElement("unit", "Unit required."),
				new RequiredPatternFormElement("unit", FormElementPattern.TWO_DECIMALS_PATTERN, "Units invalid format (x.xx)."),
				new RequiredFormElement("assignment", "Assignment required.")
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
				TeacherAllocationPermanentPositionBean position = null;

				if (!form.exists("positionid")) {
					position = new TeacherAllocationPermanentPositionBean();

					position.setAllocationId(form.getInt("allocationid"));
					position.setEmployee(EmployeeManager.getEmployeeBean(form.get("empid").trim()));
					if (!StringUtils.isEmpty(form.get("csize")))
						position.setClassSize(form.getInt("csize"));
					else
						position.setClassSize(0);
					position.setAssignment(form.get("assignment"));
					position.setUnit(form.getDouble("unit"));
					position.setTenur(form.get("tenur"));

					TeacherAllocationPermanentPositionManager.addTeacherAllocationPermanentPositionBean(position);

					msg = "Permanent position added successfully.";
				}
				else {
					position = TeacherAllocationPermanentPositionManager.getTeacherAllocationPermanentPositionBean(form.getInt("positionid"));

					position.setEmployee(EmployeeManager.getEmployeeBean(form.get("empid").trim()));
					if (!StringUtils.isEmpty(form.get("csize")))
						position.setClassSize(form.getInt("csize"));
					else
						position.setClassSize(0);
					position.setAssignment(form.get("assignment"));
					position.setUnit(form.getDouble("unit"));
					position.setTenur(form.get("tenur"));

					TeacherAllocationPermanentPositionManager.updateTeacherAllocationPermanentPositionBean(position);

					msg = "Permanent position updated successfully.";
				}

				TeacherAllocationBean allocation = TeacherAllocationManager.getTeacherAllocationBean(form.getInt("allocationid"));

				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<ADD-TEACHER-ALLOCATION-PERMANENT-POSITION-RESPONSE msg='" + msg + "'>");
				if (allocation != null)
					sb.append(allocation.toXML());
				sb.append("</ADD-TEACHER-ALLOCATION-PERMANENT-POSITION-RESPONSE>");

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

				sb.append("<ADD-TEACHER-ALLOCATION-PERMANENT-POSITION-RESPONSE msg='Permanent position could not be added/updated.<br />"
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

			sb.append("<ADD-TEACHER-ALLOCATION-PERMANENT-POSITION-RESPONSE msg='" + this.validator.getErrorString() + "' />");

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
