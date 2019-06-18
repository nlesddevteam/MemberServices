package com.esdnl.servlet;

public class FormElement {

	private String element_name;
	private Object value;
	private String invalid_value_mgs;

	public FormElement(String element_name) {

		this.element_name = element_name;
		this.invalid_value_mgs = null;
	}

	public FormElement(String element_name, String invalid_value_msg) {

		this.element_name = element_name;
		this.invalid_value_mgs = invalid_value_msg;
	}

	public String getName() {

		return this.element_name;
	}

	public void setValue(Object value) {

		this.value = value;
	}

	public Object getValue() {

		return this.value;
	}

	public void setInvalidValueMsg(String invalid_value_msg) {

		this.invalid_value_mgs = invalid_value_msg;
	}

	public String getInvalidValueMsg() {

		return this.invalid_value_mgs;
	}

	public boolean validate() {

		return true;
	}

	public String toString() {

		return getName();
	}
}