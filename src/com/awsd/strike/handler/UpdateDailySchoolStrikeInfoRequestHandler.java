package com.awsd.strike.handler;

import com.awsd.school.*;
import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.servlet.*;
import com.awsd.strike.*;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class UpdateDailySchoolStrikeInfoRequestHandler  implements RequestHandler
{
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    HttpSession session = null;
    User usr = null;
    String path = "";
    School school = null;
    SchoolStrikeGroup group = null;
    DailySchoolStrikeInfo info = null;
    int picketers = 0;

    session = request.getSession(false);
    if((session != null) && (session.getAttribute("usr") != null))
    {
      usr = (User) session.getAttribute("usr");
      if(!(usr.getUserPermissions().containsKey("STRIKECENTRAL-PRINCIPAL-ADMINVIEW")))
      {
        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
      }
    }
    else
    {
      throw new SecurityException("User login required.");
    }

    school = usr.getPersonnel().getSchool();
    if(school != null)
      request.setAttribute("School", school);

    group = school.getSchoolStrikeGroup();
    if(group != null)
      request.setAttribute("SchoolStrikeGroup", group);

    if(request.getParameter("picketers") != null)
    {
      try
      {
        picketers = Integer.parseInt(request.getParameter("picketers"));
      }
      catch(NumberFormatException e)
      {
        picketers = 0;
      }      
    }
    else
    {
      picketers = 0;
    }
    
    info = DailySchoolStrikeInfoDB.getDailySchoolStrikeInfo(usr.getPersonnel().getSchool());    
    if(info != null)
    {
      info.setNumberPicketers(picketers);
      info.setPicketLineIncidences(request.getParameter("pket_line_incidents"));
      info.setStudentAttendance(request.getParameter("student_attd"));
      info.setStudentSupportServicesIssues(request.getParameter("stud_supp_services_issues"));
      info.setEssentialWorkersNames(request.getParameter("essential_workers"));
      info.setEssentialWorkersIssues(request.getParameter("essential_workers_issues"));
      info.setTransportationIssues(request.getParameter("transportation_issues"));
      info.setIrregularOccurrences(request.getParameter("irregular_occurrences"));
      info.setBuildingSaftySanitationIssues(request.getParameter("bldg_safy_sanitation_issues"));

      DailySchoolStrikeInfoDB.updateDailySchoolStrikeInfo(info);
    }
    else
    {
      info = new DailySchoolStrikeInfo(school.getSchoolID(), picketers,
                 request.getParameter("pket_line_incidents"),
                 request.getParameter("student_attd"),
                 request.getParameter("essential_workers"),
                 request.getParameter("essential_workers_issues"),
                 request.getParameter("transportation_issues"),
                 request.getParameter("irregular_occurrences"),
                 request.getParameter("bldg_safy_sanitation_issues"),
                 request.getParameter("stud_supp_services_issues"));
                 
      DailySchoolStrikeInfoDB.addDailySchoolStrikeInfo(info);
    }

    info = DailySchoolStrikeInfoDB.getDailySchoolStrikeInfo(school);

    request.setAttribute("DailySchoolStrikeInfo", info);

    path = "principaladmin.jsp";
    
    return path;
  }
}