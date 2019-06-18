package com.esdnl.personnel.jobs.handler;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.ApplicantEducationOtherBean;
import com.esdnl.personnel.jobs.bean.ApplicantEsdExperienceBean;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.NLESDReferenceAdminBean;
import com.esdnl.personnel.jobs.bean.NLESDReferenceExternalBean;
import com.esdnl.personnel.jobs.dao.ApplicantEducationOtherManager;
import com.esdnl.personnel.jobs.dao.ApplicantEsdExperienceManager;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.NLESDReferenceAdminManager;
import com.esdnl.personnel.jobs.dao.NLESDReferenceExternalManager;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.util.StringUtils;
public class AddNLESDExternalReferenceCheckRequestHandler extends RequestHandlerImpl {
	public AddNLESDExternalReferenceCheckRequestHandler() {
		requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-VIEW", "PERSONNEL-PRINCIPAL-VIEW", "PERSONNEL-VICEPRINCIPAL-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		
		if (form.hasValue("op", "APPLICANT_FILTER")) // AJAX CALL
		{
			ApplicantProfileBean profiles[] = null;
			// generate XML for candidate details.
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

		else if (form.hasValue("confirm", "true")) {
			try {
				NLESDReferenceExternalBean ref = null;
				ref = new NLESDReferenceExternalBean();
				if (form.exists("reference_id"))
					ref = NLESDReferenceExternalManager.getNLESDReferenceExternalBean(form.getInt("reference_id"));
				else {
					ref = new NLESDReferenceExternalBean();

					ref.setProfile(ApplicantProfileManager.getApplicantProfileBean(form.get("applicant_id")));
				}
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
				Date d = new Date();
				ref.setDateProvided(d);
				
				
				if (ref.isNew()) {
					ref = NLESDReferenceExternalManager.addNLESDReferenceExternalBean(ref);
				}
				else {
					NLESDReferenceExternalManager.updateNLESDReferenceExternalBean(ref);
				}
				request.setAttribute("REFERENCE_BEAN", ref);
				request.setAttribute("PROFILE", ref.getProfile());
				request.setAttribute("msg", "Reference submitted successfully. Thank you!");
				path = "view_nlesd_external_reference.jsp";

			}
			catch (Exception e) {
				e.printStackTrace(System.err);
				request.setAttribute("msg", "Could not add reference.<BR>" + e.getMessage());
				request.setAttribute("FORM", form);
				path = "add_nlesd_external_reference.jsp";
			}
		}
		else {
			if (form.hasValue("confirm", "true")) {
				request.setAttribute("FORM", form);
				request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));
			}
			path = "add_nlesd_external_reference.jsp";
		}
		return path;
	}
}
