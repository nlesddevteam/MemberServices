package com.esdnl.personnel.jobs.handler;

import com.esdnl.util.*;
import com.esdnl.personnel.jobs.bean.*;
import com.esdnl.personnel.jobs.dao.*;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.PersonnelApplicationRequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import java.io.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AddApplicantEducationRequestHandler  extends PersonnelApplicationRequestHandlerImpl {
	public AddApplicantEducationRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("institution", "Please specify name of institution"),
				new RequiredFormElement("from_date", "Please specify from date"),
				new RequiredFormElement("to_date","Please specify to date"),
				new RequiredFormElement("program","Please specify program/faculty"),
				new RequiredFormElement("major","Please specify major"),
				new RequiredFormElement("major_courses","Please specify number major courses"),
				new RequiredFormElement("minor","Please specify minor"),
				new RequiredFormElement("minor_courses","Please specify number minor courses")
		});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		String path;

		SimpleDateFormat sdf = null;
		ApplicantProfileBean profile = null;
		if (validate_form()) {
			try
			{

				String institution = request.getParameter("institution");
				String from = request.getParameter("from_date");
				String to = request.getParameter("to_date");
				String program = request.getParameter("program");
				String major_courses = request.getParameter("major_courses");
				String minor = request.getParameter("minor");
				String minor_courses = request.getParameter("minor_courses");
				String degree = request.getParameter("degree");
				profile = (ApplicantProfileBean) request.getSession().getAttribute("APPLICANT");
				sdf = new SimpleDateFormat("MM/yyyy");
				ApplicantEducationBean abean = new ApplicantEducationBean();
				Integer[] majors = form.getIntegerArray("major");
				if(majors.length == 2) {
					//double major
					abean.setMajor(majors[0]);
					abean.setMajor_other(majors[1]);
				}else {
					//single major
					abean.setMajor(majors[0]);
				}
				abean.setSIN(profile.getSIN());
				abean.setInstitutionName(institution);
				abean.setFrom(sdf.parse(from));
				abean.setTo(sdf.parse(to));
				abean.setProgramFacultyName(program);
				abean.setNumberMajorCourses(Integer.parseInt(major_courses));
				abean.setMinor(Integer.parseInt(minor));
				abean.setNumberMinorCourses(Integer.parseInt(minor_courses));

				if(!StringUtils.isEmpty(degree) && !degree.equals("0"))
					abean.setDegreeConferred(degree);

				ApplicantEducationManager.addApplicantEducationBean(abean);

				request.setAttribute("msg", "Education successfully added.");
				path = "applicant_registration_step_5.jsp";
			}


			catch(JobOpportunityException e)
			{
				e.printStackTrace();
				request.setAttribute("errmsg", "Could not add applicant education.");
				path = "applicant_registration_step_5.jsp";
			}
			catch(ParseException e)
			{
				e.printStackTrace();
				request.setAttribute("errmsg", "Invalid date format.");
				path = "applicant_registration_step_5.jsp";
			}
		}else {
			request.setAttribute("errmsg", this.validator.getErrorString());
			path = "applicant_registration_step_5.jsp";
		}
		return path;
	}
}