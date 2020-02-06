package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.dao.RequestToHireManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class DeleteRequestToHireRequestHandler extends RequestHandlerImpl {
	public DeleteRequestToHireRequestHandler() {
		requiredPermissions = new String[] {
				"PERSONNEL-ADREQUEST-REQUEST","RTH-NEW-REQUEST"
			};
		validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("rid")
			});
		
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		try {
			
			if(validate_form()) {
				RequestToHireManager.deleteRequestToHire(form.getInt("rid"));
				request.setAttribute("msg", "Request has been deleted");
				path = "admin_index.jsp";
			}else {
				request.setAttribute("msg", validator.getErrorString());
				path = "admin_index.jsp";
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			path = null;
		}
		return path;
	}
}