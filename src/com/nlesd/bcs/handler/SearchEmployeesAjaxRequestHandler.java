package com.nlesd.bcs.handler;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.bcs.bean.BussingContractorEmployeeBean;
import com.nlesd.bcs.constants.BoardOwnedContractorsConstant;
import com.nlesd.bcs.constants.EmployeeStatusConstant;
import com.nlesd.bcs.dao.BussingContractorEmployeeManager;
public class SearchEmployeesAjaxRequestHandler extends RequestHandlerImpl {
	public SearchEmployeesAjaxRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("searchby")
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
				String searchby = form.get("searchby");
				String searchfor = form.get("searchfor");
				String searchp = form.get("searchprovince");
				Integer searchi = form.getInt("searchstatus");
				Integer searchpos = form.getInt("searchposition");
				Integer searchdl = form.getInt("searchdl");
				Integer regionid = form.getInt("searchregion");
				Integer depotid = form.getInt("searchdepot");
				ArrayList<BussingContractorEmployeeBean> list = new ArrayList<BussingContractorEmployeeBean>();
				
				if(usr.checkPermission("BCS-VIEW-WESTERN") || usr.checkPermission("BCS-VIEW-CENTRAL") || usr.checkPermission("BCS-VIEW-LABRADOR")){
			    	int cid=0;
			    	if(usr.checkPermission("BCS-VIEW-WESTERN")){
						cid = BoardOwnedContractorsConstant.WESTERN.getValue();
					}
					if(usr.checkPermission("BCS-VIEW-CENTRAL")){
						cid = BoardOwnedContractorsConstant.CENTRAL.getValue();
					}
					if(usr.checkPermission("BCS-VIEW-LABRADOR")){
						cid = BoardOwnedContractorsConstant.LABRADOR.getValue();
					}
					if(searchby.equals("Status")){
						list = BussingContractorEmployeeManager.searchEmployeesByIntegerReg(searchby, searchfor,searchi,cid);
					}else if(searchby.equals("Position")){
						list = BussingContractorEmployeeManager.searchEmployeesByIntegerReg(searchby, searchfor,searchpos,cid);
					}else if(searchby.equals("Driver Licence Class")){
						list = BussingContractorEmployeeManager.searchEmployeesByIntegerReg(searchby, searchfor,searchdl,cid);
					}else{
						list = BussingContractorEmployeeManager.searchEmployeesByStringReg(searchby, searchfor,searchp,cid);
					}
				}else{
					if(searchby.equals("Status")){
						list = BussingContractorEmployeeManager.searchEmployeesByInteger(searchby, searchfor,searchi);
					}else if(searchby.equals("Position")){
						list = BussingContractorEmployeeManager.searchEmployeesByInteger(searchby, searchfor,searchpos);
					}else if(searchby.equals("Driver Licence Class")){
						list = BussingContractorEmployeeManager.searchEmployeesByInteger(searchby, searchfor,searchdl);
					}else if(searchby.equals("Region")){
						list = BussingContractorEmployeeManager.getContractorsEmployeesByRegion(regionid);
					}else if(searchby.equals("Depot")){
						list = BussingContractorEmployeeManager.getContractorsEmployeesByDepot(depotid);
					}else{
						list = BussingContractorEmployeeManager.searchEmployeesByString(searchby, searchfor,searchp);
					}
				}
				sb.append("<CONTRACTORS>");
				if(list.size() > 0){
					for(BussingContractorEmployeeBean ebean : list){
						sb.append("<CONTRACTOR>");
						sb.append("<ID>" + ebean.getId() + "</ID>");
						sb.append("<LASTNAME>" + ebean.getLastName()+ "</LASTNAME>");
						sb.append("<FIRSTNAME>" + ebean.getFirstName() + "</FIRSTNAME>");
						sb.append("<COMPANY>" + ebean.getBcBean().getCompany() + "</COMPANY>");
						sb.append("<POSITION>" + ebean.getEmployeePositionText() + "</POSITION>");
						sb.append("<STATUS>" + EmployeeStatusConstant.get(ebean.getStatus()).getDescription() + "</STATUS>");
						sb.append("<MESSAGE>LISTFOUND</MESSAGE>");
						sb.append("</CONTRACTOR>");
					}
				}else{
					sb.append("<CONTRACTOR>");
					sb.append("<MESSAGE>No employees found</MESSAGE>");
					sb.append("</CONTRACTOR>");
				}
			}
			catch (Exception e) {
				// generate XML for candidate details.
				sb.append("<CONTRACTORS>");
				sb.append("<CONTRACTOR>");
				sb.append("<MESSAGE>ERROR SEARCHING CONTRACTORS</MESSAGE>");
				sb.append("</CONTRACTOR>");
				sb.append("</CONTRACTORS>");
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
		sb.append("</CONTRACTORS>");
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
