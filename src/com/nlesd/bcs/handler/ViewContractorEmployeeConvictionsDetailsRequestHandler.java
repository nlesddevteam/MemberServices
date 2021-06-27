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
import com.nlesd.bcs.bean.BussingContractorEmployeeBean;
import com.nlesd.bcs.dao.BussingContractorEmployeeManager;

public class ViewContractorEmployeeConvictionsDetailsRequestHandler extends RequestHandlerImpl {
	public ViewContractorEmployeeConvictionsDetailsRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("cid")
		});
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		if (validate_form()) {
			int cid = form.getInt("cid");
			ArrayList<BussingContractorEmployeeBean> list = new ArrayList<BussingContractorEmployeeBean>();
			if(cid == 0){
				list = BussingContractorEmployeeManager.getContractorEmployeeConvictionsAll();
			}else{
				list = BussingContractorEmployeeManager.getContractorEmployeeConvictionsByType(cid);
			}
			request.setAttribute("employees", list);
			path = "view_employee_convictions_details_report.jsp";
		}else {
			request.setAttribute("msg", com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()));
	    	path = "index.html";
		}
		
			
		return path;
	}

}