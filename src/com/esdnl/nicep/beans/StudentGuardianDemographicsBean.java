package com.esdnl.nicep.beans;

import com.esdnl.util.*;

public class StudentGuardianDemographicsBean 
{ 
  private int guardianId;
  private int studentId;
  private String lastname;
  private String firstname;
  private String address1;
  private String address2;
  private String cityTown;
  private String stateProvince;
  private String country;
  private String phone1;
  private String phone2;
  private String email;
  private String zipcode;

  public StudentGuardianDemographicsBean()
  {
    guardianId = 0;
    studentId = 0;
    lastname = null;
    firstname = null;
    address1 = null;
    address2 = null;
    cityTown = null;
    stateProvince = null;
    country = null;
    phone1 = null;
    phone2 = null;
    email = null;
  }

  public int getGuardianId()
  {
    return guardianId;
  }
  
  public void setGuardianId(int newGuardianId)
  {
    guardianId = newGuardianId;
  }
  
  public int getStudentId()
  {
    return studentId;
  }

  public void setStudentId(int newStudentId)
  {
    studentId = newStudentId;
  }

  public String getLastname()
  {
    return lastname;
  }

  public void setLastname(String newLastname)
  {
    lastname = newLastname;
  }

  public String getFirstname()
  {
    return firstname;
  }

  public void setFirstname(String newFirstname)
  {
    firstname = newFirstname;
  }
  

  public String getAddress1()
  {
    return address1;
  }

  public void setAddress1(String newAddress1)
  {
    address1 = newAddress1;
  }

  public String getAddress2()
  {
    return address2;
  }

  public void setAddress2(String newAddress2)
  {
    address2 = newAddress2;
  }

  public String getCityTown()
  {
    return cityTown;
  }

  public void setCityTown(String newCityTown)
  {
    cityTown = newCityTown;
  }

  public String getStateProvince()
  {
    return stateProvince;
  }

  public void setStateProvince(String newStateProvince)
  {
    stateProvince = newStateProvince;
  }

  public String getCountry()
  {
    return country;
  }

  public void setCountry(String newCountry)
  {
    country = newCountry;
  }

  public String getPhone1()
  {
    return phone1;
  }

  public void setPhone1(String newPhone1)
  {
    phone1 = newPhone1;
  }

  public String getPhone2()
  {
    return phone2;
  }

  public void setPhone2(String newPhone2)
  {
    phone2 = newPhone2;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String newEmail)
  {
    email = newEmail;
  }

  public String getZipcode()
  {
    return zipcode;
  }

  public void setZipcode(String newZipcode)
  {
    zipcode = newZipcode;
  }

  public String getFullname()
  {
    return lastname + ", " + firstname;
  }
  
  public String getAddress()
  {
    StringBuffer buf = new StringBuffer(this.address1 + "\n");
    
    if(!StringUtils.isEmpty(this.address2))
      buf.append(this.address2 + "\n");
    
    buf.append(this.cityTown + "," + this.stateProvince + "\n");
    
    buf.append(this.country + " " + this.zipcode);
    
    return buf.toString();
  }
}