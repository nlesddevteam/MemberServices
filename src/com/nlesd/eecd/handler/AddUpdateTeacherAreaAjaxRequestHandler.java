package com.nlesd.eecd.handler;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.mail.bean.EmailBean;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.esdnl.velocity.VelocityUtils;
import com.nlesd.eecd.bean.EECDTeacherAreaBean;
import com.nlesd.eecd.bean.EECDTeacherAreaStatusBean;
import com.nlesd.eecd.constants.TeacherAreaStatus;
import com.nlesd.eecd.dao.EECDTeacherAreaManager;
import com.nlesd.eecd.dao.EECDTeacherAreaStatusManager;
public class AddUpdateTeacherAreaAjaxRequestHandler implements RequestHandler
{
	  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException
	  {
	    HttpSession session = null;
	    User usr = null;
	    String message="";
	    int newid=0;
	    session = request.getSession(false);
	    boolean sendapprovalemail=false;
	    if((session != null) && (session.getAttribute("usr") != null))
	    {
	      usr = (User) session.getAttribute("usr");
	      
	      if(!(usr.getUserPermissions().containsKey("EECD-VIEW")) && !(usr.getUserPermissions().containsKey("EECD-VIEW-APPROVALS")))
	      {
	        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
	      }
	    }
	    else
	    {
	      throw new SecurityException("User login required.");
	    }
	    try{
	    	String testingthis = request.getParameter("firstsave").toString();
	    	if(testingthis.equals("N")){
	    	//if(request.getParameter("firstsave").toString() == "N"){
	    		//we need to get treemap of any existing selections
	    		TreeMap<Integer,EECDTeacherAreaBean>list = new TreeMap<Integer,EECDTeacherAreaBean>();
	    		list = EECDTeacherAreaManager.getAllEECDAreas(usr.getPersonnel().getPersonnelID());
	    		String selectedids = request.getParameter("selectedids").toString();
	    		//first we loop through and add any new ones
	    		String[] idlist = selectedids.split(",");
	    		for(String s :idlist){
	    			if(!(list.containsKey(Integer.parseInt(s)))){
	    				//we add the value
	    				EECDTeacherAreaBean ebean = new EECDTeacherAreaBean();
		    			ebean.setPersonnelId(usr.getPersonnel().getPersonnelID());
		    			ebean.setAreaId(Integer.parseInt(s));
		    			ebean.setCurrentStatus(TeacherAreaStatus.SUBMITTED);
		    			int taid = EECDTeacherAreaManager.addTeacherArea(ebean);
		    			//update teacher status
			    		EECDTeacherAreaStatusBean sbean = new EECDTeacherAreaStatusBean();
			    		sbean.setPersonnelId(usr.getPersonnel().getPersonnelID());
			    		sbean.setStatus(TeacherAreaStatus.SUBMITTED);
			    		sbean.setStatusBy(usr.getPersonnel().getPersonnelID());
			    		sbean.setStatusNotes("Added new teaching area [" + ebean.getAreaId() + "]");
			    		sbean.setTeacherAreaId(taid);
			    		EECDTeacherAreaStatusManager.addTeacherAreaStatus(sbean);
			    		sendapprovalemail=true;
	    			}//else do nothing already in the database
	    		}
	    		//now we have to check to see if any were removed, if they are already approved then
	    		//they can not be deleted
	    		//first we create a treemap of integers to use for checking, instr will break if you have 5 and 55 in string
	    		TreeMap<Integer,String> checklist = new TreeMap<Integer,String>();
	    		for(String s : idlist){
	    			checklist.put(Integer.parseInt(s), s);
	    		}
	    		for(Map.Entry<Integer,EECDTeacherAreaBean> entry : list.entrySet()) {
	    			EECDTeacherAreaBean ebean = (EECDTeacherAreaBean)entry.getValue();
	    			//check to see if it is in list
					  //if(selectedids.indexOf(ebean.getAreaId()) < 0){
						if(!(checklist.containsKey(ebean.getAreaId()))){  
						  if(ebean.getCurrentStatus() == TeacherAreaStatus.SUBMITTED){
							  //deleted it since it has not been approved yet
							  EECDTeacherAreaManager.deleteTeacherArea(ebean.getId());
							//update teacher status
					    		EECDTeacherAreaStatusBean sbean = new EECDTeacherAreaStatusBean();
					    		sbean.setPersonnelId(usr.getPersonnel().getPersonnelID());
					    		sbean.setStatus(TeacherAreaStatus.DELETED);
					    		sbean.setStatusBy(usr.getPersonnel().getPersonnelID());
					    		sbean.setStatusNotes("Deleted teaching area [" + ebean.getAreaId() + "]");
					    		sbean.setTeacherAreaId(ebean.getAreaId());
					    		EECDTeacherAreaStatusManager.addTeacherAreaStatus(sbean);
						  }
					  }
					}
	    	}else{
	    		String selectedids = request.getParameter("selectedids").toString();
	    		String[] idlist = selectedids.split(",");
	    		for(String s :idlist){
	    			//now we save each entry selected
	    			EECDTeacherAreaBean ebean = new EECDTeacherAreaBean();
	    			ebean.setPersonnelId(usr.getPersonnel().getPersonnelID());
	    			ebean.setAreaId(Integer.parseInt(s));
	    			ebean.setCurrentStatus(TeacherAreaStatus.SUBMITTED);
	    			int taid = EECDTeacherAreaManager.addTeacherArea(ebean);
	    			//update teacher status
		    		EECDTeacherAreaStatusBean sbean = new EECDTeacherAreaStatusBean();
		    		sbean.setPersonnelId(usr.getPersonnel().getPersonnelID());
		    		sbean.setStatus(TeacherAreaStatus.SUBMITTED);
		    		sbean.setStatusBy(usr.getPersonnel().getPersonnelID());
		    		sbean.setStatusNotes("Added new teaching area[" + ebean.getAreaId() + "]");
		    		sbean.setTeacherAreaId(taid);
		    		EECDTeacherAreaStatusManager.addTeacherAreaStatus(sbean);
		    		sendapprovalemail=true;
	    		}
	    		
	    		
	    	}
	    	if(sendapprovalemail && usr.getPersonnel().getSupervisor() != null){
				EmailBean email = new EmailBean();
				//employees principal
				email.setTo(usr.getPersonnel().getSupervisor().getEmailAddress());
				email.setFrom("ms@nlesd.ca");
				email.setSubject("NLESD EECD Teacher Area Approval");
				HashMap<String, Object> model = new HashMap<String, Object>();
				// set values to be used in template
				model.put("requesterName", usr.getLotusUserFullName());
				email.setBody(VelocityUtils.mergeTemplateIntoString("eecd/send_added_message.vm", model));
				email.send();
	    		
	    	}
	    	message="ADDED";
	    }catch(Exception e){
	    	message=e.toString();
	    }
	    
	    
	    String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		sb.append("<TAREAS>");
		sb.append("<TAREA>");
		sb.append("<MESSAGE>" + message + "</MESSAGE>");
		sb.append("<ID>" + newid + "</ID>");
		sb.append("</TAREA>");
		sb.append("</TAREAS>");
		xml = sb.toString().replaceAll("&", "&amp;");
		PrintWriter out = response.getWriter();
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		out.write(xml);
		out.flush();
		out.close();
		return null;
	  }
}