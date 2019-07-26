package com.nlesd.bcs.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.nlesd.bcs.constants.VehicleStatusConstant;
import com.nlesd.bcs.dao.DropdownManager;

public class BussingContractorVehicleBean implements Serializable {

	private static final long serialVersionUID = -844016122427000813L;
	private int id;
	private int vMake;
	private int vModel;
	private String vYear;
	private String vSerialNumber;
	private String vPlateNumber;
	private int vType;
	private int vSize;
	private String vOwner;
	private Date regExpiryDate;
	private Date insExpiryDate;
	private String insuranceProvider;
	private Date fallInsDate;
	private Date winterInsDate;
	private Date fallHeInsDate;
	private Date miscHeInsDate1;
	private Date miscHeInsDate2;
	private int contractorId;
	private int vStatus;
	private String approvedBy;
	private Date dateApproved;
	private String isDeleted;
	private BussingContractorBean bcBean;
	private String statusNotes;
	private String warningNotes;//used with main screen warnings for contractors and automated ones
	private String vModel2;//field changed from dropdown to text
	private String vMakeOther;
	private String fallCMVI;
	private String winterCMVI;
	private String fallInsStation;
	private String winterInsStation;
	private String unitNumber;
	private String insurancePolicyNumber;
	private String fallInsFile;
	private String winterInsFile;
	private String fallHEInsFile;
	private String miscHEInsFile1;
	private String miscHEInsFile2;
	private String regFile;
	private String insFile;
	public String getFallInsFile() {
		return fallInsFile;
	}
	public void setFallInsFile(String fallInsFIle) {
		this.fallInsFile = fallInsFIle;
	}
	public String getWinterInsFile() {
		return winterInsFile;
	}
	public void setWinterInsFile(String winterInsFile) {
		this.winterInsFile = winterInsFile;
	}
	public String getFallHEInsFile() {
		return fallHEInsFile;
	}
	public void setFallHEInsFile(String fallHEInsFile) {
		this.fallHEInsFile = fallHEInsFile;
	}
	public String getMiscHEInsFile1() {
		return miscHEInsFile1;
	}
	public void setMiscHEInsFile1(String miscHEInsFile1) {
		this.miscHEInsFile1 = miscHEInsFile1;
	}
	public String getMiscHEInsFile2() {
		return miscHEInsFile2;
	}
	public void setMiscHEInsFile2(String miscHEInsFile2) {
		this.miscHEInsFile2 = miscHEInsFile2;
	}
	public String getRegFile() {
		return regFile;
	}
	public void setRegFile(String regFile) {
		this.regFile = regFile;
	}
	public String getInsFile() {
		return insFile;
	}
	public void setInsFile(String insFile) {
		this.insFile = insFile;
	}

	public int getvMake() {
		return vMake;
	}
	public void setvMake(int vMake) {
		this.vMake = vMake;
	}
	public int getvModel() {
		return vModel;
	}
	public void setvModel(int vModel) {
		this.vModel = vModel;
	}
	public String getvYear() {
		return vYear;
	}
	public void setvYear(String vYear) {
		this.vYear = vYear;
	}
	public String getvSerialNumber() {
		return vSerialNumber;
	}
	public void setvSerialNumber(String vSerialNumber) {
		this.vSerialNumber = vSerialNumber;
	}
	public String getvPlateNumber() {
		return vPlateNumber;
	}
	public void setvPlateNumber(String vPlateNumber) {
		this.vPlateNumber = vPlateNumber;
	}
	public int getvType() {
		return vType;
	}
	public void setvType(int vType) {
		this.vType = vType;
	}
	public int getvSize() {
		return vSize;
	}
	public void setvSize(int vSize) {
		this.vSize = vSize;
	}
	public String getvOwner() {
		return vOwner;
	}
	public void setvOwner(String vOwner) {
		this.vOwner = vOwner;
	}
	public Date getRegExpiryDate() {
		return regExpiryDate;
	}
	public void setRegExpiryDate(Date regExpiryDate) {
		this.regExpiryDate = regExpiryDate;
	}
	public Date getInsExpiryDate() {
		return insExpiryDate;
	}
	public void setInsExpiryDate(Date insExpiryDate) {
		this.insExpiryDate = insExpiryDate;
	}
	public String getInsuranceProvider() {
		return insuranceProvider;
	}
	public void setInsuranceProvider(String insuranceProvider) {
		this.insuranceProvider = insuranceProvider;
	}
	public Date getFallInsDate() {
		return fallInsDate;
	}
	public void setFallInsDate(Date fallInsDate) {
		this.fallInsDate = fallInsDate;
	}
	public Date getWinterInsDate() {
		return winterInsDate;
	}
	public void setWinterInsDate(Date winterInsDate) {
		this.winterInsDate = winterInsDate;
	}
	public Date getMiscHeInsDate1() {
		return miscHeInsDate1;
	}
	public void setMiscHeInsDate1(Date miscHeInsDate1) {
		this.miscHeInsDate1 = miscHeInsDate1;
	}
	public Date getMiscHeInsDate2() {
		return miscHeInsDate2;
	}
	public void setMiscHeInsDate2(Date miscHeInsDate2) {
		this.miscHeInsDate2 = miscHeInsDate2;
	}
	public int getContractorId() {
		return contractorId;
	}
	public void setContractorId(int contractorId) {
		this.contractorId = contractorId;
	}
	public int getvStatus() {
		return vStatus;
	}
	public void setvStatus(int vStatus) {
		this.vStatus = vStatus;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public Date getDateApproved() {
		return dateApproved;
	}
	public void setDateApproved(Date dateApproved) {
		this.dateApproved = dateApproved;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getFallHeInsDate() {
		return fallHeInsDate;
	}
	public void setFallHeInsDate(Date fallHeInsDate) {
		this.fallHeInsDate = fallHeInsDate;
	}
	public String getStatusText(){
		return VehicleStatusConstant.get(this.vStatus).getDescription();
		
	}
	public String getRegExpiryDateFormatted() {
		if(this.regExpiryDate != null){
			return new SimpleDateFormat("MM/dd/yyyy").format(this.regExpiryDate);
		}else{
			return "";
		}
	}
	public String getInsExpiryDateFormatted() {
		if(this.insExpiryDate != null){
			return new SimpleDateFormat("MM/dd/yyyy").format(this.insExpiryDate);
		}else{
			return "";
		}
	}
	public String getFallInsDateFormatted() {
		if(this.fallInsDate != null){
			return new SimpleDateFormat("MM/dd/yyyy").format(this.fallInsDate);
		}else{
			return "";
		}
	}
	public String getWinterInsDateFormatted() {
		if(this.winterInsDate != null){
			return new SimpleDateFormat("MM/dd/yyyy").format(this.winterInsDate);
		}else{
			return "";
		}
	}
	public String getFallHeInsDateFormatted() {
		if(this.fallHeInsDate != null){
			return new SimpleDateFormat("MM/dd/yyyy").format(this.fallHeInsDate);
		}else{
			return "";
		}
	}
	public String getMiscHeInsDate1Formatted() {
		if(this.miscHeInsDate1 != null){
			return new SimpleDateFormat("MM/dd/yyyy").format(this.miscHeInsDate1);
		}else{
			return "";
		}
	}
	public String getMiscHeInsDate2Formatted() {
		if(this.miscHeInsDate2 != null){
			return new SimpleDateFormat("MM/dd/yyyy").format(this.miscHeInsDate2);
		}else{
			return "";
		}
	}
	public String getDateApprovedFormatted() {
		if(this.dateApproved != null){
			return new SimpleDateFormat("MM/dd/yyyy").format(this.dateApproved);
		}else{
			return "";
		}
	}
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	public BussingContractorBean getBcBean() {
		return bcBean;
	}
	public void setBcBean(BussingContractorBean bcBean) {
		this.bcBean = bcBean;
	}
	public String getStatusNotes() {
		return statusNotes;
	}
	public void setStatusNotes(String statusNotes) {
		this.statusNotes = statusNotes;
	}
	public String getWarningNotes() {
		return warningNotes;
	}
	public void setWarningNotes(String warningNotes) {
		this.warningNotes = warningNotes;
	}
	public String getMakeText(){
		if(this.getvMake() > 0){
			return DropdownManager.getDropdownItemText(this.getvMake());
		}else{
			return "";
		}
		
	}
	public String getModelText(){
		if(this.getvModel() > 0){
			return DropdownManager.getDropdownItemText(this.getvModel());
		}else{
			return "";
		}
		
	}
	public String getTypeText(){
		if(this.getvType() > 0){
			return DropdownManager.getDropdownItemText(this.getvType());
		}else{
			return "";
		}
		
	}
	public String getSizeText(){
		if(this.getvSize() > 0){
			return DropdownManager.getDropdownItemText(this.getvSize());
		}else{
			return "";
		}
		
	}
	public String getvModel2() {
		return vModel2;
	}
	public void setvModel2(String vModel2) {
		this.vModel2 = vModel2;
	}
	public String getvMakeOther() {
		return vMakeOther;
	}
	public void setvMakeOther(String vMakeOther) {
		this.vMakeOther = vMakeOther;
	}
	public String getFallCMVI() {
		return fallCMVI;
	}
	public void setFallCMVI(String fallCMVI) {
		this.fallCMVI = fallCMVI;
	}
	public String getWinterCMVI() {
		return winterCMVI;
	}
	public void setWinterCMVI(String winterCMVI) {
		this.winterCMVI = winterCMVI;
	}
	public String getFallInsStation() {
		return fallInsStation;
	}
	public void setFallInsStation(String fallInsStation) {
		this.fallInsStation = fallInsStation;
	}
	public String getWinterInsStation() {
		return winterInsStation;
	}
	public void setWinterInsStation(String winterInsStation) {
		this.winterInsStation = winterInsStation;
	}
	public String getUnitNumber() {
		return unitNumber;
	}
	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}
	public String getInsurancePolicyNumber() {
		return insurancePolicyNumber;
	}
	public void setInsurancePolicyNumber(String insurancePolicyNumber) {
		this.insurancePolicyNumber = insurancePolicyNumber;
	}
	
	
	

}
