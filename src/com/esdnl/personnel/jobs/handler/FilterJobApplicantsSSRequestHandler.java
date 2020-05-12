package com.esdnl.personnel.jobs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.ApplicantFilterParametersSS;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.servlet.RequestHandlerImpl;
public class FilterJobApplicantsSSRequestHandler extends RequestHandlerImpl {

	public FilterJobApplicantsSSRequestHandler() {

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

		ApplicantFilterParametersSS params = new ApplicantFilterParametersSS();

		String[] degrees = null;
		String[] certs = null;
		String[] diplomas = null;

		try {

			if (session.getAttribute("JOB") != null) {
				opp = (JobOpportunityBean) session.getAttribute("JOB");
				path = "admin_view_job_applicants.jsp";
			}

			if ((opp == null)) {
				request.setAttribute("msg", "Choose a job to filter applicants.");
				path = "admin_index.jsp";
			}
			else {
				if (opp != null)
					params.setJob(opp);
				params.setCurrentEmployee(form.get("perm"));
				/**removed for now
				if (form.exists("sfilter"))
					params.setSenorityDateFilter(form.get("sfilter"));
				
				if (form.exists("sdate"))
					params.setSenorityDate(form.getDate("sdate"));

				if (form.exists("perm_position"))
					params.setCurrentPosition(form.get("perm_position"));
				
				if (form.exists("positiontype"))
					params.setCurrentPositionType(form.get("positiontype"));
				**/
				//if union code exists then we get union_code and position
				if (form.exists("union_code")){
					params.setUnionCode(form.getInt("union_code"));
					if (form.exists("perm_position")){
						params.setCurrentUnionPosition(form.getInt("perm_position"));
					}
				}
					
				
				
				if ((degrees = form.getArray("degrees")) != null) {
					if ((degrees.length == 1) && degrees[0].equals("0"))
						degrees = null;

					params.setDegrees(degrees);
				}
				
				if ((certs = form.getArray("dcert")) != null) {
					if ((certs.length == 1) && certs[0].equals("0"))
						certs = null;

					params.setCertificates(certs);
				}
				
				if ((diplomas = form.getArray("ddiploma")) != null) {
					if ((diplomas.length == 1) && diplomas[0].equals("0"))
						diplomas = null;

					params.setDiplomas(diplomas);
				}
				if (form.exists("da"))
					params.setDriversAbstract(true);
				if (form.exists("ohs"))
					params.setOhsTraining(true);
				if (form.exists("cc"))
					params.setCodeOfConduct(true);
				if (form.exists("vs"))
					params.setVulnerableSectorCheck(true);
				if (form.exists("fa"))
					params.setFirstAid(true);
				if (form.exists("whmis"))
					params.setWhmis(true);
				if (form.exists("dl"))
					params.setDriversLicense(true);

				profiles = ApplicantProfileManager.filterJobApplicantProfileBeanSS(params);

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