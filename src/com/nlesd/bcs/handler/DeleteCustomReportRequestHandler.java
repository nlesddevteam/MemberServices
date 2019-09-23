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
import com.nlesd.bcs.dao.BussingContractorSystemCustomReportManager;
public class DeleteCustomReportRequestHandler extends RequestHandlerImpl {
	public DeleteCustomReportRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("reportid")
			});
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException{
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		super.handleRequest(request, response);
		if (validate_form()) {
			try{
		    	Integer did = form.getInt("reportid");
		    	BussingContractorSystemCustomReportManager.deleteBussingContractorCustomReportBean(did);
		    	sb.append("<REPORTS>");
				sb.append("<REPORT>");
				sb.append("<MESSAGE>SUCCESS</MESSAGE>");
				sb.append("</REPORT>");
				sb.append("</REPORTS>");
			}catch(Exception e){
		    	sb.append("<REPORTS>");
				sb.append("<REPORT>");
				sb.append("<MESSAGE>" + e.getMessage() + "</MESSAGE>");
				sb.append("</REPORT>");
				sb.append("</REPORTS>");
				xml = sb.toString().replaceAll("&", "&amp;");
				PrintWriter out = response.getWriter();
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
	        }  
		}else {
			sb.append("<REPORTS>");
			sb.append("<REPORT>");
			sb.append("<MESSAGE>" + com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()) + "</MESSAGE>");
			sb.append("</REPORT>");
			sb.append("</REPORTS>");
			xml = sb.toString().replaceAll("&", "&amp;");
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
		}
	     
	    return null;
	    
        
	}
}
