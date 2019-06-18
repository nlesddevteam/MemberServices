package com.esdnl.personnel.v2.site.availability.handler;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.esdnl.personnel.v2.database.availability.EmployeeAvailabilityManager;
import com.esdnl.personnel.v2.database.sds.EmployeeManager;
import com.esdnl.personnel.v2.model.sds.bean.EmployeeBean;

public class EmployeeDeleteFutureBookingsRequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		String path;
		HttpSession session = null;
		User usr = null;

		session = request.getSession(false);
		if ((session != null) && (session.getAttribute("usr") != null)) {
			usr = (User) session.getAttribute("usr");
			if (!(usr.getUserPermissions().containsKey("PERSONNEL-SUBSTITUTES-STUDASS-VIEW"))) {
				throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
			}
		}
		else {
			throw new SecurityException("User login required.");
		}

		try {
			SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy hh:mm:a");

			String id = request.getParameter("id");
			String view_date = request.getParameter("view_date");

			EmployeeBean employee = EmployeeManager.getEmployeeBean(id);

			if (employee != null) {
				EmployeeAvailabilityManager.deleteEmployeeAvailabilityBean(employee.getSIN(), sdf2.parse(view_date));

				request.setAttribute("msg", "Employee future availability status was changed successfully.");
			}

			request.setAttribute("view_date", view_date);

			path = "availability_list.jsp";
		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			path = "availability_list.jsp";
		}

		return path;
	}
}