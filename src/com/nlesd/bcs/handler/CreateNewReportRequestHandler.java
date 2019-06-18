package com.nlesd.bcs.handler;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.awsd.security.User;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.bean.BussingContractorSystemReportTableBean;
import com.nlesd.bcs.dao.BussingContractorSystemReportTableManager;
public class CreateNewReportRequestHandler extends RequestHandlerImpl
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
	    ArrayList<BussingContractorSystemReportTableBean> list = BussingContractorSystemReportTableManager.getReportTables();
	    //now we remove the contractor table if regional admin
	    if(usr.checkPermission("BCS-VIEW-WESTERN") || usr.checkPermission("BCS-VIEW-CENTRAL") || usr.checkPermission("BCS-VIEW-LABRADOR")){
	    	BussingContractorSystemReportTableBean fbean = BussingContractorSystemReportTableManager.getReportTableById(1);
	    	list.remove(fbean);
	    	fbean = BussingContractorSystemReportTableManager.getReportTableById(5);
	    	list.remove(fbean);
	    	fbean = BussingContractorSystemReportTableManager.getReportTableById(6);
	    	list.remove(fbean);
	    }
	    request.setAttribute("tables",list );
	    path = "admin_create_new_report.jsp";
	    return path;
	  }
}
