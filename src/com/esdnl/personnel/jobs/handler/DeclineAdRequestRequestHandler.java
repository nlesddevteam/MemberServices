package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.mail.bean.EmailBean;
import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.esdnl.personnel.jobs.bean.AdRequestBean;
import com.esdnl.personnel.jobs.bean.AdRequestHistoryBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.constants.RequestStatus;
import com.esdnl.personnel.jobs.dao.AdRequestManager;
import com.esdnl.velocity.VelocityUtils;

public class DeclineAdRequestRequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		String path;
		HttpSession session = null;
		User usr = null;

		session = request.getSession(false);
		if ((session != null) && (session.getAttribute("usr") != null)) {
			usr = (User) session.getAttribute("usr");
			if (!(usr.getUserPermissions().containsKey("PERSONNEL-ADREQUEST-APPROVE"))) {
				throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
			}
		}
		else {
			throw new SecurityException("User login required.");
		}

		try {
			AdRequestBean req = AdRequestManager.getAdRequestBean(Integer.parseInt(request.getParameter("request_id")));

			if (req != null) {
				try {
					//add the request to db
					AdRequestManager.declineAdRequestBean(req, usr.getPersonnel());

					AdRequestHistoryBean history = req.getHistory(RequestStatus.SUBMITTED);
					//send email to admins
					HashMap<String, Object> model = new HashMap<String, Object>();
					// set values to be used in template
					model.put("requesterName", usr.getPersonnel().getFullNameReverse());
					model.put("competitionTitle", req.getTitle());
					EmailBean email = new EmailBean();
					email.setTo(history.getPersonnel().getEmailAddress());
					email.setSubject("NLESD Personnel Package: Job Ad Request Declined");
					email.setFrom("ms@esdnl.ca");
					email.setBody(VelocityUtils.mergeTemplateIntoString("personnel/job_ad_request_decline.vm", model));
					email.send();
					request.setAttribute("msg", "Ad Request declined successfully.");
				}
				catch (JobOpportunityException e) {
					e.printStackTrace(System.err);
					request.setAttribute("msg", "Ad requested could not be declined.");
				}
				catch (Exception e) {
					e.printStackTrace(System.err);
					request.setAttribute("msg", "Ad requested declined, requester email not sent.");
				}

				path = "admin_list_ad_requests.jsp";
			}
			else {
				path = "admin_list_ad_requests.jsp";
				request.setAttribute("msg", "REQUEST NOT FOUND.");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			path = "admin_list_ad_requests.jsp";
		}

		return path;
	}
}