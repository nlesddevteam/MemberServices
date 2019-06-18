package com.awsd.travel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TravelClaimKMRate {
	private double baseRate;
	private double approvedRate;
	private Date effectiveStartDate;
	private Date effectiveEndDate;
	  
	public double getBaseRate() {
		return baseRate;
	}
	public void setBaseRate(double baseRate) {
		this.baseRate = baseRate;
	}
	public double getApprovedRate() {
		return approvedRate;
	}
	public void setApprovedRate(double approvedRate) {
		this.approvedRate = approvedRate;
	}
	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}
	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}
	public Date getEffectiveEndDate() {
		return effectiveEndDate;
	}
	public void setEffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}
	public String getEffectiveStartDateFormatted()
	{
		return new SimpleDateFormat("dd/MM/yyyy").format(this.effectiveStartDate);
	}
	public String getEffectiveEndDateFormatted()
	{
		return new SimpleDateFormat("dd/MM/yyyy").format(this.effectiveEndDate);
	}
}
