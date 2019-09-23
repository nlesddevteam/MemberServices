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
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.constants.StatusConstant;
import com.nlesd.bcs.dao.BussingContractorManager;
public class SearchContractorsAjaxRequestHandler extends RequestHandlerImpl {
	public SearchContractorsAjaxRequestHandler() {
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
				Integer searchi =form.getInt("searchstatus");
				ArrayList<BussingContractorBean> list = new ArrayList<BussingContractorBean>();
				if(searchby.equals("Status")){
					list = BussingContractorManager.searchContractorsByInteger(searchby, searchfor,searchi);
				}else{
					list = BussingContractorManager.searchContractorsByString(searchby, searchfor,searchp);
				}
				sb.append("<CONTRACTORS>");
				if(list.size() > 0){
					for(BussingContractorBean ebean : list){
						sb.append("<CONTRACTOR>");
						sb.append("<ID>" + ebean.getId() + "</ID>");
						sb.append("<LASTNAME>" + ebean.getLastName()+ "</LASTNAME>");
						sb.append("<FIRSTNAME>" + ebean.getFirstName() + "</FIRSTNAME>");
						sb.append("<CITY>" + ebean.getCity() + "</CITY>");
						sb.append("<COMPANY>" + ebean.getCompany() + "</COMPANY>");
						sb.append("<STATUS>" + StatusConstant.get(ebean.getStatus()).getDescription() + "</STATUS>");
						sb.append("<MESSAGE>LISTFOUND</MESSAGE>");
						sb.append("</CONTRACTOR>");
					}
				}else{
					sb.append("<CONTRACTOR>");
					sb.append("<MESSAGE>No contractors found</MESSAGE>");
					sb.append("</CONTRACTOR>");
				}
				
				sb.append("</CONTRACTORS>");
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