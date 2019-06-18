package com.awsd.travel;

public class TravelClaimRateSummary 
{
  private double per_km_rate;
  private int sum_kms;
  private double kms_total;
  
  public TravelClaimRateSummary(double per_km_rate, int sum_kms, double kms_total)
  {
    this.per_km_rate = per_km_rate;
    this.sum_kms = sum_kms;
    this.kms_total = kms_total;
  }

  public double getPerKilometerRate()
  {
    return this.per_km_rate;
  }

  public int getKMSSummary()
  {
    return this.sum_kms;
  }

  public double getKMSTotal()
  {
    return this.kms_total;
  }
}