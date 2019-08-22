package com.esdnl.personnel.jobs.bean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.awsd.school.bean.RegionBean;
import com.awsd.school.dao.RegionManager;
import com.esdnl.personnel.jobs.constants.PostingType;

public class OtherJobOpportunityBean {

	private int id;
	private String title;
	private Date endDate;
	private String filename;
	private PostingType ptype;
	private RegionBean region;
	private Date cancelled_date;
	private String cancelled_reason;
	private String cancelled_by;
	public static final String DATE_FORMAT_STRING = "MMMM dd, yyyy";
	public static final String DATE_FORMAT_STRING_COMP_END_DATE = "MMMM dd, yyyy h:mm a";

	public OtherJobOpportunityBean() {

		this.id = 0;
		this.title = null;
		this.endDate = null;
		this.filename = null;
		this.ptype = PostingType.EXTERNAL_ONLY;
		this.cancelled_date = null;
		this.cancelled_by = null;
		this.cancelled_reason = null;
		try {
			this.region = RegionManager.getRegionBean(5); //district wide
		}
		catch (Exception e) {
			this.region = null;
		}
	}

	public int getId() {

		return id;
	}

	public void setId(int newId) {

		id = newId;
	}

	public String getTitle() {

		return title;
	}

	public void setTitle(String newTitle) {

		title = newTitle;
	}

	public Date getEndDate() {

		return endDate;
	}

	public void setEndDate(Date newEndDate) {

		endDate = newEndDate;
	}

	public String getFilename() {

		return filename;
	}

	public void setFilename(String newFilename) {

		filename = newFilename;
	}

	public boolean isInternalOnly() {

		return ptype.equals(PostingType.INTERNAL_ONLY);
	}

	public boolean isExternalOnly() {

		return ptype.equals(PostingType.EXTERNAL_ONLY);
	}

	public boolean isExternalAndInternal() {

		return ptype.equals(PostingType.INTERNAL_EXTERNAL);
	}

	public void setPostingType(PostingType ptype) {

		this.ptype = ptype;
	}

	public PostingType getPostingType() {

		return this.ptype;
	}

	public RegionBean getRegion() {

		return this.region;
	}

	public void setRegion(RegionBean region) {

		this.region = region;
	}

	public String getFormatedEndDate() {

		if (this.getEndDate() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(OtherJobOpportunityBean.DATE_FORMAT_STRING);

			return sdf.format(this.getEndDate());
		}
		else
			return "INDEFINITE";
	}

	public Date getCancelledDate() {

		return this.cancelled_date;
	}

	public void setCancelledDate(Date cancelled_date) {

		this.cancelled_date = cancelled_date;
	}

	public String getFormatedCancelledDate() {

		SimpleDateFormat sdf = new SimpleDateFormat(OtherJobOpportunityBean.DATE_FORMAT_STRING);

		return sdf.format(this.getCancelledDate());
	}

	public boolean isCancelled() {

		if (this.cancelled_date != null)
			return true;
		else
			return false;
	}

	public void setCancelled_reason(String cancelled_reason) {

		this.cancelled_reason = cancelled_reason;
	}

	public String getCancelled_reason() {

		return cancelled_reason;
	}

	public void setCancelled_by(String cancelled_by) {

		this.cancelled_by = cancelled_by;
	}

	public String getCancelled_by() {

		return cancelled_by;
	}

	public boolean isClosed() {

		if (Calendar.getInstance().getTime().after(this.getEndDate()))
			return true;
		else
			return false;
	}
}