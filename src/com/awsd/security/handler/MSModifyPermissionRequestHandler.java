package com.awsd.security.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class MSModifyPermissionRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String path = "";

    String uid = "", ouid = "", desc;
    HttpSession session = null;
    User usr = null;
    Permission p = null;
    
    try
    {
     path =  "modifypermission.jsp";   
      session = request.getSession(false);
      if((session != null) && (session.getAttribute("usr") != null))
      {
        usr = (User) session.getAttribute("usr");
        if(!(usr.getUserPermissions().containsKey("MEMBERADMIN-VIEW")))
        {
          throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
        }
      }
      else
      {
        throw new SecurityException("User login required.");
      }
      
      if(request.getParameter("ouid") != null)
      {
        ouid = (String)request.getParameter("ouid");
      }
      else
      {
        throw new PermissionException("Permission UID required for modification.");
      }
      
      request.setAttribute("modified", new Boolean(false));

      if(request.getParameter("confirmed") != null)
      {
        if(request.getParameter("uid") != null)
        {
          uid = (String)request.getParameter("uid");
        }
        else
        {
          throw new PermissionException("Permission UID required for modification.");
        }
        
        desc = (String) request.getParameter("description");
        if(desc==null)
        {
          throw new PermissionException("Permission Description Not Provided");
        }
 
        boolean flag = PermissionDB.updatePermission(ouid, new Permission(uid, desc));

        if(flag)
        {
          request.setAttribute("msgOK", "SUCCESS: Permission modified successfully.");
          request.setAttribute("modified", new Boolean(true));
          path= "viewpermissions.jsp";
        }
        else
        {
          request.setAttribute("msgERR", "ERROR: Permission modification unsuccessfully.");
        }
        p = PermissionDB.getPermission(uid);
      }
      else
      {
        p = PermissionDB.getPermission(ouid);
      }
      
      request.setAttribute("Permission", p);
      
    }
    catch(PermissionException e)
    {
      request.setAttribute("msgERR", e.getMessage());
      p = PermissionDB.getPermission(uid);
      request.setAttribute("Permission", p);
      path =  "modifypermission.jsp";  
    }
    return path;
    
  }
}