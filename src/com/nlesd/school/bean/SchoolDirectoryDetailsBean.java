package com.nlesd.school.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.awsd.personnel.Personnel;

public class SchoolDirectoryDetailsBean implements Serializable {

	private static final long serialVersionUID = -3914355675665585056L;

	private int directoryId;
	private int schoolId;
	private Personnel trustee;
	private int electorialZone;
	private boolean accessible;
	private boolean frenchImmersion;
	private String busRoutesFilename;
	private String catchmentAreaFilename;
	private String schoolReportFilename;
	private String schoolPhotoFilename;
	private String schoolCrestFilename;
	private String youtubeUrl;
	private String twitterUrl;
	private String facebookUrl;
	private String schoolStartTime;
	private String schoolEndTime;
	private String schoolOpening;
	private String kindergartenTimes;
	private String kinderstartTimes;
	private String otherInfo;
	private String secretaries;
	private String googleMapUrl;
	private String catchmentMapUrl;
	private Date lastModified;

	private ArrayList<SchoolSpecialReportBean> specialReports;

	public SchoolDirectoryDetailsBean() {

	}

	public int getDirectoryId() {

		return directoryId;
	}

	public void setDirectoryId(int directoryId) {

		this.directoryId = directoryId;
	}

	public int getSchoolId() {

		return schoolId;
	}

	public void setSchoolId(int schoolId) {

		this.schoolId = schoolId;
	}

	public String getSecretaries() {

		return secretaries;
	}

	public void setSecretaries(String secretaries) {

		this.secretaries = secretaries;
	}

	public Personnel getTrustee() {

		return trustee;
	}

	public void setTrustee(Personnel trustee) {

		this.trustee = trustee;
	}

	public int getElectorialZone() {

		return electorialZone;
	}

	public void setElectorialZone(int electorialZone) {

		this.electorialZone = electorialZone;
	}

	public boolean isAccessible() {

		return accessible;
	}

	public void setAccessible(boolean accessible) {

		this.accessible = accessible;
	}

	public boolean isFrenchImmersion() {

		return frenchImmersion;
	}

	public void setFrenchImmersion(boolean frenchImmersion) {

		this.frenchImmersion = frenchImmersion;
	}

	public String getBusRoutesFilename() {

		return busRoutesFilename;
	}

	public void setBusRoutesFilename(String busRoutesFilename) {

		this.busRoutesFilename = busRoutesFilename;
	}

	public String getCatchmentAreaFilename() {

		return catchmentAreaFilename;
	}

	public void setCatchmentAreaFilename(String catchmentAreaFilename) {

		this.catchmentAreaFilename = catchmentAreaFilename;
	}

	public String getSchoolReportFilename() {

		return schoolReportFilename;
	}

	public void setSchoolReportFilename(String schoolReportFilename) {

		this.schoolReportFilename = schoolReportFilename;
	}

	public String getSchoolPhotoFilename() {

		return schoolPhotoFilename;
	}

	public void setSchoolPhotoFilename(String schoolPhotoFilename) {

		this.schoolPhotoFilename = schoolPhotoFilename;
	}

	public String getSchoolCrestFilename() {

		return schoolCrestFilename;
	}

	public void setSchoolCrestFilename(String schoolCrestFilename) {

		this.schoolCrestFilename = schoolCrestFilename;
	}

	public String getYoutubeUrl() {

		return youtubeUrl;
	}

	public void setYoutubeUrl(String youtubeUrl) {

		this.youtubeUrl = youtubeUrl;
	}

	public String getTwitterUrl() {

		return twitterUrl;
	}

	public void setTwitterUrl(String twitterUrl) {

		this.twitterUrl = twitterUrl;
	}

	public String getFacebookUrl() {

		return facebookUrl;
	}

	public void setFacebookUrl(String facebookUrl) {

		this.facebookUrl = facebookUrl;
	}

	public String getSchoolStartTime() {

		return schoolStartTime;
	}

	public void setSchoolStartTime(String schoolStartTime) {

		this.schoolStartTime = schoolStartTime;
	}

	public String getSchoolEndTime() {

		return schoolEndTime;
	}

	public void setSchoolEndTime(String schoolEndTime) {

		this.schoolEndTime = schoolEndTime;
	}

	public String getSchoolOpening() {

		return schoolOpening;
	}

	public void setSchoolOpening(String schoolOpening) {

		this.schoolOpening = schoolOpening;
	}

	public String getKindergartenTimes() {

		return kindergartenTimes;
	}

	public void setKindergartenTimes(String kindergartenTimes) {

		this.kindergartenTimes = kindergartenTimes;
	}

	public String getKinderstartTimes() {

		return kinderstartTimes;
	}

	public void setKinderstartTimes(String kinderstartTimes) {

		this.kinderstartTimes = kinderstartTimes;
	}

	public String getOtherInfo() {

		return otherInfo;
	}

	public void setOtherInfo(String otherInfo) {

		this.otherInfo = otherInfo;
	}

	public String getGoogleMapUrl() {

		return googleMapUrl;
	}

	public void setGoogleMapUrl(String googleMapUrl) {

		this.googleMapUrl = googleMapUrl;
	}

	public String getCatchmentMapUrl() {

		return catchmentMapUrl;
	}

	public void setCatchmentMapUrl(String catchmentMapUrl) {

		this.catchmentMapUrl = catchmentMapUrl;
	}

	public Date getLastModified() {

		return lastModified;
	}

	public void setLastModified(Date lastModified) {

		this.lastModified = lastModified;
	}

	public ArrayList<SchoolSpecialReportBean> getSpecialReports() {

		return specialReports;
	}

	public void setSpecialReports(ArrayList<SchoolSpecialReportBean> specialReports) {

		this.specialReports = specialReports;
	}

}
