package com.esdnl.webmaint.policies.handler;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.esdnl.webmaint.policies.Policy;
import com.esdnl.webmaint.policies.PolicyCategoryDB;
import com.esdnl.webmaint.policies.PolicyDB;

import javazoom.upload.MultipartFormDataRequest;
import javazoom.upload.UploadBean;
import javazoom.upload.UploadFile;

public class AddPolicyRequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		HttpSession session = null;
		User usr = null;
		String path = "";
		UploadBean bean = null;
		MultipartFormDataRequest mrequest = null;
		UploadFile file = null;
		Hashtable files = null;
		File f = null;

		session = request.getSession(false);
		if ((session != null) && (session.getAttribute("usr") != null)) {
			usr = (User) session.getAttribute("usr");
			if (!(usr.getUserPermissions().containsKey("WEBMAINTENANCE-VIEW")
					&& usr.getUserPermissions().containsKey("WEBMAINTENANCE-DISTRICTPOLICIES"))) {
				throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
			}
		}

		try {
			if (MultipartFormDataRequest.isMultipartFormData(request)) {
				mrequest = new MultipartFormDataRequest(request);

				if (mrequest.getParameter("op") != null) {
					if (mrequest.getParameter("op").equals("CONFIRM")) {
						if (mrequest.getParameter("cat_code") == null) {
							request.setAttribute("msg", "CATEGORY CODE IS REQUIRED");
						}
						else if ((mrequest.getParameter("pol_code") == null) || mrequest.getParameter("pol_code").equals("")) {
							request.setAttribute("msg", "POLICY CODE IS REQUIRED");
						}
						else if ((mrequest.getParameter("pol_title") == null) || mrequest.getParameter("pol_title").equals("")) {
							request.setAttribute("msg", "POLICY TITLE IS REQUIRED");
						}
						else if (mrequest.getParameter("pol_code").length() > 10) {
							request.setAttribute("msg", "POLICY CODE HAS MAX LENGHT OF 10 CHARACTERS");
						}
						else if (mrequest.getParameter("pol_title").length() > 100) {
							request.setAttribute("msg", "CATEGORY TITLE HAS MAX LENGHT OF 100 CHARACTERS");
						}
						else {
							files = mrequest.getFiles();

							if ((files == null) || (files.size() < 1)) {
								request.setAttribute("msg", "PLEASE SELECT POLICY FILE TO UPLOAD");
							}
							else {
								file = (UploadFile) files.get("filedata");

								if ((file.getFileName().indexOf(".pdf") < 0) && (file.getFileName().indexOf(".PDF") < 0)) {
									request.setAttribute("msg", "ONLY PDF POLICY FILES ARE ACCEPTED");
								}
								else {
									try {
										PolicyDB.addPolicy(
												new Policy(mrequest.getParameter("cat_code").toUpperCase(), mrequest.getParameter(
														"pol_code").toUpperCase(), mrequest.getParameter("pol_title").toUpperCase()));

										bean = new UploadBean();
										bean.setFolderstore(session.getServletContext().getRealPath("/")
												+ "/../../nlesdweb/WebContent/about/policies/esd/");
										bean.store(mrequest, "filedata");
										f = new File(session.getServletContext().getRealPath("/")
												+ "/../../nlesdweb/WebContent/about/policies/esd/" + file.getFileName());
										f.renameTo(new File(session.getServletContext().getRealPath("/")
												+ "/../../nlesdweb/WebContent/about/policies/esd/"
												+ mrequest.getParameter("cat_code").toUpperCase() + "_"
												+ mrequest.getParameter("pol_code").toUpperCase() + ".pdf"));
										request.setAttribute("msg", "POLICY ADD SUCCESSFULLY");
									}
									catch (SQLException e) {
										switch (e.getErrorCode()) {
										case 1:
											request.setAttribute("msg", "POLICY " + mrequest.getParameter("cat_code") + " ALREADY EXISTS");
											break;
										default:
											request.setAttribute("msg", e.getMessage());
										}
									}
								}
							}
						}
					}
				}
			}
		}
		catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
		}

		request.setAttribute("POLICY_CATEGORIES", PolicyCategoryDB.getPolicyCategories());
		path = "add_policy.jsp";

		return path;
	}
}