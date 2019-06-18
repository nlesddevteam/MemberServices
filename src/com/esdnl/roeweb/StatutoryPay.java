package com.esdnl.roeweb;

public class StatutoryPay {
	
	private String date;
	private double amt;
	
	public StatutoryPay(String date, double amt){
		this.date = date;
		this.amt = amt;
	}
	
	public String getDate(){
		return this.date;
	}
	
	public double getAmount(){
		return this.amt;
	}
}
