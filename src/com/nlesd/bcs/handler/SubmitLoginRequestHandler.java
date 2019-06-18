package com.nlesd.bcs.handler;
import com.awsd.servlet.*;
import com.esdnl.util.*;
import com.nlesd.bcs.bean.AuditTrailBean;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;
import com.nlesd.bcs.constants.StatusConstant;
import com.nlesd.bcs.dao.AuditTrailManager;
import com.nlesd.bcs.dao.BussingContractorManager;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.*;
import javax.servlet.http.*;
public class SubmitLoginRequestHandler implements LoginNotRequiredRequestHandler
{
	  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException
	  {
	    String path;
	    BussingContractorBean profile = null;
	        
	    try
	    {
	      String email = request.getParameter("username");
	      String password = request.getParameter("password");
	      profile = BussingContractorManager.getBussingContractorByEmail(email);
	      if(StringUtils.isEmpty(email))
	      {
	        request.setAttribute("msg", "Please specify your email address.");
	        path = "login.jsp";
	      }
	      else if(StringUtils.isEmpty(password))
	      {
	        request.setAttribute("msg", "Please specify your password.");
	        path = "login.jsp";
	      }
	      else
	      {
	        
	        if((profile.getEmail() == null))
	        {
	          request.setAttribute("msg", "email address and/or password are incorrect.");
	          path = "login.jsp";
	        }else if((profile.getSecBean() == null)){
	        	request.setAttribute("msg", "email address and/or password are incorrect.");
		          path = "login.jsp";
	        }else if(!profile.getSecBean().getPassword().equals(password)){
	        	request.setAttribute("msg", "email address and/or password are incorrect.");
		          path = "login.jsp";
	        }
	        else
	        {
	        	if(profile.getStatus() ==  StatusConstant.APPROVED.getValue()){
	        		request.setAttribute("AUTHENTICATED", new Boolean(true));
		        	request.getSession(true).setAttribute("CONTRACTOR", profile);
		        	//update the audit trail
		        	AuditTrailBean atbean = new AuditTrailBean();
					atbean.setEntryType(EntryTypeConstant.CONTRACTORLOGIN);
					atbean.setEntryId(profile.getSecBean().getId());
					atbean.setEntryTable(EntryTableConstant.CONTRACTORSECURITY);
					DateFormat dateTimeInstance = SimpleDateFormat.getDateTimeInstance();
					atbean.setEntryNotes("Contractor Logged in on " + dateTimeInstance.format(Calendar.getInstance().getTime()));
					atbean.setContractorId(profile.getId());
					AuditTrailManager.addAuditTrail(atbean);
					path = "contractorMain.html";
	        	}else{
	  	          request.setAttribute("msg", "User account has been suspended.");
		          path = "login.jsp";
	        	}
	        	
	        }
	      }
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	      request.setAttribute("msg", "Could find contractor record on file, please try again.");
	      path = "login.jsp";
	    }
	  
	    return path;
	  }
	}
