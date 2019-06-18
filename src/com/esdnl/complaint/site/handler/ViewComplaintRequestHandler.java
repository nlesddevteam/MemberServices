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
import com.awsd.security.PermissionDB;
import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.security.crypto.PasswordEncryption;
import com.awsd.servlet.LoginNotRequiredRequestHandler;
import com.esdnl.complaint.database.ComplaintManager;
import com.esdnl.complaint.model.bean.ComplaintBean;
import com.esdnl.complaint.model.bean.ComplaintException;
import com.esdnl.util.StringUtils;

public class ViewComplaintRequestHandler implements LoginNotRequiredRequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		String path;
		HttpSession session = null;
		User usr = null;
		ComplaintBean complaint = null;
		String username = null;
		String hash = null;
		Personnel p = null;

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
			else {
				try {
					username = request.getParameter("u");
					if ((username == null) || (username.equals(""))) {
						System.err.println("NO USERNAME");
						throw new SecurityException("User login required.");
					}

					hash = request.getParameter("p");
					if ((hash == null) || (hash.equals(""))) {
						System.err.println("NO PASSWORD");
						throw new SecurityException("User login required.");
					}

					p = PersonnelDB.getPersonnel(username);
					if (p == null) {
						System.err.println("NO PREC");
						throw new SecurityException("User login required.");
					}

					if (!p.getPassword().equals(PasswordEncryption.decrypt(hash))) {
						System.err.println("PASSWORD NOT EQUAL");
						throw new SecurityException("User login required.");
					}

					usr = new User(username, p.getPassword());
					session = request.getSession(true);
					session.setAttribute("usr", usr);

					if (!(usr.getUserPermissions().containsKey("COMPLAINT-MONITOR")
							|| usr.getUserPermissions().containsKey("COMPLAINT-ADMIN") || usr.getUserPermissions().containsKey(
							"COMPLAINT-VIEW"))) {
						throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
					}
				}
				catch (Exception e) {
					e.printStackTrace(System.err);
					throw new SecurityException(e.getMessage() + " User login required.");
				}
			}

			String id = request.getParameter("id");
			if (StringUtils.isEmpty(id))
				request.setAttribute("msg", "ERROR: Complaint ID is require to view a complaint record.");
			else {
				complaint = ComplaintManager.getComplaintBean(Integer.parseInt(id), usr.getPersonnel());
				if (!(usr.getUserPermissions().containsKey("COMPLAINT-MONITOR") || usr.getUserPermissions().containsKey(
						"COMPLAINT-ADMIN"))
						&& usr.getUserPermissions().containsKey("COMPLAINT-VIEW")
						&& !ComplaintManager.isComplaintBeanAssignedTo(complaint, usr.getPersonnel()))
					request.setAttribute("msg", "ERROR: You do not have the permissions necessary to view this complaint record.");
				else {
					request.setAttribute("COMPLAINT_BEAN", complaint);

					Personnel[] monitors = (Personnel[]) PersonnelDB.getPersonnelList(
							PermissionDB.getPermission("COMPLAINT-MONITOR")).toArray(new Personnel[0]);

					for (int i = 0; i < monitors.length; i++) {

						if (monitors[i].getPersonnelID() == usr.getPersonnel().getPersonnelID())
							continue;

						try {
							EmailBean ebean = new EmailBean();

							ebean.setFrom("ms@nlesd.ca");
							ebean.setTo(new String[] {
								monitors[i].getEmailAddress()
							});
							ebean.setSubject("COMPLAINT REVIEWED: " + complaint.getFullName() + " BY "
									+ usr.getPersonnel().getFullName());
							ebean.setBody("COMPLAINT REVIEWED: "
									+ complaint.getFullName()
									+ " BY "
									+ usr.getPersonnel().getFullName()
									+ "<BR><BR>To review this complaint click the link below to login to Member Services and access the Complaint Administration System.<br><br>"
									+ "<a href='http://www.nlesd.ca/MemberServices/complaint/viewAdminComplaintSummary.html?u="
									+ monitors[i].getUserName() + "&p=" + PasswordEncryption.encrypt(monitors[i].getPassword()) + "&id="
									+ complaint.getId() + "'><B>CLICK HERE</B></a>"
									+ "<br><br>PLEASE DO NOT REPLY TO THIS MESSAGE.<br><br>" + "Member Services");
							ebean.send();
						}
						catch (EmailException e) {
							e.printStackTrace();
						}
					}
				}
			}
			path = "admin_complaint_summary.jsp";
		}
		catch (ComplaintException e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			path = "admin_complaint_summary.jsp";
		}

		return path;
	}
}