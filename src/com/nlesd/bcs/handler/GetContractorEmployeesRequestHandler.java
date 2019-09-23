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
import com.nlesd.bcs.bean.BussingContractorEmployeeBean;
import com.nlesd.bcs.dao.BussingContractorEmployeeManager;
import com.nlesd.bcs.dao.BussingContractorManager;
public class GetContractorEmployeesRequestHandler extends RequestHandlerImpl {
	public GetContractorEmployeesRequestHandler() {
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
			int contractorid = form.getInt("cid");
			ArrayList<BussingContractorEmployeeBean> list = new ArrayList<BussingContractorEmployeeBean>();
			BussingContractorBean cbean = null;
			if(contractorid == 0){
				list = BussingContractorEmployeeManager.getAllContractorsEmployees();
			}else{
				cbean = BussingContractorManager.getBussingContractorById(contractorid);
				list = BussingContractorEmployeeManager.getContractorsEmployees(contractorid);
			}
			request.setAttribute("employees", list);
			String cname = "";
			if(contractorid == 0){
				cname="All";
			}else{
				if(cbean.getCompany() != null){
					cname=cbean.getLastName() + ", " + cbean.getFirstName() + "(" + cbean.getCompany() + ")";
				}else{
					cname=cbean.getLastName() + ", " + cbean.getFirstName();
				}
			}
			
			request.setAttribute("companyname",cname);
			path = "view_employees_details_report.jsp";
		}else {
			request.setAttribute("msg", com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()));
	    	path = "index.html";
		}
		
			
		return path;
	}

}
