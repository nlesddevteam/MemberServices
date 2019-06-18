package com.esdnl.webupdatesystem.programs.constants;

public class ProgramsRegion {
	private int value;
	private String desc;
	public static final ProgramsRegion PROGRAMS_COMMON = new ProgramsRegion(1, "COMMON");
	public static final ProgramsRegion PROGRAMS_EASTERN= new ProgramsRegion(2, "EASTERN");
	public static final ProgramsRegion PROGRAMS_WESTERN = new ProgramsRegion(3, "WESTERN");
	public static final ProgramsRegion PROGRAMS_CENTRAL= new ProgramsRegion(4, "CENTRAL");
	public static final ProgramsRegion PROGRAMS_LABRADOR= new ProgramsRegion(5, "LABRADOR");
	
	public static final ProgramsRegion[] ALL = new ProgramsRegion[] {
		PROGRAMS_COMMON , PROGRAMS_EASTERN,PROGRAMS_WESTERN,PROGRAMS_CENTRAL,PROGRAMS_LABRADOR
	};
	private ProgramsRegion(int value, String desc) {
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
		if (!(o instanceof ProgramsRegion))
			return false;
		else
			return (this.getValue() == ((ProgramsRegion) o).getValue());
	}
	public static ProgramsRegion get(int value) {
		ProgramsRegion tmp = null;
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
