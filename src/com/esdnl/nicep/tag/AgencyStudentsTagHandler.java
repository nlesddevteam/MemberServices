package com.esdnl.nicep.tag;


import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import com.esdnl.util.StringUtils;
import com.esdnl.nicep.beans.*;
import com.esdnl.nicep.dao.*;

public class AgencyStudentsTagHandler  extends TagSupport
{
  private String id;
  private String cls;
  private String style;
  
  public AgencyStudentsTagHandler()
  {
    this.id = null;
    this.cls = null;
    this.style = null;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public void setCls(String cls)
  {
    this.cls = cls;
  }
  
  public void setStyle(String style)
  {
    this.style = style;
  }
  
  public int doStartTag() throws JspException
  {
    JspWriter out = null;
    AgencyDemographicsBean agency = null;
    StudentDemographicsBean[] students = null;
    
    try
    {    
      out = pageContext.getOut();
      
      agency = (AgencyDemographicsBean) pageContext.getAttribute("AGENCYBEAN", PageContext.REQUEST_SCOPE);
      students = (StudentDemographicsBean[]) pageContext.getAttribute("STUDENTBEANS", PageContext.REQUEST_SCOPE);
      
      if(students.length > 0)
      {
        out.println("<div id='AgencyStudentsTag'>");
        out.println("\t<table cellspacing='0' cellpadding='2'>");
        out.println("\t\t<tr><th width='40%'>Name</th><th width='40%'>Address</th><th width='20%'>Phone #</th></tr>");
        for(int i=0; i < students.length; i++)
        {
          out.println("\t\t<tr>");
            out.println("\t\t\t<td>" + students[i].getFullname() + "</td>");
            out.println("\t\t\t<td>" + StringUtils.encodeHTML(students[i].getAddress()) + "</td>");
            out.println("\t\t\t<td>" + (!StringUtils.isEmpty(students[i].getPhone1())?students[i].getPhone1():"&nbsp;") 
                                 + (!StringUtils.isEmpty(students[i].getPhone2())?"<BR>" + students[i].getPhone2():"&nbsp;")
                                 + "</td>");
          out.println("\t\t</tr>");
          out.println("\t\t<tr bgcolor='#f4f4f4'>");
            out.println("\t\t\t<td colspan='3' class='options'>");
            out.println("\t\t\t\t<input type='button' value='view' onclick=\"document.forms[0].action='viewStudent.html';document.forms[0].id.value='" + students[i].getStudentId() + "';document.forms[0].submit();\">");
            out.println("\t\t\t\t<input type='button' value='Delete' onclick=\"document.forms[0].action='delAgencyStudent.html?agency_id=" + agency.getAgencyId() + "&student_id=" + students[i].getStudentId() + "'; document.forms[0].submit();\">");
            out.println("\t\t\t</td>");
          out.println("\t\t</tr>");
        }
        out.println("\t</table>");
        out.println("</div>");
      }
      else
      {
        out.println("<table cellspacing='0' cellpadding='2'>");
        out.println("\t<tr><td colspan='3' class='messageText'>No students associated with this agency.</td></tr>");
        out.println("</table>");
      }
      
    }
    catch(IOException e)
    {
      e.printStackTrace();
      throw new JspException(e.getMessage());
    }

    return SKIP_BODY;
  }
  
}