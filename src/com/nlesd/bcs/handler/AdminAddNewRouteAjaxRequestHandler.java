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
import com.nlesd.bcs.bean.BussingContractorSystemRouteBean;
import com.nlesd.bcs.constants.BoardOwnedContractorsConstant;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorSystemRouteManager;
public class AdminAddNewRouteAjaxRequestHandler extends RequestHandlerImpl{
	public AdminAddNewRouteAjaxRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("routename"),
				new RequiredFormElement("routeschool"),
				new RequiredFormElement("vtype"),
				new RequiredFormElement("vsize")
		});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	throws ServletException,
		IOException {
		super.handleRequest(request, response);
			String message="SUCCESS";
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			if (validate_form()) {
				BussingContractorSystemRouteBean vbean = new BussingContractorSystemRouteBean();
				int cid = form.getInt("cid");
				//now we check to see if it is a board owned contract
				if(usr.checkPermission("BCS-VIEW-WESTERN")){
					vbean.setBoardOwned(BoardOwnedContractorsConstant.WESTERN.getValue());
				}
				if(usr.checkPermission("BCS-VIEW-CENTRAL")){
					vbean.setBoardOwned(BoardOwnedContractorsConstant.CENTRAL.getValue());
				}
				if(usr.checkPermission("BCS-VIEW-LABRADOR")){
					vbean.setBoardOwned(BoardOwnedContractorsConstant.LABRADOR.getValue());
				}
				try {
					//get fields
					vbean.setRouteName(form.get("routename"));
					vbean.setRouteNotes(form.get("routenotes"));
					vbean.setRouteSchool(form.getInt("routeschool"));
					vbean.setAddedBy(usr.getPersonnel().getFullNameReverse());
					vbean.setVehicleType(form.getInt("vtype"));
					vbean.setVehicleSize(form.getInt("vsize"));
					//save file to db
					if(cid > 0){
						vbean.setId(cid);
						BussingContractorSystemRouteManager.updateBussingContractorSystemRoute(vbean);
					}else{
						BussingContractorSystemRouteManager.addBussingContractorSystemRoute(vbean);
					}
					
					//update audit trail
					AuditTrailBean atbean = new AuditTrailBean();
					if(cid > 0){
						atbean.setEntryType(EntryTypeConstant.UPDATEROUTE);
					}else{
						atbean.setEntryType(EntryTypeConstant.ADDNEWROUTE);
					}
					
					atbean.setEntryId(vbean.getId());
					atbean.setEntryTable(EntryTableConstant.CONTRACT);
					DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
					if(cid > 0){
						atbean.setEntryNotes("Route:  (" + vbean.getRouteName() + ") updated by: " + usr.getPersonnel().getFullNameReverse() + " on " + dateTimeInstance.format(Calendar.getInstance().getTime()));
					}else{
						atbean.setEntryNotes("Route:  (" + vbean.getRouteName() + ") added by: " + usr.getPersonnel().getFullNameReverse() + " on " + dateTimeInstance.format(Calendar.getInstance().getTime()));
					}
					atbean.setContractorId(0);
					AuditTrailManager.addAuditTrail(atbean);
	            }
				catch (Exception e) {
					message=e.getMessage();
					sb.append("<CONTRACTS>");
					sb.append("<CONTRACT>");
					sb.append("<MESSAGE>" + message + "</MESSAGE>");
					sb.append("</CONTRACT>");
					sb.append("</CONTRACTS>");
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
				sb.append("<ID>" + vbean.getId() + "</ID>");
				sb.append("</CONTRACTOR>");
				sb.append("</CONTRACTORS>");
			}else {
				sb.append("<CONTRACTS>");
				sb.append("<CONTRACT>");
				sb.append("<MESSAGE>" + com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()) + "</MESSAGE>");
				sb.append("</CONTRACT>");
				sb.append("</CONTRACTS>");
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