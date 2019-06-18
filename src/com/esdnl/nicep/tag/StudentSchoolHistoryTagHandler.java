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

import com.awsd.school.*;

public class StudentSchoolHistoryTagHandler extends TagSupport
{
  private String id;
  private String cls;
  private String style;
  
  public StudentSchoolHistoryTagHandler()
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
    StudentDemographicsBean student = null;
    StudentSchoolHistoryBean[] history = null;
    
    try
    {    
      out = pageContext.getOut();
      
      student = (StudentDemographicsBean) pageContext.getAttribute("STUDENTBEAN", PageContext.REQUEST_SCOPE);
      history = StudentSchoolHistoryManager.getStudentSchoolHistoryBeans(student);
      
      if(history.length > 0)
      {
        out.println("<div id='StudentSchoolHistoryTag'>");
        out.println("\t<table cellspacing='0' cellpadding='2'>");
        out.println("\t\t<tr><th width='60%'>School Name</th><th width='30%'>School Year</th><th width='10%'>Term</th></tr>");
        for(int i=0; i < history.length; i++)
        {
          try
          {
            out.println("\t\t<tr>");
              out.println("\t\t\t<td>" + SchoolDB.getSchool(history[i].getSchoolId()).getSchoolName() + "</td>");
              out.println("\t\t\t<td>" + history[i].getSchoolYear() + "</td>");
              out.println("\t\t\t<td>" + history[i].getTerm() + "</td>");
            out.println("\t\t</tr>");
            out.println("\t\t<tr bgcolor='#f4f4f4'>");
              out.println("\t\t\t<td colspan='4' class='options'>");
              out.println("&nbsp;");
              //out.println("\t\t\t\t<input type='button' value='Edit' onclick=\"document.forms[0].action='editStudentGuardian.html';document.forms[0].id.value='" + guardians[i].getGuardianId() + "';document.forms[0].submit();\">");
              //out.println("\t\t\t\t<input type='button' value='Delete' onclick=\"document.forms[0].action='delStudentGuardian.html';document.forms[0].id.value='" + guardians[i].getGuardianId() + "';document.forms[0].submit();\">");
              out.println("\t\t\t</td>");
            out.println("\t\t</tr>");
          }
          catch(SchoolException e){e.printStackTrace();}
          
        }
        out.println("\t</table>");
        out.println("</div>");
      }
      else
      {
        out.println("<table cellspacing='0' cellpadding='2'>");
        out.println("\t<tr><td colspan='4' class='messageText'>No school history on record.</td></tr>");
        out.println("</table>");
      }
      
    }
    
    catch(NICEPException e)
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