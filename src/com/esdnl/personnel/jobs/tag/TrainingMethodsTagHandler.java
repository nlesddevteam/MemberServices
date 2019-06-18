package com.esdnl.personnel.jobs.tag;

import java.io.*;

import java.text.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import com.esdnl.util.*;
import com.esdnl.personnel.jobs.constants.TrainingMethodConstant;

public class TrainingMethodsTagHandler extends TagSupport
{
  private String id;
  private String cls;
  private String style;
  private String multiple = "true";
  private String value;
  
  
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
  
  public void setMultiple(String multiple)
  {
    this.multiple = multiple;
  }
  
  public void setValue(String value)
  {
    this.value = value;
  }
  
  public int doStartTag() throws JspException
  {
    JspWriter out = null;

    try
    {
      out = pageContext.getOut();
      
      if (TrainingMethodConstant.ALL.length >= 0) {
      for(int i = 0; i < TrainingMethodConstant.ALL.length; i++) {
      out.print("<div style=\"float:left;min-width:200px;width:20%;color:DimGrey;font-size:11px;\">");
      out.print("<label class=\"checkbox-inline\">");
      out.print("<input type=\"checkbox\" name=\"" + this.id + "\" VALUE=\"" + TrainingMethodConstant.ALL[i].getValue() + "\"" + ((!StringUtils.isEmpty(this.value) && (Integer.parseInt(this.value)==TrainingMethodConstant.ALL[i].getValue()))?" CHECKED":"") + ">" + TrainingMethodConstant.ALL[i].getDescription());
      out.print("</label></div>");
      }
      }  else {    	  
    	  
    	  out.print("<div style=\"float:left;min-width:200px;width:20%;color:DimGrey;font-size:11px;\">");
          out.print("Error in Training");
          out.print("</div>");
    	  
      }
      
     
      
      
      
      //ORIGINAL CODE
      //out.print("<SELECT name=\"" + this.id + "\" id=\"" + this.id + "\"");
      //if((this.cls != null) && !this.cls.trim().equals(""))
     //   out.print(" class=\"" + this.cls + "\"");
     // if((this.style != null) && !this.style.trim().equals(""))
     //   out.print(" style=\"" + this.style + "\"");
     // if(Boolean.valueOf(multiple).booleanValue())
     //   out.println(" MULTIPLE");
    //  out.println(">");
      
     // for(int i = 0; i < TrainingMethodConstant.ALL.length; i++)
     //   out.println("<OPTION VALUE=\"" + TrainingMethodConstant.ALL[i].getValue() + "\""
     //     + ((!StringUtils.isEmpty(this.value) && (Integer.parseInt(this.value)==TrainingMethodConstant.ALL[i].getValue()))?" SELECTED":"") + ">" 
     //     + TrainingMethodConstant.ALL[i].getDescription() + "</OPTION>");
      
     // out.println("</SELECT>");
      
    }
    catch(IOException e)
    {
      throw new JspException(e.getMessage());
    }

    return SKIP_BODY;
  }
}