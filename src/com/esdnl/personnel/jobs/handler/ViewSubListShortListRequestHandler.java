package com.esdnl.personnel.jobs.handler;

import com.awsd.security.*;
import com.awsd.servlet.*;
import com.esdnl.util.*;
import com.esdnl.personnel.jobs.bean.*;
import com.esdnl.personnel.jobs.dao.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ViewSubListShortListRequestHandler implements LoginNotRequiredRequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String path;
    HttpSession session = null;
    User usr = null;
    ApplicantProfileBean[] profiles = null;
    SubListBean list= null;
    
    
    try
    {
      session = request.getSession();
      
      usr = (User) session.getAttribute("usr");
      
      String list_id = request.getParameter("list_id");
      
      if(StringUtils.isEmpty(list_id))
      {
        request.setAttribute("msg", "LIST_ID required to view applicant shortlist.");
        path = "admin_view_sub_lists.jsp";
      }
      else
      {
        
        list = SubListManager.getSubListBean(Integer.parseInt(list_id));
        session.setAttribute("SUBLIST", list);
        
        if(usr.getUserPermissions().containsKey("PERSONNEL-ADMIN-VIEW"))
          profiles = ApplicantProfileManager.getApplicantShortlist(list);
        else if(usr.getUserPermissions().containsKey("PERSONNEL-PRINCIPAL-VIEW") 
             || usr.getUserPermissions().containsKey("PERSONNEL-VICEPRINCIPAL-VIEW"))
        {
          profiles = ApplicantProfileManager.getApplicantShortlist(list, usr.getPersonnel().getSchool());
        }
        else
          profiles = null;
        
        session.setAttribute("SUBLIST_SHORTLIST", profiles);
        path = "admin_view_sublist_applicants_shortlist.jsp";
      }
      
    }
    catch(JobOpportunityException e)
    {
      e.printStackTrace();
      request.setAttribute("msg", "Could not retrieve Job applicants.");
      path = "admin_view_sub_lists.jsp";
    }
    
    return path;
  }
}