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

public class EditTemplateRequestHandler   implements RequestHandler
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
          String id = request.getParameter("id");
          String t_name = request.getParameter("template_name");
          String contents = request.getParameter("contents");

          if(StringUtils.isEmpty(id))
          {
            request.setAttribute("msg", "Template ID is required for editing.");
            request.setAttribute("TEMPLATES", TemplateManager.getTemplateBeans());
            path = "list_templates.jsp";
          }
          else if(StringUtils.isEmpty(t_name))
          {
            request.setAttribute("msg", "Please enter TEMPLATE NAME.");
            
            TemplateBean bean = TemplateManager.getTemplateBean(Integer.parseInt(id));
            bean.setText(FileUtils.getFileContents(new File(session.getServletContext().getRealPath("/") + TemplateConstant.TEMPLATE_DIRECTORY + bean.getFilename())));
            request.setAttribute("TEMPLATE", bean);
            
            path = "edit_template.jsp";
          }
          else if(StringUtils.isEmpty(contents))
          {
            request.setAttribute("msg", "Please enter template content.");
            
            TemplateBean bean = TemplateManager.getTemplateBean(Integer.parseInt(id));
            bean.setText(FileUtils.getFileContents(new File(session.getServletContext().getRealPath("/") + TemplateConstant.TEMPLATE_DIRECTORY + bean.getFilename())));
            request.setAttribute("TEMPLATE", bean);
            
            path = "edit_template.jsp";
          }
          else
          {
              TemplateBean bean = TemplateManager.getTemplateBean(Integer.parseInt(id));
              
              bean.setName(t_name);
      
              FileUtils.writeFileContents(new File(session.getServletContext().getRealPath("/") + TemplateConstant.TEMPLATE_DIRECTORY + bean.getFilename()), contents);
              
              TemplateManager.updateTemplateBean(bean);
              
              request.setAttribute("msg", "Template UPDATED successfully.");
                
              bean = TemplateManager.getTemplateBean(Integer.parseInt(id));
              bean.setText(FileUtils.getFileContentsHTML(new File(session.getServletContext().getRealPath("/") + TemplateConstant.TEMPLATE_DIRECTORY + bean.getFilename())));
              request.setAttribute("TEMPLATE", bean);
            
              path = "view_template.jsp";
          }          
        }
        else
        {
          String id = request.getParameter("id");
          if(StringUtils.isEmpty(id))
          {
            request.setAttribute("msg", "Template ID is required for editing.");
            request.setAttribute("TEMPLATES", TemplateManager.getTemplateBeans());
            path = "list_templates.jsp";
          }
          else
          {
            TemplateBean bean = TemplateManager.getTemplateBean(Integer.parseInt(id));
            bean.setText(FileUtils.getFileContents(new File(session.getServletContext().getRealPath("/") + TemplateConstant.TEMPLATE_DIRECTORY + bean.getFilename())));
            request.setAttribute("TEMPLATE", bean);
            path = "edit_template.jsp";
          }
        }
    }
    catch(Exception e)
    {
      e.printStackTrace();
      request.setAttribute("msg", e.getMessage());
      path = "index.jsp";
    }
    
    return path;
  }
}