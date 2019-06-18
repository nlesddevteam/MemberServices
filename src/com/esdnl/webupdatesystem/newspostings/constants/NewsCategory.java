package com.esdnl.webupdatesystem.newspostings.constants;

import java.util.HashMap;
import java.util.Map;
public class NewsCategory {
	private int value;
	private String desc;
	public static final NewsCategory GOOD_NEWS= new NewsCategory(1, "GOOD NEWS");
	public static final NewsCategory ANNOUNCEMENTS= new NewsCategory(2, "ANNOUNCEMENTS");
	public static final NewsCategory MEDIA_RELEASE = new NewsCategory(3, "MEDIA RELEASE");
	public static final NewsCategory SCHOOLS_IN_THE_NEWS= new NewsCategory(4, "SCHOOLS IN THE NEWS");
	public static final NewsCategory IMPORTANT_NOTICE = new NewsCategory(5, "IMPORTANT NOTICE");
	public static final NewsCategory STAFF_NEWS = new NewsCategory(6, "STAFF NEWS");
	
	public static final NewsCategory[] ALL = new NewsCategory[] {
		GOOD_NEWS , ANNOUNCEMENTS,MEDIA_RELEASE,SCHOOLS_IN_THE_NEWS,IMPORTANT_NOTICE,STAFF_NEWS
	};
	private NewsCategory(int value, String desc) {
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
		if (!(o instanceof NewsCategory))
			return false;
		else
			return (this.getValue() == ((NewsCategory) o).getValue());
	}
	public static NewsCategory get(int value) {
		NewsCategory tmp = null;
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
		for(NewsCategory t : NewsCategory.ALL)
		{
			categorylist.put(t.getValue(),t.getDescription());
		}
		return categorylist;
	}
}
