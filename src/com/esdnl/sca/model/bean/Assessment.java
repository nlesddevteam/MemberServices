package com.esdnl.sca.model.bean;

import com.awsd.school.*;
import com.awsd.personnel.*;
import java.util.Date;
import java.text.*;

public class Assessment 
{
  private int id;
  private String student_name;
  private String student_mcp;
  private Grade grade;
  private Date referral_date;
  private ReferralReason[] reasons;
  private Pathway current_pathway;
  private Date previous_assessment_date;
  private AssessmentStatus status;
  private Date start_date;
  private Date completed_date;
  private AssessmentType[] tests;
  private School school;
  private Personnel adder;
  
  public Assessment()
  {
  }
  
  public void setId(int id)
  {
    this.id = id;
  }
  
  public int getId()
  {
    return this.id;
  }
  
  public void setStudentName(String student_name)
  {
    this.student_name = student_name;
  }
  
  public String getStudentName()
  {
    return student_name;
  }
  
  public void setStudentMCP(String student_mcp)
  {
    this.student_mcp = student_mcp;
  }
  
  public String getStudentMCP()
  {
    return this.student_mcp;
  }
  
  public void setGrade(Grade grade)
  {
    this.grade = grade;
  }
  
  public Grade getGrade()
  {
    return this.grade;
  }
  
  public void setReferralDate(Date referral_date)
  {
    this.referral_date = referral_date;
  }
  
  public Date getReferralDate()
  {
    return referral_date;
  }
  
  public void setCurrentPathway(Pathway current_pathway)
  {
    this.current_pathway = current_pathway;
  }
  
  public Pathway getCurrentPathway()
  {
    return this.current_pathway;
  }
  
  public void setPreviousAssessmentDate(Date previous_assessment_date)
  {
    this.previous_assessment_date = previous_assessment_date;
  }
  
  public Date getPreviousAssessmentDate()
  {
    return this.previous_assessment_date;
  }
  
  public String getPreviousAssessmentDateFormatted()
  {
    return (new SimpleDateFormat("MM/yyyy")).format(this.previous_assessment_date);
  }
  
  public void setStatus(AssessmentStatus status)
  {
    this.status = status;
  }
  
  public AssessmentStatus getStatus()
  {
    return this.status;
  }
  
  public void setStartDate(Date start_date)
  {
    this.start_date = start_date;
  }
  
  public Date getStartDate()
  {
    return this.start_date;
  }
  
  public void setCompletedDate(Date completed_date)
  {
    this.completed_date = completed_date;
  }
  
  public Date getCompletedDate()
  {
    return this.completed_date;
  }
  
  public void setTests(AssessmentType[] tests)
  {
    this.tests = tests;
  }
  
  public AssessmentType[] getTests()
  {
    return this.tests;
  }
  
  public void setReasons(ReferralReason[] reasons)
  {
    this.reasons = reasons;
  }
  
  public ReferralReason[] getReasons()
  {
    return this.reasons;
  }
  
  public void setSchool(School school)
  {
    this.school = school;
  }
  
  public School getSchool()
  {
    return this.school;
  }
  
  public void setAdder(Personnel adder)
  {
    this.adder = adder;
  }
  
  public Personnel getAdder()
  {
    return this.adder;
  }
}