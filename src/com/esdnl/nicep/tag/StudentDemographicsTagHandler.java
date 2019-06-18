package com.esdnl.nicep.tag;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import com.esdnl.util.StringUtils;
import com.esdnl.nicep.beans.*;

public class StudentDemographicsTagHandler extends TagSupport
{
  private String id;
  private String cls;
  private String style;
  private String action;
  private String field;
  
  public StudentDemographicsTagHandler()
  {
    this.id = null;
    this.cls = null;
    this.style = null;
    this.action = "display";
    this.field = null;
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
  
  public void setAction(String action)
  {
    this.action = action;
  }
  
  public void setField(String field)
  {
    this.field = field;
  }
  
  public int doStartTag() throws JspException
  {
    JspWriter out = null;
    StudentDemographicsBean student = null;
    
    try
    {    
      out = pageContext.getOut();
      
      student = (StudentDemographicsBean) pageContext.getAttribute("STUDENTBEAN", PageContext.REQUEST_SCOPE);
      
      
      if(StringUtils.isEmpty(this.action) || StringUtils.isEqual(this.action, "display"))
      {
        actionDisplay(out, student);
      }
    }
    catch(IOException e)
    {
      e.printStackTrace();
      throw new JspException(e.getMessage());
    }

    return SKIP_BODY;
  }
  
  private void actionDisplay(JspWriter out, StudentDemographicsBean value) throws IOException
  {
    if(value != null)
    {
      if(StringUtils.isEqual(this.field, "id"))
      {
        out.println(value.getStudentId());
      }
      else if(StringUtils.isEqual(this.field, "fullname"))
      {
        out.println(value.getFullname());
      }
      else if(StringUtils.isEqual(this.field, "firstname"))
      {
        out.println(value.getFirstname());
      }
      else if(StringUtils.isEqual(this.field, "lastname"))
      {
        out.println(value.getLastname());
      }
      else if(StringUtils.isEqual(this.field, "dob"))
      {
        out.println(value.getFormattedDateOfBirth());
      }
      else if(StringUtils.isEqual(this.field, "address1"))
      {
        out.println(value.getAddress1());
      }
      else if(StringUtils.isEqual(this.field, "address2"))
      {
        out.println(value.getAddress2());
      }
      else if(StringUtils.isEqual(this.field, "address"))
      {
        out.println(StringUtils.encodeHTML(value.getAddress()));
      }
      else if(StringUtils.isEqual(this.field, "city_town"))
      {
        out.println(value.getCityTown());
      }
      else if(StringUtils.isEqual(this.field, "province_state"))
      {
        out.println(value.getStateProvince());
      }
      else if(StringUtils.isEqual(this.field, "country"))
      {
        out.println(value.getCountry());
      }
      else if(StringUtils.isEqual(this.field, "zipcode"))
      {
        out.println(value.getZipcode());
      }
      else if(StringUtils.isEqual(this.field, "phone1"))
      {
        out.println(value.getPhone1());
      }
      else if(StringUtils.isEqual(this.field, "phone2"))
      {
        out.println(!StringUtils.isEmpty(value.getPhone2())?value.getPhone2():"");
      }
      else if(StringUtils.isEqual(this.field, "phones"))
      {
        out.println(value.getPhone1());
        out.println(!StringUtils.isEmpty(value.getPhone2())?"<BR>"+value.getPhone2():"");
      }
      else if(StringUtils.isEqual(this.field, "email"))
      {
        out.println(!StringUtils.isEmpty(value.getEmail())?value.getEmail():"");
      }
      else if(StringUtils.isEqual(this.field, "emaillink"))
      {
        out.println(!StringUtils.isEmpty(value.getEmail())?"<a href='mailto:"+value.getEmail()+"'>"+value.getEmail()+"</a>":"");
      }
    }
  }
}