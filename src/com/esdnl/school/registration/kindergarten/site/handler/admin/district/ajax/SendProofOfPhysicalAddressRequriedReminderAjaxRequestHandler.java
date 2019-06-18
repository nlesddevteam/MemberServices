package com.esdnl.school.registration.kindergarten.site.handler.admin.district.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.bean.EmailException;
import com.esdnl.school.registration.bean.SchoolRegistrationException;
import com.esdnl.school.registration.kindergarten.bean.KindergartenRegistrantBean;
import com.esdnl.school.registration.kindergarten.bean.KindergartenRegistrationPeriodBean;
import com.esdnl.school.registration.kindergarten.service.KindergartenRegistrationManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;
import com.esdnl.velocity.VelocityUtils;

public class SendProofOfPhysicalAddressRequriedReminderAjaxRequestHandler extends RequestHandlerImpl {

	public SendProofOfPhysicalAddressRequriedReminderAjaxRequestHandler() {

		this.requiredPermissions = new String[] {
			"KINDERGARTEN-REGISTRATION-ADMIN-VIEW"
		};

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("id", "Registration ID is required.")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			if (validate_form()) {

				KindergartenRegistrationPeriodBean period = KindergartenRegistrationManager.getKindergartenRegistrationPeriodBean(form.getInt("id"));

				if (period != null) {
					Collection<KindergartenRegistrantBean> registrants = KindergartenRegistrationManager.getKindergartenRegistrantBeans(period);

					if (registrants != null && registrants.size() > 0) {

						int total = 0, good = 0, bad = 0;

						HashMap<String, Object> model = new HashMap<String, Object>();

						for (KindergartenRegistrantBean kr : registrants) {

							if (kr.isPhysicalAddressApproved())
								continue;

							total++;

							model.put("kr", kr);

							System.out.println(kr.getPrimaryContactEmail());

							try {
								EmailBean email = new EmailBean();
								email.setTo(kr.getPrimaryContactEmail());
								email.setSubject("Newfoundland and Labrador English School District - Kindergarten Registration - Proof of Physical Address Required");
								email.setBody(VelocityUtils.mergeTemplateIntoString(
										"schools/registration/kindergarten/physicaladdressproof_reminder.vm", model));
								email.send();

								good++;
							}
							catch (EmailException e) {
								bad++;
							}
						}

						String xml = null;
						StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
						sb.append("<PHYSICAL-ADDRESS-PROOF-REMINDER-RESPONSE sent='true' msg='" + good + " Reminders send of "
								+ total + ".' />");

						xml = StringUtils.encodeXML(sb.toString());

						System.out.println(xml);

						PrintWriter out = response.getWriter();

						response.setContentType("text/xml");
						response.setHeader("Cache-Control", "no-cache");
						out.write(xml);
						out.flush();
						out.close();
					}
					else {
						String xml = null;
						StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
						sb.append("<PHYSICAL-ADDRESS-PROOF-REMINDER-RESPONSE sent='false' msg='0 Registrants found for period. [ID="
								+ form.get("id") + "]' />");

						xml = StringUtils.encodeXML(sb.toString());

						System.out.println(xml);

						PrintWriter out = response.getWriter();

						response.setContentType("text/xml");
						response.setHeader("Cache-Control", "no-cache");
						out.write(xml);
						out.flush();
						out.close();
					}
				}
				else {
					String xml = null;
					StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
					sb.append("<PHYSICAL-ADDRESS-PROOF-REMINDER-RESPONSE sent='false' msg='Could not find registration period. [ID="
							+ form.get("id") + "]' />");

					xml = StringUtils.encodeXML(sb.toString());

					System.out.println(xml);

					PrintWriter out = response.getWriter();

					response.setContentType("text/xml");
					response.setHeader("Cache-Control", "no-cache");
					out.write(xml);
					out.flush();
					out.close();
				}
			}
			else {
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<PHYSICAL-ADDRESS-PROOF-REMINDER-RESPONSE sent='false' msg='" + validator.getErrorString() + "' />");

				xml = StringUtils.encodeXML(sb.toString());

				System.out.println(xml);

				PrintWriter out = response.getWriter();

				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
				request.setAttribute("msg", this.validator.getErrorString());
			}

			request.setAttribute("periods", KindergartenRegistrationManager.getKindergartenRegistrationPeriodBeans());
		}
		catch (SchoolRegistrationException e) {
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<PHYSICAL-ADDRESS-PROOF-REMINDER-RESPONSE sent='false' msg='" + e.getMessage() + "' />");

			xml = StringUtils.encodeXML(sb.toString());

			System.out.println(xml);

			PrintWriter out = response.getWriter();

			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
		}

		return null;
	}
}
