package com.esdnl.student.travel.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.SchoolDB;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.student.travel.bean.StudentTravelException;
import com.esdnl.student.travel.bean.TravelRequestBean;
import com.esdnl.student.travel.dao.TravelRequestManager;
import com.esdnl.util.StringUtils;

public class ViewStudentTravelRequestRequestHandler extends RequestHandlerImpl {

	public ViewStudentTravelRequestRequestHandler() {

		requiredPermissions = new String[] {
				"STUDENT-TRAVEL-PRINCIPAL-VIEW", "STUDENT-TRAVEL-ADMIN-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		path = "view_request.jsp";

		validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("id")
		});

		if (validate_form()) {
			try {
				TravelRequestBean treq = TravelRequestManager.getTravelRequestBean(Integer.parseInt(form.get("id")));

				request.setAttribute("TRAVELREQUESTBEAN", treq);

				request.setAttribute("APPROVER",
						SchoolDB.getSchool(treq.getSchoolId()).getSchoolFamily().getProgramSpecialist());
			}
			catch (StudentTravelException e) {
				e.printStackTrace(System.err);
				request.setAttribute("msg", "Could not add travel request.");
				request.setAttribute("FORM", form);

				path = "index.jsp";
			}
			catch (Exception e) {
				e.printStackTrace(System.err);
				request.setAttribute("msg", "Could not add travel request.");
				request.setAttribute("FORM", form);

				path = "index.jsp";
			}
		}
		else {
			request.setAttribute("FORM", form);
			request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));
			path = "index.jsp";
		}

		return path;
	}
}