package com.nlesd.bcs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.bean.BussingContractorEmployeeBean;
import com.nlesd.bcs.dao.BussingContractorEmployeeManager;
import com.nlesd.bcs.dao.BussingContractorManager;
public class ApproveRejectEmployeeRequestHandler implements RequestHandler
{
	  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException
	  {
	    HttpSession session = null;
	    User usr = null;
	    String path = "";
	    Integer cid = Integer.parseInt(request.getParameter("cid"));
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
	    BussingContractorEmployeeBean vbean = new BussingContractorEmployeeBean();
  	  	vbean = BussingContractorEmployeeManager.getBussingContractorEmployeeById(cid);
  	  	request.setAttribute("employee", vbean);
  	  	request.setAttribute("spath",request.getContextPath());
  	  	request.setAttribute("dpath","/BCS/documents/employeedocs/");
	    BussingContractorBean contractor = BussingContractorManager.getBussingContractorById(vbean.getContractorId());
	    request.setAttribute("contractor", contractor);
	    path = "approve_employee.jsp";
	    return path;
	  }
}