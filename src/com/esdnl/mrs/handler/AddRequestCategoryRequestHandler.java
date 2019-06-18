package com.esdnl.mrs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.esdnl.mrs.RequestCategory;
import com.esdnl.mrs.RequestCategoryDB;

public class AddRequestCategoryRequestHandler implements RequestHandler {

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
					RequestCategoryDB.addRequestCategory(new RequestCategory(request.getParameter("type_id")));
					request.setAttribute("msg", "REQUEST CATEGORY added successfully.");
				}
				else {
					request.setAttribute("msg", "REQUEST CATEGORY ID is required.");
				}

				request.setAttribute("REQUEST_CATEGORIES",
						(RequestCategory[]) RequestCategoryDB.getRequestCategories().toArray(new RequestCategory[0]));
				path = "add_request_category.jsp";
			}
			else if (request.getParameter("op").equalsIgnoreCase("DEL")) {
				if (RequestCategoryDB.deleteRequestCategory(new RequestCategory(request.getParameter("t_id")))) {
					request.setAttribute("msg", "REQUEST Category deleted successfully.");
				}
				else {
					request.setAttribute("msg", "REQUEST Category not deleted!");
				}
				request.setAttribute("REQUEST_CATEGORIES",
						(RequestCategory[]) RequestCategoryDB.getRequestCategories().toArray(new RequestCategory[0]));
				path = "add_request_category.jsp";
			}
			else {
				path = "error_message.jsp";
				request.setAttribute("msg", "INVALID OPTION");
			}
		}
		else {
			request.setAttribute("REQUEST_CATEGORIES", (RequestCategory[]) RequestCategoryDB.getRequestCategories().toArray(
					new RequestCategory[0]));
			path = "add_request_category.jsp";
		}

		return path;
	}
}