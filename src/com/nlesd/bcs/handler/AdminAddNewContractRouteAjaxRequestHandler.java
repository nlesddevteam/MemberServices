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
import com.nlesd.bcs.bean.BussingContractorSystemContractBean;
import com.nlesd.bcs.bean.BussingContractorSystemRouteBean;
import com.nlesd.bcs.bean.BussingContractorSystemRouteContractBean;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorSystemContractManager;
import com.nlesd.bcs.dao.BussingContractorSystemRouteContractManager;
import com.nlesd.bcs.dao.BussingContractorSystemRouteManager;
public class AdminAddNewContractRouteAjaxRequestHandler extends RequestHandlerImpl{
	public AdminAddNewContractRouteAjaxRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("cid"),
				new RequiredFormElement("rid")
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
				BussingContractorSystemRouteContractBean vbean = new BussingContractorSystemRouteContractBean();
				int cid = form.getInt("cid");
				int rid = form.getInt("rid");
				BussingContractorSystemRouteBean rbean = BussingContractorSystemRouteManager.getBussingContractorSystemRouteById(rid);
				BussingContractorSystemContractBean cbean = BussingContractorSystemContractManager.getBussingContractorSystemContractById(cid);
				try {
					//get fields
					vbean.setRouteId(rid);
					vbean.setContractId(cid);
					vbean.setAddedBy(usr.getPersonnel().getFullNameReverse());
					BussingContractorSystemRouteContractManager.addBussingContractorSystemRouteContract(vbean);
					//update audit trail
					AuditTrailBean atbean = new AuditTrailBean();
					atbean.setEntryType(EntryTypeConstant.ROUTEADDEDTOCONTRACT);
					atbean.setEntryId(vbean.getId());
					atbean.setEntryTable(EntryTableConstant.ROUTE);
					DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
					atbean.setEntryNotes("Route:  (" + rbean.getRouteName() + ") added to Contract: " + cbean.getContractName() + usr.getPersonnel().getFullNameReverse() + " on " + dateTimeInstance.format(Calendar.getInstance().getTime()));
					atbean.setContractorId(rid);
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
				sb.append("<CONTRACTS>");
				sb.append("<CONTRACT>");
				sb.append("<MESSAGE>" + message + "</MESSAGE>");
				sb.append("</CONTRACT>");
				sb.append("</CONTRACTS>");
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