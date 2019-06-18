package com.esdnl.mrs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.esdnl.mrs.RequestType;
import com.esdnl.mrs.RequestTypeDB;

public class AddRequestTypeRequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		HttpSession session = null;
		User usr = null;
		String path = "";

		session = request.getSession(false);
		if ((session != null) && (session.getAttribute("usr") != null)) {
			usr = (User) session.getAttribute("usr");
			if (!(usr.getUserPermissions().containsKey("MAINTENANCE-ADMIN-VIEW"))) {
				throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
			}
		}

		if (request.getParameter("op") != null) {
			if (request.getParameter("op").equalsIgnoreCase("ADD")) {
				if (request.getParameter("type_id") != null) {
					RequestType rt = new RequestType(request.getParameter("type_id"));
					RequestTypeDB.addRequestType(rt);
					request.setAttribute("msg", "REQUEST TYPE added successfully.");

				}
				else {
					request.setAttribute("msg", "REQUEST TYPE ID is required.");
				}

				request.setAttribute("REQUEST_TYPES", (RequestType[]) RequestTypeDB.getRequestTypes().toArray(
						new RequestType[0]));
				path = "add_request_type.jsp";
			}
			else if (request.getParameter("op").equalsIgnoreCase("DEL")) {
				RequestType rt = new RequestType(request.getParameter("t_id"));
				if (RequestTypeDB.deleteRequestType(rt)) {
					request.setAttribute("msg", "REQUEST TYPE deleted successfully.");

				}
				else {
					request.setAttribute("msg", "REQUEST TYPE not deleted!");
				}
				request.setAttribute("REQUEST_TYPES", (RequestType[]) RequestTypeDB.getRequestTypes().toArray(
						new RequestType[0]));
				path = "add_request_type.jsp";
			}
			else {
				path = "error_message.jsp";
				request.setAttribute("msg", "INVALID OPTION");
			}
		}
		else {
			request.setAttribute("REQUEST_TYPES", (RequestType[]) RequestTypeDB.getRequestTypes().toArray(new RequestType[0]));
			path = "add_request_type.jsp";

		}

		return path;
	}
}