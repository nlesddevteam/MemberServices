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

public class StudentsNoAgencyAssociationTagHandler extends TagSupport
{
  private String id;
  private String cls;
  private String style;
  private int value;
  
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
  
  public void setValue(int value)
  {
    this.value = value;
  }
  
  public int doStartTag() throws JspException
  {
    JspWriter out = null;
    
    try
    {
      out = pageContext.getOut();
      
      StudentDemographicsBean[] students = StudentDemographicsManager.getStudentDemographicsBeansNoAgencyAssociation();
      
            
      out.print("<SELECT name=\"" + this.id + "\" id=\"" + this.id + "\"");
      if((this.cls != null) && !this.cls.trim().equals(""))
        out.print(" class=\"" + this.cls + "\"");
      if((this.style != null) && !this.style.trim().equals(""))
        out.print(" style=\"" + this.style + "\"");
      out.println(" MULTIPLE>");
      
      out.println("<OPTION VALUE='-1'>SELECT ONE OR MORE STUDENTS</OPTION>");
      for(int i = 0; i < students.length; i++)
        out.println("<OPTION VALUE=\"" + students[i].getStudentId() + "\"" + ((this.value == students[i].getStudentId())? " SELECTED":"") + ">" + students[i].getFullname() + "</OPTION>");
      
      out.println("</SELECT>");
      
    }
    catch(NICEPException e)
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