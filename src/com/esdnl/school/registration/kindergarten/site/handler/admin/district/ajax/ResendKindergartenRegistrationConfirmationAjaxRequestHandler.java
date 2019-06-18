package com.esdnl.school.registration.kindergarten.site.handler.admin.district.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.bean.EmailException;
import com.esdnl.school.registration.bean.SchoolRegistrationException;
import com.esdnl.school.registration.kindergarten.bean.KindergartenRegistrantBean;
import com.esdnl.school.registration.kindergarten.service.KindergartenRegistrationManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;
import com.esdnl.velocity.VelocityUtils;

public class ResendKindergartenRegistrationConfirmationAjaxRequestHandler extends RequestHandlerImpl {

	public ResendKindergartenRegistrationConfirmationAjaxRequestHandler() {

		this.requiredPermissions = new String[] {
			"KINDERGARTEN-REGISTRATION-ADMIN-VIEW"
		};

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("id", "Registrant ID is required.")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			if (validate_form()) {

				KindergartenRegistrantBean kr = KindergartenRegistrationManager.getKindergartenRegistrantBean(form.getInt("id"));

				if (kr != null) {
					//send confirmation email.	
					HashMap<String, Object> model = new HashMap<String, Object>();
					model.put("kr", kr);

					try {
						EmailBean email = new EmailBean();
						email.setTo(kr.getPrimaryContactEmail());
						email.setSubject("Newfoundland and Labrador English School District - Kindergarten Registration - Application Received");
						email.setBody(VelocityUtils.mergeTemplateIntoString(
								"schools/registration/kindergarten/registration_confirmation.vm", model));
						email.send();

						String xml = null;
						StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
						sb.append("<RESEND-CONFIRMATION-RESPONSE resent='true' msg='Confirmation resent.' />");

						xml = StringUtils.encodeXML(sb.toString());

						System.out.println(xml);

						PrintWriter out = response.getWriter();

						response.setContentType("text/xml");
						response.setHeader("Cache-Control", "no-cache");
						out.write(xml);
						out.flush();
						out.close();
					}
					catch (EmailException e) {
						e.printStackTrace();

						String xml = null;
						StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
						sb.append("<RESEND-CONFIRMATION-RESPONSE resent='false' msg='" + e.getMessage() + "' />");

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
					sb.append("<RESEND-CONFIRMATION-RESPONSE resent='false' msg='Registrant not found. [ID=" + form.get("id")
							+ "]' />");

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
				sb.append("<RESEND-CONFIRMATION-RESPONSE resent='false' msg='" + validator.getErrorString() + "' />");

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
			sb.append("<RESEND-CONFIRMATION-RESPONSE resent='false' msg='" + e.getMessage() + "' />");

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
