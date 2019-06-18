package com.esdnl.nicep.beans;

public class AgencyContactBean 
{
  private int agencyId;
  private int contactId;
  private String name;
  private String phone1;
  private String phone2;
  private String phone3;
  private String email;

  public AgencyContactBean()
  {
    agencyId = -1;
    contactId = -1;
    name = null;
    phone1 = null;
    phone2 = null;
    phone3 = null;
    email = null;
  }

  public int getAgencyId()
  {
    return agencyId;
  }

  public void setAgencyId(int newAgencyId)
  {
    agencyId = newAgencyId;
  }

  public int getContactId()
  {
    return contactId;
  }

  public void setContactId(int newContactId)
  {
    contactId = newContactId;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
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

  public String getPhone3()
  {
    return phone3;
  }

  public void setPhone3(String newPhone3)
  {
    phone3 = newPhone3;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String newEmail)
  {
    email = newEmail;
  }
}