package com.nlesd.icfreg.handler;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.bean.EmailException;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.velocity.VelocityUtils;
import com.nlesd.icfreg.bean.IcfRegApplicantBean;
import com.nlesd.icfreg.bean.IcfRegistrationPeriodBean;
import com.nlesd.icfreg.constants.IcfRegistrationStatusConstant;
import com.nlesd.icfreg.dao.IcfApplicantManager;
import com.nlesd.icfreg.dao.IcfRegistrationPeriodManager;

public class AddNewIcfApplicantRequestHandler extends PublicAccessRequestHandlerImpl {
	public AddNewIcfApplicantRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("txt_StudentName"),
				new RequiredFormElement("txt_GuardianName"),
				new RequiredFormElement("txt_ParentGuardianEmail"),
				new RequiredFormElement("txt_ContactNumber1"),
				new RequiredFormElement("selSchool"),
				new RequiredFormElement("registration_id")
				
		});
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		if (validate_form()) {
			IcfRegApplicantBean rbean = new IcfRegApplicantBean();
			rbean.setIcfAppFullName(form.get("txt_StudentName"));
			rbean.setIcfAppGuaFullName(form.get("txt_GuardianName"));
			rbean.setIcfAppEmail(form.get("txt_ParentGuardianEmail"));
			rbean.setIcfAppContact1(form.get("txt_ContactNumber1"));
			rbean.setIcfAppContact2(form.get("txt_ContactNumber2"));
			rbean.setIcfAppRegPer(form.getInt("registration_id"));
			rbean.setIcfAppSchool(form.getInt("selSchool"));
			rbean.setIcfAppStatus(IcfRegistrationStatusConstant.SUBMITTED.getValue());
			rbean.setIcfAppDateSubmitted(new Date());
			//get school name for confirmation
			rbean.setIcfAppSchoolName(form.get("hidschool"));
			IcfApplicantManager.addIcfRegApplicantBean(rbean);
			IcfRegistrationPeriodBean pbean = IcfRegistrationPeriodManager.getRegistrationPeriodById(rbean.getIcfAppRegPer());
			//send email
			//send confirmation email.	
			HashMap<String, Object> model = new HashMap<String, Object>();
			model.put("studentname", rbean.getIcfAppFullName());
			model.put("schoolname", rbean.getIcfAppSchoolName());
			model.put("schoolyear", pbean.getIcfRegPerSchoolYear());
			model.put("studentname", rbean.getIcfAppFullName());
			model.put("regdate", rbean.getIcfAppDateSubmittedFormatted());
			try {
				EmailBean email = new EmailBean();
				email.setTo(rbean.getIcfAppEmail());
				email.setSubject("Newfoundland and Labrador English School District - ICF Registration - Application Received");
				email.setBody(VelocityUtils.mergeTemplateIntoString(
						"schools/icfregistration/registration_confirmation.vm", model));
				email.send();
			}
			catch (EmailException e) {
				e.printStackTrace();
			}
			
			
			
			request.setAttribute("regper", pbean);
			request.setAttribute("reg", rbean);
			return "confirmation.jsp";
			
		}else {
			return "index.jsp";
		}
		
		
		
	}
}
