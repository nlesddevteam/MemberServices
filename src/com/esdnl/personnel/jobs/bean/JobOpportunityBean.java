package com.esdnl.personnel.jobs.bean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.PersonnelException;
import com.awsd.school.SubjectDB;
import com.awsd.school.SubjectException;
import com.esdnl.personnel.jobs.constants.EmploymentConstant;
import com.esdnl.personnel.jobs.constants.JobTypeConstant;
import com.esdnl.personnel.jobs.dao.AdRequestManager;
import com.esdnl.personnel.jobs.dao.AssignmentEducationManager;
import com.esdnl.personnel.jobs.dao.AssignmentMajorMinorManager;
import com.esdnl.personnel.jobs.dao.AssignmentTrainingMethodManager;
import com.esdnl.personnel.jobs.dao.DegreeManager;
import com.esdnl.personnel.jobs.dao.JobOpportunityAssignmentManager;
import com.esdnl.personnel.jobs.dao.RequestToHireManager;

public class JobOpportunityBean extends Vector<JobOpportunityAssignmentBean> {

	private static final long serialVersionUID = 1889951457406406863L;

	public static final String COMPETITION_FORMAT_STRING = "000000";
	public static final String DATE_FORMAT_STRING = "MMMM dd, yyyy";
	public static final String DATE_FORMAT_STRING_COMP_END_DATE = "MMMM dd, yyyy h:mm a";

	private String competition_number;
	private String position_title;
	private EmploymentConstant employment_class;
	private Date post_date;
	private Date competition_end_date;
	private Date start_date;
	private Date end_date;
	private String job_ad;
	private Date awarded_date;
	private Date listing_date;
	private JobTypeConstant job_type;
	private Date modified_date;
	private Date cancelled_date;
	private boolean private_candidate_list;
	private Date shortlistCompleteDate;
	private int reopenedById;
	private Personnel reopenedBy;
	private Date reopenedDate;

	private boolean unadvertise;
	private String isSupport;
	private boolean multipleRecommendations;
	private boolean awardedEmailSent;
	private String jobRequirements;

	private AdRequestBean adRequest;

	public JobOpportunityBean() {

		super();
		
		this.competition_number = null;
		this.position_title = null;
		this.employment_class = null;
		this.post_date = null;
		this.competition_end_date = null;
		this.start_date = null;
		this.end_date = null;
		this.job_ad = null;
		this.awarded_date = null;
		this.listing_date = null;
		this.job_type = null;
		this.modified_date = null;
		this.cancelled_date = null;
		this.private_candidate_list = false;
		this.shortlistCompleteDate = null;
		this.reopenedById = 0;
		this.reopenedBy = null;
		this.reopenedDate = null;
		this.multipleRecommendations = false;
		this.awardedEmailSent = false;
		this.jobRequirements = null;
		this.adRequest = null;
	}

	public void setCompetitionNumber(String competition_number) {

		this.competition_number = competition_number;
	}

	public String getCompetitionNumber() {

		return this.competition_number;
	}

	public void setPositionTitle(String position_title) {

		this.position_title = position_title;
	}

	public String getPositionTitle() {

		return this.position_title;
	}

	public void setEmploymentClass(EmploymentConstant employment_class) {

		this.employment_class = employment_class;
	}

	public EmploymentConstant getEmploymentClass() {

		return this.employment_class;
	}

	public Date getPostedDate() {

		return this.post_date;
	}

	public void setPostedDate(Date post_date) {

		this.post_date = post_date;
	}

	public String getFormatedPostedDate() {

		SimpleDateFormat sdf = new SimpleDateFormat(JobOpportunityBean.DATE_FORMAT_STRING);

		return sdf.format(this.getPostedDate());
	}

	public Date getCompetitionEndDate() {

		return this.competition_end_date;
	}

	public void setCompetitionEndDate(Date competition_end_date) {

		this.competition_end_date = competition_end_date;
	}

	public String getFormatedCompetitionEndDate() {

		SimpleDateFormat sdf = new SimpleDateFormat(JobOpportunityBean.DATE_FORMAT_STRING_COMP_END_DATE);

		return sdf.format(this.getCompetitionEndDate());
	}

	public boolean isClosed() {

		if (Calendar.getInstance().getTime().after(this.competition_end_date))
			return true;
		else
			return false;
	}

	public Date getJobStartDate() {

		return this.start_date;
	}

	public void setJobStartDate(Date start_date) {

		this.start_date = start_date;
	}

	public String getFormatedJobStartDate() {

		SimpleDateFormat sdf = new SimpleDateFormat(JobOpportunityBean.DATE_FORMAT_STRING);

		return sdf.format(this.getJobStartDate());
	}

	public Date getJobEndDate() {

		return this.end_date;
	}

	public void setJobEndDate(Date end_date) {

		this.end_date = end_date;
	}

	public String getFormatedJobEndDate() {

		if (this.getJobEndDate() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(JobOpportunityBean.DATE_FORMAT_STRING);

			return sdf.format(this.getJobEndDate());
		}
		else
			return "INDEFINITE";
	}

	public Date getModifiedDate() {

		return this.modified_date;
	}

	public void setModifiedDate(Date modified_date) {

		this.modified_date = modified_date;
	}

	public String getFormatedModifiedDate() {

		SimpleDateFormat sdf = new SimpleDateFormat(JobOpportunityBean.DATE_FORMAT_STRING);

		return sdf.format(this.getModifiedDate());
	}

	public Date getCancelledDate() {

		return this.cancelled_date;
	}

	public void setCancelledDate(Date cancelled_date) {

		this.cancelled_date = cancelled_date;
	}

	public String getFormatedCancelledDate() {

		SimpleDateFormat sdf = new SimpleDateFormat(JobOpportunityBean.DATE_FORMAT_STRING);

		return sdf.format(this.getCancelledDate());
	}

	public boolean isCancelled() {

		if (this.cancelled_date != null)
			return true;
		else
			return false;
	}

	public String getJobAdText() {

		return this.job_ad;
	}

	public void setJobAdText(String job_ad) {

		this.job_ad = job_ad;
	}

	public Date getJobAwardedDate() {

		return this.awarded_date;
	}

	public void setJobAwardedDate(Date awarded_date) {

		this.awarded_date = awarded_date;
	}

	public String getFormatedJobAwardedDate() {

		SimpleDateFormat sdf = new SimpleDateFormat(JobOpportunityBean.DATE_FORMAT_STRING);

		return sdf.format(this.getJobAwardedDate());
	}

	public boolean isAwarded() {

		if (this.awarded_date == null)
			return false;
		else
			return true;
	}

	public Date getListingDate() {

		return this.listing_date;
	}

	public String getFormatedListingDate() {

		SimpleDateFormat sdf = new SimpleDateFormat(JobOpportunityBean.DATE_FORMAT_STRING);

		return sdf.format(this.getListingDate());
	}

	public void setListingDate(Date listing_date) {

		this.listing_date = listing_date;
	}

	public JobTypeConstant getJobType() {

		return this.job_type;
	}

	public void setJobType(JobTypeConstant job_type) {

		this.job_type = job_type;
	}

	public boolean isCandidateListPrivate() {

		return this.private_candidate_list;
	}

	public void setPrivateCandidateList(boolean private_candidate_list) {

		this.private_candidate_list = private_candidate_list;
	}

	public boolean add(JobOpportunityAssignmentBean o) {

		if (o == null)
			return false;

		return super.add(o);
	}

	public boolean isUnadvertise() {

		return unadvertise;
	}

	public void setUnadvertise(boolean unadvertise) {

		this.unadvertise = unadvertise;
	}

	public Date getShortlistCompleteDate() {

		return shortlistCompleteDate;
	}

	public String getFormattedShortlistCompleteDate() {

		SimpleDateFormat sdf = new SimpleDateFormat(JobOpportunityBean.DATE_FORMAT_STRING_COMP_END_DATE);

		return sdf.format(this.getShortlistCompleteDate());
	}

	public void setShortlistCompleteDate(Date shortlistCompleteDate) {

		this.shortlistCompleteDate = shortlistCompleteDate;
	}

	public boolean isShortlistComplete() {

		return (this.getShortlistCompleteDate() != null);
	}

	public Date getReopenedDate() {

		return reopenedDate;
	}

	public String getFormattedReopenedDate() {

		SimpleDateFormat sdf = new SimpleDateFormat(JobOpportunityBean.DATE_FORMAT_STRING_COMP_END_DATE);

		return sdf.format(this.getReopenedDate());
	}

	public void setReopenedDate(Date reopenedDate) {

		this.reopenedDate = reopenedDate;
	}

	public boolean isReopened() {

		return (this.getReopenedDate() != null);
	}

	public int getReopenedById() {

		return this.reopenedById;
	}

	public Personnel getReopenedBy() throws PersonnelException {

		if ((this.reopenedById > 0) && (this.reopenedBy == null)) {
			this.reopenedBy = PersonnelDB.getPersonnel(this.reopenedById);
		}

		return this.reopenedBy;
	}

	public void setReopenedById(int reopenedById) {

		this.reopenedById = reopenedById;
	}

	public String toString() {

		return "Competition Number: " + this.competition_number;
	}

	public String getJobLocation() throws JobOpportunityException {

		JobOpportunityAssignmentBean[] abean = null;
		StringBuffer buf = new StringBuffer();

		abean = JobOpportunityAssignmentManager.getJobOpportunityAssignmentBeans(this);
		if (abean.length > 0) {
			for (int i = 0; i < abean.length; i++)
				buf.append(abean[i].getLocationText() + ((i > 0) ? " " : ""));
		}

		return buf.toString();
	}

	public JobOpportunityAssignmentBean[] getAssignments() {

		return (JobOpportunityAssignmentBean[]) (this.toArray(new JobOpportunityAssignmentBean[0]));
	}

	public String toHTML() {

		JobOpportunityAssignmentBean[] abean = null;
		AssignmentEducationBean[] edu = null;
		AssignmentMajorMinorBean[] mjr = null;
		AssignmentTrainingMethodBean[] trnmtd = null;
		StringBuffer out = new StringBuffer();

		try {
			abean = JobOpportunityAssignmentManager.getJobOpportunityAssignmentBeans(this);

			out.append("<TABLE width='100%' cellpadding='0' cellspacing='0'>");
			out.append("<TR><TD colspan='2' class='displayPositionTitle'>" + this.getPositionTitle() + "</TD></TR>");
			out.append(
					"<TR style='padding-top:5px;'><TD width='125px' class='displayCompetitionNumber'>Competition #:</TD><TD width='*' class='displayCompetitionNumber'> "
							+ this.getCompetitionNumber() + "</TD></TR>");

			if (abean.length > 0) {
				out.append(
						"<TR  style='padding-top:5px;'><TD width='125px' class='displayLocation'>Location(s):</TD><TD width='*' class='displayCompetitionNumber'><B>"
								+ abean[0].getLocationText() + "</B></TD></TR>");
				for (int i = 1; i < abean.length; i++)
					out.append("<TR><TD width='125px'>&nbsp;</TD><TD width='*' class='displayCompetitionNumber'>"
							+ abean[i].getLocationText() + "</TD></TR>");

				for (int j = 0; j < abean.length; j++) {
					edu = AssignmentEducationManager.getAssignmentEducationBeans(abean[j]);
					if (edu.length > 0) {
						out.append(
								"<TR style='padding-top:5px;'><TD width='125px' class='displayEducationRequired'>Education Required:</TD><TD width='*' class='displayCompetitionNumber'>&nbsp;</TD></TR>");
						for (int k = 0; k < edu.length; k++)
							out.append("<TR><TD width='125px'>&nbsp;</TD><TD width='*' class='displayCompetitionNumber'>"
									+ DegreeManager.getDegreeBeans(edu[k].getDegreeId()).getTitle() + "</TD></TR>");
					}

					mjr = AssignmentMajorMinorManager.getAssignmentMajorMinorBeans(abean[j]);
					if (mjr.length > 0) {
						out.append(
								"<TR style='padding-top:5px;'><TD width='125px' class='displayEducationRequired'>Major(s) Required:</TD><TD width='*' class='displayCompetitionNumber'>&nbsp;</TD></TR>");
						for (int l = 0; l < mjr.length; l++) {
							if (mjr[l].getMajorId() > 0)
								out.append("<TR><TD width='125px'>&nbsp;</TD><TD width='*' class='displayCompetitionNumber'>"
										+ SubjectDB.getSubject(mjr[l].getMajorId()).getSubjectName() + "</TD></TR>");
						}
					}
					if (mjr.length > 0) {
						out.append(
								"<TR style='padding-top:5px;'><TD width='125px' class='displayEducationRequired'>Minor(s) Required:</TD><TD width='*' class='displayCompetitionNumber'>&nbsp;</TD></TR>");
						for (int l = 0; l < mjr.length; l++) {
							if (mjr[l].getMinorId() > 0)
								out.append("<TR><TD width='125px'>&nbsp;</TD><TD width='*' class='displayCompetitionNumber'>"
										+ SubjectDB.getSubject(mjr[l].getMinorId()).getSubjectName() + "</TD></TR>");
						}
					}

					trnmtd = AssignmentTrainingMethodManager.getAssignmentTrainingMethodBeans(abean[j]);

					if (trnmtd.length > 0) {
						try {
							out.append(
									"<TR style='padding-top:5px;'><TD width='125px' class='displayEducationRequired'>Training Method:</TD><TD width='*' class='displayCompetitionNumber'>"
											+ trnmtd[0].getTrainingMethod().getDescription() + "</TD></TR>");
						}
						catch (NullPointerException ex) {
							System.err.println(
									">>>>>>>> NULL POINTER EXCEPTION: ASSIGN_ID=" + trnmtd[0].getJobOpportunityAssignmentId());
						}
						for (int s = 1; s < trnmtd.length; s++)
							out.append("<TR><TD width='125px'>&nbsp;</TD><TD width='*' class='displayCompetitionNumber'>"
									+ trnmtd[s].getTrainingMethod().getDescription() + "</TD></TR>");
					}
				}
			}
			out.append(
					"<TR><TD  colspan='2' class='displayAdText'>" + this.getJobAdText().replaceAll("\n", "<BR>") + "</TD></TR>");

			out.append("</TABLE>");
		}
		catch (SubjectException e) {
			e.printStackTrace();
		}
		catch (JobOpportunityException e) {
			e.printStackTrace();
		}

		return out.toString();
	}
	public String toHTMLNoAd(TeacherRecommendationBean rec) throws JobOpportunityException {
		StringBuilder sb = new StringBuilder();
		sb.append("<TABLE width='100%' cellpadding='0' cellspacing='0'>");
		sb.append("<TR style='padding-top:5px;'><TD width='125px' class='displayCompetitionNumber'><b>Competition #:</b></TD><TD width='*' class='displayCompetitionNumber'> "
				+ this.getCompetitionNumber() + "</TD></TR>");
		if(this.isSupport()) {
			RequestToHireBean rbean = RequestToHireManager.getRequestToHireByCompNum(this.competition_number);
			sb.append("<TR style='padding-top:5px;'><TD width='125px' class='displayCompetitionNumber'><b>School/Work Location:</b></TD><TD width='*' class='displayCompetitionNumber'> "
					+ (rbean.getWorkLocation() == null?"":rbean.getLocationDescription()) + "</TD></TR>");
			sb.append("<TR style='padding-top:5px;'><TD width='125px' class='displayCompetitionNumber'><b>Position:</b></TD><TD width='*' class='displayCompetitionNumber'> "
					+ (rbean.getJobTitle() == null ? "": rbean.getJobTitle()) + "</TD></TR>");
			sb.append("<TR style='padding-top:5px;'><TD width='125px' class='displayCompetitionNumber'><b>Hours/Week:</b></TD><TD width='*' class='displayCompetitionNumber'> "
					+ (rbean.getPositionHours() == null ? "":rbean.getPositionHours() ) + "</TD></TR>");
			sb.append("<TR style='padding-top:5px;'><TD width='125px' class='displayCompetitionNumber'><b>10 or 12 Month:</b></TD><TD width='*' class='displayCompetitionNumber'> "
					+ (rbean.getPositionTerm() == 0 ? "":rbean.getPositionTermString() ) + "</TD></TR>");
			sb.append("<TR style='padding-top:5px;'><TD width='125px' class='displayCompetitionNumber'><b>Type:</b></TD><TD width='*' class='displayCompetitionNumber'> "
					+ (rbean.getPositionType() == 0 ? "":rbean.getPositionTypeString() ) + "</TD></TR>");
			if(rbean.getStartDate() != null) {
				sb.append("<TR style='padding-top:5px;'><TD width='125px' class='displayCompetitionNumber'><b>Start Date:</b></TD><TD width='*' class='displayCompetitionNumber'> "
						+ (rbean.getStartDate() == null ? "":rbean.getStartDateFormatted() ) + "</TD></TR>");
			}
			if(rbean.getEndDate() != null) {
				sb.append("<TR style='padding-top:5px;'><TD width='125px' class='displayCompetitionNumber'><b>End Date:</b></TD><TD width='*' class='displayCompetitionNumber'> "
						+ (rbean.getEndDate() == null ? "":rbean.getEndDateFormatted() ) + "</TD></TR>");
			}
			
		}else {
			sb.append("<TR style='padding-top:5px;'><TD width='125px' class='displayCompetitionNumber'><b>School/Work Location:</b></TD><TD width='*' class='displayCompetitionNumber'> "
					+ (this.getJobLocation() == null?"":this.getJobLocation()) + "</TD></TR>");
			sb.append("<TR style='padding-top:5px;'><TD width='125px' class='displayCompetitionNumber'><b>Position:</b></TD><TD width='*' class='displayCompetitionNumber'> "
					+ (this.getPositionTitle() == null ? "": this.getPositionTitle()) + "</TD></TR>");
			sb.append("<TR style='padding-top:5px;'><TD width='125px' class='displayCompetitionNumber'><b>Percentage:</b></TD><TD width='*' class='displayCompetitionNumber'> ");
			if(rec.getTotalUnits() > 0){
				sb.append(String.format("%.2f",rec.getTotalUnits()));
			}else if(this.getAdRequest().getUnits() >0) {
				sb.append(String.format("%.2f",this.getAdRequest().getUnits()));
			}else {
				sb.append("0.0");
			}
			sb.append("</TD></TR>");
			
			sb.append("<TR style='padding-top:5px;'><TD width='125px' class='displayCompetitionNumber'><b>Type:</b></TD><TD width='*' class='displayCompetitionNumber'> "
					+ rec.getEmploymentStatus().getDescription()  + "</TD></TR>");
		
			if(this.getAdRequest().getStartDate() != null) {
				sb.append("<TR style='padding-top:5px;'><TD width='125px' class='displayCompetitionNumber'><b>Start Date (dd/mm/yyyy):</b></TD><TD width='*' class='displayCompetitionNumber'> "
						+ this.getAdRequest().getFormatedStartDate() + "</TD></TR>");
			}
			if(this.getAdRequest().getEndDate() != null) {
				sb.append("<TR style='padding-top:5px;'><TD width='125px' class='displayCompetitionNumber'><b>End Date (dd/mm/yyyy):</b></TD><TD width='*' class='displayCompetitionNumber'> "
						+ this.getAdRequest().getFormatedEndDate() + "</TD></TR>");
			}
		}
		sb.append("</TABLE>");
		return sb.toString();
	}

	public String getIsSupport() {

		return isSupport;
	}

	public void setIsSupport(String isSupport) {

		this.isSupport = isSupport;
	}

	public boolean isSupport() {

		return StringUtils.equals(this.getIsSupport(), "Y");
	}

	public boolean isMultipleRecommendations() {

		return multipleRecommendations;
	}

	public void setMultipleRecommendations(boolean multipleRecommendations) {

		this.multipleRecommendations = multipleRecommendations;
	}

	public boolean isAwardedEmailSent() {

		return awardedEmailSent;
	}

	public void setAwardedEmailSent(boolean awardedEmailSent) {

		this.awardedEmailSent = awardedEmailSent;
	}

	public String getJobRequirements() {

		return jobRequirements;
	}

	public void setJobRequirements(String jobRequirements) {

		this.jobRequirements = jobRequirements;
	}

	public AdRequestBean getAdRequest() throws JobOpportunityException {

		if (StringUtils.isNotBlank(this.competition_number) && this.adRequest == null) {
			this.adRequest = AdRequestManager.getAdRequestBean(this.competition_number);
		}

		return this.adRequest;
	}
}