package com.nlesd.school.bean;

import java.io.Serializable;

public class SchoolStreamSchoolsBean implements Serializable {

	private static final long serialVersionUID = -3914355675665585056L;
	private Integer id;
	private Integer schoolId;
	private Integer streamType;
	private Integer streamId;
	private String schoolName;
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
	public Integer getStreamType() {
		return streamType;
	}
	public void setStreamType(Integer streamType) {
		this.streamType = streamType;
	}
	public Integer getStreamId() {
		return streamId;
	}
	public void setStreamId(Integer streamId) {
		this.streamId = streamId;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	

}
