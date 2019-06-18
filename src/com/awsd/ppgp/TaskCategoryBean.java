package com.awsd.ppgp;

import java.io.Serializable;

public class TaskCategoryBean implements Serializable {

	private static final long serialVersionUID = -7110376195197896200L;

	private int cat_id;
	private String cat_title;

	public TaskCategoryBean() {

		this(-1, "UNKNOWN");
	}

	public TaskCategoryBean(int cat_id, String cat_title) {

		this.cat_id = cat_id;
		this.cat_title = cat_title;
	}

	public int getCategoryID() {

		return this.cat_id;
	}

	public void setCategoryID(int cat_id) {

		this.cat_id = cat_id;
	}

	public String getCategoryTitle() {

		return this.cat_title;
	}

	public void setCategoryTitle(String cat_title) {

		this.cat_title = cat_title;
	}

	public boolean equals(Object obj) {

		if (obj == null)
			return false;
		else if (!(obj instanceof TaskCategoryBean))
			return false;
		else if (((TaskCategoryBean) obj).getCategoryID() != this.cat_id)
			return false;
		else
			return true;
	}

}