package com.esdnl.personnel.jobs.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;

import com.awsd.school.Subject;
import com.awsd.school.SubjectDB;
import com.awsd.school.SubjectException;
import com.esdnl.personnel.jobs.constants.JobTypeConstant;
import com.esdnl.personnel.jobs.constants.RequestStatus;
import com.esdnl.personnel.jobs.constants.TrainingMethodConstant;
import com.esdnl.personnel.jobs.dao.AdRequestDegreeManager;
import com.esdnl.personnel.jobs.dao.AdRequestHistoryManager;
import com.esdnl.personnel.jobs.dao.AdRequestMajorManager;
import com.esdnl.personnel.jobs.dao.AdRequestMinorManager;
import com.esdnl.personnel.jobs.dao.DegreeManager;
import com.esdnl.personnel.v2.model.sds.bean.EmployeeBean;
import com.esdnl.personnel.v2.model.sds.bean.LocationBean;

public class AdRequestBean {

	public static final String DATE_FORMAT = "dd/MM/yyyy";

	private int id;
	private String title;
	private double units;
	private Subject[] major;
	private Subject[] minor;
	private DegreeBean[] degree;
	private TrainingMethodConstant trainingMethod;
	private String vacancyReason;
	private Date startDate;
	private Date endDate;
	private RequestStatus currentStatus;
	private EmployeeBean owner;
	private LocationBean location;
	private JobTypeConstant job_type;
	private String ad_text;
	private HashMap<RequestStatus, AdRequestHistoryBean> history;
	private String comp_num;
	private boolean unadvertised;
	private String jobRequirements;
	
	public AdRequestBean() {

		this.id = 0;
		this.units = 1.0;
		this.major = null;
		this.minor = null;
		this.trainingMethod = null;
		this.vacancyReason = null;
		this.title = null;
		this.ad_text = null;
		this.startDate = null;
		this.endDate = null;
		this.currentStatus = null;
		this.owner = null;
		this.location = null;
		this.job_type = null;
		this.degree = null;
		this.comp_num = null;
		this.unadvertised = false;

		this.history = null;
		this.jobRequirements=null;
	}

	public int getId() {

		return id;
	}

	public void setId(int newId) {

		id = newId;
	}

	public void setTitle(String title) {

		this.title = title;
	}

	public String getTitle() {

		return this.title;
	}

	public double getUnits() {

		return units;
	}

	public void setUnits(double newUnits) {

		units = newUnits;
	}

	public void setMajors(String majors[]) throws SubjectException {

		Vector<Subject> tmp = new Vector<Subject>();

		for (int i = 0; i < majors.length; i++) {
			if (majors[i].equals("-1"))
				continue;
			tmp.add(SubjectDB.getSubject(Integer.parseInt(majors[i])));
		}

		major = tmp.toArray(new Subject[0]);
	}

	public void setMajors(Subject majors[]) throws SubjectException {

		major = majors;
	}

	public Subject[] getMajors() {

		if (this.major == null) {
			try {
				this.setMajors(AdRequestMajorManager.getAdRequestMajors(this));
			}
			catch (Exception e) {
				this.major = null;
			}
		}

		return this.major;
	}

	public void setMinors(String minors[]) throws SubjectException {

		Vector<Subject> tmp = new Vector<Subject>();

		for (int i = 0; i < minors.length; i++) {
			if (minors[i].equals("-1"))
				continue;
			tmp.add(SubjectDB.getSubject(Integer.parseInt(minors[i])));
		}

		minor = tmp.toArray(new Subject[0]);
	}

	public void setMinors(Subject minors[]) throws SubjectException {

		minor = minors;
	}

	public Subject[] getMinors() {

		if (this.minor == null) {
			try {
				this.setMinors(AdRequestMinorManager.getAdRequestMinors(this));
			}
			catch (Exception e) {
				this.minor = null;
			}
		}
		return this.minor;
	}

	public void setDegrees(String degrees[]) throws JobOpportunityException {

		Vector<DegreeBean> tmp = new Vector<DegreeBean>();

		for (int i = 0; i < degrees.length; i++) {
			if (degrees[i].equals("0"))
				continue;
			tmp.add(DegreeManager.getDegreeBeans(degrees[i]));
		}

		degree = tmp.toArray(new DegreeBean[0]);
	}

	public void setDegrees(DegreeBean[] degrees) {

		degree = degrees;
	}

	public DegreeBean[] getDegrees() {

		if (this.degree == null) {
			try {
				this.setDegrees(AdRequestDegreeManager.getAdRequestDegrees(this));
			}
			catch (Exception e) {
				this.degree = null;
			}
		}
		return this.degree;
	}

	public TrainingMethodConstant getTrainingMethod() {

		return trainingMethod;
	}

	public void setTrainingMethod(TrainingMethodConstant newTrainingMethod) {

		trainingMethod = newTrainingMethod;
	}

	public String getVacancyReason() {

		return vacancyReason;
	}

	public void setVacancyReason(String newVacancyReason) {

		vacancyReason = newVacancyReason;
	}

	public Date getStartDate() {

		return startDate;
	}

	public String getFormatedStartDate() {

		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

		return sdf.format(startDate);
	}

	public void setStartDate(Date newStartDate) {

		startDate = newStartDate;
	}

	public Date getEndDate() {

		return endDate;
	}

	public String getFormatedEndDate() {

		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

		return sdf.format(endDate);
	}

	public void setEndDate(Date newEndDate) {

		endDate = newEndDate;
	}

	public RequestStatus getCurrentStatus() {

		return currentStatus;
	}

	public void setCurrentStatus(RequestStatus newCurrentStatus) {

		currentStatus = newCurrentStatus;
	}

	public EmployeeBean getOwner() {

		return owner;
	}

	public void setOwner(EmployeeBean newOwner) {

		owner = newOwner;
	}

	public boolean isVacantPosition() {

		return (this.job_type.equal(JobTypeConstant.REGULAR)) && (owner == null);
	}

	public LocationBean getLocation() {

		return location;
	}

	public void setLocation(LocationBean newLocation) {

		location = newLocation;
	}

	public void setJobType(JobTypeConstant job_type) {

		this.job_type = job_type;
	}

	public JobTypeConstant getJobType() {

		return this.job_type;
	}

	public String getAdText() {

		return this.ad_text;
	}

	public void setAdText(String ad_text) {

		this.ad_text = ad_text;
	}

	public String getCompetitionNumber() {

		return this.comp_num;
	}

	public void setCompetitionNumber(String comp_num) {

		this.comp_num = comp_num;
	}

	public void setUnadvertised(boolean unadvertised) {

		this.unadvertised = unadvertised;
	}

	public boolean isUnadvertised() {

		return this.unadvertised;
	}

	public void setHistory(HashMap<RequestStatus, AdRequestHistoryBean> history) {

		this.history = history;
	}

	public AdRequestHistoryBean[] getHistory() {

		if (this.history == null) {
			try {
				this.setHistory(AdRequestHistoryManager.getAdRequestHistory(this));
			}
			catch (Exception e) {
				this.history = new HashMap<RequestStatus, AdRequestHistoryBean>();
			}
		}

		return this.history.values().toArray(new AdRequestHistoryBean[0]);
	}

	public AdRequestHistoryBean getHistory(RequestStatus status) {

		if (this.history == null) {
			try {
				this.setHistory(AdRequestHistoryManager.getAdRequestHistory(this));
			}
			catch (Exception e) {
				this.history = new HashMap<RequestStatus, AdRequestHistoryBean>();
			}
		}

		return history.get(status);
	}
	public String getJobRequirements() {
		return jobRequirements;
	}

	public void setJobRequirements(String jobRequirements) {
		this.jobRequirements = jobRequirements;
	}
}