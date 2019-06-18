package com.esdnl.webmaint.ceoweb.handler;

import com.awsd.security.*;
import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import com.esdnl.webmaint.ceoweb.bean.*;
import com.esdnl.webmaint.ceoweb.dao.*;

import java.io.*;

import java.sql.*;

import java.text.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import javazoom.upload.*;

public class AddDirectorReportRequestHandler  implements RequestHandler
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
    SimpleDateFormat sdf_file = new SimpleDateFormat("MM_yyyy");
    SimpleDateFormat sdf_full = new SimpleDateFormat("MMMM yyyy");
    DirectorReportBean rpt = null;
    
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {      
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("WEBMAINTENANCE-VIEW")
           && usr.getUserPermissions().containsKey("WEBMAINTENANCE-DIRECTORSWEB")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    
    try
    {
      if(MultipartFormDataRequest.isMultipartFormData(request))
      {
        mrequest = new MultipartFormDataRequest(request);
        
        if(mrequest.getParameter("op") != null)
        {
          if(mrequest.getParameter("op").equals("CONFIRM"))
          {
            if(mrequest.getParameter("month") == null)
            {
              request.setAttribute("msg", "REPORT MONTH IS REQUIRED");
            }
            else if(mrequest.getParameter("year") == null)
            {
              request.setAttribute("msg", "REPORT YEAR IS REQUIRED");
            }
            else if(mrequest.getParameter("title") == null)
            {
              request.setAttribute("msg", "REPORT TITLE IS REQUIRED");
            }
            
            else
            {
              files = mrequest.getFiles();
              
              if((files == null) || (files.size() < 1))
              {
                request.setAttribute("msg", "PLEASE SELECT MINUTES FILE TO UPLOAD");
              }
              else
              {
                file = (UploadFile) files.get("filedata");
                
                if((file.getFileName().indexOf(".pdf") < 0) && (file.getFileName().indexOf(".PDF") < 0))
                {
                  request.setAttribute("msg", "ONLY PDF FILES ARE ACCEPTED");
                }
                else
                {
                  try
                  { 
                    rpt = new DirectorReportBean();
                    rpt.setReportDate((new SimpleDateFormat("dd/MM/yyyy")).parse("01/" + mrequest.getParameter("month") + "/" + mrequest.getParameter("year")));
                    rpt.setReportTitle(mrequest.getParameter("title"));
                    
                    DirectorReportManager.addDirectorReportBean(rpt);
                    
                    File dir = new File(session.getServletContext().getRealPath("/") + "../../director/ROOT/director_reports/");
                    if(!dir.exists())
                      dir.mkdirs();
                      
                    bean = new UploadBean();
                    bean.setFolderstore(session.getServletContext().getRealPath("/") + "../../director/ROOT/director_reports/");
                    bean.store(mrequest, "filedata");
                    f = new File(session.getServletContext().getRealPath("/") + "../../director/ROOT/director_reports/" + file.getFileName());
                    f.renameTo(new File(session.getServletContext().getRealPath("/") + "../../director/ROOT/director_reports/"
                      + sdf_file.format(rpt.getReportDate()) + ".pdf"));
                      
                    request.setAttribute("msg", "REPORT ADD SUCCESSFULLY");
                  }
                  catch(CeoWebException e)
                  {
                    switch(((SQLException)e.getCause()).getErrorCode())
                    {
                      case 1:
                        request.setAttribute("msg", "A REPORT FOR " + sdf_full.format(rpt.getReportDate()) + " ALREADY EXISTS");
                        break;
                      default:
                        request.setAttribute("msg", e.getMessage());
                    }
                  }
                }
              }              
            }
          }
          else
            request.setAttribute("msg","INVALID OPTION");
        }
      }
      request.setAttribute("DIRECTOR-REPORTS", DirectorReportManager.getDirectorReportBeans());
    }
    catch(Exception e)
    {
      request.setAttribute("msg", e.getMessage());
    }
   
    path = "director_reports.jsp";
    
    return path;
  }
}