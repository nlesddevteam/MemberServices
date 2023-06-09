package com.esdnl.personnel.jobs.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.AdRequestBean;
import com.esdnl.personnel.jobs.bean.TeacherAllocationBean;
import com.esdnl.personnel.jobs.bean.TeacherAllocationVacantPositionBean;
import com.esdnl.personnel.jobs.constants.JobTypeConstant;
import com.esdnl.personnel.jobs.constants.TrainingMethodConstant;
import com.esdnl.personnel.jobs.dao.AdRequestManager;
import com.esdnl.personnel.jobs.dao.TeacherAllocationManager;
import com.esdnl.personnel.jobs.dao.TeacherAllocationVacantPositionManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class CreateVacantPositionAdAjaxRequestHandler extends RequestHandlerImpl {

	public CreateVacantPositionAdAjaxRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("id", "Position ID missing.")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
			IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			try {
				String msg = "";
				TeacherAllocationVacantPositionBean position = null;

				position = TeacherAllocationVacantPositionManager.getTeacherAllocationVacantPositionBean(form.getInt("id"));
				TeacherAllocationBean tab = TeacherAllocationManager.getTeacherAllocationBean(position.getAllocationId());
				AdRequestBean adbean = new AdRequestBean();
				adbean.setTitle(position.getJobDescription());
				adbean.setLocation(tab.getLocation());
				adbean.setVacancyReason(position.getVacancyReason());
				if(position.getTermStart() ==  null) {
					//ad request expecting a start date
					//default to first day of september, can be adjusted
					Calendar cal = Calendar.getInstance();
					String[] ssplit = tab.getSchoolYear().split("-");
					cal.set(Calendar.YEAR,Integer.parseInt(ssplit[0]));
					cal.set(Calendar.MONTH,8);
					cal.set(Calendar.DAY_OF_MONTH,1);
					adbean.setStartDate(cal.getTime());
				}else {
					adbean.setStartDate(position.getTermStart());
				}
				adbean.setEndDate(position.getTermEnd());
				adbean.setUnits(position.getUnit());
				StringBuilder sbb = new StringBuilder();
				sbb.append("Auto-generated by Position Planner");
				sbb.append("<br /><br />");
				sbb.append(position.getJobDescription());
				adbean.setAdText(sbb.toString());
				adbean.setOwner(position.getEmployee());
				adbean.setJobType(JobTypeConstant.REGULAR);
				adbean.setTrainingMethod(TrainingMethodConstant.PRIMARY_ELEMENTARY);
				AdRequestManager.addAdRequestBean(adbean,usr.getPersonnel());
				TeacherAllocationVacantPositionManager.updateTeacherAllocationVacantPositionAdRequest(adbean.getId(),position.getPositionId());
				//get fresh copy with new changes
				tab = TeacherAllocationManager.getTeacherAllocationBean(position.getAllocationId());
				//now we update the position with the ad request id
				//TeacherAllocationVacantPositionManager.updateTeacherAllocationVacantPositionAdRequest(adbean.getId(), position.getPositionId());
				//send email about new ad request

				msg = "Ad Request for Vacant position created successfully.";
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<ADD-TEACHER-ALLOCATION-VACANT-POSITION-RESPONSE msg='" + msg + "'>");
				if (tab != null)
					sb.append(tab.toXML());
				sb.append("</ADD-TEACHER-ALLOCATION-VACANT-POSITION-RESPONSE>");

				xml = StringUtils.encodeXML(sb.toString());

				System.out.println(xml);

				PrintWriter out = response.getWriter();

				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			}
			catch (Exception e) {
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");

				sb.append("<ADD-TEACHER-ALLOCATION-VACANT-POSITION-RESPONSE msg='Permanent position could not be added/updated.<br />"
						+ StringUtils.encodeHTML2(e.getMessage()) + "' />");

				xml = StringUtils.encodeXML(sb.toString());

				System.out.println(xml);

				PrintWriter out = response.getWriter();

				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			}
		}
		else {
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");

			sb.append("<ADD-TEACHER-ALLOCATION-VACANT-POSITION-RESPONSE msg='" + this.validator.getErrorString() + "' />");

			xml = StringUtils.encodeXML(sb.toString());

			System.out.println(xml);

			PrintWriter out = response.getWriter();

			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
		}

		return null;
	}

}