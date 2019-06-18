package com.esdnl.mrs.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

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
import com.awsd.servlet.RequestHandler;
import com.esdnl.mrs.MaintenanceRequest;
import com.esdnl.mrs.MaintenanceRequestDB;
import com.esdnl.mrs.RequestComment;

public class AddCommentRequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		HttpSession session = null;
		User usr = null;
		String path = "";
		MaintenanceRequest req = null;
		session = request.getSession(false);
		if ((session != null) && (session.getAttribute("usr") != null)) {
			usr = (User) session.getAttribute("usr");
			if (!(usr.getUserPermissions().containsKey("MAINTENANCE-SCHOOL-VIEW")
					|| usr.getUserPermissions().containsKey("MAINTENANCE-ADMIN-VIEW") || usr.getUserPermissions().containsKey(
					"MAINTENANCE-WORKORDERS-VIEW"))) {
				throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
			}
		}

		if (request.getParameter("op") != null) {
			if (request.getParameter("op").equalsIgnoreCase("CONFIRM")) {
				if ((request.getParameter("req") == null) || (request.getParameter("req").equals(""))) {
					request.setAttribute("msg", "REQUEST ID REQUIRED");
				}
				else {
					req = MaintenanceRequestDB.getMaintenanceRequest(Integer.parseInt(request.getParameter("req")));

					if ((request.getParameter("req_comment") != null) && (!request.getParameter("req_comment").equals(""))) {
						RequestComment comment = new RequestComment(req, usr.getPersonnel(), request.getParameter("req_comment"));
						MaintenanceRequestDB.addRequestComment(comment);

						// new MRSAuditTrailBean(usr, ActionTypeConstant.CREATE, comment);

						Vector<Personnel> to = new Vector<Personnel>(MaintenanceRequestDB.getSchoolSupervisor(req.getSchool()));
						to.add(req.getSchool().getSchoolPrincipal());

						try {
							EmailBean email = new EmailBean();
							email.setTo((Personnel[]) to.toArray(new Personnel[0]));
							//add admin emails as cc so two headers do not send
							String[] admins = new String[PersonnelDB.getPersonnelByRole("ADMINISTRATOR").length];
							int x=0;
							for(Personnel p : PersonnelDB.getPersonnelByRole("ADMINISTRATOR")){
									admins[x]=p.getEmailAddress();
									x++;
							}
							if(x > 0){
								email.setBCC(admins);
							}
							email.setFrom("noreply@nlesd.ca");
							email.setSubject("MRS: Comment added to work order " + req.getFormattedRequestID());
							email.setBody(req.toEmail(MaintenanceRequest.EMAIL_SCHOOLADMIN));
							email.send();
						}
						catch (EmailException e) {}
					}
				}
				request.setAttribute("MAINT_REQUEST", req);
				request.setAttribute("COMPLETE", new Boolean(true));
				path = "add_comment.jsp";
			}
			else {
				request.setAttribute("msg", "INVALID OPTION");

				path = "error_message.jsp";
			}
		}
		else {
			if (request.getParameter("req") != null) {
				req = MaintenanceRequestDB.getMaintenanceRequest(Integer.parseInt(request.getParameter("req")),
						usr.getPersonnel());
				request.setAttribute("MAINT_REQUEST", req);
				path = "add_comment.jsp";
			}
			else {
				request.setAttribute("msg", "REQUEST ID REQUIRED");

				path = "error_message.jsp";
			}
		}

		return path;
	}
}