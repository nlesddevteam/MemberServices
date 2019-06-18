package com.esdnl.personnel.recognition.model.bean;

import com.awsd.personnel.*;

public class RecognitionCategoryBean {
	private int uid;
	private String name;
	private String description;
	private boolean secureOnly;
	private int monitor_id;
	
	private int period_count;
	
	public RecognitionCategoryBean(){
		this.uid = 0;
		this.name = "UNKNOWN";
		this.description = "UNKNOWN";
		this.secureOnly = false;
		this.monitor_id = 0;
		
		this.period_count = 0;
	}
	
	public void setUID(int uid){
		this.uid = uid;
	}
	
	public int getUID(){
		return this.uid;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public boolean isSecureOnly()
	{
		return this.secureOnly;
	}
	
	public void setSecureOnly(boolean secureOnly)
	{
		this.secureOnly = secureOnly;
	}
	
	public int getMonitorID()
	{
		return this.monitor_id;
	}
	
	public void setMonitorID(int monitor_id)
	{
		this.monitor_id = monitor_id;
	}
	
	public Personnel getMonitor()
	{
		Personnel tmp = null;
		
		if(this.monitor_id > 0){
			try{
				tmp = PersonnelDB.getPersonnel(this.monitor_id);
			}catch(PersonnelException e){
					tmp = null;
			}
		}
			
		return tmp;
	}
	
	public void setNominationPeriodCount(int period_count){
		this.period_count = period_count;
	}
	
	public int getNominationPeriodCount(){
		return this.period_count;
	}
	
	
	                                      
	public String toString()
	{
		return this.getName();
	}
}
