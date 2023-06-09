package com.esdnl.personnel.jobs.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
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
import com.esdnl.personnel.jobs.bean.TeacherAllocationBean;
import com.esdnl.personnel.jobs.bean.TeacherAllocationVacantPositionBean;
import com.esdnl.personnel.jobs.constants.EmploymentConstant;
import com.esdnl.personnel.jobs.constants.JobTypeConstant;
import com.esdnl.personnel.jobs.constants.RequestStatus;
import com.esdnl.personnel.jobs.constants.TrainingMethodConstant;
import com.esdnl.personnel.jobs.dao.AdRequestManager;
import com.esdnl.personnel.jobs.dao.TeacherAllocationManager;
import com.esdnl.personnel.jobs.dao.TeacherAllocationVacantPositionManager;
import com.esdnl.personnel.v2.database.sds.EmployeeManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormElementPattern;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.servlet.RequiredPatternFormElement;
import com.esdnl.servlet.RequiredSelectionFormElement;
import com.esdnl.util.StringUtils;
import com.esdnl.velocity.VelocityUtils;

public class AddUpdateTeacherAllocationVacantPositionAjaxRequestHandler extends RequestHandlerImpl {

	public AddUpdateTeacherAllocationVacantPositionAjaxRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("allocationid", "Allocation ID missing."),
				new RequiredFormElement("jobdesc", "Job description required."),
				new RequiredSelectionFormElement("jobtype", -1, "Type selection required."),
				new RequiredFormElement("reason", "Vacancy reason required."),
				new RequiredFormElement("unit", "Unit required."),
				new RequiredPatternFormElement("unit", FormElementPattern.TWO_DECIMALS_PATTERN, "Units invalid format (x.xx).")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			try {
				String msg = "";
				TeacherAllocationVacantPositionBean position = null;

				if (!form.exists("positionid")) {
					position = new TeacherAllocationVacantPositionBean();

					position.setAllocationId(form.getInt("allocationid"));
					position.setJobDescription(form.get("jobdesc"));
					position.setType(EmploymentConstant.get(form.getInt("jobtype")));

					if (!form.hasValue("empid", "0"))
						position.setEmployee(EmployeeManager.getEmployeeBean(form.get("empid").trim()));
					else
						position.setEmployee(null);

					position.setVacancyReason(form.get("reason"));

					if (form.exists("termstart"))
						position.setTermStart(form.getDate("termstart"));
					else
						position.setTermStart(null);

					if (form.exists("termend"))
						position.setTermEnd(form.getDate("termend"));
					else
						position.setTermEnd(null);

					position.setUnit(form.getDouble("unit"));
					position.setAdvertised(form.getBoolean("advertised"));
					position.setFilled(form.getBoolean("filled"));

					TeacherAllocationVacantPositionManager.addTeacherAllocationVacantPositionBean(position);
					
					TeacherAllocationBean tab = TeacherAllocationManager.getTeacherAllocationBean(form.getInt("allocationid"));
					//now we add the job ad
					
					AdRequestBean adbean = new AdRequestBean();
					adbean.setTitle(position.getJobDescription());
					adbean.setLocation(tab.getLocation());
					adbean.setVacancyReason(position.getVacancyReason());
					if(position.getTermStart() ==  null) {
						//ad request expecting a start date
						//default to first day of september, can be adjusted
						Calendar cal = Calendar.getInstance();
						String[] ssplit = tab.getSchoolYear().split("-");
						cal.set(Calendar.YEAR,Integer.parseInt(ssplit[0]));
						cal.set(Calendar.MONTH,8);
						cal.set(Calendar.DAY_OF_MONTH,1);
						adbean.setStartDate(cal.getTime());
					}else {
						adbean.setStartDate(position.getTermStart());
					}
					adbean.setEndDate(position.getTermEnd());
					adbean.setUnits(position.getUnit());
					StringBuilder sb = new StringBuilder();
					sb.append("Auto-generated by Position Planner");
					sb.append("<br /><br />");
					sb.append(position.getJobDescription());
					adbean.setAdText(sb.toString());
					adbean.setOwner(position.getEmployee());
					adbean.setJobType(JobTypeConstant.REGULAR);
					adbean.setTrainingMethod(TrainingMethodConstant.PRIMARY_ELEMENTARY);
					AdRequestManager.addAdRequestBean(adbean,usr.getPersonnel());
					TeacherAllocationVacantPositionManager.updateTeacherAllocationVacantPositionAdRequest(adbean.getId(),position.getPositionId());
					
					//now we update the position with the ad request id
					//TeacherAllocationVacantPositionManager.updateTeacherAllocationVacantPositionAdRequest(adbean.getId(), position.getPositionId());
					//send email about new ad request
					
					try {

						// add the request to db
						// send email to admins
						// map SDS location ids to my location ids
						int location_id = 0;

						switch (Integer.parseInt(adbean.getLocation().getLocationId().trim())) {
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
									Integer.parseInt(adbean.getLocation().getLocationId().trim()) % 1000).getSchoolID();
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
							model.put("recommendationId", adbean.getId());
							// send email to principal
							ebean.setTo(p.getEmailAddress());
							ebean.setBody(VelocityUtils.mergeTemplateIntoString("personnel/job_ad_request_approval.vm", model));
							ebean.setFrom("ms@nlesd.ca");
							//ebean.send();
						}
					}catch(Exception ee) {
						
					}

					msg = "Vacant position added successfully.";
				}
				else {
					position = TeacherAllocationVacantPositionManager.getTeacherAllocationVacantPositionBean(form.getInt("positionid"));

					position.setJobDescription(form.get("jobdesc"));
					position.setType(EmploymentConstant.get(form.getInt("jobtype")));

					if (!form.hasValue("empid", "0"))
						position.setEmployee(EmployeeManager.getEmployeeBean(form.get("empid").trim()));
					else
						position.setEmployee(null);

					position.setVacancyReason(form.get("reason"));

					if (form.exists("termstart"))
						position.setTermStart(form.getDate("termstart"));
					else
						position.setTermStart(null);

					if (form.exists("termend"))
						position.setTermEnd(form.getDate("termend"));
					else
						position.setTermEnd(null);

					position.setUnit(form.getDouble("unit"));
					position.setAdvertised(form.getBoolean("advertised"));
					position.setFilled(form.getBoolean("filled"));

					TeacherAllocationVacantPositionManager.updateTeacherAllocationVacantPositionBean(position);
					
					if(position.getAdRequest() != null) {
						AdRequestBean adbean = position.getAdRequest();
						//only update if in submitted mode
						if(adbean.getCurrentStatus() == RequestStatus.SUBMITTED) {
							//if position is filled then
							//we cancel the ad which removes udpates the ad_request_id in vacaancy to null
							if(position.isFilled()) {
									AdRequestManager.cancelAdRequestBean(position.getAdRequest().getId(), usr.getPersonnel().getPersonnelID());
								
							}else {
								TeacherAllocationBean tab = TeacherAllocationManager.getTeacherAllocationBean(position.getAllocationId());
								adbean.setTitle(position.getJobDescription());
								adbean.setLocation(tab.getLocation());
								adbean.setVacancyReason(position.getVacancyReason());
								if(position.getTermStart() ==  null) {
									//ad request expecting a start date
									//default to first day of september, can be adjusted
									Calendar cal = Calendar.getInstance();
									String[] ssplit = tab.getSchoolYear().split("-");
									cal.set(Calendar.YEAR,Integer.parseInt(ssplit[0]));
									cal.set(Calendar.MONTH,8);
									cal.set(Calendar.DAY_OF_MONTH,1);
									adbean.setStartDate(cal.getTime());
								}else {
									adbean.setStartDate(position.getTermStart());
								}
								adbean.setEndDate(position.getTermEnd());
								adbean.setUnits(position.getUnit());
								StringBuilder sb = new StringBuilder();
								sb.append("Auto-generated by Position Planner");
								sb.append("<br /><br />");
								sb.append(position.getJobDescription());
								adbean.setAdText(sb.toString());
								adbean.setOwner(position.getEmployee());
								adbean.setJobType(JobTypeConstant.REGULAR);
								adbean.setTrainingMethod(TrainingMethodConstant.PRIMARY_ELEMENTARY);
								AdRequestManager.updateAdRequestBeanDetails(adbean);
							}
							
						}

					}
					
					
					msg = "Vacant position updated successfully.";
				}

				TeacherAllocationBean allocation = TeacherAllocationManager.getTeacherAllocationBean(form.getInt("allocationid"));

				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<ADD-TEACHER-ALLOCATION-VACANT-POSITION-RESPONSE msg='" + msg + "'>");
				if (allocation != null)
					sb.append(allocation.toXML());
				sb.append("</ADD-TEACHER-ALLOCATION-VACANT-POSITION-RESPONSE>");

				xml = StringUtils.encodeXML(sb.toString());

				System.out.println(xml);

				PrintWriter out = response.getWriter();

				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			}
			catch (Exception e) {
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");

				sb.append("<ADD-TEACHER-ALLOCATION-VACANT-POSITION-RESPONSE msg='Permanent position could not be added/updated.<br />"
						+ StringUtils.encodeHTML2(e.getMessage()) + "' />");

				xml = StringUtils.encodeXML(sb.toString());

				System.out.println(xml);

				PrintWriter out = response.getWriter();

				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			}
		}
		else {
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");

			sb.append("<ADD-TEACHER-ALLOCATION-VACANT-POSITION-RESPONSE msg='" + this.validator.getErrorString() + "' />");

			xml = StringUtils.encodeXML(sb.toString());

			System.out.println(xml);

			PrintWriter out = response.getWriter();

			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
		}

		return null;
	}

}
