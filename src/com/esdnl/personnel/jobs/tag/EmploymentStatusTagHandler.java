package com.esdnl.personnel.jobs.tag;


import java.io.*;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import com.esdnl.personnel.jobs.constants.*;

public class EmploymentStatusTagHandler extends TagSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6542994615447509701L;
	
	private String id;
  private String cls;
  private String style;
  private EmploymentConstant value;
  
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
  
  public void setValue(EmploymentConstant value)
  {
    this.value = value;
  }
  
  public int doStartTag() throws JspException
  {
    JspWriter out = null;
    
    try
    {
      out = pageContext.getOut();
      
            
      out.print("<SELECT name=\"" + this.id + "\" id=\"" + this.id + "\"");
      if((this.cls != null) && !this.cls.trim().equals(""))
        out.print(" class=\"" + this.cls + "\"");
      if((this.style != null) && !this.style.trim().equals(""))
        out.print(" style=\"" + this.style + "\"");
      out.println(">");
      
      out.println("<OPTION VALUE='-1'>--- EMPLOYMENT STATUS ---</OPTION>");
      for(int i = 0; i < EmploymentConstant.ALL.length; i++)
        out.println("<OPTION VALUE=\"" + EmploymentConstant.ALL[i].getValue() + "\"" + (((this.value != null) && this.value.equal(EmploymentConstant.ALL[i]))? " SELECTED":"") + ">" + EmploymentConstant.ALL[i] + "</OPTION>");
      
      out.println("</SELECT>");
      
    }
    catch(IOException e)
    {
      throw new JspException(e.getMessage());
    }

    return SKIP_BODY;
  }
}
