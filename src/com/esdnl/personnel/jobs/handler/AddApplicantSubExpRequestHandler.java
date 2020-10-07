package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.ApplicantSubstituteTeachingExpBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.ApplicantSubExpManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.PersonnelApplicationRequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
public class AddApplicantSubExpRequestHandler extends PersonnelApplicationRequestHandlerImpl {
	public AddApplicantSubExpRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("from_date", "Please specify from date"),
				new RequiredFormElement("to_date", "Please specify to date"),
				new RequiredFormElement("grds_subs", "Please specify approximate number of days per year")
		});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		String path;
		SimpleDateFormat sdf = null;
		ApplicantProfileBean profile = null;
		if (validate_form()) {
			try {
				String from = request.getParameter("from_date");
				String to = request.getParameter("to_date");
				String days = request.getParameter("grds_subs");

				profile = (ApplicantProfileBean) request.getSession(false).getAttribute("APPLICANT");
				
					sdf = new SimpleDateFormat("MM/yyyy");

					ApplicantSubstituteTeachingExpBean abean = new ApplicantSubstituteTeachingExpBean();

					abean.setSIN(profile.getSIN());
					abean.setFrom(sdf.parse(from));
					abean.setTo(sdf.parse(to));
					abean.setSchoolBoard(null);
					abean.setNumDays(Integer.parseInt(days));

					ApplicantSubExpManager.addApplicantSubstituteTeachingExpBean(abean);

					request.setAttribute("msg", "Substitute teaching experience successfully added.");
					path = "applicant_registration_step_4.jsp";
				

			}
			catch (JobOpportunityException e) {
				e.printStackTrace();
				request.setAttribute("errmsg", "Could not add applicant profile ESD general experience.");
				path = "applicant_registration_step_4.jsp";
			}
			catch (ParseException e) {
				e.printStackTrace();
				request.setAttribute("errmsg", "Invalid date format.");
				path = "applicant_registration_step_4.jsp";
			}
		}else {
			request.setAttribute("errmsg", this.validator.getErrorString());
			path = "applicant_registration_step_4.jsp";
		}


		return path;
	}
}