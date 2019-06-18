package com.esdnl.personnel.recognition.site.handler;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.mail.bean.EmailBean;
import com.awsd.personnel.Personnel;
import com.awsd.school.SchoolDB;
import com.awsd.security.User;
import com.esdnl.personnel.recognition.database.NominationManager;
import com.esdnl.personnel.recognition.database.NominationPeriodManager;
import com.esdnl.personnel.recognition.model.bean.NominationBean;
import com.esdnl.personnel.recognition.model.bean.RecognitionException;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
import com.esdnl.servlet.RequiredFileFormElement;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class AddRecognitionNominationRequestHandler extends PublicAccessRequestHandlerImpl {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		usr = (User) session.getAttribute("usr");

		if (usr == null)
			path = "index.jsp";
		else
			path = "add_recognition_nomination.jsp";

		if (StringUtils.isEqual(form.get("op"), "ADD")) {

			usr = (User) session.getAttribute("usr");

			if (usr != null) {
				validator = new FormValidator(new FormElement[] {
						new RequiredFormElement("nominator_id"), new RequiredFormElement("nominee_first_name"),
						new RequiredFormElement("nominee_last_name"), new RequiredFormElement("nominee_location_id"),
						new RequiredFormElement("period_id"), new RequiredFileFormElement("rationale_file", new String[] {
								".pdf", ".doc", ".txt"
						})
				});
			}
			else {
				validator = new FormValidator(new FormElement[] {
						new RequiredFormElement("nominee_first_name"), new RequiredFormElement("nominee_last_name"),
						new RequiredFormElement("nominee_location_id"), new RequiredFormElement("period_id"),
						new RequiredFileFormElement("rationale_file", new String[] {
								".pdf", ".doc", ".txt"
						}), new RequiredFormElement("nominator_first_name"), new RequiredFormElement("nominator_last_name"),
						new RequiredFormElement("nominator_location")
				});
			}

			if (validate_form()) {
				try {
					NominationBean bean = new NominationBean();
					bean.setNomineeFirstname(form.get("nominee_first_name"));
					bean.setNomineeLastname(form.get("nominee_last_name"));
					bean.setNomineeLocation(SchoolDB.getSchool(form.getInt("nominee_location_id")));
					bean.setNominationDate(Calendar.getInstance().getTime());
					bean.setNominationPeriod(NominationPeriodManager.getNominationPeriodBean(form.getInt("period_id")));
					if (usr != null)
						bean.setNominator(usr.getPersonnel());
					else {
						bean.setNominatorFirstName(form.get("nominator_first_name"));
						bean.setNominatorLastName(form.get("nominator_last_name"));
						bean.setNominatorLocation(form.get("nominator_location"));
					}
					if (form.uploadFileExists("rationale_file"))
						bean.setRationaleFilename(save_file("rationale_file", "/recognition/nominations/rationales/"));

					NominationManager.addNominationBean(bean);
					request.setAttribute("msg", "Nomination added successfully.");
					request.setAttribute("NOMINATIONBEAN", bean);

					Personnel monitor = bean.getNominationPeriod().getCategory().getMonitor();
					if (monitor != null) {
						EmailBean email = new EmailBean();
						email.setTo(new String[] {
								"chriscrane@esdnl.ca", monitor.getEmailAddress()
						});
						email.setSubject("Nomination Received: " + bean.getNominationPeriod().getCategory().getName() + " - "
								+ bean.getNomineeFullName());
						email.setBody(bean.getNomineeFullName() + " has been nominated for  "
								+ bean.getNominationPeriod().getCategory().getName() + " by " + bean.getNominatorFullName()
								+ ". The nomination rational is attached."
								+ "<BR><BR>PLEASE DO NOT RESPOND TO THIS MESSAGE. THANK YOU.<br><br>" + "Member Services");
						email.setFrom("ms@nlesd.ca");
						email.setAttachments(new File[] {
							new File(ROOT_DIR + "/recognition/nominations/rationales/" + bean.getRationaleFilename())
						});
						email.send();
					}

					path = "view_recognition_nomination.jsp";
				}
				catch (RecognitionException e) {
					e.printStackTrace();

					request.setAttribute("FORM", form);
					request.setAttribute("msg", "Could not add nomination.");
				}
				catch (Exception e) {
					e.printStackTrace();

					request.setAttribute("FORM", form);
					request.setAttribute("msg", "Could not add nomination.");
				}
			}
			else {
				request.setAttribute("FORM", form);
				request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));
			}
		}

		return path;
	}
}
