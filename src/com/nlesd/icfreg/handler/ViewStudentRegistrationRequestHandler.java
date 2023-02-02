package com.nlesd.icfreg.handler;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
import com.nlesd.icfreg.bean.IcfRegistrationPeriodBean;
import com.nlesd.icfreg.bean.IcfSchoolBean;
import com.nlesd.icfreg.dao.IcfRegistrationPeriodManager;
import com.nlesd.icfreg.dao.IcfSchoolManager;

public class ViewStudentRegistrationRequestHandler extends PublicAccessRequestHandlerImpl {
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		IcfRegistrationPeriodBean cbean = IcfRegistrationPeriodManager.getCurrentRegistrationPeriod();
		request.setAttribute("ap", cbean);
		ArrayList<IcfRegistrationPeriodBean> flist = IcfRegistrationPeriodManager.getFutureRegistrationPeriods();
		request.setAttribute("fp", flist);
		ArrayList<IcfSchoolBean> slist = IcfSchoolManager.getRegistrationPeriodSchools(cbean.getIcfRegPerId());
		request.setAttribute("slist", slist);
		return "index.jsp";
	}
}