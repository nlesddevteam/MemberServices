package com.esdnl.personnel.v2.site.recognition.handler;

import com.awsd.security.*;
import com.awsd.security.SecurityException;
import com.awsd.servlet.*;
import com.esdnl.util.*;

import java.io.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.esdnl.personnel.v2.model.recognition.bean.*;
import com.esdnl.personnel.v2.database.recognition.*;

public class AddAwardTypeRequestHandler  implements RequestHandler
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
        String cid = request.getParameter("cid");
        String t_name = request.getParameter("type_name");
        
        if(StringUtils.isEmpty(cid))
        {
          request.setAttribute("msg", "Category ID is required.");
        }
        if(StringUtils.isEmpty(t_name))
        {
          request.setAttribute("msg", "Please enter AWARD NAME.");
        }
        else
        {
            AwardTypeBean rbean = new AwardTypeBean();
            AwardCategoryBean cat = AwardCategoryManager.getAwardCategoryBean(Integer.parseInt(cid));
            rbean.setCategoryId(cat.getCategoryId());
            rbean.setAwardName(t_name);
            
            AwardTypeManager.addAwardTypeBean(rbean);
            
            request.setAttribute("AWARD_CATEGORY", cat);
            request.setAttribute("AWARD_TYPES", AwardTypeManager.getAwardTypeBean(cat));
        }
        path = "award_types.jsp";
      }
      else
        path = "award_types.jsp";
    }
    catch(Exception e)
    {
      request.setAttribute("msg", e.getMessage());
      path = "award_types.jsp";
    }
    
    return path;
  }
}