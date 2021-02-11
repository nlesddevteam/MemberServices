package com.esdnl.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import com.esdnl.util.StringUtils;

import javazoom.upload.MultipartFormDataRequest;
import javazoom.upload.UploadException;
import javazoom.upload.UploadFile;

public class Form {

	private HashMap<String, Object> name_map;
	private boolean is_valid;
	private boolean is_multipart;

	@SuppressWarnings("unchecked")
	public Form(HttpServletRequest request) {

		String name = null;
		FormElement ele = null;
		MultipartFormDataRequest mrequest = null;

		try {
			name_map = new HashMap<String, Object>();

			is_multipart = StringUtils.contains(request.getContentType(), "multipart/form-data");

			if (is_multipart) {
				mrequest = new MultipartFormDataRequest(request);

				name_map.put("_UPLOADED_FILES", mrequest.getFiles());

				for (Enumeration<String> e = (Enumeration<String>) mrequest.getParameterNames(); e.hasMoreElements();) {
					name = (String) e.nextElement();

					ele = new FormElement(name);
					ele.setValue(mrequest.getParameterValues(name));

					name_map.put(name, ele);
				}
			}
			else {
				for (Enumeration<String> e = (Enumeration<String>) request.getParameterNames(); e.hasMoreElements();) {
					name = (String) e.nextElement();

					ele = new FormElement(name);
					ele.setValue(request.getParameterValues(name));

					name_map.put(name, ele);
				}
			}

			is_valid = true;
		}
		catch (IOException e) {
			e.printStackTrace();
			is_valid = false;
		}
		catch (UploadException e) {
			e.printStackTrace();
			is_valid = false;
		}
	}

	public boolean exists(String element_name) {

		boolean check = false;

		if (name_map.containsKey(element_name))
			check = !StringUtils.isEmpty(((String[]) ((FormElement) name_map.get(element_name)).getValue())[0]);

		return check;
	}

	public boolean valuesMatch(String element_name1, String element_name2) {

		boolean check = false;

		if (name_map.containsKey(element_name1) && name_map.containsKey(element_name2)) {
			String val1 = ((String[]) ((FormElement) name_map.get(element_name1)).getValue())[0];
			String val2 = ((String[]) ((FormElement) name_map.get(element_name2)).getValue())[0];

			if (!StringUtils.isEmpty(val1) && !StringUtils.isEmpty(val2) && val1.equals(val2))
				check = true;
		}

		return check;
	}

	public boolean hasValue(String element_name, String element_value) {

		boolean check = false;

		if (name_map.containsKey(element_name)) {
			String val1 = ((String[]) ((FormElement) name_map.get(element_name)).getValue())[0];

			if (!StringUtils.isEmpty(val1) && val1.equals(element_value))
				check = true;
		}

		return check;
	}

	public boolean hasValueIgnoreCase(String element_name, String element_value) {

		boolean check = false;

		if (name_map.containsKey(element_name)) {
			String val1 = ((String[]) ((FormElement) name_map.get(element_name)).getValue())[0];

			if (!StringUtils.isEmpty(val1) && val1.equalsIgnoreCase(element_value))
				check = true;
		}

		return check;
	}

	public String get(String element_name) {

		String value = null;

		if (name_map.containsKey(element_name))
			value = ((String[]) ((FormElement) name_map.get(element_name)).getValue())[0];

		return value;
	}

	public int getInt(String element_name) throws NullPointerException {

		int value = 0;

		if (name_map.containsKey(element_name))
			value = Integer.parseInt(((String[]) ((FormElement) name_map.get(element_name)).getValue())[0]);
		else
			throw new NullPointerException(element_name + " does not exists.");

		return value;
	}

	public double getDouble(String element_name) throws NullPointerException {

		double value = 0;

		if (name_map.containsKey(element_name))
			value = Double.parseDouble(((String[]) ((FormElement) name_map.get(element_name)).getValue())[0]);
		else
			throw new NullPointerException(element_name + " does not exists.");

		return value;
	}

	public boolean getBoolean(String element_name) throws NullPointerException {

		boolean value = false;

		if (name_map.containsKey(element_name))
			value = Boolean.parseBoolean(((String[]) ((FormElement) name_map.get(element_name)).getValue())[0]);
		else
			throw new NullPointerException(element_name + " does not exists.");

		return value;
	}

	public Date getDate(String element_name) throws NullPointerException {

		Date value = null;

		if (name_map.containsKey(element_name)) {
			try {
				value = new SimpleDateFormat(FormElementFormat.DATE_FORMAT).parse(
						((String[]) ((FormElement) name_map.get(element_name)).getValue())[0]);
			}
			catch (ParseException e) {
				throw new NullPointerException(e.getMessage());
			}
		}
		else
			throw new NullPointerException(element_name + " does not exists.");

		return value;
	}

	public Date getDateTime(String element_name) throws NullPointerException {

		Date value = null;

		if (name_map.containsKey(element_name)) {
			try {
				value = new SimpleDateFormat(FormElementFormat.DATE_TIME_FORMAT).parse(
						((String[]) ((FormElement) name_map.get(element_name)).getValue())[0]);
			}
			catch (ParseException e) {
				throw new NullPointerException(e.getMessage());
			}
		}
		else
			throw new NullPointerException(element_name + " does not exists.");

		return value;
	}

	public String[] getArray(String element_name) {

		String[] value = null;

		if (name_map.containsKey(element_name))
			value = (String[]) ((FormElement) name_map.get(element_name)).getValue();

		return value;
	}

	public int[] getIntArray(String element_name) {

		int[] value = null;

		if (name_map.containsKey(element_name)) {
			int i = 0;
			value = new int[((String[]) ((FormElement) name_map.get(element_name)).getValue()).length];

			for (String tmp : ((String[]) ((FormElement) name_map.get(element_name)).getValue()))
				value[i++] = Integer.parseInt(tmp);
		}
		return value;
	}

	public Integer[] getIntegerArray(String element_name) {

		int[] arr = getIntArray(element_name);
		Integer[] objs = new Integer[arr.length];

		for (int i = 0; i < arr.length; i++) {
			objs[i] = new Integer(arr[i]);
		}

		return objs;
	}

	@SuppressWarnings("unchecked")
	public Hashtable<String, UploadFile> getUploadFiles() {

		return (Hashtable<String, UploadFile>) name_map.get("_UPLOADED_FILES");
	}

	public UploadFile getUploadFile(String field_name) {

		Hashtable<String, UploadFile> files = null;
		UploadFile file = null;

		if (this.isMultipart() && ((files = this.getUploadFiles()) != null) && (files.entrySet().size() > 0))
			file = (UploadFile) files.get(field_name);

		return file;
	}

	public boolean uploadFileExists(String field_name) {

		Hashtable<String, UploadFile> files = null;
		UploadFile file = null;

		return (isMultipart() && ((files = this.getUploadFiles()) != null) && (files.entrySet().size() > 0)
				&& ((file = (UploadFile) files.get(field_name)) != null) && !StringUtils.isEmpty(file.getFileName())
				&& (file.getFileSize() > 0));
	}

	public void set(String element_name, String value) {

		name_map.put(element_name, value);
	}

	public void setValid(boolean is_valid) {

		this.is_valid = is_valid;
	}

	public boolean isValid() {

		return is_valid;
	}

	public boolean isMultipart() {

		return is_multipart;
	}
}