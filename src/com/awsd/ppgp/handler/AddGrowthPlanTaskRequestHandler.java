package com.awsd.ppgp.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.ppgp.PPGPDB;
import com.awsd.ppgp.PPGPException;
import com.awsd.ppgp.TaskCategoryManager;
import com.awsd.ppgp.TaskDomainManager;
import com.awsd.ppgp.TaskGradeManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class AddGrowthPlanTaskRequestHandler extends RequestHandlerImpl {

	public AddGrowthPlanTaskRequestHandler() {

		this.requiredPermissions = new String[] {
			"PPGP-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		Boolean policyRead = null;

		super.handleRequest(request, response);

		policyRead = (Boolean) session.getAttribute("PPGP-POLICY");

		if (policyRead == null) {
			path = "policy.jsp";
		}
		else {
			if (form.exists("gid") && form.exists("ppgpid")) {
				request.setAttribute("PPGP", PPGPDB.getPPGP(form.getInt("ppgpid")));
				request.setAttribute("PPGP_GOAL", PPGPDB.getPPGPGoal(form.getInt("gid")));
			}
			else {
				throw new PPGPException("PGP IF and Goal ID required to add additional tasks.");
			}
			//add static dropdown list: categories, grade
			request.setAttribute("cats", TaskCategoryManager.getTaskCategoryBeansMap());
			request.setAttribute("grades", TaskGradeManager.getTaskGradeBeansMap());
			request.setAttribute("domains", TaskDomainManager.getTaskDomainBeans());
			path = "growthPlan.jsp";
		}

		return path;
	}
}