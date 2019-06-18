package com.esdnl.webmaint.esdweb.constants;

import com.esdnl.webmaint.common.bean.MessageTypeBean;

public class AnnouncementTypeConstant extends MessageTypeBean {

	public static final AnnouncementTypeConstant LATEST_ANNOUNCEMENTS = new AnnouncementTypeConstant(99, "LATEST ANNOUNCEMENTS");

	public static final AnnouncementTypeConstant ANNOUNCEMENT = new AnnouncementTypeConstant(1, "ANNOUNCEMENT");
	public static final AnnouncementTypeConstant MEDIA_RELEASE = new AnnouncementTypeConstant(2, "MEDIA RELEASE");
	public static final AnnouncementTypeConstant GOOD_NEWS = new AnnouncementTypeConstant(3, "GOOD NEWS IN OUR SCHOOLS");
	public static final AnnouncementTypeConstant BULLETIN_BOARD = new AnnouncementTypeConstant(4, "BULLETIN BOARD");
	public static final AnnouncementTypeConstant SPORTS_NEWS = new AnnouncementTypeConstant(5, "SPORTS NEWS");
	public static final AnnouncementTypeConstant PUBLICATIONS = new AnnouncementTypeConstant(6, "PUBLICATIONS");
	public static final AnnouncementTypeConstant DIRECTORS_BLOG = new AnnouncementTypeConstant(7, "DIRECTOR'S BLOG");
	public static final AnnouncementTypeConstant SCHOOLS_IN_NEWS = new AnnouncementTypeConstant(8, "SCHOOLS IN THE NEWS");

	public static final AnnouncementTypeConstant[] ALL = new AnnouncementTypeConstant[] {
			ANNOUNCEMENT, MEDIA_RELEASE, GOOD_NEWS, BULLETIN_BOARD, SPORTS_NEWS, PUBLICATIONS, DIRECTORS_BLOG,
			SCHOOLS_IN_NEWS
	};

	private AnnouncementTypeConstant(int type_id, String desc) {

		super(type_id, desc);
	}

	public static AnnouncementTypeConstant get(int type_id) {

		AnnouncementTypeConstant tmp = null;

		for (int i = 0; i < ALL.length; i++) {
			if (ALL[i].getTypeID() == type_id) {
				tmp = ALL[i];
				break;
			}
		}

		return tmp;
	}

	public boolean equals(AnnouncementTypeConstant type) {

		return this.getTypeID() == type.getTypeID();
	}
}