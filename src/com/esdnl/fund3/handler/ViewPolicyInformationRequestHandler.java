package com.esdnl.fund3.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.fund3.bean.Fund3Exception;
import com.esdnl.fund3.dao.PolicyManager;
import com.esdnl.servlet.RequestHandlerImpl;
public class ViewPolicyInformationRequestHandler extends RequestHandlerImpl {
	public ViewPolicyInformationRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {
		super.handleRequest(request, reponse);
	    try {
			request.setAttribute("policies", PolicyManager.getActivePolicies());
		} catch (Fund3Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    path = "view_policy_information.jsp";
	    return path;
	}
}
