package com.esdnl.sca.site.tag;

import java.io.*;
import java.text.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import com.esdnl.sca.dao.*;
import com.esdnl.sca.model.bean.*;

public class AssessmentTypeTagHandler extends TagSupport
{
  private String id;
  private String cls;
  private String style;
  private AssessmentType[] value;
  private String multiple;
  private boolean displayOnly = false;
  
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
  
  public void setValue(AssessmentType[] value)
  {
    this.value = value;
  }
  
  public void setMultiple(String multiple)
  {
    this.multiple = multiple;
  }
  
  public void setDisplayOnly(boolean displayOnly)
  {
    this.displayOnly = displayOnly;
  }
  
  public int doStartTag() throws JspException
  {
    JspWriter out = null;
    AssessmentType[] at = null;
    
    try
    {
      out = pageContext.getOut();
      
      if(!displayOnly)
      {
        Map vmap = new HashMap();
        if(value != null)
        {
          for(int i=0; i < value.length; i++)
          {
            vmap.put(value[i].getIdObj(), null);
          }
        }
        
        at = SCAManager.getAssessmentTypeBeans();
        
        out.print("<SELECT name=\"" + this.id + "\" id=\"" + this.id + "\"");
        if((this.cls != null) && !this.cls.trim().equals(""))
          out.print(" class=\"" + this.cls + "\"");
        if((this.style != null) && !this.style.trim().equals(""))
          out.print(" style=\"" + this.style + "\"");
        if(Boolean.valueOf(multiple).booleanValue())
          out.println(" MULTIPLE");
        out.println(" >");
      
        for(int i=0; i < at.length; i++)
        {
          out.println("<OPTION VALUE=\"" + at[i].getId() + "\"" + (vmap.containsKey(at[i].getIdObj())?" SELECTED":"") + ">" + at[i].getDescription() + "</OPTION>");
        }
        
        out.println("</SELECT>");
      }
      else
      {
        for(int i=0; i < value.length; i++)
        {
          out.println(value[i].getDescription() + "</br>");
        }
      }
    }
    catch(SCAException e)
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