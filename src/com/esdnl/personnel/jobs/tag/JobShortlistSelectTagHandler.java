package com.esdnl.personnel.jobs.tag;

import java.io.*;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import com.esdnl.personnel.jobs.bean.*;
import com.esdnl.util.*;

public class JobShortlistSelectTagHandler extends TagSupport
{
  /**
	 * 
	 */
	private static final long serialVersionUID = -4101873051216975596L;
	private String id;
  private String cls;
  private String style;
  private String value;
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
  
  public void setValue(String value)
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
      
      ApplicantProfileBean[] applicants = (ApplicantProfileBean[]) pageContext.getAttribute("JOB_SHORTLIST", PageContext.SESSION_SCOPE);
            
      out.print("<SELECT name=\"" + this.id + "\" id=\"" + this.id + "\"");
      if((this.cls != null) && !this.cls.trim().equals(""))
        out.print(" class=\"" + this.cls + "\"");
      if((this.style != null) && !this.style.trim().equals(""))
        out.print(" style=\"" + this.style + "\"");
      if((this.onChange != null) && !this.onChange.trim().equals(""))
        out.print(" onchange=\"" + this.onChange + "\"");
      out.println(">");
      
      out.println("<OPTION VALUE='-1'>SELECT APPLICANT</OPTION>");
      for(int i = 0; i < applicants.length; i++)
        out.println("<OPTION VALUE=\"" + applicants[i].getSIN() + "\"" + ((!StringUtils.isEmpty(this.value) && this.value.equals(applicants[i].getSIN()))? " SELECTED":"") + ">" + applicants[i].getFullName() + "</OPTION>");
      
      out.println("</SELECT>");
      
    }
    catch(IOException e)
    {
      throw new JspException(e.getMessage());
    }

    return SKIP_BODY;
  }
}