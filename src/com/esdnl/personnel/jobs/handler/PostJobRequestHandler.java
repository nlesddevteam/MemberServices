package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.esdnl.personnel.jobs.bean.AdRequestBean;
import com.esdnl.personnel.jobs.bean.AssignmentEducationBean;
import com.esdnl.personnel.jobs.bean.AssignmentMajorMinorBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityAssignmentBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.constants.JobTypeConstant;
import com.esdnl.personnel.jobs.constants.TrainingMethodConstant;
import com.esdnl.personnel.jobs.dao.AdRequestManager;
import com.esdnl.personnel.jobs.dao.JobOpportunityManager;
import com.esdnl.util.StringUtils;

public class PostJobRequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		String path;
		HttpSession session = null;
		User usr = null;

		session = request.getSession(false);
		if ((session != null) && (session.getAttribute("usr") != null)) {
			usr = (User) session.getAttribute("usr");
			if (!(usr.getUserPermissions().containsKey("PERSONNEL-ADMIN-VIEW"))) {
				throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
			}
		}
		else {
			throw new SecurityException("User login required.");
		}

		try {
			String comp_num = request.getParameter("ad_comp_num");
			String position_title = request.getParameter("ad_title");
			String ad_text = request.getParameter("ad_text");
			String comp_end_date = request.getParameter("ad_comp_end_date");

			String location = request.getParameter("ad_location");

			String[] degree = request.getParameterValues("ad_degree");

			String[] major = request.getParameterValues("ad_major");

			String[] minor = request.getParameterValues("ad_minor");

			String trnmthd = request.getParameter("ad_trnmtd");

			String listing_date = request.getParameter("ad_listing_date");

			String job_type = request.getParameter("ad_job_type");
			String issupport = request.getParameter("issupport");

			if (!StringUtils.isEmpty(request.getParameter("edit")) && StringUtils.isEmpty(comp_num)) {
				request.setAttribute("msgERR", "Competition number is a required field.");
				path = "admin_post_job.jsp";
			}
			else if (StringUtils.isEmpty(position_title)) {
				request.setAttribute("msgERR", "Position title is a required field.");
				path = "admin_post_job.jsp";
			}
			else if (StringUtils.isEmpty(location.replaceAll("-", ""))) {
				request.setAttribute("msgERR", "Position location is a required field.");
				path = "admin_post_job.jsp";
			}
			else if (StringUtils.isEmpty(ad_text)) {
				request.setAttribute("msgERR", "Ad text is a required field.");
				path = "admin_post_job.jsp";
			}
			/*
			else if ((degree == null) || (degree.length < 1)) {
				request.setAttribute("msgERR", "Degree a required field.");
				path = "admin_post_job.jsp";
			}
			else if ((major == null) || (major.length < 1)) {
				request.setAttribute("msgERR", "Major a required field.");
				path = "admin_post_job.jsp";
			}
			else if ((minor == null) || (minor.length < 1)) {
				request.setAttribute("msgERR", "Minor a required field.");
				path = "admin_post_job.jsp";
			}
			*/
			else if (StringUtils.isEmpty(comp_end_date)) {
				request.setAttribute("msgERR", "Competition end date is a required field.");
				path = "admin_post_job.jsp";
			}
			else if (StringUtils.isEmpty(listing_date)) {
				request.setAttribute("msgERR", "Listing date is a required field.");
				path = "admin_post_job.jsp";
			}
			else if (StringUtils.isEmpty(job_type)) {
				request.setAttribute("msgERR", "Job type is a required field.");
				path = "admin_post_job.jsp";
			}
			else {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				SimpleDateFormat sdf_ced = new SimpleDateFormat("dd/MM/yyyy hh:mm:a");

				JobOpportunityBean opp = new JobOpportunityBean();
				opp.setCompetitionNumber(comp_num);
				opp.setPositionTitle(position_title);
				opp.setJobAdText(ad_text);;
				opp.setCompetitionEndDate(sdf_ced.parse(comp_end_date));
				opp.setListingDate(sdf.parse(listing_date));
				opp.setJobType(JobTypeConstant.get(Integer.parseInt(job_type)));

				if (opp.getJobType().equal(JobTypeConstant.ADMINISTRATIVE)
						|| opp.getJobType().equal(JobTypeConstant.LEADERSHIP))
					opp.setPrivateCandidateList(true);
				else if (!StringUtils.isEmpty(request.getParameter("candidatelist_private")))
					opp.setPrivateCandidateList(true);

				JobOpportunityAssignmentBean ass = new JobOpportunityAssignmentBean(opp.getCompetitionNumber(), Integer.parseInt(
						location), 0.0);
				opp.add(ass);

				if ((degree != null) && (degree.length > 0)) {
					for (int i = 0; i < degree.length; i++) {
						if (!StringUtils.isEmpty(degree[i]))
							ass.addRequiredEducation(new AssignmentEducationBean(degree[i]));
					}
				}

				if ((major != null) && (major.length > 0)) {
					for (int i = 0; i < major.length; i++) {
						if (!StringUtils.isEmpty(major[i]) && (Integer.parseInt(major[i]) != -1))
							ass.addRequiredMajor(new AssignmentMajorMinorBean(Integer.parseInt(major[i]), -1));
					}
				}

				if ((minor != null) && (minor.length > 0)) {
					for (int i = 0; i < minor.length; i++) {
						if (!StringUtils.isEmpty(minor[i]) && (Integer.parseInt(minor[i]) != -1))
							ass.addRequiredMajor(new AssignmentMajorMinorBean(-1, Integer.parseInt(minor[i])));
					}
				}

				if (!StringUtils.isEmpty(trnmthd))
					ass.addRequiredTrainingMethod(TrainingMethodConstant.get(Integer.parseInt(trnmthd)));

				if (request.getParameter("edit") == null) {
					if (!StringUtils.isEmpty(request.getParameter("request_id")) && issupport.equals("Y")) {
						AdRequestBean ad = AdRequestManager.getAdRequestBean(Integer.parseInt(request.getParameter("request_id")));

						if (ad != null) {
							opp.setUnadvertise(ad.isUnadvertised());
						}
						else {
							opp.setUnadvertise(false);
						}
					}
					else {
						opp.setUnadvertise(false);
					}
					if (issupport.equals("Y")) {
						opp.setIsSupport("Y");
					}
					else {
						opp.setIsSupport("N");
					}
					opp = JobOpportunityManager.addJobOpportunityBean(opp);
					request.setAttribute("msgOK", "Job " + opp.getCompetitionNumber() + " posted successfully.");

					if (issupport.equals("Y")) {
						if (!StringUtils.isEmpty(request.getParameter("request_id"))) {
							AdRequestManager.postAdRequestSSBean(Integer.parseInt(request.getParameter("request_id")),
									usr.getPersonnel(), opp.getCompetitionNumber());
						}
					}
					else {
						if (!StringUtils.isEmpty(request.getParameter("request_id"))) {
							AdRequestManager.postAdRequestBean(
									AdRequestManager.getAdRequestBean(Integer.parseInt(request.getParameter("request_id"))),
									usr.getPersonnel(), opp.getCompetitionNumber());
						}
					}

				}
				else {
					JobOpportunityManager.updateJobOpportunityBean(opp);
					request.setAttribute("msgOK", "Job " + opp.getCompetitionNumber() + " updated successfully.");
				}

				path = "admin_post_job.jsp";

			}
		}
		catch (ParseException e) {
			request.setAttribute("msgERR", "Invalid data format.");
			path = "admin_post_job.jsp";
		}
		catch (JobOpportunityException e) {
			request.setAttribute("msgERR", "Could not post job.");
			path = "admin_post_job.jsp";
		}

		return path;
	}
}