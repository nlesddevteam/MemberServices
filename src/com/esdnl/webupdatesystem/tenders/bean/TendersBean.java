package com.esdnl.webupdatesystem.tenders.bean;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import com.esdnl.webupdatesystem.tenders.constants.TenderStatus;
import com.nlesd.school.bean.SchoolZoneBean;

public class TendersBean implements Serializable {

	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private String tenderNumber;
	private SchoolZoneBean tenderZone;
	private String tenderTitle;
	private Date closingDate;
	private String tenderDoc;
	private SchoolZoneBean tenderOpeningLocation;
	private TenderStatus tenderStatus;
	private String addedBy;
	private Date dateAdded;
	private String docUploadName;
	private ArrayList<TendersFileBean> otherTendersFiles;
	public static final String DOCUMENT_BASEPATH = "/WEB-INF/uploads/tenders/";
	private Date awardedDate;
	private String awardedTo;
	private double contractValue;
	private TenderExceptionBean teBean;
	public Integer getId() {

		return id;
	}

	public void setId(Integer id) {

		this.id = id;
	}

	public String getTenderNumber() {

		return tenderNumber;
	}

	public void setTenderNumber(String tenderNumber) {

		this.tenderNumber = tenderNumber;
	}

	public String getTenderTitle() {

		return tenderTitle;
	}

	public void setTenderTitle(String tenderTitle) {

		this.tenderTitle = tenderTitle;
	}

	public Date getClosingDate() {

		return closingDate;
	}

	public LocalDate getClosingLocalDate() {

		return closingDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public void setClosingDate(Date closingDate) {

		this.closingDate = closingDate;
	}

	public String getTenderDoc() {

		return tenderDoc;
	}

	public void setTenderDoc(String tenderDoc) {

		this.tenderDoc = tenderDoc;
	}

	public TenderStatus getTenderStatus() {

		return tenderStatus;
	}

	public void setTenderStatus(TenderStatus tenderStatus) {

		this.tenderStatus = tenderStatus;
	}

	public String getAddedBy() {

		return addedBy;
	}

	public void setAddedBy(String addedBy) {

		this.addedBy = addedBy;
	}

	public Date getDateAdded() {

		return dateAdded;
	}

	public LocalDate getDateAddedLocalDate() {

		return this.dateAdded.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public void setDateAdded(Date dateAdded) {

		this.dateAdded = dateAdded;
	}

	public String getDocUploadName() {

		return docUploadName;
	}

	public void setDocUploadName(String docUploadName) {

		this.docUploadName = docUploadName;
	}

	public SchoolZoneBean getTenderZone() {

		return tenderZone;
	}

	public void setTenderZone(SchoolZoneBean tenderZone) {

		this.tenderZone = tenderZone;
	}

	public SchoolZoneBean getTenderOpeningLocation() {

		return tenderOpeningLocation;
	}

	public void setTenderOpeningLocation(SchoolZoneBean tenderOpeningLocation) {

		this.tenderOpeningLocation = tenderOpeningLocation;
	}

	public String getClosingDateFormatted() {

		return new SimpleDateFormat("dd/MM/yyyy").format(this.closingDate);
	}

	public String getDateAddedFormatted() {

		return new SimpleDateFormat("dd/MM/yyyy").format(this.dateAdded);
	}

	public String getFileURL() {

		return DOCUMENT_BASEPATH + this.tenderDoc;
	}

	public ArrayList<TendersFileBean> getOtherTendersFiles() {

		return otherTendersFiles;
	}

	public void setOtherTendersFiles(ArrayList<TendersFileBean> otherTendersFiles) {

		this.otherTendersFiles = otherTendersFiles;
	}

	public Date getAwardedDate() {

		return awardedDate;
	}

	public LocalDate getAwardedLocalDate() {

		return this.awardedDate != null ? this.awardedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : null;
	}

	public void setAwardedDate(Date awardedDate) {

		this.awardedDate = awardedDate;
	}

	public String getAwardedTo() {

		return awardedTo;
	}

	public void setAwardedTo(String awardedTo) {

		this.awardedTo = awardedTo;
	}

	public String getAwardedDateFormatted() {

		if (this.awardedDate != null) {
			return new SimpleDateFormat("dd/MM/yyyy").format(this.awardedDate);
		}
		else {
			return "";
		}

	}

	public double getContractValue() {

		return contractValue;
	}

	public void setContractValue(double contractValue) {

		this.contractValue = contractValue;
	}

	public String getContractValueFormatted() {

		DecimalFormat df2 = new DecimalFormat(".00");
		return df2.format(contractValue);
	}

	public TenderExceptionBean getTeBean() {
		return teBean;
	}

	public void setTeBean(TenderExceptionBean teBean) {
		this.teBean = teBean;
	}
}
