package com.esdnl.scrs.domain;

public class IllegalSubstanceType implements Comparable<IllegalSubstanceType> {

	private int typeId;
	private String typeName;
	private boolean isSpecified;
	private String specified;
	private boolean other;

	public IllegalSubstanceType() {

		super();
	}

	public IllegalSubstanceType(int typeId) {

		this.typeId = typeId;
		this.typeName = null;
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

	public int compareTo(IllegalSubstanceType o) {

		return (new Integer(this.getTypeId())).compareTo(o.getTypeId());
	}

}
