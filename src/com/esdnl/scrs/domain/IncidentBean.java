package com.esdnl.scrs.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.awsd.personnel.Personnel;
import com.awsd.school.School;
import com.esdnl.school.bean.StudentRecordBean;

public class IncidentBean {

	private int incidentId;
	private Date incidentDate;
	private Personnel submittedBy;
	private Date submittedDate;
	private StudentRecordBean student;
	private School school;
	private int studentAge;
	private School.GRADE studentGrade;

	private int numberIncidents;
	private boolean homeContactMade;
	private boolean followUpRequired;
	private String additionalInfo;

	private ArrayList<BullyingBehaviorType> bullyingBehaviorTypes;
	private ArrayList<BullyingReasonType> bullyingReasonTypes;
	private ArrayList<IllegalSubstanceType> illegalSubstanceTypes;
	private ArrayList<SexualBehaviourType> sexualBehaviourTypes;
	private ArrayList<ThreateningBehaviorType> threateningBehaviorTypes;
	private ArrayList<SchoolSafetyIssueType> schoolSafetyIssueTypes;
	private ArrayList<LocationType> locationTypes;
	private ArrayList<TimeType> timeTypes;
	private ArrayList<TargetType> targetTypes;
	private ArrayList<ActionType> actionTypes;

	public IncidentBean() {

		super();
	}

	public int getIncidentId() {

		return incidentId;
	}

	public void setIncidentId(int incidentId) {

		this.incidentId = incidentId;
	}

	public Date getIncidentDate() {

		return incidentDate;
	}

	public String getIncidentDateFormatted() {

		return (new SimpleDateFormat("MM/dd/yyyy")).format(this.incidentDate);
	}

	public void setIncidentDate(Date incidentDate) {

		this.incidentDate = incidentDate;
	}

	public Personnel getSubmittedBy() {

		return submittedBy;
	}

	public void setSubmittedBy(Personnel submittedBy) {

		this.submittedBy = submittedBy;
	}

	public Date getSubmittedDate() {

		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {

		this.submittedDate = submittedDate;
	}

	public StudentRecordBean getStudent() {

		return student;
	}

	public void setStudent(StudentRecordBean student) {

		this.student = student;
	}

	public School getSchool() {

		return school;
	}

	public void setSchool(School school) {

		this.school = school;
	}

	public int getStudentAge() {

		return studentAge;
	}

	public void setStudentAge(int studentAge) {

		this.studentAge = studentAge;
	}

	public School.GRADE getStudentGrade() {

		return studentGrade;
	}

	public void setStudentGrade(School.GRADE studentGrade) {

		this.studentGrade = studentGrade;
	}

	public int getNumberIncidents() {

		return numberIncidents;
	}

	public void setNumberIncidents(int numberIncidents) {

		this.numberIncidents = numberIncidents;
	}

	public boolean isHomeContactMade() {

		return homeContactMade;
	}

	public void setHomeContactMade(boolean homeContactMade) {

		this.homeContactMade = homeContactMade;
	}

	public boolean isFollowUpRequired() {

		return followUpRequired;
	}

	public void setFollowUpRequired(boolean followUpRequired) {

		this.followUpRequired = followUpRequired;
	}

	public String getAdditionalInfo() {

		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {

		this.additionalInfo = additionalInfo;
	}

	public ArrayList<BullyingBehaviorType> getBullyingBehaviorTypes() {

		if (this.bullyingBehaviorTypes == null)
			this.bullyingBehaviorTypes = new ArrayList<BullyingBehaviorType>();

		return bullyingBehaviorTypes;
	}

	public void setBullyingBehaviorTypes(ArrayList<BullyingBehaviorType> bullyingBehaviorTypes) {

		this.bullyingBehaviorTypes = bullyingBehaviorTypes;
	}

	public ArrayList<BullyingReasonType> getBullyingReasonTypes() {

		if (this.bullyingReasonTypes == null)
			this.bullyingReasonTypes = new ArrayList<BullyingReasonType>();

		return bullyingReasonTypes;
	}

	public void setBullyingReasonTypes(ArrayList<BullyingReasonType> bullyingReasonTypes) {

		this.bullyingReasonTypes = bullyingReasonTypes;
	}

	public ArrayList<IllegalSubstanceType> getIllegalSubstanceTypes() {

		if (this.illegalSubstanceTypes == null)
			this.illegalSubstanceTypes = new ArrayList<IllegalSubstanceType>();

		return illegalSubstanceTypes;
	}

	public void setIllegalSubstanceTypes(ArrayList<IllegalSubstanceType> illegalSubstanceTypes) {

		this.illegalSubstanceTypes = illegalSubstanceTypes;
	}

	public ArrayList<SexualBehaviourType> getSexualBehaviourTypes() {

		if (this.sexualBehaviourTypes == null)
			this.sexualBehaviourTypes = new ArrayList<SexualBehaviourType>();

		return sexualBehaviourTypes;
	}

	public void setSexualBehaviourTypes(ArrayList<SexualBehaviourType> sexualBehaviourTypes) {

		this.sexualBehaviourTypes = sexualBehaviourTypes;
	}

	public ArrayList<ThreateningBehaviorType> getThreateningBehaviorTypes() {

		if (this.threateningBehaviorTypes == null)
			this.threateningBehaviorTypes = new ArrayList<ThreateningBehaviorType>();

		return threateningBehaviorTypes;
	}

	public void setThreateningBehaviorTypes(ArrayList<ThreateningBehaviorType> threateningBehaviorTypes) {

		this.threateningBehaviorTypes = threateningBehaviorTypes;
	}

	public ArrayList<SchoolSafetyIssueType> getSchoolSafetyIssueTypes() {

		if (this.schoolSafetyIssueTypes == null)
			this.schoolSafetyIssueTypes = new ArrayList<SchoolSafetyIssueType>();

		return schoolSafetyIssueTypes;
	}

	public void setSchoolSafetyIssueTypes(ArrayList<SchoolSafetyIssueType> schoolSafetyIssueTypes) {

		this.schoolSafetyIssueTypes = schoolSafetyIssueTypes;
	}

	public ArrayList<LocationType> getLocationTypes() {

		if (this.locationTypes == null)
			this.locationTypes = new ArrayList<LocationType>();

		return locationTypes;
	}

	public void setLocationTypes(ArrayList<LocationType> locationTypes) {

		this.locationTypes = locationTypes;
	}

	public ArrayList<TimeType> getTimeTypes() {

		if (this.timeTypes == null)
			this.timeTypes = new ArrayList<TimeType>();

		return timeTypes;
	}

	public void setTimeTypes(ArrayList<TimeType> timeTypes) {

		this.timeTypes = timeTypes;
	}

	public ArrayList<TargetType> getTargetTypes() {

		if (this.targetTypes == null)
			this.targetTypes = new ArrayList<TargetType>();

		return targetTypes;
	}

	public void setTargetTypes(ArrayList<TargetType> targetTypes) {

		this.targetTypes = targetTypes;
	}

	public ArrayList<ActionType> getActionTypes() {

		if (this.actionTypes == null)
			this.actionTypes = new ArrayList<ActionType>();

		return actionTypes;
	}

	public void setActionTypes(ArrayList<ActionType> interventionTypes) {

		this.actionTypes = interventionTypes;
	}

}
