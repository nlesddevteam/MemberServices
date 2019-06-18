package com.esdnl.personnel.jobs.handler;

import com.awsd.servlet.*;
import com.esdnl.util.*;
import com.esdnl.personnel.jobs.bean.*;
import com.esdnl.personnel.jobs.dao.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ViewSubListApplicantsRequestHandler  implements LoginNotRequiredRequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String path;
    HttpSession session = null;
    ApplicantProfileBean[] profiles = null;
    SubListBean list = null;
    
    
    try
    {
      session = request.getSession();
      
      String list_id = request.getParameter("list_id");
      
      if(StringUtils.isEmpty(list_id))
      {
        request.setAttribute("msg", "List id required to view applicants.");
        path = "admin_view_sub_lists.jsp";
      }
      else
      {
        profiles = ApplicantProfileManager.getApplicantProfileBeanBySubList(Integer.parseInt(list_id));
        list = SubListManager.getSubListBean(Integer.parseInt(list_id));
        session.setAttribute("SUBLIST_APPLICANTS", profiles);
        session.setAttribute("SUBLIST", list);
        session.setAttribute("SHORTLISTMAP", ApplicantProfileManager.getApplicantShortlistMap(list));
        session.setAttribute("SHORTLIST", ApplicantProfileManager.getApplicantShortlist(list));
        session.setAttribute("NOTAPPROVEDMAP", ApplicantProfileManager.getApplicantsNotApprovedMap(list));
        session.setAttribute("NOTAPPROVED", ApplicantProfileManager.getApplicantsNotApproved(list));
        session.setAttribute("WORKINGMAP", ApplicantProfileManager.getApplicantsWorkingMap(list));
        session.setAttribute("WORKING", ApplicantProfileManager.getApplicantsWorking(list));
        path = "admin_view_sublist_applicants.jsp";
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