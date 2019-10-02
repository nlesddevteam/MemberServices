package com.esdnl.personnel.jobs.handler;
import java.io.IOException;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.personnel.*;
import com.esdnl.personnel.jobs.bean.RequestToHireBean;
import com.esdnl.personnel.jobs.bean.RequestToHireHistoryBean;
import com.esdnl.personnel.jobs.dao.RequestToHireHistoryManager;
import com.esdnl.personnel.jobs.dao.RequestToHireManager;
import com.esdnl.servlet.RequestHandlerImpl;
import com.awsd.travel.service.DivisionService;
public class AddRequestToHireRequestHandler extends RequestHandlerImpl {

	public AddRequestToHireRequestHandler() {

		requiredPermissions = new String[] {
			"PERSONNEL-ADREQUEST-REQUEST"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
				//set view approve to false, if view existing one it will change the rights if necessary
				request.setAttribute("VIEWAPPROVE", false);
				if(!(request.getParameter("rid") == null)){
					RequestToHireBean rbean =RequestToHireManager.getRequestToHireById(Integer.parseInt(request.getParameter("rid")));
					request.setAttribute("rbean",rbean );
					//now determine if user has rights to approve
					request.setAttribute("VIEWAPPROVE", RequestToHireManager.checkViewApproveButton(rbean, usr));
					//get history objects
					TreeMap<Integer, RequestToHireHistoryBean> hbeans = RequestToHireHistoryManager.getRequestsToHireHistory(Integer.parseInt(request.getParameter("rid")));
					request.setAttribute("HBEANS",hbeans);
				}
				request.setAttribute("SUPERVISORS", new Supervisors());
				request.setAttribute("DIVISIONS", DivisionService.getDivisions());
				request.setAttribute("unioncodes", RequestToHireManager.getUnionCodes());
				
				path = "admin_request_to_hire.jsp";
		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			path = "admin_request_to_hire.jsp";
		}

		return path;
	}
}
