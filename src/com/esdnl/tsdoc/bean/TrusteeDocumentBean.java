package com.esdnl.tsdoc.bean;

import java.util.Date;

public class TrusteeDocumentBean {

	private int documentId;
	private String documentTitle;
	private String filename;
	private Date dateAdded;

	public TrusteeDocumentBean(int documentId, String documentTitle, String filename) {

		super();
		this.documentId = documentId;
		this.documentTitle = documentTitle;
		this.filename = filename;
	}

	public TrusteeDocumentBean() {

		this(0, null, null);
	}

	public int getDocumentId() {

		return documentId;
	}

	public void setDocumentId(int documentId) {

		this.documentId = documentId;
	}

	public String getDocumentTitle() {

		return documentTitle;
	}

	public void setDocumentTitle(String documentTitle) {

		this.documentTitle = documentTitle;
	}

	public String getFilename() {

		return filename;
	}

	public void setFilename(String filename) {

		this.filename = filename;
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
		result = prime * result + documentId;
		result = prime * result + ((documentTitle == null) ? 0 : documentTitle.hashCode());
		result = prime * result + ((filename == null) ? 0 : filename.hashCode());
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
		TrusteeDocumentBean other = (TrusteeDocumentBean) obj;
		if (documentId != other.documentId)
			return false;
		if (documentTitle == null) {
			if (other.documentTitle != null)
				return false;
		}
		else if (!documentTitle.equals(other.documentTitle))
			return false;
		if (filename == null) {
			if (other.filename != null)
				return false;
		}
		else if (!filename.equals(other.filename))
			return false;

		return true;
	}

}
