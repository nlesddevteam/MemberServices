package com.awsd.admin.apps.financial.handler;

import com.awsd.financial.*;
import com.awsd.personnel.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.security.SecurityException;
import com.awsd.servlet.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class FinancialAdminstrationRequestHandler  implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {  
    HttpSession session = null;
    User usr = null;
    Report rpt = null;
    Account acc = null;
    String op;
    String display_tab = "ACCOUNT";
    
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("MEMBERADMIN-VIEW")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    else
    {
      throw new SecurityException("User login required.");
    }

    System.err.println(request.getParameter("choice"));
    
    if(request.getParameter("choice").equalsIgnoreCase("CREATE NEW"))
    {
      if(request.getParameter("rpt_id") != null)
      {
        rpt = ReportDB.getReport(Integer.parseInt(request.getParameter("rpt_id")));
      }
      else if(request.getParameter("newReportName") != null)
      {
        rpt = new Report(ReportDB.addReport(request.getParameter("newReportName")), request.getParameter("newReportName"));
      }
      else
      {
        throw new FinancialException("Report ID requried.");
      }
    
      op = request.getParameter("op");
      if(op != null)
      {
        if(op.equals("ADD REPORT ACCOUNT"))
        { 
          rpt.getReportAccounts().add(AccountDB.getAccounts(request.getParameterValues("availableAccounts")));
        }
        else if(op.equals("DEL REPORT ACCOUNT"))
        {
          rpt.getReportAccounts().delete(AccountDB.getAccounts(request.getParameterValues("selectedAccounts")));
        }
        else if(op.equals("ADD REPORT USER"))
        {
          rpt.getReportPersonnel().add(PersonnelDB.getPersonnel(request.getParameterValues("availableUsers")));
          display_tab = "USER";
        }
        else if(op.equals("DEL REPORT USER"))
        {
          rpt.getReportPersonnel().delete(PersonnelDB.getPersonnel(request.getParameterValues("selectedUsers")));
          display_tab = "USER";
        }
      }
    }
    else if(request.getParameter("choice").equalsIgnoreCase("OPEN EXISTING"))
    {
      if(request.getParameter("openExistingReport") != null)
      {
        rpt = ReportDB.getReport(Integer.parseInt(request.getParameter("openExistingReport")));
      }
      else
      {
        throw new FinancialException("Report ID requried.");
      }

      op = request.getParameter("op");
      if(op != null)
      {
        if(op.equals("DEL REPORT ACCOUNT"))
        {
          rpt.getReportAccounts().delete(AccountDB.getAccount(request.getParameter("id")));
        }
        else if(op.equals("DEL REPORT USER"))
        {
          rpt.getReportPersonnel().delete(PersonnelDB.getPersonnel(Integer.parseInt(request.getParameter("id"))));
        }
        else if(op.equals("DEL REPORT"))
        {
          ReportDB.deleteReport(rpt);
          rpt = null;
        }
        display_tab = "";
      }
    }

    if(rpt != null)
      request.setAttribute("rpt", rpt);
      
    request.setAttribute("choice", request.getParameter("choice")+":"+display_tab);

    return "financialReports.jsp";
  }
}