package com.esdnl.personnel.jobs.bean;
import java.util.Date;
import com.esdnl.personnel.jobs.constants.DocumentType;
import com.esdnl.personnel.jobs.constants.DocumentTypeSS;
public class ApplicantDocumentBean {
	public static final String DOCUMENT_BASEPATH = "WEB-INF/uploads/personnel/docs/";
	private int documentId;
	private ApplicantProfileBean applicant;
	private DocumentType type;
	private String description;
	private String filename;
	private Date createdDate;
	private DocumentTypeSS typeSS;

	public ApplicantDocumentBean() {

		this.documentId = 0;
		this.applicant = null;
		this.type = null;
		this.description = null;
		this.filename = null;
		this.createdDate = null;
		this.typeSS=null;
	}

	public int getDocumentId() {

		return documentId;
	}

	public void setDocumentId(int documentId) {

		this.documentId = documentId;
	}

	public ApplicantProfileBean getApplicant() {

		return applicant;
	}

	public void setApplicant(ApplicantProfileBean applicant) {

		this.applicant = applicant;
	}

	public DocumentType getType() {

		return type;
	}

	public void setType(DocumentType type) {

		this.type = type;
	}

	public String getDescription() {

		return description;
	}

	public void setDescription(String description) {

		this.description = description;
	}

	public String getFilename() {

		return filename;
	}

	public void setFilename(String filename) {

		this.filename = filename;
	}

	public String getFilePath() {
		if(this.applicant.getProfileType().equals("T")){
			return ApplicantDocumentBean.DOCUMENT_BASEPATH + this.getType().getValue() + "/" + this.getFilename();
		}else{
			return ApplicantDocumentBean.DOCUMENT_BASEPATH + this.getTypeSS().getValue() + "/" + this.getFilename();
		}
		
	}

	public Date getCreatedDate() {

		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {

		this.createdDate = createdDate;
	}

	public DocumentTypeSS getTypeSS() {
		return typeSS;
	}

	public void setTypeSS(DocumentTypeSS typeSS) {
		this.typeSS = typeSS;
	}

}
