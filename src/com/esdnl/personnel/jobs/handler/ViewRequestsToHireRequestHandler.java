package com.esdnl.personnel.jobs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.esdnl.personnel.jobs.bean.RequestToHireBean;
import com.esdnl.personnel.jobs.constants.RequestToHireStatus;
import com.esdnl.personnel.jobs.dao.RequestToHireManager;
import com.esdnl.servlet.RequestHandlerImpl;
public class ViewRequestsToHireRequestHandler extends RequestHandlerImpl {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		String path;
		HttpSession session = null;
		User usr = null;
		RequestToHireBean[] list = null;

		try {
			session = request.getSession(false);
			if ((session != null) && (session.getAttribute("usr") != null)) {
			      usr = (User) session.getAttribute("usr");
			      if(!(usr.getUserPermissions().containsKey("PERSONNEL-ADREQUEST-APPROVE")
			      || usr.getUserPermissions().containsKey("PERSONNEL-ADREQUEST-POST")))
			      {
			        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
			      }
			}
			else {
				throw new SecurityException("User login required.");
			}
			int statusid = Integer.parseInt(request.getParameter("status"));
			if( statusid == 0){
				list = RequestToHireManager.getRequestsToHireSubmitted();
				request.setAttribute("status", "Submitted");
			}else{
				list = RequestToHireManager.getRequestsToHireByStatus(statusid);
				request.setAttribute("status", RequestToHireStatus.get(statusid).getDescription());
			}
			
			request.setAttribute("requests", list);
			
			path = "admin_view_requests_to_hire.jsp";

		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			path = "admin_view_requests_to_hire.jsp";
		}

		return path;
	}
}
