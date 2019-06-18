package com.esdnl.personnel.jobs.bean;
import java.util.Date;
public class ApplicantFilterParametersSS {
	private JobOpportunityBean job = null;
	private String currentEmployee = null;
	private String senorityDateFilter = null;
	private Date senorityDate=null;
	private String currentPosition=null;
	private String currentPositionType=null;
	private String[] degrees = null;
	private String[] certificates = null;
	private String[] diplomas = null;
	private boolean driversAbstract;
	private boolean ohsTraining;
	private boolean codeOfConduct;
	private boolean firstAid;
	private boolean whmis;
	private boolean driversLicense;
	private int unionCode;
	private int currentUnionPosition;
	public JobOpportunityBean getJob() {
		return job;
	}
	public void setJob(JobOpportunityBean job) {
		this.job = job;
	}
	public String getCurrentEmployee() {
		return currentEmployee;
	}
	public void setCurrentEmployee(String currentEmployee) {
		this.currentEmployee = currentEmployee;
	}
	public String getSenorityDateFilter() {
		return senorityDateFilter;
	}
	public void setSenorityDateFilter(String senorityDateFilter) {
		this.senorityDateFilter = senorityDateFilter;
	}
	public Date getSenorityDate() {
		return senorityDate;
	}
	public void setSenorityDate(Date senorityDate) {
		this.senorityDate = senorityDate;
	}
	public String getCurrentPosition() {
		return currentPosition;
	}
	public void setCurrentPosition(String currentPosition) {
		this.currentPosition = currentPosition;
	}
	public String getCurrentPositionType() {
		return currentPositionType;
	}
	public void setCurrentPositionType(String currentPositionType) {
		this.currentPositionType = currentPositionType;
	}
	public String[] getDegrees() {
		return degrees;
	}
	public void setDegrees(String[] degrees) {
		this.degrees = degrees;
	}
	public String[] getCertificates() {
		return certificates;
	}
	public void setCertificates(String[] certificates) {
		this.certificates = certificates;
	}
	public String[] getDiplomas() {
		return diplomas;
	}
	public void setDiplomas(String[] diplomas) {
		this.diplomas = diplomas;
	}
	public boolean isDriversAbstract() {
		return driversAbstract;
	}
	public void setDriversAbstract(boolean driversAbstract) {
		this.driversAbstract = driversAbstract;
	}
	public boolean isOhsTraining() {
		return ohsTraining;
	}
	public void setOhsTraining(boolean ohsTraining) {
		this.ohsTraining = ohsTraining;
	}
	public boolean isCodeOfConduct() {
		return codeOfConduct;
	}
	public void setCodeOfConduct(boolean codeOfConduct) {
		this.codeOfConduct = codeOfConduct;
	}
	public boolean isFirstAid() {
		return firstAid;
	}
	public void setFirstAid(boolean firstAid) {
		this.firstAid = firstAid;
	}
	public boolean isWhmis() {
		return whmis;
	}
	public void setWhmis(boolean whmis) {
		this.whmis = whmis;
	}
	public boolean isDriversLicense() {
		return driversLicense;
	}
	public void setDriversLicense(boolean driverLicense) {
		this.driversLicense = driverLicense;
	}
	public int getUnionCode() {
		return unionCode;
	}
	public void setUnionCode(int unionCode) {
		this.unionCode = unionCode;
	}
	public int getCurrentUnionPosition() {
		return currentUnionPosition;
	}
	public void setCurrentUnionPosition(int currentUnionPosition) {
		this.currentUnionPosition = currentUnionPosition;
	}
	
}
