package com.esdnl.personnel.jobs.dao;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.bean.EmailException;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.PersonnelException;
import com.awsd.school.SchoolDB;
import com.awsd.school.SchoolException;
import com.esdnl.personnel.jobs.bean.RequestToHireBean;
import com.esdnl.personnel.jobs.bean.RequestToHireHistoryBean;
import com.esdnl.velocity.VelocityUtils;

public class RequestToHireEmailManager {
	
	public static void sendRequestToHireEmail(RequestToHireBean rbean){
		//first check zone
		int zoneid;
		String zonename="";
		try {
			Integer test =Integer.parseInt(rbean.getWorkLocation());
			switch (test) {
			case 0: // District Office
				zoneid = 1;
				break;
			case 4007: // Burin Satellite Office
				zoneid = 1;
				break;
			case 449: // St. Augustine's Primary
				zoneid = 1;
				break;
			case 455: // Janeway Hospital School
				zoneid = 1;
				break;
			case 1000: //Labrador Regional Office
				zoneid = 4;
				break;
			case 1009: // Avalon West Satellite Office
				zoneid = 1;
				break;
			case 2008: // Vista Satellite Office
				zoneid = -1;
				break;
			case 2000: //Western Regional Office
				zoneid = 3;
				break;
			case 3000: //Nova Central Regional Office
				zoneid = 2;
				break;
			default:

				zoneid = SchoolDB.getSchoolZoneBySchoolName(SchoolDB.getSchoolFromDeptId(Integer.parseInt(rbean.getWorkLocation())).getSchoolName());
			}
			
			
			switch(zoneid){
			case 1://Eastern
				zonename="EAST";
				break;
			case 2://central
				zonename="CENT";
				break;
			case 3://Western
				zonename="WEST";
				break;
			case 4://Labrador
				zonename="LABR";
				break;	
			default:
				zonename="EAST";
				break;
			}
			//now we see what the status is and send the correct emails
			rbean.getDivisionString();
			ArrayList<Personnel> to = new ArrayList<Personnel>();
			String emailtemplate="";
			String emailsubject="";
			EmailBean ebean = new EmailBean();
			HashMap<String, Object> model = new HashMap<String, Object>();
			StringBuilder historyNotes = new StringBuilder();
			switch(rbean.getStatus().getValue()){
			case 1://submitted
				//user and division manager
				to.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("RTH-"+ zonename + "-" + rbean.getDivisionStringShort()+ "-DD")));
				emailtemplate="personnel/request_to_hire_submitted.vm";
				model.put("requesterName", rbean.getRequestBy());
				model.put("requestId", rbean.getId());
				model.put("alevel","1" );
				model.put("requestTitle",rbean.getJobTitle() );
				emailsubject="Request To Hire Pending Approval for " + rbean.getRequestBy();
				historyNotes.append("Approval Email Sent To:");
				break;
			case 2://Division Approval
				//user,division manager
				//to.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("RTH-BC")));
				//use new email created for the comptroller
				emailtemplate="personnel/request_to_hire_submitted.vm";
				model.put("requesterName", rbean.getRequestBy());
				model.put("requestId", rbean.getId());
				model.put("alevel","2" );
				model.put("requestTitle",rbean.getJobTitle() );
				emailsubject="Request To Hire Pending Approval for " + rbean.getRequestBy();
				historyNotes.append("Approval Email Sent To:");
				break;
			case 3://Comptroller Approval
				//user,division manager
				to.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("RTH-"+ rbean.getDivisionStringShort()+ "-AD")));
				emailtemplate="personnel/request_to_hire_submitted.vm";
				model.put("requesterName", rbean.getRequestBy());
				model.put("requestId", rbean.getId());
				model.put("alevel","3" );
				model.put("requestTitle",rbean.getJobTitle() );
				emailsubject="Request To Hire Pending Approval for " + rbean.getRequestBy();
				historyNotes.append("Approval Email Sent To:");
				break;
			case 4://Assistant Director Approval
				//user,division manager
				to.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("RTH-HR-AD")));
				emailtemplate="personnel/request_to_hire_submitted.vm";
				model.put("requesterName", rbean.getRequestBy());
				model.put("requestId", rbean.getId());
				model.put("alevel","4" );
				model.put("requestTitle",rbean.getJobTitle() );
				emailsubject="Request To Hire Pending Approval for " + rbean.getRequestBy();
				historyNotes.append("Approval Email Sent To:");
				break;
			case 5://Assistant Director HR Approval
				//send email to user
				to.add(PersonnelDB.getPersonnel(rbean.getRequestById()));
				//send email to position that posts the job
				to.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("RTH-POST-COMP")));
				emailtemplate="personnel/request_to_hire_post.vm";
				model.put("requesterName", rbean.getRequestBy());
				model.put("requestId", rbean.getId());
				model.put("alevel","5" );
				model.put("requestTitle",rbean.getJobTitle() );
				emailsubject="Request To Hire for " + rbean.getRequestBy() + " ready to be posted";
				historyNotes.append("Ready To Post Email Sent To:");
				break;
			case 6://Ad Created
				//send email to user
				to.add(PersonnelDB.getPersonnel(rbean.getRequestById()));
				emailtemplate="personnel/request_to_hire_posted.vm";
				model.put("requesterName", rbean.getRequestBy());
				model.put("alevel","6" );
				model.put("requestTitle",rbean.getJobTitle() );
				emailsubject="Request To Hire for " + rbean.getRequestBy() + " has been posted";
				historyNotes.append("Posted Email Sent");
				break;
			case 7://Rejected
				//user
				to.add(PersonnelDB.getPersonnel(rbean.getRequestById()));
				emailtemplate="personnel/request_to_hire_declined.vm";
				model.put("requesterName", rbean.getRequestBy());
				model.put("requestId", rbean.getId());
				model.put("alevel","5" );
				model.put("requestTitle",rbean.getJobTitle() );
				emailsubject="Request To Hire for " + rbean.getRequestBy() + " has been declined";
				historyNotes.append("Declined Email Sent To:");
				break;
			default:
				break;
			}
			
			
			//email  region hr seo
			if(rbean.getStatus().getValue() == 2) {
				//comptroller approval use new email created
				// budgethireapproval@nlesd.ca,
				ebean.setSubject(emailsubject);
				ebean.setTo("budgethireapproval@nlesd.ca");
				ebean.setBody(VelocityUtils.mergeTemplateIntoString(emailtemplate, model));
				ebean.setFrom("ms@nlesd.ca");
				ebean.send();
				historyNotes.append(" " + "budgethireapproval@nlesd.ca");
			}else {
				for (Personnel p : to) {
					ebean.setSubject(emailsubject);
					ebean.setTo(p.getEmailAddress());
					ebean.setBody(VelocityUtils.mergeTemplateIntoString(emailtemplate, model));
					ebean.setFrom("ms@nlesd.ca");
					ebean.send();
					historyNotes.append(" " + p.getFullName());
					}
			}
			
			//add history object
			RequestToHireHistoryBean rhis = new RequestToHireHistoryBean();
			rhis.setRequestToHireId(rbean.getId());
			rhis.setStatusId(rbean.getStatus());
			rhis.setNotes(historyNotes.toString());
			RequestToHireHistoryManager.addRequestToHireHistoryBean(rhis);
				
			
		}  catch (PersonnelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SchoolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
}
