package com.nlesd.eecd.handler;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.common.Utils;
import com.awsd.mail.bean.EmailBean;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.velocity.VelocityUtils;
import com.nlesd.eecd.bean.EECDTeacherAreaBean;
import com.nlesd.eecd.bean.EECDTeacherAreaStatusBean;
import com.nlesd.eecd.constants.TeacherAreaStatus;
import com.nlesd.eecd.dao.EECDTeacherAreaManager;
import com.nlesd.eecd.dao.EECDTeacherAreaStatusManager;
public class AddUpdateTeacherAreaAjaxRequestHandler extends RequestHandlerImpl
{
	public AddUpdateTeacherAreaAjaxRequestHandler() {
		this.requiredPermissions = new String[] {
				"EECD-VIEW","EECD-VIEW-APPROVALS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("selectedids")
		});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");

		String message="";
		int newid=0;
		boolean sendapprovalemail=false;
		if (validate_form()) {
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
							ebean.setSchoolYear(Utils.getCurrentSchoolYear());
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
						ebean.setSchoolYear(Utils.getCurrentSchoolYear());
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
				sb.append("<TAREAS>");
				sb.append("<TAREA>");
				sb.append("<MESSAGE>" + e.getMessage() + "</MESSAGE>");
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
		}else {
			message=com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString());
		}
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