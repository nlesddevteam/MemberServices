package com.nlesd.bcs.bean;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
public class BussingContractorVehicleDocumentBean implements Serializable {

	private static final long serialVersionUID = -844016122427000813L;
	private int id;
	private int vehicleId;
	private int contractorId;
	private int documentType;
	private String documentTitle;
	private String documentPath;
	private String isDeleted;
	private Date dateUploaded;
	private String typeString;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getVehicleId() {
		return vehicleId;
	}
	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}
	public int getContractorId() {
		return contractorId;
	}
	public void setContractorId(int contractorId) {
		this.contractorId = contractorId;
	}
	public int getDocumentType() {
		return documentType;
	}
	public void setDocumentType(int documentType) {
		this.documentType = documentType;
	}
	public String getDocumentTitle() {
		return documentTitle;
	}
	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}
	public String getDocumentPath() {
		return documentPath;
	}
	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	public Date getDateUploaded() {
		return dateUploaded;
	}
	public void setDateUploaded(Date dateUploaded) {
		this.dateUploaded = dateUploaded;
	}
	public String getDateUploadedFormatted() {
		if(this.dateUploaded != null){
			return new SimpleDateFormat("MM/dd/yyyy").format(this.dateUploaded);
		}else{
			return "";
		}
	}
	public String getTypeString() {
		return typeString;
	}
	public void setTypeString(String typeString) {
		this.typeString = typeString;
	}
	public String getViewPath(){
		
		return "/BCS/documents/vehicledocs/" + this.documentPath;
	}
}
