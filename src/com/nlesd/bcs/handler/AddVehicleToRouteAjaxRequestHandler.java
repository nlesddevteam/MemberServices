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
import com.nlesd.bcs.bean.BussingContractorSystemRouteVehicleBean;
import com.nlesd.bcs.bean.BussingContractorVehicleBean;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorSystemRouteManager;
import com.nlesd.bcs.dao.BussingContractorSystemRouteVehicleManager;
import com.nlesd.bcs.dao.BussingContractorVehicleManager;
public class AddVehicleToRouteAjaxRequestHandler extends BCSApplicationRequestHandlerImpl {
	public AddVehicleToRouteAjaxRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("rid"),
				new RequiredFormElement("vid")
		});
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
			IOException {
		super.handleRequest(request, response);
		BussingContractorEmployeeBean vbean =  new BussingContractorEmployeeBean();
		String message="UPDATED";
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		if (validate_form() && !(this.sessionExpired)) {
			try {
				BussingContractorBean bcbean = (BussingContractorBean) request.getSession(false).getAttribute("CONTRACTOR");
				int routeid = form.getInt("rid");
				int vehicleid = form.getInt("vid");
				//now get current driver
				BussingContractorVehicleBean origbean = BussingContractorVehicleManager.getCurrentRouteVehicle(routeid);
				BussingContractorSystemRouteVehicleBean rdbean = new BussingContractorSystemRouteVehicleBean();
				BussingContractorVehicleBean newvehicle = BussingContractorVehicleManager.getBussingContractorVehicleById(vehicleid);
				BussingContractorSystemRouteBean routebean = BussingContractorSystemRouteManager.getBussingContractorSystemRouteById(routeid);
				rdbean.setRouteId(routeid);
				rdbean.setVehicleId(vehicleid);
				rdbean.setAssignedBy(bcbean.getLastName() + "," + bcbean.getFirstName());
				//now we add the record
				BussingContractorSystemRouteVehicleManager.addBussingContractorSystemRouteVehicleBean(rdbean);
				//update audit trail with the add driver
				AuditTrailBean atbean = new AuditTrailBean();
				atbean.setEntryType(EntryTypeConstant.ADDVEHICLETOROUTE);
				atbean.setEntryId(rdbean.getId());
				atbean.setEntryTable(EntryTableConstant.ROUTEVEHICLE);
				DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
				atbean.setEntryNotes("Vehicle: " + newvehicle.getvPlateNumber() + "[" + newvehicle.getvSerialNumber() + "] added to Route: " + routebean.getRouteName() + " on  " 
						+ dateTimeInstance.format(Calendar.getInstance().getTime()));
				atbean.setContractorId(bcbean.getId());
				AuditTrailManager.addAuditTrail(atbean);
				//update audit trail with the remove driver
				atbean = new AuditTrailBean();
				atbean.setEntryType(EntryTypeConstant.REMOVEDRIVERTOROUTE);
				atbean.setEntryId(origbean.getId());
				atbean.setEntryTable(EntryTableConstant.ROUTEDRIVER);
				if(origbean != null){
					atbean.setEntryNotes("Vehicle: " + origbean.getvPlateNumber() + "[" + origbean.getvSerialNumber() + "] removed from Route: " + routebean.getRouteName() + " on  " 
							+ dateTimeInstance.format(Calendar.getInstance().getTime()));
				}

				atbean.setContractorId(bcbean.getId());
				AuditTrailManager.addAuditTrail(atbean);
			}catch(Exception e){
				message = e.getMessage();
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
