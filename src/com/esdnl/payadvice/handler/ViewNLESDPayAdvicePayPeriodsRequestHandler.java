package com.esdnl.payadvice.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.payadvice.bean.NLESDPayAdvicePayrollProcessBean;
import com.esdnl.payadvice.dao.NLESDPayAdvicePayrollProcessManager;
import com.esdnl.servlet.RequestHandlerImpl;
public class ViewNLESDPayAdvicePayPeriodsRequestHandler extends RequestHandlerImpl {
	public ViewNLESDPayAdvicePayPeriodsRequestHandler() {
		this.requiredPermissions= new String[] {
				"PAY-ADVICE-ADMIN"
			};
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,IOException {
		super.handleRequest(request, reponse);
		try {
				List<NLESDPayAdvicePayrollProcessBean> list = new ArrayList<NLESDPayAdvicePayrollProcessBean>(NLESDPayAdvicePayrollProcessManager.getNLESDPayAdvicePayrollProcessList(false).values());
				if(!list.isEmpty())
				{
					Collections.sort(list);
				}
				request.setAttribute("periods", list);
				path = "view_pay_periods_list.jsp";
		}
		catch (Exception e) {
				e.printStackTrace(System.err);
				path = "view_pay_periods_list.jsp";
		}

		return path;
	}
	
}
