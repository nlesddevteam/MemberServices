package com.awsd.travel;

import java.util.Date;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.PersonnelException;

public class TravelClaim implements Claim {

	private int claim_id;
	private int personnel_id;
	private TravelClaimStatus cur_status;
	private Date created_date;
	private Date submit_date;
	private String fiscal_year;
	private int fiscal_month;
	private int supervisor_id;
	private Personnel supervisor = null;
	private String sds_gl_acc;
	private boolean sds_paid_tchr_payroll;
	private Date approved_date;
	private Date paid_date;
	private Date export_date;

	public TravelClaim() {

		this(-1, -1, -1, null, null, "UNKNOWN", -1, -1, "", false, null, null, null);
	}

	public TravelClaim(Personnel p) {

		this(-1, p.getPersonnelID(), -1, null, null, "UNKNOWN", -1, -1, "", false, null, null, null);
	}

	public TravelClaim(int claim_id, int personnel_id, int cur_status, Date created_date, Date submit_Date,
			String fiscal_year, int fiscal_month, int supervisor_id, String sds_gl_acc, boolean sds_paid_tchr_payroll,
			Date approved_date, Date paid_date, Date exported_date) {

		this.claim_id = claim_id;
		this.personnel_id = personnel_id;
		this.cur_status = TravelClaimStatus.getStatus(cur_status);
		this.created_date = created_date;
		this.submit_date = submit_Date;
		this.fiscal_year = fiscal_year;
		this.fiscal_month = fiscal_month;
		this.supervisor_id = supervisor_id;
		this.sds_gl_acc = sds_gl_acc;
		this.sds_paid_tchr_payroll = sds_paid_tchr_payroll;
		this.approved_date = approved_date;
		this.paid_date = paid_date;
		this.export_date = exported_date;
	}

	public int getClaimID() {

		return this.claim_id;
	}

	public Personnel getPersonnel() throws PersonnelException {

		if (this.personnel_id > 0)
			return PersonnelDB.getPersonnel(this.personnel_id);
		else
			return null;
	}

	public Date getCreatedDate() {

		return this.created_date;
	}

	public Date getSubmitDate() {

		return this.submit_date;
	}

	public TravelClaimStatus getCurrentStatus() {

		return this.cur_status;
	}

	public String getFiscalYear() {

		return this.fiscal_year;
	}

	public int getFiscalMonth() {

		return this.fiscal_month;
	}

	public boolean setCurrentStatus(Personnel who, TravelClaimStatus cur_status) {

		boolean check = false;
		try {
			check = TravelClaimDB.setCurrentStatus(this, who, cur_status);
			if (check)
				this.cur_status = cur_status;
		}
		catch (TravelClaimException e) {
			System.err.println("<<<<<< COUNT NOT SET TRAVEL CLAIM STATUS >>>>>>");
			check = false;
		}

		return check;
	}

	public TravelClaimItems getItems() throws TravelClaimException {

		return new TravelClaimItems(this);
	}

	public TravelClaimSummary getSummaryTotals() throws TravelClaimException {

		return TravelClaimDB.getClaimSummaryTotals(this);
	}

	public TravelClaimRateSummaries getRateSummaryTotals() throws TravelClaimException {

		return new TravelClaimRateSummaries(this);
	}

	public Personnel getSupervisor() {

		if (this.supervisor_id < 1)
			supervisor = null;
		else if (this.supervisor == null) {
			try {
				supervisor = PersonnelDB.getPersonnel(this.supervisor_id);
			}
			catch (PersonnelException e) {
				supervisor = null;
			}
		}

		return supervisor;
	}

	public String getSDSGLAccountCode() {

		return this.sds_gl_acc;
	}

	public void setSDSGLAccountCode(String sds_gl_acc) {

		this.sds_gl_acc = sds_gl_acc;
	}

	public Date getApprovedDate() {

		return this.approved_date;
	}

	public void setApprovedDate(Date approved_date) {

		this.approved_date = approved_date;
	}

	public Date getPaidDate() {

		return this.paid_date;
	}

	public void setPaidDate(Date paid_date) {

		this.paid_date = paid_date;
	}

	public Date getExportDate() {

		return this.export_date;
	}

	public void setExportDate(Date export_date) {

		this.export_date = export_date;
	}

	public boolean isPaidThroughTeacherPayroll() {

		return this.sds_paid_tchr_payroll;
	}

	public void setPaidThroughTeacherPayroll(boolean sds_paid_tchr_payroll) {

		this.sds_paid_tchr_payroll = sds_paid_tchr_payroll;
	}

	public HistoryItems getHistory() throws TravelClaimException {

		return new HistoryItems(this);
	}

	public TravelClaimNotes getNotes() throws TravelClaimException {

		return new TravelClaimNotes(this);
	}
}