package com.awsd.ppgp;

import java.io.Serializable;

public class TaskSpecificTopicBean implements Serializable {

	private static final long serialVersionUID = -2140440880889638942L;

	private int stopic_id;
	private String stopic_title;

	public TaskSpecificTopicBean(int stopic_id, String stopic_title) {

		this.stopic_id = stopic_id;
		this.stopic_title = stopic_title;
	}

	public TaskSpecificTopicBean() {

		this(-1, "UNKNOWN");
	}

	public int getSpecificTopicID() {

		return this.stopic_id;
	}

	public void setSpecificTopicID(int stopic_id) {

		this.stopic_id = stopic_id;
	}

	public String getSpecificTopicTitle() {

		return this.stopic_title;
	}

	public void setSpecificTopicTitle(String stopic_title) {

		this.stopic_title = stopic_title;
	}

	public boolean equals(Object obj) {

		if (obj == null)
			return false;
		else if (!(obj instanceof TaskSpecificTopicBean))
			return false;
		else if (((TaskSpecificTopicBean) obj).getSpecificTopicID() != this.stopic_id)
			return false;
		else
			return true;
	}
}