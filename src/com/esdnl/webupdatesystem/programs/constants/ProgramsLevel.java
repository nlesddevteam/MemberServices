package com.esdnl.webupdatesystem.programs.constants;

public class ProgramsLevel {
	private int value;
	private String desc;
	public static final ProgramsLevel PROGRAMS_PRIMELEM = new ProgramsLevel(1, "PRIMARY/ELEMENTARY");
	public static final ProgramsLevel PROGRAMS_INTERMEDIATE= new ProgramsLevel(2, "INTERMEDIATE");
	public static final ProgramsLevel PROGRAMS_SENIOR = new ProgramsLevel(3, "SENIOR");
	
	public static final ProgramsLevel[] ALL = new ProgramsLevel[] {
		PROGRAMS_PRIMELEM , PROGRAMS_INTERMEDIATE,PROGRAMS_SENIOR
	};
	private ProgramsLevel(int value, String desc) {
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
		if (!(o instanceof ProgramsLevel))
			return false;
		else
			return (this.getValue() == ((ProgramsLevel) o).getValue());
	}
	public static ProgramsLevel get(int value) {
		ProgramsLevel tmp = null;
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