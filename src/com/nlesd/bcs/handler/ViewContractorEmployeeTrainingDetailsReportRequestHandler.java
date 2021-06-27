package com.nlesd.bcs.handler;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.bean.BussingContractorSystemETReportBean;
import com.nlesd.bcs.dao.BussingContractorManager;
import com.nlesd.bcs.dao.BussingContractorSystemETReportManager;

public class ViewContractorEmployeeTrainingDetailsReportRequestHandler extends RequestHandlerImpl
{
	public ViewContractorEmployeeTrainingDetailsReportRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("tid")
		});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		if (validate_form()) {
			//now we get the status type;
			int tid = Integer.parseInt(request.getParameter("tid"));
			BussingContractorBean bbean = BussingContractorManager.getBussingContractorById(tid);
			String reporttitle="";
			ArrayList<BussingContractorSystemETReportBean> employees = new ArrayList<BussingContractorSystemETReportBean>();
			employees=BussingContractorSystemETReportManager.getEmployeeTrainingByCompany(tid);
			reporttitle = "View Employee Training: " + bbean.getCompany();
			request.setAttribute("employees", employees);
			request.setAttribute("reporttitle", reporttitle);
			path = "view_contractor_employee_training_details_report.jsp";
			        
			
		}else {
			request.setAttribute("msg", com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()));
	    	path = "index.html";	
		}

		return path;
	}
}
