package com.esdnl.sca.site.handler;

import java.io.*;
import java.util.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.awsd.security.*;import com.awsd.security.SecurityException;
import com.awsd.school.*;

import com.esdnl.servlet.*;
import com.esdnl.util.*;
import com.esdnl.sca.dao.*;
import com.esdnl.sca.model.bean.*;

public class AddToWaitlistRequestHandler extends RequestHandlerImpl
{
  public AddToWaitlistRequestHandler()
  {
    requiredPermissions = new String[]{"SCA-ADMIN-VIEW","SCA-PRINCIPAL-VIEW"};
  }
  
  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  { 
    super.handleRequest(request, response);
    
    path = "school_waitlist_add.jsp";
    
    if(StringUtils.isEqual(form.get("op"), "ADD"))
    {
      validator = new FormValidator(new FormElement[]{
        new RequiredFormElement("sid"),
        new RequiredFormElement("student_name"),
        new RequiredFormElement("student_mcp"),
        new RequiredFormElement("student_grade"),
        new RequiredMultiSelectionFormElement("referral_reason", -1),
        new RequiredFormElement("current_pathway"),
        new RequiredMultiSelectionFormElement("assessment_types", -1),
        new RequiredPatternFormElement("prev_assessment", FormElementPattern.SHORT_DATE_PATTERN)
      });
      
      if(validate_form())
      {
        try
        {
        
          School s = SchoolDB.getSchool(Integer.parseInt(form.get("sid")));
          
          Assessment a = new Assessment();
          
          a.setAdder(usr.getPersonnel());
          a.setCurrentPathway(Pathway.get(Integer.parseInt(form.get("current_pathway"))));
          a.setGrade(GradeDB.getGrade(Integer.parseInt(form.get("student_grade"))));
          if(!StringUtils.isEmpty(form.get("prev_assessment")))
            a.setPreviousAssessmentDate(new SimpleDateFormat("MM/yyyy").parse(form.get("prev_assessment")));
          a.setReferralDate(Calendar.getInstance().getTime());
          a.setSchool(SchoolDB.getSchool(Integer.parseInt(form.get("sid"))));
          a.setStatus(AssessmentStatus.NOT_YET_BEGUN);
          a.setStudentMCP(form.get("student_mcp"));
          a.setStudentName(form.get("student_name"));
          a.setTests(SCAManager.getAssessmentTypeBeans(form.getArray("assessment_types")));
          a.setReasons(SCAManager.getReferralReasonBeans(form.getArray("referral_reason")));
          
          SCAManager.addAssessment(a);
          
          request.setAttribute("SCHOOLBEAN", s);
          request.setAttribute("msg", "Added assessment to waitlist successfully.");
        }
        catch(Exception e)
        {
          e.printStackTrace(System.err);
          
          request.setAttribute("FORM", form);
          request.setAttribute("msg", "Could not add assessment waitlist.");
        }
      }
      else
      {
        request.setAttribute("FORM", form);
        request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));
      }
    }
    else
    {
      validator = new FormValidator(new FormElement[]{
        new RequiredFormElement("sid")
      });
      
      if(validate_form())
      {
        request.setAttribute("SCHOOLBEAN", SchoolDB.getSchool(Integer.parseInt(form.get("sid"))));
      }
      else
      {
        request.setAttribute("FORM", form);
        request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));
      }
    }
    
    
    return path;
  }
}