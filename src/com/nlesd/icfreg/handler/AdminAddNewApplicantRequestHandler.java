package com.nlesd.icfreg.handler;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.icfreg.bean.IcfRegistrationPeriodBean;
import com.nlesd.icfreg.bean.IcfSchoolBean;
import com.nlesd.icfreg.dao.IcfRegistrationPeriodManager;
import com.nlesd.icfreg.dao.IcfSchoolManager;

public class AdminAddNewApplicantRequestHandler extends RequestHandlerImpl {

	public AdminAddNewApplicantRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("irp")
		});
		this.requiredPermissions = new String[] {
			"ICF-REGISTRATION-ADMIN-ADD-NEW"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		if (validate_form()) {
			IcfRegistrationPeriodBean rbean = IcfRegistrationPeriodManager.getRegistrationPeriodById(form.getInt("irp"));
			request.setAttribute("perbean", rbean);
			ArrayList<IcfSchoolBean> slist = IcfSchoolManager.getRegistrationPeriodSchools(form.getInt("irp"));
			request.setAttribute("slist", slist);
		}else {
			
		}
		return "addnewapplicant.jsp";

		
	}
}