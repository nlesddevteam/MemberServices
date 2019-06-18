package com.esdnl.fund3.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.fund3.bean.PolicyBean;
import com.esdnl.fund3.dao.PolicyManager;
import com.esdnl.servlet.RequestHandlerImpl;
public class DeletePolicyRequestHandler extends RequestHandlerImpl {
	public DeletePolicyRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {
		super.handleRequest(request, reponse);
		try {
			Integer fileId=Integer.parseInt(request.getParameter("pid").toString());
			PolicyBean pb = PolicyManager.getPolicyById(fileId);
			delete_file(PolicyBean.DOCUMENT_BASEPATH, pb.getFileName());
			PolicyManager.deletePolicy(fileId);
			request.setAttribute("msg", "Policy has been deleted");
			
			path = "viewPolicies.html";
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "viewPolicies.html";
		}
		return path;
	}
}
