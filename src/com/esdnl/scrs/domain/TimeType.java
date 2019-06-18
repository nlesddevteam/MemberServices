package com.esdnl.scrs.domain;

public class TimeType implements Comparable<TimeType> {

	private int typeId;
	private String typeName;
	private boolean isSpecified;
	private String specified;

	public TimeType() {

		super();
	}

	public int getTypeId() {

		return typeId;
	}

	public void setTypeId(int typeId) {

		this.typeId = typeId;
	}

	public String getTypeName() {

		return typeName;
	}

	public void setTypeName(String typeName) {

		this.typeName = typeName;
	}

	public boolean isIsSpecified() {

		return isSpecified;
	}

	public void setIsSpecified(boolean isSpecified) {

		this.isSpecified = isSpecified;
	}

	public String getSpecified() {

		return specified;
	}

	public void setSpecified(String specified) {

		this.specified = specified;
	}

	@Override
	public String toString() {

		return getTypeName();
	}

	public int compareTo(TimeType o) {

		return (new Integer(this.getTypeId())).compareTo(o.getTypeId());
	}

}
