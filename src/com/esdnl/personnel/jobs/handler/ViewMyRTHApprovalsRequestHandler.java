package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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
		ArrayList<RequestToHireBean> mainlist = new ArrayList<RequestToHireBean>();
		try {
			//first we have to determine what roles they are in
			
			for (Map.Entry<String,Role> entry :usr.getUserRoles().entrySet()) {
				if(entry.getKey().startsWith("RTH"))
				{
					if(entry.getKey().endsWith("HR-AD")){
						Collections.addAll(mainlist, RequestToHireManager.getRequestsToHireByStatus(RequestToHireStatus.APPROVEDASSISTANT.getValue()));
							
					}else if(entry.getKey().endsWith("-BC")){
						Collections.addAll(mainlist,RequestToHireManager.getRequestsToHireByStatus(RequestToHireStatus.APPROVEDDIVISION.getValue()));
					}else if(entry.getKey().endsWith("-AD")){
						//associate director
						//now we break up the name and find out zone and division
						String[] groups = entry.getKey().split("-");
						if(groups.length ==  3) {
							Collections.addAll(mainlist,RequestToHireManager.getRequestsToHireApprovalsAD(RequestToHireManager.getDivisionID(groups[1]),
										RequestToHireStatus.APPROVEDBUDGET.getValue()));
						}
					}else if(entry.getKey().endsWith("-DD")) {
						//division director
						//now we break up the name and find out zone and division
						String[] groups = entry.getKey().split("-");
						if(groups.length ==  4) {
							Collections.addAll(mainlist,RequestToHireManager.getRequestsToHireApprovalsDD(RequestToHireManager.getDivisionID(groups[2]),RequestToHireManager.getZoneID(groups[1])));
							//check to see if any requests for facilites with status 13
							if(groups[2].contentEquals("FAC")) {
								Collections.addAll(mainlist,RequestToHireManager.getRequestsToHireApprovalsDDMan(5,RequestToHireManager.getZoneID(groups[1]),13));
							}	
						}
						
						
					}else if(entry.getKey().endsWith("-MAN")) {
						//manager approval
						//now we break up the name and find out zone and division
						//stop duplicates since just hauling by status not school breakdown
						String[] groups = entry.getKey().split("-");
						if(groups.length == 4) {
							Collections.addAll(mainlist,RequestToHireManager.getRequestsToHireApprovalsMan(5,RequestToHireStatus.SUBMITTEDREGIONALMANAGER.getValue(),groups[1]));
							
						}
						
					}
				}
			}
			//remove duplicates for burin/vista groups
			HashSet<RequestToHireBean> hashSet = new HashSet(mainlist);
			request.setAttribute("requests", hashSet);
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