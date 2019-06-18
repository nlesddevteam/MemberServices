package com.nlesd.bcs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.awsd.security.User;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.bean.BussingContractorSystemCustomReportBean;
import com.nlesd.bcs.dao.BuildCustomReportManager;
import com.nlesd.bcs.dao.BussingContractorSystemCustomReportManager;
public class RunSavedCustomReportRequestHandler extends RequestHandlerImpl
    {
    	  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    	    throws ServletException, IOException
    	  {
    	    HttpSession session = null;
    	    User usr = null;
    	    String path = "";
    	    
    	    session = request.getSession(false);
    	    if((session != null) && (session.getAttribute("usr") != null))
    	    {
    	      usr = (User) session.getAttribute("usr");
    	      if(!(usr.getUserPermissions().containsKey("BCS-SYSTEM-ACCESS")))
    	      {
    	        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
    	      }
    	    }
    	    else
    	    {
    	      throw new SecurityException("User login required.");
    	    }
    	    int reportid = Integer.parseInt(request.getParameter("rid").toString());
    	    BussingContractorSystemCustomReportBean rbean = BussingContractorSystemCustomReportManager.getCustomReportsById(reportid);
    	    request.setAttribute("tablebody", BuildCustomReportManager.runReportSql(rbean.getReportSql()));
    	    request.setAttribute("tableheader",BuildCustomReportManager.buildSelectClauseSave(rbean.getReportTableFields(),rbean.getReportTables()));
    	    path = "admin_view_report.jsp";
    	    return path;
    	  }
    }