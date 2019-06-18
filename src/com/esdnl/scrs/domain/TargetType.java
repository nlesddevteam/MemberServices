package com.esdnl.scrs.domain;

public class TargetType implements Comparable<TargetType> {

	private int typeId;
	private String typeName;
	private boolean isSpecified;
	private String specified;
	private boolean other;

	public TargetType() {

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

	public boolean isOther() {

		return other;
	}

	public void setOther(boolean other) {

		this.other = other;
	}

	@Override
	public String toString() {

		return getTypeName();
	}

	public int compareTo(TargetType o) {

		return (new Integer(this.getTypeId())).compareTo(o.getTypeId());
	}

}
