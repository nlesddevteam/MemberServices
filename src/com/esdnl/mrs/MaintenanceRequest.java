package com.esdnl.mrs;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.PersonnelException;
import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.awsd.school.SchoolException;
import com.esdnl.servlet.FormElementFormat;

public class MaintenanceRequest implements Serializable {

	private static final long serialVersionUID = 1082039171493164418L;

	public static final int EMAIL_WORKORDER = 1;
	public static final int EMAIL_SCHOOLADMIN = 2;

	private int request_id;
	private RequestType r_type;

	private StatusCode cur_status;
	private String problem_desc;
	private Date requested_date;
	private String room_name_number;
	private int school_priority;
	private int school_id;
	private int requested_by_id;

	private RequestCategory r_cat;
	private Date date_reviewed;
	private int reviewed_by_id;
	private Date date_assigned;
	private Date date_completed;
	private Date date_cancelled;
	private CapitalType cap_type;
	private int cap_priority;
	private boolean cap_fund_approved;
	private double est_cost;
	private double actual_cost;

	private Personnel requested_by = null;
	private Personnel reviewed_by = null;
	private School school = null;

	public MaintenanceRequest(int request_id, RequestType r_type, StatusCode cur_status, String problem_desc,
			Date requested_date, String room_name_number, int requested_by_id, int school_id, int school_priority,
			RequestCategory r_cat, CapitalType cap_type, int cap_priority, boolean cap_fund_approved, Date date_reviewed,
			int reviewed_by_id, Date date_assigned, Date date_completed, Date date_cancelled, double est_cost,
			double actual_cost) {

		this.request_id = request_id;
		this.r_type = r_type;
		this.cur_status = cur_status;
		this.problem_desc = problem_desc;
		this.requested_date = requested_date;
		this.room_name_number = room_name_number;
		this.room_name_number = room_name_number;
		this.requested_by_id = requested_by_id;
		this.school_id = school_id;
		this.school_priority = school_priority;

		this.r_cat = r_cat;
		this.date_reviewed = date_reviewed;
		this.reviewed_by_id = reviewed_by_id;
		this.cap_type = cap_type;
		this.cap_priority = cap_priority;
		this.cap_fund_approved = cap_fund_approved;
		this.date_assigned = date_assigned;
		this.date_completed = date_completed;
		this.date_cancelled = date_cancelled;
		this.est_cost = est_cost;
		this.actual_cost = actual_cost;
	}

	public MaintenanceRequest(RequestType r_type, StatusCode cur_status, String problem_desc, Date requested_date,
			String room_name_number, int requested_by_id, int school_id, int school_priority) {

		this(-1, r_type, cur_status, problem_desc, requested_date, room_name_number, requested_by_id, school_id, school_priority, null, null, -1, false, null, -1, null, null, null, 0.0, 0.0);
	}

	public MaintenanceRequest(RequestType r_type, StatusCode cur_status, String problem_desc, Date requested_date,
			String room_name_number, Personnel requested_by, School school, int school_priority) {

		this(-1, r_type, cur_status, problem_desc, requested_date, room_name_number, requested_by.getPersonnelID(), school.getSchoolID(), school_priority, null, null, -1, false, null, -1, null, null, null, 0.0, 0.0);

		this.requested_by = requested_by;
		this.school = school;
	}

	public int getRequestID() {

		return this.request_id;
	}

	public void setRequestId(int request_id) {

		this.request_id = request_id;
	}

	public String getFormattedRequestID() {

		return new DecimalFormat("JOB-#000000").format(this.request_id);
	}

	public RequestType getRequestType() {

		return this.r_type;
	}

	public StatusCode getCurentStatus() {

		return this.cur_status;
	}

	public void setCurrentStatus(StatusCode cur_status) {

		this.cur_status = cur_status;
	}

	public String geProblemDescription() {

		return this.problem_desc;
	}

	public Date getRequestedDate() {

		return this.requested_date;
	}

	public String getFormattedRequestedDate() {

		return new SimpleDateFormat(FormElementFormat.DATE_FORMAT).format(this.requested_date);
	}

	public String getRoomNameNumber() {

		return this.room_name_number;
	}

	public int getSchoolID() {

		return this.school_id;
	}

	public School getSchool() throws SchoolException {

		if (this.school == null)
			this.school = SchoolDB.getSchool(this.school_id);

		return this.school;
	}

	public Personnel getRequestedBy() throws PersonnelException {

		if (this.requested_by == null)
			this.requested_by = PersonnelDB.getPersonnel(this.requested_by_id);

		return this.requested_by;
	}

	public int getSchoolPriority() {

		return this.school_priority;
	}

	public RequestCategory getRequestCategory() {

		return this.r_cat;
	}

	public void setRequestCategory(RequestCategory r_cat) {

		this.r_cat = r_cat;
	}

	public Date getDateReviewed() {

		return this.date_reviewed;
	}

	public Date getDateCompleted() {

		return this.date_completed;
	}

	public Personnel getReviewedBy() throws PersonnelException {

		if (this.reviewed_by == null)
			reviewed_by = PersonnelDB.getPersonnel(this.reviewed_by_id);

		return reviewed_by;
	}

	public Date getDateAssigned() {

		return this.date_assigned;
	}

	public String getFormattedAssignedDate() {

		return new SimpleDateFormat(FormElementFormat.DATE_FORMAT).format(this.date_assigned);
	}

	public Date getDateCancelled() {

		return this.date_cancelled;
	}

	public CapitalType getCapitalType() {

		return this.cap_type;
	}

	public void setCatiptalType(CapitalType cap_type) {

		this.cap_type = cap_type;
	}

	public int getCapitalPriority() {

		return this.cap_priority;
	}

	public void setCapitalPriority(int cap_priority) {

		this.cap_priority = cap_priority;
	}

	public boolean isCapitalFundingApproved() {

		return this.cap_fund_approved;
	}

	public void setCapitalFundingApproved(boolean cap_fund_approved) {

		this.cap_fund_approved = cap_fund_approved;
	}

	public double getEstimatedCost() {

		return this.est_cost;
	}

	public void setEstimatedCost(double est_cost) {

		this.est_cost = est_cost;
	}

	public double getActualCost() {

		return this.actual_cost;
	}

	public void setActualCost(double actual_cost) {

		this.actual_cost = actual_cost;
	}

	public HashMap<Integer, RequestAssignment> getAssignedMaintenancePersonnel()
			throws RequestException,
				PersonnelException {

		return PersonnelDB.getMaintenanceRequestAssignedPersonnel(this.request_id);
	}

	public HashMap<Integer, RequestAssignment> getAssignedVendors() throws RequestException {

		return VendorDB.getRequestAssignedVendors(this.request_id);
	}

	public RequestComment[] getRequestComments() throws RequestException {

		return (RequestComment[]) MaintenanceRequestDB.getMaintenanceRequestComments(this.request_id).toArray(
				new RequestComment[0]);
	}

	public String toString() {

		return this.geProblemDescription();
	}

	@SuppressWarnings("unchecked")
	public String toEmail(int who) {

		StringBuffer buf = new StringBuffer();

		buf.append("<h2>" + this.getFormattedRequestID() + "</h2>");

		try {
			buf.append("<B><U>School/Location:</U></B> " + this.getSchool().getSchoolName() + "<BR>");
		}
		catch (SchoolException e) {
			e.printStackTrace();
		}

		buf.append("<B><U>Room:</U></B> " + this.getRoomNameNumber() + "<BR>");
		buf.append("<B><U>Description:</U></B><BR>");
		buf.append(this.geProblemDescription() + "<BR>");
		buf.append("<B><U>Request Type:</U></B> " + this.getRequestType() + "<BR>");
		buf.append("<B><U>Requested Date:</U></B> " + this.getFormattedRequestedDate() + "<BR>");
		if (this.getDateAssigned() != null)
			buf.append("<B><U>Assigned Date:</U></B> " + this.getFormattedAssignedDate() + "<BR>");

		if (who == MaintenanceRequest.EMAIL_SCHOOLADMIN) {
			buf.append("<B><U>Assigned to:</U></B><BR>");
			try {
				Map.Entry<Integer, RequestAssignment>[] ass_per = (Map.Entry<Integer, RequestAssignment>[]) this.getAssignedMaintenancePersonnel().entrySet().toArray(
						new Map.Entry[0]);
				for (int i = 0; i < ass_per.length; i++)
					buf.append(((RequestAssignment) ass_per[i].getValue()).getMaintenancePersonnel().getFullNameReverse()
							+ "<br>");
			}
			catch (RequestException e) {}
			catch (PersonnelException e) {}

			try {
				Map.Entry<Integer, RequestAssignment>[] ass_per = (Map.Entry<Integer, RequestAssignment>[]) this.getAssignedVendors().entrySet().toArray(
						new Map.Entry[0]);
				for (int i = 0; i < ass_per.length; i++)
					buf.append(((RequestAssignment) ass_per[i].getValue()).getVendor().getVendorName() + "<br>");
			}
			catch (RequestException e) {}
		}

		try {
			RequestComment[] comments = this.getRequestComments();
			buf.append("<B><U>Comments:</U></B><BR>");
			if (comments.length < 1)
				buf.append("No comments attached to this request.<BR>");
			else {
				SimpleDateFormat sdf = new SimpleDateFormat(FormElementFormat.DATE_FORMAT);
				for (int i = 0; i < comments.length; i++) {
					try {
						buf.append("Entered On " + sdf.format(comments[i].getMadeOn()) + " by "
								+ comments[i].getMadeBy().getFullNameReverse() + "<BR>");
						buf.append(comments[i].getComment() + "<BR><BR>");
					}
					catch (PersonnelException ee) {
						ee.printStackTrace();
					}
				}
			}

		}
		catch (RequestException e) {
			e.printStackTrace();
		}

		return buf.toString();
	}
}