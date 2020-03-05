package com.esdnl.personnel.jobs.bean;
import java.io.Serializable;
public class ApplicantEducationSecSSBean implements Serializable {

	private static final long serialVersionUID = 5304511837525367873L;
	private int id;
	private String sin;
	private String educationLevel;
	private String schoolName;
	private String schoolCity;
	private String schoolProvince;
	private String schoolCountry;
	private String yearsCompleted;
	private String graduated;
	private String yearGraduated;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSin() {
		return sin;
	}
	public void setSin(String sin) {
		this.sin = sin;
	}
	public String getEducationLevel() {
		return educationLevel;
	}
	public void setEducationLevel(String educationLevel) {
		this.educationLevel = educationLevel;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getSchoolCity() {
		return schoolCity;
	}
	public void setSchoolCity(String schoolCity) {
		this.schoolCity = schoolCity;
	}
	public String getSchoolProvince() {
		return schoolProvince;
	}
	public void setSchoolProvince(String schoolProvince) {
		this.schoolProvince = schoolProvince;
	}
	public String getSchoolCountry() {
		return schoolCountry;
	}
	public void setSchoolCountry(String schoolCountry) {
		this.schoolCountry = schoolCountry;
	}
	
	public String getYearsCompleted() {
		return yearsCompleted;
	}
	public void setYearsCompleted(String yearsCompleted) {
		this.yearsCompleted = yearsCompleted;
	}
	public String getGraduated() {
		return graduated;
	}
	public void setGraduated(String graduated) {
		this.graduated = graduated;
	}
	public String getGraduatedText(){
		String stext="";
		if(this.getGraduated() != null){
			if(this.getGraduated().equals("Y")){
				stext="Yes";
			}else if(this.getGraduated().equals("N")){
				stext="No";
			}else if(this.getGraduated().equals("G")){
				stext="GED";
			}
		}
		return stext;
	}
	public String getYearGraduated() {
		return yearGraduated;
	}
	public void setYearGraduated(String yearGraduated) {
		this.yearGraduated = yearGraduated;
	}
}
