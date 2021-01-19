package com.esdnl.webupdatesystem.schoolreviews.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SchoolReviewSectionOptionBean implements Serializable {

	private static final long serialVersionUID = -844016122427000813L;
	private int sectionOptionId;
	private String sectionOptionTitle;
	private String sectionOptionEmbed;
	private String sectionOptionLink;
	private int sectionOptionSectionId;
	private String sectionOptionAddedBy;
	private Date sectionOptionDateAdded;
	private String sectionOptionType;
	public int getSectionOptionId() {
		return sectionOptionId;
	}
	public void setSectionOptionId(int sectionOptionId) {
		this.sectionOptionId = sectionOptionId;
	}
	public String getSectionOptionTitle() {
		return sectionOptionTitle;
	}
	public void setSectionOptionTitle(String sectionOptionTitle) {
		this.sectionOptionTitle = sectionOptionTitle;
	}
	public String getSectionOptionEmbed() {
		return sectionOptionEmbed;
	}
	public void setSectionOptionEmbed(String sectionOptionEmbed) {
		this.sectionOptionEmbed = sectionOptionEmbed;
	}
	public String getSectionOptionLink() {
		return sectionOptionLink;
	}
	public void setSectionOptionLink(String sectionOptionLink) {
		this.sectionOptionLink = sectionOptionLink;
	}
	public int getSectionOptionSectionId() {
		return sectionOptionSectionId;
	}
	public void setSectionOptionSectionId(int sectionOptionSectionId) {
		this.sectionOptionSectionId = sectionOptionSectionId;
	}
	public String getSectionOptionAddedBy() {
		return sectionOptionAddedBy;
	}
	public void setSectionOptionAddedBy(String sectionOptionAddedBy) {
		this.sectionOptionAddedBy = sectionOptionAddedBy;
	}
	public Date getSectionOptionDateAdded() {
		return sectionOptionDateAdded;
	}
	public void setSectionOptionDateAdded(Date sectionOptionDateAdded) {
		this.sectionOptionDateAdded = sectionOptionDateAdded;
	}
	public String getSectionOptionType() {
		return sectionOptionType;
	}
	public void setSectionOptionType(String sectionOptionType) {
		this.sectionOptionType = sectionOptionType;
	}
	public String getSectionOptionDateAddedFormatted() {
		if(this.sectionOptionDateAdded != null) {
			return new SimpleDateFormat("dd/MM/yyyy").format(this.sectionOptionDateAdded);
		}else {
			return "";
		}
		
	}
	public String toXml() {
		StringBuilder sb= new StringBuilder();
		sb.append("<SOOPTION>");
		sb.append("<MESSAGE>SUCCESS</MESSAGE>");
		sb.append("<SECTIONOPTIONID>" + this.getSectionOptionId() + "</SECTIONOPTIONID>");
		sb.append("<SECTIONOPTIONTITLE>" + this.getSectionOptionTitle() + "</SECTIONOPTIONTITLE>");
		sb.append("<SECTIONOPTIONEMBED>" + this.getSectionOptionEmbed() + "</SECTIONOPTIONEMBED>");
		sb.append("<SECTIONOPTIONLINK>" + this.getSectionOptionLink() + "</SECTIONOPTIONLINK>");
		sb.append("<SECTIONOPTIONSECTIONID>" + this.getSectionOptionSectionId() + "</SECTIONOPTIONSECTIONID>");
		sb.append("<SECTIONOPTIONADDEDBY>" + this.getSectionOptionAddedBy() + "</SECTIONOPTIONADDEDBY>");
		sb.append("<SECTIONOPTIONDATEADDED>" + this.getSectionOptionDateAddedFormatted() + "</SECTIONOPTIONDATEADDED>");
		sb.append("<SECTIONOPTIONTYPE>" + this.getSectionOptionType() + "</SECTIONOPTIONTYPE>");
		sb.append("</SOOPTION>");
		
		return sb.toString();
	}
	
}
