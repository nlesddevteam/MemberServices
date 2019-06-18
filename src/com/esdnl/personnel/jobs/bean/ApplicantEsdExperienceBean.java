package com.esdnl.personnel.jobs.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.awsd.school.SchoolException;

public class ApplicantEsdExperienceBean implements Serializable {

	private static final long serialVersionUID = 5304511837525367873L;

	public static final String DATE_FORMAT = "MM/yyyy";

	private int id;
	private String sin;
	private int perm_school;
	private String perm_position;
	private int contract_school;
	private Date contract_enddate;
	private int perm_time;
	private int repl_time;
	private int sub_time;
	private int perml_time;

	private static HashMap<Integer, String> school_names = null;

	// optomize school name lookup
	static {
		School tmp = null;
		school_names = new HashMap<Integer, String>();
		try {
			//school_names=SchoolDB.getSchoolsSmallDD();

			Iterator<School> iter = SchoolDB.getSchools().iterator();
			while (iter.hasNext()) {
				tmp = (School) iter.next();
				school_names.put(new Integer(tmp.getSchoolID()), tmp.getSchoolName());
			}

		}
		catch (SchoolException e) {
			e.printStackTrace(System.err);
		}
	}

	public ApplicantEsdExperienceBean() {

		this.id = -1;
		this.sin = null;
		this.perm_school = -1;
		this.perm_position = null;
		this.contract_school = -1;
		this.contract_enddate = null;
		this.repl_time = 0;
		this.sub_time = 0;
		this.perm_time = 0;
	}

	public int getId() {

		return this.id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public String getSIN() {

		return this.sin;
	}

	public void setSIN(String sin) {

		this.sin = sin;
	}

	public int getPermanentContractSchool() {

		return this.perm_school;
	}

	public void setPermanentContractSchool(int perm_school) {

		this.perm_school = perm_school;
	}

	public String getPermanentContractLocationText() {

		String txt = null;

		switch (this.perm_school) {
		case -3000:
			txt = "Central Regional Office";
			break;
		case -2000:
			txt = "Western Regional Office";
			break;
		case -1000:
			txt = "Labrador Regional Office";
			break;
		case -999:
			txt = "District Office";
			break;
		case -998:
			txt = "Eastern Regional Office";
			break;
		case -100:
			txt = "Avalon East Region";
			break;
		case -200:
			txt = "Avalon West Region";
			break;
		case -300:
			txt = "Burin Region";
			break;
		case -400:
			txt = "Vista Region";
			break;
		default:
			txt = (String) school_names.get(new Integer(this.perm_school));

			break;
		}

		return txt;
	}

	public boolean isPermanent() {

		return ((this.perm_school != 0) && (this.perm_school != -1));
	}

	public String getPermanentContractPosition() {

		return this.perm_position;
	}

	public void setPermanentContractPosition(String perm_position) {

		this.perm_position = perm_position;
	}

	public int getContractSchool() {

		return this.contract_school;
	}

	public void setContractSchool(int contract_school) {

		this.contract_school = contract_school;
	}

	public String getReplacementContractLocationText() {

		String txt = null;

		switch (this.contract_school) {
		case -3000:
			txt = "Central Regional Office";
			break;
		case -2000:
			txt = "Western Regional Office";
			break;
		case -1000:
			txt = "Labrador Regional Office";
			break;
		case -999:
			txt = "District Office";
			break;
		case -998:
			txt = "Eastern Regional Office";
			break;
		case -100:
			txt = "Avalon East Region";
			break;
		case -200:
			txt = "Avalon West Region";
			break;
		case -300:
			txt = "Burin Region";
			break;
		case -400:
			txt = "Vista Region";
			break;
		default:
			txt = (String) school_names.get(new Integer(this.contract_school));

			break;
		}

		return txt;
	}

	public boolean isReplacement() {

		return ((this.contract_school != 0) && (this.contract_school != -1));
	}

	public Date getContractEndDate() {

		return this.contract_enddate;
	}

	public String getFormattedContractEndDate() {

		String str = null;

		if (this.contract_enddate != null)
			str = (new SimpleDateFormat(DATE_FORMAT)).format(this.contract_enddate);

		return str;
	}

	public void setContractEndDate(Date contract_enddate) {

		this.contract_enddate = contract_enddate;
	}

	public int getReplacementTime() {

		return this.repl_time;
	}

	public void setReplacementTime(int repl_time) {

		this.repl_time = repl_time;
	}

	public int getSubstituteTime() {

		return this.sub_time;
	}

	public void setSubstituteTime(int sub_time) {

		this.sub_time = sub_time;
	}

	public int getPermanentTime() {

		return this.perm_time;
	}

	public void setPermanentTime(int perm_time) {

		this.perm_time = perm_time;
	}

	public String generateXML() {

		StringBuffer sb = new StringBuffer();

		sb.append("<ESD-EXPERIENCE>");

		if ((this.perm_school != -1) && (this.perm_school != 0)) {
			sb.append("<PERM-SCHOOL>" + this.getPermanentContractLocationText() + "</PERM-SCHOOL>");
			sb.append("<PERM-POSITION>" + this.getPermanentContractPosition() + "</PERM-POSITION>");
		}
		else if ((this.contract_school != -1) && (this.contract_school != 0)) {
			sb.append("<REPLACEMENT-CONTRACT-SCHOOL>" + this.getReplacementContractLocationText()
					+ "</REPLACEMENT-CONTRACT-SCHOOL>");
			sb.append("<REPLACEMENT-CONTRACT-ENDDATE>" + this.getFormattedContractEndDate()
					+ "</REPLACEMENT-CONTRACT-ENDDATE>");
		}

		sb.append("<REPLACEMENT-TIME-TOTAL>" + this.getReplacementTime() + " MONTH(S)</REPLACEMENT-TIME-TOTAL>");
		sb.append("<SUBSTITUTE-TIME-TOTAL>" + this.getSubstituteTime() + " DAY(S)</SUBSTITUTE-TIME-TOTAL>");

		sb.append("</ESD-EXPERIENCE>");

		return sb.toString();
	}

	public int getPermanentLTime() {

		return this.perml_time;
	}

	public void setPermanentLTime(int perml_time) {

		this.perml_time = perml_time;
	}
}