package com.esdnl.tsdoc.bean;

import java.util.Date;

import com.esdnl.util.StringUtils;

public class TrusteeNoteBean {

	private int noteId;
	private String noteTitle;
	private String noteText;
	private Date dateAdded;

	public TrusteeNoteBean(int noteId, String noteTitle, String noteText) {

		super();
		this.noteId = noteId;
		this.noteTitle = noteTitle;
		this.noteText = noteText;
	}

	public TrusteeNoteBean() {

		this(0, null, null);
	}

	public int getNoteId() {

		return noteId;
	}

	public void setNoteId(int noteId) {

		this.noteId = noteId;
	}

	public String getNoteTitle() {

		return noteTitle;
	}

	public void setNoteTitle(String noteTitle) {

		this.noteTitle = noteTitle;
	}

	public String getNoteText() {

		return noteText;
	}

	public String getNoteHTML() {

		return StringUtils.encodeHTML2(this.noteText);
	}

	public void setNoteText(String noteText) {

		this.noteText = noteText;
	}

	public Date getDateAdded() {

		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {

		this.dateAdded = dateAdded;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;

		result = prime * result + noteId;
		result = prime * result + ((noteText == null) ? 0 : noteText.hashCode());
		result = prime * result + ((noteTitle == null) ? 0 : noteTitle.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrusteeNoteBean other = (TrusteeNoteBean) obj;

		if (noteId != other.noteId)
			return false;
		if (noteText == null) {
			if (other.noteText != null)
				return false;
		}
		else if (!noteText.equals(other.noteText))
			return false;
		if (noteTitle == null) {
			if (other.noteTitle != null)
				return false;
		}
		else if (!noteTitle.equals(other.noteTitle))
			return false;
		return true;
	}

}
