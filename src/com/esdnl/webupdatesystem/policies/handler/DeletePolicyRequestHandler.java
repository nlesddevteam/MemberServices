package com.esdnl.webupdatesystem.policies.handler;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.policies.bean.PolicyBean;
import com.esdnl.webupdatesystem.policies.bean.PolicyFileBean;
import com.esdnl.webupdatesystem.policies.dao.PoliciesManager;
import com.esdnl.webupdatesystem.policies.dao.PolicyFileManager;

public class DeletePolicyRequestHandler extends RequestHandlerImpl {

	public DeletePolicyRequestHandler() {

		this.requiredRoles = new String[] {
				"ADMINISTRATOR", "WEB DESIGNER", "WEBANNOUNCMENTS-POST"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {

		super.handleRequest(request, reponse);
		try {
			Integer policyid = Integer.parseInt(form.get("pid"));
			PolicyBean pb = PoliciesManager.getPolicyById(policyid);
			//get list of files to delete from server directory
			String filelocation = "/../../nlesdweb/WebContent/includes/files/policies/doc/";
			//get list of files
			List<PolicyFileBean> list = PolicyFileManager.getPoliciesFiles(policyid);

			for (PolicyFileBean pfb : list) {
				delete_file(filelocation, pfb.getPfDoc());
			}
			//delete first two files
			if (!(pb.getPolicyAdminDoc() == null)) {
				delete_file(filelocation, pb.getPolicyAdminDoc());
			}
			delete_file(filelocation, pb.getPolicyDocumentation());
			PoliciesManager.deletePolicy(policyid);
			request.setAttribute("policies", PoliciesManager.getPolicies());
			request.setAttribute("msgOK", "Policy has been deleted");
			path = "view_policies.jsp";
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "view_policies.jsp";
		}
		return path;
	}
}
