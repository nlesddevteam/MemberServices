package com.esdnl.servlet.form.element;

import javax.servlet.http.HttpServletRequest;

import com.esdnl.servlet.FormElement;
import com.esdnl.util.StringUtils;

public class RequiredRequestParamOrAttributeFormElement extends FormElement {

	private String attr;
	private HttpServletRequest request;

	public RequiredRequestParamOrAttributeFormElement(String param, String attr, HttpServletRequest request) {

		super(param);

		this.attr = attr;
		this.request = request;
	}

	public RequiredRequestParamOrAttributeFormElement(String param, String attr, String invalid_value_msg,
			HttpServletRequest request) {

		super(param, invalid_value_msg);

		this.attr = attr;
		this.request = request;
	}

	public boolean validate() {

		return (!StringUtils.isEmpty((String) getValue()) || (request.getAttribute(this.attr) != null));
	}
}