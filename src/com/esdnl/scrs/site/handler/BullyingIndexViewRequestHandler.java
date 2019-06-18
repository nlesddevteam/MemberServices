package com.esdnl.scrs.site.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.RequestHandlerImpl;

public class BullyingIndexViewRequestHandler extends RequestHandlerImpl {

	public BullyingIndexViewRequestHandler() {

		this.requiredPermissions = new String[] {
				"BULLYING-ANALYSIS-SCHOOL-VIEW", "BULLYING-ANALYSIS-ADMIN-VIEW"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		return "add_incident.jsp";
	}

}
