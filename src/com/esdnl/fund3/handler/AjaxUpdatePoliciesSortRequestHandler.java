package com.esdnl.fund3.handler;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.fund3.dao.PolicyManager;
import com.esdnl.servlet.RequestHandlerImpl;
public class AjaxUpdatePoliciesSortRequestHandler  extends RequestHandlerImpl {
	public AjaxUpdatePoliciesSortRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		String message ="";
		super.handleRequest(request, response);
		try {
			String sortorder=form.get("sortorder");
			if(sortorder.length() > 0){
				String[] policies = sortorder.split(",");
				Integer x=1;
				for(String s : policies){
					PolicyManager.updatePolicySortOrder(Integer.parseInt(s), x);
					x++;
					
				}
				message="SUCCESS";
			}else{
				message="Error Updating Database";
			}
			// generate XML for candidate details.
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<POLICIES>");
			sb.append("<POLICY>");
			sb.append("<MESSAGE>" + message +"</MESSAGE>");
			sb.append("</POLICY>");
			sb.append("</POLICIES>");
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
			sb.append("<POLICIES>");
			sb.append("<POLICY>");
			sb.append("<MESSAGE>Error Updating Sort Order</MESSAGE>");
			sb.append("</POLICY>");
			sb.append("</POLICIES>");
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