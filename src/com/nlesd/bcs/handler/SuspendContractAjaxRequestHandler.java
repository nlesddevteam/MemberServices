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
import com.nlesd.bcs.bean.BussingContractorSystemContractBean;
import com.nlesd.bcs.bean.BussingContractorSystemContractHistoryBean;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorManager;
import com.nlesd.bcs.dao.BussingContractorSystemContractHistoryManager;
import com.nlesd.bcs.dao.BussingContractorSystemContractManager;
public class SuspendContractAjaxRequestHandler extends RequestHandlerImpl {
	public SuspendContractAjaxRequestHandler() {
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
				Integer cid =Integer.parseInt(form.get("cid"));
				String snotes = form.get("snotes");
				String stype=form.get("statustype");
				
				BussingContractorSystemContractBean conBean = BussingContractorSystemContractManager.getBussingContractorSystemContractById(cid);
				BussingContractorSystemContractHistoryBean origbean = BussingContractorSystemContractHistoryManager.getBussingContractorSystemContractStatus(cid);
				BussingContractorBean bcbean = BussingContractorManager.getBussingContractorById(origbean.getContractorId());
				//update contractor status
				BussingContractorSystemContractHistoryBean hbean = new BussingContractorSystemContractHistoryBean();
				hbean.setContractId(cid);
				hbean.setContractorId(origbean.getContractorId());
				if(stype.equals("S")) {
					hbean.setContractStatus(86);
				}else {
					hbean.setContractStatus(85);
				}
				hbean.setStatusBy(usr.getPersonnel().getFullNameReverse());
				hbean.setStatusNotes(snotes);
				BussingContractorSystemContractHistoryManager.addBussingContractorSystemContractHistory(hbean);
				AuditTrailBean atbean = new AuditTrailBean();
				if(stype.equals("S")) {
					atbean.setEntryType(EntryTypeConstant.CONTRACTSUSPENDED);
				}else {
					atbean.setEntryType(EntryTypeConstant.CONTRACTUNSUSPENDED);
				}
				
				atbean.setEntryId(hbean.getId());
				atbean.setEntryTable(EntryTableConstant.CONTRACTHISTORY);
				if(stype.equals("S")) {
					atbean.setEntryNotes("Contract: " + conBean.getContractName() + " was awarded to " + bcbean.getCompany() + "(" + bcbean.getLastName() + "," + bcbean.getFirstName()
					+ ") has been suspended by " + usr.getPersonnel().getFullNameReverse() );
				}else {
					atbean.setEntryNotes("Contract: " + conBean.getContractName() + " was awarded to " + bcbean.getCompany() + "(" + bcbean.getLastName() + "," + bcbean.getFirstName()
					+ ") has been unsuspended by " + usr.getPersonnel().getFullNameReverse() );
				}
				
				atbean.setContractorId(bcbean.getId());
				AuditTrailManager.addAuditTrail(atbean);
				//send awarded message 
				// 2 email contractor
				EmailBean email = new EmailBean();
				email.setTo(bcbean.getEmail());
				email.setFrom("bussingcontractorsystem@nlesd.ca");
				email.setSubject("NLESD Bussing Contractor System Contract Suspended");
				HashMap<String, Object> model = new HashMap<String, Object>();
				// set values to be used in template
				model.put("contractname", conBean.getContractName());
				if(stype.equals("S")) {
					model.put("statustype", " suspended" );
				}else {
					model.put("statustype", " unsuspended");
				}
				
				email.setBody(VelocityUtils.mergeTemplateIntoString("bcs/suspend_contract.vm", model));
				//email.send();
				// generate XML for candidate details.
				sb.append("<CONTRACTOR>");
				sb.append("<CONTRACTORSTATUS>");
				sb.append("<MESSAGE>STATUSUPDATED</MESSAGE>");
				sb.append("</CONTRACTORSTATUS>");
				sb.append("</CONTRACTOR>");
				

				
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
			sb.append("<CONTRACTOR>");
			sb.append("<MESSAGE>" + com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()) + "</MESSAGE>");
			sb.append("</CONTRACTOR>");
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
