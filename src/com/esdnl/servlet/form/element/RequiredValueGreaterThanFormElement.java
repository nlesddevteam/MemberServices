package com.esdnl.servlet.form.element;

import com.esdnl.servlet.RequiredFormElement;

public class RequiredValueGreaterThanFormElement extends RequiredFormElement {

	private int value;

	public RequiredValueGreaterThanFormElement(String name, int value) {

		super(name);
	}

	public RequiredValueGreaterThanFormElement(String name, int value, String invalid_value_msg) {

		super(name, invalid_value_msg);
	}

	public boolean validate() {

		return super.validate() && (Integer.parseInt((String) getValue()) > value);
	}
}