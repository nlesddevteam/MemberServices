package com.nlesd.psimport.bean;

public class PSK9ClassBean {
	private int id;
	private String schoolNumber;
	private String gradeLevel;
	private String sectionNumber;
	private int numberOfStudents;
	private String gradeLevels;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSchoolNumber() {
		return schoolNumber;
	}
	public void setSchoolNumber(String schoolNumber) {
		this.schoolNumber = schoolNumber;
	}
	public String getGradeLevel() {
		return gradeLevel;
	}
	public void setGradeLevel(String gradeLevel) {
		this.gradeLevel = gradeLevel;
	}
	public String getSectionNumber() {
		return sectionNumber;
	}
	public void setSectionNumber(String sectionNumber) {
		this.sectionNumber = sectionNumber;
	}
	public int getNumberOfStudents() {
		return numberOfStudents;
	}
	public void setNumberOfStudents(int numberOfStudents) {
		this.numberOfStudents = numberOfStudents;
	}
	public String getGradeSection() {
		return gradeLevel + "-" + sectionNumber;
	}
	public String getGradesString() {
		StringBuilder formattedstring=new StringBuilder();
		
		if(!(this.gradeLevels == null)) {
			if(gradeLevels.contains((","))){
				//more than one grade in hr
				String[] testing = gradeLevels.split(",");
				for(String s : testing) {
					//we check for 0 and switch to K
					String ss ="";
					if(s.equals("0")) {
						ss = "K";
					}else {
						ss=s;
					}
					if(formattedstring.length() == 0) {
						formattedstring.append(ss);
					}else {
						formattedstring.append(",");
						formattedstring.append(ss);
					}
				}
			}else {
				formattedstring.append(this.gradeLevels);
			}
		}
		
		return formattedstring.toString();
	}
	public String getGradeLevels() {
		return gradeLevels;
	}
	public void setGradeLevels(String gradeLevels) {
		this.gradeLevels = gradeLevels;
	}
}
