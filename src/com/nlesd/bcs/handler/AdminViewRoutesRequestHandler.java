package com.nlesd.bcs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.awsd.security.User;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.constants.BoardOwnedContractorsConstant;
import com.nlesd.bcs.dao.BussingContractorSystemRouteManager;
public class AdminViewRoutesRequestHandler  extends RequestHandlerImpl
{
	  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException
	  {
		  super.handleRequest(request, response);
	    HttpSession session = null;
	    User usr = null;
	    String path = "";
	    
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
	    if(usr.checkPermission("BCS-VIEW-WESTERN") || usr.checkPermission("BCS-VIEW-CENTRAL") || usr.checkPermission("BCS-VIEW-LABRADOR")){
	    	int cid=0;
	    	if(usr.checkPermission("BCS-VIEW-WESTERN")){
				cid = BoardOwnedContractorsConstant.WESTERN.getValue();
				request.setAttribute("routes", BussingContractorSystemRouteManager.getRoutesRegionalAdmin(cid));
			}
			if(usr.checkPermission("BCS-VIEW-CENTRAL")){
				cid = BoardOwnedContractorsConstant.CENTRAL.getValue();
				request.setAttribute("routes", BussingContractorSystemRouteManager.getRoutesRegionalAdmin(cid));
			}
			if(usr.checkPermission("BCS-VIEW-LABRADOR")){
				cid = BoardOwnedContractorsConstant.LABRADOR.getValue();
				request.setAttribute("routes", BussingContractorSystemRouteManager.getRoutesRegionalAdmin(cid));
			}
	    }else{
	    	request.setAttribute("routes", BussingContractorSystemRouteManager.getRoutes());
	    }
	    
		path = "admin_view_routes.jsp";
	    return path;
	  }
}
