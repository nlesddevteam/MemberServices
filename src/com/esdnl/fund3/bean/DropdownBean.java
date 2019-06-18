package com.esdnl.fund3.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class DropdownBean implements Serializable {
	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private String ddName;
	private Integer isActive;
	private ArrayList<DropdownItemBean> ddItems;
	public DropdownBean()
	{
		ddItems = new ArrayList<DropdownItemBean>();
		
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDdName() {
		return ddName;
	}
	public void setDdName(String ddName) {
		this.ddName = ddName;
	}
	public Integer getIsActive() {
		return isActive;
	}
	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}
	public ArrayList<DropdownItemBean> getDdItems() {
		return ddItems;
	}
	public void setDdItems(ArrayList<DropdownItemBean> ddItems) {
		this.ddItems = ddItems;
	}
	
}
