package com.esdnl.tsdoc.site.handler.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.tsdoc.manager.TrusteeGroupManager;

public class ViewCommitteesRequestHandler extends RequestHandlerImpl {

	public ViewCommitteesRequestHandler() {

		requiredPermissions = new String[] {
			"TSDOC-ADMIN-VIEW"
		};

	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {

		super.handleRequest(request, reponse);

		try {

			request.setAttribute("committees", TrusteeGroupManager.getTrusteeGroupBeans());

			path = "committees.jsp";
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "index.jsp";
		}

		return path;
	}

}
