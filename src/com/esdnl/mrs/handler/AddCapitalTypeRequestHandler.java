package com.esdnl.mrs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.esdnl.audit.constant.ActionTypeConstant;
import com.esdnl.mrs.CapitalType;
import com.esdnl.mrs.CapitalTypeDB;
import com.esdnl.mrs.audit.MRSAuditTrailBean;

public class AddCapitalTypeRequestHandler implements RequestHandler {

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
					CapitalType ct = new CapitalType(request.getParameter("type_id"));

					CapitalTypeDB.addCapitalType(ct);
					request.setAttribute("msg", "CAPITAL TYPE added successfully.");

					new MRSAuditTrailBean(usr, ActionTypeConstant.CREATE, ct);

				}
				else {
					request.setAttribute("msg", "CAPITAL TYPE ID is required.");
				}

				request.setAttribute("CAPITAL_TYPES", (CapitalType[]) CapitalTypeDB.getCapitalTypes().toArray(
						new CapitalType[0]));
				path = "add_capital_type.jsp";
			}
			else if (request.getParameter("op").equalsIgnoreCase("DEL")) {
				CapitalType ct = new CapitalType(request.getParameter("t_id"));
				if (CapitalTypeDB.deleteCapitalType(ct)) {
					request.setAttribute("msg", "CAPITAL TYPE deleted successfully.");

					new MRSAuditTrailBean(usr, ActionTypeConstant.DELETE, ct);
				}
				else {
					request.setAttribute("msg", "CAPITAL TYPE not deleted!");
				}
				request.setAttribute("CAPITAL_TYPES", (CapitalType[]) CapitalTypeDB.getCapitalTypes().toArray(
						new CapitalType[0]));
				path = "add_capital_type.jsp";
			}
			else {
				path = "error_message.jsp";
				request.setAttribute("msg", "INVALID OPTION");
			}
		}
		else {
			request.setAttribute("CAPITAL_TYPES", (CapitalType[]) CapitalTypeDB.getCapitalTypes().toArray(new CapitalType[0]));
			path = "add_capital_type.jsp";
		}

		return path;
	}
}