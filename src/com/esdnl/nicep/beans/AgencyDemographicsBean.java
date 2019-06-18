package com.esdnl.nicep.beans;

public class AgencyDemographicsBean 
{
  private int agencyId;
  private String name;
  private String address1;
  private String address2;
  private String cityTown;
  private String provinceState;
  private String country;
  private String zipcode;

  public AgencyDemographicsBean()
  {
    agencyId = 0;
    name = null;
    address1 = null;
    address2 = null;
    cityTown = null;
    provinceState = null;
    country = null;
    zipcode = null;
  }

  public int getAgencyId()
  {
    return agencyId;
  }
  
  public void setAgencyId(int newId)
  {
    agencyId = newId;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String newName)
  {
    name = newName;
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

  public String getProvinceState()
  {
    return provinceState;
  }

  public void setProvinceState(String newProvinceState)
  {
    provinceState = newProvinceState;
  }

  public String getCountry()
  {
    return country;
  }

  public void setCountry(String newCountry)
  {
    country = newCountry;
  }

  public String getZipcode()
  {
    return zipcode;
  }

  public void setZipcode(String newZipcode)
  {
    zipcode = newZipcode;
  }
}