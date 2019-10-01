package com.esdnl.student.travel.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.student.travel.dao.TravelRequestManager;

public class DeleteStudentTravelRequestHandler extends RequestHandlerImpl {

	public DeleteStudentTravelRequestHandler() {

		this.requiredRoles = new String[] {
				"ADMINISTRATOR","PRINCIPAL"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("rid")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);
		if (validate_form()) {
			try {
				Integer requestId = form.getInt("rid");
				//now delete the request
				TravelRequestManager.deleteRequest(requestId);

				request.setAttribute("msgOK", "SUCCESS: Travel Request has been deleted.");
			}
			catch (Exception e) {
				request.setAttribute("msgERR", e.getMessage());
			}
		}else {
			request.setAttribute("msgERR", com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()));
		}
		
		return "/student/travel/index.jsp";
	}
}