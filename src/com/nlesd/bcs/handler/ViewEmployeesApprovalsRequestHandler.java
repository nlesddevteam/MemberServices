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
			//now we get the status type;
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
					employees=BussingContractorEmployeeManager.getEmployeesByStatusRegFull(EmployeeStatusConstant.SUBMITTEDFORREVIEW.getValue(),cid);
					reporttitle="Employees Awaiting Approval";
				}else if(status.equals("a")){
					employees=BussingContractorEmployeeManager.getEmployeesByStatusRegFull(EmployeeStatusConstant.APPROVED.getValue(),cid);
					reporttitle="Employees Approved";
				}else if(status.equals("r")){
					employees=BussingContractorEmployeeManager.getEmployeesByStatusRegFull(EmployeeStatusConstant.NOTAPPROVED.getValue(),cid);
					reporttitle="Employees Rejected";
				}else if(status.equals("s")){
					employees=BussingContractorEmployeeManager.getEmployeesByStatusRegFull(EmployeeStatusConstant.SUSPENDED.getValue(),cid);
					reporttitle="Employees Suspended";
				}else if(status.equals("re")){
					employees=BussingContractorEmployeeManager.getEmployeesByStatusRegFull(EmployeeStatusConstant.REMOVED.getValue(),cid);
					reporttitle="Employees Removed";
				}else if(status.equals("ns")){
					employees=BussingContractorEmployeeManager.getEmployeesByStatusRegFull(EmployeeStatusConstant.NOTREVIEWED.getValue(),cid);
					reporttitle="Employees Not Submitted";
				}else if(status.equals("oh")){
					employees=BussingContractorEmployeeManager.getEmployeesByStatusRegFull(EmployeeStatusConstant.ONHOLD.getValue(),cid);
					reporttitle="Employees Temporarily On Hold";
				}
			}else{
				if(status.equals("p")){
					employees=BussingContractorEmployeeManager.getEmployeesByStatusFull(EmployeeStatusConstant.SUBMITTEDFORREVIEW.getValue());
					reporttitle="Employees Awaiting Approval";
				}else if(status.equals("a")){
					employees=BussingContractorEmployeeManager.getEmployeesByStatusFull(EmployeeStatusConstant.APPROVED.getValue());
					reporttitle="Employees Approved";
				}else if(status.equals("r")){
					employees=BussingContractorEmployeeManager.getEmployeesByStatusFull(EmployeeStatusConstant.NOTAPPROVED.getValue());
					reporttitle="Employees Rejected";
				}else if(status.equals("s")){
					employees=BussingContractorEmployeeManager.getEmployeesByStatusFull(EmployeeStatusConstant.SUSPENDED.getValue());
					reporttitle="Employees Suspended";
				}else if(status.equals("re")){
					employees=BussingContractorEmployeeManager.getRemovedEmployees(EmployeeStatusConstant.REMOVED.getValue());
					reporttitle="Employees Removed";
				}else if(status.equals("ns")){
					employees=BussingContractorEmployeeManager.getEmployeesByStatusFull(EmployeeStatusConstant.NOTREVIEWED.getValue());
					reporttitle="Employees Not Submitted";
				}else if(status.equals("oh")){
					employees=BussingContractorEmployeeManager.getEmployeesByStatusFull(EmployeeStatusConstant.ONHOLD.getValue());
					reporttitle="Employees Temporarily On Hold";
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
