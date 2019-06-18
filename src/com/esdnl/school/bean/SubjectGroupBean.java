package com.esdnl.school.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.awsd.school.Subject;

public class SubjectGroupBean {

	private int groupId;
	private String groupName;
	private Collection<Subject> subjects;

	private Map<Integer, Subject> subjectsMap;

	public SubjectGroupBean() {

		this.groupId = 0;
		this.groupName = "";
		this.subjects = null;
	}

	public int getGroupId() {

		return groupId;
	}

	public void setGroupId(int groupId) {

		this.groupId = groupId;
	}

	public String getGroupName() {

		return groupName;
	}

	public void setGroupName(String groupName) {

		this.groupName = groupName;
	}

	public Collection<Subject> getSubjects() {

		return subjects;
	}

	public void setSubjects(Collection<Subject> subjects) {

		this.subjects = subjects;
	}

	public void addGroupSubject(Subject subject) {

		if (this.subjects == null) {
			this.subjects = new ArrayList<Subject>();
		}

		this.subjects.add(subject);
	}

	public Map<Integer, Subject> getSubjectsMap() {

		if (this.subjectsMap == null) {
			this.subjectsMap = new HashMap<Integer, Subject>();
			for (Subject s : this.getSubjects()) {
				this.subjectsMap.put(s.getSubjectID(), s);
			}
		}

		return this.subjectsMap;
	}

	public boolean contains(int subjectId) {

		return this.getSubjectsMap().containsKey(subjectId);
	}

	public boolean contains(Subject subject) {

		return this.contains(subject.getSubjectID());
	}

	public String toXML() {

		StringBuilder sb = new StringBuilder();

		sb.append("<SubjectGroupBean group-id='" + this.groupId + "' group-name='" + this.groupName + "'>");
		for (Subject s : this.getSubjects()) {
			sb.append(s.toXML());
		}
		sb.append("</SubjectGroupBean>");

		return sb.toString();
	}
}
