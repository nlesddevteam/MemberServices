package com.esdnl.complaint.site.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.bean.EmailException;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.security.crypto.PasswordEncryption;
import com.awsd.servlet.RequestHandler;
import com.esdnl.complaint.database.ComplaintManager;
import com.esdnl.complaint.model.bean.ComplaintBean;
import com.esdnl.complaint.model.bean.ComplaintException;
import com.esdnl.util.StringUtils;

public class AssignComplaintRequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		String path;
		HttpSession session = null;
		User usr = null;
		ComplaintBean complaint = null;

		try {
			session = request.getSession(false);
			if ((session != null) && (session.getAttribute("usr") != null)) {
				usr = (User) session.getAttribute("usr");
				if (!(usr.getUserPermissions().containsKey("COMPLAINT-MONITOR")
						|| usr.getUserPermissions().containsKey("COMPLAINT-ADMIN") || usr.getUserPermissions().containsKey(
						"COMPLAINT-VIEW"))) {
					throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
				}
			}

			String id = request.getParameter("id");
			if (StringUtils.isEmpty(id))
				request.setAttribute("msg", "ERROR: Complaint ID is require to view a complaint record.");
			else {
				complaint = ComplaintManager.getComplaintBean(Integer.parseInt(id));
				if (!(usr.getUserPermissions().containsKey("COMPLAINT-MONITOR") || usr.getUserPermissions().containsKey(
						"COMPLAINT-ADMIN"))
						&& usr.getUserPermissions().containsKey("COMPLAINT-VIEW")
						&& !ComplaintManager.isComplaintBeanAssignedTo(complaint, usr.getPersonnel()))
					request.setAttribute("msg", "ERROR: You do not have the permissions necessary to view this complaint record.");
				else {
					if (request.getParameter("CONFIRMED") != null) {
						if (StringUtils.isEmpty("assigned_id"))
							request.setAttribute("msg", "ERROR: Please select someone to assign to the complaint.");
						else {
							Personnel assigned_to = PersonnelDB.getPersonnel(Integer.parseInt(request.getParameter("assigned_id")));

							ComplaintManager.assignComplaintBean(complaint, usr.getPersonnel(), assigned_to);

							try {
								EmailBean email = new EmailBean();
								email.setTo(new String[] {
									assigned_to.getEmailAddress()
								});
								email.setSubject("COMPLAINT ASSIGNED: " + complaint.getFullName());
								email.setBody(usr.getPersonnel().getFullNameReverse()
										+ " has assigned a complaint received from "
										+ complaint.getFullName()
										+ " to you."
										+ " To review this complaint click the link below to login to Member Services and access the Complaint Administration System.<br><br>"
										+ "<a href='http://www.nlesd.ca/MemberServices/complaint/viewAdminComplaintSummary.html?u="
										+ assigned_to.getUserName() + "&p=" + PasswordEncryption.encrypt(assigned_to.getPassword())
										+ "&id=" + complaint.getId() + "'><B>CLICK HERE</B></a>"
										+ "<br><br>PLEASE DO NOT REPLY TO THIS MESSAGE.<br><br>" + "Member Services");
								email.setFrom("ms@nlesd.ca");
								email.send();
							}
							catch (EmailException e) {
								e.printStackTrace(System.err);
							}

							request.setAttribute("RELOAD_PARENT", new Boolean(true));
						}
					}
					else
						request.setAttribute("COMPLAINT_BEAN", complaint);
				}
			}
			path = "admin_assign_complaint.jsp";
		}
		catch (ComplaintException e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			path = "admin_assign_complaint.jsp";
		}

		return path;
	}
}