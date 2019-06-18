package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.school.GradeDB;
import com.awsd.school.SubjectDB;
import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.esdnl.personnel.jobs.bean.GradeSubjectPercentUnitBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.util.StringUtils;

public class AddTeacherRecommendationGSURequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		String path = null;
		HttpSession session = null;
		User usr = null;

		session = request.getSession(false);
		if ((session != null) && (session.getAttribute("usr") != null)) {
			usr = (User) session.getAttribute("usr");
			if (!(usr.getUserPermissions().containsKey("PERSONNEL-ADMIN-VIEW")
					|| usr.getUserPermissions().containsKey("PERSONNEL-PRINCIPAL-VIEW") || usr.getUserPermissions().containsKey(
					"PERSONNEL-VICEPRINCIPAL-VIEW"))) {
				throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
			}
		}
		else {
			throw new SecurityException("User login required.");
		}

		try {
			String g = request.getParameter("g");

			String s = request.getParameter("s");

			String u = request.getParameter("u");

			JobOpportunityBean job = (JobOpportunityBean) session.getAttribute("JOB");

			@SuppressWarnings("unchecked")
			HashMap<String, ArrayList<GradeSubjectPercentUnitBean>> all_jobs_gsu = (HashMap<String, ArrayList<GradeSubjectPercentUnitBean>>) session.getAttribute("ALL_JOBS_GSU_BEANS");

			ArrayList<GradeSubjectPercentUnitBean> gsu_beans = null;
			GradeSubjectPercentUnitBean gsu = null;

			if (all_jobs_gsu == null) {
				all_jobs_gsu = new HashMap<String, ArrayList<GradeSubjectPercentUnitBean>>();
			}

			if (all_jobs_gsu.containsKey(job.getCompetitionNumber()))
				gsu_beans = (ArrayList<GradeSubjectPercentUnitBean>) all_jobs_gsu.get(job.getCompetitionNumber());

			if (gsu_beans == null)
				gsu_beans = new ArrayList<GradeSubjectPercentUnitBean>();

			if (!StringUtils.isEmpty(g) && !StringUtils.isEmpty(u))
				gsu_beans.add(new GradeSubjectPercentUnitBean(job.getCompetitionNumber(), GradeDB.getGrade(Integer.parseInt(g)), ((Integer.parseInt(s) != -1) ? SubjectDB.getSubject(Integer.parseInt(s))
						: null), Double.parseDouble(u)));

			all_jobs_gsu.put(job.getCompetitionNumber(), gsu_beans);

			session.setAttribute("ALL_JOBS_GSU_BEANS", all_jobs_gsu);

			// generate XML for candidate details.
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<GSU-BEANS>");
			for (Iterator<GradeSubjectPercentUnitBean> iter = gsu_beans.iterator(); iter.hasNext();) {
				gsu = iter.next();
				sb.append(gsu.toXML());
			}
			sb.append("</GSU-BEANS>");
			xml = sb.toString().replaceAll("&", "&amp;");

			System.out.println(xml);

			PrintWriter out = response.getWriter();

			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
			path = null;

		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			path = null;
		}

		return path;
	}
}