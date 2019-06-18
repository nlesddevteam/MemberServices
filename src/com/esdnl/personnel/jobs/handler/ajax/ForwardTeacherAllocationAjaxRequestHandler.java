package com.esdnl.personnel.jobs.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.dao.TeacherAllocationManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class ForwardTeacherAllocationAjaxRequestHandler extends RequestHandlerImpl {

	public ForwardTeacherAllocationAjaxRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("schoolyear", "School year is required."),
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			try {

				String sy = form.get("schoolyear");

				String[] syp = sy.split("-");

				syp[0] = Integer.toString(Integer.parseInt(syp[0]) + 1);
				syp[1] = Integer.toString(Integer.parseInt(syp[1]) + 1);

				TeacherAllocationManager.forwardTeacherAllocationBeanStatus(sy,
						org.apache.commons.lang.StringUtils.join(syp, "-"));

				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<FORWARD-TEACHER-ALLOCATION-RESPONSE msg='forwarded' />");

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

				sb.append("<FORWARD-TEACHER-ALLOCATION-RESPONSE msg='Allocations could not be forwarded.<br />"
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

			sb.append("<FORWARD-TEACHER-ALLOCATION-RESPONSE msg='" + this.validator.getErrorString() + "' />");

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
