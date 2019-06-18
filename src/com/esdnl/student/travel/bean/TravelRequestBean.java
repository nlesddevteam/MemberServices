package com.esdnl.student.travel.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.esdnl.student.travel.constant.RequestStatus;
import com.esdnl.util.StringUtils;

public class TravelRequestBean {

	private int school_id;
	private Date departureDate;
	private String destination;
	private String rational;
	private String grades;
	private int numStudents;
	private int totalChaperons;
	private int totalTeacherChaperons;
	private String teacherChaperon;
	private int totalOtherChaperons;
	private String otherChaperon;
	private boolean chaperonsApproved;
	private String iteneraryFilename;
	private String emergencyContact;
	private RequestStatus status;
	private int requestId;
	private Date returnDate;
	private int requestedBy;
	private Date requestedDate;
	private int actionedBy;
	private Date actionDate;
	private double daysMissed;
	private boolean billetingInvolved;
	private boolean schoolFundraising;

	public static String DATE_FORMAT = "dd/MM/yyyy";

	public int getSchoolId() {

		return school_id;
	}

	public void setSchoolId(int newSchool_id) {

		school_id = newSchool_id;
	}

	public Date getDepartureDate() {

		return departureDate;
	}

	public String getDepartureDateFormated() {

		return new SimpleDateFormat(DATE_FORMAT).format(this.getDepartureDate());
	}

	public void setDepartureDate(Date newDepartureDate) {

		departureDate = newDepartureDate;
	}

	public String getDestination() {

		return destination;
	}

	public void setDestination(String newDestination) {

		destination = newDestination;
	}

	public String getRational() {

		return rational;
	}

	public void setRational(String newRational) {

		rational = newRational;
	}

	public String getGrades() {

		return grades;
	}

	public void setGrades(String newGrades) {

		grades = newGrades;
	}

	public int getNumStudents() {

		return numStudents;
	}

	public void setNumStudents(int newNumStudents) {

		numStudents = newNumStudents;
	}

	public String getTeacherChaperon() {

		return teacherChaperon;
	}

	public void setTeacherChaperon(String newTeacherChaperon) {

		teacherChaperon = newTeacherChaperon;
	}

	public String getOtherChaperon() {

		return otherChaperon;
	}

	public void setOtherChaperon(String newOtherChaperon) {

		otherChaperon = newOtherChaperon;
	}

	public String getIteneraryFilename() {

		return iteneraryFilename;
	}

	public void setIteneraryFilename(String newIteneraryFilename) {

		iteneraryFilename = newIteneraryFilename;
	}

	public String getEmergencyContact() {

		return emergencyContact;
	}

	public void setEmergencyContact(String newEmergencyContact) {

		emergencyContact = newEmergencyContact;
	}

	public RequestStatus getStatus() {

		return status;
	}

	public void setStatus(RequestStatus newStatus) {

		status = newStatus;
	}

	public int getRequestId() {

		return requestId;
	}

	public void setRequestId(int newRequestId) {

		requestId = newRequestId;
	}

	public Date getReturnDate() {

		return returnDate;
	}

	public String getReturnDateFormatted() {

		return new SimpleDateFormat(DATE_FORMAT).format(this.getReturnDate());
	}

	public void setReturnDate(Date newReturnDate) {

		returnDate = newReturnDate;
	}

	public int getRequestedBy() {

		return requestedBy;
	}

	public void setRequestedBy(int newRequestedBy) {

		requestedBy = newRequestedBy;
	}

	public Date getRequestedDate() {

		return requestedDate;
	}

	public String getRequestedDateFormatted() {

		return new SimpleDateFormat(DATE_FORMAT).format(this.getRequestedDate());
	}

	public void setRequestedDate(Date newRequestedDate) {

		requestedDate = newRequestedDate;
	}

	public int getActionedBy() {

		return actionedBy;
	}

	public void setActionedBy(int newActionedBy) {

		actionedBy = newActionedBy;
	}

	public Date getActionDate() {

		return actionDate;
	}

	public String getActionDateFormatted() {

		return new SimpleDateFormat(DATE_FORMAT).format(this.getActionDate());
	}

	public void setActionDate(Date newActionDate) {

		actionDate = newActionDate;
	}

	public void setDaysMissed(double daysMissed) {

		this.daysMissed = daysMissed;
	}

	public double getDaysMissed() {

		return daysMissed;
	}

	public int getSchool_id() {

		return school_id;
	}

	public void setSchool_id(int schoolId) {

		school_id = schoolId;
	}

	public int getTotalChaperons() {

		return totalChaperons;
	}

	public void setTotalChaperons(int totalChaperons) {

		this.totalChaperons = totalChaperons;
	}

	public int getTotalTeacherChaperons() {

		return totalTeacherChaperons;
	}

	public void setTotalTeacherChaperons(int totalTeacherChaperons) {

		this.totalTeacherChaperons = totalTeacherChaperons;
	}

	public int getTotalOtherChaperons() {

		return totalOtherChaperons;
	}

	public void setTotalOtherChaperons(int totalOtherChaperons) {

		this.totalOtherChaperons = totalOtherChaperons;
	}

	public boolean isChaperonsApproved() {

		return chaperonsApproved;
	}

	public void setChaperonsApproved(boolean chaperonsApproved) {

		this.chaperonsApproved = chaperonsApproved;
	}

	public boolean isBilletingInvolved() {

		return billetingInvolved;
	}

	public void setBilletingInvolved(boolean billetingInvolved) {

		this.billetingInvolved = billetingInvolved;
	}

	public boolean isSchoolFundraising() {

		return schoolFundraising;
	}

	public void setSchoolFundraising(boolean schoolFundraising) {

		this.schoolFundraising = schoolFundraising;
	}

	public String toHTML() {

		StringBuffer buf = new StringBuffer();
		buf.append("<html><head>");
		buf.append("<style TYPE='text/css'><!--");
		buf.append("body {margin:25px;}");
		buf.append("table {width:75%; border:solid 2px #333333;}");
		buf.append("table th {border-right:solid 1px #333333; padding-right:3px; text-align:right;}");
		buf.append("--></style>");
		buf.append("</head><body>");
		buf.append("<div>");
		buf.append("<table cellpadding='2' cellspacing='0'>");
		buf.append("<tr><th>Destination</th><td>" + this.getDestination() + "</td></tr>");
		buf.append("<tr><th>Departure Date</th><td>" + this.getDepartureDateFormated() + "</td></tr>");
		buf.append("<tr><th>Return Date</th><td>" + this.getReturnDateFormatted() + "</td></tr>");
		buf.append("<tr><th># School Days Missed</th><td>" + this.getDaysMissed() + "</td></tr>");
		buf.append("<tr><th>Rational</th><td>" + this.getRational() + "</td></tr>");
		buf.append("<tr><th>Grades Involved</th><td>" + this.getGrades() + "</td></tr>");
		buf.append("<tr><th># Students Involved</th><td>" + this.getNumStudents() + "</td></tr>");
		buf.append("<tr><th>Total # Chaperons</th><td>" + this.getTotalChaperons() + "</td></tr>");
		buf.append("<tr><th>Total # Teacher Chaperons</th><td>" + this.getTotalTeacherChaperons() + "</td></tr>");
		buf.append("<tr><th>Teacher Chaperons</th><td>" + this.getTeacherChaperon() + "</td></tr>");
		buf.append("<tr><th>Total # Non-Teacher Chaperons</th><td>" + this.getTotalOtherChaperons() + "</td></tr>");
		buf.append("<tr><th>Non-Teacher Chaperons</th><td>"
				+ (StringUtils.isEmpty(this.getOtherChaperon()) ? "&nbsp;" : this.getOtherChaperon()) + "</td></tr>");
		buf.append("<tr><th>Chaperons Approved By Principal?</th><td>" + (this.isChaperonsApproved() ? "YES" : "NO")
				+ "</td></tr>");
		buf.append("<tr><th>Does this trip involve billeting?</th><td>" + (this.isBilletingInvolved() ? "YES" : "NO")
				+ "</td></tr>");
		buf.append("<tr><th>Is there any school fundraising associated with this trip?</th><td>"
				+ (this.isSchoolFundraising() ? "YES" : "NO") + "</td></tr>");
		buf.append("<tr><th>Emergency Contact</th><td>" + this.getEmergencyContact() + "</td></tr>");
		buf.append("<tr><th>Itinerary</th><td>FILE ATTACHED.</td></tr>");
		buf.append("</table>");
		buf.append("</div>");
		buf.append("</body></html>");

		return buf.toString();
	}
}