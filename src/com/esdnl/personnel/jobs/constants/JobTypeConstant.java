package com.esdnl.personnel.jobs.constants;

import java.io.Serializable;

public class JobTypeConstant implements Serializable {

	private static final long serialVersionUID = 4238041416589319100L;

	private int value;
	private String desc;

	public static transient final JobTypeConstant REGULAR = new JobTypeConstant(1, "TEACHER PERMANENT POSITION");
	public static transient final JobTypeConstant REPLACEMENT = new JobTypeConstant(2, "TEACHER REPLACEMENT/TERM POSITION");
	public static transient final JobTypeConstant TRANSFER = new JobTypeConstant(3, "TRANSFER POSITION");
	public static transient final JobTypeConstant ADMINISTRATIVE = new JobTypeConstant(4, "ADMINISTRATIVE POSITION");
	public static transient final JobTypeConstant LEADERSHIP = new JobTypeConstant(5, "LEADERSHIP POSITION");
	public static transient final JobTypeConstant AWARDED = new JobTypeConstant(6, "AWARDED POSITION");
	public static transient final JobTypeConstant SUMMER_SCHOOL = new JobTypeConstant(7, "SUMMER SCHOOL POSITION");
	public static transient final JobTypeConstant POOL = new JobTypeConstant(8, "GENERAL INTERVIEWS");
	public static transient final JobTypeConstant TLA_REGULAR = new JobTypeConstant(9, "TEACHING AND LEARNING ASSISTANT VACANT POSITION");
	public static transient final JobTypeConstant TLA_REPLACEMENT = new JobTypeConstant(10, "TEACHING AND LEARNING ASSISTANT REPLACEMENT/TERM POSITION");
	public static transient final JobTypeConstant INTERNALONLY = new JobTypeConstant(11, "INTERNAL ONLY");
	public static transient final JobTypeConstant EXTERNALONLY = new JobTypeConstant(12, "EXTERNAL ONLY");
	public static transient final JobTypeConstant INTERNALEXTERNAL = new JobTypeConstant(13, "INTERNAL AND EXTERNAL");

	public static final JobTypeConstant[] ALL = new JobTypeConstant[] {
			POOL, REGULAR, REPLACEMENT, TLA_REGULAR, TLA_REPLACEMENT, ADMINISTRATIVE, LEADERSHIP, SUMMER_SCHOOL, AWARDED,
			INTERNALONLY, EXTERNALONLY, INTERNALEXTERNAL
	};

	private JobTypeConstant(int value, String desc) {

		this.value = value;
		this.desc = desc;
	}

	public int getValue() {

		return this.value;
	}

	public String getDescription() {

		return this.desc;
	}

	public static JobTypeConstant get(int value) {

		switch (value) {
		case 1:
			return JobTypeConstant.REGULAR;
		case 2:
			return JobTypeConstant.REPLACEMENT;
		case 3:
			return JobTypeConstant.TRANSFER;
		case 4:
			return JobTypeConstant.ADMINISTRATIVE;
		case 5:
			return JobTypeConstant.LEADERSHIP;
		case 6:
			return JobTypeConstant.AWARDED;
		case 7:
			return JobTypeConstant.SUMMER_SCHOOL;
		case 8:
			return JobTypeConstant.POOL;
		case 9:
			return JobTypeConstant.TLA_REGULAR;
		case 10:
			return JobTypeConstant.TLA_REPLACEMENT;
		case 11:
			return JobTypeConstant.INTERNALONLY;
		case 12:
			return JobTypeConstant.EXTERNALONLY;
		case 13:
			return JobTypeConstant.INTERNALEXTERNAL;
		default:
			return null;
		}
	}

	public boolean equal(Object o) {

		if (!(o instanceof JobTypeConstant))
			return false;
		else
			return (this.getValue() == ((JobTypeConstant) o).getValue());
	}

	public String toString() {

		return this.getDescription();
	}
}
