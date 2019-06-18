package com.esdnl.webmaint.esdweb.bean;

import com.esdnl.webmaint.esdweb.constants.AnnouncementTypeConstant;

public class AnnouncementSettingsBean {

	private int settingId;
	private AnnouncementTypeConstant type;
	private int exportLimit;

	public int getSettingId() {

		return settingId;
	}

	public void setSettingId(int settingId) {

		this.settingId = settingId;
	}

	public AnnouncementTypeConstant getType() {

		return type;
	}

	public void setType(AnnouncementTypeConstant type) {

		this.type = type;
	}

	public int getExportLimit() {

		return exportLimit;
	}

	public void setExportLimit(int exportLimit) {

		this.exportLimit = exportLimit;
	}

}
