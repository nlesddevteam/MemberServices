package com.esdnl.personnel.v2.site.recognition.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.security.SecurityException;
import com.awsd.servlet.*;
import com.esdnl.util.*;

import java.io.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.esdnl.personnel.v2.database.recognition.*;
import com.esdnl.personnel.v2.model.recognition.bean.*;
import com.esdnl.personnel.v2.model.recognition.constant.*;

public class ViewAwardTypesRequestHandler  implements RequestHandler
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
      if(!(usr.getUserPermissions().containsKey("PERSONNEL-RECOGNITION-VIEW")
        || usr.getUserPermissions().containsKey("PERSONNEL-RECOGNITION-ADMIN-VIEW")))
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
      String cid = request.getParameter("cid");
      
      if(!StringUtils.isEmpty(cid))
      {
        AwardCategoryBean cat = AwardCategoryManager.getAwardCategoryBean(Integer.parseInt(cid));
        
        if(cat != null)
        {
          request.setAttribute("AWARD_CATEGORY", cat);
          request.setAttribute("AWARD_TYPES", AwardTypeManager.getAwardTypeBean(cat));
          path = "award_types.jsp";
        }
        else
        {
          request.setAttribute("msg", "Category NOT found.");
          path = "award_categories.jsp";
        }
      }
      else
      {
        request.setAttribute("msg", "Category ID required.");
        path = "award_categories.jsp";
      }
    }
    catch(Exception e)
    {
      request.setAttribute("msg", e.getMessage());
      path = "award_categories.jsp";
    }
    
    return path;
  }
}