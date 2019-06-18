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

public class requestActionRequestHandler implements RequestHandler
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
      String id = request.getParameter("id");
      String cat_id = request.getParameter("cat_id");
      String award_id = request.getParameter("award_id");
      
      if(!StringUtils.isEmpty(op) && !StringUtils.isEmpty(id))
      {
        RecognitionRequestBean req = RecognitionRequestManager.getRecognitionRequestBean(Integer.parseInt(id)); 
        
        if(op.equals("PRESUBMIT_CATEGORY"))
        {
          if(!StringUtils.isEmpty(cat_id))
          {
            AwardCategoryBean cat = AwardCategoryManager.getAwardCategoryBean(Integer.parseInt(cat_id));
            request.setAttribute("AWARD_CATEGORY", cat);
            request.setAttribute("RECOGNITION_REQUEST", req);
          }
          
          path = "view_request.jsp";
        }
        else if(op.equals("APPROVE_REQUEST"))
        {
          if(StringUtils.isEmpty(cat_id))
          {
            request.setAttribute("msg", "Please choose an AWARD CATEGORY.");
            request.setAttribute("RECOGNITION_REQUEST", req);
            
            path = "view_request.jsp";
          }
          else if(StringUtils.isEmpty(award_id))
          {
            AwardCategoryBean cat = AwardCategoryManager.getAwardCategoryBean(Integer.parseInt(cat_id));
            request.setAttribute("AWARD_CATEGORY", cat);
            
            request.setAttribute("msg", "Please choose an AWARD TYPE.");
            request.setAttribute("RECOGNITION_REQUEST", req);
            
            path = "view_request.jsp";
          }
          else
          {
            AwardTypeBean abean = AwardTypeManager.getAwardTypeBean(Integer.parseInt(award_id));
            req.setAwardType(abean);
            RecognitionRequestManager.approveRecognitionRequestBean(req, usr.getPersonnel());
            request.setAttribute("msg", "REQUEST APPROVED.");
            request.setAttribute("REQUESTS", RecognitionRequestManager.getRecognitionRequestBean(RequestStatus.SUBMITTED));
            
            path = "list_requests.jsp";
          }
        }
        else if(op.equals("DECLINE_REQUEST"))
        {
            RecognitionRequestManager.declineRecognitionRequestBean(req, usr.getPersonnel());
            request.setAttribute("msg", "REQUEST DECLINED.");
            request.setAttribute("REQUESTS", RecognitionRequestManager.getRecognitionRequestBean(RequestStatus.SUBMITTED));
            
            path = "list_requests.jsp";
        }
        else if(op.equals("PROCESS_REQUEST"))
        {
          session.setAttribute("RECOGNITION_REQUEST", req);
          session.setAttribute("TEMPLATE", null);
          path = "process_request.jsp";
        }
        else
        {
          request.setAttribute("RECOGNITION_REQUEST", req);
          path = "view_request.jsp";
        }
      }
      else
      {
        path = "index.jsp";
      }
    }
    catch(Exception e)
    {
      request.setAttribute("msg", e.getMessage());
      path = "index.jsp";
    }
    
    return path;
  }
}