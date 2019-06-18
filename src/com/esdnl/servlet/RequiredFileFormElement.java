package com.esdnl.servlet;

import javazoom.upload.UploadFile;

import com.esdnl.util.StringUtils;

public class RequiredFileFormElement extends FormElement {

	private String[] extensions;

	public RequiredFileFormElement(String name, String extension) {

		this(name, new String[] {
			extension
		}, "");
	}

	public RequiredFileFormElement(String name, String extension, String invalid_value_msg) {

		this(name, new String[] {
			extension
		}, invalid_value_msg);
	}

	public RequiredFileFormElement(String name, String[] extensions) {

		this(name, extensions, "");
	}

	public RequiredFileFormElement(String name, String[] extensions, String invalid_value_msg) {

		super(name, invalid_value_msg);
		this.extensions = extensions;
		this.prepareValidExtensions();
	}

	public RequiredFileFormElement(String name) {

		super(name);
		this.extensions = null;
	}

	public boolean validate() {

		return ((getValue() != null) && !StringUtils.isEmpty(((UploadFile) getValue()).getFileName()) && validateExtension());
	}

	private boolean validateExtension() {

		boolean check = false;
		String filename = null;

		if (this.extensions == null)
			check = true;
		else if ((filename = ((UploadFile) getValue()).getFileName()).lastIndexOf(".") >= 0) {
			String extension = filename.substring(filename.lastIndexOf("."));

			if (!StringUtils.isEmpty(extension)) {
				for (int i = 0; i < this.extensions.length; i++) {
					if (extension.equalsIgnoreCase(extensions[i])) {
						check = true;
						break;
					}
				}
			}
		}

		return check;
	}

	private void prepareValidExtensions() {

		if (this.extensions != null) {
			for (int i = 0; i < this.extensions.length; i++) {
				if (!this.extensions[i].startsWith("."))
					this.extensions[i] = "." + this.extensions[i];
			}
		}
	}
}