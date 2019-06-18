package com.esdnl.personnel.jobs.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.TeacherAllocationBean;
import com.esdnl.personnel.jobs.bean.TeacherAllocationExtraBean;
import com.esdnl.personnel.jobs.dao.TeacherAllocationExtraManager;
import com.esdnl.personnel.jobs.dao.TeacherAllocationManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormElementPattern;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.servlet.RequiredPatternFormElement;
import com.esdnl.util.StringUtils;

public class AddUpdateTeacherAllocationExtraAjaxRequestHandler extends RequestHandlerImpl {

	public AddUpdateTeacherAllocationExtraAjaxRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("allocationid", "Allocation ID missing."),
				new RequiredFormElement("eunits", "Extra units required."),
				new RequiredPatternFormElement("eunits", FormElementPattern.TWO_DECIMALS_PATTERN, "Extra units invalid format (x.xx)."),
				new RequiredFormElement("rationale", "Rationale required."),
				new RequiredFormElement("atype", "Allocation type required.")
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
				TeacherAllocationExtraBean extra = null;

				if (!form.exists("eid")) {
					extra = new TeacherAllocationExtraBean();

					extra.setAllocationId(form.getInt("allocationid"));
					extra.setExtraAllocationUnits(form.getDouble("eunits"));
					extra.setRationale(form.get("rationale"));
					extra.setAllocationType(TeacherAllocationExtraBean.AllocationType.get(form.getInt("atype")));

					TeacherAllocationExtraManager.addTeacherAllocationExtraBean(extra);

					msg = "Allocation adjustment added successfully.";
				}
				else {
					extra = TeacherAllocationExtraManager.getTeacherAllocationExtraBean(form.getInt("eid"));

					extra.setExtraAllocationUnits(form.getDouble("eunits"));
					extra.setRationale(form.get("rationale"));
					extra.setAllocationType(TeacherAllocationExtraBean.AllocationType.get(form.getInt("atype")));

					TeacherAllocationExtraManager.updateTeacherAllocationExtraBean(extra);

					msg = "Allocation adjustment updated successfully.";
				}

				TeacherAllocationBean allocation = TeacherAllocationManager.getTeacherAllocationBean(
						form.getInt("allocationid"));

				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<ADD-TEACHER-ALLOCATION-EXTRA-RESPONSE msg='" + msg + "'>");
				if (allocation != null)
					sb.append(allocation.toXML());
				sb.append("</ADD-TEACHER-ALLOCATION-EXTRA-RESPONSE>");

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

				sb.append("<ADD-TEACHER-ALLOCATION-EXTRA-RESPONSE msg='Allocation could not be added/updated.<br />"
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

			sb.append("<ADD-TEACHER-ALLOCATION-EXTRA-RESPONSE msg='" + this.validator.getErrorString() + "' />");

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
