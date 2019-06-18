package com.esdnl.roeweb;

import com.esdnl.util.*;

import java.util.*;
import java.text.*;

public class DemographicFileLine extends Exception 
{
  private static final String DATE_FORMAT = "yyyyMMdd";
  
  private String orig_line;
  
  private String nbr_sin;           // 1-9
  private String first_name;        // 10-22
  private String last_name;         // 23-42
  private String addr_line_1;       // 43-76
  private String addr_line_2;       // 77-110
  private String addr_line_3;       // 111-144
  private String postal_code;       // 145-150
  private String date_birth;        // 151-158
  private String occupation;        // 159-175
  private String first_day_worked;  // 176-183
  private String last_day_worked;   // 184-191
  
  public static final int MIN_LINE_LENGTH = 191;
  
  public DemographicFileLine(String orig_line)
  {
    this.orig_line = orig_line;
    
    parseData();
  }
  
  public String getSIN()
  {
    return this.nbr_sin;
  }
  
  public String getFirstName()
  {
    return this.first_name;
  }
  
  public String getLastName()
  {
    return this.last_name;
  }
  
  public String getAddressLine1()
  {
    return this.addr_line_1;
  }
  
  public String getAddressLine2()
  {
    return this.addr_line_2;
  }
  
  public String getAddressLine3()
  {
    return this.addr_line_3;
  }
  
  public String getPostalCode()
  {
    return this.postal_code;
  }
  
  public Date getBirthDate()
  {
    Date dt = null;
    
    try
    {
      dt = (new SimpleDateFormat(DemographicFileLine.DATE_FORMAT)).parse(this.date_birth);
    }
    catch(ParseException e)
    {
      dt = null;
    }
    
    return dt;
  }
  
  public String getOccupation()
  {
    return this.occupation;
  }
  
  public Date getFirstDayWorked()
  {
    Date dt = null;
    
    try
    {
      dt = (new SimpleDateFormat(DemographicFileLine.DATE_FORMAT)).parse(this.first_day_worked);
    }
    catch(ParseException e)
    {
      dt = null;
    }
    
    return dt;
  }
  
  public Date getLastDayWorked()
  {
    Date dt = null;
    
    try
    {
      dt = (new SimpleDateFormat(DemographicFileLine.DATE_FORMAT)).parse(this.last_day_worked);
    }
    catch(ParseException e)
    {
      dt = null;
    }
    
    return dt;
  }
  
  public String toString()
  {
    String str = "";
    
    str += this.nbr_sin;
    str += "\t" + this.first_name;
    str += "\t" + this.last_name;
    str += "\t" + this.addr_line_1;
    str += "\t" + this.addr_line_2;
    str += "\t" + this.addr_line_3;
    str += "\t" + this.postal_code;
    str += "\t" + this.date_birth;
    str += "\t" + this.occupation;
    
    return str;
  }
  
  private void parseData()
  {
    this.nbr_sin = !StringUtils.isEmpty(orig_line.substring(0, 9))?orig_line.substring(0, 9).trim():null;
    this.first_name = !StringUtils.isEmpty(orig_line.substring(9, 22))?orig_line.substring(9, 22).trim():null;
    this.last_name = !StringUtils.isEmpty(orig_line.substring(22, 42))?orig_line.substring(22, 42).trim():null;
    this.addr_line_1 = !StringUtils.isEmpty(orig_line.substring(42, 76))?orig_line.substring(42, 76).trim():null;
    this.addr_line_2 = !StringUtils.isEmpty(orig_line.substring(76, 110))?orig_line.substring(76, 110).trim():null;
    this.addr_line_3 = !StringUtils.isEmpty(orig_line.substring(110, 144))?orig_line.substring(110, 144).trim():null;
    this.postal_code = !StringUtils.isEmpty(orig_line.substring(144, 150))?orig_line.substring(144, 150).trim():null;
    this.date_birth = !StringUtils.isEmpty(orig_line.substring(150, 158))?orig_line.substring(150, 158).trim():null;
    this.occupation = !StringUtils.isEmpty(orig_line.substring(158, 175))?orig_line.substring(158, 175).trim():null;
    this.first_day_worked = !StringUtils.isEmpty(orig_line.substring(175, 183))?orig_line.substring(175, 183).trim():null;
    this.last_day_worked = !StringUtils.isEmpty(orig_line.substring(183))?orig_line.substring(183).trim():null;
  }
}