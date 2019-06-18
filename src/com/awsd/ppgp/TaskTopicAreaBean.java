package com.awsd.ppgp;

import java.io.Serializable;

public class TaskTopicAreaBean implements Serializable {

	private static final long serialVersionUID = -1312524465870532082L;

	private int topic_id;
	private String topic_title;

	public TaskTopicAreaBean(int topic_id, String topic_title) {

		this.topic_id = topic_id;
		this.topic_title = topic_title;
	}

	public TaskTopicAreaBean() {

		this(-1, "UNKNOWN");
	}

	public int getTopicID() {

		return this.topic_id;
	}

	public void setTopicID(int topic_id) {

		this.topic_id = topic_id;
	}

	public String getTopicTitle() {

		return this.topic_title;
	}

	public void setTopicTitle(String topic_title) {

		this.topic_title = topic_title;
	}

	public boolean equals(Object obj) {

		if (obj == null)
			return false;
		else if (!(obj instanceof TaskTopicAreaBean))
			return false;
		else if (((TaskTopicAreaBean) obj).getTopicID() != this.topic_id)
			return false;
		else
			return true;
	}
}