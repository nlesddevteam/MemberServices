package com.esdnl.webmaint.esdweb.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.awsd.school.School;
import com.esdnl.webmaint.esdweb.constants.AnnouncementTypeConstant;

public class AnnouncementBean {

	private int id;
	private int type;
	private Date date;
	private String header;
	private String body;
	private String image;
	private String image_caption;
	private String full_story_link;
	private boolean show_on_front_page;
	private boolean archived;
	private School school;

	public AnnouncementBean() {

		id = 0;
		date = null;
		header = null;
		body = null;
		image = null;
		image_caption = null;
		full_story_link = null;
		show_on_front_page = false;
		archived = false;
		school = null;
	}

	public int getID() {

		return this.id;
	}

	public void setID(int id) {

		this.id = id;
	}

	public AnnouncementTypeConstant getType() {

		return AnnouncementTypeConstant.get(this.type);
	}

	public void setType(int type) {

		this.type = type;
	}

	public Date getDate() {

		return this.date;
	}

	public String getFormattedDate() {

		return (new SimpleDateFormat("dd/MM/yyyy")).format(this.getDate());
	}

	public String getLongFormattedDate() {

		return (new SimpleDateFormat("MMMM dd, yyyy")).format(this.getDate());
	}

	public void setDate(Date date) {

		this.date = date;
	}

	public String getHeader() {

		return this.header;
	}

	public void setHeader(String header) {

		this.header = header;
	}

	public String getBody() {

		return this.body;
	}

	public void setBody(String body) {

		this.body = body;
	}

	public String getImage() {

		return this.image;
	}

	public void setImage(String image) {

		this.image = image;
	}

	public String getImageCaption() {

		return this.image_caption;
	}

	public void setImageCaption(String image_caption) {

		this.image_caption = image_caption;
	}

	public String getFullStoryLink() {

		return this.full_story_link;
	}

	public void setFullStoryLink(String full_story_link) {

		this.full_story_link = full_story_link;
	}

	public boolean isShowingOnFrontPage() {

		return this.show_on_front_page;
	}

	public void setShowOnFrontPage(boolean show_on_front_page) {

		this.show_on_front_page = show_on_front_page;
	}

	public boolean isArchived() {

		return this.archived;
	}

	public void setArchived(boolean archived) {

		this.archived = archived;
	}

	public School getSchool() {

		return school;
	}

	public void setSchool(School school) {

		this.school = school;
	}
}