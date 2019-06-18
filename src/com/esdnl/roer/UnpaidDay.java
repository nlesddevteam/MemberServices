package com.esdnl.roer;

import java.text.*;

import java.util.*;


public class UnpaidDay 
{
  private Date unpaid_date;
  private int hrs;
  
  public UnpaidDay(Date unpaid_date, int hrs)
  {
    this.unpaid_date = unpaid_date;
    this.hrs = hrs;
  }
  
  public Date getUnpaidDate()
  {
    return this.unpaid_date;
  }
  
  public int getHoursWorked()
  {
    return this.hrs;
  }
  
  public String toString()
  {
    return (new SimpleDateFormat("dd/MM/yyyy")).format(this.unpaid_date) + " - " + this.hrs + " HRS";
  }
}