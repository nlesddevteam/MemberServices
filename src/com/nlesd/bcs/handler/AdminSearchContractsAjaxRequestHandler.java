package com.nlesd.bcs.handler;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.bean.BussingContractorSystemContractBean;
import com.nlesd.bcs.constants.BoardOwnedContractorsConstant;
import com.nlesd.bcs.dao.BussingContractorSystemContractManager;
public class AdminSearchContractsAjaxRequestHandler extends RequestHandlerImpl {
	public AdminSearchContractsAjaxRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		try {
			String searchby = form.get("searchby");
			String searchfor = form.get("searchfor");
			Integer searchtype =form.getInt("ctype");
			Integer searchregion =form.getInt("cregion");
			String searchexpiry = form.get("cexpirydate");
			ArrayList<BussingContractorSystemContractBean> list = new ArrayList<BussingContractorSystemContractBean>();
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
				if(searchby.equals("Name") || searchby.equals("Notes") || searchby.equals("Expiry Date")){
					if(searchby.equals("Expiry Date")){
						list = BussingContractorSystemContractManager.searchContractsByStringReg(searchby, searchfor,searchexpiry,cid);
					}else{
						list = BussingContractorSystemContractManager.searchContractsByStringReg(searchby, searchfor,searchfor,cid);
					}
					
				}else{
					if(searchby.equals("Region")){
						list = BussingContractorSystemContractManager.searchContractsByIntegerReg(searchby, searchfor,searchregion,cid);
					}else{
						list = BussingContractorSystemContractManager.searchContractsByIntegerReg(searchby, searchfor,searchtype,cid);
					}
					
				}
			}else{
				if(searchby.equals("Name") || searchby.equals("Notes") || searchby.equals("Expiry Date")){
					if(searchby.equals("Expiry Date")){
						list = BussingContractorSystemContractManager.searchContractsByString(searchby, searchfor,searchexpiry);
					}else{
						list = BussingContractorSystemContractManager.searchContractsByString(searchby, searchfor,searchfor);
					}
					
				}else{
					if(searchby.equals("Region")){
						list = BussingContractorSystemContractManager.searchContractsByInteger(searchby, searchfor,searchregion);
					}else{
						list = BussingContractorSystemContractManager.searchContractsByInteger(searchby, searchfor,searchtype);
					}
					
				}
			}
			
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<CONTRACTS>");
			if(list.size() > 0){
				for(BussingContractorSystemContractBean ebean : list){
					sb.append("<CONTRACT>");
					sb.append("<ID>" + ebean.getId() + "</ID>");
					sb.append("<CONTRACTNAME>" + ebean.getContractName()+ "</CONTRACTNAME>");
					sb.append("<CONTRACTTYPE>" + ebean.getContractTypeString() + "</CONTRACTTYPE>");
					sb.append("<CONTRACTREGION>" + ebean.getContractRegionString() + "</CONTRACTREGION>");
					sb.append("<CONTRACTEXPIRYDATE>" + ebean.getContractExpiryDateFormatted() + "</CONTRACTEXPIRYDATE>");
					sb.append("<CONTRACTSTATUS>" + ebean.getContractHistory().getStatusString() + "</CONTRACTSTATUS>");
					sb.append("<MESSAGE>LISTFOUND</MESSAGE>");
					sb.append("</CONTRACT>");
				}
			}else{
				sb.append("<CONTRACT>");
				sb.append("<MESSAGE>No contracts found</MESSAGE>");
				sb.append("</CONTRACT>");
			}
			
			sb.append("</CONTRACTS>");
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
