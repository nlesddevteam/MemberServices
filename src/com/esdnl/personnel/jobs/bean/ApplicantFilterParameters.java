package com.esdnl.personnel.jobs.bean;

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

}
