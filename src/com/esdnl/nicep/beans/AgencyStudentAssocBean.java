package com.esdnl.nicep.beans;

public class AgencyStudentAssocBean 
{
  private AgencyDemographicsBean agency;
  private StudentDemographicsBean student;
  
  public AgencyStudentAssocBean(AgencyDemographicsBean agency, StudentDemographicsBean student)
  {
    this.agency = agency;
    this.student = student;
  }
  
  public AgencyStudentAssocBean()
  {
    this(null, null);
  }
  
  public AgencyDemographicsBean getAgency()
  {
    return this.agency;
  }
  
  public void setAgency(AgencyDemographicsBean agency)
  {
    this.agency = agency;
  }
  
  public StudentDemographicsBean getStudent()
  {
    return this.student;
  }
  
  public void setStudent(StudentDemographicsBean student)
  {
    this.student = student;
  }
}