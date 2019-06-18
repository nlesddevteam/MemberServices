package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.NLESDReferenceGuideBean;
import com.esdnl.personnel.jobs.bean.ReferenceCheckRequestBean;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.NLESDReferenceGuideManager;
import com.esdnl.personnel.jobs.dao.ReferenceCheckRequestManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class AddNLESDExternalGuideReferenceCheckRequestHandler extends PublicAccessRequestHandlerImpl {
	public AddNLESDExternalGuideReferenceCheckRequestHandler() {
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
							if (ref.isNew()) {
								ref = NLESDReferenceGuideManager.addNLESDReferenceGuideBean(ref);
							}
							if (form.exists("request_id")) {
								ReferenceCheckRequestBean refchk = ReferenceCheckRequestManager.getReferenceCheckRequestBean(form.getInt("request_id"));

								if (refchk != null) {
									refchk.setReferenceId(ref.getId());
									ReferenceCheckRequestManager.updateReferenceCheckRequestBean(refchk);
								}
							}							
							request.setAttribute("REFERENCE_BEAN", ref);
							request.setAttribute("PROFILE", ref.getProfile());
							request.setAttribute("msg", "Reference submitted successfully. Thank you!");
							path = "view_nlesd_guide_reference.jsp";

						}
						catch (Exception e) {
							e.printStackTrace(System.err);
							request.setAttribute("msg", "Could not add reference.<BR>" + e.getMessage());
							request.setAttribute("FORM", form);
							path = "nlesd_guide_reference_checklist.jsp";
						}
					}
					else {
						if (form.hasValue("confirm", "true")) {
							request.setAttribute("FORM", form);
							request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));
						}
						path = "nlesd_guide_reference_checklist.jsp";
					}
					return path;
				}
}
