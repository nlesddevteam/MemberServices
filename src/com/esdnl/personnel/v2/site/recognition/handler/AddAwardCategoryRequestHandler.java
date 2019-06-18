package com.esdnl.personnel.v2.site.recognition.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.security.SecurityException;
import com.awsd.servlet.*;
import com.esdnl.util.*;

import java.io.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.esdnl.personnel.v2.model.recognition.bean.*;
import com.esdnl.personnel.v2.database.recognition.*;

public class AddAwardCategoryRequestHandler  implements RequestHandler
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
      String op = request.getParameter("op"); 
      if(!StringUtils.isEmpty(op))
      {
        String c_name = request.getParameter("cat_name");
        
        if(StringUtils.isEmpty(c_name))
        {
          request.setAttribute("msg", "Please enter CATEGORY NAMEe.");
        }
        else
        {
            AwardCategoryBean rbean = new AwardCategoryBean();
            
            rbean.setCategoryName(c_name);
            
            AwardCategoryManager.addAwardCategoryBean(rbean);
            
            request.setAttribute("AWARD_CATEGORIES", AwardCategoryManager.getAwardCategoryBean());
        }
        path = "award_categories.jsp";
      }
      else
        path = "award_categories.jsp";
    }
    catch(Exception e)
    {
      request.setAttribute("msg", e.getMessage());
      path = "award_categories.jsp";
    }
    
    return path;
  }
}