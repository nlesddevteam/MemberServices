package com.esdnl.personnel.jobs.tag;

import java.io.*;

import java.text.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import com.awsd.school.Subject;
import com.esdnl.personnel.jobs.bean.*;
import com.esdnl.personnel.jobs.dao.*;
import com.esdnl.util.*;

public class DegreeTagHandler  extends TagSupport
{
  private String id;
  private String cls;
  private String style;
  private String multiple;
  private Object value;
  private String listtype;
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public void setListtype(String listtype)
  {
    this.listtype = listtype;
  }
  
  public void setCls(String cls)
  {
    this.cls = cls;
  }
  
  public void setStyle(String style)
  {
    this.style = style;
  }
  
  public void setMultiple(String multiple)
  {
    this.multiple = multiple;
  }
  
  public void setValue(Object value)
  {
    this.value = value;
  }
  
  public int doStartTag() throws JspException
  {
    JspWriter out = null;
    JobOpportunityBean opp = null;
    JobOpportunityAssignmentBean ass[] = null;
    HashMap sel = null;
    
    try
    {
      out = pageContext.getOut();
      
      DegreeBean[] degrees = DegreeManager.getDegreeBeans();
      
      sel = new HashMap();
      
      if(this.value != null)
      {
        if(this.value instanceof String)
        {
          if(!StringUtils.isEmpty((String)this.value))
          {
            opp = JobOpportunityManager.getJobOpportunityBean((String)this.value);
            
            if(opp != null)
              ass = JobOpportunityAssignmentManager.getJobOpportunityAssignmentBeans(opp);
            
            for(int i=0; ((ass != null) && (i < ass.length)); i++)
            {
              AssignmentEducationBean[] tmp = AssignmentEducationManager.getAssignmentEducationBeans(ass[i]);
                
              for(int j=0; ((tmp != null) && (j < tmp.length)); j++)
                sel.put(tmp[j].getDegreeId(), null);
            }
          }
        }
        else if(this.value instanceof AssignmentEducationBean[])
        {
          AssignmentEducationBean[] tmp = (AssignmentEducationBean[]) this.value;
          
          for(int j=0; ((tmp != null) && (j < tmp.length)); j++)
                sel.put(tmp[j].getDegreeId(), null);
        }
        else if(this.value instanceof DegreeBean[])
        {
          DegreeBean[] tmp = (DegreeBean[]) this.value;
          
          for(int j=0; ((tmp != null) && (j < tmp.length)); j++)
                sel.put(tmp[j].getAbbreviation(), null);
        }
        else if(this.value instanceof JobOpportunityBean)
        {
          opp = (JobOpportunityBean) this.value;
          
          if(opp != null)
          {
            ass = (JobOpportunityAssignmentBean[]) opp.toArray(new JobOpportunityAssignmentBean[0]);
            
            if((ass == null) || (ass.length <= 0))
              ass = JobOpportunityAssignmentManager.getJobOpportunityAssignmentBeans(opp);
            
            for(int i=0; ((ass != null) && (i < ass.length)); i++)
            {
              AssignmentEducationBean[] tmp = ass[i].getRequiredEducation();
              
              if((tmp == null) || (tmp.length <= 0))
                tmp = AssignmentEducationManager.getAssignmentEducationBeans(ass[i]);
                  
              for(int j=0; ((tmp != null) && (j < tmp.length)); j++)
                sel.put(tmp[j].getDegreeId(), null);
            }
          }
        }
      }
      
      
      if(listtype != "selectbox" && listtype != "filterbox" && listtype != "filterboxDataTable") {
      //Modified code for new PP 2019
	      out.print("<div style=\"clear:both;padding-top:10px;\"></div>");
	   String degreeName;
	      for(int i = 0; i < degrees.length; i++) {    
	    	  
	      out.print("<div style=\"float:left;min-width:300px;width:50%;color:DimGrey;font-size:11px;\">");	    
	      out.print("<label class=\"checkbox-inline\">");
	      //Some are too long name wise, cut em.
	      if(degrees[i].getTitle().length() >75) {
			   degreeName = degrees[i].getTitle().substring(0,75);
		   } else {
			   degreeName = degrees[i].getTitle();
		   }
	      
	      out.print("<input class='"+this.id+"' type='checkbox' name='" + this.id + "' VALUE=\"" + degrees[i].getAbbreviation() + "\"" + (sel.containsKey(degrees[i].getAbbreviation())? " checked":"") + ">" + degreeName + "");
	      out.print("</label></div>");
	      }
	      out.print("<div style=\"float:left;min-width:300px;width:50%;color:DimGrey;font-size:11px;\">");
	      out.print("<label class=\"checkbox-inline\">");      
	      out.print("<input class='"+this.id+"' type='checkbox' name='" + this.id + "' VALUE='0'>Not Applicable");
	      out.print("</label></div>"); 
	      out.print("<div style=\"clear:both;padding-top:10px;\"></div>");     
	     
      } else if (listtype == "filterbox") {
    	  
    	  //Modified code for new PP 2019
	      out.print("<div style=\"clear:both;padding-top:10px;\"></div>");
	      String degreeName;
	      for(int i = 0; i < degrees.length; i++) {
	   
	     if(degrees[i].getTitle().contains("Diploma") || degrees[i].getTitle().contains("Bachelor") || degrees[i].getTitle().contains("Master")) {
	    	  
	      out.print("<div style=\"float:left;min-width:300px;width:50%;color:DimGrey;font-size:11px;\">");	    
	      out.print("<label class=\"checkbox-inline\">");
	      
	      if(degrees[i].getTitle().length() >75) {
			   degreeName = degrees[i].getTitle().substring(0,75);
		   } else {
			   degreeName = degrees[i].getTitle();
		   }
	      
	      out.print("<input class='"+this.id+"' type='checkbox' name='" + this.id + "' VALUE=\"" + degrees[i].getAbbreviation() + "\"" + (sel.containsKey(degrees[i].getAbbreviation())? " checked":"") + ">" + degreeName + "");
	      out.print("</label></div>");
	      }}
	      out.print("<div style=\"float:left;min-width:300px;width:50%;color:DimGrey;font-size:11px;\">");
	      out.print("<label class=\"checkbox-inline\">");      
	      out.print("<input class='"+this.id+"' type='checkbox' name='" + this.id + "' VALUE='0'>Not Applicable");
	      out.print("</label></div>"); 
	      out.print("<div style=\"clear:both;padding-top:10px;\"></div>");   
    	  
      }  else if (listtype == "filterboxDataTable") {
    	  
    	  out.print("<div style=\"clear:both;padding-top:10px;\"></div>");
    	  String degreeName;
    	  out.print("<div style=\"min-width:300px;width:100%;color:DimGrey;font-size:11px;\">");
    	  out.print("<table width='100%' class='table table-sm table-striped degreeTable' style='width:100%;font-size:11px;'>");	  
    	  out.print("<thead class='thead-light'>");	 
    	  out.print("<tr><th width='10%'>SELECT</th><th width='70%'>DEGREE NAME</th><th width='20%'>ABBREVIATION</th></tr>");	 
    	  out.print("</thead><tbody>");	
    	 
	      for(int i = 0; i < degrees.length; i++) {	  	     
	     
	    	  
	    	  
	  if(!degrees[i].getTitle().contains("Doctor")) {
	    	  
	    	  
	    if(degrees[i].getTitle().length() >100) {
			   degreeName = degrees[i].getTitle().substring(0,100);
		   } else {
			   degreeName = degrees[i].getTitle();
		   }
	      out.print("<tr id='"+degrees[i].getAbbreviation()+"'><td width='10%'>");
	      out.print("<input class='"+this.id+"' type='checkbox' name='" + this.id + "' VALUE=\"" + degrees[i].getAbbreviation() + "\"" + (sel.containsKey(degrees[i].getAbbreviation())? " checked":"") + ">");
	      out.print("</td><td width='70%'>"+ degreeName +"</td><td width='20%'>"+ degrees[i].getAbbreviation() + "</td></tr>");
	     }}	     
	      out.print("</tbody></table></div>");
	      out.print("<div style=\"clear:both;padding-top:10px;\"></div>");   
      	
      
      } else {
      
	   
			      out.print("<SELECT name=\"" + this.id + "\" id=\"" + this.id + "\"");
			      if((this.cls != null) && !this.cls.trim().equals(""))
			      out.print(" class=\"" + this.cls + "\"");
			      if((this.style != null) && !this.style.trim().equals(""))
			     out.print(" style=\"" + this.style + "\"");
			      if(Boolean.valueOf(multiple).booleanValue())
			      out.println(" MULTIPLE");
			      out.println(">");      
			      out.println("<OPTION VALUE=\"0\">NOT APPLICABLE</OPTION>");
			      for(int i = 0; i < degrees.length; i++)
			      out.println("<OPTION VALUE=\"" + degrees[i].getAbbreviation() + "\"" + (sel.containsKey(degrees[i].getAbbreviation())? " SELECTED":"") + ">" + degrees[i].getTitle() + "</OPTION>");
			      
			      out.println("</SELECT>");
      } 
    }
    catch(JobOpportunityException e)
    {
      throw new JspException(e.getMessage());
    }
    catch(IOException e)
    {
      throw new JspException(e.getMessage());
    }

    return SKIP_BODY;
  }
}