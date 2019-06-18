package com.esdnl.servlet.form.element;

import com.esdnl.servlet.FormElement;

public class DependentFormElements extends FormElement {

	private FormElement element;
	private FormElement dependent;
	private String dependent_value;

	public DependentFormElements(FormElement element, FormElement dependent, String dependent_value) {

		super(element.getName(), element.getInvalidValueMsg());

		this.element = element;
		this.dependent = dependent;
		this.dependent_value = dependent_value;
	}

	public FormElement getElement() {

		return this.element;
	}

	public FormElement getDependent() {

		return this.dependent;
	}

	public String getDependentValue() {

		return this.dependent_value;
	}

	public boolean validate() {

		boolean valid = true;

		if (this.getDependent().validate()
				&& ((String) this.getDependent().getValue()).equalsIgnoreCase(this.getDependentValue()))
			valid = this.element.validate();

		return valid;
	}
}
