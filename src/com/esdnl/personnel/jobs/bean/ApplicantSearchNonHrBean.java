package com.esdnl.personnel.jobs.bean;

import com.esdnl.personnel.jobs.constants.DocumentType;
import com.esdnl.personnel.jobs.constants.DocumentTypeSS;

public class ApplicantSearchNonHrBean {
	public String sdsLastName;
	public String sdsFirstName;
	public String sdsEmail;
	public String appLastName;
	public String appFirstName;
	public String appEmail;
	public String appSin;
	public int docType;
	public int docId;
	public String profileType;
	public String getSdsLastName() {
		if(sdsLastName != null) {
			return sdsLastName;
		}else {
			return appLastName;
		}
	}
	public void setSdsLastName(String sdsLastName) {
		this.sdsLastName = sdsLastName;
	}
	public String getSdsFirstName() {
		if(sdsFirstName != null) {
			return sdsFirstName;
		}else {
			return appFirstName;
		}
	}
	public void setSdsFirstName(String sdsFirstName) {
		this.sdsFirstName = sdsFirstName;
	}
	public String getSdsEmail() {
		if( sdsEmail !=  null) {
			return sdsEmail;
		}else {
			return appEmail;
		}
	}
	public void setSdsEmail(String sdsEmail) {
		this.sdsEmail = sdsEmail;
	}
	public String getAppLastName() {
		return appLastName;
	}
	public void setAppLastName(String appLastName) {
		this.appLastName = appLastName;
	}
	public String getAppFirstName() {
		return appFirstName;
	}
	public void setAppFirstName(String appFirstName) {
		this.appFirstName = appFirstName;
	}
	public String getAppEmail() {
		return appEmail;
	}
	public void setAppEmail(String appEmail) {
		this.appEmail = appEmail;
	}
	public String getAppSin() {
		return appSin;
	}
	public void setAppSin(String appSin) {
		this.appSin = appSin;
	}
	public int getDocType() {
		return docType;
	}
	public void setDocType(int docType) {
		this.docType = docType;
	}
	public int getDocId() {
		return docId;
	}
	public void setDocId(int docId) {
		this.docId = docId;
	}
	public String toXml() {
		StringBuilder sb = new StringBuilder();
		sb.append("<APPLICANT>");
		if(this.getSdsLastName() ==  null) {
			sb.append("<SDSLASTNAME></SDSLASTNAME>");
		}else {
			sb.append("<SDSLASTNAME>" + this.getSdsLastName() + "</SDSLASTNAME>");
		}
		if(this.getSdsFirstName() ==  null) {
			sb.append("<SDSFIRSTNAME></SDSFIRSTNAME>");
		}else {
			sb.append("<SDSFIRSTNAME>" + this.getSdsFirstName() + "</SDSFIRSTNAME>");
		}
		if(this.getSdsEmail() ==  null) {
			sb.append("<SDSEMAIL></SDSEMAIL>");
		}else {
			sb.append("<SDSEMAIL>" + this.getSdsEmail() + "</SDSEMAIL>");
		}
		
		if(this.appLastName ==  null) {
			sb.append("<APPLASTNAME></APPLASTNAME>");
		}else {
			sb.append("<APPLASTNAME>" + this.appLastName + "</APPLASTNAME>");
		}
		if(this.appFirstName ==  null) {
			sb.append("<APPFIRSTNAME></APPFIRSTNAME>");
		}else {
			sb.append("<APPFIRSTNAME>" + this.appFirstName + "</APPFIRSTNAME>");
		}
		if(this.appEmail==  null) {
			sb.append("<APPEMAIL></APPEMAIL>");
		}else {
			sb.append("<APPEMAIL>" + this.appEmail + "</APPEMAIL>");
		}
		if(this.appSin==  null) {
			sb.append("<APPSIN></APPSIN>");
		}else {
			sb.append("<APPSIN>" + this.appSin + "</APPSIN>");
		}
		sb.append("<DOCID>" + this.docId + "</DOCID>");
		if(this.docType == DocumentType.CODE_OF_ETHICS_CONDUCT.getValue()) {
			sb.append("<DOCTYPE>T</DOCTYPE>");
		}else if(this.docType == DocumentTypeSS.CODE_OF_ETHICS_CONDUCT.getValue()) {
			sb.append("<DOCTYPE>S</DOCTYPE>");
		}
		sb.append("<SDSLINKED>" + linkedToSds() + "</SDSLINKED>" );
		sb.append("</APPLICANT>");
		return sb.toString();
	}
	public String getProfileType() {
		return profileType;
	}
	public void setProfileType(String profileType) {
		this.profileType = profileType;
	}
	public String linkedToSds() {
		if(this.sdsLastName == null) {
			return "NOT LINKED";
		}else {
			return "LINKED";
		}
	}
}
