package com.nlesd.eecd.handler;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.eecd.dao.EECDAreaManager;
public class DeleteAreaAjaxRequestHandler extends RequestHandlerImpl
{
	public DeleteAreaAjaxRequestHandler() {
		this.requiredPermissions = new String[] {
				"EECD-VIEW-ADMIN"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("aid"),
				new RequiredFormElement("astatus")
		});
	}
	  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException
	  {
		  super.handleRequest(request, response);
	    String message="";
	    String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		if (validate_form()) {
			try{
		    	int id = Integer.parseInt(request.getParameter("aid"));
		    	EECDAreaManager.deleteArea(id);
			    message="DELETED";
		    }catch(Exception e){
		    	sb.append("<CAREAS>");
				sb.append("<CAREA>");
				sb.append("<MESSAGE>" + e.getMessage() + "</MESSAGE>");
				sb.append("</CAREA>");
				sb.append("</CAREAS>");
				xml = sb.toString().replaceAll("&", "&amp;");
				PrintWriter out = response.getWriter();
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
				return null;
		    }
		}else {
			message=com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString());
		}
	    sb.append("<CAREAS>");
		sb.append("<CAREA>");
		sb.append("<MESSAGE>" + message + "</MESSAGE>");
		sb.append("</CAREA>");
		sb.append("</CAREAS>");
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
