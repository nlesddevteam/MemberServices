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
import com.esdnl.personnel.jobs.bean.AdRequestBean;
import com.esdnl.personnel.jobs.bean.AssignmentEducationBean;
import com.esdnl.personnel.jobs.bean.AssignmentMajorMinorBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityAssignmentBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.RequestToHireBean;
import com.esdnl.personnel.jobs.constants.JobTypeConstant;
import com.esdnl.personnel.jobs.constants.RequestStatus;
import com.esdnl.personnel.jobs.dao.AdRequestManager;
import com.esdnl.personnel.jobs.dao.RequestToHireManager;


public class PostAdRequestRequestHandler implements RequestHandler {

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
			
			
			if(request.getParameter("issupport") ==  null){
				AdRequestBean req = AdRequestManager.getAdRequestBean(Integer.parseInt(request.getParameter("request_id")));

				if (req != null) {
					JobOpportunityBean opp = new JobOpportunityBean();

					opp.setPositionTitle(req.getTitle());
					opp.setJobAdText(req.getAdText());

					opp.setJobType(req.getJobType());

					int location_id = 0;

					// map SDS location ids to my location ids
					switch (Integer.parseInt(req.getLocation().getLocationId().trim())) {
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
					case 5000: //District Conference Centre
						location_id = 220;
						break;
					default:

						location_id = SchoolDB.getSchoolFromDeptId(Integer.parseInt(req.getLocation().getLocationId().trim()) % 1000).getSchoolID();
					}

					if (location_id == 0)
						throw new JobOpportunityException("Ad could not be posted, location does not have a dept. id assigned.");

					JobOpportunityAssignmentBean ass = new JobOpportunityAssignmentBean(null, location_id, 0.0);
					opp.add(ass);

					if ((req.getDegrees() != null) && (req.getDegrees().length > 0)) {
						for (int i = 0; i < req.getDegrees().length; i++)
							ass.addRequiredEducation(new AssignmentEducationBean(req.getDegrees()[i].getAbbreviation()));
					}

					if ((req.getMajors() != null) && (req.getMajors().length > 0)) {
						for (int i = 0; i < req.getMajors().length; i++)
							ass.addRequiredMajor(new AssignmentMajorMinorBean(req.getMajors()[i].getSubjectID(), -1));
					}

					if ((req.getMinors() != null) && (req.getMinors().length > 0)) {
						for (int i = 0; i < req.getMinors().length; i++)
							ass.addRequiredMajor(new AssignmentMajorMinorBean(-1, req.getMinors()[i].getSubjectID()));
					}

					ass.addRequiredTrainingMethod(req.getTrainingMethod());
					opp.setIsSupport("N");
					request.setAttribute("JOB_OPP", opp);
					request.setAttribute("AD_REQUEST", req);

					path = "admin_post_job.jsp";
				}
				else {
					path = "admin_list_ad_requests.jsp?status=" + RequestStatus.SUBMITTED.getId();
					request.setAttribute("msg", "REQUEST NOT FOUND.");
				}
				
			}else{
				RequestToHireBean req = RequestToHireManager.getRequestToHireById(Integer.parseInt(request.getParameter("rid")));

				if (req != null) {
					JobOpportunityBean opp = new JobOpportunityBean();

					opp.setPositionTitle(req.getJobTitle());
					opp.setJobAdText(req.getComments());

					opp.setJobType(JobTypeConstant.INTERNALONLY);

					int location_id = 0;

					// map SDS location ids to my location ids
					switch (Integer.parseInt(req.getWorkLocation().trim())) {
					case 0: // District Office
						location_id = -999;
						break;
					case 4007: // Burin Satellite Office
						location_id = 285;
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
					case 5000: //District Conference Centre
						location_id = 220;
						break;
					case 3034: //Western Baie Verte BusDep
						location_id = 832;
						break;
					case 3032: //Nova Central Fogo Bus Depot
						location_id = 833;
						break;
					case 3035: //Nova Central Gander Bus/Maint
						location_id = 834;
						break;
					case 3036: //Nova Central GF-Windsor Bus
						location_id = 835;
						break;
					case 3031: //Nova Central Lewisporte BusDep
						location_id = 836;
						break;
					case 3030: //Nova Central Summerford BusDep
						location_id = 837;
						break;
					case 2001: //Western Bus Depot
						location_id = 838;
						break;
					case 1001: //Labrador HVGB Bus Depot
						location_id = 839;
						break;
					case 1002: //Labrador Wabush Bus Depot
						location_id = 840;
						break;							
					default:

						location_id = SchoolDB.getSchoolFromDeptId(Integer.parseInt(req.getWorkLocation().trim()) % 1000).getSchoolID();
					}

					if (location_id == 0)
						throw new JobOpportunityException("Ad could not be posted, location does not have a dept. id assigned.");

					JobOpportunityAssignmentBean ass = new JobOpportunityAssignmentBean(null, location_id, 0.0);
					opp.add(ass);

					

				
					opp.setIsSupport("S");
					request.setAttribute("JOB_OPP", opp);
					request.setAttribute("RTH", req);

					path = "admin_post_job.jsp";
				}
				else {
					path = "admin_list_ad_requests.jsp?status=" + RequestStatus.SUBMITTED.getId();
					request.setAttribute("msg", "REQUEST NOT FOUND.");
				}
			}
				

		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			path = "admin_list_ad_requests.jsp?status=" + RequestStatus.SUBMITTED.getId();
		}

		return path;
	}
}