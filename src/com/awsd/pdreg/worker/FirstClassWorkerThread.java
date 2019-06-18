package com.awsd.pdreg.worker;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import com.awsd.mail.bean.EmailBean;
import com.awsd.pdreg.Event;
import com.awsd.pdreg.RegisteredPersonnel;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.school.School;
import com.awsd.security.crypto.PasswordEncryption;
import com.esdnl.mrs.MaintenanceRequestDB;
import com.esdnl.util.StringUtils;

public class FirstClassWorkerThread extends Thread {

	public static final int DELETE_EVENT = 1;
	public static final int DEREGISTER_EVENT = 2;
	public static final int DISTRICT_EVENT = 3;
	public static final int REGISTER_EVENT = 4;
	public static final int MODIFY_EVENT = 5;
	public static final int SCHEDULE_EVENT = 6;
	public static final int REQUEST_SCHOOL_PD_EVENT = 7;
	public static final int REQUEST_SCHOOL_CLOSEOUT_EVENT = 8;
	public static final int REQUEST_APPROVED = 9;
	public static final int REQUEST_DECLINED = 10;

	private Personnel p = null;
	private Event[] events = null;
	private int type;
	private File[] files = null;

	private String additional_info;

	public FirstClassWorkerThread(Personnel p, Event events[], int type) {

		this.p = p;
		this.events = events;
		this.type = type;
		this.files = null;
		this.additional_info = null;
	}

	public FirstClassWorkerThread(Personnel p, Event events[], int type, String additional_info) {

		this.p = p;
		this.events = events;
		this.type = type;
		this.files = null;
		this.additional_info = additional_info;
	}

	public FirstClassWorkerThread(Personnel p, Event events[], int type, File[] files) {

		this.p = p;
		this.events = events;
		this.type = type;
		this.files = files;
		this.additional_info = null;
	}

	public void run() {

		EmailBean email = null;

		Personnel tmp = null;

		Iterator iter = null;

		try {
			switch (this.type) {
			case FirstClassWorkerThread.SCHEDULE_EVENT:
				email = new EmailBean();
				email.setTo(new String[] {
						events[0].getScheduler().getEmailAddress()
				});
				email.setSubject(events[0].getEventName() + ": SCHEDULED EVENT CONFIRMATION");
				email.setBody(
						"This is to confirm you have scheduled the event below. " + "Please do not reply to this message.<br><br>"
								+ StringUtils.encodeHTML(events[0].toString()) + "<br><br>Member Services");
				email.setFrom("ms@nlesd.ca");
				email.send();

				break;
			case FirstClassWorkerThread.DELETE_EVENT:

				email = new EmailBean();
				email.setTo(new String[] {
						events[0].getScheduler().getEmailAddress()
				});
				email.setSubject(events[0].getEventName() + ": EVENT CANCELLED");
				email.setBody("This is to confirm you have cancelled the event below. "
						+ "Please do not respond to this message.<br><br>Original Event:<br>"
						+ StringUtils.encodeHTML(events[0].toString()) + "<br><br>Member Services");
				email.setFrom("ms@nlesd.ca");
				email.send();

				iter = (new RegisteredPersonnel(this.events[0])).entrySet().iterator();
				while (iter.hasNext()) {
					tmp = (Personnel) (((Map.Entry) iter.next()).getValue());

					email = new EmailBean();
					email.setTo(new String[] {
							tmp.getEmailAddress()
					});
					email.setSubject(events[0].getEventName() + ": EVENT CANCELLED");
					email.setBody(
							"There has been a cancellation of an event you are registered for, and you have automatically been deregistered. "
									+ "See details below. Please do not respond to this message.<br><br>" + "Original Event:<br>"
									+ StringUtils.encodeHTML(events[0].toString()) + "<br><br>Member Services");
					email.setFrom("ms@nlesd.ca");
					email.send();

				}
				break;
			case FirstClassWorkerThread.DEREGISTER_EVENT:
				email = new EmailBean();
				email.setTo(new String[] {
						p.getEmailAddress()
				});
				email.setSubject(events[0].getEventName() + ": EVENT DEREGISTRATION");
				email.setBody("This is to confirm your deregistration from the event below."
						+ "Please do not respond to this message.<br><br>" + StringUtils.encodeHTML(events[0].toString())
						+ "<br><br>Member Services");
				email.setFrom("ms@nlesd.ca");
				email.send();

				ArrayList<Personnel> to = new ArrayList<Personnel>();

				to.add(events[0].getScheduler());

				if (p.getSchool() != null) {
					School s = p.getSchool();

					if (s.getSchoolPrincipal() != null) {
						to.add(s.getSchoolPrincipal());
					}

					Personnel ap[] = s.getAssistantPrincipals();
					if ((ap != null) && (ap.length > 0)) {
						to.addAll(Arrays.asList(ap));
					}
				}
				else if (p.getSupervisor() != null) {
					to.add(p.getSupervisor());
				}

				email = new EmailBean();
				email.setTo(to);
				email.setSubject(p.getFullNameReverse() + " DEREGISTERED from - " + events[0].getEventName());
				email.setBody("This is to inform you that " + p.getFullNameReverse()
						+ " has been deregistered form the event below. Please do not reply to this message.<br><br>"
						+ StringUtils.encodeHTML(events[0].toString()) + "<br><br>Member Services");
				email.setFrom("ms@nlesd.ca");
				email.send();

				break;
			case FirstClassWorkerThread.REGISTER_EVENT:
				email = new EmailBean();
				email.setTo(new String[] {
						p.getEmailAddress()
				});
				email.setSubject(events[0].getEventName() + ": EVENT REGISTRATION");
				email.setBody("This is to confirm your registration in the event below. "
						+ "Please do not respond to this message.<br><br>" + StringUtils.encodeHTML(events[0].toString())
						+ "<br><br>Member Services");
				email.setFrom("ms@nlesd.ca");
				email.send();

				ArrayList<Personnel> to1 = new ArrayList<Personnel>();

				to1.add(events[0].getScheduler());

				if (p.getSchool() != null) {
					School s = p.getSchool();

					if (s.getSchoolPrincipal() != null) {
						to1.add(s.getSchoolPrincipal());
					}

					Personnel ap[] = s.getAssistantPrincipals();
					if ((ap != null) && (ap.length > 0)) {
						to1.addAll(Arrays.asList(ap));
					}
				}
				else if (p.getSupervisor() != null) {
					to1.add(p.getSupervisor());
				}

				email = new EmailBean();
				email.setTo(to1);
				email.setSubject(p.getFullNameReverse() + " REGISTERED for - " + events[0].getEventName());
				email.setBody("This is to inform you that " + p.getFullNameReverse()
						+ " has registered for the event below. Please do not reply to this message.<br><br>"
						+ StringUtils.encodeHTML(events[0].toString()) + "<br><br>Member Services");
				email.setFrom("ms@nlesd.ca");
				email.send();
				break;
			case FirstClassWorkerThread.MODIFY_EVENT:
				email = new EmailBean();
				email.setTo(new String[] {
						events[0].getScheduler().getEmailAddress()
				});
				email.setSubject(events[0].getEventName() + ": EVENT MODIFICATION");
				email.setBody("This is to confirm you have modified the event details of event below. "
						+ "Please do not respond to this message.<br><br>Original Event:<br>"
						+ StringUtils.encodeHTML(events[0].toString()) + "<br><br>Modified Event:<br>"
						+ StringUtils.encodeHTML(events[1].toString()) + "<br><br>Member Services");
				email.setFrom("ms@nlesd.ca");
				email.send();

				iter = (new RegisteredPersonnel(this.events[0])).entrySet().iterator();
				while (iter.hasNext()) {
					tmp = (Personnel) (((Map.Entry) iter.next()).getValue());

					email = new EmailBean();
					email.setTo(new String[] {
							tmp.getEmailAddress()
					});
					email.setSubject(events[0].getEventName() + ": EVENT MODIFICATION");
					email.setBody("There has been a change in the event details of an event you are registered for. "
							+ "See details below, and deregister if necessary. Please do not respond to this message.<br><br>"
							+ "Original Event:<br>" + StringUtils.encodeHTML(events[0].toString()) + "<br><br>Modified Event:<br>"
							+ StringUtils.encodeHTML(events[1].toString()) + "<br><br>Member Services");
					email.setFrom("ms@nlesd.ca");
					email.send();

				}
				break;
			case FirstClassWorkerThread.REQUEST_SCHOOL_PD_EVENT:
				tmp = events[0].getScheduler().getSchool().getSchoolFamily().getProgramSpecialist();

				email = new EmailBean();
				email.setTo(new String[] {
						tmp.getEmailAddress()
				});
				email.setSubject("SCHOOL PD REQUEST - " + events[0].getScheduler().getSchool().getSchoolName());
				email.setBody("This School PD Request has been sent to you by " + events[0].getScheduler().getFullNameReverse()
						+ "<br><br>" + StringUtils.encodeHTML(events[0].toString())
						+ "<br><br>To APPROVE this request, please click "
						+ "<a href='http://www.nlesd.ca/MemberServices/PDReg/schoolPDRequestAdmin.html?u=" + tmp.getUserName()
						+ "&p=" + PasswordEncryption.encrypt(tmp.getPassword()) + "&op=approve&id=" + events[0].getEventID()
						+ "' target='_blank'>HERE</a><br><br>" + "To DECLINE this request, please click "
						+ "<a href='http://www.nlesd.ca/MemberServices/PDReg/schoolPDRequestAdmin.html?u=" + tmp.getUserName()
						+ "&p=" + PasswordEncryption.encrypt(tmp.getPassword()) + "&op=decline&id=" + events[0].getEventID()
						+ "' target='_blank'>HERE</a><br><br>" + "PLEASE DO NOT RESPOND TO THIS MESSAGE. THANK YOU.<br><br>"
						+ "Member Services");
				email.setFrom("ms@nlesd.ca");
				email.setAttachments(files);
				email.send();

				break;
			case FirstClassWorkerThread.REQUEST_APPROVED:
				tmp = events[0].getScheduler().getSchool().getSchoolFamily().getProgramSpecialist();

				email = new EmailBean();
				email.setTo(new String[] {
						p.getEmailAddress(), tmp.getEmailAddress()
				});
				email.setSubject("School PD Request APPROVED - " + events[0].getScheduler().getSchool().getSchoolName());
				email.setBody("The following School PD Request as been approved by " + tmp.getFullNameReverse() + "<br><br>"
						+ StringUtils.encodeHTML(events[0].toString())
						+ "<BR><BR>PLEASE DO NOT RESPOND TO THIS MESSAGE. THANK YOU.<br><br>Member Services");
				email.setFrom("ms@nlesd.ca");
				email.send();

				Personnel[] monitors = PersonnelDB.getPersonnelByRole("SCHOOL PD APPROVAL MONITIOR");
				if (monitors != null && monitors.length > 0) {
					email = new EmailBean();
					email.setTo(monitors);
					email.setSubject("School PD Scheduled for - " + events[0].getScheduler().getSchool().getSchoolName());
					email.setBody("The following School PD Request as been scheduled;<br><br>"
							+ StringUtils.encodeHTML(events[0].toString())
							+ "<BR><BR>PLEASE DO NOT RESPOND TO THIS MESSAGE. THANK YOU.<br><br>Member Services");
					email.setFrom("ms@nlesd.ca");
					email.send();
				}

				// also send email to rom and maintenance supervisor
				Vector<Personnel> sups = MaintenanceRequestDB.getSchoolSupervisor(events[0].getScheduler().getSchool());
				if (sups.size() > 0) {
					email = new EmailBean();
					email.setTo((Personnel[]) sups.toArray(new Personnel[0]));
					email.setSubject("School PD Scheduled for - " + events[0].getScheduler().getSchool().getSchoolName());
					email.setBody("The following School PD Request as been scheduled;<br><br>"
							+ StringUtils.encodeHTML(events[0].toString())
							+ "<BR><BR>PLEASE DO NOT RESPOND TO THIS MESSAGE. THANK YOU.<br><br>Member Services");
					email.setFrom("ms@nlesd.ca");
					email.send();
				}
				break;
			case FirstClassWorkerThread.REQUEST_DECLINED:
				tmp = events[0].getScheduler().getSchool().getSchoolFamily().getProgramSpecialist();

				email = new EmailBean();
				email.setTo(new String[] {
						p.getEmailAddress(), tmp.getEmailAddress()
				});
				email.setSubject("School PD Request DECLINED - " + events[0].getScheduler().getSchool().getSchoolName());
				email.setBody("The following School PD Request as been declined by " + tmp.getFullNameReverse() + "<br><br>"
						+ StringUtils.encodeHTML(events[0].toString())
						+ (!StringUtils.isEmpty(this.additional_info) ? "<br><br>" + this.additional_info : "")
						+ "<BR><BR>PLEASE DO NOT RESPOND TO THIS MESSAGE. THANK YOU.<br><br>Member Services");
				email.setFrom("ms@nlesd.ca");
				email.send();

				break;
			}
		}
		catch (Exception e) {
			System.err.println(e);
		}
	}
}