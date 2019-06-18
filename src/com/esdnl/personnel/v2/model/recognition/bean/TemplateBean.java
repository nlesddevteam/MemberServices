package com.esdnl.personnel.v2.model.recognition.bean;

import com.esdnl.personnel.v2.model.recognition.constant.TemplateConstant;
import com.esdnl.personnel.v2.model.sds.bean.EmployeeBean;

public class TemplateBean {

	private int id;
	private String name;
	private String filename;
	private String text;

	public TemplateBean() {

		this.id = 0;
		this.name = null;
		this.filename = null;
		this.text = null;
	}

	public int getId() {

		return id;
	}

	public void setId(int newId) {

		id = newId;
	}

	public String getName() {

		return name;
	}

	public void setName(String newName) {

		name = newName;
	}

	public String getFilename() {

		return this.filename;
	}

	public void setFilename(String newFilename) {

		filename = newFilename;
	}

	public String getText() {

		return this.text;
	}

	public void setText(String text) {

		this.text = text;
	}

	public boolean equals(Object o) {

		boolean check = false;

		if (o instanceof TemplateBean)
			check = (((TemplateBean) o).getId() == this.getId());

		return check;
	}

	public String process(Object o) {

		String letter = this.text;

		for (int i = 0; i < TemplateConstant.PLACEHOLDERS.length; i++) {
			if (TemplateConstant.PLACEHOLDERS[i].equals(TemplateConstant.PLACEHOLDER_FIRSTNAME)) {
				if (o instanceof EmployeeBean)
					letter = letter.replaceAll(TemplateConstant.PLACEHOLDER_FIRSTNAME, ((EmployeeBean) o).getFirstName());
			}
		}

		return letter;
	}
}