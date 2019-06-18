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

public class AgencyContactsTagHandler  extends TagSupport
{
  private String id;
  private String cls;
  private String style;
  
  public AgencyContactsTagHandler()
  {
    this.id = null;
    this.cls = null;
    this.style = null;
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
  
  public int doStartTag() throws JspException
  {
    JspWriter out = null;
    AgencyDemographicsBean agency = null;
    AgencyContactBean[] contacts = null;
    
    try
    {    
      out = pageContext.getOut();
      
      agency = (AgencyDemographicsBean) pageContext.getAttribute("AGENCYBEAN", PageContext.REQUEST_SCOPE);
      contacts = AgencyContactManager.getAgencyContactBeans(agency);
      
      out.println("<div id='AgencyContactsTag'>");
      out.println("<table cellspacing='0' cellpadding='2'>");
      
      if(contacts.length > 0)
      {
        out.println("<tr><th width='25%'>Name</th><th width='15%'>Phone 1</th><th width='15%'>Phone 2</th><th width='15%'>Phone 3</th><th width='30%'>Email</th></tr>");
        for(int i=0; i < contacts.length; i++)
        {
          out.println("<tr>");
            out.println("\t<td>" + contacts[i].getName() + "</td>");
            out.println("\t<td>" + contacts[i].getPhone1() + "</td>");
            out.println("\t<td>" + (!StringUtils.isEmpty(contacts[i].getPhone2())?contacts[i].getPhone2():"&nbsp;") + "</td>");
            out.println("\t<td>" + (!StringUtils.isEmpty(contacts[i].getPhone3())?contacts[i].getPhone3():"&nbsp;") + "</td>");
            out.println("\t<td>" + (!StringUtils.isEmpty(contacts[i].getEmail())?"<a href='mailto:" + contacts[i].getEmail() + "'>" + contacts[i].getEmail() + "</a>" :"&nbsp;") + "</td>");
          out.println("</tr><tr bgcolor='#f4f4f4'>");
            out.println("<td colspan='5' align='right'>");
            out.println("<input type='button' value='Edit' onclick=\"document.forms[0].action='editAgencyContact.html';document.forms[0].id.value='" + contacts[i].getContactId() + "';document.forms[0].submit();\">");
            out.println("<input type='button' value='Delete' onclick=\"document.forms[0].action='delAgencyContact.html';document.forms[0].id.value='" + contacts[i].getContactId() + "';document.forms[0].submit();\">");
          out.println("</td></tr>");
        }
      }
      else
        out.println("<tr><td colspan='5' class='messageText'>No contacts on file.</td></tr>");
      out.println("</table>");
      out.println("</div>");
    }
    catch(NICEPException e)
    {
      e.printStackTrace();
      throw new JspException(e.getMessage());
    }
    catch(IOException e)
    {
      e.printStackTrace();
      throw new JspException(e.getMessage());
    }

    return SKIP_BODY;
  }
  
}