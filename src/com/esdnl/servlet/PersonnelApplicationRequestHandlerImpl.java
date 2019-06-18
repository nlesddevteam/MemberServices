package com.esdnl.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;

public class PersonnelApplicationRequestHandlerImpl extends BypassLoginRequestHandlerImpl {

	protected ApplicantProfileBean profile;

	public PersonnelApplicationRequestHandlerImpl() {

		super();
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {

		boolean validated = false;
		form = new Form(request);

		session = request.getSession(false);
		if ((session != null) && (session.getAttribute("APPLICANT") != null))
			validated = true;

		if (!validated)
			request.getRequestDispatcher("/MemberServices/Personnel/applicant_login.jsp").forward(request, reponse);
		else {
			ROOT_DIR = new File(session.getServletContext().getRealPath("/"));

			profile = (ApplicantProfileBean) session.getAttribute("APPLICANT");
		}
		return null;
	}
}
