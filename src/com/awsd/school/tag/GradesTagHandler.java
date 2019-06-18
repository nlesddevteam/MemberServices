package com.awsd.school.tag;

import java.io.*;

import java.text.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import com.awsd.school.*;
import com.esdnl.util.*;

public class GradesTagHandler extends TagSupport
{
  private String id;
  private String cls;
  private String style;
  private String value;
  private String multiple;
  
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
  
  public void setValue(String value)
  {
    this.value = value;
  }
  
  public void setMultiple(String multiple)
  {
    this.multiple = multiple;
  }
  
  public int doStartTag() throws JspException
  {
    JspWriter out = null;
    Grade s = null;    
    try
    {
      out = pageContext.getOut();
      
      Iterator iter = GradeDB.getGrades().iterator();
      
      
      //Modified code for new PP 2019
		      out.print("<div style=\"clear:both;padding-top:10px;\"></div>");
		      while(iter.hasNext())
		      {
		        s = (Grade) iter.next();					
					
		      out.print("<div style=\"float:left;min-width:200px;width:20%;color:DimGrey;font-size:11px;\">");
		      out.print("<label class=\"checkbox-inline\">");
		      out.print("<input type=\"checkbox\" name=\"" + this.id + "\" id=\"" + this.id + "\" VALUE=\"" + s.getGradeID() + "\"" + ((!StringUtils.isEmpty(this.value) && (Integer.parseInt(this.value) == s.getGradeID()))?" checked":"") + ">" + s.getGradeName() + "");
		      out.print("</label></div>");
		      
		      }
		      out.print("<div style=\"clear:both;padding-top:10px;\"></div>");
      
      
      //This is the original code for multiple select.
			      //out.print("<SELECT name=\"" + this.id + "\" id=\"" + this.id + "\"");
			      //if((this.cls != null) && !this.cls.trim().equals(""))
			      //  out.print(" class=\"" + this.cls + "\"");
			      // if((this.style != null) && !this.style.trim().equals(""))
			      //  out.print(" style=\"" + this.style + "\"");
			      // if(Boolean.valueOf(multiple).booleanValue())
			      //   out.println(" MULTIPLE");
			      //  out.println(" >");    
			      // while(iter.hasNext())
			      //  {
			      //   s = (Grade) iter.next();
			      //    out.println("<OPTION VALUE=\"" + s.getGradeID() + "\"" + ((!StringUtils.isEmpty(this.value) && (Integer.parseInt(this.value) == s.getGradeID()))?" SELECTED":"") + ">" + s.getGradeName() + "</OPTION>");
			      //  }      
			      //    out.println("</SELECT>");
      
    }
    catch(GradeException e)
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