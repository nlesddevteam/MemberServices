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
import com.nlesd.bcs.bean.BussingContractorSystemETReportBean;
import com.nlesd.bcs.dao.BussingContractorSystemETReportManager;

public class ViewEmployeeTrainingDetailsRequestHandler extends RequestHandlerImpl
{
	public ViewEmployeeTrainingDetailsRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("tid"),new RequiredFormElement("tstatus")
		});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		if (validate_form()) {
			//now we get the status type;
			int status = Integer.parseInt(request.getParameter("tstatus"));
			int tid = Integer.parseInt(request.getParameter("tid"));
			String ttype = request.getParameter("tstring");
			String reporttitle="";
			ArrayList<BussingContractorSystemETReportBean> employees = new ArrayList<BussingContractorSystemETReportBean>();
			if(status == 0) {
				employees=BussingContractorSystemETReportManager.getEmployeeTrainingByStatus(tid, status);
				reporttitle = ttype + " Not Completed";
			}else {
				reporttitle = ttype + " Completed";
				employees=BussingContractorSystemETReportManager.getEmployeeTrainingByStatus(tid, status);
			}
			request.setAttribute("employees", employees);
			request.setAttribute("reporttitle", reporttitle);
			path = "view_employee_training_details_report.jsp";
			        
			
		}else {
			request.setAttribute("msg", com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()));
	    	path = "index.html";	
		}

		return path;
	}
}