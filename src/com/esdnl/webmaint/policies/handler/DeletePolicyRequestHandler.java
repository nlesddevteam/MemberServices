package com.esdnl.webmaint.policies.handler;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.esdnl.webmaint.policies.PolicyDB;
import com.esdnl.webmaint.policies.PolicyRegulation;
import com.esdnl.webmaint.policies.PolicyRegulationDB;

public class DeletePolicyRequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		HttpSession session = null;
		User usr = null;
		File f = null;
		String path = "";
		Iterator iter = null;
		PolicyRegulation reg = null;

		session = request.getSession(false);
		if ((session != null) && (session.getAttribute("usr") != null)) {
			usr = (User) session.getAttribute("usr");
			if (!(usr.getUserPermissions().containsKey("WEBMAINTENANCE-VIEW")
					&& usr.getUserPermissions().containsKey("WEBMAINTENANCE-DISTRICTPOLICIES"))) {
				throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
			}
		}

		if ((request.getParameter("cat") == null) || request.getParameter("cat").equals("")) {
			request.setAttribute("edit_msg", "CATEGORY CODE IS REQUIRED");
		}
		if ((request.getParameter("code") == null) || request.getParameter("code").equals("")) {
			request.setAttribute("edit_msg", "POLICY CODE IS REQUIRED");
		}
		else {
			try {
				iter = PolicyRegulationDB.getPolicyRegulations(request.getParameter("cat").toUpperCase(),
						request.getParameter("code").toUpperCase()).iterator();
				while (iter.hasNext()) {
					reg = (PolicyRegulation) iter.next();

					if (PolicyRegulationDB.deletePolicyRegulation(reg.getCategoryCode(), reg.getPolicyCode(),
							reg.getRegulationCode())) {
						f = new File(session.getServletContext().getRealPath("/")
								+ "/../../nlesdweb/WebContent/about/policies/esd/regulations/" + reg.getCategoryCode() + "_"
								+ reg.getPolicyCode() + "_" + reg.getRegulationCode() + ".pdf");

						if (f.exists())
							f.delete();
					}
				}

				if (PolicyDB.deletePolicy(request.getParameter("cat").toUpperCase(),
						request.getParameter("code").toUpperCase())) {
					f = new File(session.getServletContext().getRealPath("/") + "/../../nlesdweb/WebContent/about/policies/esd/"
							+ request.getParameter("cat").toUpperCase() + "_" + request.getParameter("code").toUpperCase() + ".pdf");

					if (f.exists())
						f.delete();
				}

				request.setAttribute("edit_msg", "POLICY DELETED SUCCESSFULLY");
			}
			catch (SQLException e) {
				switch (e.getErrorCode()) {
				case 1:
					request.setAttribute("edit_msg", "CATEGORY " + request.getParameter("cat_code") + " ALREADY EXISTS");
					break;
				default:
					request.setAttribute("edit_msg", e.getMessage());
				}
			}
		}

		request.setAttribute("POLICIES", PolicyDB.getPolicies());
		path = "policies.jsp";

		return path;
	}
}