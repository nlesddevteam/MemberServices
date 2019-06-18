package com.esdnl.fund3.dao;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import org.apache.commons.lang.StringUtils;
import com.awsd.mail.bean.EmailBean;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.esdnl.fund3.bean.Fund3Exception;
import com.esdnl.fund3.bean.ProjectBean;
import com.esdnl.velocity.VelocityUtils;
public class Fund3EmailManager {
	public static void sendAddProjectEmails(ArrayList<String> maillist,ProjectBean pb) throws Fund3Exception {
		try {
				//loop through list and send emails
				for(String s : maillist)
				{
					ArrayList<Personnel> sendTo = null;
					sendTo = new ArrayList<Personnel>();
					String regionalemail="FUND3 REGIONAL MANAGER " + s;
					sendTo.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole(regionalemail)));
					for (Personnel p : sendTo) {
						EmailBean ebean = new EmailBean();
						HashMap<String, Object> model = new HashMap<String, Object>();
						// set values to be used in template
						model.put("project",pb.getProjectName() + "(" + pb.getProjectNumber() + ")" );
						ebean.setSubject("REGIONAL " + s + "-New project added to Fund3 system: " + pb.getProjectName() + "(" + pb.getProjectNumber() + ")");
						ebean.setTo(p.getEmailAddress());
						ebean.setFrom("fund3@nlesd.ca");
						ebean.setBody(VelocityUtils.mergeTemplateIntoString("fund3/fund3_new_project_region.vm", model));
						ebean.send();
					}
				}
		}
		catch (Exception e) {
			System.err.println("void sendAddProjectEmails(ArrayList<String> maillist) " + e);
			throw new Fund3Exception("Can not send project emails" + e);
		}
}
	public static void sendAddProjectEmailsApprovals(ArrayList<String> maillist,ProjectBean pb) throws Fund3Exception {
		try {
				//all regions selected, send to provincal ad
				if(maillist.size() > 1)
				{
					ArrayList<Personnel> sendTo = null;
					sendTo = new ArrayList<Personnel>();
					String regionalemail="FUND3 PROVINCIAL ADP ";
					sendTo.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole(regionalemail)));
					for (Personnel p : sendTo) {
						EmailBean ebean = new EmailBean();
						HashMap<String, Object> model = new HashMap<String, Object>();
						// set values to be used in template
						model.put("project",pb.getProjectName() + "(" + pb.getProjectNumber() + ")" );
						ebean.setSubject("New project awaiting approval in Fund3 system: " + pb.getProjectName() + "(" + pb.getProjectNumber() + ")");
						ebean.setTo(p.getEmailAddress());
						ebean.setFrom("fund3@nlesd.ca");
						ebean.setBody(VelocityUtils.mergeTemplateIntoString("fund3/fund3_new_project_region_app.vm", model));
						ebean.send();
					}
				}else{
				//loop through list and send emails
					//should only be one
					for(String s : maillist)
					{
						ArrayList<Personnel> sendTo = null;
						sendTo = new ArrayList<Personnel>();
						String regionalemail="FUND3 REGIONAL ADP " + s;
						sendTo.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole(regionalemail)));
						for (Personnel p : sendTo) {
							EmailBean ebean = new EmailBean();
							HashMap<String, Object> model = new HashMap<String, Object>();
							// set values to be used in template
							model.put("project",pb.getProjectName() + "(" + pb.getProjectNumber() + ")" );
							ebean.setSubject("New project awaiting approval in Fund3 system: " + pb.getProjectName() + "(" + pb.getProjectNumber() + ")");
							ebean.setTo(p.getEmailAddress());
							ebean.setFrom("fund3@nlesd.ca");
							ebean.setBody(VelocityUtils.mergeTemplateIntoString("fund3/fund3_new_project_region_app.vm", model));
							ebean.send();
						}
					}
				}
		}
		catch (Exception e) {
			System.err.println("sendAddProjectEmailsApprovals(ArrayList<String> maillist,ProjectBean pb) " + e);
			throw new Fund3Exception("Can not send project emails" + e);
		}
	}
	public static void sendAddProjectEmailSeniorAcct(ProjectBean pb) throws Fund3Exception {
		try {
					ArrayList<Personnel> sendTo = null;
					sendTo = new ArrayList<Personnel>();
					String regionalemail="FUND3 SENIOR ACCOUNTANT";
					sendTo.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole(regionalemail)));
					for (Personnel p : sendTo) {
						EmailBean ebean = new EmailBean();
						HashMap<String, Object> model = new HashMap<String, Object>();
						// set values to be used in template
						model.put("project",pb.getProjectName() + "(" + pb.getProjectNumber() + ")" );
						ebean.setSubject("Senior " + "New project added to Fund3 system: " + pb.getProjectName() + "(" + pb.getProjectNumber() + ")");
						ebean.setTo(p.getEmailAddress());
						ebean.setFrom("fund3@nlesd.ca");
						ebean.setBody(VelocityUtils.mergeTemplateIntoString("fund3/fund3_new_project_region.vm", model));
						ebean.send();
					}
		}
		catch (Exception e) {
			System.err.println("void sendAddProjectEmailSeniorAcct(ProjectBean pb)) " + e);
			throw new Fund3Exception("Can not send project emails" + e);
		}
	}
	public static void sendApprovedProjectEmailSeniorAcct(ProjectBean pb) throws Fund3Exception {
		try {
					ArrayList<Personnel> sendTo = null;
					sendTo = new ArrayList<Personnel>();
					String regionalemail="FUND3 SENIOR ACCOUNTANT";
					sendTo.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole(regionalemail)));
					for (Personnel p : sendTo) {
						EmailBean ebean = new EmailBean();
						HashMap<String, Object> model = new HashMap<String, Object>();
						// set values to be used in template
						model.put("project",pb.getProjectName() + "(" + pb.getProjectNumber() + ")" );
						ebean.setSubject("Senior " + "New project added to Fund3 system: " + pb.getProjectName() + "(" + pb.getProjectNumber() + ")");
						ebean.setTo(p.getEmailAddress());
						ebean.setFrom("fund3@nlesd.ca");
						ebean.setBody(VelocityUtils.mergeTemplateIntoString("fund3/fund3_project_approved.vm", model));
						ebean.send();
					}
		}
		catch (Exception e) {
			System.err.println("void sendApprovedProjectEmailSeniorAcct(ProjectBean pb)  " + e);
			throw new Fund3Exception("Can not send project emails" + e);
		}
	}	
	public static void sendApprovedProjectEmails(ArrayList<String> maillist,ProjectBean pb) throws Fund3Exception {
		try {
				//loop through list and send emails
				for(String s : maillist)
				{
					ArrayList<Personnel> sendTo = null;
					sendTo = new ArrayList<Personnel>();
					String regionalemail="FUND3 REGIONAL MANAGER " + s;
					sendTo.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole(regionalemail)));
					for (Personnel p : sendTo) {
						EmailBean ebean = new EmailBean();
						HashMap<String, Object> model = new HashMap<String, Object>();
						// set values to be used in template
						model.put("project",pb.getProjectName() + "(" + pb.getProjectNumber() + ")" );
						ebean.setSubject("REGIONAL " + s + "Project Approved in the Fund3 system: " + pb.getProjectName() + "(" + pb.getProjectNumber() + ")");
						ebean.setTo(p.getEmailAddress());
						ebean.setFrom("fund3@nlesd.ca");
						ebean.setBody(VelocityUtils.mergeTemplateIntoString("fund3/fund3_project_approved.vm", model));
						ebean.send();
					}
				}
		}
		catch (Exception e) {
			System.err.println("void sendApprovedProjectEmails(ArrayList<String> maillist,ProjectBean pb) " + e);
			throw new Fund3Exception("Can not send project emails" + e);
		}
	}
	public static void sendNewDocumentEmails(ArrayList<String> maillist,ProjectBean pb,String docname) throws Fund3Exception {
		try {
				String docpath="/../MemberServices/Fund3/documents/";
				//loop through list and send emails
				for(String s : maillist)
				{
					ArrayList<Personnel> sendTo = null;
					sendTo = new ArrayList<Personnel>();
					String regionalemail="FUND3 REGIONAL MANAGER " + s;
					sendTo.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole(regionalemail)));
					for (Personnel p : sendTo) {
						EmailBean ebean = new EmailBean();
						HashMap<String, Object> model = new HashMap<String, Object>();
						// set values to be used in template
						model.put("project",pb.getProjectName() + "(" + pb.getProjectNumber() + ")" );
						ebean.setSubject("REGIONAL " + s + "New document for review: " + pb.getProjectName() + "(" + pb.getProjectNumber() + ")");
						ebean.setTo(p.getEmailAddress());
						ebean.setFrom("fund3@nlesd.ca");
						ebean.setBody(VelocityUtils.mergeTemplateIntoString("fund3/fund3_new_document.vm", model));
						//now attachment
						if (StringUtils.isNotEmpty(docname)) {
							ebean.setAttachments(new File[] {
								new File(docpath + docname)
							});
	
						}
						ebean.send();
					}
				}
		}
		catch (Exception e) {
			System.err.println("void sendApprovedProjectEmails(ArrayList<String> maillist,ProjectBean pb) " + e);
			throw new Fund3Exception("Can not send project emails" + e);
		}
	}
	public static void sendProjectNumberEmails(ArrayList<String> maillist,ProjectBean pb) throws Fund3Exception {
		try {
				//loop through list and send emails
				for(String s : maillist)
				{
					ArrayList<Personnel> sendTo = null;
					sendTo = new ArrayList<Personnel>();
					String regionalemail="FUND3 REGIONAL MANAGER " + s;
					sendTo.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole(regionalemail)));
					for (Personnel p : sendTo) {
						EmailBean ebean = new EmailBean();
						HashMap<String, Object> model = new HashMap<String, Object>();
						// set values to be used in template
						model.put("project",pb.getProjectName());
						ebean.setSubject("REGIONAL " + s + "Project Number Updated for : " + pb.getProjectName()+ " updated to " + pb.getProjectNumber());
						ebean.setTo(p.getEmailAddress());
						ebean.setFrom("fund3@nlesd.ca");
						ebean.setBody(VelocityUtils.mergeTemplateIntoString("fund3/fund3_update_project_number.vm", model));
						ebean.send();
					}
				}
		}
		catch (Exception e) {
			System.err.println("void sendProjectNumberEmails(ArrayList<String> maillist,ProjectBean pb) " + e);
			throw new Fund3Exception("Can not send project emails" + e);
		}
	}	
}