package com.esdnl.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.servlet.PersonnelApplicationRequestHandler;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;

public class PersonnelApplicationRequestHandlerImpl extends RequestHandlerImpl
		implements PersonnelApplicationRequestHandler {

	protected ApplicantProfileBean profile;

	public PersonnelApplicationRequestHandlerImpl() {

		super();
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		boolean validated = false;
		form = new Form(request);

		session = request.getSession(false);
		if ((session != null) && (session.getAttribute("APPLICANT") != null)) {
			validated = true;
		}

		if (validated) {
			ROOT_DIR = new File(session.getServletContext().getRealPath("/"));

			profile = (ApplicantProfileBean) session.getAttribute("APPLICANT");
		}

		return null;
	}
}
