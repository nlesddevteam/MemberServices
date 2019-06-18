package com.esdnl.payadvice.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.payadvice.dao.NLESDPayrollDocumentManager;
import com.esdnl.servlet.RequestHandlerImpl;
public class ViewUnprocessedNLESDPayrollDocumentRequestHandler extends RequestHandlerImpl {
	public ViewUnprocessedNLESDPayrollDocumentRequestHandler() {
		this.requiredPermissions= new String[] {
				"PAY-ADVICE-ADMIN"
			};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,IOException {
		super.handleRequest(request, reponse);
		try {
				request.setAttribute("documents", NLESDPayrollDocumentManager.getNLESDPayrollDocumentBeans());
				path = "unprocessed_files.jsp";
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "unprocessed_files.jsp";
		}

		return path;
	}
}
