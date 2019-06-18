package com.nlesd.eecd.handler;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.nlesd.eecd.bean.EECDAreaBean;
import com.nlesd.eecd.dao.EECDAreaManager;
public class AddNewAreaAjaxRequestHandler implements RequestHandler
{
	  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException
	  {
	    HttpSession session = null;
	    User usr = null;
	    String message="";
	    int newid=0;
	    session = request.getSession(false);
	    if((session != null) && (session.getAttribute("usr") != null))
	    {
	      usr = (User) session.getAttribute("usr");
	      if(!(usr.getUserPermissions().containsKey("EECD-VIEW-ADMIN")))
	      {
	        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
	      }
	    }
	    else
	    {
	      throw new SecurityException("User login required.");
	    }
	    try{
	    	EECDAreaBean ebean = new EECDAreaBean();
		    ebean.setAddedBy(usr.getLotusUserFullName());
		    ebean.setAreaDescription(request.getParameter("areadescription"));
		    newid = EECDAreaManager.addAreaDescription(ebean);
		    message="ADDED";
	    }catch(Exception e){
	    	message=e.toString();
	    }
	    
	    
	    String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		sb.append("<CAREAS>");
		sb.append("<CAREA>");
		sb.append("<MESSAGE>" + message + "</MESSAGE>");
		sb.append("<ID>" + newid + "</ID>");
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
