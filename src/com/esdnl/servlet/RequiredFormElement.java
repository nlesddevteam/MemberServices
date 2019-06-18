package com.esdnl.servlet;

import com.esdnl.util.StringUtils;

public class RequiredFormElement extends FormElement {

	public RequiredFormElement(String name) {

		super(name);
	}

	public RequiredFormElement(String name, String invalid_value_msg) {

		super(name, invalid_value_msg);
	}

	public boolean validate() {

		return !StringUtils.isEmpty((String) getValue());
	}
}