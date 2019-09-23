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
import com.nlesd.bcs.bean.BussingContractorSecurityArcBean;
import com.nlesd.bcs.bean.BussingContractorSecurityBean;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorSecurityArcManager;
import com.nlesd.bcs.dao.BussingContractorSecurityManager;
public class UpdateSecurityInformationRequestHandler extends BCSApplicationRequestHandlerImpl {
	public UpdateSecurityInformationRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("cid"),
				new RequiredFormElement("npassword"),
				new RequiredFormElement("squestion"),
				new RequiredFormElement("sqanswer")
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
					String password = request.getParameter("npassword");
					String question = request.getParameter("squestion");
					String answer = request.getParameter("sqanswer");
					BussingContractorSecurityBean origbean = BussingContractorSecurityManager.getBussingContractorSecurityById(cid);
					BussingContractorSecurityBean ebean = new BussingContractorSecurityBean();
					ebean.setContractorId(cid);
					ebean.setId(origbean.getId());
					ebean.setPassword(password);
					ebean.setSecurityQuestion(question);
					ebean.setSqAnswer(answer);
					//update the new info
					BussingContractorSecurityManager.updateBussingContractorSecurity(ebean);
					//now we check to see if password has changed
					if(!(ebean.getPassword().toUpperCase().equals(origbean.getPassword().toUpperCase()))){
						//changed password we update the archive table
						//update contractor security arc
						BussingContractorSecurityArcBean sbean = new BussingContractorSecurityArcBean();
						sbean.setNewPassword(ebean.getPassword());
						sbean.setOldPassword(origbean.getPassword());
						sbean.setSecurityId(ebean.getId());
						BussingContractorSecurityArcBean arcbean = BussingContractorSecurityArcManager.addBussingContractorSecurityArc(sbean);
						//add audit trail for change password
						AuditTrailBean atbean = new AuditTrailBean();
						atbean.setEntryType(EntryTypeConstant.CONTRACTORCHANGEPASSWORD);
						atbean.setEntryId(arcbean.getId());
						atbean.setEntryTable(EntryTableConstant.CONTRACTORSECURITYARC);
						atbean.setEntryNotes("Contractor Password Changed From " + origbean.getPassword() + " TO " + ebean.getPassword());
						atbean.setContractorId(origbean.getContractorId());
						AuditTrailManager.addAuditTrail(atbean);
					}
					//update audit trail
					AuditTrailBean atbean = new AuditTrailBean();
					atbean.setEntryType(EntryTypeConstant.CONTRACTORSECUIRTYUPDATED);
					atbean.setEntryId(origbean.getId());
					atbean.setEntryTable(EntryTableConstant.CONTRACTORSECURITY);
					DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
					atbean.setEntryNotes("Contractor Security Info updated on " + dateTimeInstance.format(Calendar.getInstance().getTime()));
					atbean.setContractorId(ebean.getId());
					AuditTrailManager.addAuditTrail(atbean);
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
						return null;
					}
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
