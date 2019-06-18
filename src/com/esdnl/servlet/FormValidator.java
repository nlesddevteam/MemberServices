package com.esdnl.servlet;

import java.util.ArrayList;

import com.esdnl.servlet.form.element.DependentFormElements;
import com.esdnl.util.StringUtils;

public class FormValidator {

	private ArrayList<FormElement> elements = null;
	private ArrayList<FormElement> invalid_elements = null;

	public FormValidator(FormElement[] elements) {

		this.elements = new ArrayList<FormElement>();
		for (int i = 0; i < elements.length; i++)
			this.elements.add(elements[i]);
	}

	public FormValidator() {

		this.elements = new ArrayList<FormElement>();
	}

	public boolean validate(Form form) {

		boolean valid = true;

		invalid_elements = new ArrayList<FormElement>();

		FormElement[] elements = (FormElement[]) this.elements.toArray(new FormElement[0]);

		if (elements != null) {
			for (int i = 0; i < elements.length; i++) {
				setValue(form, elements[i]);

				if (!elements[i].validate()) {
					invalid_elements.add(elements[i]);
					valid = false;
				}
			}
		}

		form.setValid(valid);

		return valid;
	}

	public void addFormElement(FormElement element) {

		if (this.elements == null)
			this.elements = new ArrayList<FormElement>();

		if (element != null)
			elements.add(element);
	}

	public FormElement[] getInvalidElements() {

		return ((invalid_elements != null) ? ((FormElement[]) invalid_elements.toArray(new FormElement[0])) : null);
	}

	public String getErrorString() {

		StringBuffer buf = new StringBuffer();
		FormElement[] ele = getInvalidElements();

		for (int i = 0; ((ele != null) && (i < ele.length)); i++) {
			if (!StringUtils.isEmpty(ele[i].getInvalidValueMsg()))
				buf.append(ele[i].getInvalidValueMsg() + "\n");
			else
				buf.append(ele[i].getName() + " is invalid.\n");
		}

		return buf.toString();
	}

	private void setValue(Form form, FormElement ele) {

		if (ele instanceof RequiredFileFormElement)
			ele.setValue(form.getUploadFile(ele.getName()));

		else if (ele instanceof RequiredMultiSelectionFormElement)
			ele.setValue(form.getArray(ele.getName()));

		else if (ele instanceof DependentFormElement) {
			ele.setValue(form.get(ele.getName()));

			FormElement dependent = ((DependentFormElement) ele).getDependent();
			setValue(form, dependent);
		}

		else if (ele instanceof DependentFormElements) {
			setValue(form, ((DependentFormElements) ele).getElement());

			setValue(form, ((DependentFormElements) ele).getDependent());
		}

		else
			ele.setValue(form.get(ele.getName()));
	}
}