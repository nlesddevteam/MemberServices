package com.nlesd.bcs.constants;

public class VehicleStatusConstant {
	private int value;
	private String desc;
	public static final VehicleStatusConstant SUBMITTED= new VehicleStatusConstant(1, "Not Yet Submitted");
	public static final VehicleStatusConstant APPROVED= new VehicleStatusConstant(2, "Approved For Use");
	public static final VehicleStatusConstant REJECTED= new VehicleStatusConstant(3, "Rejected For Use");
	public static final VehicleStatusConstant SUSPENDED= new VehicleStatusConstant(4, "Suspended For Use");
	public static final VehicleStatusConstant REMOVED= new VehicleStatusConstant(5, "Removed From System");
	public static final VehicleStatusConstant SUBMITTEDFORREVIEW =new VehicleStatusConstant(6, "Submitted For Approval");
	


	public static final VehicleStatusConstant[] ALL = new VehicleStatusConstant[] {
		SUBMITTED, APPROVED, REJECTED, SUSPENDED, REMOVED,SUBMITTEDFORREVIEW
	};

	private VehicleStatusConstant(int value, String desc) {

		this.value = value;
		this.desc = desc;
	}

	public int getValue() {

		return this.value;
	}

	public String getDescription() {

		return this.desc;
	}

	public boolean equal(Object o) {

		if (!(o instanceof VehicleStatusConstant))
			return false;
		else
			return (this.getValue() == ((VehicleStatusConstant) o).getValue());
	}

	public static VehicleStatusConstant get(int value) {

		VehicleStatusConstant tmp = null;

		for (int i = 0; i < ALL.length; i++) {
			if (ALL[i].getValue() == value) {
				tmp = ALL[i];
				break;
			}
		}

		return tmp;
	}

	public String toString() {

		return this.getDescription();
	}

} 
