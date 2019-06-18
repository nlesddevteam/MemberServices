package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.TeacherRecommendationBean;
import com.esdnl.personnel.jobs.dao.AdRequestManager;
import com.esdnl.personnel.jobs.dao.RecommendationManager;
import com.esdnl.personnel.jobs.dao.RequestToHireManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.PersonnelApplicationRequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class ViewApplicantEmploymentLetterRequestHandler extends PersonnelApplicationRequestHandlerImpl {

	public ViewApplicantEmploymentLetterRequestHandler() {

		validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("id")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			if (validate_form()) {

				TeacherRecommendationBean rec = RecommendationManager.getTeacherRecommendationBean(form.getInt("id"));

				// check the correct applicant is looking at this position offer, if not redirect to login page

				if ((rec == null) || !profile.getSIN().equalsIgnoreCase(rec.getCandidateId()))
					response.sendRedirect("/MemberServices/Personnel/applicant_login.jsp");
				else {
					if (validate_form()) {
						JobOpportunityBean job = rec.getJob();
						if(job.getIsSupport().equals("Y")){
							//we add the request to hire
							request.setAttribute("rbean", RequestToHireManager.getRequestToHireByCompNum(job.getCompetitionNumber()));
							//set the correct path
							path = "view_applicant_employment_letter_ss.jsp";
						}else{
							//we add the ad request
							request.setAttribute("adreq", AdRequestManager.getAdRequestBean(job.getCompetitionNumber()));
							//set the correct path
							path = "view_applicant_employment_letter.jsp";
						}
						request.setAttribute("rec", rec);
						request.setAttribute("candidate", rec.getCandidate());
						request.setAttribute("jbean", job);
						

						
					}
					else
						response.sendRedirect("/MemberServices/Personnel/applicant_login.jsp");
				}
			}
		}
		catch (JobOpportunityException e) {
			e.printStackTrace();
			response.sendRedirect("/MemberServices/Personnel/applicant_login.jsp");
		}

		return path;
	}
}