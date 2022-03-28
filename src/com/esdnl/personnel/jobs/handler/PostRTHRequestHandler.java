package com.esdnl.personnel.jobs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.awsd.school.SchoolDB;
import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.esdnl.personnel.jobs.bean.JobOpportunityAssignmentBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.RequestToHireBean;
import com.esdnl.personnel.jobs.dao.RequestToHireManager;
public class PostRTHRequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		String path;
		HttpSession session = null;
		User usr = null;

		session = request.getSession(false);
		if ((session != null) && (session.getAttribute("usr") != null)) {
			usr = (User) session.getAttribute("usr");
			if (!(usr.getUserPermissions().containsKey("PERSONNEL-ADREQUEST-POST"))) {
				throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
			}
		}
		else {
			throw new SecurityException("User login required.");
		}

		try {
			RequestToHireBean rbean = RequestToHireManager.getRequestToHireById(Integer.parseInt(request.getParameter("rid")));

			if (rbean != null) {
				JobOpportunityBean opp = new JobOpportunityBean();

				opp.setPositionTitle(rbean.getJobTitle());
				opp.setJobAdText(rbean.getComments());
				

				int location_id = 0;

				// map SDS location ids to my location ids
				switch (Integer.parseInt(rbean.getWorkLocation())) {
				case 0: // District Office
					location_id = -999;
					break;
				case 4007: // Burin Satellite Office
					location_id = -300;
					break;
				case 449: // St. Augustine's Primary
					location_id = 188;
					break;
				case 455: // Janeway Hospital School
					location_id = 270;
					break;
				case 1000: //Labrador Regional Office
					location_id = -1000;
					break;
				case 1009: // Avalon West Satellite Office
					location_id = -200;
					break;
				case 2008: // Vista Satellite Office
					location_id = -400;
					break;
				case 2000: //Western Regional Office
					location_id = -2000;
					break;
				case 3000: //Nova Central Regional Office
					location_id = -3000;
					break;
				case 3001: //Nova Central Regional Office
					location_id = -3001;
					break;
				case 5000: //District Conference Centre
					location_id = 220;
					break;
				case 666: //District Conference Centre
					location_id = 846;
					break;
				default:

					location_id = SchoolDB.getSchoolFromDeptId(Integer.parseInt(rbean.getWorkLocation().trim()) % 1000).getSchoolID();
				}

				if (location_id == 0)
					throw new JobOpportunityException("Ad could not be posted, location does not have a dept. id assigned.");

				JobOpportunityAssignmentBean ass = new JobOpportunityAssignmentBean(null, location_id, 0.0);
				opp.add(ass);
				opp.setIsSupport("Y");
				request.setAttribute("JOB_OPP", opp);
				request.setAttribute("RTH", rbean);

				path = "admin_post_job.jsp";
			}
			else {
				path = "adminViewRequestsToHire.html?status=5";
				request.setAttribute("msg", "REQUEST NOT FOUND.");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			path = "adminViewRequestsToHire.html?status=5";
		}

		return path;
	}
}
