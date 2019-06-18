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

public class StudentGuardiansTagHandler extends TagSupport
{
  private String id;
  private String cls;
  private String style;
  
  public StudentGuardiansTagHandler()
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
    StudentGuardianDemographicsBean[] guardians = null;
    
    try
    {    
      out = pageContext.getOut();
      
      student = (StudentDemographicsBean) pageContext.getAttribute("STUDENTBEAN", PageContext.REQUEST_SCOPE);
      guardians = StudentGuardianDemographicsManager.getStudentGuardianDemographicsBeans(student);
      
      if(guardians.length > 0)
      {
        out.println("<div id='StudentGuardianDemographicsTag'>");
        out.println("\t<table cellspacing='0' cellpadding='2'>");
        out.println("\t\t<tr><th width='25%'>Name</th><th width='30%'>Address</th><th width='20%'>Phone #</th><th width='25%'>Email</th></tr>");
        for(int i=0; i < guardians.length; i++)
        {
          out.println("\t\t<tr>");
            out.println("\t\t\t<td>" + guardians[i].getFullname() + "</td>");
            out.println("\t\t\t<td>" + StringUtils.encodeHTML(guardians[i].getAddress()) + "</td>");
            out.println("\t\t\t<td>" + (!StringUtils.isEmpty(guardians[i].getPhone1())?guardians[i].getPhone1():"&nbsp;") 
                                 + (!StringUtils.isEmpty(guardians[i].getPhone2())?"<BR>" + guardians[i].getPhone2():"&nbsp;")
                                 + "</td>");
            out.println("\t\t\t<td>" + (!StringUtils.isEmpty(guardians[i].getEmail())?"<a href='mailto:" + guardians[i].getEmail() + "'>" + guardians[i].getEmail() + "</a>" :"&nbsp;") + "</td>");
          out.println("\t\t</tr>");
          out.println("\t\t<tr bgcolor='#f4f4f4'>");
            out.println("\t\t\t<td colspan='4' class='options'>");
            out.println("\t\t\t\t<input type='button' value='Edit' onclick=\"document.forms[0].action='editStudentGuardian.html';document.forms[0].id.value='" + guardians[i].getGuardianId() + "';document.forms[0].submit();\">");
            out.println("\t\t\t\t<input type='button' value='Delete' onclick=\"document.forms[0].action='delStudentGuardian.html';document.forms[0].id.value='" + guardians[i].getGuardianId() + "';document.forms[0].submit();\">");
            out.println("\t\t\t</td>");
          out.println("\t\t</tr>");
        }
        out.println("\t</table>");
        out.println("</div>");
      }
      else
      {
        out.println("<table cellspacing='0' cellpadding='2'>");
        out.println("\t<tr><td colspan='4' class='messageText'>No parents/guardians on file.</td></tr>");
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