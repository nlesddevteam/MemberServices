package com.esdnl.personnel.jobs.tag;

import java.io.*;

import java.text.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import com.awsd.school.*;
import com.esdnl.personnel.jobs.bean.*;
import com.esdnl.util.*;

public class GradeSubjectUnitPercentageTagHandler  extends TagSupport {
	
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
    Grade g = null;
    Subject s = null;
    
    try
    {
      out = pageContext.getOut();
      
      out.println("<table width='100%' cellpadding='2' cellspacing='0' border='0'>");
      
      //header
      out.println("<tr><td width='35%' class='displayHeaderTitle'>GRADE</td><td width='35%' class='displayHeaderTitle'>SUBJECT</td><td width='20%' class='displayHeaderTitle'>%UNIT</td><td class='displayHeaderTitle' width='10%'>OPTION</td></tr>");
      
      out.println("<tr>");
      
      //grade
      out.print("<td>");
      try{
      Iterator iter = GradeDB.getGrades().iterator();
      
      out.print("<SELECT name='gsu_grade' id='gsu_grade'");
      if((this.cls != null) && !this.cls.trim().equals(""))
        out.print(" class=\"" + this.cls + "\"");
      if((this.style != null) && !this.style.trim().equals(""))
        out.print(" style=\"" + this.style + "\"");
      out.println(" >");
    
      while(iter.hasNext())
      {
        g = (Grade) iter.next();
        if((g.getGradeID()==1)||(g.getGradeID()==30)||(g.getGradeID()==31)
        		||(g.getGradeID()==32)||(g.getGradeID()==33)||(g.getGradeID()==34) ||(g.getGradeID()==35))
        	out.println("<OPTION VALUE=\"" + g.getGradeID() + "\">" + g.getGradeName() + "</OPTION>");
      }
      
      out.println("</SELECT>");
      }
      catch(GradeException e){
      	throw new JspException(e.getMessage());
      }
      out.println("</td>");
      
      //subject
      out.print("<td>");
      try
      {
        Iterator iter = SubjectDB.getSubjects().iterator();
        
        out.print("<SELECT name=\"gsu_subject\" id=\"gsu_subject\"");
        if((this.cls != null) && !this.cls.trim().equals(""))
          out.print(" class=\"" + this.cls + "\"");
        if((this.style != null) && !this.style.trim().equals(""))
          out.print(" style=\"" + this.style + "\"");
        out.println(">");
        out.println("<OPTION VALUE='-1'>NOT APPLICABLE</OPTION>");
        while(iter.hasNext())
        {
          s = (Subject) iter.next();
          if((s.getSubjectID() < 43) || (s.getSubjectID() > 48))
            out.println("<OPTION VALUE=\"" + s.getSubjectID() + "\">" + s.getSubjectName() + "</OPTION>");
        }
        
        out.println("</SELECT>");
        
      }
      catch(SubjectException e)
      {
        throw new JspException(e.getMessage());
      }
      out.println("</td>");
      
      //percent
      out.print("<td>");
      out.print("<input type='text' name='gsu_percent' id='gsu_percent' size='10'");
      if((this.cls != null) && !this.cls.trim().equals(""))
        out.print(" class=\"" + this.cls + "\"");
      if((this.style != null) && !this.style.trim().equals(""))
        out.print(" style=\"" + this.style + "\"");
      out.print(">");
      out.println("</td>");
      
      //add button
      out.print("<td align='center'>");
      out.print("<input type='button' class='btn btn-xs btn-success' value='ADD' onClick='addGSU();'>");
      out.println("</td>");
      
      out.println("</tr>");
      
      out.println("</table>");
      
    }
    catch(IOException e)
    {
      throw new JspException(e.getMessage());
    }

    return SKIP_BODY;
  }

}
