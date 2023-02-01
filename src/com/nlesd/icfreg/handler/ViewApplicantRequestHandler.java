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
import com.nlesd.icfreg.bean.IcfSchoolBean;
import com.nlesd.icfreg.dao.IcfApplicantManager;
import com.nlesd.icfreg.dao.IcfRegistrationPeriodManager;
import com.nlesd.icfreg.dao.IcfSchoolManager;

public class ViewApplicantRequestHandler extends RequestHandlerImpl {

	public ViewApplicantRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("irp")
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
			//get applicant
			IcfRegApplicantBean abean = IcfApplicantManager.getApplicantById(form.getInt("irp"));
			request.setAttribute("regbean", abean);
			IcfRegistrationPeriodBean rbean = IcfRegistrationPeriodManager.getRegistrationPeriodById(abean.getIcfAppRegPer());
			request.setAttribute("perbean", rbean);
			request.setAttribute("ReturnURL", "/schools/registration/icfreg/admin/viewPeriodRegistrants.html?irp=" + rbean.getIcfRegPerId());
			ArrayList<IcfSchoolBean> slist = IcfSchoolManager.getRegistrationPeriodSchools(abean.getIcfAppRegPer());
			request.setAttribute("slist", slist);
		}else {
			
		}
		if(form.get("vtype").equals("A")) {
			return "editapplicant.jsp";
		}else {
			return "viewapplicant.jsp";
		}

		
	}
}
