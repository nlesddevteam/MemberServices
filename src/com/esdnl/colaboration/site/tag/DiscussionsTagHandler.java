package com.esdnl.colaboration.site.tag;

import java.io.*;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import com.esdnl.colaboration.bean.*;
import com.esdnl.colaboration.dao.*;

public class DiscussionsTagHandler extends TagSupport
{

	private static final long serialVersionUID = -5901352348568950336L;
	private String id;
  private String cls;
  private String style;
  
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
    
    try
    {
      out = pageContext.getOut();
      
      DiscussionBean[] discussions = CollaborationManager.getDiscussionBeans();
      
      out.print("<SELECT name=\"" + this.id + "\" id=\"" + this.id + "\"");
      if((this.cls != null) && !this.cls.trim().equals(""))
        out.print(" class=\"" + this.cls + "\"");
      if((this.style != null) && !this.style.trim().equals(""))
        out.print(" style=\"" + this.style + "\"");
      out.println(">");;
      
      for(int i = 0; i < discussions.length; i++)
        out.println("<OPTION VALUE=\"" + discussions[i].getId() + "\">" + discussions[i] + "</OPTION>");

      out.println("</SELECT>");
      
    }
    catch(CollaborationException e)
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