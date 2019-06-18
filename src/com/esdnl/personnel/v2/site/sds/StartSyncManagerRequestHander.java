package com.esdnl.personnel.v2.site.sds;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;

import com.esdnl.personnel.v2.database.sds.SyncManager;
import com.esdnl.personnel.v2.database.sds.worker.SyncWorker;

public class StartSyncManagerRequestHander implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String path;
    HttpSession session = null;
    User usr = null;
    
    
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("PERSONNEL-SUBSTITUTES-RELOAD-TABLES")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    else
    {
      throw new SecurityException("User login required.");
    }
    
    try
    {
    	
    	if(SyncManager.canSync()){
	    	new SyncWorker().start();
	    	
	    	request.setAttribute("msg", "Synchronization process has started. You will receive an email notification when the process has finished.");
    	}
    	else
    		request.setAttribute("msg", "The synchronization process is currently running.");
      
      path = "admin_sync_status.jsp";
    }
    catch(Exception e)
    {
      e.printStackTrace();
      request.setAttribute("msg", e.getMessage());
      path = "sync_status.jsp";
    }
    
    return path;
  }
}