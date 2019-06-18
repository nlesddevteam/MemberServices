package com.esdnl.fund3.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.fund3.bean.PolicyBean;
import com.esdnl.fund3.dao.PolicyManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class UpdatePolicyRequestHandler extends RequestHandlerImpl {
	public UpdatePolicyRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {
		super.handleRequest(request, reponse);
		try {
				//check file
				//parse the fields
				Integer id=form.getInt("id");
				PolicyBean bb = PolicyManager.getPolicyById(id);
				String filename="";
				if (form.uploadFileExists("filename"))
				{
					//save new file
					filename=save_file("filename", PolicyBean.DOCUMENT_BASEPATH);
					//delete oldfile
					delete_file(PolicyBean.DOCUMENT_BASEPATH, bb.getFileName());
					
				}else{
					filename=bb.getFileName();
				}
				bb.setLinkText(form.get("linktext"));
				bb.setIsActive(form.getInt("isactive"));
				bb.setFileName(filename);
				bb.setAddedBy(usr.getPersonnel().getFullNameReverse());
				PolicyManager.updateNewPolicy(bb);
				request.setAttribute("msg", "Policy has been updated");
				request.setAttribute("policies", PolicyManager.getPolicies());
				path = "view_policies.jsp";
			

		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "add_new_policy.jsp";
		}
		return path;
	}


}
