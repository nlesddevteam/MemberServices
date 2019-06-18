package com.awsd.school.tag;

import java.io.*;

import java.text.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import com.awsd.school.*;

import com.esdnl.util.*;
import com.esdnl.personnel.jobs.bean.*;
import com.esdnl.personnel.jobs.dao.*;

public class SubjectsTagHandler  extends TagSupport
{
  private String id;
  private String cls;
  private String style;
  private String multiple;
  private Object value;
  
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
  
  public void setMultiple(String multiple)
  {
    this.multiple = multiple;
  }
  
  public void setValue(Object value)
  {
    this.value = value;
  }

  public int doStartTag() throws JspException
  {
    JspWriter out = null;
    Subject s = null;
    HashMap sel = null;
    try
    {
      out = pageContext.getOut();
      
      Iterator iter = SubjectDB.getSubjects().iterator();
      
      sel = new HashMap();
      
      if(this.value != null)
      {
        if(value instanceof Integer)
        {
          sel.put(value, null);
        }
        else if(value instanceof Subject[])
        {
          Subject[] tmp = (Subject[]) value;
          
          for(int i=0; ((tmp != null)&& (i < tmp.length)); i++)
            sel.put(new Integer(tmp[i].getSubjectID()), null);
        }
      }
     
      
      //Modified code for new PP 2019
	      out.print("<div style=\"clear:both;padding-top:10px;\"></div>");
	      while(iter.hasNext())
	      {
	        s = (Subject) iter.next();
	        if((s.getSubjectID() < 43) || (s.getSubjectID() > 48)) {
	      out.print("<div style=\"float:left;min-width:200px;width:20%;color:DimGrey;font-size:11px;\">");
	      out.print("<label class=\"checkbox-inline\">");
	      out.print("<input class='" + this.id + "' type='checkbox' name='" + this.id + "' VALUE=\"" + s.getSubjectID() + "\"" + ((sel.containsKey(new Integer(s.getSubjectID())))? " checked":"") + ">" + s.getSubjectName() + "");
	      out.print("</label></div>");
	      }}
	      
	      out.print("<div style=\"float:left;min-width:200px;width:20%;color:DimGrey;font-size:11px;\">");
	      out.print("<label class=\"checkbox-inline\">");      
	      out.print("<input class='"+ this.id +"' type='checkbox' name='" + this.id + "' VALUE='-1'>Not Applicable");
	      out.print("</label></div>"); 
	      out.print("<div style=\"clear:both;padding-top:10px;\"></div>");
      
      
      
      //Original Code
				      //out.print("<SELECT name=\"" + this.id + "\" id=\"" + this.id + "\"");
				      //if((this.cls != null) && !this.cls.trim().equals(""))
				      // out.print(" class=\"" + this.cls + "\"");
				      //if((this.style != null) && !this.style.trim().equals(""))
				      //out.print(" style=\"" + this.style + "\"");
				      //if(Boolean.valueOf(multiple).booleanValue())
				      //out.println(" MULTIPLE");
				      //out.println(">");
				      //out.println("<OPTION VALUE='-1'>NOT APPLICABLE1</OPTION>");
				      //while(iter.hasNext())
				      //{
				      //  s = (Subject) iter.next();
				      //  if((s.getSubjectID() < 43) || (s.getSubjectID() > 48))
				      //    out.println("<OPTION VALUE=\"" + s.getSubjectID() + "\"" + (sel.containsKey(new Integer(s.getSubjectID()))? " SELECTED":"") + ">" + s.getSubjectName() + "</OPTION>");
				      //}
				      
				      //out.println("</SELECT>");
      
    }
    catch(SubjectException e)
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