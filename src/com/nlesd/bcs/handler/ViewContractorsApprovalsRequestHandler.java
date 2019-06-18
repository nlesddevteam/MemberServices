package com.nlesd.bcs.handler;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.awsd.security.User;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.constants.StatusConstant;
import com.nlesd.bcs.dao.BussingContractorManager;
public class ViewContractorsApprovalsRequestHandler extends RequestHandlerImpl
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
	    //now we get the status type
	    String status = request.getParameter("status");
	    String reporttitle="";
	    ArrayList<BussingContractorBean> contractors = new ArrayList<BussingContractorBean>();
	    if(status.equals("p")){
	    	contractors=BussingContractorManager.getContractorsByStatus(StatusConstant.SUBMITTED.getValue());
	    	reporttitle="Contractors Awaiting Approval";
	    }else if(status.equals("a")){
	    	contractors=BussingContractorManager.getContractorsByStatus(StatusConstant.APPROVED.getValue());
	    	reporttitle="Contactors Approved";
	    }else if(status.equals("r")){
	    	contractors=BussingContractorManager.getContractorsByStatus(StatusConstant.REJECTED.getValue());
	    	reporttitle="Contractors Rejected";
	    }else if(status.equals("s")){
	    	contractors=BussingContractorManager.getContractorsByStatus(StatusConstant.SUSPENDED.getValue());
	    	reporttitle="Contractors Suspended";
	    }else if(status.equals("re")){
	    	contractors=BussingContractorManager.getContractorsByStatus(StatusConstant.REMOVED.getValue());
	    	reporttitle="Contractors Removed";
	    }
	    request.setAttribute("contractors", contractors);
	    request.setAttribute("reporttitle", reporttitle);
	    path = "view_contractors_approvals.jsp";
	    return path;
	  }
}