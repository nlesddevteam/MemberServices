package com.nlesd.school.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SchoolStreamDetailsBean implements Serializable {

	private static final long serialVersionUID = -3914355675665585056L;
	private Integer id=0;
	private Integer schoolId;
	private String streamNotes;
	private String addedBy;
	private Date dateAdded;
	private ArrayList<SchoolStreamSchoolsBean> schoolStreamsEnglish;
	private ArrayList<SchoolStreamSchoolsBean> schoolStreamsFrench;
	public SchoolStreamDetailsBean()
	{
		schoolStreamsEnglish = new ArrayList<SchoolStreamSchoolsBean>();
		schoolStreamsFrench = new ArrayList<SchoolStreamSchoolsBean>();
		
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}
	public String getStreamNotes() {
		return streamNotes;
	}
	public void setStreamNotes(String streamNotes) {
		this.streamNotes = streamNotes;
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
	public String getDateAddedFormatted() {

		return new SimpleDateFormat("dd/MM/yyyy").format(this.dateAdded);
	}
	public ArrayList<SchoolStreamSchoolsBean> getSchoolStreamsFrench() {
		return schoolStreamsFrench;
	}
	public void setSchoolStreamsFrench(
			ArrayList<SchoolStreamSchoolsBean> schoolStreamsFrench) {
		this.schoolStreamsFrench = schoolStreamsFrench;
	}
	public ArrayList<SchoolStreamSchoolsBean> getSchoolStreamsEnglish() {
		return schoolStreamsEnglish;
	}
	public void setSchoolStreamsEnglish(
			ArrayList<SchoolStreamSchoolsBean> schoolStreamsEnglish) {
		this.schoolStreamsEnglish = schoolStreamsEnglish;
	}
	public ArrayList<Integer> getSchoolStreamsEnglishSelectedList()
	{
		ArrayList<Integer>al = new ArrayList<Integer>();
		
		for(SchoolStreamSchoolsBean sssb : this.schoolStreamsEnglish)
		{
			al.add(sssb.getSchoolId());
		}
		
		return al;
		
		
	}

}
