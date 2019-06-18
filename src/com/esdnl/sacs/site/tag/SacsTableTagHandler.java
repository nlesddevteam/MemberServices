package com.esdnl.sacs.site.tag;

import java.io.*;
import java.text.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.school.*;
import com.esdnl.sacs.dao.*;
import com.esdnl.sacs.model.bean.*;

public class SacsTableTagHandler extends TagSupport
{
  private String altColor="#FFFFFF";
  
  public int doStartTag() throws JspException
  {
    JspWriter out = null;
    User usr = null;
    
    School[] sch = null;
    InitiativeBean[] inits = null;
    HashMap sch_inits = null;
    boolean isPrincipal = false;
    int scnt = 0;
    
    try
    {
      usr = (User) pageContext.getAttribute("usr", PageContext.SESSION_SCOPE);
      
      out = pageContext.getOut();
      
      sch = (School[]) SchoolDB.getSchools().toArray(new School[0]);
      inits = InitiativeManager.getInitiativeBeans();
  
      
      out.println("<div id='sacs_overview'>");
        out.println("<table cellpadding='0' cellspacing='0' border='0' class='legend' align='center'>");
        out.println("<tr><td class='title' colspan='"+inits.length+"'>Legend</td></tr>");
        out.println("<tr>");
              for(int j=0; j < inits.length; j++)
              {
                if((j%5)==0)
                  out.println("</tr><tr>");
                  
                out.println("<td><span class='name'>P" + (j+1) + "</span>: " +  inits[j] + "</td>");
              }
            out.println("</tr>");
        out.println("</table>");
        out.println("<BR/><BR/>");
        out.println("<table cellpadding='0' cellspacing='0' border='0' align='center'>");
        for(int i=0; i < sch.length; i++)
        {          
          //create header row
          if(i==0) 
          {
            out.println("<tr>");
              out.println("<td class='blank'>&nbsp;</td>");
              for(int j=0; j < inits.length; j++)
              {
                out.println("<th>P" + (j+1) + "</th>");
              }
            out.println("</tr>");
          }
          
          if(sch[i].getSchoolID() > 270)
            continue;
            
          scnt++;
        
          //individual school summary rows
          isPrincipal = (usr.getPersonnel().getPersonnelID() == sch[i].getSchoolPrincipal().getPersonnelID());
          sch_inits = SchoolInitiativeManager.getSchoolInitiativeBeans(sch[i]);
          
          //System.out.println(sch[i].getSchoolName() + " : " + sch_inits.size());
          
          out.println("<tr>");
            
            out.println("<td class='school_name"+ ((scnt%2==0)?" alt":"") + (isPrincipal?" my":"") + "'>" + sch[i].getSchoolName() + "</td>");
            
            for(int k=0; k<inits.length; k++)
            {
              out.println("<td class='"+ ((scnt%2==0)?" alt":"") + (isPrincipal?" my":"") + "'>");
             
              if(sch_inits.containsKey(inits[k]))
              {
                out.println("<a href='viewSchoolInitative.html?sid="+ sch[i].getSchoolID() +"&iid="+ inits[k].getId() +"'>View</a>");
              }
              else
              {
                if(isPrincipal)
                {
                  out.println("<a href='addSchoolInitative.html?sid="+ sch[i].getSchoolID() +"&iid="+ inits[k].getId() +"'>Add</a>");
                }
                else
                {
                  out.println("&nbsp;&nbsp;&nbsp;");
                }
              }
              
              out.println("</td>");
            }
              
          out.println("</tr>");
        }
        out.println("</table>");
      out.println("</div>");
    }
    catch(Exception e)
    {
      e.printStackTrace();
      throw new JspException(e.getMessage());
    }

    return SKIP_BODY;
  }
}