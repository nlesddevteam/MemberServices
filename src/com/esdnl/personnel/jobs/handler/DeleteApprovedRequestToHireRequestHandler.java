package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.bean.EmailException;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.PersonnelException;
import com.awsd.school.SchoolDB;
import com.awsd.school.SchoolException;
import com.esdnl.personnel.jobs.bean.RequestToHireBean;
import com.esdnl.personnel.jobs.bean.RequestToHireHistoryBean;
import com.esdnl.personnel.jobs.constants.RequestToHireStatus;
import com.esdnl.personnel.jobs.dao.RequestToHireEmailManager;
import com.esdnl.personnel.jobs.dao.RequestToHireHistoryManager;
import com.esdnl.personnel.jobs.dao.RequestToHireManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.velocity.VelocityUtils;

public class DeleteApprovedRequestToHireRequestHandler extends RequestHandlerImpl {
	public DeleteApprovedRequestToHireRequestHandler() {
		requiredRoles= new String[] {
				"ADMINISTRATOR","SEO - PERSONNEL"
			};
		validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("rid")
			});
		
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		try {
			
			if(validate_form()) {
				//first we set the status to deleted
				RequestToHireBean rbeanorig = RequestToHireManager.getRequestToHireById(form.getInt("rid"));
				int laststatus= rbeanorig.getStatus().getValue();
				RequestToHireManager.updateRequestToHireStatus(form.getInt("rid"), RequestToHireStatus.DELETED.getValue());
				//next we save the history object
				RequestToHireHistoryBean rbean = new RequestToHireHistoryBean();
				rbean.setNotes(RequestToHireStatus.DELETED.getDescription() + ":" + usr.getPersonnel().getFullName());
				rbean.setRequestToHireId(form.getInt("rid"));
				rbean.setStatusId(RequestToHireStatus.DELETED);
				RequestToHireHistoryManager.addRequestToHireHistoryBean(rbean);
				//now we send email messages to previous approvals
				SendDeletionEmail(rbeanorig,laststatus);
				
				request.setAttribute("msg", "Request has been deleted");
				path = "admin_index.jsp";
			}else {
				request.setAttribute("msg", validator.getErrorString());
				path = "admin_index.jsp";
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			path = null;
		}
		return path;
	}
	private void SendDeletionEmail(RequestToHireBean rbean, int laststatus) {
		ArrayList<Personnel> to = new ArrayList<Personnel>();
		boolean addcomptroller=false;
		try {
			
			int zoneid;
			int schoolid=-1;
			String zonename="";
			Integer test =Integer.parseInt(rbean.getWorkLocation());
			switch (test) {
			case 0: // District Office
				zoneid = 1;
				break;
			case 4007: // Burin Satellite Office
				zoneid = 2;
				break;
			case 449: // St. Augustine's Primary
				zoneid = 1;
				break;
			case 455: // Janeway Hospital School
				zoneid = 1;
				break;
			case 1000:
			case 1001:
			case 1002:
			case 1003:	//Labrador Regional Office
				zoneid = 4;
				break;
			case 1009: // Avalon West Satellite Office
				zoneid = 1;
				break;
			case 2008: // Vista Satellite Office
				zoneid = 2;
				break;
			case 2000:
			case 2001:
			case 3034://Western Regional Office
				zoneid = 3;
				break;
			case 3000: //Nova Central Regional Office
			case 3001:
			case 3030:
			case 3031:
			case 3032:
			case 3033:
			case 3035:
			case 3036:
			case 3037:
			case 3038:
				zoneid = 2;
				break;
			case 5000: //District Conference Center
				zoneid = 1;
				break;
			default:
				zoneid = SchoolDB.getSchoolZoneBySchoolName(SchoolDB.getSchoolFromDeptId(test%1000).getSchoolName());
				schoolid =  test%1000;
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
			
			switch(rbean.getStatus().getValue()){
			case 2:
				//send message to DD
				to.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("RTH-"+ zonename + "-" + rbean.getDivisionStringShort()+ "-DD")));
				break;
			case 3:
				//send message to DD,Comptroller
				to.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("RTH-"+ zonename + "-" + rbean.getDivisionStringShort()+ "-DD")));
				//add comptroller
				addcomptroller=true;
				break;
			case 4:
				//send message to DD,Comptroller, AD Division
				to.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("RTH-"+ zonename + "-" + rbean.getDivisionStringShort()+ "-DD")));
				to.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("RTH-"+ rbean.getDivisionStringShort()+ "-AD")));
				//add comptroller
				addcomptroller=true;
				break;
			case 5:
				//send message to DD,Comptroller, AD Division, AD HR
				if(rbean.getDivision() == 7) {
					//only need to send to DD since other levels skipped for casual
					to.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("RTH-"+ zonename + "-" + rbean.getDivisionStringShort()+ "-DD")));
				}else {
					to.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("RTH-"+ zonename + "-" + rbean.getDivisionStringShort()+ "-DD")));
					to.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("RTH-"+ rbean.getDivisionStringShort()+ "-AD")));
					to.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("RTH-HR-AD")));
					addcomptroller=true;
				}
				break;
			case 13:
				//send message to Regional Manager
				if(RequestToHireEmailManager.burinschools.contains(schoolid)) {
					to.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("RTH-BURIN-FAC-MAN")));
				}else if(RequestToHireEmailManager.vistaschools.contains(schoolid)) {
					to.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("RTH-VISTA-FAC-MAN")));
				}else {
					to.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("RTH-"+ zonename + "-FAC-MAN")));
				}
				break;
			default:
				
				break;
			}
			//add user who created request
			to.add(PersonnelDB.getPersonnel(rbean.getRequestById()));
			String emailtemplate="personnel/request_to_hire_deleted.vm";
			String emailsubject="Request To Hire Deleted";
			EmailBean ebean = new EmailBean();
			HashMap<String, Object> model = new HashMap<String, Object>();
			model.put("requesterName",PersonnelDB.getPersonnel(rbean.getRequestById()).getFullName());
			model.put("userName",usr.getPersonnel().getFirstName());
			model.put("requestTitle", rbean.getJobTitle());
			ebean.setSubject(emailsubject);
			ebean.setBody(VelocityUtils.mergeTemplateIntoString(emailtemplate, model));
			ebean.setFrom("ms@nlesd.ca");
			for (Personnel p : to) {
				ebean.setTo(p.getEmailAddress());
				ebean.send();
			}
			if(addcomptroller) {
				ArrayList<Personnel> finto = new ArrayList<Personnel>();
				finto.addAll(Arrays.asList(PersonnelDB.getPersonnelByRole("RTH-ADDITIONAL")));
				for (Personnel p : finto) {
					ebean.setTo(p.getEmailAddress());
					ebean.send();
				}
			}
				
		} catch (SchoolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PersonnelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}