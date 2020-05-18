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
import com.esdnl.personnel.jobs.bean.ReferenceCheckRequestBean;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.ApplicantRefRequestManager;
import com.esdnl.personnel.jobs.dao.NLESDReferenceExternalManager;
import com.esdnl.personnel.jobs.dao.ReferenceCheckRequestManager;
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
				new RequiredFormElement("applicant_id"),
				new RequiredSetValueFormElement("confirm", new String[] {
						"true"
				})
		});
		validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("applicant_id"),
				new RequiredFormElement("ref_provider_name"),
				new RequiredFormElement("ref_provider_position"),
				new RequiredFormElement("Q1"),
				new RequiredFormElement("Q2"),
				new RequiredFormElement("Q3"),
				new RequiredFormElement("Q4"),
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
				new RequiredFormElement("Scale16"),
				new RequiredFormElement("Scale17"),
				new RequiredFormElement("Scale18"),
				new RequiredFormElement("Scale19"),
				new RequiredFormElement("Scale20"),
				new RequiredFormElement("Scale21"),
				new RequiredFormElement("Scale22"),
				new RequiredFormElement("ref_provider_email")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);
		ApplicantRefRequestBean refReq = null;
		ReferenceCheckRequestBean rbean = null;
		if (validate_form()) {
			try {
				if(form.exists("arefreqid")) {
					refReq = ApplicantRefRequestManager.getApplicantRefRequestBean(form.getInt("arefreqid"));
				}else if(form.exists("refreqid")) {
					rbean = ReferenceCheckRequestManager.getReferenceCheckRequestBean(form.getInt("refreqid"));
				}
				
				ApplicantProfileBean profile = ApplicantProfileManager.getApplicantProfileBean(form.get("applicant_id"));

				if ((refReq != null || rbean != null) && (profile != null)) {
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
					ref.setEmailAddress(form.get("ref_provider_email"));
					ref = NLESDReferenceExternalManager.addNLESDReferenceExternalBean(ref);
					if(form.exists("mancheck")) {
						//add new request
						ReferenceCheckRequestBean refreq = new ReferenceCheckRequestBean();
						refreq.setCandidateId(ref.getProfile().getSIN());
						refreq.setCompetitionNumber(form.get("jobcomp"));
						refreq.setCheckRequester(usr.getPersonnel());
						refreq.setReferenceType("G");
						refreq.setReferredEmail(ref.getEmailAddress());
						refreq.setReferenceId(ref.getId());
						refreq = ReferenceCheckRequestManager.addReferenceCheckRequestBean(refreq);
						ReferenceCheckRequestManager.updateReferenceCheckRequestBean(refreq);
					}else if (form.exists("refreqid")) {
						ReferenceCheckRequestBean refchk = ReferenceCheckRequestManager.getReferenceCheckRequestBean(form.getInt("refreqid"));
						if (refchk != null) {
							refchk.setReferenceId(ref.getId());
							ReferenceCheckRequestManager.updateReferenceCheckRequestBean(refchk);
						}
					}else if (form.exists("arefreqid")) {//now we check to see if there is a related apprefrequest
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
					}
					

					//request.setAttribute("REFERENCE_BEAN", ref);
					if(refReq != null) {
						request.setAttribute("arefreq", refReq);
					}
					if(rbean != null) {
						request.setAttribute("refreq", rbean);
					}
					
					request.setAttribute("PROFILE", ref.getProfile());
					request.setAttribute("msg", "Reference submitted successfully. Thank you!");
				}
				else {
					request.setAttribute("msg", "Invalid reference check request");
					request.setAttribute("FORM", form);
				}

				path = "nlesd_external_reference_checklist_app.jsp";
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
