package com.esdnl.servlet;

public class RequiredSelectionFormElement extends RequiredFormElement {

	private int no_select_value;

	public RequiredSelectionFormElement(String name, int no_select_value) {

		super(name);
		this.no_select_value = no_select_value;
	}

	public RequiredSelectionFormElement(String name, int no_select_value, String invalid_value_msg) {

		super(name, invalid_value_msg);
		this.no_select_value = no_select_value;
	}

	public int getNoSelectionValue() {

		return this.no_select_value;
	}

	public boolean validate() {

		return super.validate() && (Integer.parseInt((String) getValue()) != this.no_select_value);
	}
}