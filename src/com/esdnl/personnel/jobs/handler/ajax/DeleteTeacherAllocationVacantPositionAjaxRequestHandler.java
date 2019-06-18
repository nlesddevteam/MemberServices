package com.esdnl.personnel.jobs.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.TeacherAllocationBean;
import com.esdnl.personnel.jobs.bean.TeacherAllocationVacantPositionBean;
import com.esdnl.personnel.jobs.constants.RequestStatus;
import com.esdnl.personnel.jobs.dao.AdRequestManager;
import com.esdnl.personnel.jobs.dao.TeacherAllocationManager;
import com.esdnl.personnel.jobs.dao.TeacherAllocationVacantPositionManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class DeleteTeacherAllocationVacantPositionAjaxRequestHandler extends RequestHandlerImpl {

	public DeleteTeacherAllocationVacantPositionAjaxRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("id", "Position ID missing.")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);
		boolean isdeleted=false;
		if (validate_form()) {
			try {

				TeacherAllocationBean allocation = null;

				TeacherAllocationVacantPositionBean position = TeacherAllocationVacantPositionManager.getTeacherAllocationVacantPositionBean(form.getInt("id"));
				/*
				if (position != null) {
					//now we check to see if there is ajob ad posted for the postion, if not then we can delete
					if(position.getAdRequest() == null){
						//no add then delete
						TeacherAllocationVacantPositionManager.deleteTeacherAllocationVacantPositionBean(position);
						allocation = TeacherAllocationManager.getTeacherAllocationBean(position.getAllocationId());
						isdeleted=true;
					}else{
						if(!(position.getAdRequest().getCurrentStatus()== RequestStatus.POSTED)){
							//not posted so we can delete it
							TeacherAllocationVacantPositionManager.deleteTeacherAllocationVacantPositionBean(position);
							allocation = TeacherAllocationManager.getTeacherAllocationBean(position.getAllocationId());
							//plus delete the ad request
							AdRequestManager.deleteAdRequestBean(position.getAdRequest().getId());
							isdeleted=true;
						}else{
							allocation = TeacherAllocationManager.getTeacherAllocationBean(position.getAllocationId());
						}
					}
					

				}
				*/
				if (position != null) {
					TeacherAllocationVacantPositionManager.deleteTeacherAllocationVacantPositionBean(position);

					allocation = TeacherAllocationManager.getTeacherAllocationBean(position.getAllocationId());
				}

				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				//if(isdeleted){
					sb.append("<DEL-TEACHER-ALLOCATION-VACANT-POSITION-RESPONSE msg='Vacant position deleted successfully.'>");
				//}else{
					//sb.append("<DEL-TEACHER-ALLOCATION-VACANT-POSITION-RESPONSE msg='Vacant position not delete job ad posted.'>");
			//	}
				
				if (allocation != null)
					sb.append(allocation.toXML());
				sb.append("</DEL-TEACHER-ALLOCATION-VACANT-POSITION-RESPONSE>");

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

				sb.append("<DEL-TEACHER-ALLOCATION-VACANT-POSITION-RESPONSE msg='Vacant position could not be deleted.<br />"
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

			sb.append("<DEL-TEACHER-ALLOCATION-VACANT-POSITION-RESPONSE msg='" + this.validator.getErrorString() + "' />");

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
