package com.nlesd.bcs.handler;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.bean.BussingContractorVehicleBean;
import com.nlesd.bcs.constants.BoardOwnedContractorsConstant;
import com.nlesd.bcs.constants.StatusConstant;
import com.nlesd.bcs.dao.BussingContractorVehicleManager;
public class SearchVehiclesAjaxRequestHandler extends RequestHandlerImpl {
	public SearchVehiclesAjaxRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		try {
			String searchby = form.get("searchby");
			String searchfor = form.get("searchfor");
			String searchp = form.get("searchprovince");
			Integer searchi = form.getInt("searchstatus");
			Integer searchmake = form.getInt("searchmake");
			Integer searchtype = form.getInt("searchtype");
			//Integer searchmodel = form.getInt("searchmodel");
			Integer searchsize = form.getInt("searchsize");
			ArrayList<BussingContractorVehicleBean> list = new ArrayList<BussingContractorVehicleBean>();
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
					list = BussingContractorVehicleManager.searchVehiclesByIntegerReg(searchby, searchfor,searchi,cid);
				}else if(searchby.equals("Make")){
					list = BussingContractorVehicleManager.searchVehiclesByIntegerReg(searchby, searchfor,searchmake,cid);
				}else if(searchby.equals("Model")){
					list = BussingContractorVehicleManager.searchVehiclesByStringReg(searchby, searchfor,searchp,cid);
				}else if(searchby.equals("Type")){
					list = BussingContractorVehicleManager.searchVehiclesByIntegerReg(searchby, searchfor,searchtype,cid);
				}else if(searchby.equals("Size")){
					list = BussingContractorVehicleManager.searchVehiclesByIntegerReg(searchby, searchfor,searchsize,cid);
				}else{
					list = BussingContractorVehicleManager.searchVehiclesByStringReg(searchby, searchfor,searchp,cid);
				}
			}else{
				if(searchby.equals("Status")){
					list = BussingContractorVehicleManager.searchVehiclesByInteger(searchby, searchfor,searchi);
				}else if(searchby.equals("Make")){
					list = BussingContractorVehicleManager.searchVehiclesByInteger(searchby, searchfor,searchmake);
				}else if(searchby.equals("Model")){
					list = BussingContractorVehicleManager.searchVehiclesByString(searchby, searchfor,searchp);
				}else if(searchby.equals("Type")){
					list = BussingContractorVehicleManager.searchVehiclesByInteger(searchby, searchfor,searchtype);
				}else if(searchby.equals("Size")){
					list = BussingContractorVehicleManager.searchVehiclesByInteger(searchby, searchfor,searchsize);
				}else{
					list = BussingContractorVehicleManager.searchVehiclesByString(searchby, searchfor,searchp);
				}
			}
			
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<CONTRACTORS>");
			if(list.size() > 0){
				for(BussingContractorVehicleBean ebean : list){
					sb.append("<CONTRACTOR>");
					sb.append("<ID>" + ebean.getId() + "</ID>");
					sb.append("<COMPANY>" + ebean.getBcBean().getCompany()+ "</COMPANY>");
					sb.append("<PLATENUMBER>" + ebean.getvPlateNumber() + "</PLATENUMBER>");
					sb.append("<SERIALNUMBER>" + ebean.getvSerialNumber() + "</SERIALNUMBER>");
					sb.append("<STATUS>" + StatusConstant.get(ebean.getvStatus()).getDescription() + "</STATUS>");
					sb.append("<MESSAGE>LISTFOUND</MESSAGE>");
					sb.append("</CONTRACTOR>");
				}
			}else{
				sb.append("<CONTRACTOR>");
				sb.append("<MESSAGE>No vehicles found</MESSAGE>");
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

			
		}
		catch (Exception e) {
			// generate XML for candidate details.
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
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
		return path;
	}

}
