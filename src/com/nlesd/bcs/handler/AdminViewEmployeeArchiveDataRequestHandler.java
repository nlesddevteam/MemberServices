package com.nlesd.bcs.handler;
import java.io.IOException;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.bcs.bean.BussingContractorEmployeeBean;
import com.nlesd.bcs.dao.BussingContractorEmployeeManager;
public class AdminViewEmployeeArchiveDataRequestHandler extends RequestHandlerImpl {
	public AdminViewEmployeeArchiveDataRequestHandler() {
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
			TreeMap<Integer,BussingContractorEmployeeBean> list = new TreeMap<Integer,BussingContractorEmployeeBean>();
			BussingContractorEmployeeBean ebean = BussingContractorEmployeeManager.getBussingContractorEmployeeById(cid);
			list.put(0, ebean);
			BussingContractorEmployeeManager.getEmployeeArchiveRecordsById(cid, list);
			request.setAttribute("employees", list);
			request.setAttribute("empname",ebean.getLastName() + "," + ebean.getFirstName());
			request.setAttribute("conname",ebean.getBcBean().getContractorName());
			path = "admin_view_employee_archive.jsp";
		}else {
			request.setAttribute("msg", com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()));
	    	path = "index.html";				
		}
		return path;
	}

}