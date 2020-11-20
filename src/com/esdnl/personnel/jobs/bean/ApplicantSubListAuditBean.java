package com.esdnl.personnel.jobs.bean;
import java.util.Date;
import com.awsd.personnel.Personnel;
import com.esdnl.personnel.jobs.constants.SublistAuditTypeCostant;

public class ApplicantSubListAuditBean {
	private int entryId;
	private int subListId;
	private String applicantId;
	private SublistAuditTypeCostant entryType;
	private Date entryDate;
	private Personnel entryBy;
	private String entryNotes;
	private String entryDateString;
	public int getEntryId() {
		return entryId;
	}
	public void setEntryId(int entryId) {
		this.entryId = entryId;
	}
	public int getSubListId() {
		return subListId;
	}
	public void setSubListId(int subListId) {
		this.subListId = subListId;
	}
	public String getApplicantId() {
		return applicantId;
	}
	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}
	public SublistAuditTypeCostant getEntryType() {
		return entryType;
	}
	public void setEntryType(SublistAuditTypeCostant entryType) {
		this.entryType = entryType;
	}
	public Date getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	public Personnel getEntryBy() {
		return entryBy;
	}
	public void setEntryBy(Personnel entryBy) {
		this.entryBy = entryBy;
	}
	public String getEntryNotes() {
		return entryNotes;
	}
	public void setEntryNotes(String entryNotes) {
		this.entryNotes = entryNotes;
	}
	public String getEntryDateString() {
		return entryDateString;
	}
	public void setEntryDateString(String entryDateString) {
		this.entryDateString = entryDateString;
	}
	public String toXml() {
		StringBuilder sb = new StringBuilder();
		sb.append("<SLENTRY><MESSAGE>DATA</MESSAGE>");
		sb.append("<ENTRYID>" + this.getEntryId() + "</ENTRYID>" );
		sb.append("<SUBLISTID>" + this.getSubListId() + "</SUBLISTID>" );
		sb.append("<APPLICANTID>" + this.getApplicantId() + "</APPLICANTID>" );
		sb.append("<AUDITTYPE>" + this.entryType.getDescription() + "</AUDITTYPE>" );
		sb.append("<ENTRYDATE>" + this.getEntryDateString() + "</ENTRYDATE>" );
		sb.append("<ENTRYNOTES>" + this.getEntryNotes() + "</ENTRYNOTES>" );
		sb.append("</SLENTRY>");
		return sb.toString();
	}
	public String getApplicantNotes() {
		String notes="";
		if(this.entryNotes == null) {
			notes="";
		}else {
			if(this.entryNotes.contains("Notes")) {
				int start = this.entryNotes.indexOf("Notes");
				notes = this.entryNotes.substring(start);
			}
		}
		return notes;
	}
}
