package com.awsd.ppgp;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.commons.lang.StringUtils;

public class PPGPTask implements Serializable {

	private static final long serialVersionUID = 1180320794327939511L;

	private int tsk_id;
	private String tdesc;
	private String ssupport;
	private String dsupport;
	private String completion;
	private String selfeval;
	private String technologySupport;
	private String technologySchoolSupport;
	private String technologyDistrictSupport;
	private int gid;

	private TaskCategoryBean category;
	private TaskGradeBean grade;
	private TaskSubjectBean subject;
	private TaskTopicAreaBean topic;
	private TaskSpecificTopicBean stopic;
	private TaskDomainBean domain;
	private TaskDomainStrengthBean strength;

	public PPGPTask() {

	}

	public int getTaskID() {

		return tsk_id;
	}

	public void setTaskID(int tsk_id) {

		this.tsk_id = tsk_id;
	}

	public int getGoalID() {

		return gid;
	}

	public void setGoalID(int gid) {

		this.gid = gid;
	}

	public String getDescription() {

		return tdesc;
	}

	public void setDescription(String tdesc) {

		this.tdesc = tdesc;
	}

	public String getSchoolSupport() {

		return ssupport;
	}

	public void setSchoolSupport(String ssupport) {

		this.ssupport = ssupport;
	}

	public String getDistrictSupport() {

		return dsupport;
	}

	public void setDistrictSupport(String dsupport) {

		this.dsupport = dsupport;
	}

	public String getCompletionDate() {

		return completion;
	}

	public void setCompletionDate(String completion) {

		this.completion = completion;
	}

	public boolean isPast() throws ParseException {

		return Calendar.getInstance().getTime().after((new SimpleDateFormat("dd-MMM-yyyy")).parse(this.getCompletionDate()));
	}

	public String getSelfEvaluation() {

		return selfeval;
	}

	public void setSelfEvaluation(String selfeval) {

		this.selfeval = selfeval;
	}

	public boolean isSelfReflectionComplete() {

		boolean complete = false;

		try {
			complete = this.isPast() && StringUtils.isNotEmpty(this.getSelfEvaluation());
		}
		catch (ParseException e) {
			complete = false;
		}

		return complete;
	}

	public TaskCategoryBean getCategory() {

		return this.category;
	}

	public void setCategory(TaskCategoryBean category) {

		this.category = category;
	}

	public TaskGradeBean getGrade() {

		return this.grade;
	}

	public void setGrade(TaskGradeBean grade) {

		this.grade = grade;
	}

	public TaskSubjectBean getSubject() {

		return this.subject;
	}

	public void setSubject(TaskSubjectBean subject) {

		this.subject = subject;
	}

	public TaskTopicAreaBean getTopic() {

		return topic;
	}

	public void setTopic(TaskTopicAreaBean topic) {

		this.topic = topic;
	}

	public TaskSpecificTopicBean getSpecificTopic() {

		return stopic;
	}

	public void setSpecificTopic(TaskSpecificTopicBean stopic) {

		this.stopic = stopic;
	}

	public String getTechnologySupport() {

		return technologySupport;
	}

	public void setTechnologySupport(String technologySupport) {

		this.technologySupport = technologySupport;
	}

	public String getTechnologySchoolSupport() {

		return technologySchoolSupport;
	}

	public void setTechnologySchoolSupport(String technologySchoolSupport) {

		this.technologySchoolSupport = technologySchoolSupport;
	}

	public String getTechnologyDistrictSupport() {

		return technologyDistrictSupport;
	}

	public void setTechnologyDistrictSupport(String technologyDistrictSupport) {

		this.technologyDistrictSupport = technologyDistrictSupport;
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