package com.awsd.pdreg;


public class EventType 
{
  public static final int CLOSEOUT_DAY_PD_SESSION = 1;
  public static final int DISTRICT_CALENDAR_ENTRY = 2;
  public static final int DISTRICT_CALENDAR_CLOSEOUT_ENTRY = 3;
  public static final int PD_OPPORTUNITY = 4;
  public static final int PRIVATE_CALENDAR_ENTRY = 5;
  public static final int DISTRICT_CALENDAR_HOLIDAY_ENTRY = 23;
  public static final int DISTRICT_CALENDAR_REMINDER_ENTRY = 24;
  public static final int SCHOOL_PD_REQUEST = 41;
  public static final int SCHOOL_CLOSEOUT_REQUEST = 62;
  public static final int SCHOOL_PD_ENTRY = 63;
  
  private int id;
  private String name;
  
  public EventType(int id, String name)
  {
    this.id = id;
    this.name = name;
  }

  public int getEventTypeID()
  {
    return id;
  }

  public String getEventTypeName()
  {
    return name;
  }
}