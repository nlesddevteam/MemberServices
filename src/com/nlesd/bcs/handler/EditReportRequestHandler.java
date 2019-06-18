package com.nlesd.bcs.handler;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.awsd.security.User;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.bean.BussingContractorSystemCustomReportBean;
import com.nlesd.bcs.bean.BussingContractorSystemReportTableFieldBean;
import com.nlesd.bcs.dao.BussingContractorSystemCustomReportConditionsManager;
import com.nlesd.bcs.dao.BussingContractorSystemCustomReportManager;
import com.nlesd.bcs.dao.BussingContractorSystemReportTableFieldManager;
import com.nlesd.bcs.dao.BussingContractorSystemReportTableManager;
public class EditReportRequestHandler extends RequestHandlerImpl
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
	    int rid = Integer.parseInt(request.getParameter("rid"));
	    BussingContractorSystemCustomReportBean rbean =BussingContractorSystemCustomReportManager.getCustomReportsById(rid);
	    request.setAttribute("report", rbean);
	    request.setAttribute("table", BussingContractorSystemReportTableManager.getReportTableById(Integer.parseInt(rbean.getReportTables().replace("[","").replace("]", ""))));
	    //now we need to pass the fields that are selected and not selected
	    ArrayList<BussingContractorSystemReportTableFieldBean> selected = new ArrayList<BussingContractorSystemReportTableFieldBean>();
	    ArrayList<BussingContractorSystemReportTableFieldBean> notselected = new ArrayList<BussingContractorSystemReportTableFieldBean>();
	    ArrayList<BussingContractorSystemReportTableFieldBean> allfields = BussingContractorSystemReportTableFieldManager.getReportTableFields(Integer.parseInt(rbean.getReportTables().replace("[","").replace("]", "")));
	    String[] selectedids = rbean.getReportTableFields().replace("[","").replace("]", "").split(",");
	    for(BussingContractorSystemReportTableFieldBean fbean: allfields){
	    	boolean isfound=false;
	    	for(String s : selectedids){
	    		if(fbean.getId() == Integer.parseInt(s.trim())){
	    			isfound=true;
	    		}
	    	}
	    	if(isfound==true){
	    		selected.add(fbean);
	    	}else{
	    		notselected.add(fbean);
	    	}
	    }
	    request.setAttribute("selected", selected);
	    request.setAttribute("notselected", notselected);
	    request.setAttribute("conditions",BussingContractorSystemCustomReportConditionsManager.getCustomReportConditionsById(rid));
	    request.setAttribute("allfields", allfields);
	    path = "edit_report.jsp";
	    return path;
	  }
}
