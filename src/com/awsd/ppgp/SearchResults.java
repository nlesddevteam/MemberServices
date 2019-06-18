package com.awsd.ppgp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;
import com.awsd.school.bean.RegionBean;

public class SearchResults implements Serializable {

	private static final long serialVersionUID = 6062126988743060755L;

	private ArrayList<Vector<SearchResult>> pages = null;
	private int pgp_cnt = 0;

	// criteria
	private String year = null;
	private RegionBean region = null;
	private String keywords = null;
	private TaskCategoryBean cat = null;
	private TaskGradeBean grd = null;
	private TaskSubjectBean subject = null;
	private TaskTopicAreaBean topic = null;
	private TaskSpecificTopicBean stopic = null;
	private TaskDomainBean domain = null;
	private TaskDomainStrengthBean strength = null;

	public SearchResults(ArrayList<Vector<SearchResult>> pages, int pgp_cnt) {

		this.pages = pages;
		this.pgp_cnt = pgp_cnt;
	}

	public boolean isEmpty() {

		return (pages.size() == 0);
	}

	public Vector<SearchResult> getPage(int page) {

		Vector<SearchResult> v = null;

		if ((page >= 1) && (page <= pages.size())) {
			v = ((Vector<SearchResult>) pages.get(page - 1));
		}

		return v;
	}

	public int getPGPCount() {

		return pgp_cnt;
	}

	public int getPageCount() {

		return pages.size();
	}

	public void setRegion(RegionBean region) {

		this.region = region;
	}

	public RegionBean getRegion() {

		return this.region;
	}

	public void setSchoolYear(String year) {

		this.year = year;
	}

	public String getSchoolYear() {

		return this.year;
	}

	public void setKeywords(String keywords) {

		this.keywords = keywords;
	}

	public String getKeywords() {

		return this.keywords;
	}

	public void setCategory(TaskCategoryBean cat) {

		this.cat = cat;
	}

	public TaskCategoryBean getCategory() {

		return this.cat;
	}

	public void setGrade(TaskGradeBean grd) {

		this.grd = grd;
	}

	public TaskGradeBean getGrade() {

		return this.grd;
	}

	public void setTaskSubjectBean(TaskSubjectBean subject) {

		this.subject = subject;
	}

	public TaskSubjectBean getSubject() {

		return this.subject;
	}

	public void setTaskTopicAreaBean(TaskTopicAreaBean topic) {

		this.topic = topic;
	}

	public TaskTopicAreaBean getTopic() {

		return this.topic;
	}

	public void setTaskSpecificTopicBean(TaskSpecificTopicBean stopic) {

		this.stopic = stopic;
	}

	public TaskSpecificTopicBean getSpecificTopic() {

		return this.stopic;
	}

	public TaskDomainBean getDomain() {
		return domain;
	}

	public void setDomain(TaskDomainBean domain) {
		this.domain = domain;
	}

	public TaskDomainStrengthBean getStrength() {
		return strength;
	}

	public void setStrength(TaskDomainStrengthBean strength) {
		this.strength = strength;
	}

}