package com.esdnl.servlet;

public class RequiredMultiSelectionFormElement extends RequiredSelectionFormElement {

	public RequiredMultiSelectionFormElement(String name, int no_select_value) {

		super(name, no_select_value);
	}

	public RequiredMultiSelectionFormElement(String name) {

		super(name, -1);
	}

	public boolean validate() {

		boolean check = true;

		String[] values = (String[]) getValue();

		if ((values != null) && (values.length > 0)) {
			for (int i = 0; i < values.length; i++) {
				if (Integer.parseInt(values[i]) == getNoSelectionValue()) {
					check = false;
					break;
				}
			}
		}
		else
			check = false;

		return check;
	}
}