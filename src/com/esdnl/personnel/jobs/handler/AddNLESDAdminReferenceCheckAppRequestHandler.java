package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.mail.bean.EmailBean;
import com.esdnl.personnel.jobs.bean.NLESDReferenceAdminBean;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.ApplicantRefRequestManager;
import com.esdnl.personnel.jobs.dao.NLESDReferenceAdminManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;
import com.esdnl.velocity.VelocityUtils;

public class AddNLESDAdminReferenceCheckAppRequestHandler extends PublicAccessRequestHandlerImpl {
	public AddNLESDAdminReferenceCheckAppRequestHandler() {
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("applicant_id"),
				new RequiredFormElement("ref_provider_name"),
				new RequiredFormElement("ref_provider_position"),
				new RequiredFormElement("Q1"),
				new RequiredFormElement("Q2"),
				new RequiredFormElement("Q3"),
				new RequiredFormElement("Q4"),
				new RequiredFormElement("Q5"),
				new RequiredFormElement("Q6"),
				new RequiredFormElement("Scale1"),
				new RequiredFormElement("Scale2"),
				new RequiredFormElement("Scale3"),
				new RequiredFormElement("Scale4"),
				new RequiredFormElement("Scale5"),
				new RequiredFormElement("Scale6"),
				new RequiredFormElement("Scale7"),
				new RequiredFormElement("Scale8"),
				new RequiredFormElement("Scale9"),
				new RequiredFormElement("Scale10"),
				new RequiredFormElement("Scale11"),
				new RequiredFormElement("Scale12"),
				new RequiredFormElement("Scale13"),
				new RequiredFormElement("Scale14"),
				new RequiredFormElement("Scale15"),
				new RequiredFormElement("Scale16")
		});

		if (form.hasValue("confirm", "true") && validate_form()) {
			try {
				int refreqid=Integer.parseInt(form.get("refreqid"));
				NLESDReferenceAdminBean ref=null;
				ref = new NLESDReferenceAdminBean();
				ref.setProfile(ApplicantProfileManager.getApplicantProfileBean(form.get("applicant_id")));
				ref.setQ1(form.get("Q1"));
				ref.setQ2(form.get("Q2"));
				ref.setQ3(form.get("Q3"));
				ref.setQ4(form.get("Q4"));
				ref.setQ5(form.get("Q5"));
				ref.setQ6(form.get("Q6"));
				ref.setQ6Comment(form.get("Q6_Comment"));
				ref.setScale1(form.get("Scale1"));
				ref.setScale2(form.get("Scale2"));
				ref.setScale3(form.get("Scale3"));
				ref.setScale4(form.get("Scale4"));
				ref.setScale5(form.get("Scale5"));
				ref.setScale6(form.get("Scale6"));
				ref.setScale7(form.get("Scale7"));
				ref.setScale8(form.get("Scale8"));
				ref.setScale9(form.get("Scale9"));
				ref.setScale10(form.get("Scale10"));
				ref.setScale11(form.get("Scale11"));
				ref.setScale12(form.get("Scale12"));
				ref.setScale13(form.get("Scale13"));
				ref.setScale14(form.get("Scale14"));
				ref.setScale15(form.get("Scale15"));
				ref.setScale16(form.get("Scale16"));
				ref.setDomain1Comments(form.get("d1c"));
				ref.setDomain2Comments(form.get("d2c"));
				ref.setDomain3Comments(form.get("d3c"));
				ref.setProvidedBy(form.get("ref_provider_name"));
				ref.setProvidedByPosition(form.get("ref_provider_position"));
				ref.setReferenceScale("5");
				Date d = new Date();
				ref.setDateProvided(d);
				ref = NLESDReferenceAdminManager.addNLESDReferenceAdminBean(ref);
				//update applicant_ref_request
				ApplicantRefRequestManager.applicantReferenceCompleted(refreqid, "Reference Completed", ref.getId());
				//send email to applicant informing it is complete
				EmailBean ebean = new EmailBean();
				ebean.setTo(new String[] {
						ApplicantProfileManager.getApplicantProfileBean(form.get("applicant_id")).getEmail()
				});
				ebean.setFrom("ms@nlesd.ca");
				ebean.setSubject("Reference Check Requested Completed By " + form.get("ref_provider_name"));
				HashMap<String, Object> model = new HashMap<String, Object>();
				model.put("reqEmail", form.get("ref_provider_name"));
				ebean.setBody(VelocityUtils.mergeTemplateIntoString("personnel/send_applicant_completed_request.vm", model));
				ebean.send();
				request.setAttribute("REFERENCE_BEAN", ref);
				request.setAttribute("PROFILE", ref.getProfile());
				request.setAttribute("msg", "Reference submitted successfully. Thank you!");
				path = "view_nlesd_admin_reference.jsp";

			}
			catch (Exception e) {
				e.printStackTrace(System.err);
				request.setAttribute("msg", "Could not add reference.<BR>" + e.getMessage());
				request.setAttribute("FORM", form);
				path = "nlesd_admin_reference_checklist_app.jsp";
			}
		}
		else {
			if (form.hasValue("confirm", "true")) {
				request.setAttribute("FORM", form);
				request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));
			}
			path = "nlesd_admin_reference_checklist_app.jsp";
		}
		return path;
	}
}
