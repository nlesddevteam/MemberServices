package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.ApplicantCriminalOffenceDeclarationBean;
import com.esdnl.personnel.jobs.bean.ApplicantCriminalOffenceDeclarationOffenceBean;
import com.esdnl.personnel.jobs.dao.ApplicantCriminalOffenceDeclarationManager;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.PersonnelApplicationRequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class AddApplicantCriminalOffenceDeclarationRequestHandler extends PersonnelApplicationRequestHandlerImpl {

	public AddApplicantCriminalOffenceDeclarationRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("txt_position", "Current position is required."),
				new RequiredFormElement("txt_location", "Current school or location is required."),
				new RequiredFormElement("num_offences_listed")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			try {

				if (form.exists("txt_dob")) {
					profile.setDOB(form.getDate("txt_dob"));

					ApplicantProfileManager.updateApplicantProfileBean(profile);
				}

				ApplicantCriminalOffenceDeclarationBean cod = new ApplicantCriminalOffenceDeclarationBean();

				cod.setApplicant(profile);
				cod.setDeclarationDate(new Date());
				cod.setPosition(form.get("txt_position"));
				cod.setLocation(form.get("txt_location"));

				ApplicantCriminalOffenceDeclarationOffenceBean codo = null;
				for (int i = 1; i <= form.getInt("num_offences_listed"); i++) {

					if (form.exists("txt_offence_date_" + i) && form.exists("txt_court_location_" + i)
							&& form.exists("txt_conviction_" + i)) {
						codo = new ApplicantCriminalOffenceDeclarationOffenceBean();

						codo.setOffenceDate(form.getDate("txt_offence_date_" + i));
						codo.setCourtLocation(form.get("txt_court_location_" + i));
						codo.setConviction(form.get("txt_conviction_" + i));

						cod.addOffence(codo);
					}
				}

				ApplicantCriminalOffenceDeclarationManager.addApplicantCriminalOffenceDeclarationBean(cod);

				request.setAttribute("msg", "Criminal Offence Declaration added successfully.");

			}
			catch (Exception e) {
				e.printStackTrace(System.err);
				request.setAttribute("errmsg", e.getMessage());
			}
		}
		else
			request.setAttribute("errmsg", this.validator.getErrorString());

		if(profile.getProfileType().equals("S")) {
			path = "applicant_registration_step_9_ss.jsp";
		}else {
			path = "applicant_registration_step_10.jsp";
		}
		

		return path;
	}

}
