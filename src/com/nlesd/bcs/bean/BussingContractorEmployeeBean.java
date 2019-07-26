package com.nlesd.bcs.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.nlesd.bcs.constants.EmployeeStatusConstant;
import com.nlesd.bcs.dao.DropdownManager;

public class BussingContractorEmployeeBean implements Serializable {

	private static final long serialVersionUID = -844016122427000813L;
	private int id;
	private int contractorId;
	private int employeePosition;
	private String startDate;
	private String continuousService;
	private String firstName;
	private String lastName;
	private String middleName;
	private String address1;
	private String address2;
	private String city;
	private String province;
	private String postalCode;
	private String homePhone;
	private String cellPhone;
	private String email;
	private String dlNumber;
	private Date dlExpiryDate;
	private int dlClass;
	private Date daRunDate;
	private String daConvictions;
	private Date faExpiryDate;
	private Date pccDate;
	private Date scaDate;
	private String findingsOfGuilt;
	private String dlFront;
	private String dlBack;
	private String daDocument;
	private String faDocument;
	private String prcvsqDocument;
	private String pccDocument;
	private String scaDocument;
	private Date prcvsqDate;
	private int status;
	private String isDeleted;
	private String employeePositionText;
	private BussingContractorBean bcBean;
	private String approvedBy;
	private Date DateApproved;
	private String statusNotes;
	private String warningNotes;//used with main screen warnings for contractors and automated ones
	private Date birthDate;
	private String daSuspensions;
	private String daAccidents;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getContractorId() {
		return contractorId;
	}
	public void setContractorId(int contractorId) {
		this.contractorId = contractorId;
	}
	public int getEmployeePosition() {
		return employeePosition;
	}
	public void setEmployeePosition(int employeePosition) {
		this.employeePosition = employeePosition;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getContinuousService() {
		//now we check to see if a value has been saved
		//if yes then return it, if not try to calculate it
		if(continuousService == null) {
			if(startDate ==  null) {
				return "0.00";
			}else {
				//now we calculate the years of service
				Date today = new Date();
				Calendar cal = Calendar.getInstance();
				String[] test = startDate.split("-");
				cal.set(Calendar.YEAR,Integer.parseInt(test[1]));
				cal.set(Calendar.MONTH,Integer.parseInt(test[0]));
				cal.set(Calendar.DAY_OF_MONTH,1);
				long diff = today.getTime() - cal.getTimeInMillis();
				long diffDays = diff / (24 * 60 * 60 * 1000);
				float stime = diffDays/365;
				String displaytime = String.format("%.02f", stime);
				return displaytime;
			}
			
		}else {
			return continuousService;
		}
	}
	public void setContinuousService(String continuousService) {
		this.continuousService = continuousService;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getHomePhone() {
		return homePhone;
	}
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}
	public String getCellPhone() {
		return cellPhone;
	}
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDlNumber() {
		return dlNumber;
	}
	public void setDlNumber(String dlNumber) {
		this.dlNumber = dlNumber;
	}
	public Date getDlExpiryDate() {
		return dlExpiryDate;
	}
	public void setDlExpiryDate(Date dlExpiryDate) {
		this.dlExpiryDate = dlExpiryDate;
	}
	public int getDlClass() {
		return dlClass;
	}
	public void setDlClass(int dlClass) {
		this.dlClass = dlClass;
	}
	public Date getDaRunDate() {
		return daRunDate;
	}
	public void setDaRunDate(Date daRunDate) {
		this.daRunDate = daRunDate;
	}
	public String getDaConvictions() {
		return daConvictions;
	}
	public void setDaConvictions(String daConvictions) {
		this.daConvictions = daConvictions;
	}
	public Date getFaExpiryDate() {
		return faExpiryDate;
	}
	public void setFaExpiryDate(Date faExpiryDate) {
		this.faExpiryDate = faExpiryDate;
	}
	public Date getPccDate() {
		return pccDate;
	}
	public void setPccDate(Date pccDate) {
		this.pccDate = pccDate;
	}
	public Date getScaDate() {
		return scaDate;
	}
	public void setScaDate(Date scaDate) {
		this.scaDate = scaDate;
	}
	public String getFindingsOfGuilt() {
		return findingsOfGuilt;
	}
	public void setFindingsOfGuilt(String findingsOfGuilt) {
		this.findingsOfGuilt = findingsOfGuilt;
	}
	public String getDlFront() {
		return dlFront;
	}
	public void setDlFront(String dlFront) {
		this.dlFront = dlFront;
	}
	public String getDlBack() {
		return dlBack;
	}
	public void setDlBack(String dlBack) {
		this.dlBack = dlBack;
	}
	public String getDaDocument() {
		return daDocument;
	}
	public void setDaDocument(String daDocument) {
		this.daDocument = daDocument;
	}
	public String getFaDocument() {
		return faDocument;
	}
	public void setFaDocument(String faDocument) {
		this.faDocument = faDocument;
	}
	public String getPrcvsqDocument() {
		return prcvsqDocument;
	}
	public void setPrcvsqDocument(String prcvsqDocument) {
		this.prcvsqDocument = prcvsqDocument;
	}
	public String getPccDocument() {
		return pccDocument;
	}
	public void setPccDocument(String pccDocument) {
		this.pccDocument = pccDocument;
	}
	public String getScaDocument() {
		return scaDocument;
	}
	public void setScaDocument(String scaDocument) {
		this.scaDocument = scaDocument;
	}
	public String getStartYear(){
		String vyear="";
		if(!(this.getStartDate()==null)){
			/**
			if(this.getStartDate().length() > 5){
				vyear=this.getStartDate().substring(2,5);
			}else{
				vyear=this.getStartDate().substring(1,4);
			}
			**/
			String[] test = this.getStartDate().split("-");
			vyear=test[1];
		}
		return vyear;
	}
	public String getStartMonth(){
		String vmonth="";
		if(!(this.getStartDate()==null)){
			/**
			if(this.getStartDate().length() > 5){
				vmonth=this.getStartDate().substring(0,1);
			}else{
				vmonth=this.getStartDate().substring(0,2);
			}**/
			String[] test = this.getStartDate().split("-");
			vmonth=test[0];
			
		}
		return vmonth;
	}
	public String getDlExpiryDateFormatted() {
		if(this.dlExpiryDate!= null){
			return new SimpleDateFormat("MM/dd/yyyy").format(this.dlExpiryDate);
		}else{
			return "";
		}
	}
	public String getDaRunDateFormatted() {
		if(this.daRunDate!= null){
			return new SimpleDateFormat("MM/dd/yyyy").format(this.daRunDate);
		}else{
			return "";
		}
	}
	public String getFaExpiryDateFormatted() {
		if(this.faExpiryDate!= null){
			return new SimpleDateFormat("MM/dd/yyyy").format(this.faExpiryDate);
		}else{
			return "";
		}
	}
	public Date getPrcvsqDate() {
		return prcvsqDate;
	}
	public void setPrcvsqDate(Date prcvsqDate) {
		this.prcvsqDate = prcvsqDate;
	}
	public String getPrcvsqDateFormatted() {
		if(this.prcvsqDate!= null){
			return new SimpleDateFormat("MM/dd/yyyy").format(this.prcvsqDate);
		}else{
			return "";
		}
	}
	public String getPccDateFormatted() {
		if(this.pccDate!= null){
			return new SimpleDateFormat("MM/dd/yyyy").format(this.pccDate);
		}else{
			return "";
		}
	}
	public String getScaDateFormatted() {
		if(this.scaDate!= null){
			return new SimpleDateFormat("MM/dd/yyyy").format(this.scaDate);
		}else{
			return "";
		}
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getStatusText(){
		return EmployeeStatusConstant.get(this.status).getDescription();
		
	}
	public String getEmployeePositionText() {
		return employeePositionText;
	}
	public void setEmployeePositionText(String employeePositionText) {
		this.employeePositionText = employeePositionText;
	}
	public BussingContractorBean getBcBean() {
		return bcBean;
	}
	public void setBcBean(BussingContractorBean bcBean) {
		this.bcBean = bcBean;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public Date getDateApproved() {
		return DateApproved;
	}
	public void setDateApproved(Date dateApproved) {
		DateApproved = dateApproved;
	}
	public String getStatusNotes() {
		return statusNotes;
	}
	public void setStatusNotes(String statusNotes) {
		this.statusNotes = statusNotes;
	}
	public String getDateApprovedFormatted() {
		if(this.DateApproved!= null){
			return new SimpleDateFormat("MM/dd/yyyy").format(this.DateApproved);
		}else{
			return "";
		}
	}
	public String getDlClassText() {
		if(this.dlClass > 0){
			return DropdownManager.getDropdownItemText(this.dlClass);
		}else{
			return "";
		}
	}
	public String getWarningNotes() {
		return warningNotes;
	}
	public void setWarningNotes(String warningNotes) {
		this.warningNotes = warningNotes;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getBirthDateFormatted() {
		if(this.birthDate!= null){
			return new SimpleDateFormat("MM/dd/yyyy").format(this.birthDate);
		}else{
			return "";
		}
	}
	public String getDaSuspensions() {
		return daSuspensions;
	}
	public void setDaSuspensions(String daSuspensions) {
		this.daSuspensions = daSuspensions;
	}
	public String getDaAccidents() {
		return daAccidents;
	}
	public void setDaAccidents(String daAccidents) {
		this.daAccidents = daAccidents;
	}
}
