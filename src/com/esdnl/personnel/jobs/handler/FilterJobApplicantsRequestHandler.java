package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.ApplicantFilterParameters;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.SubListBean;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.util.StringUtils;

public class FilterJobApplicantsRequestHandler extends RequestHandlerImpl {

	public FilterJobApplicantsRequestHandler() {

		this.requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		path = "admin_index.jsp";

		ApplicantProfileBean[] profiles = null;
		JobOpportunityBean opp = null;
		SubListBean list = null;

		ApplicantFilterParameters params = new ApplicantFilterParameters();

		String[] degrees = null;
		String[] majors = null;
		String[] minors = null;
		String[] trnmthds = null;
		int[] regions = null;

		try {

			if (session.getAttribute("JOB") != null) {
				opp = (JobOpportunityBean) session.getAttribute("JOB");
				path = "admin_view_job_applicants.jsp";
			}
			else if (session.getAttribute("SUBLIST") != null) {
				list = (SubListBean) session.getAttribute("SUBLIST");
				path = "admin_view_sublist_applicants.jsp";
			}

			if ((opp == null) && (list == null)) {
				request.setAttribute("msg", "Choose a job to filter applicants.");
				path = "admin_index.jsp";
			}
			else {
				if (opp != null)
					params.setJob(opp);
				else if (list != null)
					params.setSubList(list);

				params.setPermanentContract(form.get("perm"));

				if (form.exists("perm_exp"))
					params.setPermanentExp(form.getInt("perm_exp"));

				if (form.exists("rep_exp"))
					params.setReplacementExp(form.getInt("rep_exp"));

				if (form.exists("tot_exp"))
					params.setTotalExp(form.getInt("tot_exp"));

				if (form.exists("sub_days"))
					params.setSubDays(form.getInt("sub_days"));

				if (form.exists("num_sped"))
					params.setSpecialEducationCourses(form.getInt("num_sped"));

				if (form.exists("num_french"))
					params.setFrenchCourses(form.getInt("num_french"));

				if (form.exists("num_math"))
					params.setMathCourses(form.getInt("num_math"));

				if (form.exists("num_english"))
					params.setEnglishCourses(form.getInt("num_english"));

				if (form.exists("num_music"))
					params.setMusicCourses(form.getInt("num_music"));

				if (form.exists("num_tech"))
					params.setTechnologyCourses(form.getInt("num_tech"));

				if (form.exists("num_science"))
					params.setScienceCourses(form.getInt("num_science"));
				
				if (form.exists("num_sstudies"))
					params.setSocialStudiesCourses(form.getInt("num_sstudies"));
				
				if (form.exists("num_art"))
					params.setArtCourses(form.getInt("num_art"));

				if (StringUtils.isEqual(form.get("tla_requirement"), "on"))
					params.setTLARequirements(true);
				else
					params.setTLARequirements(false);

				if ((degrees = form.getArray("degrees")) != null) {
					if ((degrees.length == 1) && degrees[0].equals("0"))
						degrees = null;

					params.setDegrees(degrees);
				}

				if ((majors = form.getArray("majors")) != null) {
					if ((majors.length == 1) && majors[0].equals("-1"))
						majors = null;

					params.setMajors(majors);
				}

				if ((minors = form.getArray("minors")) != null) {
					if ((minors.length == 1) && minors[0].equals("-1"))
						minors = null;

					params.setMinors(minors);
				}

				if ((trnmthds = form.getArray("trnmtds")) != null) {
					if ((trnmthds.length == 1) && (trnmthds[0].equals("-1") || trnmthds[0].equals("0")))
						trnmthds = null;

					params.setTrainingMethods(trnmthds);
				}

				if ((regions = form.getIntArray("region_prefs")) != null) {

					params.setRegionalPreferences(regions);
				}
				if ((form.getIntArray("majorsgroup")) != null) {

					params.setMajorsSubjectGroup(form.getArray("majorsgroup"));
				}
				if ((form.getIntArray("minorsgroup")) != null) {

					params.setMinorsSubjectGroup(form.getArray("minorsgroup"));
				}
				if (StringUtils.isEqual(form.get("delf"), "on")) {
					params.setDelfDocument(true);
				}	else {
					params.setDelfDocument(false);
				}
				
				profiles = ApplicantProfileManager.filterJobApplicantProfileBean(params);
				request.setAttribute("filterparams", params);

				session.setAttribute("JOB_APPLICANTS", profiles);
			}

		}
		catch (JobOpportunityException e) {
			e.printStackTrace();
			request.setAttribute("msg", "Could not retrieve Job applicants.");
			path = "admin_index.jsp";
		}

		return path;
	}
}