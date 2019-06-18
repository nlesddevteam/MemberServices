package com.awsd.travel.tag;

import java.io.*;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import com.awsd.travel.constant.*;
import com.esdnl.util.*;

public class KeyTypeTagHandler  extends TagSupport{
	
	private static final long serialVersionUID = 1023016862014637234L;
	
	private String id;
  private String cls;
  private String style;
  private String onChange;
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
  
  public void setOnChange(String onChange)
  {
  	this.onChange = onChange;
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
      
      out.print("<SELECT name=\"" + this.id + "\" id=\"" + this.id + "\"");
      if((this.cls != null) && !this.cls.trim().equals(""))
        out.print(" class=\"" + this.cls + "\"");
      if((this.style != null) && !this.style.trim().equals(""))
        out.print(" style=\"" + this.style + "\"");
      if((this.onChange != null) && !this.onChange.trim().equals(""))
        out.print(" onchange=\"" + this.onChange + "\"");
      out.println(">");
      
      out.println("<OPTION VALUE='-1'>SELECT KEY TYPE</OPTION>");
      for(int i = 0; i < KeyType.ALL.length; i++)
        out.println("<OPTION VALUE=\"" + KeyType.ALL[i].getValue() + "\"" + ((!StringUtils.isEmpty(this.value) && (Integer.parseInt(this.value) == KeyType.ALL[i].getValue()))? " SELECTED":"") + ">" + KeyType.ALL[i].getDescription() + "</OPTION>");
      
      out.println("</SELECT>");
      
    }
    catch(IOException e)
    {
      throw new JspException(e.getMessage());
    }

    return SKIP_BODY;
  }

}
