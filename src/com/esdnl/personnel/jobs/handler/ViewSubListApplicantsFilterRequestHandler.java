package com.esdnl.personnel.jobs.handler;
import com.awsd.servlet.*;
import com.esdnl.util.*;
import com.esdnl.personnel.jobs.bean.*;
import com.esdnl.personnel.jobs.dao.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class ViewSubListApplicantsFilterRequestHandler implements LoginNotRequiredRequestHandler
{
	  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException
	  {
	    String path;
	    SubListBean list = null;
	    
	    
	    try
	    {
	      String list_id = request.getParameter("list_id");
	      
	      if(StringUtils.isEmpty(list_id))
	      {
	        request.setAttribute("msg", "List id required to view applicants.");
	        path = "admin_view_sub_lists.jsp";
	      }
	      else
	      {
	    	
	        list = SubListManager.getSubListBean(Integer.parseInt(list_id));
	        if(list.getRegion() != null){
	        	request.setAttribute("rname", list.getRegion().getName());
	        	if(list.getRegion().getZone() != null){
	        		request.setAttribute("zname", list.getRegion().getZone().getZoneName());
	        	}else{
	        		request.setAttribute("zname", "");
	        	}
	        }else{
	        	request.setAttribute("zname", "");
	        	request.setAttribute("rname", "");
	        }
	        request.setAttribute("list", list);
	        path = "admin_filter_sublist_applicants.jsp";
	      }
	      
	    }
	    catch(JobOpportunityException e)
	    {
	      e.printStackTrace();
	      request.setAttribute("msg", "Could not retrieve Job applicants.");
	      path = "admin_view_sub_lists.jsp";
	    }
	    
	    return path;
	  }
	}
