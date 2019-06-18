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

public class AgencyContractTagHandler  extends TagSupport
{
  private String id;
  private String cls;
  private String style;
  private boolean current;
  
  public AgencyContractTagHandler()
  {
    this.id = null;
    this.cls = null;
    this.style = null;
    this.current = false;
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
  
  public  void setCurrent(boolean current)
  {
    this.current = current;
  }
  
  public int doStartTag() throws JspException
  {
    JspWriter out = null;
    AgencyDemographicsBean agency = null;
    AgencyContractBean[] contracts = null;
    
    try
    {    
      out = pageContext.getOut();
      
      agency = (AgencyDemographicsBean) pageContext.getAttribute("AGENCYBEAN", PageContext.REQUEST_SCOPE);
      
      if(current)
        contracts = AgencyContractManager.getAgencyCurrentContractBean(agency);
      
      out.println("<div id='AgencyCurrentContractTag'>");
      out.println("<table cellspacing='0' cellpadding='2' align='center'>");
      
      if(contracts != null)
      {
        if(contracts.length < 1)
        {
          out.println("<tr><td colspan='5' class='messageText'>No contracts on file.</td></tr>");
        }
        else if(contracts.length == 1)
        {
          out.println("<caption>Current Contract</caption>");
          out.println("<tr><th>Effective Date</th><td>"+ contracts[0].getFormatedEffectiveDate() + "</td></tr>");
          out.println("<tr><th>End Date</th><td>"+ contracts[0].getFormatedEndDate() + "</td></tr>");
          out.println("<tr><th>Contract Type</th><td>"+ contracts[0].getContractType().getDescription() + "</td></tr>");
          out.println("<tr><th>Contract Type Value</th><td>"+ contracts[0].getFormattedContractTypeValue() + "</td></tr>");
          if(!StringUtils.isEmpty(contracts[0].getFilename()))
            out.println("<tr><th>Contract Document</th><td><a href='agencies/contracts/"+ contracts[0].getFilename() + "' target='_blank'>view</a></td></tr>");
          else
            out.println("<tr><th>Contract Document:</th><td>Not Available</td></tr>");
          out.println("</tr><tr bgcolor='#f4f4f4'>");
              out.println("<td colspan='5' align='right'>");
              out.println("<input type='button' value='Edit' onclick=\"document.forms[0].action='editAgencyContract.html';document.forms[0].id.value='" + contracts[0].getContractId() + "';document.forms[0].submit();\">");
              out.println("<input type='button' value='Delete' onclick=\"document.forms[0].action='delAgencyContract.html';document.forms[0].id.value='" + contracts[0].getContractId() + "';document.forms[0].submit();\">");
            out.println("</td></tr>");
        }
        else
        {
          out.println("<tr><th width='20%'>Effective Date</th><th width='20%'>End Date</th><th width='30%'>Contract Type</th><th width='15%'>Value</th><th width='15%'>Document</th></tr>");
          for(int i=0; i < contracts.length; i++)
          {
            out.println("<tr>");
              out.println("\t<td>" + contracts[i].getFormatedEffectiveDate() + "</td>");
              out.println("\t<td>" + contracts[i].getFormatedEndDate() + "</td>");
              out.println("\t<td>" + contracts[i].getContractType().getDescription() + "</td>");
              out.println("\t<td>" + contracts[i].getFormattedContractTypeValue() + "</td>");
              if(!StringUtils.isEmpty(contracts[i].getFilename()))
                out.println("<td><a href='agencies/contracts/"+ contracts[i].getFilename() + "' target='_blank'>view</a></td>");
              else
                out.println("<td>N/A</td>");
            out.println("</tr><tr bgcolor='#f4f4f4'>");
              out.println("<td colspan='5' align='right'>");
              out.println("<input type='button' value='Edit' onclick=\"document.forms[0].action='editAgencyContract.html';document.forms[0].id.value='" + contracts[i].getContractId() + "';document.forms[0].submit();\">");
              out.println("<input type='button' value='Delete' onclick=\"document.forms[0].action='delAgencyContract.html';document.forms[0].id.value='" + contracts[i].getContractId() + "';document.forms[0].submit();\">");
            out.println("</td></tr>");
          }
        }
      }
      else
        out.println("<tr><td colspan='5' class='messageText'>No contracts on file.</td></tr>");
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
