package com.esdnl.webmaint.ceoweb.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import com.esdnl.util.*;
import com.esdnl.webmaint.ceoweb.bean.*;
import com.esdnl.webmaint.ceoweb.dao.*;

import java.io.*;

import java.sql.*;

import java.text.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import javazoom.upload.*;

public class AddSchoolVisitRequestHandler  implements RequestHandler
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
    SimpleDateFormat sdf_cal = new SimpleDateFormat("dd/MM/yyyy");
    SchoolVisitBean visit = null;
    
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
            if(mrequest.getParameter("visit_date") == null)
            {
              request.setAttribute("msg", "DATE OF VISIT IS REQUIRED");
            }
            else if(mrequest.getParameter("caption") == null)
            {
              request.setAttribute("msg", "CAPTION IS REQUIRED");
            }
            
            else
            {
              files = mrequest.getFiles();
              
              if((files == null) || (files.size() < 1))
              {
                request.setAttribute("msg", "PLEASE SELECT IMAGE FILE TO UPLOAD");
              }
              else
              {
                file = (UploadFile) files.get("filedata");
                
                  try
                  { 
                    visit = new SchoolVisitBean();
                    visit.setImageFileName(FileUtils.generateRandomFilename(session.getServletContext().getRealPath("/") + "../../director/ROOT/director_reports/",
                        "img", FileUtils.extractExtension(file.getFileName())));
                    visit.setVisitDate(sdf_cal.parse(mrequest.getParameter("visit_date")));
                    visit.setCaption(mrequest.getParameter("caption"));
                    
                    SchoolVisitManager.addSchoolVisitBean(visit);
                    
                    File dir = new File(session.getServletContext().getRealPath("/") + "../../director/ROOT/school_visits/");
                    if(!dir.exists())
                      dir.mkdirs();
                      
                    bean = new UploadBean();
                    bean.setFolderstore(session.getServletContext().getRealPath("/") + "../../director/ROOT/school_visits/");
                    bean.store(mrequest, "filedata");
                    f = new File(session.getServletContext().getRealPath("/") + "../../director/ROOT/school_visits/" + file.getFileName());
                    f.renameTo(new File(session.getServletContext().getRealPath("/") + "../../director/ROOT/school_visits/"
                      + visit.getImageFileName()));
                      
                    request.setAttribute("msg", "SCHOOL VISIT ADD SUCCESSFULLY");
                  }
                  catch(CeoWebException e)
                  {
                    switch(((SQLException)e.getCause()).getErrorCode())
                    {
                      case 1:
                        request.setAttribute("msg", "A VISIT ALREADY EXISTS");
                        break;
                      default:
                        request.setAttribute("msg", e.getMessage());
                    }
                  }
              }              
            }
          }
          else
            request.setAttribute("msg","INVALID OPTION");
        }
      }
      request.setAttribute("SCHOOL_VISITS", SchoolVisitManager.getSchoolVisitBeans(false));
      request.setAttribute("ARCHIVED_SCHOOL_VISITS", SchoolVisitManager.getSchoolVisitBeans(true));
    }
    catch(Exception e)
    {
      request.setAttribute("msg", e.getMessage());
    }
   
    path = "school_visits.jsp";
    
    return path;
  }
}