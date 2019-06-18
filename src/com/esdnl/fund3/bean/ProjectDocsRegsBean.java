package com.esdnl.fund3.bean;

import java.io.Serializable;
public class ProjectDocsRegsBean implements Serializable {
	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private Integer projectId;
	private Integer regionId;
	private Integer documentId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public Integer getRegionId() {
		return regionId;
	}
	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}
	public Integer getDocumentId() {
		return documentId;
	}
	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}
}
