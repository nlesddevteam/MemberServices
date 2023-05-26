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
import com.esdnl.webupdatesystem.blogs.bean.BlogsException;
import com.esdnl.webupdatesystem.policies.bean.PolicyBean;
import com.esdnl.webupdatesystem.policies.constants.PolicyCategory;
import com.esdnl.webupdatesystem.policies.constants.PolicyStatus;
import com.esdnl.webupdatesystem.policies.dao.PoliciesManager;

import javazoom.upload.UploadFile;

public class UpdatePolicyRequestHandler extends RequestHandlerImpl {

	public UpdatePolicyRequestHandler() {

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
		String filelocation = "/../../nlesdweb/WebContent/includes/files/policies/doc/";
		boolean fileokd = false;
		String docfilename = "";
		String adminfilename = "";
		UploadFile file = null;
		PolicyBean pbo = null;
		try {
			//get copy of original to use for file update and deletion
			pbo = PoliciesManager.getPolicyById(form.getInt("id"));
			//check file
			if (form.uploadFileExists("policy_documentation")) {
				file = (UploadFile) form.getUploadFiles().get("policy_documentation");
				if ((file.getFileName().indexOf(".pdf") < 0) && (file.getFileName().indexOf(".PDF") < 0)) {
					request.setAttribute("msgERR", "Only PDF Files Accepted");
				}
				else {
					//save the file
					docfilename = save_file("policy_documentation", filelocation);
					fileokd = true;
				}
			}
			else {
				//no new file uploaded so we will retrieve the exisiting info
				docfilename = pbo.getPolicyDocumentation();
				fileokd = true;
			}
			if (form.uploadFileExists("policy_admin_doc")) {
				file = (UploadFile) form.getUploadFiles().get("policy_admin_doc");
				if ((file.getFileName().indexOf(".pdf") < 0) && (file.getFileName().indexOf(".PDF") < 0)) {
					request.setAttribute("msgERR", "Only PDF  Files Accepted");
				}
				else {
					//save the file
					adminfilename = save_file("policy_admin_doc", filelocation);
				}
			}
			else {
				//no new file uploaded so we will retrieve the exisiting info
				adminfilename = pbo.getPolicyAdminDoc();

			}
			//check mandatory fields
			if (validate_form() && fileokd) {
				//parse the fields
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				PolicyBean pb = new PolicyBean();
				pb.setPolicyCategory(PolicyCategory.get(Integer.parseInt(form.get("policy_category").toString())));
				pb.setPolicyStatus(PolicyStatus.get(Integer.parseInt(form.get("policy_status").toString())));
				pb.setPolicyNumber(form.get("policy_number").toString());
				pb.setPolicyTitle(form.get("policy_title").toString());
				pb.setPolicyDocumentation(docfilename);
				pb.setPolicyAdminDoc(adminfilename);
				pb.setAddedBy(usr.getPersonnel().getFullNameReverse());
				pb.setId(form.getInt("id"));
				PoliciesManager.updatePolicy(pb);
				//now remove the origninal file from server
				if (!pbo.getPolicyDocumentation().equals(pb.getPolicyDocumentation())) {
					delete_file(filelocation, pbo.getPolicyDocumentation());
				}
				if (!(pbo.getPolicyAdminDoc() == null) && !(pb.getPolicyAdminDoc() == null)) {
					if (!pbo.getPolicyAdminDoc().equals(pb.getPolicyAdminDoc())) {
						delete_file(filelocation, pbo.getPolicyAdminDoc());
					}
				}
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
				request.setAttribute("policy", PoliciesManager.getPolicyById(pb.getId()));
				path = "view_policy_details.jsp";
				request.setAttribute("msgOK", "Policy has been updated");
			}
			else {
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
				request.setAttribute("policy", PoliciesManager.getPolicyById(pbo.getId()));
				path = "view_policy_details.jsp";
			}
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			try {
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
				request.setAttribute("policy", PoliciesManager.getPolicyById(pbo.getId()));
			}
			catch (NullPointerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (BlogsException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			path = "view_policy_details.jsp";

		}
		return path;
	}
}
