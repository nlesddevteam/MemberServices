package com.awsd.personnel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import com.awsd.common.Utils;
import com.awsd.financial.FinancialException;
import com.awsd.financial.Report;
import com.awsd.financial.ReportDB;
import com.awsd.personnel.profile.Profile;
import com.awsd.personnel.profile.ProfileDB;
import com.awsd.personnel.profile.ProfileException;
import com.awsd.ppgp.PPGP;
import com.awsd.ppgp.PPGPDB;
import com.awsd.ppgp.PPGPException;
import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.awsd.school.SchoolException;
import com.awsd.security.PermissionDB;
import com.awsd.security.PermissionException;
import com.awsd.security.Role;
import com.awsd.security.RoleDB;
import com.awsd.security.RoleException;
import com.awsd.survey.SurveyException;
import com.awsd.survey.Surveys;
import com.awsd.travel.TravelClaimDB;
import com.awsd.travel.TravelClaimException;
import com.awsd.travel.TravelClaims;
import com.awsd.travel.bean.TravelBudget;
import com.awsd.travel.service.TravelBudgetService;
import com.esdnl.sds.SDSException;
import com.esdnl.sds.SDSInfo;
import com.esdnl.sds.SDSInfoDB;

public class Personnel implements Serializable {

	private static final long serialVersionUID = 2844110596239596090L;

	private int pID;
	private String lastLogin;
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private int supervisorID;
	private String email;
	private int categoryid;
	private int sid;
	private String view_on_next_logon = null;
	private String district_calendar_updated = null;
	private String schoolName;

	private static HashMap<Integer, PersonnelCategory> catsMap = null;

	static {
		try {
			loadCategoriesMap();
		}
		catch (Exception e) {
			System.err.println("Personnel (static block): " + e);
		}
	}

	// constructors

	public Personnel(int pid) throws PersonnelException {

		Personnel p = PersonnelDB.getPersonnel(pid);

		this.pID = pid;
		this.userName = p.userName;
		this.password = p.password;
		this.firstName = p.firstName;
		this.lastName = p.lastName;
		this.supervisorID = p.supervisorID;
		this.email = p.email;
		this.categoryid = p.categoryid;
		this.sid = p.sid;
	}

	public Personnel(String userName, String password, String firstName, String lastName, String email, int categoryID,
			int supervisorID) throws PersonnelException {

		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.supervisorID = supervisorID;
		this.email = email;
		this.categoryid = categoryID;

		pID = -1;
		sid = -1;
	}

	public Personnel(String userName, String password, String firstName, String lastName, String email, int categoryID,
			int supervisorID, int schoolID) throws PersonnelException {

		this(userName, password, firstName, lastName, email, categoryID, supervisorID);

		pID = -1;

		this.sid = schoolID;
	}

	public Personnel(int pID, String userName, String password, String firstName, String lastName, String email,
			int categoryID, int supervisorID) throws PersonnelException {

		this(userName, password, firstName, lastName, email, categoryID, supervisorID);

		this.pID = pID;
	}

	public Personnel(int pID, String userName, String password, String firstName, String lastName, String email,
			int categoryID, int supervisorID, int schoolID) throws PersonnelException {

		this(userName, password, firstName, lastName, email, categoryID, supervisorID, schoolID);

		this.pID = pID;
	}

	// accessor methods
	public int getPersonnelID() {

		return this.pID;
	}

	public void setPersonnelID(int pID) {

		this.pID = pID;
	}

	public String getUserName() {

		return this.userName;
	}

	public void setUserName(String userName) {

		this.userName = userName;
	}

	public String getPassword() {

		return this.password;
	}

	public void setPassword(String password) throws PersonnelException {

		if ((this.password == null) || this.password.equals("") || !this.password.equals(password)) {
			this.password = password;
			PersonnelDB.updatePersonnel(this);
		}
	}

	public void setFirstClassPassword(String password) throws PersonnelException {

		this.password = password;
	}

	public String getFirstName() {

		return this.firstName;
	}

	public void setFirstName(String firstName) {

		this.firstName = firstName;
	}

	public String getLastName() {

		return this.lastName;
	}

	public void setLastName(String lastName) {

		this.lastName = lastName;
	}

	public void setName(String firstName, String lastName) throws PersonnelException {

		this.firstName = firstName;
		this.lastName = lastName;
		PersonnelDB.updatePersonnel(this);
	}

	public String getEmailAddress() {

		return email;
	}

	public void setEmailAddress(String email) {

		this.email = email;
	}

	public Personnel getSupervisor() throws PersonnelException {

		Personnel s = null;

		if (supervisorID > 0) {
			s = PersonnelDB.getPersonnel(supervisorID);
		}

		return s;
	}

	public void setSupervisor(Personnel supervisor) throws PersonnelException {

		if (supervisor == null) {
			this.supervisorID = -1;
		}
		else {
			this.supervisorID = supervisor.getPersonnelID();
		}
		PersonnelDB.updatePersonnel(this);
	}

	public PersonnelCategory getPersonnelCategory() throws PersonnelException {

		PersonnelCategory pc = null;

		pc = (PersonnelCategory) catsMap.get(new Integer(categoryid));

		if (pc == null) {
			throw new PersonnelException("PersonnelCategory not found in DB");
		}

		return pc;
	}

	public void setPersonnelCategory(PersonnelCategory cat)
			throws PersonnelException,
				PersonnelCategoryException,
				PermissionException,
				RoleException {

		int oldcat = this.categoryid;

		// if(oldcat == cat.getPersonnelCategoryID())
		// return;

		Role r = RoleDB.getRole(cat.getPersonnelCategoryName());
		if (r == null) {
			RoleDB.addRole(new Role(cat.getPersonnelCategoryName(), cat.getPersonnelCategoryName()));
			r = RoleDB.getRole(cat.getPersonnelCategoryName());
			RoleDB.addRolePermission(r, PermissionDB.getPermission("TRAVEL-EXPENSE-VIEW"));
		}

		this.categoryid = cat.getPersonnelCategoryID();
		PersonnelDB.updatePersonnel(this);
		RoleDB.deleteRoleMembership(
				RoleDB.getRole(PersonnelCategoryDB.getPersonnelCategory(oldcat).getPersonnelCategoryName()), this);
		RoleDB.addRoleMembership(r, this);
	}

	public School getSchool() throws PersonnelException {

		School s = null;

		if (sid > 0) {
			try {
				s = SchoolDB.getSchool(sid);
			}
			catch (SchoolException e) {
				throw new PersonnelException("Could not find school");
			}
		}
		else {
			s = null;
		}

		return s;
	}

	public String getViewOnNextLogon() throws PersonnelException {

		if ((this.view_on_next_logon == null) || this.view_on_next_logon.equals(""))
			this.view_on_next_logon = PersonnelDB.getViewOnNextLogon(this);

		return this.view_on_next_logon;
	}

	public void setViewOnNextLogon(String app) throws PersonnelException {

		PersonnelDB.setViewOnNextLogon(this, app);

		this.view_on_next_logon = app;
	}

	public String getDistrictCalendarUpdated() throws PersonnelException {

		if ((this.district_calendar_updated == null) || this.district_calendar_updated.equals(""))
			this.district_calendar_updated = PersonnelDB.getDistrictCalendarUpdated(this);

		return this.district_calendar_updated;
	}

	public void setDistrictCalendarUpdated(String app) throws PersonnelException {

		PersonnelDB.setDistrictCalendarUpdated(this, app);

		this.district_calendar_updated = app;
	}

	public void setSchool(School s) throws PersonnelException, SchoolException {

		if (s != null) {
			this.sid = s.getSchoolID();
			this.setSupervisor(s.getSchoolPrincipal());
		}
		else {
			this.sid = -1;
			this.setSupervisor(null);
		}
	}

	public Vector<PPGP> getPPGPs() {

		Vector<PPGP> ppgps = null;

		try {
			ppgps = PPGPDB.getPPGP(this);
		}
		catch (PPGPException e) {
			ppgps = null;
		}

		return ppgps;
	}

	public PPGP getPPGP() {

		PPGP ppgp = null;

		try {
			ppgp = PPGPDB.getPPGP(this, PPGP.getCurrentGrowthPlanYear());
		}
		catch (PPGPException e) {
			ppgp = null;
		}

		return ppgp;
	}
	public PPGP getPreviousPPGP() {

		PPGP ppgp = null;

		try {
			ppgp = PPGPDB.getPPGP(this, PPGP.getPreviousGrowthPlanYear());
		}
		catch (PPGPException e) {
			ppgp = null;
		}

		return ppgp;
	}

	public Surveys getSurveys() throws PersonnelException, SurveyException {

		return new Surveys(this);
	}

	public boolean validateReportRequest(Report rpt) throws FinancialException {

		Map m = ReportDB.getPersonnelReportsMap(this);

		return ((m != null) && (m.containsKey(new Integer(rpt.getReportID()))));
	}

	// convenience methods
	public String getFullName() {

		return getLastName() + ", " + getFirstName();
	}

	public String getFullNameReverse() {

		return getFirstName().toLowerCase() + " " + getLastName().toLowerCase();
	}

	public String getDisplay() {

		return this.getFullName() + " [" + this.userName + "]";
	}

	public TravelClaims getTravelClaims() throws TravelClaimException {

		return new TravelClaims(this);
	}

	public TreeMap getTravelClaimsPendingApproval() throws TravelClaimException {

		return TravelClaimDB.getClaimsPendingApprovalTreeMap(this);
	}

	public TreeMap getTravelClaimsApproved() throws TravelClaimException {

		return TravelClaimDB.getClaimsApprovedTreeMap();
	}

	public LinkedHashMap getTravelClaimsPaymentPending() throws TravelClaimException {

		return TravelClaimDB.getClaimsPaymentPendingTreeMap();
	}

	public TreeMap getTravelClaimsPaidToday() throws TravelClaimException {

		return TravelClaimDB.getClaimsPaidTodayTreeMap();
	}
	
	public LinkedHashMap getTravelClaimsPreSubmission() throws TravelClaimException {

		return TravelClaimDB.getClaimsPreSubmissionTreeMap();
	}
	public LinkedHashMap getTravelClaimsRejected() throws TravelClaimException {

		return TravelClaimDB.getClaimsRejectedTreeMap();
	}
	public int getYearToDateKilometerUsage() throws TravelClaimException {

		return TravelClaimDB.getYearToDateKMSTotals(this);
	}

	public double getYearToDateClaimTotal() throws TravelClaimException {

		return TravelClaimDB.getYearToDateTotalClaimed(this);
	}

	public TravelBudget getCurrrentTravelBudget() throws TravelClaimException {

		return TravelBudgetService.getTravelBudget(this, Utils.getCurrentSchoolYear());
	}

	public SDSInfo getSDSInfo() throws SDSException {

		return SDSInfoDB.getSDSInfo(this);
	}

	public Profile getProfile() throws ProfileException {

		return ProfileDB.getProfile(this);
	}

	public String toString() {

		return this.getFullNameReverse();
	}
	
	public double getYearToDateKilometerUsageFiscalYear() throws TravelClaimException {

		return TravelClaimDB.getYearToDateTotalKMSFY(this,Utils.getCurrentSchoolYear());
	}
	public double getCurrentYearClaimTotal() throws TravelClaimException {

		return TravelClaimDB.getCurrentYearTotalClaimed(this);
	}
	public String toXML() {

		StringBuffer xml = new StringBuffer();
		xml.append("<PERSONNEL>");

		xml.append("<ID>" + this.pID + "</ID>");
		xml.append("<DISPLAY>" + this.getFullName() + " [" + this.userName + "]</DISPLAY>");
		xml.append("<USERNAME>" + this.userName + "</USERNAME>");
		xml.append("<FIRSTNAME>" + this.firstName + "</FIRSTNAME>");
		xml.append("<LASTNAME>" + this.lastName + "</LASTNAME>");
		xml.append("<EMAIL>" + this.email + "</EMAIL>");
		try {
			xml.append("<CATEGORY>" + this.getPersonnelCategory().getPersonnelCategoryName() + "</CATEGORY>");
		}
		catch (PersonnelException e) {
			xml.append("<CATEGORY>UNAVAILABLE</CATEGORY>");
		}

		xml.append("</PERSONNEL>");

		return xml.toString();
	}

	public boolean equals(Object o) {

		boolean check = false;

		if (o != null) {
			if (o instanceof Personnel) {
				if (this.pID == ((Personnel) o).getPersonnelID())
					check = true;
			}
		}

		return check;
	}

	/**
	 * PRIVATE UTILITY METHODS
	 */
	private static void loadCategoriesMap() throws PersonnelCategoryException {

		Vector<PersonnelCategory> cats = null;
		PersonnelCategory t = null;
		Iterator<PersonnelCategory> iter = null;

		catsMap = new HashMap<Integer, PersonnelCategory>(7);
		cats = PersonnelCategoryDB.getPersonnelCategories();
		iter = cats.iterator();

		while (iter.hasNext()) {
			t = (PersonnelCategory) iter.next();
			catsMap.put(new Integer(t.getPersonnelCategoryID()), t);
		}
	}

	public String getLastLogin() {
		return lastLogin;
	}
	
	public void  setLastLogin(String lastLogin) {		
		this.lastLogin = lastLogin;
	}
	
	public String getSchoolName() {
		
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
}