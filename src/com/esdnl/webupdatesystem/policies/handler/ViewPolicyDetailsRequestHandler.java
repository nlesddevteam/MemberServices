package com.esdnl.webupdatesystem.policies.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.policies.constants.PolicyCategory;
import com.esdnl.webupdatesystem.policies.constants.PolicyStatus;
import com.esdnl.webupdatesystem.policies.dao.PoliciesManager;
import com.esdnl.webupdatesystem.policies.dao.PolicyFileManager;
public class ViewPolicyDetailsRequestHandler extends RequestHandlerImpl {
	public ViewPolicyDetailsRequestHandler() {

		this.requiredRoles = new String[] {
				"ADMINISTRATOR","WEB DESIGNER","WEBANNOUNCMENTS-POST"
			};
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {
		super.handleRequest(request, reponse);
	    try {
			Map<Integer,String> categorylist = new HashMap<Integer,String>();
			for(PolicyCategory t : PolicyCategory.ALL)
			{
				categorylist.put(t.getValue(),t.getDescription());
			}
			request.setAttribute("categorylist", categorylist);
			Map<Integer,String> statuslist = new HashMap<Integer,String>();
			for(PolicyStatus t : PolicyStatus.ALL)
			{
				statuslist.put(t.getValue(),t.getDescription());
			}
			request.setAttribute("statuslist", statuslist);
			request.setAttribute("policy",PoliciesManager.getPolicyById(Integer.parseInt(request.getParameter("id").toString())));
			
			request.setAttribute("otherfiles", PolicyFileManager.getPoliciesFiles(Integer.parseInt(request.getParameter("id").toString())));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    path = "view_policy_details.jsp";
	    return path;
	}
}
