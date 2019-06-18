package com.esdnl.personnel.jobs.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import com.awsd.mail.bean.AlertBean;
import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.bean.EmailException;
import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.PersonnelException;
import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.awsd.school.SchoolException;
import com.awsd.school.Subject;
import com.awsd.school.bean.RegionBean;
import com.awsd.school.bean.RegionException;
import com.esdnl.personnel.jobs.constants.TrainingMethodConstant;
import com.nlesd.school.bean.SchoolZoneBean;
import com.nlesd.school.service.SchoolZoneService;

public class JobOpportunityAssignmentBean implements Serializable {

	private static final long serialVersionUID = -6033649967299517039L;
	private int assign_id;
	private String comp_num;
	private int location;
	private double units;

	private Vector<String> skills;
	private Vector<AssignmentEducationBean> edureqs;
	private Vector<AssignmentMajorMinorBean> majors;
	private Vector<Subject> subjects;
	private Vector<TrainingMethodConstant> trnmthds;

	private static HashMap<Integer, School> school_names = null;

	// optomize school name lookup
	static {
		School tmp = null;
		school_names = new HashMap<Integer, School>();
		try {
			Iterator<School> iter = SchoolDB.getSchools().iterator();
			while (iter.hasNext()) {
				tmp = (School) iter.next();
				school_names.put(new Integer(tmp.getSchoolID()), tmp);
			}
		}
		catch (SchoolException e) {
			e.printStackTrace(System.err);
		}
	}

	public JobOpportunityAssignmentBean(String comp_num, int location, double units) {

		this(-1, comp_num, location, units);
	}

	public JobOpportunityAssignmentBean(int assign_id, String comp_num, int location, double units) {

		this.assign_id = assign_id;
		this.comp_num = comp_num;
		this.location = location;
		this.units = units;

		skills = new Vector<String>();
		edureqs = new Vector<AssignmentEducationBean>();
		majors = new Vector<AssignmentMajorMinorBean>();
		subjects = new Vector<Subject>();
		trnmthds = new Vector<TrainingMethodConstant>();
	}

	public int getAssignmentId() {

		return this.assign_id;
	}

	public void setAssignmentId(int assign_id) {

		this.assign_id = assign_id;
	}

	public String getCompetitionNumber() {

		return this.comp_num;
	}

	public int getLocation() {

		return this.location;
	}

	public String getLocationText() {

		String txt = null;

		switch (this.location) {
		case -3000:
			txt = "Central Regional Office";
			break;
		case -2000:
			txt = "Western Regional Office";
			break;
		case -1000:
			txt = "Labrador Regional Office";
			break;
		case -999:
			txt = "District Office";
			break;
		case -998:
			txt = "Eastern Regional Office";
			break;
		case -100:
			txt = "Avalon East Region";
			break;
		case -200:
			txt = "Avalon West Region";
			break;
		case -300:
			txt = "Burin Region";
			break;
		case -400:
			txt = "Vista Region";
			break;
		case 0:
			txt = "";
			break;
		default:
			School s = ((School) school_names.get(new Integer(this.location)));
			if (s != null)
				txt = ((School) school_names.get(new Integer(this.location))).getSchoolName();
			else
				txt = "UNKNOWN SCHOOL [id=" + this.location + "]";

			break;
		}

		return txt;
	}

	public SchoolZoneBean getLocationZone() {

		SchoolZoneBean zone = null;

		if (this.getLocation() < 0) {
			try {
				switch (this.location) {
				case -3000:
					//txt = "Central Regional Office";
					zone = SchoolZoneService.getSchoolZoneBean(2);
					break;
				case -2000:
					//txt = "Western Regional Office";
					zone = SchoolZoneService.getSchoolZoneBean(3);
					break;
				case -1000:
					//txt = "Labrador Regional Office";
					zone = SchoolZoneService.getSchoolZoneBean(4);
					break;
				case -999:
					//txt = "District Office";
					zone = SchoolZoneService.getSchoolZoneBean(1);
					break;
				case -998:
					//txt = "Eastern Regional Office";
					zone = SchoolZoneService.getSchoolZoneBean(1);
					break;
				case -100:
					//txt = "Avalon East Region";
					zone = SchoolZoneService.getSchoolZoneBean(1);
					break;
				case -200:
					//txt = "Avalon West Region";
					zone = SchoolZoneService.getSchoolZoneBean(1);
					break;
				case -300:
					//txt = "Burin Region";
					zone = SchoolZoneService.getSchoolZoneBean(1);
					break;
				case -400:
					//txt = "Vista Region";
					zone = SchoolZoneService.getSchoolZoneBean(1);
					break;
				}
			}
			catch (SchoolException e) {
				try {
					(new AlertBean(e)).send();
				}
				catch (EmailException e1) {}

				zone = null;
			}
		}
		else {
			School s = ((School) school_names.get(new Integer(this.location)));

			if (s != null) {
				try {
					zone = s.getZone();
				}
				catch (SchoolException e) {
					try {
						(new AlertBean(e)).send();
					}
					catch (EmailException e1) {}
					zone = null;
				}
			}
		}

		if (zone == null) {
			try {
				EmailBean email = new EmailBean();
				email.setFrom("error@nlesd.ca");
				email.setTo(PersonnelDB.getPersonnelByRole("ADMINISTRATOR"));
				if(this.comp_num != null){
					email.setSubject("Member Services Processing Error Comp Number:" + this.comp_num);
				}else{
					email.setSubject("Member Services Processing Error");
				}
				
				
				email.setBody("School Zone not found for location ID [" + this.location + "]");

				email.send();
			}
			catch (PersonnelException e) {
				try {
					(new AlertBean(e)).send();
				}
				catch (EmailException e1) {}
			}
			catch (EmailException e) {
				try {
					(new AlertBean(e)).send();
				}
				catch (EmailException e1) {}
			}
		}

		return zone;
	}

	public String getRegionText() {

		String txt = "";

		if (getLocation() < 0)
			txt = getLocationText();
		else {
			try {
				School s = (School) school_names.get(new Integer(this.location));

				SchoolZoneBean zone = null;
				try {
					zone = s.getZone();

					if (zone != null) {
						txt += StringUtils.capitalize(zone.getZoneName()) + " Region - ";
					}
				}
				catch (SchoolException e) {
					zone = null;
				}

				RegionBean region = null;
				try {
					region = s.getRegion();

					if (region != null) {
						txt += region.getName() + " Zone";
					}
				}
				catch (RegionException e) {
					region = null;
				}

				if (StringUtils.isEmpty(txt)) {
					txt = "UNKNOWN REGION";
				}
			}
			catch (NullPointerException e) {
				txt = "N/A";
			}
		}

		return txt;
	}

	public double getUnits() {

		return this.units;
	}

	public void addRequiredSkill(String skill) {

		this.skills.add(skill);
	}

	public String[] getRequiredSkills() {

		return (String[]) skills.toArray(new String[0]);
	}

	public void addRequiredEducation(AssignmentEducationBean edu) {

		this.edureqs.add(edu);
	}

	public AssignmentEducationBean[] getRequiredEducation() {

		return (AssignmentEducationBean[]) this.edureqs.toArray(new AssignmentEducationBean[0]);
	}

	public void addRequiredMajor(AssignmentMajorMinorBean major) {

		this.majors.add(major);
	}

	public AssignmentMajorMinorBean[] getRequiredMajors() {

		return (AssignmentMajorMinorBean[]) this.majors.toArray(new AssignmentMajorMinorBean[0]);
	}

	public void addRequiredSubject(Subject subject) {

		this.subjects.add(subject);
	}

	public Subject[] getRequiredSubjects() {

		return (Subject[]) this.subjects.toArray(new Subject[0]);
	}

	public void addRequiredTrainingMethod(TrainingMethodConstant method) {

		this.trnmthds.add(method);
	}

	public TrainingMethodConstant[] getRequriedTrainingMethods() {

		return (TrainingMethodConstant[]) this.trnmthds.toArray(new TrainingMethodConstant[0]);
	}
}