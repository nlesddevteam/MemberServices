package com.awsd.strike;

import com.awsd.school.*;

import java.util.*;


public class DailySchoolStrikeInfo 
{
  private int info_id;
  private int school_id;
  private School school = null;
  private int num_picketers;
  private String picket_line_incidents;
  private String student_attendance;
  private String essential_workers_names;
  private String essential_workers_issues;
  private String transportation_issues;
  private String irregular_occurrences;
  private Date info_date;
  private String info_last_updated;
  private String building_safty_sanitation_issues;
  private String student_support_services_issues;
  
  
  public DailySchoolStrikeInfo(int info_id,
                               int school_id,
                               Date info_date,
                               String info_last_updated,
                               int num_picketers,
                               String picket_line_incidents,
                               String student_attendance,
                               String essential_workers_names,
                               String essential_workers_issues,
                               String transportation_issues,
                               String irregular_occurrences,
                               String building_safty_sanitation_issues,
                               String student_support_services_issues)
  {
    this.info_id = info_id;
    this.school_id = school_id;
    this.info_date = info_date;
    this.info_last_updated = info_last_updated;
    this.num_picketers = num_picketers;
    this.picket_line_incidents = picket_line_incidents;
    this.student_attendance = student_attendance;
    this.essential_workers_names = essential_workers_names;
    this.essential_workers_issues = essential_workers_issues;
    this.transportation_issues = transportation_issues;
    this.irregular_occurrences = irregular_occurrences;
    this.building_safty_sanitation_issues = building_safty_sanitation_issues;
    this.student_support_services_issues = student_support_services_issues;
  }

  public DailySchoolStrikeInfo(int school_id,
                               int num_picketers,
                               String picket_line_incidents,
                               String student_attendance,
                               String essential_workers_names,
                               String essential_workers_issues,
                               String transportation_issues,
                               String irregular_occurrences,
                               String building_safty_sanitation_issues,
                               String student_support_services_issues)
  {
    this(-1, 
         school_id, 
         null, 
         null, 
         num_picketers, 
         picket_line_incidents,
         student_attendance,
         essential_workers_names,
         essential_workers_issues,
         transportation_issues,
         irregular_occurrences,
         building_safty_sanitation_issues,
         student_support_services_issues);
  }

  public DailySchoolStrikeInfo(int info_id,
                               School school,
                               Date info_date,
                               String info_last_updated,
                               int num_picketers,
                               String picket_line_incidents,
                               String student_attendance,
                               String essential_workers_names,
                               String essential_workers_issues,
                               String transportation_issues,
                               String irregular_occurrences,
                               String building_safty_sanitation_issues,
                               String student_support_services_issues)
  {
    this(info_id, 
         school.getSchoolID(), 
         info_date, 
         info_last_updated, 
         num_picketers, 
         picket_line_incidents,
         student_attendance,
         essential_workers_names,
         essential_workers_issues,
         transportation_issues,
         irregular_occurrences,
         building_safty_sanitation_issues,
         student_support_services_issues);
         
    this.school = school;
  }

  public int getInfoID()
  {
    return this.info_id;
  }

  public int getSchoolID()
  {
    return this.school_id;
  }

  public School getSchool() throws SchoolException
  {
    if(school == null)
    {
      school = SchoolDB.getSchool(school_id);
    }

    return school;
  }

  public Date getInfoDate()
  {
    return this.info_date;
  }

  public String getInfoLastUpdated()
  {
    return this.info_last_updated;
  }

  public int getNumberPicketers()
  {
    return this.num_picketers;
  }

  public void setNumberPicketers(int num_picketers)
  {
    this.num_picketers = num_picketers;
  }

  public String getPicketLineIncidences()
  {
    return this.picket_line_incidents;
  }

  public void setPicketLineIncidences(String picket_line_incidents)
  {
    this.picket_line_incidents = picket_line_incidents;
  }

  public String getStudentAttendance()
  {
    return this.student_attendance;
  }

  public void setStudentAttendance(String student_attendance)
  {
    this.student_attendance=student_attendance;
  }

  public String getEssentialWorkersNames()
  {
    return this.essential_workers_names;
  }

  public void setEssentialWorkersNames(String essential_workers_names)
  {
    this.essential_workers_names=essential_workers_names;
  }

  public String getEssentialWorkersIssues()
  {
    return this.essential_workers_issues;
  }

  public void setEssentialWorkersIssues(String essential_workers_issues)
  {
    this.essential_workers_issues=essential_workers_issues;
  }

  public String getTransportationIssues()
  {
    return this.transportation_issues;
  }

  public void setTransportationIssues(String transportation_issues)
  {
    this.transportation_issues=transportation_issues;
  }

  public String getIrregularOccurrences()
  {
    return this.irregular_occurrences;
  }

  public void setIrregularOccurrences(String irregular_occurrences)
  {
    this.irregular_occurrences=irregular_occurrences;
  }

  public String getBuildingSaftySanitationIssues()
  {
    return this.building_safty_sanitation_issues;
  }

  public void setBuildingSaftySanitationIssues(String building_safty_sanitation_issues)
  {
    this.building_safty_sanitation_issues=building_safty_sanitation_issues;
  }

  public String getStudentSupportServicesIssues()
  {
    return this.student_support_services_issues;
  }

  public void setStudentSupportServicesIssues(String student_support_services_issues)
  {
    this.student_support_services_issues=student_support_services_issues;
  }
}