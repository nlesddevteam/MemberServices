package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.v2.database.sds.EmployeeManager;
import com.esdnl.personnel.v2.model.sds.bean.EmployeeException;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class ViewApplicantNonHrRequestHandler extends RequestHandlerImpl {

	public ViewApplicantNonHrRequestHandler() {
		validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("sin")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			try {
				ApplicantProfileBean abean = ApplicantProfileManager.getApplicantProfileBean(form.get("sin"));
				
				if (abean != null) {
					request.setAttribute("APPLICANT", abean);
					request.setAttribute("EMPBEAN",EmployeeManager.getEmployeeBeanByApplicantProfile(abean));
					path = "view_applicant_non.jsp";
				}
				else {
					request.setAttribute("msg", "Invalid request.");

					path = "view_applicant_non.jsp";
				}
			}
			catch (JobOpportunityException e) {
				e.printStackTrace();
				request.setAttribute("msg", "Could not retrieve applicant record.");

				path = "admin_index.jsp";
			} catch (EmployeeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			request.setAttribute("FORM", form);
			request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));

			path = "admin_index.jsp";
		}

		return path;
	}
}
