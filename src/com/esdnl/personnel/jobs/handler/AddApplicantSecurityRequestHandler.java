package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.ApplicantSecurityBean;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.ApplicantSecurityException;
import com.esdnl.personnel.jobs.dao.ApplicantSecurityManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.PersonnelApplicationRequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
public class AddApplicantSecurityRequestHandler extends PersonnelApplicationRequestHandlerImpl {
	public AddApplicantSecurityRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("security_question", "Please specify security question"),
				new RequiredFormElement("security_answer", "Please specify security answer")
		});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
			IOException {
		super.handleRequest(request, response);
		String path = null;
		ApplicantProfileBean profile = null;
		if (validate_form()) {
			try {
					profile = (ApplicantProfileBean) request.getSession(false).getAttribute("APPLICANT");
					String security_question = request.getParameter("security_question");
					String security_answer = request.getParameter("security_answer");
					ApplicantSecurityBean ibean = new ApplicantSecurityBean();
					ibean.setSin(profile.getSIN());
					ibean.setSecurity_question(security_question);
					ibean.setSecurity_answer(security_answer);
					ApplicantSecurityManager.addApplicantSecurityBean(ibean);
					request.setAttribute("msg", "Security Question has been updated.");
					path = "applicant_security.jsp";
			}
			catch (ApplicantSecurityException e) {
				e.printStackTrace();
				request.setAttribute("msg", "Could not add applicant profileother information.");
				path = "applicant_security.jsp";
			}
		}else {
			request.setAttribute("errmsg", this.validator.getErrorString());
			path = "applicant_security.jsp";
		}

		return path;
	}
}
