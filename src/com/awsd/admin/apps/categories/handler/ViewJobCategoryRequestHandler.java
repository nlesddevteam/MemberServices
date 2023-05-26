package com.awsd.admin.apps.categories.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.admin.apps.categories.dao.CategoriesManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class ViewJobCategoryRequestHandler extends RequestHandlerImpl {

	public ViewJobCategoryRequestHandler() {

		this.requiredPermissions = new String[] {
				"MEMBERADMIN-VIEW"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("cid")
			});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
			IOException {

		super.handleRequest(request, response);
		
		request.setAttribute("category", CategoriesManager.getCategoryById(form.getInt("cid")));
		request.setAttribute("roles", CategoriesManager.getCategoryRolesById(form.getInt("cid")));
		request.setAttribute("rolesdd", CategoriesManager.getRolesDropdown(form.getInt("cid")));
		
		path = "viewcategory.jsp";
		return path;
	}
}