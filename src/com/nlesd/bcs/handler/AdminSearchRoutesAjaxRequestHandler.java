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
import com.nlesd.bcs.bean.BussingContractorSystemRouteBean;
import com.nlesd.bcs.constants.BoardOwnedContractorsConstant;
import com.nlesd.bcs.dao.BussingContractorSystemRouteManager;
public class AdminSearchRoutesAjaxRequestHandler extends RequestHandlerImpl {
	public AdminSearchRoutesAjaxRequestHandler() {
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
		try {
			String searchby = form.get("searchby");
			String searchfor = form.get("searchfor");
			Integer searchschool=form.getInt("searchschool");
			ArrayList<BussingContractorSystemRouteBean> list = new ArrayList<BussingContractorSystemRouteBean>();
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			if (validate_form()) {
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
					if(searchby.equals("Name") || searchby.equals("Notes")){
						list = BussingContractorSystemRouteManager.searchContractsByStringReg(searchby, searchfor,cid);
					}else{
						list = BussingContractorSystemRouteManager.searchRoutesByIntegerReg(searchby, searchschool,cid);
					}
				}else{
					if(searchby.equals("Name") || searchby.equals("Notes")){
						list = BussingContractorSystemRouteManager.searchContractsByString(searchby, searchfor);
					}else{
						list = BussingContractorSystemRouteManager.searchRoutesByInteger(searchby, searchschool);
					}
				}
				
				sb.append("<ROUTES>");
				if(list.size() > 0){
					for(BussingContractorSystemRouteBean ebean : list){
						sb.append("<ROUTE>");
						sb.append("<ID>" + ebean.getId() + "</ID>");
						sb.append("<ROUTENAME>" + ebean.getRouteName()+ "</ROUTENAME>");
						sb.append("<ROUTESCHOOL>" + ebean.getRouteSchoolString() + "</ROUTESCHOOL>");
						sb.append("<CONTRACTNAME>" + ebean.getContractBean().getContractName() + "</CONTRACTNAME>");
						sb.append("<ADDEDBY>" + ebean.getAddedBy() + "</ADDEDBY>");
						sb.append("<MESSAGE>LISTFOUND</MESSAGE>");
						sb.append("</ROUTE>");
					}
				}else{
					sb.append("<ROUTE>");
					sb.append("<MESSAGE>No routes found</MESSAGE>");
					sb.append("</ROUTE>");
				}
			}else {
				sb.append("<CONTRACTS>");
				sb.append("<CONTRACT>");
				sb.append("<MESSAGE>" + com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString())+ "</MESSAGE>");
				sb.append("</CONTRACT>");
				sb.append("</CONTRACTS>");
			}

			String xml = null;
			sb.append("</ROUTES>");
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
			sb.append("<ROUTES>");
			sb.append("<ROUTE>");
			sb.append("<MESSAGE>ERROR SEARCHING ROUTES</MESSAGE>");
			sb.append("</ROUTE>");
			sb.append("</ROUTES>");
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