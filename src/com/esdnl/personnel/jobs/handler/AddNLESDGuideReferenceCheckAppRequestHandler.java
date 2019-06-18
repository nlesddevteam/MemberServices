package com.esdnl.personnel.jobs.handler;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.mail.bean.EmailBean;
import com.esdnl.personnel.jobs.bean.NLESDReferenceGuideBean;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.ApplicantRefRequestManager;
import com.esdnl.personnel.jobs.dao.NLESDReferenceGuideManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;
import com.esdnl.velocity.VelocityUtils;

public class AddNLESDGuideReferenceCheckAppRequestHandler extends PublicAccessRequestHandlerImpl {
	public AddNLESDGuideReferenceCheckAppRequestHandler() {
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
							new RequiredFormElement("Scale9")
					});
					if (form.hasValue("confirm", "true") && validate_form()) {
						try {
							int refreqid=Integer.parseInt(form.get("refreqid"));
							NLESDReferenceGuideBean ref=null;
							ref = new NLESDReferenceGuideBean();
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
							ref.setProvidedBy(form.get("ref_provider_name"));
							ref.setProvidedByPosition(form.get("ref_provider_position"));
							ref.setReferenceScale("3");
							Date d = new Date();
							ref.setDateProvided(d);
							ref = NLESDReferenceGuideManager.addNLESDReferenceGuideBean(ref);
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
							path = "view_nlesd_guide_reference.jsp";

						}
						catch (Exception e) {
							e.printStackTrace(System.err);
							request.setAttribute("msg", "Could not add reference.<BR>" + e.getMessage());
							request.setAttribute("FORM", form);
							path = "nlesd_guide_reference_checklist_app.jsp";
						}
					}
					else {
						if (form.hasValue("confirm", "true")) {
							request.setAttribute("FORM", form);
							request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));
						}
						path = "nlesd_guide_reference_checklist_app.jsp";
					}
					return path;
				}
}