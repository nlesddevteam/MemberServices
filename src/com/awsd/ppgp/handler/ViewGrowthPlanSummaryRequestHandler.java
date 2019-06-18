package com.awsd.ppgp.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.ppgp.PPGPDB;
import com.esdnl.personnel.v2.database.sds.EmployeeManager;
import com.esdnl.personnel.v2.model.sds.bean.EmployeeBean;
import com.esdnl.personnel.v2.model.sds.bean.EmployeeException;
import com.esdnl.servlet.RequestHandlerImpl;

public class ViewGrowthPlanSummaryRequestHandler extends RequestHandlerImpl {

	public ViewGrowthPlanSummaryRequestHandler() {

		this.requiredPermissions = new String[] {
				"PPGP-VIEW", "PPGP-VIEW-SUMMARY"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		Boolean policyRead = (Boolean) session.getAttribute("PPGP-POLICY");

		if ((policyRead == null)
				&& !((usr.checkPermission("PPGP-VIEW-SUMMARY") || (usr.checkPermission("PPGP-VIEW-SUMMARY-PROGRAMSPECIALIST"))))) {
			path = "policy.jsp";
		}
		else {
			if (form.exists("pid")) {
				Personnel teacher = PersonnelDB.getPersonnel(form.getInt("pid"));
				request.setAttribute("TEACHER", teacher);
				//get employee info to show assignment
				try {
					EmployeeBean ebean = EmployeeManager.getEmployeeBean(form.get("pid"));
					request.setAttribute("ebean", ebean);
					
				} catch (EmployeeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if (form.exists("ppgpid"))
					request.setAttribute("PPGP", PPGPDB.getPPGP(teacher, form.getInt("ppgpid")));
				else
					request.setAttribute("PPGP", teacher.getPPGP());
			}
			else {
				if (form.exists("ppgpid"))
					request.setAttribute("PPGP", PPGPDB.getPPGP(usr.getPersonnel(), form.getInt("ppgpid")));
				else
					request.setAttribute("PPGP", usr.getPersonnel().getPPGP());
				
				try {
					EmployeeBean ebean = EmployeeManager.getEmployeeBean(String.valueOf(usr.getPersonnel().getPersonnelID()));
					request.setAttribute("ebean", ebean);
					
				} catch (EmployeeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			path = "teacherSummary.jsp";
		}

		return path;
	}
}