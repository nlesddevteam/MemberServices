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
import com.nlesd.bcs.bean.BussingContractorCompanyBean;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorCompanyManager;
public class UpdateCompanyInformatonRequestHandler extends BCSApplicationRequestHandlerImpl {
	public UpdateCompanyInformatonRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("cid")
		});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
			IOException {
		super.handleRequest(request, response);
		String message="UPDATED";
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		if (validate_form()) {
			Integer cid = Integer.parseInt(request.getParameter("cid"));
			BussingContractorBean bcbean = (BussingContractorBean) request.getSession(false).getAttribute("CONTRACTOR");
			BussingContractorCompanyBean bccbean = new BussingContractorCompanyBean();
			bccbean.setContractorId(bcbean.getId());
			if(request.getParameter("tregular") == null){
				bccbean.settRegular("N");
			}else{
				bccbean.settRegular("Y");
			}
			if(request.getParameter("talternate") == null){
				bccbean.settAlternate("N");
			}else{
				bccbean.settAlternate("Y");
			}
			if(request.getParameter("tparent") == null){
				bccbean.settParent("N");
			}else{
				bccbean.settParent("Y");
			}
			if(request.getParameter("crsameas") == null){
				bccbean.setCrSameAs("N");
			}else{
				bccbean.setCrSameAs("Y");
			}
			bccbean.setCrFirstName(request.getParameter("crfirstname"));
			bccbean.setCrLastName(request.getParameter("crlastname"));
			bccbean.setCrEmail(request.getParameter("cremail"));
			bccbean.setCrPhoneNumber(request.getParameter("crphonenumber"));
			if(request.getParameter("tosameas") == null){
				bccbean.setToSameAs("N");
			}else{
				bccbean.setToSameAs("Y");
			}
			bccbean.setToFirstName(request.getParameter("tofirstname"));
			bccbean.setToLastName(request.getParameter("tolastname"));
			bccbean.setToEmail(request.getParameter("toemail"));
			bccbean.setToPhoneNumber(request.getParameter("tophonenumber"));
			if(cid > 0){
				//get a copy of the original
				BussingContractorCompanyBean origbean = BussingContractorCompanyManager.getBussingContractorCompanyById(bcbean.getId());
				//update record
				BussingContractorCompanyManager.updateBussingContractorCompany(bccbean);
				//insert arc record
				BussingContractorCompanyManager.updateBussingContractorCompanyArc(origbean);
			}else{
				//insert record
				BussingContractorCompanyManager.addBussingContractorCompany(bccbean);

			}

			//update audit trail
			AuditTrailBean atbean = new AuditTrailBean();
			atbean.setEntryType(EntryTypeConstant.CONTRACTORCOMPANYUPDATED);
			atbean.setEntryId(bccbean.getId());
			atbean.setEntryTable(EntryTableConstant.CONTRACTORCOMPANY);
			DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
			atbean.setEntryNotes("Contractor Company Info updated on " + dateTimeInstance.format(Calendar.getInstance().getTime()));
			atbean.setContractorId(bcbean.getId());
			AuditTrailManager.addAuditTrail(atbean);
			sb.append("<CONTRACTORS>");
			sb.append("<CONTRACTOR>");
			sb.append("<MESSAGE>" + message + "</MESSAGE>");
			sb.append("</CONTRACTOR>");
			sb.append("</CONTRACTORS>");
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
