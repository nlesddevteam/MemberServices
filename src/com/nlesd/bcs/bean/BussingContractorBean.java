package com.nlesd.bcs.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.nlesd.bcs.constants.StatusConstant;
public class BussingContractorBean implements Serializable,Comparable<BussingContractorBean> {

	private static final long serialVersionUID = -844016122427000813L;
	private int id;
	private String firstName;
	private String lastName;
	private String middleName;
	private String email;
	private String address1;
	private String address2;
	private String city;
	private String province;
	private String postalCode;
	private String homePhone;
	private String cellPhone;
	private String workPhone;
	private String company;
	private Integer status;
	private String businessNumber;
	private String hstNumber;
	private Date DateSubmitted;
	private String maddress1;
	private String maddress2;
	private String mcity;
	private String mprovince;
	private String mpostalCode;
	private String msameAs;
	private BussingContractorSecurityBean secBean;
	private BussingContractorCompanyBean comBean;
	private String boardOwned;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getWorkPhone() {
		return workPhone;
	}
	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getBusinessNumber() {
		return businessNumber;
	}
	public void setBusinessNumber(String businessNumber) {
		this.businessNumber = businessNumber;
	}
	public String getHstNumber() {
		return hstNumber;
	}
	public void setHstNumber(String hstNumber) {
		this.hstNumber = hstNumber;
	}
	public Date getDateSubmitted() {
		return DateSubmitted;
	}
	public void setDateSubmitted(Date dateSubmitted) {
		DateSubmitted = dateSubmitted;
	}
	public String getDateSubmittedFormatted()
	{
		return new SimpleDateFormat("MM/dd/yyyy").format(this.DateSubmitted);
	}
	public BussingContractorSecurityBean getSecBean() {
		return secBean;
	}
	public void setSecBean(BussingContractorSecurityBean secBean) {
		this.secBean = secBean;
	}
	public String getMaddress1() {
		return maddress1;
	}
	public void setMaddress1(String maddress1) {
		this.maddress1 = maddress1;
	}
	public String getMaddress2() {
		return maddress2;
	}
	public void setMaddress2(String maddress2) {
		this.maddress2 = maddress2;
	}
	public String getMcity() {
		return mcity;
	}
	public void setMcity(String mcity) {
		this.mcity = mcity;
	}
	public String getMprovince() {
		return mprovince;
	}
	public void setMprovince(String mprovince) {
		this.mprovince = mprovince;
	}
	public String getMpostalCode() {
		return mpostalCode;
	}
	public void setMpostalCode(String mpostalCode) {
		this.mpostalCode = mpostalCode;
	}
	public String getMsameAs() {
		return msameAs;
	}
	public void setMsameAs(String msameAs) {
		this.msameAs = msameAs;
	}	
	public String getStatusText(){
		return StatusConstant.get(this.status).getDescription();
		
	}
	public BussingContractorCompanyBean getComBean() {
		return comBean;
	}
	public void setComBean(BussingContractorCompanyBean comBean) {
		this.comBean = comBean;
	}
	public String getContractorName(){
		if(this.getCompany() != null){
			return this.getLastName() + "," + this.getFirstName() + "(" + this.getCompany() + ")";
		}else{
			return this.getLastName() + "," + this.getFirstName();
		}
	
	}
	public String getContractorNameRev(){
		if(this.getCompany() != null){
			return this.getCompany() + "(" + this.getLastName() + "," + this.getFirstName() + ")";
		}else{
			return this.getLastName() + "," + this.getFirstName();
		}
	
	}
	@Override     
	  public int compareTo(BussingContractorBean contractor) {          
		//return this.getLastName().compareTo(contractor.getLastName());
		//return this.getContractorName().compareTo(contractor.getContractorName());
		
		return this.getContractorNameRev().compareTo(contractor.getContractorNameRev()); 
	  }
	public void setBoardOwned(String boardOwned) {
		this.boardOwned = boardOwned;
	}
	public String getBoardOwned() {
		return boardOwned;
	} 
}
