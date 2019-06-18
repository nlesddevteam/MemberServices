package com.esdnl.personnel.jobs.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.esdnl.personnel.jobs.constants.TrainingMethodConstant;

public class ApplicantEducationOtherBean implements Serializable {

	private static final long serialVersionUID = 2530643762645377136L;
	private int id;
	private String sin;
	private TrainingMethodConstant trnlvl;
	private int sped_crs;
	private int fr_crs;
	private int math_crs;
	private int eng_crs;
	private String cert_lvl;
	private Date cert_date;
	private int music_crs;
	private int tech_crs;
	private int science_crs;
	private int total_crs_completed;

	public ApplicantEducationOtherBean() {

		this.id = -1;
		this.sin = null;
		this.trnlvl = null;
		this.sped_crs = 0;
		this.fr_crs = 0;
		this.math_crs = 0;
		this.eng_crs = 0;
		this.cert_lvl = null;
		this.cert_date = null;
		this.music_crs = 0;
		this.tech_crs = 0;
		this.science_crs = 0;
		this.total_crs_completed = 0;
	}

	public int getId() {

		return this.id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public String getSIN() {

		return this.sin;
	}

	public void setSIN(String sin) {

		this.sin = sin;
	}

	public TrainingMethodConstant getProfessionalTrainingLevel() {

		return this.trnlvl;
	}

	public void setProfessionalTrainingLevel(TrainingMethodConstant trnlvl) {

		this.trnlvl = trnlvl;
	}

	public int getNumberSpecialEducationCourses() {

		return this.sped_crs;
	}

	public void setNumberSpecialEducationCourses(int sped_crs) {

		this.sped_crs = sped_crs;
	}

	public int getNumberFrenchCourses() {

		return this.fr_crs;
	}

	public void setNumberFrenchCourses(int fr_crs) {

		this.fr_crs = fr_crs;
	}

	public int getNumberMathCourses() {

		return this.math_crs;
	}

	public void setNumberMathCourses(int math_crs) {

		this.math_crs = math_crs;
	}

	public int getNumberEnglishCourses() {

		return this.eng_crs;
	}

	public void setNumberEnglishCourses(int eng_crs) {

		this.eng_crs = eng_crs;
	}

	public String getTeachingCertificateLevel() {

		return this.cert_lvl;
	}

	public void setTeachingCertificateLevel(String cert_lvl) {

		this.cert_lvl = cert_lvl;
	}

	public Date getTeachingCertificateIssuedDate() {

		return this.cert_date;
	}

	public String getFormatedTeachingCertificateIssuedDate() {

		String formatted = "";

		if (this.getTeachingCertificateIssuedDate() != null) {
			formatted = (new SimpleDateFormat("dd/MM/yyyy")).format(this.getTeachingCertificateIssuedDate());
		}

		return formatted;
	}

	public void setTeachingCertificateIssuedDate(Date cert_date) {

		this.cert_date = cert_date;
	}

	public void setNumberMusicCourses(int music_crs) {

		this.music_crs = music_crs;
	}

	public int getNumberMusicCourses() {

		return this.music_crs;
	}

	public void setNumberTechnologyCourses(int tech_crs) {

		this.tech_crs = tech_crs;
	}

	public int getNumberTechnologyCourses() {

		return this.tech_crs;
	}

	public void setNumberScienceCourses(int science_crs) {

		this.science_crs = science_crs;
	}

	public int getNumberScienceCourses() {

		return science_crs;
	}

	public void setTotalCoursesCompleted(int total_crs_completed) {

		this.total_crs_completed = total_crs_completed;
	}

	public int getTotalCoursesCompleted() {

		return this.total_crs_completed;
	}

	public String generateXML() {

		StringBuffer sb = new StringBuffer();

		sb.append("<EDUCATION-OTHER>");

		sb.append("<TRAINING-METHOD>" + this.getProfessionalTrainingLevel().getDescription() + "</TRAINING-METHOD>");
		sb.append("<SPECIAL-EDUCATION-COURSES>" + this.getNumberSpecialEducationCourses() + "</SPECIAL-EDUCATION-COURSES>");
		sb.append("<FRENCH-COURSES>" + this.getNumberFrenchCourses() + "</FRENCH-COURSES>");
		sb.append("<ENGLISH-COURSES>" + this.getNumberEnglishCourses() + "</ENGLISH-COURSES>");
		sb.append("<MATH-COURSES>" + this.getNumberMathCourses() + "</MATH-COURSES>");
		sb.append("<TEACHING-CERTIFICATE-LEVEL>" + this.getTeachingCertificateLevel() + "</TEACHING-CERTIFICATE-LEVEL>");
		sb.append("<TEACHING-CERTIFICATE-ISSUED-DATE>" + this.getFormatedTeachingCertificateIssuedDate()
				+ "</TEACHING-CERTIFICATE-ISSUED-DATE>");
		sb.append("<MUSIC-COURSES>" + this.getNumberMusicCourses() + "</MUSIC-COURSES>");
		sb.append("<TECHNOLOGY-COURSES>" + this.getNumberTechnologyCourses() + "</TECHNOLOGY-COURSES>");
		sb.append("<SCIENCE-COURSES>" + this.getNumberScienceCourses() + "</SCIENCE-COURSES>");
		sb.append("<TOTAL-COURSES-COMPLETED>" + this.getTotalCoursesCompleted() + "</TOTAL-COURSES-COMPLETED>");

		sb.append("</EDUCATION-OTHER>");

		return sb.toString();
	}
}