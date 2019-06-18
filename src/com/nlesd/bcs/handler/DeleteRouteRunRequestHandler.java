package com.nlesd.bcs.handler;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.awsd.security.User;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.dao.BussingContractorSystemRouteRunManager;
public class DeleteRouteRunRequestHandler extends RequestHandlerImpl {
	public DeleteRouteRunRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException{
		super.handleRequest(request, response);
		HttpSession session = null;
	    User usr = null;
	    session = request.getSession(false);
	    if((session != null) && (session.getAttribute("usr") != null))
	    {
	      usr = (User) session.getAttribute("usr");
	      if(!(usr.getUserPermissions().containsKey("BCS-SYSTEM-ACCESS")))
	      {
	        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
	      }
	    }
	    else
	    {
	      throw new SecurityException("User login required.");
	    }
	    
	    try{
	    	Integer did = form.getInt("routeid");
	    	BussingContractorSystemRouteRunManager.deleteRouteRun(did);
	    	String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<ROUTES>");
			sb.append("<ROUTE>");
			sb.append("<MESSAGE>SUCCESS</MESSAGE>");
			sb.append("</ROUTE>");
			sb.append("</ROUTES>");
			xml = sb.toString().replaceAll("&", "&amp;");
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
	    }catch(Exception e){
	    	String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<ROUTES>");
			sb.append("<ROUTE>");
			sb.append("<MESSAGE>" + e.getMessage() + "</MESSAGE>");
			sb.append("</ROUTE>");
			sb.append("</ROUTES>");
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
