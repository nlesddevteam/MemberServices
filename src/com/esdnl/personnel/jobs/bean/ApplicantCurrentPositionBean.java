package com.esdnl.personnel.jobs.bean;
import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;
public class ApplicantCurrentPositionBean implements Serializable {
	private static final long serialVersionUID = 7479014654525485539L;
	private String sin;
	private int schoolId;
	private int positionHeld;
	private String PositionHours;
	private int id;
	private String positionType;
	private Date startDate;
	private Date endDate;
	private String positionName;
	private String positionUnion;
	public String getSin() {
		return sin;
	}
	public void setSin(String sin) {
		this.sin = sin;
	}
	public int getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}
	public int getPositionHeld() {
		return positionHeld;
	}
	public void setPositionHeld(int positionHeld) {
		this.positionHeld = positionHeld;
	}
	public String getPositionHours() {
		return PositionHours;
	}
	public void setPositionHours(String positionHours) {
		PositionHours = positionHours;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPositionType() {
		return positionType;
	}
	public void setPositionType(String positionType) {
		this.positionType = positionType;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public String getPositionUnion() {
		return positionUnion;
	}
	public void setPositionUnion(String positionUnion) {
		this.positionUnion = positionUnion;
	}
	public String getStartEndDates(){
		StringBuilder sb = new StringBuilder();
		String DATE_FORMAT = "MM/dd/yyyy";
		if (this.startDate != null){
			sb.append((new SimpleDateFormat(DATE_FORMAT)).format(this.startDate));
			sb.append("-");
			if(this.endDate != null){
				sb.append("<br />");
				sb.append((new SimpleDateFormat(DATE_FORMAT)).format(this.endDate));
			}
		}else{
			sb.append("&nbsp;");
		}
		return sb.toString();
	}
	public String getStartDateFormatted(){
		if(!(this.startDate == null)){
			String DATE_FORMAT = "MM/dd/yyyy";
			return (new SimpleDateFormat(DATE_FORMAT)).format(this.startDate);
		}else{
			return "";
		}
	}
	public String getEndDateFormatted(){
		if(!(this.endDate == null)){
			String DATE_FORMAT = "MM/dd/yyyy";
			return (new SimpleDateFormat(DATE_FORMAT)).format(this.endDate);
		}else{
			return "";
		}
	}
	public String getPositionTypeString()
	{
		String ptstring="";
		if(this.positionType.equals("C")){
			ptstring="Casual";
		}else if(this.positionType.equals("P")){
			ptstring="Permanent";
		}else if(this.positionType.equals("S")){
			ptstring="Substitute";
		}else if(this.positionType.equals("T")){
			ptstring="Temporary";
		}
		return ptstring;
	}
}