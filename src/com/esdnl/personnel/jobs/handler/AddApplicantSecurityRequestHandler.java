package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.servlet.LoginNotRequiredRequestHandler;
import com.esdnl.personnel.jobs.bean.ApplicantSecurityBean;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.ApplicantSecurityException;
import com.esdnl.personnel.jobs.dao.ApplicantSecurityManager;
import com.esdnl.util.StringUtils;
public class AddApplicantSecurityRequestHandler implements LoginNotRequiredRequestHandler {
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
	IOException {
		String path = null;
		ApplicantProfileBean profile = null;
		try {
			String security_question = request.getParameter("security_question");
			String security_answer = request.getParameter("security_answer");
			boolean valid=true;
			profile = (ApplicantProfileBean) request.getSession().getAttribute("APPLICANT");
			//check to see if the question
				if (!StringUtils.isEmpty(security_question)) {
					if(StringUtils.isEmpty(security_answer))
					{
						valid=false;
						request.setAttribute("msg", "Please enter a Security Answer!");
					}else{
						if(security_question.toUpperCase().equals(security_answer))
						{
							valid=false;
							request.setAttribute("msg", "Security Question and Security cannot be the same!");
						}
					}
				}
				if (valid)
				{
						ApplicantSecurityBean ibean = new ApplicantSecurityBean();
						ibean.setSin(profile.getSIN());
						ibean.setSecurity_question(security_question);
						ibean.setSecurity_answer(security_answer);
						ApplicantSecurityManager.addApplicantSecurityBean(ibean);
						request.setAttribute("msg", "Security Question has been updated.");
						
				}
				
				path = "applicant_security.jsp";
		}
		catch (ApplicantSecurityException e) {
			e.printStackTrace();
			request.setAttribute("msg", "Could not add applicant profileother information.");
			path = "applicant_security.jsp";
		}
		return path;
	}
}
