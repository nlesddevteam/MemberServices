package com.esdnl.webupdatesystem.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
public class ViewWebUpdateSystemRequestHandler  extends RequestHandlerImpl {
	public ViewWebUpdateSystemRequestHandler() {
		this.requiredRoles = new String[] {
				"ADMINISTRATOR","WEB DESIGNER"
			};
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {
		super.handleRequest(request, reponse);
		try {
			//request.setAttribute("documents", NLESDPayrollDocumentManager.getNLESDPayrollDocumentBeans());
			path = "index.jsp";
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "index.jsp";
		}
		return path;
	}

}
