package com.esdnl.fund3.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.fund3.bean.PolicyBean;
import com.esdnl.fund3.dao.PolicyManager;
import com.esdnl.servlet.RequestHandlerImpl;
public class AddNewPolicyRequestHandler extends RequestHandlerImpl {
	public AddNewPolicyRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {
		super.handleRequest(request, reponse);
		try {
			
			if(form.get("op") == null)
			{
				path = "add_new_policy.jsp";
			}else{
				//check file
					//parse the fields
					PolicyBean bb = new PolicyBean();
					if (form.uploadFileExists("filename"))
					{
						System.out.println("file there");
						
					}else{
						System.out.println("no file");
					}
					bb.setLinkText(form.get("linktext"));
					bb.setFileName(save_file("filename", PolicyBean.DOCUMENT_BASEPATH));
					bb.setIsActive(form.getInt("isactive"));
					bb.setAddedBy(usr.getPersonnel().getFullNameReverse());
					PolicyManager.addNewPolicy(bb);
					request.setAttribute("msg", "Policy has been added");
					request.setAttribute("policies", PolicyManager.getPolicies());
					path = "view_policies.jsp";
			}

		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "add_new_policy.jsp";
		}
		return path;
	}


}
