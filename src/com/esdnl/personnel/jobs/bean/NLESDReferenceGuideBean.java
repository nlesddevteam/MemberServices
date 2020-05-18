package com.esdnl.personnel.jobs.bean;
import java.util.Date;
public class NLESDReferenceGuideBean {
	private int id;
	private ApplicantProfileBean profile;
	private String providedBy;
	private String providedByPosition;
	private Date dateProvided;
	private String q1;
	private String q2;
	private String q3;
	private String q4;
	private String q5;
	private String q6;
	private String q6Comment;
	private String scale1;
	private String scale2;
	private String scale3;
	private String scale4;
	private String scale5;
	private String scale6;
	private String scale7;
	private String scale8;
	private String scale9;
	private String referenceScale;
	private String emailAddress;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ApplicantProfileBean getProfile() {
		return profile;
	}
	public void setProfile(ApplicantProfileBean profile) {
		this.profile = profile;
	}
	public String getProvidedBy() {
		return providedBy;
	}
	public void setProvidedBy(String providedBy) {
		this.providedBy = providedBy;
	}
	public String getProvidedByPosition() {
		return providedByPosition;
	}
	public void setProvidedByPosition(String providedByPosition) {
		this.providedByPosition = providedByPosition;
	}
	public Date getDateProvided() {
		return dateProvided;
	}
	public void setDateProvided(Date dateProvided) {
		this.dateProvided = dateProvided;
	}
	public String getQ1() {
		return q1;
	}
	public void setQ1(String q1) {
		this.q1 = q1;
	}
	public String getQ2() {
		return q2;
	}
	public void setQ2(String q2) {
		this.q2 = q2;
	}
	public String getQ3() {
		return q3;
	}
	public void setQ3(String q3) {
		this.q3 = q3;
	}
	public String getQ4() {
		return q4;
	}
	public void setQ4(String q4) {
		this.q4 = q4;
	}
	public String getQ5() {
		return q5;
	}
	public void setQ5(String q5) {
		this.q5 = q5;
	}
	public String getQ6() {
		return q6;
	}
	public void setQ6(String q6) {
		this.q6 = q6;
	}

	public String getQ6Comment() {
		return q6Comment;
	}
	public void setQ6Comment(String q6Comment) {
		this.q6Comment = q6Comment;
	}
	public String getScale1() {
		return scale1;
	}
	public void setScale1(String scale1) {
		this.scale1 = scale1;
	}
	public String getScale2() {
		return scale2;
	}
	public void setScale2(String scale2) {
		this.scale2 = scale2;
	}
	public String getScale3() {
		return scale3;
	}
	public void setScale3(String scale3) {
		this.scale3 = scale3;
	}
	public String getScale4() {
		return scale4;
	}
	public void setScale4(String scale4) {
		this.scale4 = scale4;
	}
	public String getScale5() {
		return scale5;
	}
	public void setScale5(String scale5) {
		this.scale5 = scale5;
	}
	public String getScale6() {
		return scale6;
	}
	public void setScale6(String scale6) {
		this.scale6 = scale6;
	}
	public String getScale7() {
		return scale7;
	}
	public void setScale7(String scale7) {
		this.scale7 = scale7;
	}
	public String getScale8() {
		return scale8;
	}
	public void setScale8(String scale8) {
		this.scale8 = scale8;
	}
	public String getScale9() {
		return scale9;
	}
	public void setScale9(String scale9) {
		this.scale9 = scale9;
	}
	public boolean isNew() {
		return this.id <= 0;
	}
	public String getReferenceScale() {
		return referenceScale;
	}
	public void setReferenceScale(String referenceScale) {
		this.referenceScale = referenceScale;
	}
	public Integer getTotalScore()
	{
		return(Integer.parseInt(scale1) + Integer.parseInt(scale2)+Integer.parseInt(scale3)+Integer.parseInt(scale4)+Integer.parseInt(scale5)+
				Integer.parseInt(scale6) + Integer.parseInt(scale7)+Integer.parseInt(scale8)+Integer.parseInt(scale9));
		
	}
	public Integer getPossibleTotal(){
		Integer total = 0;
		if(Integer.parseInt(scale1) > 0){
			total++;
		}
		if(Integer.parseInt(scale2) > 0){
			total++;
		}
		if(Integer.parseInt(scale3) > 0){
			total++;
		}
		if(Integer.parseInt(scale4) > 0){
			total++;
		}
		if(Integer.parseInt(scale5) > 0){
			total++;
		}
		if(Integer.parseInt(scale6) > 0){
			total++;
		}
		if(Integer.parseInt(scale7) > 0){
			total++;
		}
		if(Integer.parseInt(scale8) > 0){
			total++;
		}
		if(Integer.parseInt(scale9) > 0){
			total++;
		}
		
		return total;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
}
