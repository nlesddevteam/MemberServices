package com.esdnl.personnel.jobs.handler;
import com.esdnl.personnel.jobs.bean.*;
import com.esdnl.personnel.jobs.dao.*;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.PersonnelApplicationRequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class AddApplicantEducationSecSSRequestHandler extends PersonnelApplicationRequestHandlerImpl
{
	public AddApplicantEducationSecSSRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("educationlevel"),
				new RequiredFormElement("schoolname"),
				new RequiredFormElement("schoolcity"),
				new RequiredFormElement("state_province"),
				new RequiredFormElement("graduated"),
				new RequiredFormElement("graduated"),
		});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		String path;
		ApplicantProfileBean profile = null;
		if (validate_form()) {
			try
			{

				String educationlevel = form.get("educationlevel");
				String schoolname = form.get("schoolname");
				String schoolcity = form.get("schoolcity");
				String schoolprovince = form.get("state_province");
				String yearscompleted = "";
				String graduated = form.get("graduated");
				String yearGraduated = form.get("yeargraduated");
				profile = (ApplicantProfileBean) request.getSession().getAttribute("APPLICANT");

				if(profile == null)
				{
					path = "applicant_registration_step_1_ss.jsp";
				}
				else
				{
					path = "applicant_registration_step_5_ss.jsp"; // default path

					ApplicantEducationSecSSBean exp = new ApplicantEducationSecSSBean();
					exp.setSin(profile.getSIN());
					exp.setEducationLevel(educationlevel);
					exp.setSchoolName(schoolname);
					exp.setSchoolCity(schoolcity);
					exp.setSchoolProvince(schoolprovince);
					exp.setYearsCompleted(yearscompleted);
					exp.setGraduated(graduated);
					exp.setYearGraduated(yearGraduated);
					if(request.getParameter("op") == null){
						ApplicantEducationSecSSManager.addApplicantEducationSecSSBean(exp);
						request.setAttribute("msg", "Education added successfully.");
					}else{
						exp.setId(Integer.parseInt(request.getParameter("id")));
						ApplicantEducationSecSSManager.updateApplicantEducationSecSSBean(exp);
						request.setAttribute("msg", "Education updated successfully.");
					}

					request.getSession(true).setAttribute("APPLICANT", profile);
					path = "applicant_registration_step_5_ss.jsp";
				}
			}catch(Exception e)
			{
				e.printStackTrace();
				request.setAttribute("errmsg", "Could not add education experience.");
				path = "applicant_registration_step_5_ss.jsp";
			}
		}else {
			request.setAttribute("errmsg", this.validator.getErrorString());
			path = "applicant_registration_step_5_ss.jsp";
		}
		return path;
	}
}