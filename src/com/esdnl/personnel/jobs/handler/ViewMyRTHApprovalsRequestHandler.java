package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.RequestToHireBean;
import com.esdnl.personnel.jobs.constants.RequestToHireStatus;
import com.esdnl.personnel.jobs.dao.RequestToHireManager;
import com.esdnl.servlet.RequestHandlerImpl;
import com.awsd.security.Role;
public class ViewMyRTHApprovalsRequestHandler extends RequestHandlerImpl {
	public ViewMyRTHApprovalsRequestHandler() {
		this.requiredPermissions = new String[] {
			"PERSONNEL-RTH-VIEW-APPROVALS"
		};
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		RequestToHireBean[] list = null;
		RequestToHireBean[] list2 = null;
		try {
			//first we have to determine what roles they are in
			for (Map.Entry<String,Role> entry :usr.getUserRoles().entrySet()) {
				if(entry.getKey().startsWith("RTH"))
				{
					if(entry.getKey().endsWith("HR-AD")){
						//this is Assistant Director HR
						//status only value we need
						if(list == null) {
							list = RequestToHireManager.getRequestsToHireByStatus(RequestToHireStatus.APPROVEDASSISTANT.getValue());
						}else {
							list2 = RequestToHireManager.getRequestsToHireByStatus(RequestToHireStatus.APPROVEDASSISTANT.getValue());
						}
					}else if(entry.getKey().endsWith("-BC")){
						//comptroller
						//status only value we need
						if(list == null) {
							list = RequestToHireManager.getRequestsToHireByStatus(RequestToHireStatus.APPROVEDDIVISION.getValue());
						}else {
							list2 = RequestToHireManager.getRequestsToHireByStatus(RequestToHireStatus.APPROVEDDIVISION.getValue());
						}
					}else if(entry.getKey().endsWith("-AD")){
						//associate director
						//now we break up the name and find out zone and division
						String[] groups = entry.getKey().split("-");
						if(groups.length ==  3) {
							if(list == null) {
								list = RequestToHireManager.getRequestsToHireApprovalsAD(RequestToHireManager.getDivisionID(groups[1]),
										RequestToHireStatus.APPROVEDBUDGET.getValue());
							}else {
								list2 = RequestToHireManager.getRequestsToHireApprovalsAD(RequestToHireManager.getDivisionID(groups[1]),
										RequestToHireStatus.APPROVEDBUDGET.getValue());
							}
						}
					}else if(entry.getKey().endsWith("-DD")) {
						//division director
						//now we break up the name and find out zone and division
						String[] groups = entry.getKey().split("-");
						if(groups.length ==  4) {
							if(list == null) {
								list = RequestToHireManager.getRequestsToHireApprovalsDD(RequestToHireManager.getDivisionID(groups[2]),RequestToHireManager.getZoneID(groups[1]));
								
							}else {
								list2 = RequestToHireManager.getRequestsToHireApprovalsDD(RequestToHireManager.getDivisionID(groups[2]),RequestToHireManager.getZoneID(groups[1]));
								
							}
						}
					}
				}
			}
			//now we check to see if they were a member of two groups
			if(list2 != null) {
				//now we merge the first and second list
				RequestToHireBean[] clist = new RequestToHireBean[list.length + list2.length];
				System.arraycopy(list,0, clist, 0, list.length);
				System.arraycopy(list2,0, clist, 0, list2.length);
				request.setAttribute("requests", clist);
			}else {
				request.setAttribute("requests", list);
			}
			
			path = "admin_view_my_requests_to_hire_approvals.jsp";
			
		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			path = "admin_view_my_requests_to_hire_approvals.jsp";
		}

		return path;
	}
}