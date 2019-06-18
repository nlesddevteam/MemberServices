package com.nlesd.bcs.bean;

import java.io.Serializable;

public class DropdownItemBean implements Serializable {

	private static final long serialVersionUID = -844016122427000813L;
	private int id;
	private String text;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
