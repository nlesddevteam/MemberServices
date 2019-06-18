package com.esdnl.personnel.jobs.constants;

import java.io.Serializable;

public class OtherJobRegionConstant implements Serializable {

	private static final long serialVersionUID = 4238041416589319100L;
	private int value;
	private String desc;
	public static transient final OtherJobRegionConstant AVALONEAST = new OtherJobRegionConstant(1, "EASTERN REGION - AVALON EAST ZONE");
	public static transient final OtherJobRegionConstant AVALONWEST = new OtherJobRegionConstant(2, "EASTERN REGION - AVALON WEST ZONE");
	public static transient final OtherJobRegionConstant VISTA = new OtherJobRegionConstant(3, "EASTERN REGION - VISTA ZONE");
	public static transient final OtherJobRegionConstant BURIN = new OtherJobRegionConstant(4, "EASTERN REGION - BURIN ZONE");
	public static transient final OtherJobRegionConstant ALLEASTERNREGION = new OtherJobRegionConstant(5, "ALL EASTERN REGION");
	public static transient final OtherJobRegionConstant EASTERN = new OtherJobRegionConstant(6, "LABRADOR REGION - EASTERN ZONE");
	public static transient final OtherJobRegionConstant WESTERN = new OtherJobRegionConstant(7, "LABRADOR REGION - WESTERN ZONE");
	public static transient final OtherJobRegionConstant COASTAL = new OtherJobRegionConstant(8, "LABRADOR REGION - COASTAL ZONE");
	public static transient final OtherJobRegionConstant NORTHERN = new OtherJobRegionConstant(9, "WESTERN REGION - NORTHERN ZONE");
	public static transient final OtherJobRegionConstant CENTRAL = new OtherJobRegionConstant(10, "WESTERN REGION - CENTRAL ZONE");
	public static transient final OtherJobRegionConstant SOUTHERN = new OtherJobRegionConstant(11, "WESTERN REGION - SOUTHERN ZONE");
	public static transient final OtherJobRegionConstant ALLWESTERNREGION = new OtherJobRegionConstant(12, "ALL WESTERN REGION");
	public static transient final OtherJobRegionConstant ALLCENTRALREGION = new OtherJobRegionConstant(13, "ALL CENTRAL REGION");
	public static transient final OtherJobRegionConstant ALLLABRADORREGION = new OtherJobRegionConstant(14, "ALL LABRADOR REGION");
	public static final OtherJobRegionConstant[] ALL = new OtherJobRegionConstant[] {
			ALLCENTRALREGION, ALLEASTERNREGION, AVALONEAST, AVALONWEST, BURIN, VISTA, ALLLABRADORREGION, COASTAL, EASTERN,
			WESTERN, ALLWESTERNREGION, CENTRAL, NORTHERN, SOUTHERN
	};

	private OtherJobRegionConstant(int value, String desc) {

		this.value = value;
		this.desc = desc;
	}

	public int getValue() {

		return this.value;
	}

	public String getDescription() {

		return this.desc;
	}

	public static OtherJobRegionConstant get(int value) {

		switch (value) {
		case 1:
			return OtherJobRegionConstant.AVALONEAST;
		case 2:
			return OtherJobRegionConstant.AVALONWEST;
		case 3:
			return OtherJobRegionConstant.VISTA;
		case 4:
			return OtherJobRegionConstant.BURIN;
		case 5:
			return OtherJobRegionConstant.ALLEASTERNREGION;
		case 6:
			return OtherJobRegionConstant.EASTERN;
		case 7:
			return OtherJobRegionConstant.WESTERN;
		case 8:
			return OtherJobRegionConstant.COASTAL;
		case 9:
			return OtherJobRegionConstant.NORTHERN;
		case 10:
			return OtherJobRegionConstant.CENTRAL;
		case 11:
			return OtherJobRegionConstant.SOUTHERN;
		case 12:
			return OtherJobRegionConstant.ALLWESTERNREGION;
		case 13:
			return OtherJobRegionConstant.ALLCENTRALREGION;
		case 14:
			return OtherJobRegionConstant.ALLLABRADORREGION;
		default:
			return null;
		}
	}

	public boolean equal(Object o) {

		if (!(o instanceof OtherJobRegionConstant))
			return false;
		else
			return (this.getValue() == ((OtherJobRegionConstant) o).getValue());
	}

	public String toString() {

		return this.getDescription();
	}
}