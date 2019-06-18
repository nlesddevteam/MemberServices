package com.esdnl.nicep.tag;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import com.esdnl.util.StringUtils;
import com.esdnl.nicep.beans.*;

public class AgencyDemographicsTagHandler extends TagSupport
{
  private String id;
  private String cls;
  private String style;
  private String action;
  private String field;
  
  public AgencyDemographicsTagHandler()
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
    AgencyDemographicsBean agency = null;
    try
    {    
      out = pageContext.getOut();
      
      agency = (AgencyDemographicsBean) pageContext.getAttribute("AGENCYBEAN", PageContext.REQUEST_SCOPE);
      
      
      if(StringUtils.isEmpty(this.action) || StringUtils.isEqual(this.action, "display"))
      {
        actionDisplay(out, agency);
      }
    }
    catch(IOException e)
    {
      e.printStackTrace();
      throw new JspException(e.getMessage());
    }

    return SKIP_BODY;
  }
  
  private void actionDisplay(JspWriter out, AgencyDemographicsBean value) throws IOException
  {
    if(value != null)
    {
      if(StringUtils.isEqual(this.field, "id"))
      {
        out.println(value.getAgencyId());
      }
      else if(StringUtils.isEqual(this.field, "name"))
      {
        out.println(value.getName());
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
        out.println(value.getAddress1() + (!StringUtils.isEmpty(value.getAddress2())?"<BR>" + value.getAddress2():""));
      }
      else if(StringUtils.isEqual(this.field, "city_town"))
      {
        out.println(value.getCityTown());
      }
      else if(StringUtils.isEqual(this.field, "province_state"))
      {
        out.println(value.getProvinceState());
      }
      else if(StringUtils.isEqual(this.field, "country"))
      {
        out.println(value.getCountry());
      }
      else if(StringUtils.isEqual(this.field, "zipcode"))
      {
        out.println(value.getZipcode());
      }
    }
  }
}