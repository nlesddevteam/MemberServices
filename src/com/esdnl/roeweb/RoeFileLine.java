package com.esdnl.roeweb;

import java.util.*;
import java.text.*;
import com.esdnl.util.*;

public class RoeFileLine 
{
  private static final String DATE_FORMAT = "yyyyMMdd";

  private String orig_line;                 //original line of data.
  
  private String nbr_sin;                   // 1-9
  private String nbr_district;              // 10-12
  private String nbr_school;                // 13-15
  private String copy_payment_type;         // 16
  private String date_pay_period;           // 17-24
  private String date_eff_begin;            // 25-32
  private String date_eff_end;              // 33-40
  private String full_daily_gross;          // 41-50
  private String amt_ei_insurable;          // 51-60
  private String qty_ei_insurable;          // 61-68
  private String count_weeks;               // 69-70
  private String ind_roe;                   // 71
  private String date_week_ending;          // 72-79
  private String amt_weekly_gross;          // 80-89
  private String qty_hrs_mon;               // 90-97
  private String qty_hrs_tue;               // 98-105
  private String qty_hrs_wed;               // 106-113
  private String qty_hrs_thu;               // 114-121
  private String qty_hrs_fri;               // 122-129
  private String nbr_cheque;                // 130-135
  private String job_code;                  // 136-137
  
  public static final int MIN_LINE_LENGTH = 135;
  
  public RoeFileLine(String orig_line) throws Exception
  {
    this.orig_line = orig_line;
    parseData();
  }
  
  public String getSIN()
  {
    return this.nbr_sin;
  }
  
  public Date getPayPeriodDate()
  {
    Date d = null;
    try
    {
      d = (new SimpleDateFormat(RoeFileLine.DATE_FORMAT)).parse(this.date_pay_period);
    }
    catch(ParseException e)
    {
      d = null;
    }
    
    return d;
  }
  
  public Date getPayPeriodBeginningDate()
  {
    Date d = null;
    try
    {
      d = (new SimpleDateFormat(RoeFileLine.DATE_FORMAT)).parse(this.date_eff_begin);
    }
    catch(ParseException e)
    {
      d = null;
    }
    
    return d;
  }
  
  public Date getPayPeriodEndingDate()
  {
    Date d = null;
    try
    {
      d = (new SimpleDateFormat(RoeFileLine.DATE_FORMAT)).parse(this.date_eff_end);
    }
    catch(ParseException e)
    {
      d = null;
    }
    
    return d;
  }
  
  public Date getWeekEndingDate()
  {
    Date d = null;
    try
    {
      if(!this.date_week_ending.replaceAll("0", "").equals(""))
        d = (new SimpleDateFormat(RoeFileLine.DATE_FORMAT)).parse(this.date_week_ending);
    }
    catch(ParseException e)
    {
      d = null;
    }
    
    return d;
  }
  
  public Date getWeekBeginningDate(){
  	Date d = null;
  	
  	if(getWeekEndingDate() != null){
  		Calendar cal = Calendar.getInstance();
  		cal.setTime(getWeekEndingDate());
  		cal.add(Calendar.DATE, -4);
  		
  		d = cal.getTime();
  	}
  	
  	return d;
  }
  
  public double getTotalBiWeeklyInsurableEarning()
  {
    return Double.parseDouble(this.amt_ei_insurable);
  }
  
  public double getTotalBiWeeklyInsurableHours()
  {
    return Double.parseDouble(this.qty_ei_insurable);
  }
  
  public double getWeeklyGross()
  {
    return Double.parseDouble(this.amt_weekly_gross);
  }
  
  public double getMondayHours()
  {
    double h = 0;
    
    try
    {
      h = Double.parseDouble(this.qty_hrs_mon);  
    }
    catch(NumberFormatException e)
    {
      h = 0;
    }
    
    return h;
  }
  
  public double getMondayGross(){
  	return getHourlyWage() * getMondayHours();
  }
  
  public double getTuesdayHours()
  {
    double h = 0;
    
    try
    {
      h = Double.parseDouble(this.qty_hrs_tue);  
    }
    catch(NumberFormatException e)
    {
      h = 0;
    }
    
    return h;
  }
  
  public double getTuesdayGross(){
  	return getHourlyWage() * getTuesdayHours();
  }
  
  public double getWednesdayHours()
  {
    double h = 0;
    
    try
    {
      h = Double.parseDouble(this.qty_hrs_wed);  
    }
    catch(NumberFormatException e)
    {
      h = 0;
    }
    
    return h;
  }
  
  public double getWednesdayGross(){
  	return getHourlyWage() * getWednesdayHours();
  }
  
  public double getThursdayHours()
  {
    double h = 0;
    
    try
    {
      h = Double.parseDouble(this.qty_hrs_thu);  
    }
    catch(NumberFormatException e)
    {
      h = 0;
    }
    
    return h;
  }
  
  public double getThursdayGross(){
  	return getHourlyWage() * getThursdayHours();
  }
  
  public double getFridayHours()
  {
    double h = 0;
    
    try
    {
      h = Double.parseDouble(this.qty_hrs_fri);  
    }
    catch(NumberFormatException e)
    {
      h = 0;
    }
    
    return h;
  }
  
  public double getFridayGross(){
  	return getHourlyWage() * getFridayHours();
  }
  
  public double getDailyHours(int day){
  	
  	double h = 0;
  	
  	switch (day){
  		case Calendar.MONDAY:
  			h = this.getMondayHours();
  			break;
  		case Calendar.TUESDAY:
  			h = this.getTuesdayHours();
  			break;
  		case Calendar.WEDNESDAY:
  			h = this.getWednesdayHours();
  			break;
  		case Calendar.THURSDAY:
  			h = this.getThursdayHours();
  			break;
  		case Calendar.FRIDAY:
  			h = this.getFridayHours();
  			break;
  			default:
  				h = 0;
  	}
  	
  	return h;
  }
  
  public String getJobCode()
  {
    return this.job_code;
  }
  
  public boolean workedDuringWeek()
  {
    return ((getMondayHours() > 0) || (getTuesdayHours() > 0)
      || (getWednesdayHours() > 0) || (getThursdayHours() > 0)
      || (getFridayHours() > 0));
  }
  
  public double getTotalWeeklyHoursWorked(){
  	return (getMondayHours() + getTuesdayHours() + getWednesdayHours()
  			+ getThursdayHours() + getFridayHours());
  }
  
  public double getHourlyWage(){
  	double wage = 0.00;
  	
  	if(workedDuringWeek())
  		wage = getWeeklyGross()/getTotalWeeklyHoursWorked();
  	
  	return wage;
  }
  
  public StatutoryPay getStatutoryHolidayPay(Date d){
  	boolean included = false;
  	StatutoryPay pay = null;
  	
  	Date beginning = getWeekBeginningDate();
  	Date end = getWeekEndingDate();
  	
  	if((beginning != null) && (end != null)){
  		if((beginning.compareTo(d) == 0) || (end.compareTo(d) == 0))
  			included = true;
  		else if(beginning.before(d) && d.before(end))
  			included = true;
  	}
  	
  	if(included){
  		Calendar cal = Calendar.getInstance();
  		cal.setTime(d);
  		
  		switch(cal.get(Calendar.DAY_OF_WEEK)){  		
  			case Calendar.MONDAY:
  				if(getMondayGross() > 0)
  					pay = new StatutoryPay(new SimpleDateFormat("ddMMyyyy").format(d), getMondayGross());
  				break;
  			case Calendar.TUESDAY:
  				if(getTuesdayGross() > 0)
  					pay = new StatutoryPay(new SimpleDateFormat("ddMMyyyy").format(d), getTuesdayGross());
  				break;
  			case Calendar.WEDNESDAY:
  				if(getWednesdayGross() > 0)
  					pay = new StatutoryPay(new SimpleDateFormat("ddMMyyyy").format(d), getWednesdayGross());
  				break;
  			case Calendar.THURSDAY:
  				if(getThursdayGross() > 0)
  					pay = new StatutoryPay(new SimpleDateFormat("ddMMyyyy").format(d), getThursdayGross());
  				break;
  			case Calendar.FRIDAY:
  				if(getFridayGross() > 0)
  					pay = new StatutoryPay(new SimpleDateFormat("ddMMyyyy").format(d), getFridayGross());
  				break;
  		}
  	}
  	
  	return pay;
  }
  
  public String toString()
  {
    String str = this.orig_line;
    
    str += "\n" +  nbr_sin;;
    str += " | " +  nbr_district;
    str += " | " +  nbr_school;  
    str += " | " +  copy_payment_type;    
    str += " | " +  date_pay_period;    
    str += " | " +  date_eff_begin;
    str += " | " +  date_eff_end;    
    str += " | " +  full_daily_gross;  
    str += " | " +  amt_ei_insurable; 
    str += " | " +  qty_ei_insurable; 
    str += " | " +  count_weeks;   
    str += " | " +  ind_roe;    
    str += " | " +  date_week_ending;  
    str += " | " +  amt_weekly_gross;   
    str += " | " +  qty_hrs_mon;   
    str += " | " +  qty_hrs_tue;   
    str += " | " +  qty_hrs_wed;  
    str += " | " +  qty_hrs_thu;     
    str += " | " +  qty_hrs_fri;   
    str += " | " +  nbr_cheque; 
    str += " | " +  job_code; 
    
    return str;
  }
  
  private void parseData() throws Exception
  {
    try
    {
      this.nbr_sin = !StringUtils.isEmpty(orig_line.substring(0, 9))?orig_line.substring(0, 9).trim():null;
      this.nbr_district = !StringUtils.isEmpty(orig_line.substring(9, 12))?orig_line.substring(9, 12).trim():null;
      this.nbr_school = !StringUtils.isEmpty(orig_line.substring(12, 15))?orig_line.substring(12, 15).trim():null;
      this.copy_payment_type = !StringUtils.isEmpty(orig_line.substring(15, 16))?orig_line.substring(15, 16).trim():null;
      this.date_pay_period = !StringUtils.isEmpty(orig_line.substring(16, 24))?orig_line.substring(16, 24).trim():null;
      this.date_eff_begin = !StringUtils.isEmpty(orig_line.substring(24, 32))?orig_line.substring(24, 32).trim():null;
      this.date_eff_end = !StringUtils.isEmpty(orig_line.substring(32, 40))?orig_line.substring(32, 40).trim():null;
      this.full_daily_gross = !StringUtils.isEmpty(orig_line.substring(40, 50))?orig_line.substring(40, 50).trim():null;
      this.amt_ei_insurable = !StringUtils.isEmpty(orig_line.substring(50, 60))?orig_line.substring(50, 60).trim():null;
      this.qty_ei_insurable = !StringUtils.isEmpty(orig_line.substring(60, 68))?orig_line.substring(60, 68).trim():null;
      this.count_weeks = !StringUtils.isEmpty(orig_line.substring(68, 70))?orig_line.substring(68, 70).trim():null;
      this.ind_roe = !StringUtils.isEmpty(orig_line.substring(70, 71))?orig_line.substring(70, 71).trim():null;
      this.date_week_ending = !StringUtils.isEmpty(orig_line.substring(71, 79))?orig_line.substring(71, 79).trim():null;
      this.amt_weekly_gross = !StringUtils.isEmpty(orig_line.substring(79, 89))?orig_line.substring(79, 89).trim():null;
      this.qty_hrs_mon = !StringUtils.isEmpty(orig_line.substring(89, 97))?orig_line.substring(89, 97).trim():null;
      this.qty_hrs_tue = !StringUtils.isEmpty(orig_line.substring(97, 105))?orig_line.substring(97, 105).trim():null;
      this.qty_hrs_wed = !StringUtils.isEmpty(orig_line.substring(105, 113))?orig_line.substring(105, 113).trim():null;
      this.qty_hrs_thu = !StringUtils.isEmpty(orig_line.substring(113, 121))?orig_line.substring(113, 121).trim():null;
      this.qty_hrs_fri = !StringUtils.isEmpty(orig_line.substring(121, 129))?orig_line.substring(121, 129).trim():null;
      this.nbr_cheque = !StringUtils.isEmpty(orig_line.substring(129, 135))?orig_line.substring(129, 135).trim():null;
      this.job_code = !StringUtils.isEmpty(orig_line.substring(135))?orig_line.substring(135).trim():null;
    }
    catch(Exception e)
    {
      System.err.println("Orginal Line:\n" + this.orig_line);
      throw e;
    }
  }
}