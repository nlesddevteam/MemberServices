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
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.bcs.bean.AuditTrailBean;
import com.nlesd.bcs.bean.BussingContractorVehicleBean;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorVehicleManager;

public class RestoreVehicleAjaxRequestHandler extends RequestHandlerImpl{
	public RestoreVehicleAjaxRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("eid")
			});
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException{
		super.handleRequest(request, response);
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		if (validate_form()) {
	        Integer eid = form.getInt("eid");
	        BussingContractorVehicleBean ebean = BussingContractorVehicleManager.getBussingContractorVehicleById(eid);
	        boolean result=false;
	        result = BussingContractorVehicleManager.restoreContractorVehicle(eid);
	        //update audit trail
			AuditTrailBean atbean = new AuditTrailBean();
			atbean.setEntryType(EntryTypeConstant.CONTRACTOREMPLOYEERESTORED);
			atbean.setEntryId(eid);
			atbean.setEntryTable(EntryTableConstant.CONTRACTOREMPLOYEE);
			DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
			atbean.setEntryNotes("Contractor vehicle (SN:" + ebean.getvSerialNumber() + " PN:" + ebean.getvPlateNumber() + ") restored on  " + dateTimeInstance.format(Calendar.getInstance().getTime())
			+ " by " + usr.getLotusUserFullName());
			atbean.setContractorId(-1);
			AuditTrailManager.addAuditTrail(atbean);
	        if(result){
				sb.append("<CONTRACTORS>");
				sb.append("<CONTRACTOR>");
				sb.append("<MESSAGE>SUCCESS</MESSAGE>");
				sb.append("</CONTRACTOR>");
				sb.append("</CONTRACTORS>");
				xml = sb.toString().replaceAll("&", "&amp;");
				PrintWriter out = response.getWriter();
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
	        }else{
				sb.append("<CONTRACTORS>");
				sb.append("<CONTRACTOR>");
				sb.append("<MESSAGE>ERROR RESTORING EMPLOYEE</MESSAGE>");
				sb.append("</CONTRACTOR>");
				sb.append("</CONTRACTORS>");
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
