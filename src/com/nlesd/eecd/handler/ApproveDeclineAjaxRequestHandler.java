package com.nlesd.eecd.handler;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.mail.bean.EmailBean;
import com.awsd.personnel.PersonnelDB;
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
public class ApproveDeclineAjaxRequestHandler extends RequestHandlerImpl
{
	public ApproveDeclineAjaxRequestHandler() {
		this.requiredPermissions = new String[] {
				"EECD-VIEW-APPROVALS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("aid"),
				new RequiredFormElement("astatus")
		});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		String message="";
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");

		if (validate_form()) {
			try{
				int id = Integer.parseInt(request.getParameter("aid"));
				EECDTeacherAreaBean tbean = EECDTeacherAreaManager.getEECDTeacherAreaById(id);
				int astatus = Integer.parseInt(request.getParameter("astatus"));
				String snotes = request.getParameter("snotes");
				//now we update the current status
				EECDTeacherAreaManager.updateCurrentStatusTeacherArea(id,astatus);
				//now we update the teacher area status table
				EECDTeacherAreaStatusBean sbean = new EECDTeacherAreaStatusBean();
				sbean.setPersonnelId(tbean.getPersonnelId());
				sbean.setStatus(TeacherAreaStatus.get(astatus));
				sbean.setStatusBy(usr.getPersonnel().getPersonnelID());
				if(sbean.getStatus() ==  TeacherAreaStatus.APPROVED_SCHOOL_ADMIN){
					sbean.setStatusNotes("Approved by [" + usr.getLotusUserFullName() + "]: " + snotes);
				}else{
					sbean.setStatusNotes("Declined by [" + usr.getLotusUserFullName() + "]: " + snotes);
				}

				sbean.setTeacherAreaId(tbean.getId());
				EECDTeacherAreaStatusManager.addTeacherAreaStatus(sbean);
				//next we send the email to teacher
				EmailBean email = new EmailBean();
				//employees principal
				email.setTo(PersonnelDB.getPersonnel(tbean.getPersonnelId()).getEmailAddress());
				email.setFrom("ms@nlesd.ca");
				if(sbean.getStatus() ==  TeacherAreaStatus.APPROVED_SCHOOL_ADMIN){
					email.setSubject("NLESD EECD Teacher Area Approved");
				}else{
					email.setSubject("NLESD EECD Teacher Area Declined");
				}

				HashMap<String, Object> model = new HashMap<String, Object>();
				// set values to be used in template
				model.put("taStatus", sbean.getStatusNotes());
				model.put("taArea", tbean.getAreaDescription());
				email.setBody(VelocityUtils.mergeTemplateIntoString("eecd/send_admin_status_message.vm", model));
				email.send();
				message="UPDATED";
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
