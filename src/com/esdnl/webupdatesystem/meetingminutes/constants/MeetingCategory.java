package com.esdnl.webupdatesystem.meetingminutes.constants;

import java.util.HashMap;
import java.util.Map;
public class MeetingCategory {
	private int value;
	private String desc;
	public static final MeetingCategory BOARD = new MeetingCategory(1, "BOARD");
	public static final MeetingCategory EXECUTIVE= new MeetingCategory(2, "EXECUTIVE");
	public static final MeetingCategory PROGRAMS_HUMAN_RESOURCES = new MeetingCategory(3, "PROGRAMS AND HUMAN RESOURCES");
	public static final MeetingCategory FINANCE_OPERATIONS = new MeetingCategory(4, "FINANCE AND OPERATIONS");
	public static final MeetingCategory NISEP = new MeetingCategory(5, "NISEP");
	public static final MeetingCategory OTHER = new MeetingCategory(6, "OTHER");
	public static final MeetingCategory[] ALL = new MeetingCategory[] {
		BOARD, EXECUTIVE,PROGRAMS_HUMAN_RESOURCES,FINANCE_OPERATIONS,NISEP,OTHER
	};
	private MeetingCategory(int value, String desc) {
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
		if (!(o instanceof MeetingCategory))
			return false;
		else
			return (this.getValue() == ((MeetingCategory) o).getValue());
	}
	public static MeetingCategory get(int value) {
		MeetingCategory tmp = null;
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
	public static Map<Integer,String> getCategoriesList()
	{
		Map<Integer,String> categorylist = new HashMap<Integer,String>();
		for(MeetingCategory t : MeetingCategory.ALL)
		{
			categorylist.put(t.getValue(),t.getDescription());
		}
		return categorylist;
	}
}
