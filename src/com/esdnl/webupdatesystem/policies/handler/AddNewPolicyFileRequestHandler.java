package com.esdnl.webupdatesystem.policies.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.policies.bean.PolicyFileBean;
import com.esdnl.webupdatesystem.policies.dao.PolicyFileManager;

public class AddNewPolicyFileRequestHandler extends RequestHandlerImpl {

	public AddNewPolicyFileRequestHandler() {

		this.requiredRoles = new String[] {
				"ADMINISTRATOR", "WEB DESIGNER", "WEBANNOUNCMENTS-POST"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			//get fields
			PolicyFileBean pfb = new PolicyFileBean();
			pfb.setPolicyId(form.getInt("polid"));
			pfb.setPfTitle(form.get("poltitle"));
			pfb.setAddedBy(usr.getPersonnel().getFullNameReverse());

			//now we save the file
			String filelocation = "/../../nlesdweb/WebContent/includes/files/policies/doc/";
			String docfilename = save_file("polfile", filelocation);
			pfb.setPfDoc(docfilename);
			//save file to db
			PolicyFileManager.addPolicyFile(pfb);
			//send file list back

			// generate XML for candidate details.
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<POLICIES>");

			Iterator i = PolicyFileManager.getPoliciesFiles(form.getInt("polid")).iterator();
			while (i.hasNext()) {

				PolicyFileBean p = (PolicyFileBean) i.next();
				sb.append("<FILES>");
				sb.append("<MESSAGE>SUCCESS</MESSAGE>");
				sb.append("<ID>" + p.getId() + "</ID>");
				sb.append("<PFTITLE>" + p.getPfTitle() + "</PFTITLE>");
				sb.append("<PFDOC>" + p.getPfDoc() + "</PFDOC>");
				sb.append("<ADDEDBY>" + p.getAddedBy() + "</ADDEDBY>");
				sb.append("<DATEADDED>" + p.getDateAddedFormatted() + "</DATEADDED>");
				sb.append("<POLICYID>" + p.getPolicyId() + "</POLICYID>");
				sb.append("</FILES>");
			}

			sb.append("</POLICIES>");
			xml = sb.toString().replaceAll("&", "&amp;");
			System.out.println(xml);
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
			path = null;
		}
		catch (Exception e) {
			e.printStackTrace();

			path = null;
		}
		return path;
	}

}