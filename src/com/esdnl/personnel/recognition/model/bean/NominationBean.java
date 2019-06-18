package com.esdnl.personnel.recognition.model.bean;

import com.awsd.school.*;
import com.awsd.personnel.*;
import com.esdnl.servlet.FormElementFormat;
import java.util.*;
import java.text.*;

public class NominationBean {
	private int UID;
	private String nominee_firtname;
	private String nominee_lastname;
	private NominationPeriodBean nomination_period;
	private School location;
	private String rationle_filename;
	private Personnel nominator;
	private Date nomination_date;
	private String nominator_firstname;
	private String nominator_lastname;
	private String nominator_location;
	
	public NominationBean(){
		this.UID = 0;
		this.nomination_date = null;
		this.location = null;
		this.nomination_period = null;
		this.nominator = null;
		this.nominee_firtname = null;
		this.nominee_lastname = null;
		this.rationle_filename = null;
		this.nominator_firstname = null;
		this.nominator_lastname = null;
		this.nominator_location = null;
	}
	
	public int getUID()
	{
		return this.UID;
	}
	
	public void setUID(int UID)
	{
		this.UID = UID;
	}
	
	public void setNomineeFirstname(String nominee_firstname){
		this.nominee_firtname = nominee_firstname;
	}
	
	public String getNomineeFirstname(){
		return this.nominee_firtname;
	}
	
	public void setNomineeLastname(String nominee_lastname){
		this.nominee_lastname = nominee_lastname;
	}
	
	public String getNomineeLastname(){
		return this.nominee_lastname;
	}
	
	public String getNomineeFullName(){
		return this.getNomineeFirstname()  + " " + this.getNomineeLastname();
	}
	
	public void setNominationPeriod(NominationPeriodBean nomination_period){
		this.nomination_period = nomination_period;
	}
	
	public NominationPeriodBean getNominationPeriod(){
		return this.nomination_period;
	}
	
	public void setNomineeLocation(School location){
		this.location = location;
	}
	
	public School getNomineeLocation(){
		return this.location;
	}
	
	public void setRationaleFilename(String rationale_filename){
		this.rationle_filename = rationale_filename;
	}
	
	public String getRationaleFilename(){
		return this.rationle_filename;
	}
	
	public void setNominator(Personnel nominator){
		this.nominator = nominator;
	}
	
	public Personnel getNominator(){
		return this.nominator;
	}
	
	public void setNominationDate(Date nomination_date){
		this.nomination_date = nomination_date;
	}
	
	public Date getNominationDate(){
		return this.nomination_date;
	}
	
	public String getFormattedNominationDate(){
		return (new SimpleDateFormat(FormElementFormat.DATE_FORMAT)).format(getNominationDate());
	}
	
	public void setNominatorFirstName(String nominator_firstname){
		this.nominator_firstname = nominator_firstname;
	}
	
	public String getNominatorFirstName(){
		return this.nominator_firstname;
	}
	
	public void setNominatorLastName(String nominator_lastname){
		this.nominator_lastname = nominator_lastname;
	}
	
	public String getNominatorLastName(){
		return this.nominator_lastname;
	}
	
	public String getNominatorFullName(){
		String name = "";
		
		if(this.getNominator() != null){
			name = this.getNominator().getFullNameReverse();
		}
		else
			name = this.getNominatorFirstName() + " " + this.getNominatorLastName();
		
		return name;
	}
	
	public void setNominatorLocation(String nominator_location){
		this.nominator_location = nominator_location;
	}
	
	public String getNominatorLocation() throws PersonnelException{
		String loc = "";
		if(this.getNominator() != null){
			if(this.getNominator().getSchool() != null)
				loc = this.getNominator().getSchool().getSchoolName();
		}
		else
			loc = this.nominator_location;
		
		return loc;
	}
}
