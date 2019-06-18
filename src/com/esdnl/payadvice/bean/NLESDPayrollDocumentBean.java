package com.esdnl.payadvice.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.esdnl.payadvice.constants.NLESDPayrollDocumentType;


public class NLESDPayrollDocumentBean implements Serializable {

	private static final long serialVersionUID = -8771605504141292333L;
	public static final String DOCUMENT_BASEPATH = "/WEB-INF/uploads/payadvice/docs/";
	public static final String TEMPLATE_BASEPATH = "/WEB-INF/uploads/payadvice/templates/";

	private int documentId;
	private NLESDPayrollDocumentType type;
	private String uploadedBy;
	private String filename;
	private Date createdDate;
	private String notes;
	private String isProcessed;
	private String originalFileName;
	private Integer fileGroup;

	public NLESDPayrollDocumentBean() {

		this.documentId = 0;
		this.type = null;
		this.uploadedBy = null;
		this.filename = null;
		this.createdDate = null;
		this.notes=null;
		this.isProcessed=null;
		this.originalFileName=null;
		this.fileGroup=0;
		
	}

	public int getDocumentId() {

		return documentId;
	}

	public void setDocumentId(int documentId) {

		this.documentId = documentId;
	}
	public NLESDPayrollDocumentType getType() {

		return type;
	}

	public void setType(NLESDPayrollDocumentType type) {

		this.type = type;
	}

	public String getUploadedBy() {

		return uploadedBy;
	}

	public void setUploadedBy(String uploadedBy) {

		this.uploadedBy = uploadedBy;
	}

	public String getFilename() {

		return filename;
	}

	public void setFilename(String filename) {

		this.filename = filename;
	}

	public String getFilePath() {

		return NLESDPayrollDocumentBean.DOCUMENT_BASEPATH + this.getType().getValue() + "/" + this.getFilename();
	}

	public Date getCreatedDate() {

		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {

		this.createdDate = createdDate;
	}
	
	public String getCreatedDateFormatted() {

		return new SimpleDateFormat("dd/MM/yyyy").format(this.createdDate);
	}
	
	public String getDocumentType()
	{
		return this.getType().toString();
	}


	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getIsProcessed() {
		return isProcessed;
	}

	public void setIsProcessed(String isProcessed) {
		this.isProcessed = isProcessed;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public Integer getFileGroup() {
		return fileGroup;
	}

	public void setFileGroup(Integer fileGroup) {
		this.fileGroup = fileGroup;
	}
}
