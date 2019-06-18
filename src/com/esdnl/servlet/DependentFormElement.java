package com.esdnl.servlet;

public class DependentFormElement extends RequiredFormElement {

	private FormElement dependent;
	private String dependent_value;

	public DependentFormElement(String name, FormElement dependent, String dependent_value) {

		super(name);

		this.dependent = dependent;
		this.dependent_value = dependent_value;
	}

	public DependentFormElement(String name, FormElement dependent, String dependent_value, String invalid_value_msg) {

		super(name, invalid_value_msg);

		this.dependent = dependent;
		this.dependent_value = dependent_value;
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
			valid = super.validate();

		return valid;
	}
}
