package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.mail.bean.EmailBean;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.ApplicantRefRequestBean;
import com.esdnl.personnel.jobs.bean.NLESDReferenceExternalBean;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.ApplicantRefRequestManager;
import com.esdnl.personnel.jobs.dao.NLESDReferenceExternalManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.servlet.RequiredSetValueFormElement;
import com.esdnl.util.StringUtils;
import com.esdnl.velocity.VelocityUtils;

public class AddNLESDExternalReferenceCheckAppRequestHandler extends PublicAccessRequestHandlerImpl {

	public AddNLESDExternalReferenceCheckAppRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("refreqid"), new RequiredFormElement("applicant_id"),
				new RequiredSetValueFormElement("confirm", new String[] {
						"true"
				})
				// TODO: DO WE NEED TO VALIDATE ANYMORE FIELDS
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			try {

				ApplicantRefRequestBean refReq = ApplicantRefRequestManager.getApplicantRefRequestBean(form.getInt("refreqid"));
				ApplicantProfileBean profile = ApplicantProfileManager.getApplicantProfileBean(form.get("applicant_id"));

				if ((refReq != null) && (profile != null)) {
					NLESDReferenceExternalBean ref = null;
					ref = new NLESDReferenceExternalBean();
					ref.setProfile(profile);
					ref.setQ1(form.get("Q1"));
					ref.setQ2(form.get("Q2"));
					ref.setQ3(form.get("Q3"));
					ref.setQ4(form.get("Q4"));
					ref.setQ7(form.get("radQ7"));
					ref.setQ7Comment(form.get("Q7_Comment"));
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
					ref.setScale17(form.get("Scale17"));
					ref.setScale18(form.get("Scale18"));
					ref.setScale19(form.get("Scale19"));
					ref.setScale20(form.get("Scale20"));
					ref.setScale21(form.get("Scale21"));
					ref.setScale22(form.get("Scale22"));
					ref.setDomain1Comments(form.get("d1c"));
					ref.setDomain2Comments(form.get("d2c"));
					ref.setDomain3Comments(form.get("d3c"));
					ref.setDomain4Comments(form.get("d4c"));
					ref.setProvidedBy(form.get("ref_provider_name"));
					ref.setProvidedByPosition(form.get("ref_provider_position"));
					ref.setReferenceScale("3");
					ref.setDateProvided(new Date());

					ref = NLESDReferenceExternalManager.addNLESDReferenceExternalBean(ref);
					//update applicant_ref_request
					ApplicantRefRequestManager.applicantReferenceCompleted(refReq.getId(), "Reference Completed", ref.getId());
					//send email to applicant informing it is complete

					EmailBean ebean = new EmailBean();
					ebean.setTo(new String[] {
							profile.getEmail()
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
				}
				else {
					request.setAttribute("msg", "Invalid reference check request");
					request.setAttribute("FORM", form);
				}

				path = "view_nlesd_external_reference.jsp";
			}
			catch (Exception e) {
				e.printStackTrace(System.err);
				request.setAttribute("msg", "Could not add reference.<BR>" + e.getMessage());
				request.setAttribute("FORM", form);

				path = "nlesd_external_reference_checklist_app.jsp";
			}
		}
		else {
			request.setAttribute("FORM", form);
			request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));

			path = "nlesd_external_reference_checklist_app.jsp";
		}
		return path;
	}
}
