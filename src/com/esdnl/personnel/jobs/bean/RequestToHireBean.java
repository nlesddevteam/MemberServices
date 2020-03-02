package com.esdnl.personnel.jobs.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.awsd.school.SchoolDB;
import com.awsd.school.SchoolException;
import com.esdnl.personnel.jobs.constants.RTHPositionTypeConstant;
import com.esdnl.personnel.jobs.constants.RequestToHireStatus;

public class RequestToHireBean {
	private int id;
	private String jobTitle;
	private String previousIncumbent;
	private Date dateVacated;
	private String workLocation;
	private int positionType;
	private String positionSalary;
	private Date startDate;
	private int supervisor;
	private String supervisorName;
	private int division;
	private String comments;
	private Date ddApproved;
	private String ddApprovedBy;
	private int ddApprovedById;
	private Date bcApproved;
	private String bcApprovedBy;
	private int bcApprovedById;
	private Date adApproved;
	private String adApprovedBy;
	private int adApprovedById;
	private Date adhrApproved;
	private String adhrApprovedBy;
	private int adhrApprovedById;
	private String requestBy;
	private int requestById;
	private Date dateRequested;
	private String locationDescription;
	private RequestToHireStatus status;
	private String CompetitionNumber;
	public static final String DATE_FORMAT = "MM/dd/yyyy";
	public static final String DATE_FORMAT_REC = "dd/MM/yyyy";
	private int unionCode;
	private String unionCodeString;
	private int positionName;
	private String positionNameString;
	private int positionTerm;
	private String positionTermString;
	private String positionHours;
	private String requestType;
	private String requestTypeString;
	private int shiftDiff;
	private String positionNumber;
	private Date EndDate;
	private int privateList;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getPreviousIncumbent() {
		return previousIncumbent;
	}
	public void setPreviousIncumbent(String previousIncumbent) {
		this.previousIncumbent = previousIncumbent;
	}
	public Date getDateVacated() {
		return dateVacated;
	}
	public void setDateVacated(Date dateVacated) {
		this.dateVacated = dateVacated;
	}
	public String getWorkLocation() {
		return workLocation;
	}
	public void setWorkLocation(String workLocation) {
		this.workLocation = workLocation;
	}
	public int getPositionType() {
		return positionType;
	}
	public void setPositionType(int positionType) {
		this.positionType = positionType;
	}
	public String getPositionSalary() {
		return positionSalary;
	}
	public void setPositionSalary(String positionSalary) {
		this.positionSalary = positionSalary;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public int getSupervisor() {
		return supervisor;
	}
	public void setSupervisor(int supervisor) {
		this.supervisor = supervisor;
	}
	public int getDivision() {
		return division;
	}
	public void setDivision(int division) {
		this.division = division;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Date getDdApproved() {
		return ddApproved;
	}
	public void setDdApproved(Date ddApproved) {
		this.ddApproved = ddApproved;
	}
	public String getDdApprovedBy() {
		return ddApprovedBy;
	}
	public void setDdApprovedBy(String ddApprovedBy) {
		this.ddApprovedBy = ddApprovedBy;
	}
	public Date getBcApproved() {
		return bcApproved;
	}
	public void setBcApproved(Date bcApproved) {
		this.bcApproved = bcApproved;
	}
	public String getBcApprovedBy() {
		return bcApprovedBy;
	}
	public void setBcApprovedBy(String bcApprovedBy) {
		this.bcApprovedBy = bcApprovedBy;
	}
	public Date getAdApproved() {
		return adApproved;
	}
	public void setAdApproved(Date adApproved) {
		this.adApproved = adApproved;
	}
	public String getAdApprovedBy() {
		return adApprovedBy;
	}
	public void setAdApprovedBy(String adApprovedBy) {
		this.adApprovedBy = adApprovedBy;
	}
	public Date getAdhrApproved() {
		return adhrApproved;
	}
	public void setAdhrApproved(Date adhrApproved) {
		this.adhrApproved = adhrApproved;
	}
	public String getAdhrApprovedBy() {
		return adhrApprovedBy;
	}
	public void setAdhrApprovedBy(String adhrApprovedBy) {
		this.adhrApprovedBy = adhrApprovedBy;
	}
	public String getRequestBy() {
		return requestBy;
	}
	public void setRequestBy(String requestBy) {
		this.requestBy = requestBy;
	}
	public Date getDateRequested() {
		return dateRequested;
	}
	public void setDateRequested(Date dateRequested) {
		this.dateRequested = dateRequested;
	}
	public String getLocationDescription() {
		return locationDescription;
	}
	public void setLocationDescription(String locationDescription) {
		this.locationDescription = locationDescription;
	}
	public RequestToHireStatus getStatus() {
		return status;
	}
	public void setStatus(RequestToHireStatus status) {
		this.status = status;
	}
	public String getDateRequestedFormatted() {
		if(this.getDateRequested() == null){
			return "";
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			return sdf.format(this.getDateRequested());
		}
	}
	public String getDateVacatedFormatted() {
		if(this.getDateVacated() == null){
			return "";
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			return sdf.format(this.getDateVacated());
		}
	}	
	public String getStartDateFormatted() {

		if(this.getStartDate() == null){
			return "";
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			return sdf.format(this.getStartDate());
		}
	}
	public String getStartDateFormattedRec() {

		if(this.getStartDate() == null){
			return "";
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_REC);
			return sdf.format(this.getStartDate());
		}
	}
	public String getDdApprovedFormatted() {
		if(this.getDdApproved() == null){
			return "";
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			return sdf.format(this.getDdApproved());
		}
	}
	public String getBcApprovedFormatted() {
		if(this.getBcApproved() == null){
			return "";
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			return sdf.format(this.getBcApproved());
		}
	}
	public String getAdApprovedFormatted() {
		if(this.getAdApproved() == null){
			return "";
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			return sdf.format(this.getAdApproved());
		}
	}
	public String getAdhrApprovedFormatted() {
		if(this.getAdhrApproved() == null){
			return "";
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			return sdf.format(this.getAdhrApproved());
		}
	}
	public String getEndDateFormatted() {

		if(this.getEndDate() == null){
			return "";
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			return sdf.format(this.getEndDate());
		}
	}
	public String getEndDateFormattedRec() {

		if(this.getEndDate() == null){
			return "";
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_REC);
			return sdf.format(this.getEndDate());
		}
	}
	public String getCompetitionNumber() {
		return CompetitionNumber;
	}
	public void setCompetitionNumber(String competitionNumber) {
		CompetitionNumber = competitionNumber;
	}
	public String getPositionTypeString(){
		return RTHPositionTypeConstant.get(this.positionType).getDescription();
	}
	public String getDivisionString(){
		String dString="";
		switch(this.division){
		case 1:
			dString="Programs";
			break;
		
		case 2:
			dString="Finance - Information Technology";
			break;
		case 3:
			dString="Finance - Procurement and Business Services";
			break;
		case 4:
			dString="Finance - Student Transportation";
			break;
		case 5:
			dString="Facilities";
			break;
		case 6:
			dString="Human Resources";
			break;
		case 7:
			dString="Human Resources - Casuals/Student Assistant/Teacher Aides";
			break;				
		default:
			dString="";
			break;
		}
		return dString;
	}
	public String getDivisionStringShort(){
		String dString="";
		switch(this.division){
		case 1:
			dString="PRO";
			break;
		
		case 2:
			dString="IT";
			break;
		case 3:
			dString="PBS";
			break;
		case 4:
			dString="ST";
			break;
		case 5:
			dString="FAC";
			break;
		case 6:
			dString="HR";
			break;
		case 7:
			dString="HR";
			break;
		default:
			dString="";
			break;
		}
		return dString;
	}
	public int getDdApprovedById() {
		return ddApprovedById;
	}
	public void setDdApprovedById(int ddApprovedById) {
		this.ddApprovedById = ddApprovedById;
	}
	public int getBcApprovedById() {
		return bcApprovedById;
	}
	public void setBcApprovedById(int bcApprovedById) {
		this.bcApprovedById = bcApprovedById;
	}
	public int getAdApprovedById() {
		return adApprovedById;
	}
	public void setAdApprovedById(int adApprovedById) {
		this.adApprovedById = adApprovedById;
	}
	public int getAdhrApprovedById() {
		return adhrApprovedById;
	}
	public void setAdhrApprovedById(int adhrApprovedById) {
		this.adhrApprovedById = adhrApprovedById;
	}
	public int getRequestById() {
		return requestById;
	}
	public void setRequestById(int requestById) {
		this.requestById = requestById;
	}
	public String getSupervisorName() {
		return supervisorName;
	}
	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}
	public String getZoneName(){
		String zonename="";
		try {
			zonename = SchoolDB.getSchool(Integer.parseInt(this.workLocation)).getZone().getZoneName();
		} catch (SchoolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return zonename;
	}
	public int getUnionCode() {
		return unionCode;
	}
	public void setUnionCode(int unionCode) {
		this.unionCode = unionCode;
	}
	public String getUnionCodeString() {
		return unionCodeString;
	}
	public void setUnionCodeString(String unionCodeString) {
		this.unionCodeString = unionCodeString;
	}
	public int getPositionName() {
		return positionName;
	}
	public void setPositionName(int positionName) {
		this.positionName = positionName;
	}
	public String getPositionNameString() {
		return positionNameString;
	}
	public void setPositionNameString(String positionNameString) {
		this.positionNameString = positionNameString;
	}
	public int getPositionTerm() {
		return positionTerm;
	}
	public void setPositionTerm(int positionTerm) {
		this.positionTerm = positionTerm;
	}
	public String getPositionTermString() {
		return positionTermString;
	}
	public void setPositionTermString(String positionTermString) {
		this.positionTermString = positionTermString;
	}
	public String getPositionHours() {
		return positionHours;
	}
	public void setPositionHours(String positionHours) {
		this.positionHours = positionHours;
	}
	public String getDeclinedText(){
		StringBuilder sb = new StringBuilder();
		try {
		if(this.getStatus() == RequestToHireStatus.REJECTED){
			if(this.getAdhrApprovedBy() != null){
				sb.append(" by ");
				sb.append(this.getAdhrApprovedBy());
				sb.append(" on ");
				sb.append(this.getAdhrApprovedFormatted());
			}else if(this.getAdApprovedBy() != null){
				sb.append(" by ");
				sb.append(this.getAdApprovedBy());
				sb.append(" on ");
				sb.append(this.getAdApprovedFormatted());
			}else if(this.getBcApprovedBy() != null){
				sb.append(" by ");
				sb.append(this.getBcApprovedBy());
				sb.append(" on ");
				sb.append(this.getBcApprovedFormatted());
			}else if(this.getDdApprovedBy() != null){
				sb.append(" by ");
				sb.append(this.getDdApprovedBy());
				sb.append(" on ");
				sb.append(this.getDdApprovedFormatted());
			}else{
				sb.append("");
			}
		}} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();
	}
	public int getShiftDiff() {
		return shiftDiff;
	}
	public void setShiftDiff(int shiftDiff) {
		this.shiftDiff = shiftDiff;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getRequestTypeString() {
		return requestTypeString;
	}
	public void setRequestTypeString(String requestTypeString) {
		this.requestTypeString = requestTypeString;
	}
	public String getPositionNumber() {
		return positionNumber;
	}
	public void setPositionNumber(String positionNumber) {
		this.positionNumber = positionNumber;
	}
	public Date getEndDate() {
		return EndDate;
	}
	public void setEndDate(Date endDate) {
		EndDate = endDate;
	}
	public int getPrivateList() {
		return privateList;
	}
	public void setPrivateList(int privateList) {
		this.privateList = privateList;
	}
	@Override
    public boolean equals(Object obj) {
        if (obj instanceof RequestToHireBean) {
            return ((RequestToHireBean) obj).id == id;
        }
        return false;
    }
	@Override
    public int hashCode() {
        return this.id;
    }
}
