package com.esdnl.personnel.jobs.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TeacherAllocationExtraBean {

	public enum AllocationType {
		UNKNOWN(0, "UNKNOWN"), TCHR(1, "Teacher/Administrator"), TLA(2, "Teaching and Learning Assistant"), SA(3,
				"Student Assistant");

		private int value;
		private String desc;

		private AllocationType(int value, String desc) {

			this.value = value;
			this.desc = desc;
		}

		public int getValue() {

			return this.value;
		}

		public boolean equals(AllocationType at) {

			return ((at != null) && (at.getValue() == this.getValue())) ? true : false;
		}

		public String toString() {

			return this.desc + " (" + this.name() + ")";
		}

		public static AllocationType get(int value) {

			AllocationType tmp = UNKNOWN;

			for (AllocationType at : AllocationType.values()) {
				if (at.getValue() == value) {
					tmp = at;
					break;
				}
			}

			return tmp;
		}
	}

	private int extraId;
	private int allocationId;
	private double extraAllocationUnits;
	private String rationale;
	private AllocationType allocationType;
	private Date createdDate;

	public TeacherAllocationExtraBean() {

		this.extraId = 0;
		this.allocationId = 0;
		this.extraAllocationUnits = 0.0;
		this.rationale = null;
		this.allocationType = AllocationType.TCHR;
	}

	public int getExtraId() {

		return extraId;
	}

	public void setExtraId(int extraId) {

		this.extraId = extraId;
	}

	public int getAllocationId() {

		return allocationId;
	}

	public void setAllocationId(int allocationId) {

		this.allocationId = allocationId;
	}

	public double getExtraAllocationUnits() {

		return extraAllocationUnits;
	}

	public void setExtraAllocationUnits(double extraAllocationUnits) {

		this.extraAllocationUnits = extraAllocationUnits;
	}

	public String getRationale() {

		return rationale;
	}

	public void setRationale(String description) {

		this.rationale = description;
	}

	public AllocationType getAllocationType() {

		return this.allocationType;
	}

	public void setAllocationType(AllocationType allocationType) {

		this.allocationType = allocationType;
	}

	public Date getCreatedDate() {

		return this.createdDate;
	}

	public String getCreatedDateFormated() {

		return ((getCreatedDate() != null) ? (new SimpleDateFormat("dd-MM-yyyy")).format(this.createdDate) : "");
	}

	public void setCreatedDate(Date createdDate) {

		this.createdDate = createdDate;
	}

	public String toXML() {

		StringBuffer buf = new StringBuffer();

		buf.append("<TEACHER-ALLOCATION-EXTRA-BEAN ALLOCATION-ID=\"" + this.allocationId + "\" EXTRA-ID=\"" + this.extraId
				+ "\" EXTRA-UNITS=\"" + this.extraAllocationUnits + "\" ALLOCATION-TYPE-ID=\"" + this.allocationType.value
				+ "\" ALLOCATION-TYPE-CODE=\"" + this.allocationType.name() + "\" ALLOCATION-TYPE-DESC=\""
				+ this.allocationType.toString() + "\" CREATED-DATE=\"" + this.getCreatedDateFormated() + "\" RATIONALE=\""
				+ this.getRationale() + "\" />");

		return buf.toString();
	}

}
