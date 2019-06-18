package com.esdnl.fund3.bean;

import java.io.Serializable;

public class DropdownItemBean implements Serializable {
	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private String ddText;
	private Integer isActive;
	private Integer ddId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDdText() {
		return ddText;
	}
	public void setDdText(String ddText) {
		this.ddText = ddText;
	}
	public Integer getIsActive() {
		return isActive;
	}
	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}
	public Integer getDdId() {
		return ddId;
	}
	public void setDdId(Integer ddId) {
		this.ddId = ddId;
	}
}
