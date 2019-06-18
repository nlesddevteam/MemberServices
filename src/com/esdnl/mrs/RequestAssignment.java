package com.esdnl.mrs;

import java.io.Serializable;
import java.util.Date;

import com.awsd.personnel.Personnel;

public class RequestAssignment implements Serializable {

	private static final long serialVersionUID = 3626836432039296386L;

	//private Personnel maint = null;
	//private Vendor vendor = null;
	//private PersonnelType type = null;
	private Object obj = null;
	private Date assigned_date = null;

	public RequestAssignment(Personnel maint, Date assigned_date) {

		//this.maint = maint;
		this.assigned_date = assigned_date;
		//this.vendor = null;
		this.obj = maint;
	}

	public RequestAssignment(Vendor vendor, Date assigned_date) {

		//this.vendor = vendor;
		this.assigned_date = assigned_date;
		//this.maint = null;
		this.obj = vendor;
	}

	public RequestAssignment(PersonnelType type, Date assigned_date) {

		this.assigned_date = assigned_date;
		this.obj = type;
	}

	public Personnel getMaintenancePersonnel() {

		if (obj instanceof Personnel)
			return (Personnel) this.obj;
		else
			return null;
	}

	public Vendor getVendor() {

		if (obj instanceof Vendor)
			return (Vendor) this.obj;
		else
			return null;
	}

	public PersonnelType getPersonnelType() {

		if (obj instanceof PersonnelType)
			return (PersonnelType) obj;
		else
			return null;
	}

	public Date getAssignedDate() {

		return this.assigned_date;
	}

	public String toString() {

		String str = "";

		if (obj instanceof Personnel)
			str = ((Personnel) obj).getFullNameReverse();
		else if (obj instanceof Vendor)
			str = ((Vendor) obj).getVendorName();
		else if (obj instanceof PersonnelType)
			str = ((PersonnelType) obj).getDescription();

		return str;
	}

	public int getId() {

		int id = 0;

		if (obj instanceof Personnel)
			id = ((Personnel) obj).getPersonnelID();
		else if (obj instanceof Vendor)
			id = ((Vendor) obj).getVendorID();
		else if (obj instanceof PersonnelType)
			id = ((PersonnelType) obj).getValue();

		return id;
	}
}