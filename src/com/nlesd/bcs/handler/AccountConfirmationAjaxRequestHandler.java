package com.nlesd.bcs.handler;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.mail.bean.EmailBean;
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
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
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequiredFormElement;
public class AccountConfirmationAjaxRequestHandler extends PublicAccessRequestHandlerImpl { 
	public AccountConfirmationAjaxRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("hidcid"),
				new RequiredFormElement("hidcsid"),
				new RequiredFormElement("hidid"),
				new RequiredFormElement("npassword"),
				new RequiredFormElement("squestion"),
				new RequiredFormElement("sqanswer")
				
			});
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		try {
			
			if (validate_form()) {
				Integer contractorid =Integer.parseInt(form.get("hidcid"));
				BussingContractorBean bcbean = BussingContractorManager.getBussingContractorById(contractorid);
				Integer contractorsecid =Integer.parseInt(form.get("hidcsid"));
				String tpassword = form.get("hidid");
				String npassword = form.get("npassword");
				String squestion = form.get("squestion");
				String sqanswer = form.get("sqanswer");
				//update contractor security
				BussingContractorSecurityBean ebean = new BussingContractorSecurityBean();
				ebean.setContractorId(contractorid);
				ebean.setId(contractorsecid);
				ebean.setPassword(npassword);
				ebean.setSecurityQuestion(squestion);
				ebean.setSqAnswer(sqanswer);
				BussingContractorSecurityManager.updateBussingContractorSecurity(ebean);
				//update contractor security arc
				BussingContractorSecurityArcBean sbean = new BussingContractorSecurityArcBean();
				sbean.setNewPassword(npassword);
				sbean.setOldPassword(tpassword);
				sbean.setSecurityId(contractorsecid);
				BussingContractorSecurityArcBean arcbean = BussingContractorSecurityArcManager.addBussingContractorSecurityArc(sbean);
				//add audit trail for confirmation
				AuditTrailBean atbean = new AuditTrailBean();
				atbean.setEntryType(EntryTypeConstant.CONTRACTORCONFIRMED);
				atbean.setEntryId(contractorid);
				atbean.setEntryTable(EntryTableConstant.CONTRACTORS);
				DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
				atbean.setEntryNotes("Contractor Confirmed Account On " + dateTimeInstance.format(Calendar.getInstance().getTime()));
				atbean.setContractorId(contractorid);
				AuditTrailManager.addAuditTrail(atbean);
				//add audit trail for change password
				 atbean = new AuditTrailBean();
				atbean.setEntryType(EntryTypeConstant.CONTRACTORCHANGEPASSWORD);
				atbean.setEntryId(arcbean.getId());
				atbean.setEntryTable(EntryTableConstant.CONTRACTORSECURITYARC);
				atbean.setEntryNotes("Contractor Password Changed From " + tpassword + " TO " + npassword);
				atbean.setContractorId(contractorid);
				AuditTrailManager.addAuditTrail(atbean);
				// send email to contractor
				EmailBean email = new EmailBean();
				email.setTo(bcbean.getEmail());
				email.setFrom("bussingcontractorsystem@nlesd.ca");
				email.setSubject("NLESD Bussing Contractor System Account Confirmation");
				HashMap<String, Object> model = new HashMap<String, Object>();
				// set values to be used in template
				model.put("name", bcbean.getFirstName() + " " + bcbean.getLastName());
				model.put("link", "http://www.nlesd.ca/MemberServices/BCS/contractorLogin.html");
				email.setBody(VelocityUtils.mergeTemplateIntoString("bcs/account_confirmation.vm", model));
				//email.send();
				// generate XML for candidate details.
				sb.append("<CONTRACTORS>");
				sb.append("<CONTRACTOR>");
				sb.append("<MESSAGE>CONFIRMED</MESSAGE>");
				sb.append("</CONTRACTOR>");
				sb.append("</CONTRACTORS>");
			}else {
				sb.append("<CONTRACTORS>");
				sb.append("<CONTRACTOR>");
				sb.append("<MESSAGE>Error Confirming Account</MESSAGE>");
				sb.append("</CONTRACTOR>");
				sb.append("</CONTRACTORS>");
			}
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
			return path;

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
