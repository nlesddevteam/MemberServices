package com.esdnl.payadvice.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.payadvice.bean.NLESDPayAdvicePayrollProcessBean;
import com.esdnl.payadvice.dao.NLESDPayAdvicePayrollProcessErrorManager;
import com.esdnl.payadvice.dao.NLESDPayAdvicePayrollProcessManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class viewNLESDPayrollProcessErrorsRequestHandler extends RequestHandlerImpl {
	public viewNLESDPayrollProcessErrorsRequestHandler() {
		this.requiredPermissions= new String[] {
				"PAY-ADVICE-ADMIN"
			};
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,IOException {
		super.handleRequest(request, reponse);
		String paygroupid = form.get("payid");
		try {
				NLESDPayAdvicePayrollProcessBean ppbean= NLESDPayAdvicePayrollProcessManager.getNLESDPayAdvicePayrollProcessBean(Integer.parseInt(paygroupid));
				request.setAttribute("ppbean", ppbean);
				request.setAttribute("pgbean", ppbean.getPayGroupBean());
				request.setAttribute("paygroups", NLESDPayAdvicePayrollProcessErrorManager.getNLESDPayAdvicePayrollProcessErrors(Integer.parseInt(paygroupid)));
				path = "view_process_errors.jsp";
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "view_process_errors.jsp";
		}
		return path;
	}
	
}
