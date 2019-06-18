package com.awsd.travel;

public class TravelClaimSummary 
{
  private int sum_kms;
  private double sum_meals;
  private double sum_lodging;
  private double sum_other;
  private double kms_total;
  private double sum_total;
  
  public TravelClaimSummary(int sum_kms, double sum_meals, 
    double sum_lodging, double sum_other, double kms_total, double sum_total)
  {
    this.sum_kms = sum_kms;
    this.sum_meals = sum_meals;
    this.sum_lodging = sum_lodging;
    this.sum_other = sum_other;
    this.kms_total = kms_total;
    this.sum_total = sum_total;
  }
  
  public int getKMSSummary()
  {
    return this.sum_kms;
  }

  public double getKMSTotal()
  {
    return this.kms_total;
  }

  public double getMealSummary()
  {
    return this.sum_meals;
  }

  public double getLodgingSummary()
  {
    return this.sum_lodging;
  }

  public double getOtherSummary()
  {
    return this.sum_other;
  }

  public double getSummaryTotal()
  {
    return this.sum_total;
  }
}