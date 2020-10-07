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

public class AddApplicantESDGeneralExperienceRequestHandler extends PersonnelApplicationRequestHandlerImpl
{
	public AddApplicantESDGeneralExperienceRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("perm_contract", "Please specify whether or not you help a permanent contract")
		});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		String path;
		ApplicantProfileBean profile = null;
		boolean submit = false;
		if (validate_form()) {
			try
			{
				String perm_contract = request.getParameter("perm_contract");
				String perm_school = request.getParameter("perm_school");
				String perm_position = request.getParameter("perm_position");
				String contract_school = request.getParameter("contract_school");
				String contract_enddate = request.getParameter("contract_enddate");
				String repl_sub_time = request.getParameter("repl_sub_time");
				//String repl_time = request.getParameter("repl_time");
				//String sub_time = request.getParameter("sub_time");

				profile = (ApplicantProfileBean) request.getSession().getAttribute("APPLICANT");

				if(perm_contract.equals("N") && StringUtils.isEmpty(repl_sub_time))
				{
					request.setAttribute("errmsg", "Please specify whether or not you have replacement or substitute time.");
					path = "applicant_registration_step_2.jsp";
				}
				else
				{
					request.setAttribute("msg", "Permanent contract experience updated successfully.");
					path = "applicant_registration_step_2.jsp"; // default path

					ApplicantEsdExperienceBean exp = new ApplicantEsdExperienceBean();
					exp.setSIN(profile.getSIN());

					if(perm_contract.equalsIgnoreCase("Y"))
					{
						if(StringUtils.isEmpty(perm_school))
						{
							request.setAttribute("errmsg", "Please specify permanent school.");
							path = "applicant_registration_step_2.jsp";
						}
						else if(StringUtils.isEmpty(perm_position))
						{
							request.setAttribute("errmsg", "Please specify permanent position.");
							path = "applicant_registration_step_2.jsp";
						}
						else
						{
							exp.setPermanentContractSchool(Integer.parseInt(perm_school));
							exp.setPermanentContractPosition(perm_position);

							submit = true;
						}
					}
					else if(repl_sub_time.equalsIgnoreCase("Y"))
					{
						if(StringUtils.isEmpty(contract_school))
						{
							request.setAttribute("errmsg", "Please specify contract school.");
							path = "applicant_registration_step_2.jsp";
						}
						else if(StringUtils.isEmpty(contract_enddate))
						{
							request.setAttribute("errmsg", "Please specify contract end date.");
							path = "applicant_registration_step_2.jsp";
						}
						else
						{
							exp.setContractSchool(Integer.parseInt(contract_school));
							try
							{
								SimpleDateFormat sdf = new SimpleDateFormat(ApplicantEsdExperienceBean.DATE_FORMAT);
								exp.setContractEndDate(sdf.parse(contract_enddate));

								submit = true;
								request.setAttribute("msg", "Experience updated successfully!");
								path = "applicant_registration_step_2.jsp";
							}
							catch(ParseException e)
							{
								request.setAttribute("errmsg", "Invalid contract end date format.");
								path = "applicant_registration_step_2.jsp";
							}
						}
					}
					else
						submit = true;

					if(submit)
						ApplicantEsdExperienceManager.addApplicantEsdExperienceBean(exp);
				}
			}
			catch(JobOpportunityException e)
			{
				e.printStackTrace();
				request.setAttribute("errmsg", "Could not add applicant profile ESD general experience.");
				path = "applicant_registration_step_2.jsp";
			}
		}else {
			request.setAttribute("errmsg", this.validator.getErrorString());
			path = "applicant_registration_step_2.jsp";
		}



		return path;
	}
}