package com.esdnl.personnel.recognition.model.bean;

import java.util.*;
import java.text.*;
import com.esdnl.servlet.FormElementFormat;

public class NominationPeriodBean {
	private RecognitionCategoryBean category;
	private int id;
	private Date start_date;
	private Date end_date;
	
	private int nomination_count;
	
	public NominationPeriodBean(){
		id = 0;
		category = null;
		start_date = null;
		end_date = null;
		
		nomination_count = 0;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return this.id;
	}
	
	public void setCategory(RecognitionCategoryBean category){
		this.category = category;
	}
	
	public RecognitionCategoryBean getCategory(){
		return this.category;
	}
	
	public void setStartDate(Date start_date){
		this.start_date = start_date;
	}
	
	public Date getStartDate(){
		return this.start_date;
	}
	
	public String getFormattedStartDate(){
		return new SimpleDateFormat(FormElementFormat.DATE_FORMAT).format(getStartDate());
	}
	
	public void setEndDate(Date end_date){
		this.end_date = end_date;
	}
	
	public Date getEndDate(){
		return this.end_date;
	}
	
	public String getFormattedEndDate(){
		return new SimpleDateFormat(FormElementFormat.DATE_FORMAT).format(getEndDate());
	}
	
	public void setNominationCount(int nomination_count){
		this.nomination_count = nomination_count;
	}
	
	public int getNominationCount(){
		return this.nomination_count;
	}
}
