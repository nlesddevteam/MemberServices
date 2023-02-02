package com.nlesd.icfreg.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.icfreg.bean.IcfRegistrationPeriodBean;
import com.nlesd.icfreg.bean.IcfSchoolBean;
import com.nlesd.icfreg.dao.IcfRegistrationPeriodManager;
import com.nlesd.icfreg.dao.IcfSchoolManager;

public class ViewPeriodSchoolsRequestHandler  extends RequestHandlerImpl {

	public ViewPeriodSchoolsRequestHandler() {

		this.requiredPermissions = new String[] {
			"ICF-REGISTRATION-ADMIN-VIEW"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);
		int pid =  form.getInt("irp");
		IcfRegistrationPeriodBean rbean = IcfRegistrationPeriodManager.getRegistrationPeriodById(pid);
		request.setAttribute("period", rbean);
		ArrayList<IcfSchoolBean> alist = IcfSchoolManager.getRegistrationPeriodSchools(pid);
		request.setAttribute("schools", alist);
		TreeMap<String,Integer> ilist = IcfSchoolManager.getAllSchools();
		request.setAttribute("ischools", ilist);
		return "viewperiodschools.jsp";
	}
}
