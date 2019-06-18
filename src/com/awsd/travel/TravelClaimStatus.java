package com.awsd.travel;

import java.util.*;


public class TravelClaimStatus 
{
  public static final TravelClaimStatus PRE_SUBMISSION;
  public static final TravelClaimStatus SUBMITTED;
  public static final TravelClaimStatus REVIEWED;
  public static final TravelClaimStatus APPROVED;
  public static final TravelClaimStatus REJECTED;
  public static final TravelClaimStatus PAYMENT_PENDING;
  public static final TravelClaimStatus PAID;
  
  private static HashMap statuses;
  
  private int status_id;
  private String status_desc;

  static
  {
    try
    {
      statuses = TravelClaimStatusDB.getAllStatuses();
    }
    catch(Exception e)
    {
      System.err.println("<<<<<< COULD NOT LOAD TRAVEL CLAIM STATUS CODES >>>>>>");
      statuses = null;
    }

    if(statuses != null)
    {
      PRE_SUBMISSION = (TravelClaimStatus) statuses.get(new Integer(1));
      SUBMITTED = (TravelClaimStatus) statuses.get(new Integer(2));
      REVIEWED = (TravelClaimStatus) statuses.get(new Integer(3));
      APPROVED = (TravelClaimStatus) statuses.get(new Integer(4));
      REJECTED = (TravelClaimStatus) statuses.get(new Integer(5));
      PAYMENT_PENDING = (TravelClaimStatus) statuses.get(new Integer(6));
      PAID = (TravelClaimStatus) statuses.get(new Integer(7));
    }
    else
    {
      PRE_SUBMISSION = null;
      SUBMITTED = null;
      REVIEWED = null;
      APPROVED = null;
      REJECTED = null;
      PAYMENT_PENDING = null;
      PAID = null;
    }
  }

  public TravelClaimStatus(int status_id, String status_desc)
  {
    this.status_id = status_id;
    this.status_desc = status_desc;
  }

  public int getID()
  {
    return this.status_id;
  }

  public String getDescription()
  {
    return this.status_desc;
  }

  public static TravelClaimStatus getStatus(int status_id)
  {
    if(statuses != null)
      return (TravelClaimStatus) statuses.get(new Integer(status_id));
    else
      return null;
  }

  public boolean equals(Object obj)
  {
    boolean check = true;

    if(!(obj instanceof TravelClaimStatus))
      check = false;
    else if(this.status_id != ((TravelClaimStatus)obj).getID())
      check = false;

    return check;
  }
}