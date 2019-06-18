package com.esdnl.mrs.handler;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import com.esdnl.mrs.*;

import java.io.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class AddVendorRequestHandler  implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    String path = "";
    String username = "";
    String hash = "";
    Vector vendors = null;
    
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("MAINTENANCE-ADMIN-VIEW")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    
    if(request.getParameter("op") != null)
    {
      if(request.getParameter("op").equalsIgnoreCase("ADD"))
      {
        if(request.getParameter("vendor_id") != null)
        {
          VendorDB.addVendor(new Vendor(request.getParameter("vendor_id")));
          request.setAttribute("msg", "VENDOR added successfully.");
        }
        else
        {
          request.setAttribute("msg", "VENDOR NAME is required.");
        }
        
        request.setAttribute("VENDORS", VendorDB.getVendors());
        path = "add_vendor.jsp";
      }
      else if(request.getParameter("op").equalsIgnoreCase("DEL"))
      {
        if(VendorDB.deleteVendor(Integer.parseInt(request.getParameter("ven_id"))))
        {
          request.setAttribute("msg", "VENDOR deleted successfully.");
        }
        else
        {
          request.setAttribute("msg", "VENDOR not deleted!");
        }
        request.setAttribute("VENDORS", VendorDB.getVendors());
        path = "add_vendor.jsp";
      }
      else
      {
        path = "error_message.jsp";
        request.setAttribute("msg", "INVALID OPTION");
      }
    }
    else
    {
      request.setAttribute("VENDORS", VendorDB.getVendors());
      path = "add_vendor.jsp";
    }
    
    return path;
  }
}