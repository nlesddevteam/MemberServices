package com.esdnl.webupdatesystem.blogs.constants;
public class BlogStatus {
	private int value;
	private String desc;
	public static final BlogStatus BLOG_ACTIVE = new BlogStatus(1, "ACTIVE");
	public static final BlogStatus BLOG_ARCHIVED= new BlogStatus(2, "ARCHIVED");
	public static final BlogStatus BLOG_DISABLED = new BlogStatus(3, "DISABLED");
	
	public static final BlogStatus[] ALL = new BlogStatus[] {
		BLOG_ACTIVE , BLOG_ARCHIVED,BLOG_DISABLED
	};
	private BlogStatus(int value, String desc) {
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
		if (!(o instanceof BlogStatus))
			return false;
		else
			return (this.getValue() == ((BlogStatus) o).getValue());
	}
	public static BlogStatus get(int value) {
		BlogStatus tmp = null;
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
