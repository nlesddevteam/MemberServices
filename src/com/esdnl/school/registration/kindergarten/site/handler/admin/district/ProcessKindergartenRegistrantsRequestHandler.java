package com.esdnl.school.registration.kindergarten.site.handler.admin.district;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.bean.EmailException;
import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.esdnl.school.registration.bean.SchoolRegistrationException;
import com.esdnl.school.registration.kindergarten.bean.KindergartenRegistrantBean;
import com.esdnl.school.registration.kindergarten.bean.KindergartenRegistrantBean.REGISTRANT_STATUS;
import com.esdnl.school.registration.kindergarten.bean.KindergartenRegistrantBean.SCHOOLSTREAM;
import com.esdnl.school.registration.kindergarten.bean.SchoolRegistrantCapBean;
import com.esdnl.school.registration.kindergarten.service.KindergartenRegistrationManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.velocity.VelocityUtils;

public class ProcessKindergartenRegistrantsRequestHandler extends RequestHandlerImpl {

	public ProcessKindergartenRegistrantsRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("sy", "School Year required."), new RequiredFormElement("sid", "School ID required.")
		});

		this.requiredPermissions = new String[] {
			"KINDERGARTEN-REGISTRATION-ADMIN-VIEW"
		};

	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {

			try {
				Collection<KindergartenRegistrantBean> registrants = null;

				String sy = form.get("sy");
				request.setAttribute("sy", sy);

				School s = SchoolDB.getSchool(form.getInt("sid"));
				request.setAttribute("sch", s);

				KindergartenRegistrantBean.SCHOOLSTREAM ss = null;
				if (form.exists("ss")) {
					ss = KindergartenRegistrantBean.SCHOOLSTREAM.get(form.getInt("ss"));
					request.setAttribute("ss", ss);

					registrants = KindergartenRegistrationManager.getKindergartenRegistrantBeans(sy, s.getSchoolID(),
							ss.getValue());
				}
				else
					registrants = KindergartenRegistrationManager.getKindergartenRegistrantBeans(sy, s.getSchoolID());

				if (registrants.size() > 0) {

					SchoolRegistrantCapBean cap = KindergartenRegistrationManager.getSchoolRegistrantCapBean(sy, s.getSchoolID());

					int english_accepted = 0, french_accepted = 0;

					for (KindergartenRegistrantBean kr : registrants) {
						if (ss != null) {
							if (kr.getSchoolStream().equals(ss)) {
								if (kr.getStatus().equals(REGISTRANT_STATUS.ACCEPTED)) {
									if (kr.getSchoolStream().equals(SCHOOLSTREAM.ENGLISH))
										english_accepted++;
									else if (kr.getSchoolStream().equals(SCHOOLSTREAM.FRENCH))
										french_accepted++;
								}
								else {
									if (kr.isPhysicalAddressApproved()) {
										if (kr.getSchoolStream().equals(SCHOOLSTREAM.ENGLISH)) {
											if (english_accepted < cap.getEnglishCap()) {
												kr.setStatus(REGISTRANT_STATUS.ACCEPTED);
												KindergartenRegistrationManager.updateKindergartenRegistrantBean(kr);
												sendAcceptanceEmail(kr);
												english_accepted++;
											}
											else {
												if (!kr.getStatus().isWaitlisted()) {
													kr.setStatus(REGISTRANT_STATUS.WAITLISTED);
													KindergartenRegistrationManager.updateKindergartenRegistrantBean(kr);
													sendWaitlistedEmail(kr);
												}
											}
										}
										else if (kr.getSchoolStream().equals(SCHOOLSTREAM.FRENCH)) {
											if (french_accepted < cap.getFrenchCap()) {
												kr.setStatus(REGISTRANT_STATUS.ACCEPTED);
												KindergartenRegistrationManager.updateKindergartenRegistrantBean(kr);
												sendAcceptanceEmail(kr);
												french_accepted++;
											}
											else {
												if (!kr.getStatus().isWaitlisted()) {
													kr.setStatus(REGISTRANT_STATUS.WAITLISTED);
													KindergartenRegistrationManager.updateKindergartenRegistrantBean(kr);
													sendWaitlistedEmail(kr);
												}
											}
										}
									}
									else {
										if (!kr.getStatus().isWaitlisted()) {
											kr.setStatus(REGISTRANT_STATUS.WAITLISTED);
											KindergartenRegistrationManager.updateKindergartenRegistrantBean(kr);
											sendWaitlistedEmail(kr);
										}
									}
								}
							}
						}
						else {
							if (kr.getStatus().equals(REGISTRANT_STATUS.ACCEPTED)) {
								if (kr.getSchoolStream().equals(SCHOOLSTREAM.ENGLISH))
									english_accepted++;
								else if (kr.getSchoolStream().equals(SCHOOLSTREAM.FRENCH))
									french_accepted++;
							}
							else {
								if (kr.isPhysicalAddressApproved()) {
									if (kr.getSchoolStream().equals(SCHOOLSTREAM.ENGLISH)) {
										if (english_accepted < cap.getEnglishCap()) {
											kr.setStatus(REGISTRANT_STATUS.ACCEPTED);
											KindergartenRegistrationManager.updateKindergartenRegistrantBean(kr);
											sendAcceptanceEmail(kr);
											english_accepted++;
										}
										else {
											if (!kr.getStatus().isWaitlisted()) {
												kr.setStatus(REGISTRANT_STATUS.WAITLISTED);
												KindergartenRegistrationManager.updateKindergartenRegistrantBean(kr);
												sendWaitlistedEmail(kr);
											}
										}
									}
									else if (kr.getSchoolStream().equals(SCHOOLSTREAM.FRENCH)) {
										if (french_accepted < cap.getFrenchCap()) {
											kr.setStatus(REGISTRANT_STATUS.ACCEPTED);
											KindergartenRegistrationManager.updateKindergartenRegistrantBean(kr);
											sendAcceptanceEmail(kr);
											french_accepted++;
										}
										else {
											if (!kr.getStatus().isWaitlisted()) {
												kr.setStatus(REGISTRANT_STATUS.WAITLISTED);
												KindergartenRegistrationManager.updateKindergartenRegistrantBean(kr);
												sendWaitlistedEmail(kr);
											}
										}
									}
								}
								else {
									if (!kr.getStatus().isWaitlisted()) {
										kr.setStatus(REGISTRANT_STATUS.WAITLISTED);
										KindergartenRegistrationManager.updateKindergartenRegistrantBean(kr);
										sendWaitlistedEmail(kr);
									}
								}
							}
						}
					}
				}

				if (ss != null) {
					request.setAttribute("registrants",
							KindergartenRegistrationManager.getKindergartenRegistrantBeans(sy, s.getSchoolID(), ss.getValue()));
				}
				else
					request.setAttribute("registrants",
							KindergartenRegistrationManager.getKindergartenRegistrantBeans(sy, s.getSchoolID()));

			}
			catch (SchoolRegistrationException e) {
				request.setAttribute("msg", e.getMessage());
			}
		}
		else {

		}

		this.path = "district_period_registrants.jsp";

		return this.path;
	}

	private void sendAcceptanceEmail(KindergartenRegistrantBean kr) {

		try {
			HashMap<String, Object> model = new HashMap<String, Object>();

			model.put("kr", kr);

			EmailBean email = new EmailBean();
			email.setTo(kr.getPrimaryContactEmail());
			email.setSubject("Newfoundland and Labrador English School District - Kindergarten Registration - Acceptance Confirmation");
			email.setBody(VelocityUtils.mergeTemplateIntoString(
					"schools/registration/kindergarten/acceptance_confirmation.vm", model));
			email.send();
		}
		catch (EmailException e) {
			e.printStackTrace();
		}
	}

	private void sendWaitlistedEmail(KindergartenRegistrantBean kr) {

		try {
			HashMap<String, Object> model = new HashMap<String, Object>();

			model.put("kr", kr);

			EmailBean email = new EmailBean();
			email.setTo(kr.getPrimaryContactEmail());
			email.setSubject("Newfoundland and Labrador English School District - Kindergarten Registration - Waitlisted");
			email.setBody(VelocityUtils.mergeTemplateIntoString(
					"schools/registration/kindergarten/waitlisted_confirmation.vm", model));
			email.send();
		}
		catch (EmailException e) {
			e.printStackTrace();
		}
	}
}
