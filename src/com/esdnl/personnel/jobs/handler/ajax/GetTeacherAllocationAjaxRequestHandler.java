package com.esdnl.personnel.jobs.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.TeacherAllocationBean;
import com.esdnl.personnel.jobs.dao.TeacherAllocationManager;
import com.esdnl.personnel.v2.database.sds.LocationManager;
import com.esdnl.personnel.v2.model.sds.bean.LocationBean;
import com.esdnl.personnel.v2.model.sds.bean.LocationException;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class GetTeacherAllocationAjaxRequestHandler extends RequestHandlerImpl {

	public GetTeacherAllocationAjaxRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("locationid"), new RequiredFormElement("schoolyear")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			LocationBean location = LocationManager.getLocationBeanByDescription(form.get("locationid"));
			TeacherAllocationBean allocation = TeacherAllocationManager.getTeacherAllocationBean(form.get("schoolyear"),
					location.getLocationId());

			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<GET-TEACHER-ALLOCATION-RESPONSE>");
			if (allocation != null)
				sb.append(allocation.toXML());
			sb.append("</GET-TEACHER-ALLOCATION-RESPONSE>");

			xml = StringUtils.encodeXML(sb.toString());

			System.out.println(xml);

			PrintWriter out = response.getWriter();

			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
		}
		catch (JobOpportunityException e) {
			e.printStackTrace(System.err);
		}
		catch (LocationException e) {
			e.printStackTrace(System.err);
		}

		return null;
	}

}
