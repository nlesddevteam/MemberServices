package com.esdnl.personnel.jobs.constants;

import java.io.Serializable;

public class TrainingMethodConstant implements Serializable {

	private static final long serialVersionUID = -664796764848873226L;
	private int value;
	private String desc;

	public static final TrainingMethodConstant NOT_APPLICABLE = new TrainingMethodConstant(0, "NOT APPLICABLE");
	//public static final TrainingMethodConstant PRIMARY = new TrainingMethodConstant(1, "PRIMARY");
	public transient static final TrainingMethodConstant PRIMARY_ELEMENTARY = new TrainingMethodConstant(2, "PRIMARY-ELEMENTARY");
	public transient static final TrainingMethodConstant SECONDARY = new TrainingMethodConstant(3, "SECONDARY");
	//public static final TrainingMethodConstant ELEMENTARY = new TrainingMethodConstant(4, "ELEMENTARY");
	public transient static final TrainingMethodConstant PRIMARY_ELEMENTARY_SECONDARY = new TrainingMethodConstant(5, "PRIM/ELEM/SEC");

	public transient static final TrainingMethodConstant[] ALL = new TrainingMethodConstant[] {
			NOT_APPLICABLE, PRIMARY_ELEMENTARY, SECONDARY, PRIMARY_ELEMENTARY_SECONDARY
	};

	private TrainingMethodConstant(int value, String desc) {

		this.value = value;
		this.desc = desc;
	}

	public int getValue() {

		return this.value;
	}

	public String getDescription() {

		return this.desc;
	}

	public static TrainingMethodConstant get(int value) {

		switch (value) {
		case 2:
			return TrainingMethodConstant.PRIMARY_ELEMENTARY;
		case 3:
			return TrainingMethodConstant.SECONDARY;
		case 5:
			return TrainingMethodConstant.PRIMARY_ELEMENTARY_SECONDARY;
		default:
			return TrainingMethodConstant.NOT_APPLICABLE;
		}
	}

	public boolean equal(Object o) {

		if (!(o instanceof TrainingMethodConstant))
			return false;
		else
			return (this.getValue() == ((TrainingMethodConstant) o).getValue());
	}

	public String toString() {

		return this.getDescription();
	}
}
