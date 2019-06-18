package com.awsd.ppgp.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.RequestHandlerImpl;

public class ViewPPGPDistrictPolicyRequestHandler extends RequestHandlerImpl {

	public ViewPPGPDistrictPolicyRequestHandler() {

		this.requiredPermissions = new String[] {
			"PPGP-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		session.setAttribute("PPGP-POLICY", new Boolean(true));

		path = "policy.jsp";

		return path;
	}
}