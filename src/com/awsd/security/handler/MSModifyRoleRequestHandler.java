package com.awsd.security.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class MSModifyRoleRequestHandler implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String path = "";

    String uid = "", ouid = "", desc;
    HttpSession session = null;
    User usr = null;
    Role r = null;
    
    try
    {
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
        throw new RoleException("Role UID required for modification.");
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
          throw new RoleException("Role UID required for modification.");
        }
        
        desc = (String) request.getParameter("description");
        if(desc==null)
        {
          throw new RoleException("Role Description Not Provided");
        }
 
        boolean flag = RoleDB.updateRole(ouid, new Role(uid, desc));

        if(flag)
        {
          request.setAttribute("msgOK", "SUCCESS: Role modified successfully.");
          request.setAttribute("modified", new Boolean(true));
        }
        else
        {
          request.setAttribute("msgERR", "ERROR: Role modification unsuccessfully.");
        }
        r = RoleDB.getRole(uid);
      }
      else
      {
        r = RoleDB.getRole(ouid);
      }
      
      request.setAttribute("Role", r);
    }
    catch(RoleException e)
    {
      request.setAttribute("msgERR", e.getMessage());
      r = RoleDB.getRole(uid);
      request.setAttribute("Role", r);
    }
    //return path;
    return "modifyrole.jsp";
  }
}