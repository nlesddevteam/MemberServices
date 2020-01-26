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
import com.nlesd.bcs.constants.StatusConstant;
import com.nlesd.bcs.dao.BussingContractorManager;
public class ViewContractorsApprovalsRequestHandler extends RequestHandlerImpl
{
	public ViewContractorsApprovalsRequestHandler() {
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
			ArrayList<BussingContractorBean> contractors = new ArrayList<BussingContractorBean>();
			if(status.equals("p")){
				contractors=BussingContractorManager.getContractorsByStatusFull(StatusConstant.SUBMITTED.getValue());
				reporttitle="Contractors Awaiting Approval";
			}else if(status.equals("a")){
				contractors=BussingContractorManager.getContractorsByStatusFull(StatusConstant.APPROVED.getValue());
				reporttitle="Contactors Approved";
			}else if(status.equals("r")){
				contractors=BussingContractorManager.getContractorsByStatusFull(StatusConstant.REJECTED.getValue());
				reporttitle="Contractors Rejected";
			}else if(status.equals("s")){
				contractors=BussingContractorManager.getContractorsByStatusFull(StatusConstant.SUSPENDED.getValue());
				reporttitle="Contractors Suspended";
			}else if(status.equals("re")){
				contractors=BussingContractorManager.getContractorsByStatusFull(StatusConstant.REMOVED.getValue());
				reporttitle="Contractors Removed";
			}
			request.setAttribute("contractors", contractors);
			request.setAttribute("reporttitle", reporttitle);
			path = "view_contractors_approvals.jsp";
		}else {
			request.setAttribute("msg", com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()));
	    	path = "index.html";
		}				
		return path;
	}
}