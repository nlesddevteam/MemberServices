package com.awsd.travel.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.Supervisors;
import com.awsd.travel.TravelClaim;
import com.awsd.travel.TravelClaimDB;
import com.esdnl.servlet.RequestHandlerImpl;

public class ChangeSupervisorRequestHandler extends RequestHandlerImpl {

	public ChangeSupervisorRequestHandler() {

		this.requiredPermissions = new String[] {
				"TRAVEL-EXPENSE-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		String op = "";
		boolean check = false;
		Personnel supervisor = null;
		TravelClaim claim = null;

		claim = TravelClaimDB.getClaim(Integer.parseInt(request.getParameter("claim_id")));
		op = request.getParameter("op");
		if (op != null) {
			if (op.equalsIgnoreCase("CONFIRM")) {
				try {
					supervisor = PersonnelDB.getPersonnel(Integer.parseInt(request.getParameter("supervisor_id")));
				}
				catch (NumberFormatException e) {
					supervisor = null;
				}
				if (supervisor == null) {
					request.setAttribute("msg", "Please select supervisor.");
				}
				else if (supervisor.getPersonnelID() == claim.getSupervisor().getPersonnelID()) {
					request.setAttribute("msg", "Selected supervisor already set.");
				}
				else {
					//all clear
					check = TravelClaimDB.setSupervisor(claim, supervisor);
					if (check) {
						request.setAttribute("SUCCESS", new Boolean(true));
					}
					else {
						request.setAttribute("msg", "Supervisor could not be changed.");
					}
				}
			}
			else {
				request.setAttribute("msg", "Invalid option.");
			}
		}
		request.setAttribute("TRAVELCLAIM", claim);
		request.setAttribute("SUPERVISORS", new Supervisors());
		path = "change_supervisor.jsp";
		return path;
	}
}