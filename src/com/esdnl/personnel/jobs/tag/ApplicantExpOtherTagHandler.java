package com.esdnl.personnel.jobs.tag;

import java.io.*;

import java.text.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import com.awsd.school.SchoolDB;
import com.esdnl.personnel.jobs.bean.*;
import com.esdnl.personnel.jobs.dao.*;
import com.esdnl.personnel.jobs.constants.*;
import com.esdnl.util.*;


public class ApplicantExpOtherTagHandler extends TagSupport
{
  public int doStartTag() throws JspException
  {
    SimpleDateFormat sdf = null;
    JspWriter out = null;
    
    ApplicantProfileBean profile = null;
    ApplicantExperienceOtherBean[] beans = null;
    
    try
    {    
      out = pageContext.getOut();
      
      profile = (ApplicantProfileBean) pageContext.getAttribute("APPLICANT", pageContext.SESSION_SCOPE);
      
      sdf = new SimpleDateFormat("MMMM yyyy");
      
      if(profile != null)
        beans = ApplicantExpOtherManager.getApplicantExperienceOtherBeans(profile.getSIN());

      
      out.println("<div style='font-size:16px;color:DimGrey;font-weight:bold;padding-top:5px;'>Experience with Other Boards</div>");
     
      
      if((beans != null) && (beans.length > 0))
      {
    	  
    	  out.println("<table class='table table-striped table-condensed' style='font-size:11px;'>");
  		out.println("<thead><tr>");
  		out.println("<th width='10%'>From</th>");
  		out.println("<th width='10%'>To</th>");
  		out.println("<th width='20%'>School &amp; Board</th>");
  		out.println("<th width='50%'>Grades and/or Subjects Taught</th>");
  		out.println("<th width='10%'>Options</th>");
  		out.println("</tr></thead>");				
  		out.println("<tbody>");
    	  
    	  
    	  
    	  
    	  
        for(int i=0; i < beans.length; i ++)
        {
        	out.println("<TR>");
			out.println("<TD>" + sdf.format(beans[i].getFrom()) + "</TD>");
			out.println("<TD>" + sdf.format(beans[i].getTo()) + "</TD>");
			out.println("<TD>" + beans[i].getSchoolAndBoard() + "</TD>");
			out.println("<TD>" + beans[i].getGradesSubjects() + "</TD>");
			out.println("<TD><a class='btn btn-xs btn-danger' href='applicantRegistration.html?step=3&del=" + beans[i].getId() + "'>DEL</a></td>");
			out.println("</TR>");
		}
		out.println("</tbody></table>");		
        
      }
      else
      {
    	  out.println("No Experience with other Boards currently on file.");
      }     
    }
    catch(JobOpportunityException e)
    {
      e.printStackTrace();
      throw new JspException(e.getMessage());
    }
    catch(IOException e)
    {
      e.printStackTrace();
      throw new JspException(e.getMessage());
    }

    return SKIP_BODY;
  }
}