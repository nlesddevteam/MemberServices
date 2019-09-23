package com.nlesd.bcs.handler;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.bcs.bean.AuditTrailBean;
import com.nlesd.bcs.bean.BussingContractorSecurityArcBean;
import com.nlesd.bcs.bean.BussingContractorSecurityBean;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorSecurityArcManager;
import com.nlesd.bcs.dao.BussingContractorSecurityManager;
public class UpdatePasswordAjaxRequestHandler extends PublicAccessRequestHandlerImpl { 
	public UpdatePasswordAjaxRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("cid"),
				new RequiredFormElement("npassword")
		});
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		if (validate_form()) {
			try {
				Integer contractorsecid =Integer.parseInt(form.get("cid"));
				BussingContractorSecurityBean bcsbean = BussingContractorSecurityManager.getBussingContractorSecurityBySid(contractorsecid);
				String npassword = form.get("npassword");
				String currentPassword = bcsbean.getPassword();
				//update contractor security
				bcsbean.setPassword(npassword);
				BussingContractorSecurityManager.updateBussingContractorSecurity(bcsbean);
				//update contractor security arc
				BussingContractorSecurityArcBean sbean = new BussingContractorSecurityArcBean();
				sbean.setNewPassword(npassword);
				sbean.setOldPassword(currentPassword);
				sbean.setSecurityId(contractorsecid);
				BussingContractorSecurityArcBean arcbean = BussingContractorSecurityArcManager.addBussingContractorSecurityArc(sbean);
				//add audit trail for change password
				AuditTrailBean atbean = new AuditTrailBean();
				atbean.setEntryType(EntryTypeConstant.CONTRACTORCHANGEPASSWORD);
				atbean.setEntryId(arcbean.getId());
				atbean.setEntryTable(EntryTableConstant.CONTRACTORSECURITYARC);
				atbean.setEntryNotes("Contractor Password Changed From " + currentPassword + " TO " + npassword);
				atbean.setContractorId(bcsbean.getContractorId());
				AuditTrailManager.addAuditTrail(atbean);
				// generate XML for candidate details.
				sb.append("<CONTRACTORS>");
				sb.append("<CONTRACTOR>");
				sb.append("<MESSAGE>UPDATED</MESSAGE>");
				sb.append("</CONTRACTOR>");
				sb.append("</CONTRACTORS>");
				

				
			}
			catch (Exception e) {
				// generate XML for candidate details.
				sb.append("<CONTRACTOR>");
				sb.append("<CONTRACTORSTATUS>");
				sb.append("<MESSAGE>ERROR SETTING CONTRACTOR STATUS</MESSAGE>");
				sb.append("</CONTRACTORSTATUS>");
				sb.append("</CONTRACTOR>");
				xml = sb.toString().replaceAll("&", "&amp;");
				PrintWriter out = response.getWriter();
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
				path = null;

			}
		}else {
			sb.append("<CONTRACTORS>");
			sb.append("<CONTRACTOR>");
			sb.append("<MESSAGE>" + com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()) + "</MESSAGE>");
			sb.append("</CONTRACTOR>");
			sb.append("</CONTRACTORS>");
		}
		xml = sb.toString().replaceAll("&", "&amp;");
		PrintWriter out = response.getWriter();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		out.write(xml);
		out.flush();
		out.close();
		path = null;
		return path;
	}

}