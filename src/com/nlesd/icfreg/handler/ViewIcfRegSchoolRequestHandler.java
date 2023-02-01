package com.nlesd.icfreg.handler;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.icfreg.bean.IcfRegistrationPeriodBean;
import com.nlesd.icfreg.dao.IcfRegistrationPeriodManager;

public class ViewIcfRegSchoolRequestHandler extends RequestHandlerImpl {

	public ViewIcfRegSchoolRequestHandler() {

		this.requiredPermissions = new String[] {
			"ICF-REGISTRATION-SCHOOL-VIEW"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);
		//period list where school is included
		//default to -1 in case not set correctly in MS
		int sid = -1;
		if(usr.getPersonnel() != null) {
			if(usr.getPersonnel().getSchool() != null) {
				sid =usr.getPersonnel().getSchool().getSchoolID();
				
			}
		}
				
		ArrayList<IcfRegistrationPeriodBean> alist = IcfRegistrationPeriodManager.getRegistrationPeriodsBySchool(sid);
		request.setAttribute("periods", alist);
		request.setAttribute("sid", sid);
		return "schoolindex.jsp";
	}
}
