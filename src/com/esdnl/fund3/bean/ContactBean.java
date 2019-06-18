package com.esdnl.fund3.bean;

import java.io.Serializable;

import com.nlesd.school.bean.SchoolZoneBean;

public class ContactBean implements Serializable {
		private static final long serialVersionUID = -8771605504141293333L;
		private Integer id;
		private String contactName;
		private String contactTitle;
		private String contactPhone;
		private String contactEmail;
		private SchoolZoneBean contactZone;
		private Integer isActive;
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getContactName() {
			return contactName;
		}
		public void setContactName(String contactName) {
			this.contactName = contactName;
		}
		public String getContactTitle() {
			return contactTitle;
		}
		public void setContactTitle(String contactTitle) {
			this.contactTitle = contactTitle;
		}
		public String getContactPhone() {
			return contactPhone;
		}
		public void setContactPhone(String contactPhone) {
			this.contactPhone = contactPhone;
		}
		public String getContactEmail() {
			return contactEmail;
		}
		public void setContactEmail(String contactEmail) {
			this.contactEmail = contactEmail;
		}
		public SchoolZoneBean getContactZone() {
			return contactZone;
		}
		public void setContactZone(SchoolZoneBean contactZone) {
			this.contactZone = contactZone;
		}
		public Integer getIsActive() {
			return isActive;
		}
		public void setIsActive(Integer isActive) {
			this.isActive = isActive;
		}
		public String getIsActiveString()
		{
			if(isActive == 1)
			{
				return "Active";
			}else{
				return "InActive";
			}
		}
		public String getZoneNameCap(){
			if(this.contactZone == null){
				return "";
			}else{
				return  this.contactZone.getZoneName().substring(0, 1).toUpperCase() + this.contactZone.getZoneName().substring(1);
			}
		}

}
