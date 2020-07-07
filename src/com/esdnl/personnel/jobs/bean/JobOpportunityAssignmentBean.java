package com.esdnl.personnel.jobs.bean;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

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
		//need entries for support staff depots
		case 832: 
			txt="Western Baie Verte Bus Depot";
			break;
		case 833: 
			txt="Central Fogo Bus Depot";
			break;
		case 834: 
			txt="Central Gander Bus Depot";
			break;
		case 835: 
			txt="Central GFW Bus Depot";
			break;
		case 836: 
			txt="Central Lewisporte Bus Depot";
			break;				
		case 837: 
			txt="Central Summerford Bus Depot";
			break;
		case 838: 
			txt="Western Corner Brook Bus Depot";
			break;
		case 839: 
			txt="Labrador HVGB Bus Depot";
			break;
		case 840: 
			txt="Labrador Wabush Bus Depot";
			break;
		case 841: 
			txt="Labrador City/Wabush Area";
			break;
		case 842: 
			txt="Vista Depot Satellite Office";
			break;
		case 843: 
			txt="Central Botwood Bus Depot";
			break;	
		case 285: 
			txt="Burin Bus Depot";
			break;			
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
			txt = "Avalon Regional Office";
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
			txt = "District Office";
			break;
		default:
			School s = ((School) school_names.get(new Integer(this.location)));
			if (s != null) {
				txt = ((School) school_names.get(new Integer(this.location))).getSchoolName();
			}
			else {
				txt = "UNKNOWN SCHOOL [id=" + this.location + "]";
			}

			break;
		}

		return txt;
	}

	public School getSchool() {

		return ((School) school_names.get(new Integer(this.location)));
	}

	public SchoolZoneBean getLocationZone() {

		SchoolZoneBean zone = null;

		if (this.getLocation() <= 0) {
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
				case 0:
					//txt = "District Office";
					zone = SchoolZoneService.getSchoolZoneBean(1);
					break;
				case -998:
					//txt = "Avalon Regional Office";
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
			//new locatons added for support staff hiring depots
			if(this.location == 832 || this.location == 838 ) {
				try {
					zone = SchoolZoneService.getSchoolZoneBean(3);
				} catch (SchoolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(this.location == 833 || this.location == 834 || this.location == 835 || this.location == 836 || this.location == 837 || this.location == 285 ||this.location == 842 ||this.location == 843 ) {
				try {
					zone = SchoolZoneService.getSchoolZoneBean(2);
				} catch (SchoolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(this.location == 839 || this.location == 840 || this.location == 841) {
				try {
					zone = SchoolZoneService.getSchoolZoneBean(4);
				} catch (SchoolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
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
			
		}

		if (zone == null) {
			try {
				EmailBean email = new EmailBean();
				email.setFrom("error@nlesd.ca");
				email.setTo(PersonnelDB.getPersonnelByRole("ADMINISTRATOR"));
				if (this.comp_num != null) {
					email.setSubject("Member Services Processing Error Comp Number:" + this.comp_num);
				}
				else {
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

	public void addRequiredEducation(Collection<AssignmentEducationBean> edus) {

		this.edureqs.addAll(edus);
	}

	public AssignmentEducationBean[] getRequiredEducation() {

		return (AssignmentEducationBean[]) this.edureqs.toArray(new AssignmentEducationBean[0]);
	}

	public int getRequiredEducationSize() {

		return this.edureqs.size();
	}

	public void addRequiredMajor(AssignmentMajorMinorBean major) {

		this.majors.add(major);
	}

	public void addRequiredMajor(Collection<AssignmentMajorMinorBean> majors) {

		this.majors.addAll(majors);
	}

	@Deprecated
	public AssignmentMajorMinorBean[] getRequiredMajors() {

		return (AssignmentMajorMinorBean[]) this.majors.toArray(new AssignmentMajorMinorBean[0]);
	}

	@Deprecated
	public int getRequiredMajorsSize() {

		return this.majors.size();
	}

	public int getRequriedMajorsOnlySize() {

		return (int) this.majors.stream().filter(p -> p.getMajorId() > 0).count();
	}

	public List<AssignmentMajorMinorBean> getRequiredMajorsOnly() {

		return (List<AssignmentMajorMinorBean>) this.majors.stream().filter(p -> p.getMajorId() > 0).collect(
				Collectors.toList());
	}

	public int getRequriedMinorsOnlySize() {

		return (int) this.majors.stream().filter(p -> p.getMinorId() > 0).count();
	}

	public List<AssignmentMajorMinorBean> getRequiredMinorsOnly() {

		return (List<AssignmentMajorMinorBean>) this.majors.stream().filter(p -> p.getMinorId() > 0).collect(
				Collectors.toList());
	}

	public void addRequiredSubject(Subject subject) {

		this.subjects.add(subject);
	}

	public Subject[] getRequiredSubjects() {

		return (Subject[]) this.subjects.toArray(new Subject[0]);
	}

	public int getRequiredSubjectsSize() {

		return this.subjects.size();
	}

	public void addRequiredTrainingMethod(TrainingMethodConstant method) {

		this.trnmthds.add(method);
	}

	public void addRequiredTrainingMethod(Collection<AssignmentTrainingMethodBean> methods) {

		for (AssignmentTrainingMethodBean b : methods) {
			addRequiredTrainingMethod(b.getTrainingMethod());
		}
	}

	public TrainingMethodConstant[] getRequriedTrainingMethods() {

		return (TrainingMethodConstant[]) this.trnmthds.toArray(new TrainingMethodConstant[0]);
	}

	public int getRequriedTrainingMethodsSize() {

		return this.trnmthds.size();
	}
}