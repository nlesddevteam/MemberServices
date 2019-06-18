package com.esdnl.roer;

import com.awsd.personnel.*;

import java.sql.*;

import java.util.*;
import java.util.Date;


public class ROERequest 
{
  private int request_id;
  private int requested_by_id;
  private Personnel requested_by;
  private Date request_date;
  private Date complete_date;
  private Date first_day_worked;
  private Date last_day_worked;
  private double week1_hours;
  private double week2_hours;
  private double week3_hours;
  private double week4_hours;
  private String reason_for_record;
  private Date baby_born_date;
  private Date replacement_start_date;
  private Date replacement_finish_date;
  private Date last_record_issued_date;
  
  private Vector sick_leave_dates = null;
  private Vector unpaid_dates = null;
  
  public ROERequest(int request_id, Personnel requested_by, Date request_date,
    Date complete_date, Date first_day_worked, Date last_day_worked, double week1_hours,
    double week2_hours, double week3_hours, double week4_hours, String reason_for_record,
    Date baby_born_date, Date replacement_start_date, Date replacement_finish_date, 
    Date last_record_issued_date)
  {
    this.request_id = request_id;
    this.requested_by = requested_by;
    this.requested_by_id = requested_by.getPersonnelID();
    this.request_date = request_date;
    this.complete_date = complete_date;
    this.first_day_worked = first_day_worked;
    this.last_day_worked = last_day_worked;
    this.week1_hours = week1_hours;
    this.week2_hours = week2_hours;
    this.week3_hours = week3_hours;
    this.week4_hours = week4_hours;
    this.reason_for_record = reason_for_record;
    this.baby_born_date = baby_born_date;
    this.replacement_start_date = replacement_start_date;
    this.replacement_finish_date = replacement_finish_date;
    this.last_record_issued_date = last_record_issued_date;
    
    this.sick_leave_dates = null;
    this.unpaid_dates = null;
  }
  
  public ROERequest(int request_id, int requested_by_id, Date request_date,
    Date complete_date, Date first_day_worked, Date last_day_worked, double week1_hours,
    double week2_hours, double week3_hours, double week4_hours, String reason_for_record,
    Date baby_born_date, Date replacement_start_date, Date replacement_finish_date, 
    Date last_record_issued_date)
  {
    this.request_id = request_id;
    this.requested_by = null;
    this.requested_by_id = requested_by_id;
    this.request_date = request_date;
    this.complete_date = complete_date;
    this.first_day_worked = first_day_worked;
    this.last_day_worked = last_day_worked;
    this.week1_hours = week1_hours;
    this.week2_hours = week2_hours;
    this.week3_hours = week3_hours;
    this.week4_hours = week4_hours;
    this.reason_for_record = reason_for_record;
    this.baby_born_date = baby_born_date;
    this.replacement_start_date = replacement_start_date;
    this.replacement_finish_date = replacement_finish_date;
    this.last_record_issued_date = last_record_issued_date;
    
    this.sick_leave_dates = null;
    this.unpaid_dates = null;
  }
  
  public ROERequest(Personnel requested_by, Date request_date,
    Date complete_date, Date first_day_worked, Date last_day_worked, double week1_hours,
    double week2_hours, double week3_hours, double week4_hours, String reason_for_record,
    Date baby_born_date, Date replacement_start_date, Date replacement_finish_date, 
    Date last_record_issued_date)
  {
    this(-1, requested_by, request_date, complete_date, first_day_worked, last_day_worked,
      week1_hours, week2_hours, week3_hours, week4_hours, reason_for_record, baby_born_date, 
      replacement_start_date, replacement_finish_date, last_record_issued_date);
      
      this.sick_leave_dates = null;
      this.unpaid_dates = null;
  }
  
  
  public int getRequestID()
  {
    return this.request_id;
  }
  
  public int getPersonnelID()
  {
    return this.requested_by_id;
  }
  
  public Personnel getPersonnel() throws PersonnelException
  {
    if(requested_by == null)
    {
      requested_by = PersonnelDB.getPersonnel(this.requested_by_id);
    }
    
    return this.requested_by;
  }
  
  public Date getRequestDate()
  {
    return this.request_date;
  }
  
  public Date getCompleteDate()
  {
    return this.complete_date;
  }
  
  public Date getFirstDayWorkedDate()
  {
    return this.first_day_worked;
  }
  
  public Date getLastDayWorkedDate()
  {
    return this.last_day_worked;
  }
  
  public double getWeekOneHoursWorked()
  {
    return this.week1_hours;
  }
  
  public double getWeekTwoHoursWorked()
  {
    return this.week2_hours;
  }
  
   public double getWeekThreeHoursWorked()
  {
    return this.week3_hours;
  }
  
   public double getWeekFourHoursWorked()
  {
    return this.week4_hours;
  }
  
  public String getReasonForRecordRequest()
  {
    return this.reason_for_record;
  }
  
  public Date getBabyBirthDate()
  {
    return this.baby_born_date;
  }
  
  public Date getReplacementStartDate()
  {
    return this.replacement_start_date;
  }
  
  public Date getReplacementFinishDate()
  {
    return this.replacement_finish_date;
  }
  
  public Date getLastRecordIssuedDate()
  {
    return this.last_record_issued_date;
  }
  
  public Vector getSickLeaveDates() throws SQLException
  {
    if(sick_leave_dates == null)
    {
      this.sick_leave_dates = ROERequestDB.getROERequestSickDates(this);
    }
    return this.sick_leave_dates;
  }
  
  public void setSickLeaveDates(Vector v)
  {
    this.sick_leave_dates = v;
  }
  
  public Vector getUnpaidDates() throws SQLException
  {
    if(this.unpaid_dates == null)
    {
      this.unpaid_dates = ROERequestDB.getROERequestUnpaidDates(this);
    }
    return this.unpaid_dates;
  }
  
  public void setUnpaidDates(Vector v)
  {
    this.unpaid_dates = v;
  }
}