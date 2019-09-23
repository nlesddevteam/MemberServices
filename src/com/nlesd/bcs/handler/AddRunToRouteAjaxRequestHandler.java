package com.nlesd.bcs.handler;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.bcs.bean.BussingContractorSystemRouteRunBean;
import com.nlesd.bcs.bean.BussingContractorSystemRouteRunSchoolBean;
import com.nlesd.bcs.dao.BussingContractorSystemRouteRunManager;
import com.nlesd.bcs.dao.BussingContractorSystemRouteRunSchoolManager;
public class AddRunToRouteAjaxRequestHandler extends RequestHandlerImpl{
	public AddRunToRouteAjaxRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("routeid"),
				new RequiredFormElement("routerun"),
				new RequiredFormElement("routetime")
		});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
			IOException {
		super.handleRequest(request, response);
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		String message="";
		if (validate_form()) {
			BussingContractorSystemRouteRunBean vbean = new BussingContractorSystemRouteRunBean();

			try {
				//get fields
				vbean.setRouteId(form.getInt("routeid"));
				vbean.setRouteRun(form.get("routerun"));
				vbean.setRouteTime(form.get("routetime"));
				vbean.setAddedBy(usr.getPersonnel().getFullNameReverse());
				//save file to db
				BussingContractorSystemRouteRunManager.addBussingContractorSystemRouteRun(vbean);
				int cid=vbean.getId();
				//now we loop through the school for the run and add the record
				int x=1;
				String[] ids = form.get("schoolids").split(",");
				for(String s : ids){
					//now we save the record
					BussingContractorSystemRouteRunSchoolBean rbean = new BussingContractorSystemRouteRunSchoolBean();
					rbean.setRouteRunId(cid);
					rbean.setSchoolId(Integer.parseInt(s));
					rbean.setSchoolOrder(x);
					BussingContractorSystemRouteRunSchoolManager.addBussingContractorRouteRunSchool(rbean);
					x++;
				}

			}
			catch (Exception e) {
				message=e.getMessage();
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
			sb.append("<ID>" + vbean.getId() + "</ID>");
			sb.append("</CONTRACTOR>");
			sb.append("</CONTRACTORS>");
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