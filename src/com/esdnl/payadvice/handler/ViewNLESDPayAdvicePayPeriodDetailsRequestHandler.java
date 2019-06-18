package com.esdnl.payadvice.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.payadvice.bean.NLESDPayAdvicePayrollProcessBean;
import com.esdnl.payadvice.dao.NLESDPayAdvicePayrollProcessManager;
import com.esdnl.servlet.RequestHandlerImpl;
public class ViewNLESDPayAdvicePayPeriodDetailsRequestHandler extends RequestHandlerImpl {
	public ViewNLESDPayAdvicePayPeriodDetailsRequestHandler() {
		this.requiredPermissions= new String[] {
				"PAY-ADVICE-ADMIN"
			};
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,IOException {
		super.handleRequest(request, reponse);
		try {
				Integer paygroupid = form.getInt("id");
				NLESDPayAdvicePayrollProcessBean ppbean= NLESDPayAdvicePayrollProcessManager.getNLESDPayAdvicePayrollProcessBean(paygroupid);
				request.setAttribute("ppbean", ppbean);
				request.setAttribute("pgbean", ppbean.getPayGroupBean());
				request.setAttribute("ijbean", ppbean.getImportJobBean());
				path = "view_pay_period_details.jsp";
		}
		catch (Exception e) {
				e.printStackTrace(System.err);
				path = "view_pay_period_details.jsp";
		}
		return path;
	}
}
	