package com.esdnl.personnel.v2.site.tag;


import com.esdnl.util.StringUtils;
import java.io.*;

import java.text.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

import com.esdnl.personnel.v2.database.sds.*;
import com.esdnl.personnel.v2.database.availability.*;
import com.esdnl.personnel.v2.model.sds.bean.*;
import com.esdnl.personnel.v2.model.sds.constant.*;
import com.esdnl.personnel.v2.model.availability.bean.*;

import com.esdnl.util.*;

public class EmployeeCardTagHandler extends TagSupport
{
  private String id;
  private boolean showAvailability;
    
  public void setId(String id)
  {
    this.id = id;
  }
  
  public void setShowAvailability(boolean showAvailability)
  {
    this.showAvailability = showAvailability;
  }
  
  public int doStartTag() throws JspException
  {
    JspWriter out = null;
    EmployeeBean emp = null;
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    
    try
    {    
      out = pageContext.getOut();
      
      emp = EmployeeManager.getEmployeeBean(this.id);
      
      out.println("<TABLE width='100%' cellpadding='0' cellspacing='0'>");
      
          out.println("<TR><TD width='100%' style='padding:5px;'>");
            out.println("<TABLE width='100%' cellpadding='0' cellspacing='0' class='EmployeeCard'>");
              out.println("<TR><TD width='100%' class='Header'>" + emp.getFullnameReverse() + "</TD></TR>");
              out.println("<TR><TD width='100%' class='Body'>");
                out.println("<TABLE width='100%' cellpadding='0' cellspacing='0'>");
                  out.println("<TR>");
                    out.println("<TD width='50%' valign='top' style='padding-top:5px;'>");
                      out.println("<TABLE width='100%' cellpadding='0' cellspacing='0'>");
                        out.println("<TR><TD width='30%' class='Label'>SIN:</TD><TD width='*' class='Content'>" + emp.getSIN() + "</TD></TR>");
                        out.println("<TR><TD width='30%' class='Label'>Address:</TD><TD width='*' class='Content'>" + emp.getAddress1() + "</TD></TR>");
                        if(!StringUtils.isEmpty(emp.getAddress2()))
                          out.println("<TR><TD width='30%' class='Label'>&nbsp;</TD><TD width='*' class='Content'>" + emp.getAddress2() + "</TD></TR>");
                        out.println("<TR><TD width='30%' class='Label'>&nbsp;</TD><TD width='*' class='Content'>" + emp.getCity() + ", " + emp.getProvince() + "</TD></TR>");
                        out.println("<TR><TD width='30%' class='Label'>&nbsp;</TD><TD width='*' class='Content'>" + emp.getPostalCode() + "</TD></TR>");
                      out.println("</TABLE>");
                    out.println("</TD>");
                    out.println("<TD width='50%' valign='top' style='padding-top:5px;'>");
                      out.println("<TABLE width='100%' cellpadding='0' cellspacing='0'>");
                        out.println("<TR><TD width='40%' class='Label'>Phone:</TD><TD width='*' class='Content'>" + emp.getPhone() + "</TD></TR>");
                        if(!StringUtils.isEmpty(emp.getAlternatePhone()))
                          out.println("<TR><TD width='40%' class='Label'>&nbsp;</TD><TD width='*' class='Content'>" + emp.getAlternatePhone() + "</TD></TR>");
                        out.println("<TR><TD width='40%' class='Label'>Seniority Date:</TD><TD width='*' class='Content'>" + ((emp.getSeniorityDate() != null)?sdf.format(emp.getSeniorityDate()):"<SPAN style='color:#FF0000;'>UNKNOWN</SPAN>") + "</TD></TR>");
                      out.println("</TABLE>");
                    out.println("</TD>");
                  out.println("</TR>");

                  if(this.showAvailability)
                  {
                    EmployeeAvailabilityBean[] abeans = EmployeeAvailabilityManager.getCurrentEmployeeAvailabilityBean(emp.getSIN());
                    out.println("<TR>");
                      out.println("<TD colspan='2' valign='top' style='padding-top:5px;'>");
                        out.println("<TABLE width='100%' cellpadding='0' cellspacing='0'>");
                          out.println("<TR><TD width='100%' class='Label'>Availability:</TD></TR>");
                          if(abeans.length > 0)
                          {
                            for(int i=0; i < abeans.length; i++)
                            {
                              out.println("<TR><TD width='100%' style='padding:5px;'>");
                                out.println("<TABLE width='100%' cellpadding='0' cellspacing='0' style='border:solid 1px #e0e0e0;'>");
                                  out.println("<TR><TD width='30%' valign='top'>");
                                    out.println("<TABLE width='100%' cellpadding='0' cellspacing='0'>");
                                      out.println("<TR><TD width='60' class='Label'>Start Date:</TD><TD width='*' class='Content'>" + sdf.format(abeans[i].getStartDate()) + "</TD></TR>");
                                      out.println("<TR><TD width='60' class='Label'>End Date:</TD><TD width='*' class='Content'>" + sdf.format(abeans[i].getEndDate()) + "</TD></TR>");
                                    out.println("</TABLE>");    
                                  out.println("</TD><TD width='*' align='left' valign='top'>");
                                    out.println("<TABLE width='100%' cellpadding='0' cellspacing='0'>");
                                      if(abeans[i].getBookedById() > 0)
                                        out.println("<TR><TD width='100' class='Label'>Booked By:</TD><TD width='*' class='Content'>" + abeans[i].getBookedBy().getFullName() + "</TD></TR>");
                                      if(abeans[i].getBookedWhereId() > 0)
                                        out.println("<TR><TD width='100' class='Label'>Booked Where:</TD><TD width='*' class='Content'>" + abeans[i].getBookedWhere().getSchoolName() + "</TD></TR>");
                                      if(abeans[i].getNotAvailableById() > 0)
                                        out.println("<TR><TD width='100' class='Label'>Not Available By:</TD><TD width='*' class='Content'>" + abeans[i].getNotAvailableBy().getFullName() + "</TD></TR>");
                                      if(!StringUtils.isEmpty(abeans[i].getReason()))
                                        out.println("<TR><TD width='100' class='Label'>Reason:</TD><TD width='*' class='Content'>" + abeans[i].getReason() + "</TD></TR>");
                                    out.println("</TABLE>");
                                  out.println("</TD></TR>");
                                  out.println("<TR><TD colspan='2' class='Operations'>");
                                    out.println("<a href='deleteAvailability.html?sin="+ emp.getSIN() +"&dt=" + sdf.format(abeans[i].getStartDate()) + "'>Delete</a>");
                                  out.println("</TD></TR>");
                                out.println("</TABLE>");
                              out.println("</TD></TR>");
                            }
                          }
                          else
                            out.println("<TR><TD width='100%' class='Content' style='padding-left:5px;'>No bookings on file.</TD></TR>");
                        out.println("</TABLE>");
                      out.println("</TD>");
                    out.println("</TR>");
                  }
                out.println("</TABLE>");
              out.println("</TD></TR>");
              out.println("<TR><TD width='100%' class='Operations'>");
                out.println("<a href='change_availability.jsp?sin="+ emp.getSIN() +"'>Change Availability</a>");
              out.println("</TD></TR>");
            out.println("</TABLE>"); 
          out.println("</TD><TR>");
      
      out.println("</TABLE>"); 
    }
    
    catch(EmployeeException e)
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