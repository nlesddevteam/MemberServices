package com.esdnl.webmaint.ceoweb.handler;

import com.awsd.security.*;
import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import com.esdnl.webmaint.ceoweb.bean.*;
import com.esdnl.webmaint.ceoweb.dao.*;
import com.esdnl.webmaint.ceoweb.constants.*;

import java.io.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import javazoom.upload.*;

public class ViewMessagesRequestHandler  implements RequestHandler
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
    
    if(request.getParameter("type") != null)
    {
      if(Integer.parseInt(request.getParameter("type")) == MessageTypeConstant.MSG_COMMUNITY.getTypeID())
      {
          request.setAttribute("MESSAGES", MessageManager.getMessageBeans(MessageTypeConstant.MSG_COMMUNITY.getTypeID(), false));
          request.setAttribute("ARCHIVED_MESSAGES", MessageManager.getMessageBeans(MessageTypeConstant.MSG_COMMUNITY.getTypeID(), true));
          request.setAttribute("VIEW_TYPE", new Integer(MessageTypeConstant.MSG_COMMUNITY.getTypeID()));
      }
      else if(Integer.parseInt(request.getParameter("type")) == MessageTypeConstant.MSG_STAFF.getTypeID())
      {
          request.setAttribute("MESSAGES", MessageManager.getMessageBeans(MessageTypeConstant.MSG_STAFF.getTypeID(), false));
          request.setAttribute("ARCHIVED_MESSAGES", MessageManager.getMessageBeans(MessageTypeConstant.MSG_STAFF.getTypeID(), true));
          request.setAttribute("VIEW_TYPE", new Integer(MessageTypeConstant.MSG_STAFF.getTypeID()));
      }
    }
    
    path = "messages.jsp";
    
    return path;
  }
}