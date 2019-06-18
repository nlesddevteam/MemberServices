package com.esdnl.personnel.v2.site.availability.handler;

import com.awsd.security.*;
import com.awsd.security.SecurityException;
import com.awsd.servlet.*;
import com.esdnl.util.*;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.esdnl.personnel.v2.database.sds.*;
import com.esdnl.personnel.v2.model.sds.bean.*;
import com.esdnl.personnel.v2.model.availability.bean.*;
import com.esdnl.personnel.v2.database.availability.*;

public class EmployeeBookedRequestHandler  implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    String path;
    HttpSession session = null;
    User usr = null;
    
    
    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("PERSONNEL-SUBSTITUTES-STUDASS-VIEW")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    else
    {
      throw new SecurityException("User login required.");
    }
    
    try
    {
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy hh:mm:a");
      
      String op = request.getParameter("op");
      String id = request.getParameter("id");
      String msg = null;
      
      if(!StringUtils.isEmpty(op))
      {
        Date start_date = sdf.parse(request.getParameter("start_date"));
        Date end_date = sdf.parse(request.getParameter("end_date"));
        int shour = Integer.parseInt(request.getParameter("shour"));
        int sminute = Integer.parseInt(request.getParameter("sminute"));
        int sAMPM = Integer.parseInt(request.getParameter("sAMPM"));
        int fhour = Integer.parseInt(request.getParameter("fhour"));
        int fminute = Integer.parseInt(request.getParameter("fminute"));
        int fAMPM = Integer.parseInt(request.getParameter("fAMPM"));
      
        EmployeeBean emp = EmployeeManager.getEmployeeBean(id);
        
        EmployeeAvailabilityBean bean = new EmployeeAvailabilityBean();
        
        bean.setSIN(emp.getSIN());
        bean.setBookedBy(usr.getPersonnel().getPersonnelID());
        bean.setBookedWhere(usr.getPersonnel().getSchool().getSchoolID());
        
        Calendar s_cal = Calendar.getInstance();
        s_cal.clear();
        s_cal.setTime(start_date);
        s_cal.set(Calendar.HOUR, shour);
        s_cal.set(Calendar.MINUTE, sminute);
        s_cal.set(Calendar.AM_PM, sAMPM);
        start_date = s_cal.getTime();
        
        Calendar e_cal = Calendar.getInstance();
        e_cal.clear();
        e_cal.setTime(end_date);
        e_cal.set(Calendar.HOUR, fhour);
        e_cal.set(Calendar.MINUTE, fminute);
        e_cal.set(Calendar.AM_PM, fAMPM);
        
        Calendar tmp_cal = Calendar.getInstance();
        tmp_cal.clear();
        tmp_cal.setTime(e_cal.getTime());
        
        while(!s_cal.after(e_cal))
        {
          bean.setStartDate(s_cal.getTime());
          
          tmp_cal.set(s_cal.get(Calendar.YEAR), s_cal.get(Calendar.MONTH), s_cal.get(Calendar.DATE));
          bean.setEndDate(tmp_cal.getTime());
          
          try
          {
            EmployeeAvailabilityManager.addEmployeeAvailabilityBean(bean);
          }
          catch(EmployeeException e)
          {
            msg = (String)request.getAttribute("msg");
            
            if(msg == null)
              msg = "Date Time Conflicts:<br>";
            
            msg += sdf2.format(bean.getStartDate()) + " - " + sdf2.format(bean.getEndDate()) + "<br>";
              
            request.setAttribute("msg", msg);
          }
          
          s_cal.add(Calendar.DATE, 1);
        }
        
        if(msg == null){
          request.setAttribute("RELOAD_PARENT", "TRUE");
          request.setAttribute("view_date", start_date);
        }
        
        request.setAttribute("EMPLOYEE", emp);
      }
      else
      {
        request.setAttribute("EMPLOYEE", EmployeeManager.getEmployeeBean(id));
      }
      
      path = "book_employee.jsp";
    }
    catch(Exception e)
    {
      e.printStackTrace();
      request.setAttribute("msg", e.getMessage());
      path = "availability_list.jsp";
    }
    
    return path;
  }
}