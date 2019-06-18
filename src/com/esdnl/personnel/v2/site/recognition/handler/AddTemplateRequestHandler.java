package com.esdnl.personnel.v2.site.recognition.handler;

import com.awsd.security.*;
import com.awsd.security.SecurityException;
import com.awsd.servlet.*;
import com.esdnl.util.*;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.esdnl.personnel.v2.model.recognition.bean.*;
import com.esdnl.personnel.v2.model.recognition.constant.*;
import com.esdnl.personnel.v2.database.recognition.*;

import javazoom.upload.*;

public class AddTemplateRequestHandler  implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String path;
    HttpSession session = null;
    User usr = null;
    UploadBean bean = null;
    MultipartFormDataRequest mrequest = null;
    UploadFile file = null;
    Hashtable files = null;
    File f = null;
    
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
      if(MultipartFormDataRequest.isMultipartFormData(request))
      {
        mrequest = new MultipartFormDataRequest(request);
        
        String op = mrequest.getParameter("op"); 
        if(!StringUtils.isEmpty(op))
        {
          String t_name = mrequest.getParameter("template_name");
          files = mrequest.getFiles();
          
          
          if(StringUtils.isEmpty(t_name))
          {
            request.setAttribute("msg", "Please enter TEMPLATE NAME.");
          }
          else if((files == null) || (files.size() < 1))
          {
            request.setAttribute("msg", "Please select the TEMPLATE FILE.");
          }
          else
          {
              TemplateBean rbean = new TemplateBean();
              
              rbean.setName(t_name);
              rbean.setFilename(Long.toString(Calendar.getInstance().getTimeInMillis())+ TemplateConstant.TEMPLATE_EXTENSION);
              
              file = (UploadFile) files.get("template_file");
              File dir = new File(session.getServletContext().getRealPath("/") + TemplateConstant.TEMPLATE_DIRECTORY);
              if(!dir.exists())
                dir.mkdirs();
                        
              bean = new UploadBean();
              bean.setFolderstore(session.getServletContext().getRealPath("/") + TemplateConstant.TEMPLATE_DIRECTORY);
              bean.store(mrequest, "template_file");
              
              f = new File(session.getServletContext().getRealPath("/") + TemplateConstant.TEMPLATE_DIRECTORY + file.getFileName());
              File f_tmp = new File(session.getServletContext().getRealPath("/") + TemplateConstant.TEMPLATE_DIRECTORY + rbean.getFilename());
              f.renameTo(f_tmp);
              
              TemplateManager.addTemplateBean(rbean);
              
              request.setAttribute("msg", "Template added successfully.");
          }
        }
      }
      else
        request.setAttribute("msg", "NOT MULTIPART FORM");
      
      request.setAttribute("TEMPLATES", TemplateManager.getTemplateBeans());
      path = "list_templates.jsp";
    }
    catch(Exception e)
    {
      e.printStackTrace();
      request.setAttribute("msg", e.getMessage());
      path = "list_templates.jsp";
    }
    
    return path;
  }
}