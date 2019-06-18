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


public class ProcessRequestRequestHandler implements RequestHandler
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
      String letter = request.getParameter("letter");
      String template_id = request.getParameter("template_id");
      
      if(!StringUtils.isEmpty(op))
      {
        RecognitionRequestBean req = (RecognitionRequestBean) session.getAttribute("RECOGNITION_REQUEST");
        
        if(op.equals("RETRIEVE_TEMPLATE"))
        {
          if(!StringUtils.isEmpty(template_id))
          {
            TemplateBean template = TemplateManager.getTemplateBean(Integer.parseInt(template_id));
            template.setText(FileUtils.getFileContents(new File(session.getServletContext().getRealPath("/") + TemplateConstant.TEMPLATE_DIRECTORY + template.getFilename())));
            session.setAttribute("TEMPLATE", template);
          }
          else
            request.setAttribute("msg", "Please select template.");
          
          path = "process_request.jsp";
        }
        else if(op.equals("PREVIEW_LETTERS"))
        {
          if(StringUtils.isEmpty(letter))
          {
            request.setAttribute("msg", "Please enter letter content.");
            path = "process_request.jsp";
          }
          else
          {
            req.setTemplateDocument(letter);
            session.setAttribute("RECOGNITION_REQUEST", req);
            
            path = "preview_letter.jsp";
          }
        }
        else if(op.equals("PROCESS_LETTERS"))
        {
          RecognitionRequestManager.processRecognitionRequestBean(req, usr.getPersonnel());
                  
          path = "print_letters.jsp";
        }
        else
        {
          request.setAttribute("RECOGNITION_REQUEST", req);
          path = "view_request.jsp";
        }
      }
      else
        path = "index.jsp";
    }
    catch(Exception e)
    {
      request.setAttribute("msg", e.getMessage());
      path = "index.jsp";
    }
    
    return path;
  }
}