package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.servlet.LoginNotRequiredRequestHandler;
import com.awsd.servlet.RequestHandler;
import com.esdnl.util.StringUtils;

public class ApplicantRegistrationControllerRequestHandler implements LoginNotRequiredRequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		String path;

		RequestHandler rh = null;

		try {
			String step = request.getParameter("step");

			if (StringUtils.isEmpty(step)) {
				path = "applicant_registration_step_1.jsp";
			}
			else if (step.equalsIgnoreCase("1")) {
				rh = new AddApplicantProfileRequestHandler();
				path = rh.handleRequest(request, response);
			}
			else if (step.equalsIgnoreCase("2")) {
				rh = new AddApplicantESDGeneralExperienceRequestHandler();
				path = rh.handleRequest(request, response);
			}
			else if (step.equalsIgnoreCase("2A")) {
				System.err.println(step);
				if (request.getParameter("del") == null)
					rh = new AddApplicantNLESDPermExperienceRequestHandler();
				else
					rh = new DeleteApplicantNLESDPermExperienceRequestHandler();
				path = rh.handleRequest(request, response);
			}
			else if (step.equalsIgnoreCase("2B")) {
				System.err.println(step);
				if (request.getParameter("del") == null)
					rh = new AddApplicantESDReplExperienceRequestHandler();
				else
					rh = new DeleteApplicantESDReplExperienceRequestHandler();
				path = rh.handleRequest(request, response);
			}
			else if (step.equalsIgnoreCase("3")) {
				if (request.getParameter("del") == null)
					rh = new AddApplicantExperienceOtherRequestHandler();
				else
					rh = new DeleteApplicantExperienceOtherRequestHandler();
				path = rh.handleRequest(request, response);
			}
			else if (step.equalsIgnoreCase("4")) {
				if (request.getParameter("del") == null)
					rh = new AddApplicantSubExpRequestHandler();
				else
					rh = new DeleteApplicantSubExpRequestHandler();
				path = rh.handleRequest(request, response);
			}
			else if (step.equalsIgnoreCase("5")) {
				if (request.getParameter("del") == null)
					rh = new AddApplicantEducationRequestHandler();
				else
					rh = new DeleteApplicantEducationRequestHandler();

				path = rh.handleRequest(request, response);
			}
			else if (step.equalsIgnoreCase("6")) {
				rh = new AddApplicantEducationOtherRequestHandler();
				path = rh.handleRequest(request, response);
			}
			else if (step.equalsIgnoreCase("7")) {
				rh = new AddApplicantOtherInformationRequestHandler();
				path = rh.handleRequest(request, response);
			}
			else if (step.equalsIgnoreCase("8")) {
				if (request.getParameter("del") == null)
					rh = new AddApplicantReferenceRequestHandler();
				else
					rh = new DeleteApplicantReferenceRequestHandler();
				path = rh.handleRequest(request, response);
			}
			else if (step.equalsIgnoreCase("9")) {
				rh = new AddApplicantRegionalPreferencesRequestHandler();
				path = rh.handleRequest(request, response);
			}
			else if (step.equalsIgnoreCase("10")) {
				rh = new AddApplicantDocumentRequestHandler();
				path = rh.handleRequest(request, response);
			}
			else if (step.equalsIgnoreCase("10COD")) {
				rh = new AddApplicantCriminalOffenceDeclarationRequestHandler();
				path = rh.handleRequest(request, response);
			}
			else {
				path = "applicant_registration_step_1.jsp";
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "Error registering applicant.");
			path = "applicant_registration_step_1.jsp";
		}

		return path;
	}
}