package com.esdnl.webupdatesystem.policies.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.policies.bean.PoliciesException;
import com.esdnl.webupdatesystem.policies.dao.PoliciesManager;

public class ViewPoliciesRequestHandler extends RequestHandlerImpl {
	public ViewPoliciesRequestHandler() {
		this.requiredRoles = new String[] {
				"ADMINISTRATOR","WEB DESIGNER","WEBANNOUNCMENTS-POST"
			};
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {
		super.handleRequest(request, reponse);
	    try {
			request.setAttribute("policies", PoliciesManager.getPolicies());
		} catch (PoliciesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    path = "view_policies.jsp";
	    return path;
	}
}
