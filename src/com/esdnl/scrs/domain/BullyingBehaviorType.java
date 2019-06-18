package com.esdnl.scrs.domain;

public class BullyingBehaviorType implements Comparable<BullyingBehaviorType> {

	private int typeId;
	private String typeName;

	public BullyingBehaviorType() {

		super();
	}

	public BullyingBehaviorType(int typeId) {

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

	@Override
	public String toString() {

		return getTypeName();
	}

	public int compareTo(BullyingBehaviorType o) {

		return (new Integer(this.getTypeId())).compareTo(o.getTypeId());
	}

}
