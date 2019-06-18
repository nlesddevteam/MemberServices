package com.awsd.personnel.profile;

import com.awsd.personnel.*;


public class Profile 
{
  private String street_address;
  private String community;
  private String province;
  private String postalcode;
  private String phone;
  private String fax;
  private String cell;
  private String gender;
  private Personnel p;
  private String sin;
  
  public Profile(Personnel p, String street_address, String community, String province,
    String postalcode, String phone, String fax, String cell, String gender)
  {
    this.street_address = street_address;
    this.community = community;
    this.province = province;
    this.postalcode = postalcode;
    this.phone = phone;
    this.fax = fax;
    this.cell = cell;
    this.gender = gender;
    this.p = p;
    this.sin = null;
  }
  
  public Profile(Personnel p, String street_address, String community, String province,
    String postalcode, String phone, String fax, String cell, String gender, String sin)
  {
    this(p, street_address, community, province, postalcode, phone, fax, cell, gender);
      
    this.sin = sin;
  }
  
  public String getStreetAddress()
  {
    return this.street_address;
  }
  
  public Personnel getPersonnel()
  {
    return this.p;
  }
  
  public String getCommunity()
  {
    return this.community;
  }
  
  public String getProvince()
  {
    return this.province;
  }
  
  public String getPostalCode()
  {
    return this.postalcode;
  }
  
  public String getPhoneNumber()
  {
    return this.phone;
  }
  
  public String getFaxNumber()
  {
    return this.fax;
  }
  
  public String getCellPhoneNumber()
  {
    return this.cell;
  }
  
  public String getGender()
  {
    return this.gender;
  }
  
  public String getSIN()
  {
    return this.sin;
  }
}