package com.awsd.security.addressbook;

public class Address 
{
  private String firstname;
  private String lastname;
  private String email;
  private String shortname;
  
  public Address(String firstname, String lastname, String shortname, String email)
  {
    this.shortname = shortname;
    this.email = email;
    this.firstname = firstname;
    this.lastname = lastname;
  }

  public String getEmailAddress()
  {
    return email;
  }

  public String getShortName()
  {
    return shortname;
  }

  public String getFirstName()
  {
    return firstname;
  }

  public String getLastName()
  {
    return lastname;
  }

  public String getFullName()
  {
    return getLastName() + ", " + getFirstName();
  }
}