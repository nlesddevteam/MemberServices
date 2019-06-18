package com.esdnl.personnel.jobs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.servlet.LoginNotRequiredRequestHandler;
import com.awsd.servlet.RequestHandler;
import com.esdnl.personnel.jobs.handler.AddApplicantEducationOtherSSRequestHandler;
import com.esdnl.util.StringUtils;
public class ApplicantRegistrationControllerSSRequestHandler implements LoginNotRequiredRequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		String path;

		RequestHandler rh = null;

		try {
			String step = request.getParameter("step");

			if (StringUtils.isEmpty(step)) {
				path = "applicant_registration_step_1_ss.jsp";
			}else if (step.equalsIgnoreCase("1")) {
				rh = new AddApplicantProfileSSRequestHandler();
				path = rh.handleRequest(request, response);
			}else if (step.equalsIgnoreCase("2")) {
				if (request.getParameter("del") == null)
					rh = new AddApplicantNLESDExperienceSSRequestHandler();
				else
					rh = new DeleteApplicantNLESDExperienceSSRequestHandler();
				
				path = rh.handleRequest(request, response);
			}else if (step.equalsIgnoreCase("3")) {
				if (request.getParameter("del") == null)
					rh = new AddApplicantEmploymentRequestHandler();
				else
					rh = new DeleteApplicantEmploymentSSRequestHandler();

				path = rh.handleRequest(request, response);
			}else if (step.equalsIgnoreCase("5a")) {
				rh = new AddApplicantEducationSecSSRequestHandler();
				path = rh.handleRequest(request, response);
			}else if (step.equalsIgnoreCase("5b")) {
				if (request.getParameter("del") == null)
					rh = new AddApplicantEducationPostSSRequestHandler();
				else
					rh = new DeleteApplicantEducationPostSSRequestHandler();

				path = rh.handleRequest(request, response);
			}else if (step.equalsIgnoreCase("6")) {
				rh = new AddApplicantEducationOtherSSRequestHandler();
				path = rh.handleRequest(request, response);
			}
			/**
			else if (step.equalsIgnoreCase("7")) {
				rh = new AddApplicantRegionalJobPoolSSRequestHandler();
				path = rh.handleRequest(request, response);
			}
			**/
			else if (step.equalsIgnoreCase("7")) {
				if (request.getParameter("del") == null)
					rh = new AddApplicantReferenceSSRequestHandler();
				else
					rh = new DeleteApplicantReferenceSSRequestHandler();
				path = rh.handleRequest(request, response);
			}else if (step.equalsIgnoreCase("8")) {
				rh = new AddApplicantDocumentSSRequestHandler();
				path = rh.handleRequest(request, response);
			}else {
				path = "applicant_registration_step_1_ss.jsp";
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "Error registering applicant.");
			path = "applicant_registration_step_1_ss.jsp";
		}

		return path;
	}
}
