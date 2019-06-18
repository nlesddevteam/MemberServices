package com.awsd.ppgp.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.ppgp.PPGP;
import com.awsd.ppgp.PPGPDB;
import com.awsd.ppgp.TaskCategoryManager;
import com.awsd.ppgp.TaskDomainManager;
import com.awsd.ppgp.TaskGradeManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class ViewGrowthPlanRequestHandler extends RequestHandlerImpl {

	public ViewGrowthPlanRequestHandler() {

		this.requiredPermissions = new String[] {
			"PPGP-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		Boolean policyRead = (Boolean) session.getAttribute("PPGP-POLICY");

		if ((policyRead == null) && !(usr.checkPermission("PPGP-VIEW-SUMMARY")))
			path = "policy.jsp";
		else {

			PPGP pgp = PPGPDB.getPPGP(usr.getPersonnel(), form.get("sy"));

			if (pgp == null) {
				pgp = new PPGP(usr.getPersonnel(), form.get("sy"));

				PPGPDB.addPPGP(pgp);

				request.setAttribute("REFRESH_ARCHIVE", new Boolean(true));
			}

			request.setAttribute("PPGP", pgp);
			//add static dropdown list: categories, grade
			request.setAttribute("cats", TaskCategoryManager.getTaskCategoryBeansMap());
			request.setAttribute("grades", TaskGradeManager.getTaskGradeBeansMap());
			request.setAttribute("domains", TaskDomainManager.getTaskDomainBeans());
			path = "growthPlan.jsp";
		}

		return path;
	}
}