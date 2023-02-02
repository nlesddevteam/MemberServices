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
import com.nlesd.icfreg.bean.IcfRegApplicantBean;
import com.nlesd.icfreg.bean.IcfRegistrationPeriodBean;
import com.nlesd.icfreg.dao.IcfApplicantManager;
import com.nlesd.icfreg.dao.IcfRegistrationPeriodManager;
import com.nlesd.icfreg.dao.IcfSchoolManager;

public class ViewPeriodRegistrantsBySchoolRequestHandler extends RequestHandlerImpl {

	public ViewPeriodRegistrantsBySchoolRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("irp"),
				new RequiredFormElement("sid"),
		});
		this.requiredPermissions = new String[] {
			"ICF-REGISTRATION-ADMIN-VIEW","ICF-REGISTRATION-SCHOOL-VIEW"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		if (validate_form()) {
			ArrayList<IcfRegApplicantBean> alist =  IcfApplicantManager.getPeriodApplicantsBySchool(form.getInt("irp"),form.getInt("sid"));
			request.setAttribute("reglist", alist);
			IcfRegistrationPeriodBean rbean = IcfRegistrationPeriodManager.getRegistrationPeriodById(form.getInt("irp"));
			request.setAttribute("regbean", rbean);
			request.setAttribute("sname",IcfSchoolManager.getSchoolNameById(form.getInt("sid")));
			request.setAttribute("sid",form.getInt("sid"));
		}else {
			
		}
		

		return "viewregistrantsbyschool.jsp";
	}
}