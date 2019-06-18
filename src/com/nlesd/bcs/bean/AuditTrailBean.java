package com.nlesd.bcs.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.nlesd.bcs.constants.EntryTableConstant;
import com.nlesd.bcs.constants.EntryTypeConstant;

public class AuditTrailBean implements Serializable {

	private static final long serialVersionUID = -844016122427000813L;
	private int id;
	private EntryTypeConstant entryType;
	private int entryId;
	private EntryTableConstant entryTable;
	private String entryNotes;
	private Date entryDate;
	private int contractorId;
	private BussingContractorBean bcBean;
	private String viewUrl;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public EntryTypeConstant getEntryType() {
		return entryType;
	}
	public void setEntryType(EntryTypeConstant entryType) {
		this.entryType = entryType;
	}
	public int getEntryId() {
		return entryId;
	}
	public void setEntryId(int entryId) {
		this.entryId = entryId;
	}
	public EntryTableConstant getEntryTable() {
		return entryTable;
	}
	public void setEntryTable(EntryTableConstant entryTable) {
		this.entryTable = entryTable;
	}
	public String getEntryNotes() {
		return entryNotes;
	}
	public void setEntryNotes(String entryNotes) {
		this.entryNotes = entryNotes;
	}
	public Date getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	public int getContractorId() {
		return contractorId;
	}
	public void setContractorId(int contractorId) {
		this.contractorId = contractorId;
	}
	public BussingContractorBean getBcBean() {
		return bcBean;
	}
	public void setBcBean(BussingContractorBean bcBean) {
		this.bcBean = bcBean;
	}
	public String getEntryDateFormatted()
	{
		return new SimpleDateFormat("MM/dd/yyyy").format(this.entryDate);
	}
	public String getViewUrl() {
		return viewUrl;
	}
	public void setViewUrl(String viewUrl) {
		this.viewUrl = viewUrl;
	}
}
