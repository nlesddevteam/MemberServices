package com.esdnl.webupdatesystem.newspostings.constants;


public class NewsStatus {
	private int value;
	private String desc;
	public static final NewsStatus ENABLED = new NewsStatus(1, "ENABLED");
	public static final NewsStatus DISABLED= new NewsStatus(2, "DISABLED");
	public static final NewsStatus ARCHIVED = new NewsStatus(3, "ARCHIVED");
	public static final NewsStatus[] ALL = new NewsStatus[] {
		ENABLED, DISABLED,ARCHIVED
	};
	private NewsStatus(int value, String desc) {
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
		if (!(o instanceof NewsStatus))
			return false;
		else
			return (this.getValue() == ((NewsStatus) o).getValue());
	}
	public static NewsStatus get(int value) {
		NewsStatus tmp = null;
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
