package com.esdnl.personnel.recognition.site.tag;

import java.io.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import com.esdnl.personnel.recognition.database.*;
import com.esdnl.personnel.recognition.model.bean.*;

import com.awsd.security.*;

public class RecognitionCategoriesTagHandler  extends TagSupport
{
	private static final long serialVersionUID = -127450956803193507L;
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
    User usr = null;
    try
    {
      out = pageContext.getOut();
      
      usr = (User)pageContext.getAttribute("usr", PageContext.SESSION_SCOPE);
      
      NominationPeriodBean[] period = NominationPeriodManager.getActiveNominationPeriodBeans();
      
      out.print("<SELECT name=\"" + this.id + "\" id=\"" + this.id + "\"");
      if((this.cls != null) && !this.cls.trim().equals(""))
        out.print(" class=\"" + this.cls + "\"");
      if((this.style != null) && !this.style.trim().equals(""))
        out.print(" style=\"" + this.style + "\"");
      out.println(">");;
      
      //out.println("<OPTION VALUE=\"0\">NOT APPLICABLE</OPTION>");
      for(int i = 0; i < period.length; i++){
      	if((usr == null) && period[i].getCategory().isSecureOnly())
      		continue;
        out.println("<OPTION VALUE=\"" + period[i].getId() + "\">" + period[i].getCategory().getName() + "</OPTION>");
      }

      out.println("</SELECT>");
      
    }
    catch(RecognitionException e)
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