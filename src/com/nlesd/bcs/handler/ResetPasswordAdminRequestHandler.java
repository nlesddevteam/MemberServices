package com.nlesd.bcs.handler;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.mail.bean.EmailBean;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.velocity.VelocityUtils;
import com.nlesd.bcs.bean.AuditTrailBean;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.bean.BussingContractorSecurityArcBean;
import com.nlesd.bcs.bean.BussingContractorSecurityBean;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorManager;
import com.nlesd.bcs.dao.BussingContractorSecurityArcManager;
import com.nlesd.bcs.dao.BussingContractorSecurityManager;
public class ResetPasswordAdminRequestHandler extends RequestHandlerImpl { 
	public ResetPasswordAdminRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("cid")
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
				Integer contractorid =Integer.parseInt(form.get("cid"));
				BussingContractorBean bcbean = BussingContractorManager.getBussingContractorById(contractorid);
				//create temporary passowrd
				String tmppwd = BussingContractorManager.createTempoararyPassword();
				//update contractor security
				BussingContractorSecurityBean ebean = new BussingContractorSecurityBean();
				BussingContractorSecurityBean origbean = new BussingContractorSecurityBean();
				origbean = BussingContractorSecurityManager.getBussingContractorSecurityById(contractorid);
				// 2 email contractor
				EmailBean email = new EmailBean();
				email.setTo(bcbean.getEmail());
				email.setFrom("bussingcontractorsystem@nlesd.ca");
				email.setSubject("NLESD Bussing Contractor System Password Reset");
				HashMap<String, Object> model = new HashMap<String, Object>();
				model.put("password", tmppwd);
				email.setBody(VelocityUtils.mergeTemplateIntoString("bcs/password_reset.vm", model));
				email.send();
				//update the security record
				ebean.setContractorId(contractorid);
				ebean.setId(origbean.getId());
				ebean.setPassword(tmppwd);
				ebean.setSecurityQuestion(origbean.getSecurityQuestion());
				ebean.setSqAnswer(origbean.getSqAnswer());
				BussingContractorSecurityManager.updateBussingContractorSecurity(ebean);
				//update contractor security arc
				BussingContractorSecurityArcBean sbean = new BussingContractorSecurityArcBean();
				sbean.setNewPassword(tmppwd);
				sbean.setOldPassword(origbean.getPassword());
				sbean.setSecurityId(origbean.getId());
				BussingContractorSecurityArcBean arcbean = BussingContractorSecurityArcManager.addBussingContractorSecurityArc(sbean);
				//add audit trail for change password
				AuditTrailBean atbean = new AuditTrailBean();
				atbean.setEntryType(EntryTypeConstant.CONTRACTORCHANGEPASSWORD);
				atbean.setEntryId(arcbean.getId());
				atbean.setEntryTable(EntryTableConstant.CONTRACTORSECURITYARC);
				atbean.setEntryNotes("Contractor Password Changed From " + origbean.getPassword() + " TO " + tmppwd);
				atbean.setContractorId(contractorid);
				AuditTrailManager.addAuditTrail(atbean);
				// generate XML for candidate details.
				sb.append("<CONTRACTORS>");
				sb.append("<CONTRACTOR>");
				sb.append("<MESSAGE>SUCCESS</MESSAGE>");
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
