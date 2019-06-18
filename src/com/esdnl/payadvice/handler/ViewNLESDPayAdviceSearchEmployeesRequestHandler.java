package com.esdnl.payadvice.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.payadvice.dao.NLESDPayAdviceEmployeeInfoManager;
import com.esdnl.servlet.RequestHandlerImpl;
public class ViewNLESDPayAdviceSearchEmployeesRequestHandler  extends RequestHandlerImpl {
	public ViewNLESDPayAdviceSearchEmployeesRequestHandler() {
		this.requiredPermissions= new String[] {
				"PAY-ADVICE-ADMIN"
			};
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,IOException {
		super.handleRequest(request, reponse);
		try {
				ArrayList<String> list = NLESDPayAdviceEmployeeInfoManager.getNLESDPayAdviceEmployeeInfoLocations();
				Collections.sort(list);
				request.setAttribute("list",list);
				path = "admin_search.jsp";
		}
		catch (Exception e) {
				e.printStackTrace(System.err);
				path = "admin_search.jsp";
		}
		return path;
	}
	
}
