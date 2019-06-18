package com.esdnl.webupdatesystem.programs.bean;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
public class ProgramsFileBean implements Serializable 
{
	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private String pfTitle;
	private String pfDoc;
	private String addedBy;
	private Date dateAdded;
	private Integer programsId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPfTitle() {
		return pfTitle;
	}
	public void setPfTitle(String pfTitle) {
		this.pfTitle = pfTitle;
	}
	public String getPfDoc() {
		return pfDoc;
	}
	public void setPfDoc(String pfDoc) {
		this.pfDoc = pfDoc;
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
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	public Integer getProgramsId() {
		return programsId;
	}
	public void setProgramsId(Integer programsId) {
		this.programsId = programsId;
	}
	public String getDateAddedFormatted() {

		return new SimpleDateFormat("dd/MM/yyyy").format(this.dateAdded);
	}
}
