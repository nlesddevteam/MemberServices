package com.esdnl.webmaint.policies.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import com.esdnl.webmaint.policies.*;

import java.io.*;

import java.sql.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import javazoom.upload.*;


public class AddPolicyRegulationRequestHandler  implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    String path = "";
    UploadBean bean = null;
    MultipartFormDataRequest mrequest = null;
    UploadFile file = null;
    Hashtable files = null;
    File f = null;
    String[] pol_code = null;
    
    
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {      
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("WEBMAINTENANCE-VIEW")
           && usr.getUserPermissions().containsKey("WEBMAINTENANCE-DISTRICTPOLICIES")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    
    try
    {
      if (MultipartFormDataRequest.isMultipartFormData(request))
      {
        mrequest = new MultipartFormDataRequest(request);
        
        if(mrequest.getParameter("op") != null)
        {
          if(mrequest.getParameter("op").equals("CONFIRM"))
          {
            if((mrequest.getParameter("policy") == null) || mrequest.getParameter("policy").equals("-1"))
            {
              request.setAttribute("msg", "POLICY IS REQUIRED");
            }
            else if((mrequest.getParameter("reg_code") == null) || mrequest.getParameter("reg_code").equals(""))
            {
              request.setAttribute("msg", "REGULATION CODE IS REQUIRED");
            }
            else if((mrequest.getParameter("reg_title") == null) || mrequest.getParameter("reg_title").equals(""))
            {
              request.setAttribute("msg", "REGULATION TITLE IS REQUIRED");
            }
            else if(mrequest.getParameter("reg_code").length() > 10)
            {
              request.setAttribute("msg", "REGULATION CODE HAS MAX LENGHT OF 10 CHARACTERS");
            }
            else if(mrequest.getParameter("reg_title").length() > 100)
            {
              request.setAttribute("msg", "REGULATION TITLE HAS MAX LENGHT OF 100 CHARACTERS");
            }
            else
            {
              files = mrequest.getFiles();
              
              if((files == null) || (files.size() < 1))
              {
                request.setAttribute("msg", "PLEASE SELECT POLICY REGULATION FILE TO UPLOAD");
              }
              else
              {
                file = (UploadFile) files.get("filedata");
                
                if((file.getFileName().indexOf(".pdf") < 0) && (file.getFileName().indexOf(".PDF") < 0))
                {
                  request.setAttribute("msg", "ONLY PDF POLICY REGULATION FILES ARE ACCEPTED");
                }
                else
                {
                  try
                  {
                    pol_code = mrequest.getParameter("policy").toUpperCase().split("-");
                    
                    PolicyRegulationDB.addPolicyRegulation(new PolicyRegulation(pol_code[0],  pol_code[1], 
                      mrequest.getParameter("reg_code").toUpperCase(), mrequest.getParameter("reg_title").toUpperCase()));
                    
                    bean = new UploadBean();
                    bean.setFolderstore(session.getServletContext().getRealPath("/") + "../ROOT/about/policies/esd/regulations/");
                    bean.store(mrequest, "filedata");
                    f = new File(session.getServletContext().getRealPath("/") + "../ROOT/about/policies/esd/regulations/" + file.getFileName());
                    
                    f.renameTo(new File(session.getServletContext().getRealPath("/") + "../ROOT/about/policies/esd/regulations/"
                      + pol_code[0] + "_" + pol_code[1] + "_" + mrequest.getParameter("reg_code").toUpperCase() 
                      + ".pdf"));
                    request.setAttribute("msg", "POLICY REGULATION ADDED SUCCESSFULLY");
                  }
                  catch(SQLException e)
                  {
                    switch(e.getErrorCode())
                    {
                      case 1:
                        request.setAttribute("msg", "POLICY REGULATION" + mrequest.getParameter("reg_code") + " ALREADY EXISTS");
                        break;
                      default:
                        request.setAttribute("msg", e.getMessage());
                    }
                  }
                }
              }              
            }
          }
        }
      }
    }
    catch(Exception e)
    {
      request.setAttribute("msg", e.getMessage());
    }
   
    request.setAttribute("POLICIES", PolicyDB.getPolicies());
    path = "add_policy_regulation.jsp";
    
    return path;
  }
}