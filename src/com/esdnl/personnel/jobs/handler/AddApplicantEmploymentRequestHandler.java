package com.esdnl.personnel.jobs.handler;
import com.esdnl.util.*;
import com.esdnl.personnel.jobs.bean.*;
import com.esdnl.personnel.jobs.dao.*;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.PersonnelApplicationRequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import java.io.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class AddApplicantEmploymentRequestHandler extends PersonnelApplicationRequestHandlerImpl {
	public AddApplicantEmploymentRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("company", "Please specify company"),
				new RequiredFormElement("address", "Please specify address"),
				new RequiredFormElement("phonenumber","Please specify phone number"),
				new RequiredFormElement("supervisor","Please specify supervisor"),
				new RequiredFormElement("jobtitle","Please specify job title"),
				new RequiredFormElement("duties","Please specify duties"),
				new RequiredFormElement("fromdate","Please specify from date"),
				new RequiredFormElement("todate","Please specify to date")
		});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		String path;
		SimpleDateFormat sdf = null;
		ApplicantProfileBean profile = null;
		if (validate_form()) {
			try
			{

				String company = request.getParameter("company");
				String address = request.getParameter("address");
				String jobtitle = request.getParameter("jobtitle");
				String phonenumber = request.getParameter("phonenumber");
				String supervisor = request.getParameter("supervisor");
				String startingsalary = "";
				String endingsalary = "";
				String duties = request.getParameter("duties");
				String reasonforleaving = request.getParameter("reasonforleaving");
				String contact = request.getParameter("contact");
				String fromdate = request.getParameter("fromdate");
				String todate = request.getParameter("todate");
				profile = (ApplicantProfileBean) request.getSession().getAttribute("APPLICANT");
				int reason = Integer.parseInt(request.getParameter("reason"));

				if(profile == null)
				{
					path = "applicant_registration_step_1_ss.jsp";
				}else
				{
					sdf = new SimpleDateFormat("MM/dd/yy");

					ApplicantEmploymentSSBean abean = new ApplicantEmploymentSSBean();

					abean.setSin(profile.getSIN());
					abean.setCompany(company);
					abean.setAddress(address);
					abean.setJobTitle(jobtitle);
					abean.setPhoneNumber(phonenumber);
					abean.setSupervisor(supervisor);
					abean.setStartingSalary(startingsalary);
					abean.setEndingSalary(endingsalary);
					abean.setDuties(duties);
					if(StringUtils.isEmpty(fromdate)){
						abean.setFromDate(null);
					}else{
						System.out.println(fromdate);
						abean.setFromDate(sdf.parse(fromdate));
					}
					if(StringUtils.isEmpty(todate)){
						abean.setToDate(null);
					}else{
						abean.setToDate(sdf.parse(todate));
					}
					abean.setReasonForLeaving(reasonforleaving);
					abean.setContact(contact);
					abean.setReason(reason);
					ApplicantEmploymentSSManager.addApplicantEmploymentSS(abean);

					request.setAttribute("msg", "Education successfully added.");
					path = "applicant_registration_step_3_ss.jsp";
				}

			}
			catch(JobOpportunityException e)
			{
				e.printStackTrace();
				request.setAttribute("errmsg", "Could not add applicant employment.");
				path = "applicant_registration_step_3_ss.jsp";
			}
			catch(ParseException e)
			{
				e.printStackTrace();
				request.setAttribute("errmsg", "Invalid date format.");
				path = "applicant_registration_step_3_ss.jsp";
			}
		}else {
			request.setAttribute("errmsg", this.validator.getErrorString());
			path = "applicant_registration_step_3_ss.jsp";
		}
		return path;
	}
}
