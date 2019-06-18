package com.esdnl.webupdatesystem.banners.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BannersBean implements Serializable {
	private static final long serialVersionUID = -8771605504141293333L;
	private Integer id;
	private String bannerFile;
	private Integer bannerRotation;
	private String bannerLink;
	private Integer bannerStatus;
	private Integer bannerShowPublic;
	private Integer bannerShowStaff;
	private Integer bannerShowBusiness;
	private String bannerCode;
	private String addedBy;
	private Date dateAdded;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBannerFile() {
		return bannerFile;
	}
	public void setBannerFile(String bannerFile) {
		this.bannerFile = bannerFile;
	}
	public Integer getBannerRotation() {
		return bannerRotation;
	}
	public void setBannerRotation(Integer bannerRotation) {
		this.bannerRotation = bannerRotation;
	}
	public String getBannerLink() {
		return bannerLink;
	}
	public void setBannerLink(String bannerLink) {
		this.bannerLink = bannerLink;
	}
	public Integer getBannerStatus() {
		return bannerStatus;
	}
	public void setBannerStatus(Integer bannerStatus) {
		this.bannerStatus = bannerStatus;
	}
	public Integer getBannerShowPublic() {
		return bannerShowPublic;
	}
	public void setBannerShowPublic(Integer bannerShowPublic) {
		this.bannerShowPublic = bannerShowPublic;
	}
	public Integer getBannerShowStaff() {
		return bannerShowStaff;
	}
	public void setBannerShowStaff(Integer bannerShowStaff) {
		this.bannerShowStaff = bannerShowStaff;
	}
	public Integer getBannerShowBusiness() {
		return bannerShowBusiness;
	}
	public void setBannerShowBusiness(Integer bannerShowBusiness) {
		this.bannerShowBusiness = bannerShowBusiness;
	}
	public String getBannerCode() {
		return bannerCode;
	}
	public void setBannerCode(String bannerCode) {
		this.bannerCode = bannerCode;
	}
	public String getAddedBy() {
		return addedBy;
	}
	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}
	public Date getDateAdded() {
		return dateAdded;
	}
	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}
	public String getDateAddedFormatted() {

		return new SimpleDateFormat("dd/MM/yyyy").format(this.dateAdded);
	}
	
}
