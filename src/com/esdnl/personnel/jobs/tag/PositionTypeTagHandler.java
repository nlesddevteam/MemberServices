package com.esdnl.personnel.jobs.tag;

import java.io.*;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import com.esdnl.personnel.jobs.constants.*;

public class PositionTypeTagHandler extends TagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7129594541034208415L;
	private String id;
  private String cls;
  private String style;
  private PositionTypeConstant value;
  private String onChange;
  
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
  
  public void setValue(PositionTypeConstant value)
  {
    this.value = value;
  }
  
  public void setOnChange(String onChange)
  {
    this.onChange = onChange;
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
      if((this.onChange != null) && !this.onChange.trim().equals(""))
        out.print(" onchange=\"" + this.onChange + "\"");
      out.println(">");
      
      out.println("<OPTION VALUE='-1'>--- SELECT POSITION TYPE ---</OPTION>");
      for(int i = 0; i < PositionTypeConstant.ALL.length; i++)
        out.println("<OPTION VALUE=\"" + PositionTypeConstant.ALL[i].getValue() + "\"" + (((this.value != null) && this.value.equal(PositionTypeConstant.ALL[i]))? " SELECTED":"") + ">" + PositionTypeConstant.ALL[i] + "</OPTION>");
      
      out.println("</SELECT>");
      
    }
    catch(IOException e)
    {
      throw new JspException(e.getMessage());
    }

    return SKIP_BODY;
  }
	
}
