package com.esdnl.roeweb;

import java.util.*;
import com.esdnl.util.*;

public class TermReplacementRoe implements IRoe
{
  private String orig_line;
  
  private String lastname;
  private String firstname;
  private String sin;
  private String address1;
  private String address2;
  private String city;
  private String postal_code;
  private String hours;
  private String salary;
  private String total_annual;
  private String block42;
  private String block43;
  
  public TermReplacementRoe(String orig_line)
  {
    this.orig_line = orig_line;
    
    parseData();
  }
  
  public String getLastName()
  {
    return this.lastname;
  }
  
  public String getFirstName()
  {
    return this.firstname;
  }
  
  public String getSIN()
  {
    return this.sin;
  }
  
  public String getAddress1()
  {
    return this.address1;
  }
  
  public String getAddress2()
  {
    return this.address2;
  }
  
  public String getCity()
  {
    return this.city;
  }
  
  public String getPostalCode()
  {
    return this.postal_code;
  }
  
  public String getHours()
  {
    return this.hours;
  }
  
  public String getSalary()
  {
    return this.salary;
  }
  
  public String getTotalAnnual()
  {
    return this.total_annual;
  }
  
  public String getBlock42()
  {
    return this.block42;
  }
  
  public String getBlock43()
  {
    return this.block43;
  }
  
  public boolean isValid()
  {
    if((this.salary != null) && !StringUtils.isEmpty(this.salary) && (Double.parseDouble(this.salary) > 0))
      return true;
    else
      return false;
  }
  
  private void parseData()
  {
    String[] data = this.orig_line.split(",");
    
    this.lastname = (data[0]!=null)?data[0].trim():null;
    this.firstname = (data[1]!=null)?data[1].trim():null;
    this.sin = (data[2]!=null)?data[2].trim():null;
    this.address1 = (data[3]!=null)?data[3].trim():null;
    this.address2 = (data[4]!=null)?data[4].trim():null;
    this.city = (data[5]!=null)?data[5].trim():null;
    this.postal_code = (data[6]!=null)?data[6].trim():null;
    this.hours = (data[7]!=null)?data[7].trim():null;
    this.salary = (data[8]!=null)?data[8].trim():null;
    this.total_annual = (data[9]!=null)?data[9].trim():null;
    this.block42 = (data[10]!=null)?data[10].trim():null;
    this.block43 = (data[11]!=null)?data[11].trim():null;
  }
}