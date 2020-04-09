package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.mail.bean.EmailBean;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.school.SchoolDB;
import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.esdnl.personnel.jobs.bean.AdRequestBean;
import com.esdnl.personnel.jobs.bean.AdRequestHistoryBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityAssignmentBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.NLESDRegionalMailHelperBean;
import com.esdnl.personnel.jobs.constants.JobTypeConstant;
import com.esdnl.personnel.jobs.constants.RequestStatus;
import com.esdnl.personnel.jobs.constants.TrainingMethodConstant;
import com.esdnl.personnel.jobs.dao.AdRequestManager;
import com.esdnl.personnel.v2.database.sds.EmployeeManager;
import com.esdnl.personnel.v2.database.sds.LocationManager;
import com.esdnl.util.StringUtils;
import com.esdnl.velocity.VelocityUtils;

public class ApproveAdRequestRequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		String path;
		HttpSession session = null;
		User usr = null;

		session = request.getSession(false);
		if ((session != null) && (session.getAttribute("usr") != null)) {
			usr = (User) session.getAttribute("usr");
			if (!(usr.getUserPermissions().containsKey("PERSONNEL-ADREQUEST-APPROVE"))) {
				throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
			}
		}
		else {
			throw new SecurityException("User login required.");
		}

		try {
			AdRequestBean req = AdRequestManager.getAdRequestBean(Integer.parseInt(request.getParameter("request_id")));

			if (req != null) {
				String title = request.getParameter("ad_title");
				String location = request.getParameter("location");
				String owner = request.getParameter("owner");
				String units = request.getParameter("ad_unit");
				String[] majors = request.getParameterValues("ad_major");
				String trnmtd = request.getParameter("ad_trnmtd");
				String reason = request.getParameter("vacancy_reason");
				String start_date = request.getParameter("start_date");
				String end_date = request.getParameter("end_date");
				String job_type = request.getParameter("ad_job_type");
				String ad_text = request.getParameter("ad_text");
				String[] degrees = request.getParameterValues("ad_degree");
				String[] minors = request.getParameterValues("ad_minor");

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

				if (!StringUtils.isEmpty(title))
					req.setTitle(title);

				if (!StringUtils.isEmpty(location) && !location.equals("-1"))
					req.setLocation(LocationManager.getLocationBeanByDescription(location));

				if (!StringUtils.isEmpty(owner) && !owner.equals("-1"))
					req.setOwner(EmployeeManager.getEmployeeBean(owner));

				if (!StringUtils.isEmpty(units))
					req.setUnits(Double.parseDouble(units));

				if (!StringUtils.isEmpty(job_type))
					req.setJobType(JobTypeConstant.get(Integer.parseInt(job_type)));
				else
					req.setJobType(JobTypeConstant.REGULAR);

				if ((majors != null) && (majors.length > 0))
					req.setMajors(majors);

				if ((minors != null) && (minors.length > 0))
					req.setMinors(minors);

				if ((degrees != null) && (degrees.length > 0))
					req.setDegrees(degrees);

				if (!StringUtils.isEmpty(trnmtd))
					req.setTrainingMethod(TrainingMethodConstant.get(Integer.parseInt(trnmtd)));

				req.setVacancyReason(reason);

				if (!StringUtils.isEmpty(start_date))
					req.setStartDate(sdf.parse(start_date));

				if (!StringUtils.isEmpty(end_date))
					req.setEndDate(sdf.parse(end_date));
				else
					req.setEndDate(null);

				if (!StringUtils.isEmpty(ad_text))
					req.setAdText(ad_text);

				String op = request.getParameter("op");
				if (op.equals("GET_EMPLOYEES")) {
					if (StringUtils.isEmpty(location)) {
						request.setAttribute("msg", "Please select LOCATION.");
					}
					else {
						request.setAttribute("AD_REQUEST", req);
					}
				}
				else if (op.equals("APPROVED")) {
					if (req.getLocation() == null)
						request.setAttribute("msg", "Please select LOCATION.");
					else if (req.getStartDate() == null)
						request.setAttribute("msg", "Please select START DATE.");
					else {
						try {
							//update the request
							AdRequestManager.updateAdRequestBeanDetails(req);
							// add the request to db
							AdRequestManager.approveAdRequestBean(req, usr.getPersonnel());
							
							AdRequestHistoryBean history = req.getHistory(RequestStatus.SUBMITTED);
							// send email to admins
							// map SDS location ids to my location ids
							int location_id = 0;

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
							default:

								location_id = SchoolDB.getSchoolFromDeptId(
										Integer.parseInt(req.getLocation().getLocationId().trim()) % 1000).getSchoolID();
							}

							if (location_id == 0)
								throw new JobOpportunityException("Ad could not be posted, location does not have a dept. id assigned.");

							JobOpportunityAssignmentBean ass = new JobOpportunityAssignmentBean(null, location_id, 0.0);

							NLESDRegionalMailHelperBean mh = new NLESDRegionalMailHelperBean(ass.getLocationZone().getZoneId());

							EmailBean ebean = new EmailBean();
							ebean.setTo(history.getPersonnel().getEmailAddress());
							ebean.setSubject("Job Ad Request Approved: " + req.getLocation().getLocationDescription() + " - "
									+ req.getTitle());
							HashMap<String, Object> model = new HashMap<String, Object>();
							// set values to be used in template
							model.put("requesterName", usr.getPersonnel().getFullNameReverse());
							model.put("competitionTitle", req.getTitle());
							ebean.setBody(VelocityUtils.mergeTemplateIntoString("personnel/job_ad_request_approved_sub.vm", model));
							ebean.setFrom("ms@nlesd.ca");
							ebean.send();

							ArrayList<Personnel> sendTo = new ArrayList<Personnel>();

							sendTo.addAll(Arrays.asList(mh.getRegionalHRAdmins()));
							sendTo.addAll(Arrays.asList(PersonnelDB.getPersonnelByPermission("PERSONNEL-ADREQUEST-POST")));

							for (Personnel p : sendTo) {

								ebean.setTo(p.getEmailAddress());
								ebean.setSubject("Job Ad Request Approved: " + req.getLocation().getLocationDescription() + " - "
										+ req.getTitle());
								// set values to be used in template
								model.put("requesterName", usr.getPersonnel().getFullNameReverse());
								model.put("competitionTitle", req.getTitle());
								model.put("userName", p.getUserName());
								model.put("recommendationId", req.getId());
								ebean.setBody(VelocityUtils.mergeTemplateIntoString("personnel/job_ad_request_approved.vm", model));
								ebean.setFrom("ms@nlesd.ca");
								ebean.send();

							}
							request.setAttribute("msg", "Ad Request approved successfully.");
						}
						catch (JobOpportunityException e) {
							e.printStackTrace(System.err);
							request.setAttribute("msg", "Ad requested could not be approved.");
						}
						catch (Exception e) {
							e.printStackTrace(System.err);
							request.setAttribute("msg", "Ad requested approved, requester email not sent.");
						}
					}
				}
				path = "admin_list_ad_requests.jsp";
			}
			else {
				path = "admin_list_ad_requests.jsp";
				request.setAttribute("msg", "REQUEST NOT FOUND.");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			path = "admin_list_ad_requests.jsp";
		}

		return path;
	}
}