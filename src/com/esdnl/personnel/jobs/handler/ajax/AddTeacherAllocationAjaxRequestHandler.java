package com.esdnl.personnel.jobs.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.TeacherAllocationBean;
import com.esdnl.personnel.jobs.dao.TeacherAllocationManager;
import com.esdnl.personnel.v2.database.sds.LocationManager;
import com.esdnl.personnel.v2.model.sds.bean.LocationBean;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormElementPattern;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.servlet.RequiredPatternFormElement;
import com.esdnl.util.StringUtils;

public class AddTeacherAllocationAjaxRequestHandler extends RequestHandlerImpl {

	public AddTeacherAllocationAjaxRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("schoolyear", "School year selection required."),
				new RequiredFormElement("locationid", "School selection required."),
				new RequiredFormElement("runits", "Regular units required."),
				new RequiredPatternFormElement("runits", FormElementPattern.TWO_DECIMALS_PATTERN, "Regular units invalid format (x.xx)."),
				new RequiredFormElement("aunits", "Administrative units required."),
				new RequiredPatternFormElement("aunits", FormElementPattern.TWO_DECIMALS_PATTERN, "Administrative units invalid format (x.xx)."),
				new RequiredFormElement("sunits", "Specialist units required."),
				new RequiredPatternFormElement("sunits", FormElementPattern.TWO_DECIMALS_PATTERN, "Specialist units invalid format (x.xx)."),
				new RequiredFormElement("gunits", "Guidance units required."),
				new RequiredPatternFormElement("gunits", FormElementPattern.TWO_DECIMALS_PATTERN, "Guidance units invalid format (x.xx)."),
				new RequiredFormElement("lrtunits", "LRT units required."),
				new RequiredPatternFormElement("lrtunits", FormElementPattern.TWO_DECIMALS_PATTERN, "LRT units invalid format (x.xx)."),
				new RequiredFormElement("irt1units", "IRT 1 units required."),
				new RequiredPatternFormElement("irt1units", FormElementPattern.TWO_DECIMALS_PATTERN, "IRT 1 units invalid format (x.xx)."),
				new RequiredFormElement("irt2units", "IRT 2 units required."),
				new RequiredPatternFormElement("irt2units", FormElementPattern.TWO_DECIMALS_PATTERN, "IRT 2 units invalid format (x.xx)."),
				new RequiredFormElement("ounits", "Other units required."),
				new RequiredPatternFormElement("ounits", FormElementPattern.TWO_DECIMALS_PATTERN, "Other units invalid format (x.xx)."),
				new RequiredFormElement("tlaunits", "TLA units required."),
				new RequiredPatternFormElement("tlaunits", FormElementPattern.TWO_DECIMALS_PATTERN, "TLA units invalid format (x.xx)."),
				new RequiredFormElement("sahours", "Student Assistant hours required."),
				new RequiredPatternFormElement("sahours", FormElementPattern.TWO_DECIMALS_PATTERN, "Student Assistant hours invalid format (x.xx)."),
				new RequiredFormElement("rsunits", "Reading Specialist units required."),
				new RequiredPatternFormElement("rsunits", FormElementPattern.TWO_DECIMALS_PATTERN, "Reading Specialist hours invalid format (x.xx).")
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
				TeacherAllocationBean allocation = null;
				LocationBean location = LocationManager.getLocationBeanByDescription(form.get("locationid"));

				if (!form.exists("allocationid")) {
					allocation = new TeacherAllocationBean();

					allocation.setLocation(location);
					allocation.setSchoolYear(form.get("schoolyear"));
					allocation.setRegularUnits(form.getDouble("runits"));
					allocation.setAdministrativeUnits(form.getDouble("aunits"));
					allocation.setGuidanceUnits(form.getDouble("gunits"));
					allocation.setSpecialistUnits(form.getDouble("sunits"));
					allocation.setLRTUnits(form.getDouble("lrtunits"));
					allocation.setIRT1Units(form.getDouble("irt1units"));
					allocation.setIRT2Units(form.getDouble("irt2units"));
					allocation.setOtherUnits(form.getDouble("ounits"));
					allocation.setTLAUnits(form.getDouble("tlaunits"));
					allocation.setStudentAssistantHours(form.getDouble("sahours"));
					allocation.setReadingSpecialistUnits(form.getDouble("rsunits"));

					TeacherAllocationManager.addTeacherAllocationBean(allocation);

					msg = "Allocation added successfully.";
				}
				else {
					allocation = TeacherAllocationManager.getTeacherAllocationBean(form.getInt("allocationid"));

					allocation.setRegularUnits(form.getDouble("runits"));
					allocation.setAdministrativeUnits(form.getDouble("aunits"));
					allocation.setGuidanceUnits(form.getDouble("gunits"));
					allocation.setSpecialistUnits(form.getDouble("sunits"));
					allocation.setLRTUnits(form.getDouble("lrtunits"));
					allocation.setIRT1Units(form.getDouble("irt1units"));
					allocation.setIRT2Units(form.getDouble("irt2units"));
					allocation.setOtherUnits(form.getDouble("ounits"));
					allocation.setTLAUnits(form.getDouble("tlaunits"));
					allocation.setStudentAssistantHours(form.getDouble("sahours"));
					allocation.setReadingSpecialistUnits(form.getDouble("rsunits"));

					TeacherAllocationManager.updateTeacherAllocationBean(allocation);

					msg = "Allocation updated successfully.";
				}

				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<ADD-TEACHER-ALLOCATION-RESPONSE msg='" + msg + "'>");
				if (allocation != null)
					sb.append(allocation.toXML());
				sb.append("</ADD-TEACHER-ALLOCATION-RESPONSE>");

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

				sb.append("<ADD-TEACHER-ALLOCATION-RESPONSE msg='Allocation could not be added/updated.<br />"
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

			sb.append("<ADD-TEACHER-ALLOCATION-RESPONSE msg='" + this.validator.getErrorString() + "' />");

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
