package com.esdnl.personnel.jobs.tag;

import java.io.*;

import java.text.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import com.awsd.school.*;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.util.*;

public class JobShortListStaticDisplayTagHandler extends TagSupport {
	
	private String cls;
  private String style;
  
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
      
      ApplicantProfileBean[] applicants = (ApplicantProfileBean[]) pageContext.getAttribute("JOB_SHORTLIST", PageContext.SESSION_SCOPE);
            
      //out.print("<UL");
      //if((this.cls != null) && !this.cls.trim().equals(""))
       //out.print(" class=\"" + this.cls + "\"");
      //if((this.style != null) && !this.style.trim().equals(""))
      //  out.print(" style=\"" + this.style + "\"");
     // out.println(">");
      
      for(int i = 0; i < applicants.length; i++){
        out.println(" &middot;");
        //if((this.cls != null) && !this.cls.trim().equals(""))
        //  out.print(" class=\"" + this.cls + "\"");
        //if((this.style != null) && !this.style.trim().equals(""))
        //  out.print(" style=\"" + this.style + "\"");
        out.println(applicants[i].getFullName() + "<br/>");
      }

      //out.println("</UL>");
      
    }
    catch(IOException e)
    {
      throw new JspException(e.getMessage());
    }

    return SKIP_BODY;
  }

}
