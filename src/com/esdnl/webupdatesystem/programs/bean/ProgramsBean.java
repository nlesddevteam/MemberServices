package com.esdnl.webupdatesystem.programs.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.esdnl.webupdatesystem.programs.constants.ProgramsLevel;
import com.esdnl.webupdatesystem.programs.constants.ProgramsRegion;

public class ProgramsBean implements Serializable 
{
	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private String descriptorTitle;
	private ProgramsRegion pRegion;
	private ProgramsLevel pLevel;
	private String addedBy;
	private Date dateAdded;
	private ArrayList<ProgramsFileBean> otherProgramsFiles;
	private Integer programStatus;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescriptorTitle() {
		return descriptorTitle;
	}
	public void setDescriptorTitle(String descriptorTitle) {
		this.descriptorTitle = descriptorTitle;
	}
	public ProgramsRegion getpRegion() {
		return pRegion;
	}
	public void setpRegion(ProgramsRegion pRegion) {
		this.pRegion = pRegion;
	}
	public ProgramsLevel getpLevel() {
		return pLevel;
	}
	public void setpLevel(ProgramsLevel pLevel) {
		this.pLevel = pLevel;
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
	public ArrayList<ProgramsFileBean> getOtherProgramsFiles() {
		return otherProgramsFiles;
	}
	public void setOtherProgramsFiles(ArrayList<ProgramsFileBean> otherProgramsFiles) {
		this.otherProgramsFiles = otherProgramsFiles;
	}
	public String getDateAddedFormatted() {

		return new SimpleDateFormat("dd/MM/yyyy").format(this.dateAdded);
	}
	public Integer getProgramStatus() {
		return programStatus;
	}
	public void setProgramStatus(Integer programStatus) {
		this.programStatus = programStatus;
	}
}
