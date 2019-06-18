package com.esdnl.personnel.jobs.bean;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import com.awsd.personnel.*;

public class ReferenceCheckRequestBean {
	
	private int request_id;
	private String comp_num;
	private String candidate_id;
	private String referrer_email;
	private int reference_id;
	private Date request_date;
	private Personnel requester;
	private String referenceType;
	
	
	public ReferenceCheckRequestBean(){
		this.request_id = 0;
		this.comp_num = null;
		this.candidate_id = null;
		this.referrer_email = null;
		this.reference_id = 0;
		this.requester = null;
		this.referenceType=null;
	}
	
	public int getRequestId(){
		return this.request_id;
	}
	
	public void setRequestId(int request_id){
		this.request_id = request_id;
	}
	
	public String getCompetitionNumber(){
		return this.comp_num;
	}
	
	public void setCompetitionNumber(String comp_num){
		this.comp_num = comp_num;
	}
	
	public String getCandidateId(){
		return this.candidate_id;
	}
	
	public void setCandidateId(String candidate_id){
		this.candidate_id = candidate_id;
	}
	
	public String getReferrerEmail(){
		return this.referrer_email;
	}
	
	public void setReferredEmail(String referrer_email){
		this.referrer_email = referrer_email;
	}
	
	public int getReferenceId(){
		return this.reference_id;
	}
	
	public void setReferenceId(int reference_id){
		this.reference_id = reference_id;
	}
	
	public boolean isReferenceCompleted()
	{
		return (this.reference_id > 0);
	}
	
	public Date getRequestDate()
	{
		return this.request_date;
	}
	
	public String getRequestDateFormatted()
	{
		return new SimpleDateFormat("dd/MM/yyyy").format(this.request_date);
	}
	
	public void setRequestDate(Date request_date)
	{
		this.request_date = request_date;
	}
	
	public Personnel getCheckRequester(){
		return this.requester;
	}
	
	public void setCheckRequester(Personnel requester){
		this.requester = requester;
	}
	
	public String toXML()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("<REFERENCE-CHECK-REQUEST>");
		
		sb.append("<REQUEST-ID>" + this.request_id + "</REQUEST-ID>");
		sb.append("<REQUEST-DATE>" + this.getRequestDateFormatted() + "</REQUEST-DATE>");
		sb.append("<COMPETITION-NUMBER>" + this.comp_num + "</COMPETITION-NUMBER>");
		sb.append("<CANDIDATE-ID>" + this.candidate_id + "</CANDIDATE-ID>");
		sb.append("<REFERRER-EMAIL>" + this.referrer_email + "</REFERRER-EMAIL>");
		sb.append("<REFERENCE-ID>" + this.reference_id + "</REFERENCE-ID>");
		sb.append("<REFERENCE-TYPE>" + this.referenceType + "</REFERENCE-TYPE>");
		if(this.requester != null)
			sb.append("<REQUESTER-NAME>" + this.requester.getFullName() + "</REQUESTER-NAME>");
		
		sb.append("</REFERENCE-CHECK-REQUEST>");
		
		return sb.toString();
	}

	public String getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}
	public String getReferenceScale(){
		String scale="4";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date d;
		try {
			d = sdf.parse("01/03/2017");
			if(!(this.request_date == null)){
				if(this.request_date.after(d)){
					scale="3";
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return scale;
		
	}
}
