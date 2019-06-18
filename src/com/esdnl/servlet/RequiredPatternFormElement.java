package com.esdnl.servlet;

import java.util.regex.Pattern;

import com.esdnl.util.StringUtils;

public class RequiredPatternFormElement extends FormElement {

	private String pattern;

	public RequiredPatternFormElement(String name, String pattern) {

		super(name);
		this.pattern = pattern;
	}

	public RequiredPatternFormElement(String name, String pattern, String invalid_value_msg) {

		super(name, invalid_value_msg);
		this.pattern = pattern;
	}

	public boolean validate() {

		boolean check = true;

		if (!StringUtils.isEmpty((String) getValue()))
			check = Pattern.matches(this.pattern, (String) getValue());

		return check;
	}
}
