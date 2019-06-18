package com.esdnl.personnel.jobs.handler;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.mail.bean.EmailBean;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.ApplicantRefRequestBean;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.ApplicantRefRequestManager;
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
import com.esdnl.velocity.VelocityUtils;

public class DeclineApplicantRefRequestRequestHandler extends PublicAccessRequestHandlerImpl  {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		String path = null;
		try {
			int refreq = Integer.parseInt(request.getParameter("refreq"));
			ApplicantRefRequestBean abean = ApplicantRefRequestManager.getApplicantRefRequestBean(refreq);
			ApplicantProfileBean profile = ApplicantProfileManager.getApplicantProfileBean(abean.getApplicantId());
			//update refrequest with status
			ApplicantRefRequestManager.applicantReferenceDeclined(refreq, "Request Declined");
			//send email to applicant
			EmailBean ebean = new EmailBean();
			ebean.setTo(new String[] {
				profile.getEmail()
			});
			ebean.setFrom("ms@nlesd.ca");
			ebean.setSubject("Reference Check Request Declined");
			HashMap<String, Object> model = new HashMap<String, Object>();
			model.put("reqEmail", abean.getEmailAddress());
			ebean.setBody(VelocityUtils.mergeTemplateIntoString("personnel/send_applicant_declined_request.vm", model));
			ebean.send();
			path="decline_applicant_ref_request.jsp";

		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			path = "decline_applicant_ref_request.jsp";
		}

		return path;
	}
}