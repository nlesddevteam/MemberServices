package com.esdnl.colaboration.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.esdnl.colaboration.dao.CollaborationManager;
import com.esdnl.servlet.FormElementFormat;

public class DiscussionBean {

	private int id;
	private String title;
	private String desc;
	private Date discussion_date;
	private DiscussionGroupBean[] groups;
	private boolean released;

	public DiscussionBean() {

		this.id = 0;
		this.title = null;
		this.desc = null;
		this.discussion_date = null;
		this.groups = null;
		this.released = false;
	}

	public int getId() {

		return this.id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public String getTitle() {

		return this.title;
	}

	public void setTitle(String title) {

		this.title = title;
	}

	public String getDescription() {

		return this.desc;
	}

	public void setDescription(String desc) {

		this.desc = desc;
	}

	public Date getDiscussionDate() {

		return this.discussion_date;
	}

	public String getFormattedDiscussionDate() {

		return new SimpleDateFormat(FormElementFormat.DATE_FORMAT).format(getDiscussionDate());
	}

	public void setDiscussionDate(Date discussion_date) {

		this.discussion_date = discussion_date;
	}

	public DiscussionGroupBean[] getGroups() {

		if (this.groups == null) {

			try {
				this.setGroups(CollaborationManager.getDiscussionGroupBeans(this));
			}
			catch (CollaborationException ex) {
				this.setGroups(null);
			}

		}
		return this.groups;
	}

	public void setGroups(DiscussionGroupBean[] groups) {

		this.groups = groups;
	}

	public void setReleased(boolean released) {

		this.released = released;
	}

	public boolean isReleased() {

		return this.released;
	}

	public String toString() {

		return "[" + this.getFormattedDiscussionDate() + "] " + this.getTitle();
	}

}
