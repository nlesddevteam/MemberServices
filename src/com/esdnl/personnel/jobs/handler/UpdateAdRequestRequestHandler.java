package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.AdRequestBean;
import com.esdnl.personnel.jobs.constants.JobTypeConstant;
import com.esdnl.personnel.jobs.constants.TrainingMethodConstant;
import com.esdnl.personnel.jobs.dao.AdRequestManager;
import com.esdnl.personnel.v2.database.sds.EmployeeManager;
import com.esdnl.personnel.v2.database.sds.LocationManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class UpdateAdRequestRequestHandler extends RequestHandlerImpl {

	public UpdateAdRequestRequestHandler() {

		requiredPermissions = new String[] {
				"PERSONNEL-ADREQUEST-UPDATE"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("request_id", "ID required for update")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);
		if (validate_form()) {
			try {

				// SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				AdRequestBean req = new AdRequestBean();
				req = AdRequestManager.getAdRequestBean(form.getInt("request_id"));
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

				if (req.getTitle() == null) {
					request.setAttribute("msg", "Please enter title.");
					request.setAttribute("AD_REQUEST", req);
				}
				else if (req.getLocation() == null) {
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
						//update request
						AdRequestManager.updateAdRequestBeanDetails(req);
						request.setAttribute("msg", "Ad Request updated successfully.");
						request.setAttribute("AD_REQUEST", req);
						path = "admin_view_ad_request.jsp";
					}
					catch (Exception e) {
						e.printStackTrace(System.err);
						request.setAttribute("msg", "Ad requested submitted, supervisory email not sent.");
						request.setAttribute("AD_REQUEST", req);
					}
				}

			}
			catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", e.getMessage());
				path = "request_ad.jsp";
			}
		}
		else {
			request.setAttribute("errmsg", this.validator.getErrorString());
			path = "admin_index.jsp";
		}

		return path;
	}
}
