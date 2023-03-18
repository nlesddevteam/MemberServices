package com.esdnl.personnel.jobs.bean;

import java.util.Arrays;

import com.esdnl.personnel.jobs.constants.TrainingMethodConstant;
import com.esdnl.personnel.jobs.dao.ApplicantFilterParametersManager;

public class ApplicantFilterParameters {

	private JobOpportunityBean job = null;
	private SubListBean subList = null;

	private int months = 0;
	private int days = 0;
	private int specialEducationCourses = 0;
	private int frenchCourses = 0;
	private int mathCourses = 0;
	private int englishCourses = 0;
	private int musicCourses = 0;
	private int technologyCourses = 0;
	private int scienceCourses = 0;
	private int socialstudiesCourses = 0;
	private int artCourses = 0;
	private String[] degrees = null;
	private String[] majors = null;
	private String[] minors = null;
	private String[] trainingMethods = null;
	private String permanentContract = null;
	private int[] regionalPreferences = null;
	private int permanentExp = 0;
	private int replacementExp = 0;
	private int subDays = 0;
	private int totalExp = 0;
	private boolean tlaRequirements = false;
	private String[] majorsSubjectGroup = null;
	private String[] minorsSubjectGroup = null;
	//used for saving filter options with shortlisting
	private String applicantId;
	private int shortlistedBy;
	private String shortlistReason;
	private boolean delfDocument;
	public ApplicantFilterParameters() {

	}

	public JobOpportunityBean getJob() {

		return job;
	}

	public void setJob(JobOpportunityBean job) {

		this.job = job;
	}

	public SubListBean getSubList() {

		return subList;
	}

	public void setSubList(SubListBean subList) {

		this.subList = subList;
	}

	public int getMonths() {

		return months;
	}

	public void setMonths(int months) {

		this.months = months;
	}

	public int getDays() {

		return days;
	}

	public void setDays(int days) {

		this.days = days;
	}

	public int getSpecialEducationCourses() {

		return specialEducationCourses;
	}

	public void setSpecialEducationCourses(int specialEducationCourses) {

		this.specialEducationCourses = specialEducationCourses;
	}

	public int getFrenchCourses() {

		return frenchCourses;
	}

	public void setFrenchCourses(int frenchCourses) {

		this.frenchCourses = frenchCourses;
	}

	public int getMathCourses() {

		return mathCourses;
	}

	public void setMathCourses(int mathCourses) {

		this.mathCourses = mathCourses;
	}

	public int getEnglishCourses() {

		return englishCourses;
	}

	public void setEnglishCourses(int englishCourses) {

		this.englishCourses = englishCourses;
	}

	public int getMusicCourses() {

		return musicCourses;
	}

	public void setMusicCourses(int musicCourses) {

		this.musicCourses = musicCourses;
	}

	public int getTechnologyCourses() {

		return technologyCourses;
	}

	public void setTechnologyCourses(int technologyCourses) {

		this.technologyCourses = technologyCourses;
	}

	public int getScienceCourses() {

		return scienceCourses;
	}

	public void setScienceCourses(int scienceCourses) {

		this.scienceCourses = scienceCourses;
	}
	
	public int getSocialStudiesCourses() {

		return socialstudiesCourses;
	}

	public void setSocialStudiesCourses(int socialstudiesCourses) {

		this.socialstudiesCourses = socialstudiesCourses;
	}
	
	public int getArtCourses() {

		return artCourses;
	}

	public void setArtCourses(int artCourses) {

		this.artCourses = artCourses;
	}

	public String[] getDegrees() {

		return degrees;
	}

	public void setDegrees(String[] degrees) {

		this.degrees = degrees;
	}

	public String[] getMajors() {

		return majors;
	}

	public void setMajors(String[] majors) {

		this.majors = majors;
	}

	public String[] getMinors() {

		return minors;
	}

	public void setMinors(String[] minors) {

		this.minors = minors;
	}

	public String[] getTrainingMethods() {

		return trainingMethods;
	}

	public void setTrainingMethods(String[] trainingMethods) {

		this.trainingMethods = trainingMethods;
	}

	public String getPermanentContract() {

		return permanentContract;
	}

	public void setPermanentContract(String permanentContract) {

		this.permanentContract = permanentContract;
	}

	public int[] getRegionalPreferences() {

		return regionalPreferences;
	}

	public void setRegionalPreferences(int[] regionalPreferences) {

		this.regionalPreferences = regionalPreferences;
	}

	public int getPermanentExp() {

		return permanentExp;
	}

	public void setPermanentExp(int permanentExp) {

		this.permanentExp = permanentExp;
	}

	public int getReplacementExp() {

		return replacementExp;
	}

	public void setReplacementExp(int replacementExp) {

		this.replacementExp = replacementExp;
	}

	public int getSubDays() {

		return subDays;
	}

	public void setSubDays(int subDays) {

		this.subDays = subDays;
	}

	public int getTotalExp() {

		return totalExp;
	}

	public void setTotalExp(int totalExp) {

		this.totalExp = totalExp;
	}

	public void setTLARequirements(boolean tlaRequirements) {

		this.tlaRequirements = tlaRequirements;
	}

	public boolean isTLARequirements() {

		return this.tlaRequirements;
	}
	public String getTrainingString() {
		StringBuilder sb = null;
		if(this.trainingMethods != null){
			for(String s : this.trainingMethods) {
				if(sb == null) {
					sb = new StringBuilder();
					sb.append(TrainingMethodConstant.get(Integer.parseInt(s)).getDescription());
				}else {
					sb.append(",");
					sb.append(TrainingMethodConstant.get(Integer.parseInt(s)).getDescription());
				}
			}
		}else {
			sb= new StringBuilder();
			sb.append("None Selected");
		}
		return sb.toString();
	}
	public String getDegreesString() {
		if(this.degrees != null) {
			String indegrees = String.join(",", this.degrees);
			return ApplicantFilterParametersManager.getDegreesString("'" + indegrees.replace(",", "','") + "'");
				
		}else {
				return "None Selected";
		}
	}
	public String getMajorsSubjectGroupsString() {
		if(this.majorsSubjectGroup != null) {
			String indegrees = String.join(",", this.majorsSubjectGroup);
			return ApplicantFilterParametersManager.getSubjectGroupString(indegrees);
				
		}else {
				return "None Selected";
		}
	}
	public String getMajorsString() {
		if(this.majors != null) {
			String indegrees = String.join(",", this.majors);
			return ApplicantFilterParametersManager.getSubjectString(indegrees);
				
		}else {
				return "None Selected";
		}
	}
	public String getMinorsString() {
		if(this.minors != null) {
			String indegrees = String.join(",", this.minors);
			return ApplicantFilterParametersManager.getSubjectString(indegrees);
				
		}else {
				return "None Selected";
		}
	}
	public String getMinorsSubjectGroupsString() {
		if(this.minorsSubjectGroup != null) {
			String indegrees = String.join(",", this.minorsSubjectGroup );
			return ApplicantFilterParametersManager.getSubjectGroupString(indegrees);
				
		}else {
				return "None Selected";
		}
	}
	public String getRegionsString() {
		if(this.regionalPreferences != null) {
			String strArray[] = Arrays.stream(this.regionalPreferences)
                    .mapToObj(String::valueOf)
                    .toArray(String[]::new);
			String indegrees = String.join(",", strArray );
			return ApplicantFilterParametersManager.getRegionsString(indegrees);
				
		}else {
				return "None Selected";
		}
	}
	public String[] getMajorsSubjectGroup() {
		return majorsSubjectGroup;
	}

	public void setMajorsSubjectGroup(String[] majorsSubjectGroup) {
		this.majorsSubjectGroup = majorsSubjectGroup;
	}

	public String[] getMinorsSubjectGroup() {
		return minorsSubjectGroup;
	}

	public void setMinorsSubjectGroup(String[] minorsSubjectGroup) {
		this.minorsSubjectGroup = minorsSubjectGroup;
	}

	public String getApplicantId() {
		return applicantId;
	}

	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}
	public String getShortlistReason() {
		return shortlistReason;
	}

	public void setShortlistReason(String shortlistReason) {
		this.shortlistReason = shortlistReason;
	}

	public int getShortlistedBy() {
		return shortlistedBy;
	}

	public void setShortlistedBy(int shortlistedBy) {
		this.shortlistedBy = shortlistedBy;
	}

	public boolean isDelfDocument() {
		return delfDocument;
	}

	public void setDelfDocument(boolean delfDocument) {
		this.delfDocument = delfDocument;
	}

}
