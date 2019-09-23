package com.nlesd.bcs.handler;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.bcs.bean.AuditTrailBean;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.constants.StatusConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorManager;
public class UpdateContactInformatonRequestHandler  extends BCSApplicationRequestHandlerImpl {
	public UpdateContactInformatonRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("cid"),
				new RequiredFormElement("firstname"),
				new RequiredFormElement("lastname"),
				new RequiredFormElement("homephone"),
				new RequiredFormElement("address1"),
				new RequiredFormElement("city"),
				new RequiredFormElement("postalcode")
				
		});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
			IOException {
		//success
		super.handleRequest(request, response);
		String message="UPDATED";
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		if (validate_form()) {
			try{
				Integer cid = Integer.parseInt(request.getParameter("cid"));
				BussingContractorBean origbean = new BussingContractorBean();
				origbean = BussingContractorManager.getBussingContractorById(cid);
				BussingContractorBean ebean = new BussingContractorBean();
				ebean.setId(cid);
				ebean.setFirstName(form.get("firstname"));
				ebean.setLastName(form.get("lastname"));
				ebean.setMiddleName(form.get("middlename"));
				ebean.setEmail(form.get("email"));
				ebean.setAddress1(form.get("address1"));
				ebean.setAddress2(form.get("address2"));
				ebean.setCity(form.get("city"));
				ebean.setProvince(form.get("province"));
				ebean.setPostalCode(form.get("postalcode"));
				ebean.setHomePhone(form.get("homephone"));
				ebean.setCellPhone(form.get("cellphone"));
				ebean.setWorkPhone(form.get("workphone"));
				ebean.setCompany(form.get("companyname"));
				ebean.setMaddress1(form.get("maddress1"));
				ebean.setMaddress2(form.get("maddress2"));
				ebean.setMcity(form.get("mcity"));
				ebean.setMprovince(form.get("mprovince"));
				ebean.setMpostalCode(form.get("mpostalcode"));
				if(form.get("msameas") == null){
					ebean.setMsameAs("N");
				}else{
					ebean.setMsameAs("Y");
				}

				ebean.setStatus(StatusConstant.APPROVED.getValue());
				//update the archive table first
				BussingContractorManager.updateBussingContractorArc(origbean);
				BussingContractorManager.updateBussingContractor(ebean);
				//update audit trail
				AuditTrailBean atbean = new AuditTrailBean();
				atbean.setEntryType(EntryTypeConstant.CONTRACTORCONTACTUPDATED);
				atbean.setEntryId(origbean.getId());
				atbean.setEntryTable(EntryTableConstant.CONTRACTORS);
				DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
				atbean.setEntryNotes("Contractor Contact Info updated on " + dateTimeInstance.format(Calendar.getInstance().getTime()));
				atbean.setContractorId(ebean.getId());
				AuditTrailManager.addAuditTrail(atbean);
				sb.append("<CONTRACTORS>");
				sb.append("<CONTRACTOR>");
				sb.append("<MESSAGE>" + message + "</MESSAGE>");
				sb.append("</CONTRACTOR>");
				sb.append("</CONTRACTORS>");
			}catch(Exception e){
				message = e.getMessage();
				sb.append("<CONTRACTORS>");
				sb.append("<CONTRACTOR>");
				sb.append("<MESSAGE>" + message + "</MESSAGE>");
				sb.append("</CONTRACTOR>");
				sb.append("</CONTRACTORS>");
				xml = sb.toString().replaceAll("&", "&amp;");
				PrintWriter out = response.getWriter();
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
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
		return null;
	}
}
