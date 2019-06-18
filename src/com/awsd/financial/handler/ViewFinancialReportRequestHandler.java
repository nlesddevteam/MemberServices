package com.awsd.financial.handler;

import com.awsd.financial.*;
import com.awsd.personnel.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class ViewFinancialReportRequestHandler  implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    String path = "";
    Personnel p = null;
    Report rpt = null;
    

    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("FINANCIAL-VIEW")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
      else
      {
        if (request.getParameter("rpt") != null)
        {
          rpt = ReportDB.getReport(Integer.parseInt(request.getParameter("rpt")));

          if(!usr.getPersonnel().validateReportRequest(rpt))
          {
            throw new SecurityException("Illegal Financial Access [" + usr.getLotusUserFullName() + "] on report " + rpt.getReportName());
          }
        }
      }
    }
    else
    {
      throw new SecurityException("User login required.");
    }

    request.setAttribute("Report", rpt);
    path = "viewReport.jsp";
    
    return path;
  }
}