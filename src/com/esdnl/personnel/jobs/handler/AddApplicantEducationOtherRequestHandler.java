package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import com.awsd.servlet.LoginNotRequiredRequestHandler;
import com.esdnl.personnel.jobs.bean.ApplicantEducationOtherBean;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.constants.TrainingMethodConstant;
import com.esdnl.personnel.jobs.dao.ApplicantEducationOtherManager;

public class AddApplicantEducationOtherRequestHandler implements LoginNotRequiredRequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		String path;
		SimpleDateFormat sdf = null;
		ApplicantProfileBean profile = null;

		try {
			String trnmtds = request.getParameter("trnmtds");
			String sped_crs = request.getParameter("sped_courses");
			String fr_crs = request.getParameter("fr_crs");
			String math_crs = request.getParameter("math_crs");
			String english_crs = request.getParameter("english_crs");
			String cert_lvl = request.getParameter("cert_lvl");
			String cert_date = request.getParameter("cert_date");
			String music_crs = request.getParameter("music_crs");
			String tech_crs = request.getParameter("tech_crs");
			String science_crs = request.getParameter("science_crs");
			String sstudies_crs = request.getParameter("sstudies_crs");
			String art_crs = request.getParameter("art_crs");
			String total_crs = request.getParameter("total_crs");

			profile = (ApplicantProfileBean) request.getSession(false).getAttribute("APPLICANT");

			if (profile == null) {
				path = "applicant_registration_step_1.jsp";
			}
			else if (StringUtils.isEmpty(trnmtds)) {
				request.setAttribute("errmsg", "Please specify your level of professional training.");
				path = "applicant_registration_step_6.jsp";
			}
			else if (StringUtils.isEmpty(sped_crs)) {
				request.setAttribute("errmsg", "Please specify number of Special Education courses.");
				path = "applicant_registration_step_6.jsp";
			}
			else if (StringUtils.isEmpty(fr_crs)) {
				request.setAttribute("errmsg", "Please specify number of French courses.");
				path = "applicant_registration_step_6.jsp";
			}
			else if (StringUtils.isEmpty(math_crs)) {
				request.setAttribute("errmsg", "Please specify number of Math courses.");
				path = "applicant_registration_step_6.jsp";
			}
			else if (StringUtils.isEmpty(english_crs)) {
				request.setAttribute("errmsg", "Please specify number of English courses.");
				path = "applicant_registration_step_6.jsp";
			}
			else if (StringUtils.isEmpty(music_crs)) {
				request.setAttribute("errmsg", "Please specify number of Music courses.");
				path = "applicant_registration_step_6.jsp";
			}
			else if (StringUtils.isEmpty(tech_crs)) {
				request.setAttribute("errmsg", "Please specify number of Technology courses.");
				path = "applicant_registration_step_6.jsp";
			}
			else if (StringUtils.isEmpty(science_crs)) {
				request.setAttribute("errmsg", "Please specify number of Science courses.");
				path = "applicant_registration_step_6.jsp";
			}
			else if (StringUtils.isEmpty(sstudies_crs)) {
				request.setAttribute("errmsg", "Please specify number of Social Studies courses.");
				path = "applicant_registration_step_6.jsp";
			}
			else if (StringUtils.isEmpty(art_crs)) {
				request.setAttribute("errmsg", "Please specify number of Art courses.");
				path = "applicant_registration_step_6.jsp";
			}
			else if (StringUtils.isEmpty(total_crs)) {
				request.setAttribute("errmsg", "Please specify total number of courses completed.");
				path = "applicant_registration_step_6.jsp";
			}
			else {
				sdf = new SimpleDateFormat("MM/yyyy");

				ApplicantEducationOtherBean abean = new ApplicantEducationOtherBean();

				abean.setSIN(profile.getSIN());
				abean.setNumberFrenchCourses(Integer.parseInt(fr_crs));
				abean.setNumberSpecialEducationCourses(Integer.parseInt(sped_crs));
				abean.setNumberMathCourses(Integer.parseInt(math_crs));
				abean.setNumberEnglishCourses(Integer.parseInt(english_crs));
				abean.setProfessionalTrainingLevel(TrainingMethodConstant.get(Integer.parseInt(trnmtds)));
				if (StringUtils.isNotBlank(cert_date)) {
					abean.setTeachingCertificateIssuedDate(sdf.parse(cert_date));
				}
				else {
					abean.setTeachingCertificateIssuedDate(null);
				}
				abean.setTeachingCertificateLevel(cert_lvl);
				abean.setNumberMusicCourses(Integer.parseInt(music_crs));
				abean.setNumberTechnologyCourses(Integer.parseInt(tech_crs));
				abean.setNumberScienceCourses(Integer.parseInt(science_crs));
				abean.setNumberSocialStudiesCourses(Integer.parseInt(science_crs));
				abean.setNumberArtCourses(Integer.parseInt(art_crs));
				abean.setTotalCoursesCompleted(Integer.parseInt(total_crs));
				ApplicantEducationOtherManager.addApplicantEducationOtherBean(abean);

				request.setAttribute("msg", "Education (Other) successfully added.");
				path = "applicant_registration_step_6.jsp";
			}

		}
		catch (JobOpportunityException e) {
			e.printStackTrace();
			request.setAttribute("errmsg", "Could not add applicant education.");
			path = "applicant_registration_step_6.jsp";
		}
		catch (ParseException e) {
			e.printStackTrace();
			request.setAttribute("errmsg", "Invalid date format.");
			path = "applicant_registration_step_6.jsp";
		}

		return path;
	}
}