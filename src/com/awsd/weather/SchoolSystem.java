package com.awsd.weather;

import java.util.Arrays;
import java.util.List;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.PersonnelException;
import com.awsd.school.School;
import com.awsd.school.SchoolException;
import com.awsd.school.Schools;
import com.nlesd.school.bean.SchoolZoneBean;

public class SchoolSystem {

	private int ss_id;
	private String ss_name;
	private int admin_id;
	private String[] admin_bckup_id;
	private Personnel admin_prec = null;
	private Personnel[] admin_bckup_prec = null;
	private Schools schools = null;

	public SchoolSystem(String ss_name) {

		this(-1, ss_name, -1, null);
	}

	public SchoolSystem(String ss_name, int ss_admin) {

		this(-1, ss_name, ss_admin, null);
	}

	public SchoolSystem(String ss_name, int ss_admin, String[] ss_admin_bckup) {

		this(-1, ss_name, ss_admin, ss_admin_bckup);
	}

	public SchoolSystem(int ss_id, String ss_name, int admin_id) {

		this(ss_id, ss_name, admin_id, null);
	}

	public SchoolSystem(int ss_id, String ss_name, int admin_id, String[] admin_bckup_id) {

		this.ss_id = ss_id;
		this.ss_name = ss_name;
		this.admin_id = admin_id;
		this.admin_bckup_id = admin_bckup_id;
	}

	public int getSchoolSystemID() {

		return ss_id;
	}

	public String getSchoolSystemName() {

		return ss_name;
	}

	public Personnel getSchoolSystemAdmin() throws PersonnelException {

		if ((admin_prec == null) && (admin_id > 0)) {
			admin_prec = PersonnelDB.getPersonnel(admin_id);
		}

		return admin_prec;
	}

	public Personnel[] getSchoolSystemAdminBackup() throws PersonnelException {

		if ((admin_bckup_prec == null) && (admin_bckup_id != null)) {
			admin_bckup_prec = PersonnelDB.getPersonnel(admin_bckup_id);
		}

		return admin_bckup_prec;
	}

	public List<Personnel> getSchoolSystemAdminBackupAsList() {

		return Arrays.asList(this.admin_bckup_prec);
	}

	public int[] getSchoolSystemAdminBackupAsIntArray() throws PersonnelException {

		int i = 0;
		int[] id = new int[getSchoolSystemAdminBackup().length];

		for (Personnel p : getSchoolSystemAdminBackup())
			id[i++] = p.getPersonnelID();

		return id;
	}

	public Schools getSchoolSystemSchools() throws SchoolException {

		if (schools == null) {
			schools = new Schools(this);
		}

		return schools;
	}

	public void setSchoolSystemSchools(Schools schools) {

		this.schools = schools;
	}

	public SchoolZoneBean getZone() throws SchoolException {

		SchoolZoneBean zone = null;

		for (School school : getSchoolSystemSchools()) {
			if (school.getZone() != null) {
				zone = school.getZone();

				break;
			}
		}

		return zone;
	}
}