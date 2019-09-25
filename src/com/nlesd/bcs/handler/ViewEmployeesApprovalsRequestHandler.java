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
import com.nlesd.bcs.constants.BoardOwnedContractorsConstant;
import com.nlesd.bcs.constants.EmployeeStatusConstant;
import com.nlesd.bcs.dao.BussingContractorEmployeeManager;
public class ViewEmployeesApprovalsRequestHandler extends RequestHandlerImpl
{
	public ViewEmployeesApprovalsRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("status")
		});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		if (validate_form()) {
			//now we get the status type
			String status = request.getParameter("status");
			String reporttitle="";
			ArrayList<BussingContractorEmployeeBean> employees = new ArrayList<BussingContractorEmployeeBean>();

			if(usr.checkPermission("BCS-VIEW-WESTERN") || usr.checkPermission("BCS-VIEW-CENTRAL") || usr.checkPermission("BCS-VIEW-LABRADOR")){
				int cid=0;
				if(usr.checkPermission("BCS-VIEW-WESTERN")){
					cid = BoardOwnedContractorsConstant.WESTERN.getValue();
				}
				if(usr.checkPermission("BCS-VIEW-CENTRAL")){
					cid = BoardOwnedContractorsConstant.CENTRAL.getValue();
				}
				if(usr.checkPermission("BCS-VIEW-LABRADOR")){
					cid = BoardOwnedContractorsConstant.LABRADOR.getValue();
				}
				if(status.equals("p")){
					employees=BussingContractorEmployeeManager.getEmployeesByStatusReg(EmployeeStatusConstant.NOTREVIEWED.getValue(),cid);
					reporttitle="Employees Awaiting Approval";
				}else if(status.equals("a")){
					employees=BussingContractorEmployeeManager.getEmployeesByStatusReg(EmployeeStatusConstant.APPROVED.getValue(),cid);
					reporttitle="Employees Approved";
				}else if(status.equals("r")){
					employees=BussingContractorEmployeeManager.getEmployeesByStatusReg(EmployeeStatusConstant.NOTAPPROVED.getValue(),cid);
					reporttitle="Employees Rejected";
				}else if(status.equals("s")){
					employees=BussingContractorEmployeeManager.getEmployeesByStatusReg(EmployeeStatusConstant.SUSPENDED.getValue(),cid);
					reporttitle="Employees Suspended";
				}else if(status.equals("re")){
					employees=BussingContractorEmployeeManager.getEmployeesByStatusReg(EmployeeStatusConstant.REMOVED.getValue(),cid);
					reporttitle="Employees Removed";
				}
			}else{
				if(status.equals("p")){
					employees=BussingContractorEmployeeManager.getEmployeesByStatus(EmployeeStatusConstant.NOTREVIEWED.getValue());
					reporttitle="Employees Awaiting Approval";
				}else if(status.equals("a")){
					employees=BussingContractorEmployeeManager.getEmployeesByStatus(EmployeeStatusConstant.APPROVED.getValue());
					reporttitle="Employees Approved";
				}else if(status.equals("r")){
					employees=BussingContractorEmployeeManager.getEmployeesByStatus(EmployeeStatusConstant.NOTAPPROVED.getValue());
					reporttitle="Employees Rejected";
				}else if(status.equals("s")){
					employees=BussingContractorEmployeeManager.getEmployeesByStatus(EmployeeStatusConstant.SUSPENDED.getValue());
					reporttitle="Employees Suspended";
				}else if(status.equals("re")){
					employees=BussingContractorEmployeeManager.getEmployeesRemoved(EmployeeStatusConstant.REMOVED.getValue());
					reporttitle="Employees Removed";
				}
			}

			request.setAttribute("employees", employees);
			request.setAttribute("reporttitle", reporttitle);
			path = "view_employees_approvals.jsp";
		}else {
			request.setAttribute("msg", com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()));
	    	path = "index.html";	
		}

		return path;
	}
}
