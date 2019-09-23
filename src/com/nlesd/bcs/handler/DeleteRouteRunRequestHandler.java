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
import com.nlesd.bcs.dao.BussingContractorSystemRouteRunManager;
public class DeleteRouteRunRequestHandler extends RequestHandlerImpl {
	public DeleteRouteRunRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("routeid")
		});
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException{
		super.handleRequest(request, response);
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		if (validate_form()) {
			try{
		    	Integer did = form.getInt("routeid");
		    	BussingContractorSystemRouteRunManager.deleteRouteRun(did);
		    	sb.append("<ROUTES>");
				sb.append("<ROUTE>");
				sb.append("<MESSAGE>SUCCESS</MESSAGE>");
				sb.append("</ROUTE>");
				sb.append("</ROUTES>");
				
		    }catch(Exception e){
		    	sb.append("<ROUTES>");
				sb.append("<ROUTE>");
				sb.append("<MESSAGE>" + e.getMessage() + "</MESSAGE>");
				sb.append("</ROUTE>");
				sb.append("</ROUTES>");
			}   
		}else {
			sb.append("<ROUTES>");
			sb.append("<ROUTE>");
			sb.append("<MESSAGE>" + com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()) + "</MESSAGE>");
			sb.append("</ROUTE>");
			sb.append("</ROUTES>");
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
