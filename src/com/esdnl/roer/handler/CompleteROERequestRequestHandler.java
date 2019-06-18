package com.esdnl.roer.handler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.mail.SMTPAuthenticatedMail;
import com.awsd.personnel.profile.Profile;
import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.esdnl.roer.ROERequest;
import com.esdnl.roer.ROERequestDB;
import com.esdnl.roer.UnpaidDay;

public class CompleteROERequestRequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		HttpSession session = null;
		User usr = null;
		ROERequest roer = null;
		Profile profile = null;
		SimpleDateFormat sdf = null;
		StringTokenizer str_tok = null;
		Vector dates = null;
		UnpaidDay uday = null;
		String arr[] = null;
		String token = "";
		String path = "";
		SMTPAuthenticatedMail smtp = null;

		session = request.getSession(false);
		if ((session != null) && (session.getAttribute("usr") != null)) {
			usr = (User) session.getAttribute("usr");
			if (!(usr.getUserPermissions().containsKey("ROEREQUEST-ADMIN"))) {
				throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
			}
		}

		if (request.getParameter("req") != null) {
			try {
				roer = ROERequestDB.getROERequest(Integer.parseInt(request.getParameter("req")));
				ROERequestDB.completeRequest(Integer.parseInt(request.getParameter("req")));

				smtp = new SMTPAuthenticatedMail("mail.esdnl.ca", "ms", "services");

				smtp.postMail(new String[] {
					roer.getPersonnel().getEmailAddress()
				}, null, null, "ROE Request COMPLETED", usr.getPersonnel().getFullNameReverse()
						+ ", <br><br> Your ROE Request has been processed. <br><br>Thank you."
						+ "<br><br>PLEASE DO NOT REPLY TO THIS MESSAGE.<br><br>" + "Member Services", "ms@nlesd.ca");

				request.setAttribute("OUTSTANDING_REQUESTS", ROERequestDB.getROERequests());
				path = "requests.jsp";
			}
			catch (Exception e) {
				e.printStackTrace(System.err);
			}
		}

		return path;
	}
}