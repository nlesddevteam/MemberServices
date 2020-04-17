package com.esdnl.personnel.jobs.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;

import com.esdnl.personnel.jobs.constants.EmploymentConstant;
import com.esdnl.personnel.jobs.dao.JobOpportunityManager;
import com.esdnl.personnel.jobs.dao.TeacherAllocationManager;
import com.esdnl.personnel.v2.model.sds.bean.EmployeeBean;

public class TeacherAllocationVacantPositionBean {

	private int positionId;
	private int allocationId;
	private String jobDescription;
	private EmploymentConstant type;
	private EmployeeBean employee;
	private String vacancyReason;
	private Date termStart;
	private Date termEnd;
	private double unit;
	private boolean advertised;
	private boolean filled;
	private AdRequestBean adRequest;

	public TeacherAllocationVacantPositionBean() {

		this.positionId = 0;
		this.allocationId = 0;
		this.jobDescription = null;
		this.type = null;
		this.employee = null;
		this.vacancyReason = null;
		this.termStart = null;
		this.termEnd = null;
		this.unit = 0.0;
		this.advertised = false;
		this.filled = false;
		this.adRequest = null;
	}

	public int getPositionId() {

		return positionId;
	}

	public void setPositionId(int positionId) {

		this.positionId = positionId;
	}

	public int getAllocationId() {

		return allocationId;
	}

	public void setAllocationId(int allocationId) {

		this.allocationId = allocationId;
	}

	public String getJobDescription() {

		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {

		this.jobDescription = jobDescription;
	}

	public EmploymentConstant getType() {

		return type;
	}

	public void setType(EmploymentConstant type) {

		this.type = type;
	}

	public EmployeeBean getEmployee() {

		return employee;
	}

	public void setEmployee(EmployeeBean employee) {

		this.employee = employee;
	}

	public String getVacancyReason() {

		return vacancyReason;
	}

	public void setVacancyReason(String vacancyReason) {

		this.vacancyReason = vacancyReason;
	}

	public Date getTermStart() {

		return termStart;
	}

	public void setTermStart(Date termStart) {

		this.termStart = termStart;
	}

	public Date getTermEnd() {

		return termEnd;
	}

	public void setTermEnd(Date termEnd) {

		this.termEnd = termEnd;
	}

	public double getUnit() {

		return unit;
	}

	public void setUnit(double unit) {

		this.unit = unit;
	}

	public boolean isAdvertised() {

		return advertised;
	}

	public void setAdvertised(boolean advertised) {

		this.advertised = advertised;
	}

	public boolean isFilled() {

		return filled;
	}

	public void setFilled(boolean filled) {

		this.filled = filled;
	}

	public String toXML() {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		StringBuffer buf = new StringBuffer();

		buf.append("<TEACHER-ALLOCATION-VACANT-POSITION-BEAN POSITION-ID=\""
				+ this.positionId
				+ "\" ALLOCATION-ID=\""
				+ this.allocationId
				+ "\" JOB-DESCRIPTION=\""
				+ StringEscapeUtils.escapeHtml(this.jobDescription)
				+ "\" TYPE-ID=\""
				+ this.type.getValue()
				+ "\" TYPE-DESCRIPTION=\""
				+ this.type.getDescription()
				+ "\" "
				+ (this.employee != null ? "EMP-ID=\"" + this.employee.getEmpId().trim() + "\" EMP-NAME=\""
						+ this.employee.getFullnameReverse() + "\" " : "") + "VACANCY-REASON=\""
				+ StringEscapeUtils.escapeHtml(this.vacancyReason) + "\" "
				+ (this.getTermStart() != null ? "TERM-START=\"" + sdf.format(this.getTermStart()) + "\" " : "")
				+ (this.getTermEnd() != null ? "TERM-END=\"" + sdf.format(this.getTermEnd()) + "\" " : "") + "UNIT=\""
				+ this.unit + "\" ADVERTISED=\"" + this.isAdvertised() + "\" FILLED=\"" + this.isFilled() + "\" ");
				//check to see if there is a link to the job ad
				//send ad title job comp numb to show on delete confirm
				if(!(this.adRequest == null)){
					if(!(this.adRequest.getCompetitionNumber() == null)){
						buf.append(" JOBLINK=\"" + "view_job_post.jsp?comp_num=" + this.adRequest.getCompetitionNumber() + "\" ");
						buf.append(" JOBCOMP=\"" +  this.adRequest.getCompetitionNumber() + "\" ");
						buf.append(" ADTITLE=\"" +  this.adRequest.getTitle() + "\" ");
						buf.append(" ADLINK=\"" + "viewAdRequest.html?rid=" + this.adRequest.getId() + "\" ");
						//now we check to see if it is filled
						try {
							JobOpportunityBean job = JobOpportunityManager.getJobOpportunityBean(this.adRequest.getCompetitionNumber());
							if(!(job == null)){
								if(job.isAwarded()){
									buf.append(" RECLINK=\"" + "admin_view_job_recommendation_list.jsp?comp_num=" + this.adRequest.getCompetitionNumber() + "\" ");
								}else{
									buf.append(" RECLINK=\"NONE\" ");
								}
							}else{
								buf.append(" RECLINK=\"NONE\" ");
							}
						} catch (JobOpportunityException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}else{
						//send back the link to the ad request
						buf.append(" ADLINK=\"" + "viewAdRequest.html?rid=" + this.adRequest.getId() + "\" ");
						buf.append(" JOBCOMP=\"NONE\" ");
						buf.append(" ADTITLE=\"" +  this.adRequest.getTitle() + "\" ");
						buf.append(" JOBLINK=\"NONE\" ");
						buf.append(" RECLINK=\"NONE\" ");
					}
				}else{
					// need to check the school year, if greater than > 19-20
					try {
						TeacherAllocationBean tab = TeacherAllocationManager.getTeacherAllocationBean(this.getAllocationId());
						String[] years = tab.getSchoolYear().split("-");
						if(Integer.parseInt(years[1]) > 20) {
							//add link for creating ad request
							buf.append(" CREATELINK=\"" + "createAdForVacantPosition?posid=" + this.getPositionId()+ "\" ");
						}else {
							buf.append(" CREATELINK=\"NONE\" ");
						}
						buf.append(" JOBLINK=\"NONE\" ");
						buf.append(" RECLINK=\"NONE\" ");
						buf.append(" ADLINK=\"NONE\" ");
						buf.append(" ADTITLE=\"NONE\" ");
						buf.append(" JOBCOMP=\"NONE\" ");
					} catch (JobOpportunityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				
				buf.append("/>");

		return buf.toString();
	}

	public AdRequestBean getAdRequest() {
		return adRequest;
	}

	public void setAdRequest(AdRequestBean adRequest) {
		this.adRequest = adRequest;
	}

}
