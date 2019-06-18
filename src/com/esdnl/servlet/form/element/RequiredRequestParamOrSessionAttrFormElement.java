package com.esdnl.servlet.form.element;

import javax.servlet.http.HttpSession;

import com.esdnl.servlet.FormElement;
import com.esdnl.util.StringUtils;

public class RequiredRequestParamOrSessionAttrFormElement extends FormElement {

	private String attr;
	private HttpSession session;

	public RequiredRequestParamOrSessionAttrFormElement(String param, String attr, HttpSession session) {

		super(param);

		this.attr = attr;
		this.session = session;
	}

	public RequiredRequestParamOrSessionAttrFormElement(String param, String attr, String invalid_value_msg,
			HttpSession session) {

		super(param, invalid_value_msg);

		this.attr = attr;
		this.session = session;
	}

	public boolean validate() {

		return (!StringUtils.isEmpty((String) getValue()) || (session.getAttribute(this.attr) != null));
	}
}