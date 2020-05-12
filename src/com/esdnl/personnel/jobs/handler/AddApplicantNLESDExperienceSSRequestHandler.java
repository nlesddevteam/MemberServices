package com.esdnl.personnel.jobs.handler;
import com.esdnl.personnel.jobs.bean.*;
import com.esdnl.personnel.jobs.dao.*;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.PersonnelApplicationRequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;
import java.io.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class AddApplicantNLESDExperienceSSRequestHandler extends PersonnelApplicationRequestHandlerImpl
{
	public AddApplicantNLESDExperienceSSRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("employed")
			});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		ApplicantProfileBean profile = null;
		if (validate_form()) {
			try
			{

				String employed = form.get("employed");
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
				profile = (ApplicantProfileBean) request.getSession().getAttribute("APPLICANT");
				if(profile == null)
				{
					path = "applicant_registration_step_1_ss.jsp";
				}
				else
				{
					path = "applicant_registration_step_2_ss.jsp"; // default path

					ApplicantNLESDExperienceSSBean exp = new ApplicantNLESDExperienceSSBean();
					exp.setSin(profile.getSIN());
					exp.setCurrentlyEmployed(employed);
					//fields that have been replaced for now
					//since value is no blank values
					exp.setSenorityDate(null);
					exp.setSenorityStatus("");
					//not used anymore
					exp.setPosition1("");
					exp.setPosition1School(-1);
					exp.setPosition1Hours("");
					exp.setPosition2("");
					exp.setPosition2School(-1);
					exp.setPosition2Hours("");

					if(request.getParameter("op") == null){
						ApplicantNLESDExperienceSSManager.addApplicantNLESDExperienceSS(exp);
						request.setAttribute("msg", "Profile added successfully.");
						path = "applicant_registration_step_3_ss.jsp";
					}else{
						exp.setId(form.getInt("id"));
						ApplicantNLESDExperienceSSManager.updateApplicantNLESDExperienceSS(exp);
						request.setAttribute("msg", "Profile updated successfully.");
						path = "applicant_registration_step_3_ss.jsp";
					}
					//now we check to see if they are adding a current position and save that
					if(request.getParameter("hidadd").equals("ADDNEW")){
						if (form.getInt("perm_school") == 0) {
							request.setAttribute("errmsg", "Please select work location.");
							path = "applicant_registration_step_2_ss.jsp";
						}else if(form.getInt("union_code") <= 0) {
							request.setAttribute("errmsg", "Please select position union.");
							path = "applicant_registration_step_2_ss.jsp";
						}else if(form.getInt("perm_position") <= 0) {
							request.setAttribute("errmsg", "Please select position held.");
							path = "applicant_registration_step_2_ss.jsp";
						}else if(StringUtils.isEmpty(form.get("positiontype"))) {
							request.setAttribute("errmsg", "Please select position type.");
							path = "applicant_registration_step_2_ss.jsp";
						}else if(StringUtils.isEmpty(form.get("position_hours"))) {
							request.setAttribute("errmsg", "Please specify position hours.");
							path = "applicant_registration_step_2_ss.jsp";
						}else if(StringUtils.isEmpty(form.get("sdate"))) {
							request.setAttribute("errmsg", "Please specify sdate.");
							path = "applicant_registration_step_2_ss.jsp";
						}else {
							ApplicantCurrentPositionBean abean = new ApplicantCurrentPositionBean();
							abean.setSin(profile.getSIN());
							abean.setSchoolId(form.getInt("perm_school"));
							abean.setPositionHeld(form.getInt("perm_position"));
							abean.setPositionHours(form.get("position_hours"));
							abean.setPositionType(form.get("positiontype"));
							String startdate=form.get("sdate");
							if(StringUtils.isEmpty(startdate)){
								abean.setStartDate(null);
							}else{
								abean.setStartDate(sdf.parse(startdate));
							}
							String enddate=form.get("edate");
							if(StringUtils.isEmpty(enddate)){
								abean.setEndDate(null);
							}else{
								abean.setEndDate(sdf.parse(enddate));
							}
							ApplicantCurrentPositionManager.addApplicantCurrentPositionBean(abean);
							request.setAttribute("msg", "Profile updated successfully.");
							path = "applicant_registration_step_2_ss.jsp";
						}
					}

					request.getSession(true).setAttribute("APPLICANT", profile);
					request.setAttribute("unioncodes", RequestToHireManager.getUnionCodes());
				}
			}catch(Exception e)
			{
				e.printStackTrace();
				request.setAttribute("errmsg", "Could not add applicant profile nlesd experience.");
				path = "applicant_registration_step_2_ss.jsp";
			}
		}else {
			request.setAttribute("errmsg", this.validator.getErrorString());
			path = "applicant_registration_step_2_ss.jsp";
		}
		return path;
	}
}
