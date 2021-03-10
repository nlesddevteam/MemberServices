package com.awsd.travel.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TravelClaimFileBean {
	private int id;
	private String filePath;
	private Date dateUploaded;
	private int claimId;
	private int itemId;
	private String fileNotes;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public Date getDateUploaded() {
		return dateUploaded;
	}
	public void setDateUploaded(Date dateUploaded) {
		this.dateUploaded = dateUploaded;
	}
	public int getClaimId() {
		return claimId;
	}
	public void setClaimId(int claimId) {
		this.claimId = claimId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getFileNotes() {
		return fileNotes;
	}
	public void setFileNotes(String fileNotes) {
		this.fileNotes = fileNotes;
	}
	public String getDateUploadedFormatted()
	{
		if(this.dateUploaded == null) {
			return "";
		}else {
			return new SimpleDateFormat("dd/MM/yyyy").format(this.dateUploaded);
		}
		
	}
	
}
