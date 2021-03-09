package com.awsd.travel;

import java.text.SimpleDateFormat;
import java.util.*;

import com.awsd.travel.bean.TravelClaimFileBean;


public class TravelClaimItem 
{
  private int item_id;
  private Date item_date;
  private String item_desc;
  private int item_kms;
  private double item_meals;
  private double item_lodging;
  private double item_other;
  private double per_km_rate;
  private String departure_time;
  private String return_time;
  private TreeMap<Integer,TravelClaimFileBean> attachments;
  
  public TravelClaimItem(int item_id, Date item_date, String item_desc, int item_kms, double item_meals, double item_lodging, double item_other, double per_km_rate, String departure_time, String return_time)
  {
    this.item_id = item_id;
    this.item_date = item_date;
    this.item_desc = item_desc;
    this.item_kms = item_kms;
    this.item_meals = item_meals;
    this.item_lodging = item_lodging;
    this.item_other = item_other;
    this.per_km_rate = per_km_rate;
    this.departure_time = departure_time;
    this.return_time = return_time;
  }

  public TravelClaimItem(Date item_date, String item_desc, int item_kms, double item_meals, double item_lodging, double item_other, String departure_time, String return_time )
  {
    this(-1, item_date, item_desc, item_kms, item_meals, item_lodging, item_other, 0.315, departure_time, return_time);
  }

  public TravelClaimItem()
  {
    this(-1, null, "UNKNOWN", 0, 0.0, 0.0, 0.0, 0.315, "UNKNOWN", "UNKNOWN");
  }

  public int getItemID()
  {
    return this.item_id;
  }

  public void setItemID(int item_id)
  {
    this.item_id = item_id;
  }

  public Date getItemDate()
  {
    return this.item_date;
  }

  public void setItemDate(Date item_date)
  {
    this.item_date = item_date;
  }

  public String getItemDescription()
  {
    return this.item_desc;
  }

  public void setItemDescription(String item_desc)
  {
    this.item_desc = item_desc;
  }

  public int getItemKMS()
  {
    return this.item_kms;
  }

  public void setItemKMS(int item_kms)
  {
    this.item_kms = item_kms;
  }

  public double getItemMeals()
  {
    return this.item_meals;
  }

  public void setItemMeals(double item_meals)
  {
    this.item_meals = item_meals;
  }

  public double getItemLodging()
  {
    return this.item_lodging;
  }

  public void setItemLodging(double item_lodging)
  {
    this.item_lodging = item_lodging;
  }

  public double getItemOther()
  {
    return this.item_other;
  }

  public void setItemOther(double item_other)
  {
    this.item_other = item_other;
  }

  public double getPerKilometerRate()
  {
    return this.per_km_rate;
  }
  
  public String getDepartureTime(){
  	return this.departure_time;
  }
  
  public void setDepartureTime(String departure_time){
  	this.departure_time = departure_time;
  }
  
  public String getReturnTime()
  {
  	return this.return_time;
  }
  
  public void setReturnTime(String return_time)
  {
  	this.return_time = return_time;
  }
	public String getItemDateFormatted() {
		if(this.item_date != null){
			return new SimpleDateFormat("dd/MM/yyyy").format(this.item_date);
		}else{
			return "";
		}
	}

	public TreeMap<Integer, TravelClaimFileBean> getAttachments() {
		return attachments;
	}

	public void setAttachments(TreeMap<Integer, TravelClaimFileBean> attachments) {
		this.attachments = attachments;
	}
}