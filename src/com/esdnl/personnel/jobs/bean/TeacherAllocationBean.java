package com.esdnl.personnel.jobs.bean;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;

import com.esdnl.personnel.v2.model.sds.bean.LocationBean;

public class TeacherAllocationBean {

	private int allocationId;
	private String schoolYear;
	private LocationBean location;
	private double regularUnits;
	private double administrativeUnits;
	private double guidanceUnits;
	private double specialistUnits;
	private double lrtUnits;
	private double irt1Units;
	private double irt2Units;
	private double otherUnits;
	private double tlaUnits;
	private double studentAssistantHours;
	private double readingSpecialistUnits;
	private boolean enabled;
	private boolean published;

	private Collection<TeacherAllocationExtraBean> extras;
	private Collection<TeacherAllocationPermanentPositionBean> permanentPositions;
	private Collection<TeacherAllocationVacantPositionBean> vacantPositions;
	private Collection<TeacherAllocationRedundantPositionBean> redundantPositions;

	public TeacherAllocationBean() {

		this.allocationId = 0;
		this.schoolYear = null;
		this.location = null;
		this.regularUnits = 0.0;
		this.administrativeUnits = 0.0;
		this.guidanceUnits = 0.0;
		this.specialistUnits = 0.0;
		this.lrtUnits = 0.0;
		this.irt1Units = 0.0;
		this.irt2Units = 0.0;
		this.otherUnits = 0.0;
		this.tlaUnits = 0.0;
		this.studentAssistantHours = 0.0;
		this.readingSpecialistUnits = 0.0;

		this.enabled = false;
		this.published = false;

		this.extras = null;
		this.permanentPositions = null;
		this.vacantPositions = null;
		this.redundantPositions = null;
	}

	public int getAllocationId() {

		return allocationId;
	}

	public void setAllocationId(int allocationId) {

		this.allocationId = allocationId;
	}

	public String getSchoolYear() {

		return schoolYear;
	}

	public void setSchoolYear(String schoolYear) {

		this.schoolYear = schoolYear;
	}

	public LocationBean getLocation() {

		return location;
	}

	public void setLocation(LocationBean location) {

		this.location = location;
	}

	public double getRegularUnits() {

		return regularUnits;
	}

	public void setRegularUnits(double regUnits) {

		this.regularUnits = regUnits;
	}

	public double getAdministrativeUnits() {

		return administrativeUnits;
	}

	public void setAdministrativeUnits(double administrativeUnits) {

		this.administrativeUnits = administrativeUnits;
	}

	public double getGuidanceUnits() {

		return guidanceUnits;
	}

	public void setGuidanceUnits(double guidanceUnits) {

		this.guidanceUnits = guidanceUnits;
	}

	public double getSpecialistUnits() {

		return specialistUnits;
	}

	public void setSpecialistUnits(double specialistUnits) {

		this.specialistUnits = specialistUnits;
	}

	public double getLRTUnits() {

		return lrtUnits;
	}

	public void setLRTUnits(double lrtUnits) {

		this.lrtUnits = lrtUnits;
	}

	public double getIRT1Units() {

		return irt1Units;
	}

	public void setIRT1Units(double irt1Units) {

		this.irt1Units = irt1Units;
	}

	public double getIRT2Units() {

		return irt2Units;
	}

	public void setIRT2Units(double irt2Units) {

		this.irt2Units = irt2Units;
	}

	public double getOtherUnits() {

		return otherUnits;
	}

	public void setOtherUnits(double otherUnits) {

		this.otherUnits = otherUnits;
	}

	public double getTLAUnits() {

		return this.tlaUnits;
	}

	public void setTLAUnits(double tlaUnits) {

		this.tlaUnits = tlaUnits;
	}

	public double getStudentAssistantHours() {

		return this.studentAssistantHours;
	}

	public void setStudentAssistantHours(double studentAssistantHours) {

		this.studentAssistantHours = studentAssistantHours;
	}

	public double getReadingSpecialistUnits() {

		return readingSpecialistUnits;
	}

	public void setReadingSpecialistUnits(double readingSpecialistUnits) {

		this.readingSpecialistUnits = readingSpecialistUnits;
	}

	public boolean isEnabled() {

		return enabled;
	}

	public void setEnabled(boolean enabled) {

		this.enabled = enabled;
	}

	public boolean isPublished() {

		return this.published;
	}

	public void setPublished(boolean published) {

		this.published = published;
	}

	public Collection<TeacherAllocationExtraBean> getExtraAllocations() {

		return extras;
	}

	public void setExtraAllocations(Collection<TeacherAllocationExtraBean> extras) {

		this.extras = extras;
	}

	public void addExtraAllocation(TeacherAllocationExtraBean extra) {

		if (this.extras == null)
			this.extras = new ArrayList<TeacherAllocationExtraBean>();

		this.extras.add(extra);
	}

	public double getTotalExtraTCHRAllocationUnits() {

		double total = 0.0;

		if (this.extras != null) {
			for (TeacherAllocationExtraBean allocation : extras) {
				if (allocation.getAllocationType().equals(TeacherAllocationExtraBean.AllocationType.TCHR)) {
					total += allocation.getExtraAllocationUnits();
				}
			}
		}

		return total;
	}

	public double getTotalExtraTLAAllocationUnits() {

		double total = 0.0;

		if (this.extras != null) {
			for (TeacherAllocationExtraBean allocation : extras) {
				if (allocation.getAllocationType().equals(TeacherAllocationExtraBean.AllocationType.TLA)) {
					total += allocation.getExtraAllocationUnits();
				}
			}
		}

		return total;
	}

	public double getTotalExtraSAAllocationHours() {

		double total = 0.0;

		if (this.extras != null) {
			for (TeacherAllocationExtraBean allocation : extras) {
				if (allocation.getAllocationType().equals(TeacherAllocationExtraBean.AllocationType.SA)) {
					total += allocation.getExtraAllocationUnits();
				}
			}
		}

		return total;
	}

	public Collection<TeacherAllocationPermanentPositionBean> getPermanentPositions() {

		return permanentPositions;
	}

	public void setPermanentPositions(Collection<TeacherAllocationPermanentPositionBean> permanentPositions) {

		this.permanentPositions = permanentPositions;
	}

	public void addPermanentPosition(TeacherAllocationPermanentPositionBean position) {

		if (this.permanentPositions == null)
			this.permanentPositions = new ArrayList<TeacherAllocationPermanentPositionBean>();

		this.permanentPositions.add(position);
	}

	public double getTotalPermanentPositionUnits() {

		double total = 0.0;

		if (this.permanentPositions != null) {
			for (TeacherAllocationPermanentPositionBean allocation : permanentPositions) {
				total += allocation.getUnit();
			}
		}

		return total;
	}

	public Collection<TeacherAllocationVacantPositionBean> getVacantPositions() {

		return vacantPositions;
	}

	public void setVacantPositions(Collection<TeacherAllocationVacantPositionBean> vacantPositions) {

		this.vacantPositions = vacantPositions;
	}

	public void addVacantPosition(TeacherAllocationVacantPositionBean position) {

		if (this.vacantPositions == null)
			this.vacantPositions = new ArrayList<TeacherAllocationVacantPositionBean>();

		this.vacantPositions.add(position);
	}

	public double getTotalVacantPositionUnits() {

		double total = 0.0;

		if (this.vacantPositions != null) {
			for (TeacherAllocationVacantPositionBean allocation : vacantPositions) {
				total += allocation.getUnit();
			}
		}

		return total;
	}

	public Collection<TeacherAllocationRedundantPositionBean> getRedundantPositions() {

		return redundantPositions;
	}

	public void setRedundantPositions(Collection<TeacherAllocationRedundantPositionBean> redundantPositions) {

		this.redundantPositions = redundantPositions;
	}

	public void addRedundantPosition(TeacherAllocationRedundantPositionBean position) {

		if (this.redundantPositions == null)
			this.redundantPositions = new ArrayList<TeacherAllocationRedundantPositionBean>();

		this.redundantPositions.add(position);
	}

	public double getTotalRedundantPositionUnits() {

		double total = 0.0;

		if (this.redundantPositions != null) {
			for (TeacherAllocationRedundantPositionBean allocation : redundantPositions) {
				total += allocation.getUnit();
			}
		}

		return total;
	}

	public double getTCHRAllocationUnits() {

		double total = this.regularUnits + this.administrativeUnits + this.guidanceUnits + this.specialistUnits
				+ this.lrtUnits + this.irt1Units + this.irt2Units + this.otherUnits + this.readingSpecialistUnits;

		return total;
	}

	public double getTotalTCHRAllocationUnits() {

		double total = this.getTCHRAllocationUnits() + this.getTotalExtraTCHRAllocationUnits();

		return total;
	}

	public double getTotalTLAAllocationUnits() {

		double total = this.getTLAUnits() + this.getTotalExtraTLAAllocationUnits();

		return total;
	}

	public double getTotalSAAllocationHours() {

		double total = this.getStudentAssistantHours() + this.getTotalExtraSAAllocationHours();

		return total;
	}

	public double getTotalStaffingUnits() {

		double total = this.getTotalPermanentPositionUnits() + this.getTotalVacantPositionUnits();

		return total;
	}

	public double getAllocationStaffingDifference() {

		return this.getTotalTCHRAllocationUnits() - this.getTotalStaffingUnits();
	}

	public String toXML() {

		DecimalFormat twoDForm = new DecimalFormat("#.##");
		StringBuffer buf = new StringBuffer();

		buf.append("<TEACHER-ALLOCATION-BEAN ALLOCATION-ID=\"" + this.allocationId + "\" LOCATION-ID=\""
				+ this.getLocation().getLocationId() + "\" SCHOOL-YEAR=\"" + this.schoolYear + "\" REGULAR-UNITS=\""
				+ this.regularUnits + "\" ADMINISTRATIVE-UNITS=\"" + this.administrativeUnits + "\" GUIDANCE-UNITS=\""
				+ this.guidanceUnits + "\" SPECIALIST-UNITS=\"" + this.specialistUnits + "\" LRT-UNITS=\"" + this.lrtUnits
				+ "\" IRT1-UNITS=\"" + this.irt1Units + "\" IRT2-UNITS=\"" + this.irt2Units + "\" OTHER-UNITS=\""
				+ this.otherUnits + "\" TLA-UNITS=\"" + this.tlaUnits + "\" STUDENT-ASSISTANT-HOURS=\""
				+ this.studentAssistantHours + "\" READING-SPECIALIST-UNITS=\"" + this.readingSpecialistUnits
				+ "\" SCHOOL-ALLOCATIONS=\"" + Double.valueOf(twoDForm.format(this.getTCHRAllocationUnits()))
				+ "\" TOTAL-TCHR-ALLOCATIONS=\"" + Double.valueOf(twoDForm.format(this.getTotalTCHRAllocationUnits()))
				+ "\" TOTAL-TLA-ALLOCATIONS=\"" + Double.valueOf(twoDForm.format(this.getTotalTLAAllocationUnits()))
				+ "\" TOTAL-SA-ALLOCATIONS=\"" + Double.valueOf(twoDForm.format(this.getTotalSAAllocationHours()))
				+ "\" TOTAL-STAFFING-UNITS=\"" + Double.valueOf(twoDForm.format(this.getTotalStaffingUnits()))
				+ "\" OUTSTANDING-ASSIGNMENT-UNITS=\"" + Double.valueOf(twoDForm.format(this.getAllocationStaffingDifference()))
				+ "\" PUBLISHED=\"" + this.published + "\" ENABLED=\"" + this.enabled + "\">");

		if ((this.extras != null) && (this.extras.size() > 0)) {
			buf.append("<TEACHER-ALLOCATION-EXTRA-BEANS COUNT=\"" + this.extras.size() + "\" TOTAL-ALLOCATIONS=\""
					+ Double.valueOf(twoDForm.format(this.getTotalExtraTCHRAllocationUnits())) + "\">");
			for (TeacherAllocationExtraBean extra : this.extras)
				buf.append(extra.toXML());
			buf.append("</TEACHER-ALLOCATION-EXTRA-BEANS>");
		}

		if ((this.permanentPositions != null) && (this.permanentPositions.size() > 0)) {
			buf.append("<TEACHER-ALLOCATION-PERMANENT-POSITION-BEANS COUNT=\"" + this.permanentPositions.size()
					+ "\" TOTAL-ALLOCATIONS=\"" + Double.valueOf(twoDForm.format(this.getTotalPermanentPositionUnits())) + "\">");
			for (TeacherAllocationPermanentPositionBean position : this.permanentPositions)
				buf.append(position.toXML());
			buf.append("</TEACHER-ALLOCATION-PERMANENT-POSITION-BEANS>");
		}

		if ((this.vacantPositions != null) && (this.vacantPositions.size() > 0)) {
			buf.append("<TEACHER-ALLOCATION-VACANT-POSITION-BEANS COUNT=\"" + this.vacantPositions.size()
					+ "\" TOTAL-ALLOCATIONS=\"" + Double.valueOf(twoDForm.format(this.getTotalVacantPositionUnits())) + "\">");
			for (TeacherAllocationVacantPositionBean position : this.vacantPositions)
				buf.append(position.toXML());
			buf.append("</TEACHER-ALLOCATION-VACANT-POSITION-BEANS>");
		}

		if ((this.redundantPositions != null) && (this.redundantPositions.size() > 0)) {
			buf.append("<TEACHER-ALLOCATION-REDUNDANT-POSITION-BEANS COUNT=\"" + this.redundantPositions.size()
					+ "\" TOTAL-ALLOCATIONS=\"" + Double.valueOf(twoDForm.format(this.getTotalRedundantPositionUnits())) + "\">");
			for (TeacherAllocationRedundantPositionBean position : this.redundantPositions)
				buf.append(position.toXML());
			buf.append("</TEACHER-ALLOCATION-REDUNDANT-POSITION-BEANS>");
		}

		buf.append("</TEACHER-ALLOCATION-BEAN>");

		return buf.toString();
	}

}
