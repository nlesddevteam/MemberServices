package com.awsd.admin.apps.categories.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.personnel.PersonnelCategoryDB;
import com.esdnl.servlet.RequestHandlerImpl;

public class ViewJobCategoriesRequestHandler extends RequestHandlerImpl {

	public ViewJobCategoriesRequestHandler() {

		this.requiredPermissions = new String[] {
				"MEMBERADMIN-VIEW"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
			IOException {

		super.handleRequest(request, response);
		
		request.setAttribute("categories", PersonnelCategoryDB.getPersonnelCategories());
		path = "index.jsp";
		return path;
	}
}