package com.esdnl.webupdatesystem.policies.handler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.webupdatesystem.policies.bean.PolicyBean;
import com.esdnl.webupdatesystem.policies.constants.PolicyCategory;
import com.esdnl.webupdatesystem.policies.constants.PolicyStatus;
import com.esdnl.webupdatesystem.policies.dao.PoliciesManager;

import javazoom.upload.UploadFile;

public class AddNewPolicyRequestHandler extends RequestHandlerImpl {

	public AddNewPolicyRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("policy_number", "Policy Number is required."),
				new RequiredFormElement("policy_title", "Policy Title is required."),
				new RequiredFormElement("policy_status", "Policy Status is required."),
				new RequiredFormElement("policy_category", "Policy Status is required.")
		});
		this.requiredRoles = new String[] {
				"ADMINISTRATOR", "WEB DESIGNER", "WEBANNOUNCMENTS-POST"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {

		super.handleRequest(request, reponse);
		boolean fileokd = false;
		String filelocation = "";
		String docfilename = "";
		String adminfilename = "";

		UploadFile file = null;
		try {
			if (form.get("op") == null) {
				Map<Integer, String> categorylist = new HashMap<Integer, String>();
				for (PolicyCategory t : PolicyCategory.ALL) {
					categorylist.put(t.getValue(), t.getDescription());
				}
				request.setAttribute("categorylist", categorylist);
				Map<Integer, String> statuslist = new HashMap<Integer, String>();
				for (PolicyStatus t : PolicyStatus.ALL) {
					statuslist.put(t.getValue(), t.getDescription());
				}
				request.setAttribute("statuslist", statuslist);
				path = "add_new_policy.jsp";

			}
			else {
				//check file
				if (form.uploadFileExists("policy_documentation")) {
					file = (UploadFile) form.getUploadFiles().get("policy_documentation");
					if ((file.getFileName().indexOf(".pdf") < 0) && (file.getFileName().indexOf(".PDF") < 0)) {
						request.setAttribute("msgERR", "Only PDF Tender Files Accepted");
					}
					else {
						//save the file
						filelocation = "/../../nlesdweb/WebContent/includes/files/policies/doc/";
						docfilename = save_file("policy_documentation", filelocation);
						fileokd = true;
					}
				}
				else {
					request.setAttribute("msgERR", "Please Select Policy Documentation For Upload");
				}
				if (form.uploadFileExists("policy_admin_doc")) {
					//save the file
					filelocation = "/../../nlesdweb/WebContent/includes/files/policies/doc/";
					adminfilename = save_file("policy_admin_doc", filelocation);

				}
				if (validate_form() && fileokd) {
					//parse the fields
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					PolicyBean bb = new PolicyBean();
					bb.setPolicyCategory(PolicyCategory.get(Integer.parseInt(form.get("policy_category").toString())));
					bb.setPolicyStatus(PolicyStatus.get(Integer.parseInt(form.get("policy_status").toString())));
					bb.setPolicyNumber(form.get("policy_number").toString());
					bb.setPolicyTitle(form.get("policy_title").toString());
					bb.setPolicyDocumentation(docfilename);
					bb.setPolicyAdminDoc(adminfilename);
					bb.setAddedBy(usr.getPersonnel().getFullNameReverse());
					int id = PoliciesManager.addPolicy(bb);
					Map<Integer, String> categorylist = new HashMap<Integer, String>();
					for (PolicyCategory t : PolicyCategory.ALL) {
						categorylist.put(t.getValue(), t.getDescription());
					}
					request.setAttribute("categorylist", categorylist);
					Map<Integer, String> statuslist = new HashMap<Integer, String>();
					for (PolicyStatus t : PolicyStatus.ALL) {
						statuslist.put(t.getValue(), t.getDescription());
					}
					request.setAttribute("statuslist", statuslist);
					path = "add_new_policy.jsp";
					request.setAttribute("msgOK", "Success: Policy has been added");
				}
				path = "view_policies.jsp";
			}
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			Map<Integer, String> categorylist = new HashMap<Integer, String>();
			for (PolicyCategory t : PolicyCategory.ALL) {
				categorylist.put(t.getValue(), t.getDescription());
			}
			request.setAttribute("categorylist", categorylist);
			Map<Integer, String> statuslist = new HashMap<Integer, String>();
			for (PolicyStatus t : PolicyStatus.ALL) {
				statuslist.put(t.getValue(), t.getDescription());
			}
			request.setAttribute("statuslist", statuslist);

			path = "add_new_policy.jsp";

		}
		return path;
	}
}
