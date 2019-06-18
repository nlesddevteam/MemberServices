package com.esdnl.personnel.jobs.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.TeacherAllocationBean;
import com.esdnl.personnel.jobs.bean.TeacherAllocationVacantPositionBean;
import com.esdnl.personnel.jobs.constants.EmploymentConstant;
import com.esdnl.personnel.jobs.dao.TeacherAllocationManager;
import com.esdnl.personnel.jobs.dao.TeacherAllocationVacantPositionManager;
import com.esdnl.personnel.v2.database.sds.EmployeeManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormElementPattern;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.servlet.RequiredPatternFormElement;
import com.esdnl.servlet.RequiredSelectionFormElement;
import com.esdnl.util.StringUtils;

public class AddUpdateTeacherAllocationVacantPositionAjaxRequestHandler extends RequestHandlerImpl {

	public AddUpdateTeacherAllocationVacantPositionAjaxRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("allocationid", "Allocation ID missing."),
				new RequiredFormElement("jobdesc", "Job description required."),
				new RequiredSelectionFormElement("jobtype", -1, "Type selection required."),
				new RequiredFormElement("reason", "Vacancy reason required."),
				new RequiredFormElement("unit", "Unit required."),
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
				TeacherAllocationVacantPositionBean position = null;

				if (!form.exists("positionid")) {
					position = new TeacherAllocationVacantPositionBean();

					position.setAllocationId(form.getInt("allocationid"));
					position.setJobDescription(form.get("jobdesc"));
					position.setType(EmploymentConstant.get(form.getInt("jobtype")));

					if (!form.hasValue("empid", "0"))
						position.setEmployee(EmployeeManager.getEmployeeBean(form.get("empid").trim()));
					else
						position.setEmployee(null);

					position.setVacancyReason(form.get("reason"));

					if (form.exists("termstart"))
						position.setTermStart(form.getDate("termstart"));
					else
						position.setTermStart(null);

					if (form.exists("termend"))
						position.setTermEnd(form.getDate("termend"));
					else
						position.setTermEnd(null);

					position.setUnit(form.getDouble("unit"));
					position.setAdvertised(form.getBoolean("advertised"));
					position.setFilled(form.getBoolean("filled"));

					TeacherAllocationVacantPositionManager.addTeacherAllocationVacantPositionBean(position);
					//now we add the job ad
					/*
					TeacherAllocationBean tab = TeacherAllocationManager.getTeacherAllocationBean(form.getInt("allocationid"));
					AdRequestBean adbean = new AdRequestBean();
					adbean.setTitle(tab.getLocation().getLocationDescription() + ": " +position.getJobDescription());
					adbean.setLocation(tab.getLocation());
					adbean.setVacancyReason(position.getVacancyReason());
					adbean.setStartDate(position.getTermStart());
					adbean.setEndDate(position.getTermEnd());
					adbean.setUnits(position.getUnit());
					adbean.setAdText(position.getJobDescription());
					adbean.setOwner(position.getEmployee());
					adbean.setJobType(JobTypeConstant.REGULAR);
					adbean.setTrainingMethod(TrainingMethodConstant.PRIMARY_ELEMENTARY);
					AdRequestManager.addAdRequestBean(adbean,usr.getPersonnel());
					*/
					//now we update the position with the ad request id
					//TeacherAllocationVacantPositionManager.updateTeacherAllocationVacantPositionAdRequest(adbean.getId(), position.getPositionId());
					//send email about new ad request

					msg = "Vacant position added successfully.";
				}
				else {
					position = TeacherAllocationVacantPositionManager.getTeacherAllocationVacantPositionBean(form.getInt("positionid"));

					position.setJobDescription(form.get("jobdesc"));
					position.setType(EmploymentConstant.get(form.getInt("jobtype")));

					if (!form.hasValue("empid", "0"))
						position.setEmployee(EmployeeManager.getEmployeeBean(form.get("empid").trim()));
					else
						position.setEmployee(null);

					position.setVacancyReason(form.get("reason"));

					if (form.exists("termstart"))
						position.setTermStart(form.getDate("termstart"));
					else
						position.setTermStart(null);

					if (form.exists("termend"))
						position.setTermEnd(form.getDate("termend"));
					else
						position.setTermEnd(null);

					position.setUnit(form.getDouble("unit"));
					position.setAdvertised(form.getBoolean("advertised"));
					position.setFilled(form.getBoolean("filled"));

					TeacherAllocationVacantPositionManager.updateTeacherAllocationVacantPositionBean(position);
					
					
					msg = "Vacant position updated successfully.";
				}

				TeacherAllocationBean allocation = TeacherAllocationManager.getTeacherAllocationBean(form.getInt("allocationid"));

				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<ADD-TEACHER-ALLOCATION-VACANT-POSITION-RESPONSE msg='" + msg + "'>");
				if (allocation != null)
					sb.append(allocation.toXML());
				sb.append("</ADD-TEACHER-ALLOCATION-VACANT-POSITION-RESPONSE>");

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

				sb.append("<ADD-TEACHER-ALLOCATION-VACANT-POSITION-RESPONSE msg='Permanent position could not be added/updated.<br />"
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

			sb.append("<ADD-TEACHER-ALLOCATION-VACANT-POSITION-RESPONSE msg='" + this.validator.getErrorString() + "' />");

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
