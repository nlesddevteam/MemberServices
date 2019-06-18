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

public class AddMessageRequestHandler implements RequestHandler
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
    MessageBean msg = null;
    
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
      
        //System.err.println("MULTIPART FORM...");
        
        mrequest = new MultipartFormDataRequest(request);
        
        if(mrequest.getParameter("op") != null)
        {
          if(mrequest.getParameter("op").equals("CONFIRM"))
          {
            if((mrequest.getParameter("msg_type") == null) || (Integer.parseInt(mrequest.getParameter("msg_type")) == -1))
            {
              request.setAttribute("msg", "MESSAGE TYPE IS REQUIRED");
            }
            else if(mrequest.getParameter("msg_date") == null)
            {
              request.setAttribute("msg", "DATE OF MESSAGE IS REQUIRED");
            }
            else if(mrequest.getParameter("msg_title") == null)
            {
              request.setAttribute("msg", "MESSAGE TITLE IS REQUIRED");
            }
            else if(mrequest.getParameter("msg_txt") == null)
            {
              request.setAttribute("msg", "MESSAGE TEXT IS REQUIRED");
            }
            else
            {
            
              //System.err.println("VALID DATA...");
              
              files = mrequest.getFiles();
  
                  try
                  {
                    msg = new MessageBean(); 
                    
                    if((files != null) && (files.size() > 0))
                    {
                      //System.err.println("FILES: " + files.size());
                      
                      file = (UploadFile) files.get("filedata");
                      
                      if((file != null) && (file.getFileSize() > 0))
                      {
                        msg.setMessageImage(FileUtils.generateRandomFilename(session.getServletContext().getRealPath("/") + "../../director/ROOT/message_images/",
                          "img", FileUtils.extractExtension(file.getFileName())));
                          
                        File dir = new File(session.getServletContext().getRealPath("/") + "../../director/ROOT/message_images/");
                        if(!dir.exists())
                          dir.mkdirs();
                          
                        bean = new UploadBean();
                        bean.setFolderstore(session.getServletContext().getRealPath("/") + "../../director/ROOT/message_images/");
                        bean.store(mrequest, "filedata");
                        f = new File(session.getServletContext().getRealPath("/") + "../../director/ROOT/message_images/" + file.getFileName());
                        f.renameTo(new File(session.getServletContext().getRealPath("/") + "../../director/ROOT/message_images/"
                          + msg.getMessageImage()));
                      }
                    }
                    
                    //System.err.println("TYPE: " + Integer.parseInt(mrequest.getParameter("msg_type")));
                    
                    msg.setMessageType(Integer.parseInt(mrequest.getParameter("msg_type")));
                    msg.setMessageDate(sdf_cal.parse(mrequest.getParameter("msg_date")));
                    msg.setMessageTitle(mrequest.getParameter("msg_title"));
                    msg.setMessage(mrequest.getParameter("msg_txt"));
                    
                    MessageManager.addMessageBean(msg);
                    
                    //System.err.println("MESSAGE ADDED...");
                      
                    request.setAttribute("msg", "MESSAGE ADD SUCCESSFULLY");
                  }
                  catch(CeoWebException e)
                  {
                    switch(((SQLException)e.getCause()).getErrorCode())
                    {
                      case 1:
                        request.setAttribute("msg", "A MESSAGE ALREADY EXISTS");
                        break;
                      default:
                        request.setAttribute("msg", e.getMessage());
                    }
                  }
            }
          }
          else
            request.setAttribute("msg","INVALID OPTION");
        }
      }
      
      //System.err.println("REFRESHING DATA...");
      
      request.setAttribute("MESSAGES", MessageManager.getMessageBeans(Integer.parseInt(mrequest.getParameter("msg_type")), false));
      request.setAttribute("ARCHIVED_MESSAGES",  MessageManager.getMessageBeans(Integer.parseInt(mrequest.getParameter("msg_type")), true));
      request.setAttribute("VIEW_TYPE", new Integer(Integer.parseInt(mrequest.getParameter("msg_type"))));
    }
    catch(Exception e)
    {
      e.printStackTrace();
      request.setAttribute("msg", e.getMessage());
    }
   
    path = "messages.jsp";
    
    return path;
  }
}