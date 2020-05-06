package com.esdnl.personnel.jobs.bean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.PersonnelException;
import com.esdnl.personnel.jobs.constants.EmploymentConstant;
import com.esdnl.personnel.jobs.constants.PositionTypeConstant;
import com.esdnl.personnel.jobs.constants.RTHPositionTypeConstant;
import com.esdnl.personnel.jobs.constants.RecommendationStatus;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.JobOpportunityManager;
import com.esdnl.servlet.FormElementFormat;

public class TeacherRecommendationBean {

	public static final int OFFER_VALID_PERIOD_HOURS = 48;

	private int recommendation_id;

	private String comp_num;
	private String candidate_id;
	private String candidate_sin2;
	private Date candidate_dob;
	private ApplicantProfileBean candidate;

	private RecommendationStatus cur_status;
	private PositionTypeConstant position_type;
	private String position_type_other;
	private EmploymentConstant employment_status;
	private String interview_panel;
	private String references_satisfactory;
	private String special_conditions;
	private String special_conditions_comment;
	private String other_comments;
	private String candidate_2;
	private String candidate_3;

	private int recommended_by;
	private Date recommended_date;

	private int approved_by;
	private Date approved_date;

	private int accepted_by;
	private Date accepted_date;

	private int rejected_by;
	private Date rejected_date;

	private int processed_by;
	private Date processed_date;

	private int offer_made_by;
	private Date offer_date;

	private Date offer_accepted_date;
	private Date offer_rejected_date;

	private GradeSubjectPercentUnitBean[] gsu;

	private boolean letterOfOfferRequired;

	private int referenceId;

	private int interviewSummaryId;
	private String candidateComments;
	private String candidateComments2;
	private String candidateComments3;
	//added for support staff postions
	private RTHPositionTypeConstant rth_position_type;

	private JobOpportunityBean job;

	public TeacherRecommendationBean() {

		recommendation_id = 0;
		comp_num = null;

		candidate_id = null;
		candidate_sin2 = null;
		candidate_dob = null;

		cur_status = null;
		position_type = null;
		position_type_other = null;
		employment_status = null;
		interview_panel = null;
		references_satisfactory = null;
		special_conditions = null;
		special_conditions_comment = null;
		other_comments = null;
		candidate_2 = null;
		candidate_3 = null;

		recommended_by = 0;
		recommended_date = null;

		accepted_by = 0;
		accepted_date = null;

		approved_by = 0;
		approved_date = null;

		rejected_by = 0;
		rejected_date = null;

		processed_by = 0;
		processed_date = null;

		offer_made_by = 0;
		offer_date = null;

		offer_accepted_date = null;
		offer_rejected_date = null;

		gsu = null;

		this.letterOfOfferRequired = false;

		this.referenceId = 0;

		this.interviewSummaryId = 0;
		candidateComments = null;
		candidateComments2 = null;
		candidateComments3 = null;
		rth_position_type = null;

		this.job = null;
		this.candidate = null;
	}

	public int getRecommendationId() {

		return this.recommendation_id;
	}

	public void setRecommendationId(int recommendation_id) {

		this.recommendation_id = recommendation_id;
	}

	public String getCompetitionNumber() {

		return this.comp_num;
	}

	public int getReferenceId() {

		return referenceId;
	}

	public void setReferenceId(int referenceId) {

		this.referenceId = referenceId;
	}

	public int getInterviewSummaryId() {

		return interviewSummaryId;
	}

	public void setInterviewSummaryId(int interviewSummaryId) {

		this.interviewSummaryId = interviewSummaryId;
	}

	public JobOpportunityBean getJob() throws JobOpportunityException {

		if (this.job == null) {
			this.job = JobOpportunityManager.getJobOpportunityBean(getCompetitionNumber());
		}

		return this.job;
	}

	public void setCompetitionNumber(String comp_num) {

		this.comp_num = comp_num;
	}

	public String getCandidateId() {

		return this.candidate_id;
	}

	public ApplicantProfileBean getCandidate() throws JobOpportunityException {

		if (this.candidate == null) {
			this.candidate = ApplicantProfileManager.getApplicantProfileBean(getCandidateId());
		}

		return this.candidate;
	}

	public void setCandidateId(String candidate_id) {

		this.candidate_id = candidate_id;
	}

	public String getCandidateSin2() {

		return this.candidate_sin2;
	}

	public void setCandidateSin2(String candidate_sin2) {

		this.candidate_sin2 = candidate_sin2;
	}

	public Date getCandidateDOB() {

		return this.candidate_dob;
	}

	public void setCandidateDOB(Date candidate_dob) {

		this.candidate_dob = candidate_dob;
	}

	public RecommendationStatus getCurrentStatus() {

		return this.cur_status;
	}

	public void setCurrentStatus(RecommendationStatus cur_status) {

		this.cur_status = cur_status;
	}

	public PositionTypeConstant getPositionType() {

		return this.position_type;
	}

	public void setPositionType(PositionTypeConstant position_type) {

		this.position_type = position_type;
	}

	public String getPositionTypeOther() {

		return this.position_type_other;
	}

	public void setPositionTypeOther(String position_type_other) {

		this.position_type_other = position_type_other;
	}

	public EmploymentConstant getEmploymentStatus() {

		return this.employment_status;
	}

	public void setEmploymentStatus(EmploymentConstant employment_status) {

		this.employment_status = employment_status;
	}

	public String getInterviewPanel() {

		return this.interview_panel;
	}

	public void setInterviewPanel(String interview_panel) {

		this.interview_panel = interview_panel;
	}

	public String getReferencesSatisfactory() {

		return this.references_satisfactory;
	}

	public void setReferencesSatisfactory(String references_satisfactory) {

		this.references_satisfactory = references_satisfactory;
	}

	public String getSpecialConditions() {

		return this.special_conditions;
	}

	public void setSpecialConditions(String special_conditions) {

		this.special_conditions = special_conditions;
	}

	public String getSpecialConditionsComment() {

		return this.special_conditions_comment;
	}

	public void setSpecialConditionsComment(String special_conditions_comment) {

		this.special_conditions_comment = special_conditions_comment;
	}

	public String getOtherComments() {

		return this.other_comments;
	}

	public void setOtherComments(String other_comments) {

		this.other_comments = other_comments;
	}

	public String getCandidate2() {

		return this.candidate_2;
	}

	public void setCandidate2(String candidate_2) {

		this.candidate_2 = candidate_2;
	}

	public String getCandidate3() {

		return this.candidate_3;
	}

	public void setCandidate3(String candidate_3) {

		this.candidate_3 = candidate_3;
	}

	public int getRecommendedBy() {

		return this.recommended_by;
	}

	public boolean isRecommended() {

		return (getRecommendedBy() > 0);
	}

	public Personnel getRecommendedByPersonnel() throws PersonnelException {

		return PersonnelDB.getPersonnel(this.recommended_by);
	}

	public void setRecommendBy(int recommended_by) {

		this.recommended_by = recommended_by;
	}

	public Date getRecommendedDate() {

		return this.recommended_date;
	}

	public String getRecommendedDateFormatted() {

		return new SimpleDateFormat(FormElementFormat.DATE_FORMAT).format(this.recommended_date);
	}

	public void setRecommendedDate(Date recommended_date) {

		this.recommended_date = recommended_date;
	}

	public int getApprovedBy() {

		return this.approved_by;
	}

	public boolean isApproved() {

		return (getApprovedBy() > 0);
	}

	public Personnel getApprovedByPersonnel() throws PersonnelException {

		return PersonnelDB.getPersonnel(this.approved_by);
	}

	public void setApprovedBy(int approved_by) {

		this.approved_by = approved_by;
	}

	public Date getApprovedDate() {

		return this.approved_date;
	}

	public String getApprovedDateFormatted() {

		return new SimpleDateFormat(FormElementFormat.DATE_FORMAT).format(this.approved_date);
	}

	public void setApprovedDate(Date approved_date) {

		this.approved_date = approved_date;
	}

	public int getAcceptedBy() {

		return this.accepted_by;
	}

	public boolean isAccepted() {

		return (getAcceptedBy() > 0);
	}

	public Personnel getAcceptedByPersonnel() throws PersonnelException {

		return PersonnelDB.getPersonnel(this.accepted_by);
	}

	public void setAcceptedBy(int accepted_by) {

		this.accepted_by = accepted_by;
	}

	public Date getAcceptedDate() {

		return this.accepted_date;
	}

	public String getAcceptedDateFormatted() {

		return new SimpleDateFormat(FormElementFormat.DATE_FORMAT).format(this.accepted_date);
	}

	public void setAcceptedDate(Date accepted_date) {

		this.accepted_date = accepted_date;
	}

	public int getRejectedBy() {

		return this.rejected_by;
	}

	public boolean isRejected() {

		return (getRejectedBy() > 0);
	}

	public Personnel getRejectedByPersonnel() throws PersonnelException {

		return PersonnelDB.getPersonnel(this.rejected_by);
	}

	public void setRejectedBy(int rejected_by) {

		this.rejected_by = rejected_by;
	}

	public Date getRejectedDate() {

		return this.rejected_date;
	}

	public String getRejectedDateFormatted() {

		return new SimpleDateFormat(FormElementFormat.DATE_FORMAT).format(this.rejected_date);
	}

	public void setRejectedDate(Date rejected_date) {

		this.rejected_date = rejected_date;
	}

	public int getProcessedBy() {

		return this.processed_by;
	}

	public boolean isProcessed() {

		return (getProcessedBy() > 0);
	}

	public Personnel getProcessedByPersonnel() throws PersonnelException {

		return PersonnelDB.getPersonnel(this.processed_by);
	}

	public void setProcessedBy(int processed_by) {

		this.processed_by = processed_by;
	}

	public Date getProcessedDate() {

		return this.processed_date;
	}

	public String getProcessedDateFormatted() {

		return new SimpleDateFormat(FormElementFormat.DATE_FORMAT).format(this.processed_date);
	}

	public void setProcessedDate(Date processed_date) {

		this.processed_date = processed_date;
	}

	public int getOfferMadeBy() {

		return this.offer_made_by;
	}

	public boolean isOfferMade() {

		return (getOfferMadeBy() > 0);
	}

	public Personnel getOfferMadeByPersonnel() throws PersonnelException {

		return PersonnelDB.getPersonnel(this.offer_made_by);
	}

	public void setOfferMadeBy(int offer_made_by) {

		this.offer_made_by = offer_made_by;
	}

	public Date getOfferMadeDate() {

		return this.offer_date;
	}

	public String getOfferMadeDateFormatted() {

		return new SimpleDateFormat(FormElementFormat.DATE_FORMAT).format(this.offer_date);
	}

	public void setOfferMadeDate(Date offer_date) {

		this.offer_date = offer_date;
	}

	public Date getOfferValidDate() {

		Date tmp = null;

		if (this.isOfferMade()) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(this.getOfferMadeDate());
			cal.add(Calendar.HOUR, TeacherRecommendationBean.OFFER_VALID_PERIOD_HOURS);
			tmp = cal.getTime();
		}

		return tmp;
	}

	public String getOfferValidDateFormatted() {

		String tmp = "";

		if (this.isOfferMade()) {
			tmp = new SimpleDateFormat(FormElementFormat.DATE_TIME_LONG_FORMAT).format(this.getOfferValidDate());
		}

		return tmp;
	}

	public boolean isOfferValid() {

		boolean check = false;

		if (this.isOfferMade()) {
			Calendar cal = Calendar.getInstance();

			check = (this.getOfferValidDate().compareTo(cal.getTime()) >= 0);
		}

		return check;
	}

	public boolean isOfferIgnored() {

		boolean check = false;

		if (this.isOfferMade() && !this.isOfferValid() && !this.isOfferAccepted() && !this.isOfferRejected()) {
			check = true;
		}

		return check;
	}

	public Date getOfferAcceptedDate() {

		return this.offer_accepted_date;
	}

	public String getOfferAcceptedDateFormatted() {

		return new SimpleDateFormat(FormElementFormat.DATE_FORMAT).format(this.offer_accepted_date);
	}

	public boolean isOfferAccepted() {

		return ((getOfferAcceptedDate() != null) && (getOfferRejectedDate() == null));
	}

	public void setOfferAcceptedDate(Date offer_accepted_date) {

		this.offer_accepted_date = offer_accepted_date;
	}

	public Date getOfferRejectedDate() {

		return this.offer_rejected_date;
	}

	public boolean isOfferRejected() {

		return ((getOfferRejectedDate() != null) && (getOfferAcceptedDate() == null));
	}

	public String getOfferRejectedDateFormatted() {

		return new SimpleDateFormat(FormElementFormat.DATE_FORMAT).format(this.offer_rejected_date);
	}

	public void setOfferRejectedDate(Date offer_rejected_date) {

		this.offer_rejected_date = offer_rejected_date;
	}

	public GradeSubjectPercentUnitBean[] getGSU() {

		return this.gsu;
	}

	public double getTotalUnits() {

		double total = 0;

		if ((this.gsu != null) && (this.gsu.length > 0)) {
			for (int i = 0; i < gsu.length; i++)
				total += this.gsu[i].getUnitPercentage();
		}

		return total;
	}

	public void setGSU(GradeSubjectPercentUnitBean[] gsu) {

		this.gsu = gsu;
	}

	// another recommendation is required (various reasons for this to occur.
	public boolean isExpired() {

		boolean check = false;

		if (this.isRecommended()) {
			if (!this.isApproved() && this.isRejected()) // rejected by
				// recommendation approver
				// (i.e. ed)
				check = true;
			else if (this.isApproved() && !this.isAccepted() && this.isRejected()) // rejected
				// by
				// recommendation
				// accepter
				// after
				// it
				// has
				// been
				// approved
				// (i.e.
				// lillian)
				check = true;
			else if (this.isApproved() && this.isAccepted() && this.isOfferMade()
					&& (this.isOfferRejected() || this.isOfferIgnored()))
				check = true;
		}

		return check;
	}

	public void setLetterOfOfferRequired(boolean letterOfOfferRequried) {

		this.letterOfOfferRequired = letterOfOfferRequried;
	}

	public boolean isLetterOfOfferRequire() {

		return this.letterOfOfferRequired;
	}

	public String getCandidateComments() {

		return candidateComments;
	}

	public void setCandidateComments(String candidateComments) {

		this.candidateComments = candidateComments;
	}

	public String getCandidateComments2() {

		return candidateComments2;
	}

	public void setCandidateComments2(String candidate2Comments) {

		this.candidateComments2 = candidate2Comments;
	}

	public String getCandidateComments3() {

		return candidateComments3;
	}

	public void setCandidateComments3(String candidate3Comments) {

		this.candidateComments3 = candidate3Comments;
	}

	public RTHPositionTypeConstant getRth_position_type() {

		return rth_position_type;
	}

	public void setRth_position_type(RTHPositionTypeConstant rth_position_type) {

		this.rth_position_type = rth_position_type;
	}
}
