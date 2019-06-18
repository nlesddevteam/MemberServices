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
import com.esdnl.nicep.constants.*;

public class AgencyContractTypeTagHandler   extends TagSupport
{
  private String id;
  private String cls;
  private String style;
  private AgencyContractType value;
  private String onChange;
  
  public AgencyContractTypeTagHandler()
  {
    this.id = null;
    this.cls = null;
    this.style = null;
    this.value = null;
    this.onChange = null;
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
  
  public void setValue(AgencyContractType value)
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
    AgencyDemographicsBean agency = null;
    
    try
    {    
      out = pageContext.getOut();
      
      //agency = (AgencyDemographicsBean) pageContext.getAttribute("AGENCYBEAN", PageContext.REQUEST_SCOPE);
      
      out.print("<SELECT name=\"" + this.id + "\" id=\"" + this.id + "\"");
      if((this.cls != null) && !this.cls.trim().equals(""))
        out.print(" class=\"" + this.cls + "\"");
      if((this.style != null) && !this.style.trim().equals(""))
        out.print(" style=\"" + this.style + "\"");
      if((this.onChange != null) && !this.onChange.trim().equals(""))
        out.print(" onchange=\"" + this.onChange + "\"");
      out.println(" >");
      
      out.println("<OPTION VALUE=\"-1\">SELECT CONTRACT TYPE</OPTION>");
      for(int i = 0; i <AgencyContractType.ALL.length; i++)
        out.println("<OPTION VALUE=\"" + AgencyContractType.ALL[i].getValue() + "\""
          + ((AgencyContractType.ALL[i].equals(this.value))?" SELECTED":"") + ">" 
          + AgencyContractType.ALL[i].getDescription() + "</OPTION>");
      
      out.println("</SELECT>");
    }
    catch(IOException e)
    {
      e.printStackTrace();
      throw new JspException(e.getMessage());
    }

    return SKIP_BODY;
  }
  
}