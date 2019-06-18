package com.esdnl.payadvice.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.payadvice.dao.NLESDPayAdviceImportJobManager;
import com.esdnl.servlet.RequestHandlerImpl;
public class ViewNLESDPayAdviceImportJobsRequestHandler extends RequestHandlerImpl {
	public ViewNLESDPayAdviceImportJobsRequestHandler() {
		this.requiredPermissions= new String[] {
				"PAY-ADVICE-ADMIN"
			};
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,IOException {
		super.handleRequest(request, reponse);
		try {
				request.setAttribute("jobs", NLESDPayAdviceImportJobManager.getNLESDPayAdviceImportJobBeans());
				path = "view_running_jobs.jsp";
		}
		catch (Exception e) {
				e.printStackTrace(System.err);
				path = "view_running_jobs.jsp";
		}
		return path;
	}
	
}