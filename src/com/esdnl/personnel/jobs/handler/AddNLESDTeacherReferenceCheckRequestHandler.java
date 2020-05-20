package com.esdnl.personnel.jobs.handler;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.mail.bean.EmailBean;
import com.esdnl.personnel.jobs.bean.ApplicantEducationOtherBean;
import com.esdnl.personnel.jobs.bean.ApplicantEsdExperienceBean;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.ApplicantRefRequestBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.NLESDReferenceTeacherBean;
import com.esdnl.personnel.jobs.bean.ReferenceCheckRequestBean;
import com.esdnl.personnel.jobs.dao.ApplicantEducationOtherManager;
import com.esdnl.personnel.jobs.dao.ApplicantEsdExperienceManager;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.ApplicantRefRequestManager;
import com.esdnl.personnel.jobs.dao.NLESDReferenceTeacherManager;
import com.esdnl.personnel.jobs.dao.ReferenceCheckRequestManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;
import com.esdnl.velocity.VelocityUtils;
public class AddNLESDTeacherReferenceCheckRequestHandler extends RequestHandlerImpl {
	public AddNLESDTeacherReferenceCheckRequestHandler() {

		requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-VIEW", "PERSONNEL-PRINCIPAL-VIEW", "PERSONNEL-VICEPRINCIPAL-VIEW","PERSONNEL-SUBMIT-REFERENCE"
		};
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
				new RequiredFormElement("Q7"),
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

		if (form.hasValue("op", "APPLICANT_FILTER")) // AJAX CALL
		{
			ApplicantProfileBean profiles[] = null;
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<APPLICANT-PROFILE-LIST>");
			try {
				profiles = ApplicantProfileManager.getApplicantProfileBeanByNameSearch(form.get("criteria"));

				for (ApplicantProfileBean profile : profiles)
					sb.append(profile.generateXML());
			}
			catch (Exception e) {
				System.err.println(">>>>ERROR SEARCH FOR APPLICANT <<<<<<<<");
				e.printStackTrace(System.err);
			}
			sb.append("</APPLICANT-PROFILE-LIST>");
			xml = sb.toString().replaceAll("&", "&amp;");
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
			path = null;
		}
		else if (form.hasValue("op", "CANDIDATE_DETAILS")) // AJAX CALL
		{
			// generate XML for candidate details.
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<APPLICANT>");
			try {
				ApplicantProfileBean profile = ApplicantProfileManager.getApplicantProfileBean(form.get("uid"));
				ApplicantEsdExperienceBean esd_exp = ApplicantEsdExperienceManager.getApplicantEsdExperienceBean(form.get("uid"));
				ApplicantEducationOtherBean other = ApplicantEducationOtherManager.getApplicantEducationOtherBean(form.get("uid"));
				sb.append(profile.generateXML());
				if (esd_exp != null)
					sb.append(esd_exp.generateXML());
				if (other != null)
					sb.append(other.generateXML());
			}
			catch (JobOpportunityException e) {
				System.err.println(">>>>>>>> ERROR LOADING APPLICANT DETAILS <<<<<<<");
				e.printStackTrace(System.err);
			}
			sb.append("</APPLICANT>");
			xml = sb.toString().replaceAll("&", "&amp;");
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
			path = null;
		}
		else if (form.hasValue("confirm", "true") && validate_form()) {
			try {
				NLESDReferenceTeacherBean ref = null;
				if (form.exists("reference_id"))
					ref = NLESDReferenceTeacherManager.getNLESDReferenceTeacherBean(form.getInt("reference_id"));
				else {
					ref = new NLESDReferenceTeacherBean();
					//if refrequest exists do not grab dropdown value, use hidden field
					if(form.exists("refreqid") || form.exists("arefreqid")) {
						ref.setProfile(ApplicantProfileManager.getApplicantProfileBean(form.get("rapplicant_id")));
					}else {
						ref.setProfile(ApplicantProfileManager.getApplicantProfileBean(form.get("applicant_id")));
					}
					
				}
				ref.setQ1(form.get("Q1"));
				ref.setQ2(form.get("Q2"));
				ref.setQ3(form.get("Q3"));
				ref.setQ4(form.get("Q4"));
				ref.setQ5(form.get("Q5"));
				ref.setQ6(form.get("Q6"));
				ref.setQ7(form.get("Q7"));
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
				ref.setEmailAddress(form.get("ref_provider_email"));
				ref.setReferenceScale("5");
				Date d = new Date();
				ref.setDateProvided(d);
				if (ref.isNew()) {
					ref = NLESDReferenceTeacherManager.addNLESDReferenceTeacherBean(ref);
					//now we check to see if there is a related refrequestbean
					// now we check how it was submitted
					//if manual then we add the ApplicantRefRequestBean
					if(form.exists("mancheck")) {
						//add new request
						ReferenceCheckRequestBean refreq = new ReferenceCheckRequestBean();
						refreq.setCandidateId(ref.getProfile().getSIN());
						refreq.setCompetitionNumber(form.get("jobcomp"));
						refreq.setCheckRequester(usr.getPersonnel());
						refreq.setReferenceType("T");
						refreq.setReferredEmail(ref.getEmailAddress());
						refreq.setReferenceId(ref.getId());
						refreq = ReferenceCheckRequestManager.addReferenceCheckRequestBean(refreq);
						ReferenceCheckRequestManager.updateReferenceCheckRequestBean(refreq);
					}else if (form.exists("refreqid")) {//request sent from shortlist/rec
						ReferenceCheckRequestBean refchk = ReferenceCheckRequestManager.getReferenceCheckRequestBean(form.getInt("refreqid"));

						if (refchk != null) {
							refchk.setReferenceId(ref.getId());
							ReferenceCheckRequestManager.updateReferenceCheckRequestBean(refchk);
						}
					}else if (form.exists("arefreqid")) {//now we check to see if there is a related apprefrequest
						//update applicant_ref_request
						ApplicantRefRequestManager.applicantReferenceCompleted(form.getInt("arefreqid"), "Reference Completed", ref.getId());
						//send email to applicant informing it is complete
						EmailBean ebean = new EmailBean();
						ebean.setTo(new String[] {
								ApplicantProfileManager.getApplicantProfileBean(form.get("rapplicant_id")).getEmail()
						});
						ebean.setFrom("ms@nlesd.ca");
						ebean.setSubject("Reference Check Requested Completed By " + ref.getProvidedBy());
						HashMap<String, Object> model = new HashMap<String, Object>();
						model.put("reqEmail", ref.getProvidedBy());
						ebean.setBody(VelocityUtils.mergeTemplateIntoString("personnel/send_applicant_completed_request.vm", model));
						ebean.send();
						
					}
				}
				else {
					NLESDReferenceTeacherManager.updateNLESDReferenceTeacherBean(ref);
				}
				request.setAttribute("REFERENCE_BEAN", ref);
				request.setAttribute("PROFILE", ref.getProfile());
				request.setAttribute("msg", "Reference submitted successfully. Thank you!");
				//send back to add page and show messages instead of view pages which some people might not have permissions
				path = "add_nlesd_teacher_reference.jsp";
			}
			catch (Exception e) {
				e.printStackTrace(System.err);
				request.setAttribute("msg", "Could not add reference.<BR>" + e.getMessage());
				request.setAttribute("FORM", form);
				path = "add_nlesd_teacher_reference.jsp";
			}
		}
		else {
			if (form.hasValue("confirm", "true")) {
				request.setAttribute("FORM", form);
				request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));
			}
			//add logic to determine what objects need to be added
			//administrator, principal, email
			boolean hideSearch=false;
			if(form.exists("refreq")) {
				//request on the shortlist/recommedation
				hideSearch=true;
				//now we pass back the ref request bean
				ApplicantRefRequestBean rbean=ApplicantRefRequestManager.getApplicantRefRequestBean(form.getInt("refreq"));
				request.setAttribute("arefreq", rbean);
			}else if(form.exists("id")) {
				//request by applicant
				try {
					ReferenceCheckRequestBean rbean = ReferenceCheckRequestManager.getReferenceCheckRequestBean(form.getInt("id"));
					request.setAttribute("refreq", rbean);
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JobOpportunityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				hideSearch=true;
			}else if(form.exists("uid")) { 
				//manually ref check
				try {
					request.setAttribute("PROFILE", ApplicantProfileManager.getApplicantProfileBean(form.get("uid")));
					hideSearch=true;
				} catch (JobOpportunityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				//selected from ms menu
			}
			request.setAttribute("hidesearch",hideSearch);
			path = "add_nlesd_teacher_reference.jsp";
		}
		return path;
	}
}
