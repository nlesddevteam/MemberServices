package com.awsd.ppgp.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.ppgp.PPGPDB;
import com.awsd.ppgp.PPGPException;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class DeleteGrowthPlanRequestHandler extends RequestHandlerImpl {

	public DeleteGrowthPlanRequestHandler() {

		this.requiredPermissions = new String[] {
			"PPGP-VIEW-SUMMARY"
		};

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("id")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form())
			PPGPDB.deletePPGP(form.getInt("id"));
		else
			throw new PPGPException("PPGP ID required for deletion.");

		path = "principalSummary.jsp";

		return path;
	}
}