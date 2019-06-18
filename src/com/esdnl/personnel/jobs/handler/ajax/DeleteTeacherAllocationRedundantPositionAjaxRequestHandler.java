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
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class DeleteTeacherAllocationRedundantPositionAjaxRequestHandler extends RequestHandlerImpl {

	public DeleteTeacherAllocationRedundantPositionAjaxRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("id", "Position ID missing.")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			try {

				TeacherAllocationBean allocation = null;

				TeacherAllocationRedundantPositionBean position = TeacherAllocationRedundantPositionManager.getTeacherAllocationRedundantPositionBean(form.getInt("id"));

				if (position != null) {
					TeacherAllocationRedundantPositionManager.deleteTeacherAllocationRedundantPositionBean(position);

					allocation = TeacherAllocationManager.getTeacherAllocationBean(position.getAllocationId());
				}

				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<DEL-TEACHER-ALLOCATION-REDUNDANT-POSITION-RESPONSE msg='Redundant position deleted successfully.'>");
				if (allocation != null)
					sb.append(allocation.toXML());
				sb.append("</DEL-TEACHER-ALLOCATION-REDUNDANT-POSITION-RESPONSE>");

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

				sb.append("<DEL-TEACHER-ALLOCATION-REDUNDANT-POSITION-RESPONSE msg='Redundant position could not be deleted.<br />"
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

			sb.append("<DEL-TEACHER-ALLOCATION-REDUNDANT-POSITION-RESPONSE msg='" + this.validator.getErrorString() + "' />");

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
