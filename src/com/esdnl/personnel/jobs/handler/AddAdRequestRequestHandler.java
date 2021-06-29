package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.mail.bean.EmailBean;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.school.SchoolDB;
import com.esdnl.personnel.jobs.bean.AdRequestBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityAssignmentBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.NLESDRegionalMailHelperBean;
import com.esdnl.personnel.jobs.constants.JobTypeConstant;
import com.esdnl.personnel.jobs.constants.TrainingMethodConstant;
import com.esdnl.personnel.jobs.dao.AdRequestManager;
import com.esdnl.personnel.v2.database.sds.EmployeeManager;
import com.esdnl.personnel.v2.database.sds.LocationManager;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.util.StringUtils;
import com.esdnl.velocity.VelocityUtils;

public class AddAdRequestRequestHandler extends RequestHandlerImpl {

	public AddAdRequestRequestHandler() {

		requiredPermissions = new String[] {
				"PERSONNEL-ADREQUEST-REQUEST"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			String op = request.getParameter("op");

			if (!StringUtils.isEmpty(op)) {
				// String title = request.getParameter("ad_title");
				// String location = request.getParameter("location");
				// String owner = request.getParameter("owner");
				// String units = request.getParameter("ad_unit");

				// String trnmtd = request.getParameter("ad_trnmtd");
				// String reason = request.getParameter("vacancy_reason");
				// String start_date = request.getParameter("start_date");
				// String end_date = request.getParameter("end_date");
				// String job_type = request.getParameter("ad_job_type");
				// String ad_text = request.getParameter("ad_text");

				// SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				AdRequestBean req = new AdRequestBean();

				if (!StringUtils.isEmpty(form.get("ad_title")))
					req.setTitle(form.get("ad_title"));

				if (!StringUtils.isEmpty(form.get("location")) && !form.hasValue("location", "-1"))
					req.setLocation(LocationManager.getLocationBeanByDescription(form.get("location")));

				if (!StringUtils.isEmpty(form.get("owner")) && !form.hasValue("owner", "-1"))
					req.setOwner(EmployeeManager.getEmployeeBean(form.get("owner")));

				if (!StringUtils.isEmpty(form.get("ad_unit")))
					req.setUnits(form.getDouble("ad_unit"));

				if (!StringUtils.isEmpty(form.get("ad_job_type")) && !form.hasValue("ad_job_type", "-1"))
					req.setJobType(JobTypeConstant.get(form.getInt("ad_job_type")));
				else
					req.setJobType(JobTypeConstant.REGULAR);

				String[] majors = form.getArray("ad_major");
				if ((majors != null) && (majors.length > 0))
					req.setMajors(majors);

				String[] minors = form.getArray("ad_minor");
				if ((minors != null) && (minors.length > 0))
					req.setMinors(minors);

				String[] degrees = form.getArray("ad_degree");
				if ((degrees != null) && (degrees.length > 0))
					req.setDegrees(degrees);

				if (!StringUtils.isEmpty(form.get("ad_trnmtd")))
					req.setTrainingMethod(TrainingMethodConstant.get(form.getInt("ad_trnmtd")));

				req.setVacancyReason(form.get("vacancy_reason"));

				if (!StringUtils.isEmpty(form.get("start_date")))
					req.setStartDate(form.getDate("start_date"));

				if (!StringUtils.isEmpty(form.get("end_date")))
					req.setEndDate(form.getDate("end_date"));

				if (!StringUtils.isEmpty(form.get("ad_text")))
					req.setAdText(form.get("ad_text"));

				req.setUnadvertised(!StringUtils.isEmpty(form.get("is_unadvertised")));
				req.setAdminPool(!StringUtils.isEmpty(form.get("chk-is-admin-pool")));
				req.setLeadershipPool(!StringUtils.isEmpty(form.get("chk-is-leadership-pool")));

				if (op.equals("GET_EMPLOYEES")) {
					if (StringUtils.isEmpty(form.get("location"))) {
						request.setAttribute("msg", "Please select LOCATION.");
					}
					else {
						request.setAttribute("AD_REQUEST", req);
					}
				}
				else if (op.equals("ADD_REQUEST")) {
					System.out.println("ADDING REQUEST...");

					if (req.getLocation() == null) {
						request.setAttribute("msg", "Please select LOCATION.");
						request.setAttribute("AD_REQUEST", req);
					}
					else if (StringUtils.isEmpty(req.getVacancyReason())) {
						request.setAttribute("msg", "Please enter VACANCY REASON.");
						request.setAttribute("AD_REQUEST", req);
					}
					else if (req.getStartDate() == null) {
						request.setAttribute("msg", "Please select START DATE.");
						request.setAttribute("AD_REQUEST", req);
					}
					else if (req.getJobType().equal(JobTypeConstant.REPLACEMENT) && req.getEndDate() == null) {
						request.setAttribute("msg",
								"END DATE is required for all " + JobTypeConstant.REPLACEMENT.getDescription() + " positions");
						request.setAttribute("AD_REQUEST", req);
					}
					else {
						try {

							// add the request to db
							AdRequestManager.addAdRequestBean(req, usr.getPersonnel());
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
							case 3001: //Nova Central Regional Office
								location_id = -3001;
								break;
							default:

								location_id = SchoolDB.getSchoolFromDeptId(
										Integer.parseInt(req.getLocation().getLocationId().trim()) % 1000).getSchoolID();
							}

							if (location_id == 0)
								throw new JobOpportunityException("Ad could not be posted, location does not have a dept. id assigned.");

							JobOpportunityAssignmentBean ass = new JobOpportunityAssignmentBean(null, location_id, 0.0);

							NLESDRegionalMailHelperBean mh = new NLESDRegionalMailHelperBean(ass.getLocationZone().getZoneId());

							ArrayList<Personnel> to = new ArrayList<Personnel>();

							to.addAll(Arrays.asList(mh.getRegionalHRAdmins()));
							to.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("AD HR")));
							to.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("ADMINISTRATOR")));

							to.remove(usr.getPersonnel());

							//email  region hr seo
							for (Personnel p : to) {
								EmailBean ebean = new EmailBean();
								HashMap<String, Object> model = new HashMap<String, Object>();
								ebean.setSubject("Job Ad Request Pending Approval for " + usr.getPersonnel().getFullNameReverse());
								// set values to be used in template
								model.put("requesterName", usr.getPersonnel().getFullNameReverse());
								model.put("userName", p.getUserName());
								model.put("recommendationId", req.getId());
								// send email to principal
								ebean.setTo(p.getEmailAddress());
								ebean.setBody(VelocityUtils.mergeTemplateIntoString("personnel/job_ad_request_approval.vm", model));
								ebean.setFrom("ms@nlesd.ca");
								ebean.send();
							}

							request.setAttribute("msg", "Ad Request submitted successfully.");
						}
						catch (JobOpportunityException e) {
							e.printStackTrace(System.err);
							request.setAttribute("msg", "Ad requested could not be submitted.");
							request.setAttribute("AD_REQUEST", req);
						}
						catch (Exception e) {
							e.printStackTrace(System.err);
							request.setAttribute("msg", "Ad requested submitted, supervisory email not sent.");
							request.setAttribute("AD_REQUEST", req);
						}
					}
				}
				else
					request.setAttribute("msg", "INVALID OPERATION.");

				path = "request_ad.jsp";
			}
			else
				path = "request_ad.jsp";
		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			path = "request_ad.jsp";
		}

		return path;
	}
}