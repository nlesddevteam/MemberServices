package com.awsd.school;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.PersonnelException;
import com.awsd.school.bean.RegionBean;
import com.awsd.school.bean.RegionException;
import com.awsd.school.bean.SchoolStatsBean;
import com.awsd.school.dao.RegionManager;
import com.awsd.school.dao.SchoolStatsManager;
import com.awsd.strike.DailySchoolStrikeInfo;
import com.awsd.strike.DailySchoolStrikeInfoDB;
import com.awsd.strike.DailySchoolStrikeInfoHistory;
import com.awsd.strike.SchoolStrikeGroup;
import com.awsd.strike.SchoolStrikeGroupDB;
import com.awsd.strike.StrikeException;
import com.awsd.weather.ClosureStatus;
import com.awsd.weather.ClosureStatusDB;
import com.awsd.weather.ClosureStatusException;
import com.awsd.weather.SchoolSystem;
import com.awsd.weather.SchoolSystemDB;
import com.awsd.weather.SchoolSystemException;
import com.esdnl.util.StringUtils;
import com.nlesd.school.bean.SchoolDirectoryDetailsBean;
import com.nlesd.school.bean.SchoolDirectoryDetailsOtherBean;
import com.nlesd.school.bean.SchoolStreamDetailsBean;
import com.nlesd.school.bean.SchoolZoneBean;
import com.nlesd.school.service.SchoolDirectoryDetailsOtherService;
import com.nlesd.school.service.SchoolDirectoryDetailsService;
import com.nlesd.school.service.SchoolStreamDetailsService;
import com.nlesd.school.service.SchoolZoneService;

public class School implements Comparable<School>, Cloneable, Serializable {

	private static final long serialVersionUID = 7907969950535716504L;

	public enum GRADE {
		UNKNOWN(0, "Unknown", "NA"), KINDERGARDEN(1, "Kindergarten", "K"), GRADE1(2, "Grade 1", "1"), GRADE2(3, "Grade 2",
				"2"), GRADE3(4, "Grade 3", "3"), GRADE4(5, "Grade 4", "4"), GRADE5(6, "Grade 5", "5"), GRADE6(7, "Grade 6", "6"), GRADE7(
				8, "Grade 7", "7"), GRADE8(9, "Grade 8", "8"), GRADE9(10, "Grade 9", "9"), LEVEL1(11, "Level I", "L1"), LEVEL2(
				12, "Level II", "L2"), LEVEL3(13, "Level III", "L3"), LEVEL4(14, "Level IV", "L4");

		private int value;
		private String name;
		private String abbrev;

		GRADE(int value, String name, String abbrev) {

			this.value = value;
			this.name = name;
			this.abbrev = abbrev;
		}

		public int getValue() {

			return this.value;
		}

		public String getName() {

			return this.name;
		}

		public String getAbbrev() {

			return this.abbrev;
		}

		public static GRADE get(int value) {

			for (GRADE g : GRADE.values()) {
				if (g.getValue() == value)
					return g;
			}

			return null;
		}
	}

	public enum GRADE_CATEGORY {
		PRIMARY(1, "Primary (K-3)"), ELEMENTARY(2, "Elementary (4-6)"), INTERMEDIATE(3, "Intermediate (7-9)"), SENIORHIGH(
				4, "Senior High (10-12)");

		private int value;
		private String name;

		GRADE_CATEGORY(int value, String name) {

			this.value = value;
			this.name = name;
		}

		public int getValue() {

			return this.value;
		}

		public String getName() {

			return this.name;
		}

		public static GRADE_CATEGORY get(GRADE value) {

			GRADE_CATEGORY cat = null;

			switch (value) {
			case KINDERGARDEN:
			case GRADE1:
			case GRADE2:
			case GRADE3:
				cat = PRIMARY;
				break;
			case GRADE4:
			case GRADE5:
			case GRADE6:
				cat = ELEMENTARY;
				break;
			case GRADE7:
			case GRADE8:
			case GRADE9:
				cat = INTERMEDIATE;
				break;
			case LEVEL1:
			case LEVEL2:
			case LEVEL3:
			case LEVEL4:
				cat = SENIORHIGH;
				break;
			default:
				cat = null;
			}

			return cat;
		}
	}

	private int school_id;
	private String school_name;
	private Personnel principal;

	private HashMap<Integer, Personnel> assistant_principals;

	private int deptid;

	private ClosureStatus closure_status = null;

	private SchoolStatsBean stats = null;

	private RegionBean region = null;

	private SchoolZoneBean zone = null;

	private SchoolDirectoryDetailsBean details = null;
	private SchoolDirectoryDetailsOtherBean detailsOther = null;
	private SchoolStreamDetailsBean schoolStreams=null;
	// address
	private String address1;
	private String address2;
	private String townCity;
	private String provinceState;
	private String postalZipCode;

	private String telephone;
	private String fax;

	private GRADE lowestGrade;
	private GRADE highestGrade;

	private String website;

	public School() {

		this.school_id = -1;
		this.school_name = null;
		this.principal = null;
		this.assistant_principals = null;
		this.deptid = -1;
		this.address1 = null;
		this.address2 = null;
		this.townCity = null;
		this.provinceState = null;
		this.postalZipCode = null;
		this.telephone = null;
		this.fax = null;
		this.lowestGrade = School.GRADE.UNKNOWN;
		this.highestGrade = School.GRADE.UNKNOWN;
		this.website = null;

		this.region = null;
		this.zone = null;
		this.details = null;
		this.detailsOther=null;
		this.schoolStreams=null;
	}

	public int getSchoolID() {

		return school_id;
	}

	public void setSchoolID(int school_id) {

		this.school_id = school_id;
	}

	public String getSchoolName() {

		return school_name;
	}

	public void setSchoolName(String school_name) {

		this.school_name = school_name;
	}

	public Personnel getSchoolPrincipal() {

		return principal;
	}

	public void setSchoolPrincipal(Personnel p) {

		this.principal = p;
	}

	public Personnel[] getAssistantPrincipals() throws PersonnelException {

		return (Personnel[]) getAssistantPrincipalsMap().values().toArray(new Personnel[0]);
	}

	public HashMap<Integer, Personnel> getAssistantPrincipalsMap() throws PersonnelException {

		if (this.assistant_principals == null)
			this.assistant_principals = PersonnelDB.getSchoolAssistantPrincipals(this);

		return this.assistant_principals;
	}

	public void setAssistantPrincipals(HashMap<Integer, Personnel> assistant_principals) {

		this.assistant_principals = assistant_principals;
	}

	public void setAssistantPrincipals(String[] ap_id) throws PersonnelException {

		for (int i = 0; i < ap_id.length; i++) {
			if (ap_id[i].equals("-1")) {
				clearAssistantPrincipals();
				break;
			}

			addAssistantPrincipal(Integer.parseInt(ap_id[i]));
		}
	}

	public void clearAssistantPrincipals() {

		if (this.assistant_principals == null)
			this.assistant_principals = new HashMap<Integer, Personnel>();
		else
			this.assistant_principals.clear();
	}

	public void addAssistantPrincipal(Personnel p) throws PersonnelException {

		if (this.assistant_principals == null)
			assistant_principals = new HashMap<Integer, Personnel>();

		this.assistant_principals.put(new Integer(p.getPersonnelID()), p);
	}

	public void addAssistantPrincipal(int pid) throws PersonnelException {

		Personnel p = PersonnelDB.getPersonnel(pid);

		if (this.assistant_principals == null)
			assistant_principals = new HashMap<Integer, Personnel>();

		this.assistant_principals.put(new Integer(p.getPersonnelID()), p);
	}

	public int getSchoolDeptID() {

		return this.deptid;
	}

	public void setSchoolDeptID(int deptid) {

		this.deptid = deptid;
	}

	public String getAddress1() {

		return address1;
	}

	public void setAddress1(String address1) {

		this.address1 = address1;
	}

	public String getAddress2() {

		return address2;
	}

	public void setAddress2(String address2) {

		this.address2 = address2;
	}

	public String getTownCity() {

		return townCity;
	}

	public void setTownCity(String townCity) {

		this.townCity = townCity;
	}

	public String getProvinceState() {

		return provinceState;
	}

	public void setProvinceState(String provinceState) {

		this.provinceState = provinceState;
	}

	public String getPostalZipCode() {

		return postalZipCode;
	}

	public void setPostalZipCode(String postalZipCode) {

		this.postalZipCode = postalZipCode;
	}

	public String getTelephone() {

		return telephone;
	}

	public void setTelephone(String telephone) {

		this.telephone = telephone;
	}

	public String getFax() {

		return fax;
	}

	public void setFax(String fax) {

		this.fax = fax;
	}

	public GRADE getLowestGrade() {

		return lowestGrade;
	}

	public void setLowestGrade(GRADE lowestGrade) {

		this.lowestGrade = lowestGrade;
	}

	public GRADE getHighestGrade() {

		return highestGrade;
	}

	public void setHighestGrade(GRADE highestGrade) {

		this.highestGrade = highestGrade;
	}

	public String getWebsite() {

		return website;
	}

	public void setWebsite(String website) {

		this.website = website;
	}

	public void save() throws SchoolException {

		if (this.school_id > 0)
			SchoolDB.updateSchool(this);
		else if (school_id == -1)
			SchoolDB.addSchool(this);
	}

	public ClosureStatus getSchoolClosureStatus() throws ClosureStatusException {

		if (closure_status == null)
			closure_status = ClosureStatusDB.getClosureStatus(this);

		return closure_status;
	}

	public void setSchoolClosureStatus(ClosureStatus closure_status) {

		this.closure_status = closure_status;
	}

	public SchoolZoneBean getZone() throws SchoolException {

		if (this.zone == null) {
			this.zone = SchoolZoneService.getSchoolZoneBean(this);
		}

		return this.zone;
	}

	public void setZone(SchoolZoneBean zone) {

		this.zone = zone;
	}

	public RegionBean getRegion() throws RegionException {

		if (region == null)
			this.region = RegionManager.getRegionBean(this);

		return region;
	}

	public void setRegion(RegionBean region) {

		this.region = region;
	}

	public SchoolDirectoryDetailsBean getDetails() throws SchoolException {

		if (this.details == null) {
			this.details = SchoolDirectoryDetailsService.getSchoolDirectoryDetailsBean(this);
		}

		return details;
	}

	public void setDetailsOther(SchoolDirectoryDetailsOtherBean details) {

		this.detailsOther = details;
	}
	public SchoolDirectoryDetailsOtherBean getDetailsOther() throws SchoolException {

		if (this.detailsOther == null) {
			if(!(this.details == null)){
			this.detailsOther = SchoolDirectoryDetailsOtherService.getSchoolDirectoryDetailsOtherBean(this.details.getDirectoryId());
			}
		}

		return detailsOther;
	}
	public void setSchoolStreams(SchoolStreamDetailsBean streams) {
		
		this.schoolStreams = streams;
	}
	public SchoolStreamDetailsBean getSchoolStreams() throws SchoolException {

		if (this.schoolStreams == null) {
			if(!(this.details == null)){
			this.schoolStreams = SchoolStreamDetailsService.getSchoolStreamDetailsBean(this.details.getDirectoryId());
			}
		}

		return schoolStreams;
	}
	public void setDetails(SchoolDirectoryDetailsBean details) {

		this.details = details;
	}
	public SchoolSystem getSchoolSystem() throws SchoolSystemException {

		return SchoolSystemDB.getSchoolSystem(this);
	}

	public SchoolStrikeGroup getSchoolStrikeGroup() throws StrikeException {

		return SchoolStrikeGroupDB.getSchoolStrikeGroup(this);
	}

	public DailySchoolStrikeInfo getDailySchoolStrikeInfo() throws StrikeException {

		return DailySchoolStrikeInfoDB.getDailySchoolStrikeInfo(this);
	}

	public DailySchoolStrikeInfoHistory getDailySchoolStrikeInfoHistory() throws StrikeException {

		return new DailySchoolStrikeInfoHistory(this);
	}

	public SchoolFamily getSchoolFamily() throws SchoolFamilyException {

		return SchoolFamilyDB.getSchoolFamily(this);
	}

	public SchoolStatsBean getSchoolStats() throws SchoolException {

		if (this.stats == null)
			this.stats = SchoolStatsManager.getSchoolStatsBean(this);

		return this.stats;
	}

	public boolean equals(Object obj) {

		School tmp = null;
		boolean check = false;

		if ((obj != null) && (obj instanceof School)) {
			tmp = (School) obj;
			if (tmp.getSchoolID() == this.getSchoolID())
				check = true;
		}

		return check;
	}

	public String toXML() {

		StringBuffer xml = new StringBuffer();

		xml.append("<SCHOOL>");
		xml.append("<ID>" + this.getSchoolID() + "</ID>");
		xml.append("<NAME>" + this.getSchoolName() + "</NAME>");
		xml.append("<DEPT-ID>" + this.getSchoolDeptID() + "</DEPT-ID>");
		if (this.getSchoolPrincipal() != null)
			xml.append("<PRINCIPAL>" + this.getSchoolPrincipal().toXML() + "</PRINCIPAL>");
		try {
			Personnel[] ap = this.getAssistantPrincipals();
			if (ap.length > 0) {
				xml.append("<ASSISTANT-PRINCIPALS>");
				for (int i = 0; i < ap.length; i++)
					xml.append("<ASSISTANT-PRINCIPAL>" + ap[i].toXML() + "</ASSISTANT-PRINCIPAL>");
				xml.append("</ASSISTANT-PRINCIPALS>");
			}
		}
		catch (PersonnelException e) {
			e.printStackTrace();
		}

		try {
			if (this.getRegion() != null)
				xml.append(this.getRegion().toXML());
		}
		catch (RegionException e) {
			e.printStackTrace();
		}

		try {
			if (this.getZone() != null)
				xml.append(this.getZone().toXML());
		}
		catch (SchoolException e) {
			e.printStackTrace();
		}

		xml.append("<ADDRESSES>");
		xml.append("<ADDRESS>");
		xml.append("<ADDRESS1>" + this.getAddress1() + "</ADDRESS1>");
		if (!StringUtils.isEmpty(this.getAddress2()))
			xml.append("<ADDRESS2>" + this.getAddress2() + "</ADDRESS2>");
		xml.append("<TOWN-CITY>" + this.getTownCity() + "</TOWN-CITY>");
		xml.append("<PROVINCE-STATE>" + this.getProvinceState() + "</PROVINCE-STATE>");
		xml.append("<POSTAL-ZIPCODE>" + this.getPostalZipCode() + "</POSTAL-ZIPCODE>");
		xml.append("</ADDRESS>");
		xml.append("</ADDRESSES>");

		xml.append("<TELEPHONES>");
		xml.append("<TELEPHONE number='" + this.getTelephone() + "' />");
		xml.append("</TELEPHONES>");

		xml.append("<FAXES>");
		xml.append("<FAX number='" + this.getFax() + "' />");
		xml.append("</FAXES>");

		xml.append("<WEBSITES>");
		xml.append("<WEBSITE url='" + this.getWebsite() + "' />");
		xml.append("</WEBSITES>");

		xml.append("<GRADES>");
		xml.append("<LOWER-GRADE id='" + this.getLowestGrade().getValue() + "' name='" + this.getLowestGrade().getName()
				+ "' />");
		xml.append("<UPPER-GRADE id='" + this.getHighestGrade().getValue() + "' name='" + this.getHighestGrade().getName()
				+ "' />");
		xml.append("</GRADES>");
		xml.append("</SCHOOL>");

		return xml.toString();
	}

	public static String getPreviousSchoolYear() {

		return School.getSchoolYear(-1);
	}

	public static String getCurrentSchoolYear() {

		return School.getSchoolYear(0);
	}

	public static String getNextSchoolYear() {

		return School.getSchoolYear(1);
	}

	public static String getSchoolYear(int offset) {

		String school_year;

		Calendar cur = Calendar.getInstance();

		cur.add(Calendar.YEAR, offset);

		if (cur.get(Calendar.MONTH) > Calendar.JUNE) {
			// beginning of school year
			school_year = cur.get(Calendar.YEAR) + "-" + (cur.get(Calendar.YEAR) + 1);
		}
		else if (cur.get(Calendar.MONTH) <= Calendar.JUNE) {
			// end of school year
			school_year = (cur.get(Calendar.YEAR) - 1) + "-" + (cur.get(Calendar.YEAR));
		}
		else {
			school_year = "UNKNOWN";
		}

		return school_year;
	}

	public int compareTo(School o) {

		return (new Integer(this.school_id)).compareTo(new Integer(o.getSchoolID()));
	}

	public School clone() {

		School tmp = new School();

		tmp.school_id = this.getSchoolID();
		tmp.school_name = this.getSchoolName();
		tmp.principal = null;
		tmp.assistant_principals = null;
		tmp.deptid = this.getSchoolDeptID();
		tmp.address1 = this.getAddress1();
		tmp.address2 = this.getAddress2();
		tmp.townCity = this.getTownCity();
		tmp.provinceState = this.getProvinceState();
		tmp.postalZipCode = this.getPostalZipCode();
		tmp.telephone = this.getTelephone();
		tmp.fax = this.getFax();
		tmp.lowestGrade = this.getLowestGrade();
		tmp.highestGrade = this.getHighestGrade();
		tmp.website = this.getWebsite();

		return tmp;
	}

}
