package com.esdnl.personnel.v2.model.sds.bean;

public class LocationBean 
{
  
  private String locationId;
  private String locationDescription;

  public LocationBean()
  {
    this.locationId = null;
    this.locationDescription = null;
  }

  public String getLocationId()
  {
    return locationId;
  }

  public void setLocationId(String newLocationId)
  {
    locationId = newLocationId;
  }

  public String getLocationDescription()
  {
    return locationDescription;
  }

  public void setLocationDescription(String newLocationDescription)
  {
    locationDescription = newLocationDescription;
  }
  
  public String toString()
  {
    return this.locationId + " : " + this.locationDescription;
  }
  
}