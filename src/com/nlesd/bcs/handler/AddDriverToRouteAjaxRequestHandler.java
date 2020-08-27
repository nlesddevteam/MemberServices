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
import com.nlesd.bcs.bean.BussingContractorEmployeeBean;
import com.nlesd.bcs.bean.BussingContractorSystemRouteBean;
import com.nlesd.bcs.bean.BussingContractorSystemRouteDriverBean;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorEmployeeManager;
import com.nlesd.bcs.dao.BussingContractorSystemRouteDriverManager;
import com.nlesd.bcs.dao.BussingContractorSystemRouteManager;
public class AddDriverToRouteAjaxRequestHandler extends BCSApplicationRequestHandlerImpl {
	public AddDriverToRouteAjaxRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("rid"),
				new RequiredFormElement("did")
		});
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
			IOException {
		super.handleRequest(request, response);
		BussingContractorEmployeeBean vbean =  new BussingContractorEmployeeBean();
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		String message="";
		if (validate_form() && !(this.sessionExpired)) {
			try {
				BussingContractorBean bcbean = (BussingContractorBean) request.getSession(false).getAttribute("CONTRACTOR");
				int routeid = form.getInt("rid");
				int driverid = form.getInt("did");
				//now get current driver
				BussingContractorEmployeeBean origbean = BussingContractorEmployeeManager.getCurrentRouteDriver(routeid);
				BussingContractorSystemRouteDriverBean rdbean = new BussingContractorSystemRouteDriverBean();
				BussingContractorEmployeeBean newdriver = BussingContractorEmployeeManager.getBussingContractorEmployeeById(driverid);
				BussingContractorSystemRouteBean routebean = BussingContractorSystemRouteManager.getBussingContractorSystemRouteById(routeid);
				rdbean.setRouteId(routeid);
				rdbean.setDriverId(driverid);
				rdbean.setAssignedBy(bcbean.getLastName() + "," + bcbean.getFirstName());
				//now we add the record
				BussingContractorSystemRouteDriverManager.addBussingContractorSystemRouteDriverBean(rdbean);
				//update audit trail with the add driver
				AuditTrailBean atbean = new AuditTrailBean();
				atbean.setEntryType(EntryTypeConstant.ADDDRIVERTOROUTE);
				atbean.setEntryId(rdbean.getId());
				atbean.setEntryTable(EntryTableConstant.ROUTEDRIVER);
				DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
				atbean.setEntryNotes("Employee: (" + newdriver.getLastName() + "," + newdriver.getFirstName() + ") added to Route: " + routebean.getRouteName() + " on  " 
						+ dateTimeInstance.format(Calendar.getInstance().getTime()));
				atbean.setContractorId(bcbean.getId());
				AuditTrailManager.addAuditTrail(atbean);
				//update audit trail with the remove driver
				atbean = new AuditTrailBean();
				atbean.setEntryType(EntryTypeConstant.REMOVEDRIVERTOROUTE);
				atbean.setEntryId(origbean.getId());
				atbean.setEntryTable(EntryTableConstant.ROUTEDRIVER);
				if(origbean != null){
					atbean.setEntryNotes("Employee: (" + origbean.getLastName() + "," + origbean.getFirstName() + ") removed from Route: " + routebean.getRouteName() + " on  " 
							+ dateTimeInstance.format(Calendar.getInstance().getTime()));
				}

				atbean.setContractorId(bcbean.getId());
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
			sb.append("<VID>" + vbean.getId() + "</VID>");
			sb.append("</CONTRACTOR>");
			sb.append("</CONTRACTORS>");
		}else {
			
			if(this.sessionExpired) {
				path="contractorLogin.html?msg=Session expired, please login again.";
				return path;
			}else {
				sb.append("<CONTRACTORS>");
				sb.append("<CONTRACTOR>");
				sb.append("<MESSAGE>" + com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()) + "</MESSAGE>");
				sb.append("</CONTRACTOR>");
				sb.append("</CONTRACTORS>");
			}
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