package com.nlesd.webmaint.bean;

import java.io.Serializable;

import com.nlesd.school.bean.SchoolZoneBean;

public class StaffDirectoryContactBean implements Serializable {

	private static final long serialVersionUID = -6913823723715477063L;

	public enum STAFF_DIRECTORY_DIVISION {

		INVALID(0, ""), 
		DIRECTORSOFFICE(1, "Director's Office"), 
		PROGRAMS(2, "Programs"), 
		HR(3, "Human Resources"), 
		FINANCE(4, "Finance and Business Administration"), 
		OPERATIONS(5, "Operations"), 
		CORPORATESERVICES(6,"Corporate Services"), 
		STUDENTSERVICES(7,"Student Services"),
		SCHOOLSYSTEMS(8,"School Systems"), 
		EXECUTIVE(9,"Executive");

		private int id;
		private String name;

		private STAFF_DIRECTORY_DIVISION(int id, String name) {

			this.id = id;
			this.name = name;
		}

		public int getId() {

			return this.id;
		}

		public String getName() {

			return this.name;
		}

		public boolean equals(STAFF_DIRECTORY_DIVISION sdd) {

			return this.id == sdd.getId();
		}

		public static STAFF_DIRECTORY_DIVISION get(int id) {

			STAFF_DIRECTORY_DIVISION tmp = INVALID;

			for (STAFF_DIRECTORY_DIVISION sdd : STAFF_DIRECTORY_DIVISION.values()) {
				if (sdd.getId() == id) {
					tmp = sdd;
				}
			}

			return tmp;
		}
	}

	private int contactId;
	private SchoolZoneBean zone;
	private STAFF_DIRECTORY_DIVISION division;
	private String fullName;
	private String position;
	private String telephone;
	private String extension;
	private String cellphone;
	private String fax;
	private String email;
	private int sortorder;

	public StaffDirectoryContactBean() {

		this.contactId = 0;
		this.zone = null;
		this.division = STAFF_DIRECTORY_DIVISION.INVALID;
		this.fullName = "";
		this.position = "";
		this.telephone = "";
		this.cellphone = "";
		this.extension = "";
		this.fax = "";
		this.email = "";
		this.sortorder = 0;
	}

	public int getContactId() {

		return contactId;
	}

	public void setContactId(int contactId) {

		this.contactId = contactId;
	}

	public SchoolZoneBean getZone() {

		return zone;
	}

	public void setZone(SchoolZoneBean zone) {

		this.zone = zone;
	}

	public STAFF_DIRECTORY_DIVISION getDivision() {

		return division;
	}

	public void setDivision(STAFF_DIRECTORY_DIVISION division) {

		this.division = division;
	}

	public String getFullName() {

		return fullName;
	}

	public void setFullName(String fullName) {

		this.fullName = fullName;
	}

	public String getPosition() {

		return position;
	}

	public void setPosition(String position) {

		this.position = position;
	}

	public String getTelephone() {

		return telephone;
	}

	public void setTelephone(String telephone) {

		this.telephone = telephone;
	}

	public String getExtension() {

		return extension;
	}

	public void setExtension(String extension) {

		this.extension = extension;
	}

	public String getCellphone() {

		return cellphone;
	}

	public void setCellphone(String cellphone) {

		this.cellphone = cellphone;
	}

	public String getFax() {

		return fax;
	}

	public void setFax(String fax) {

		this.fax = fax;
	}

	public String getEmail() {

		return email;
	}

	public void setEmail(String email) {

		this.email = email;
	}

	public int getSortorder() {

		return sortorder;
	}

	public void setSortorder(int sortorder) {

		this.sortorder = sortorder;
	}

}
