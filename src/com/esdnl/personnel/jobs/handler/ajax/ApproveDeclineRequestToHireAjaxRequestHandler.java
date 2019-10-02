package com.esdnl.personnel.jobs.handler.ajax;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.RequestToHireBean;
import com.esdnl.personnel.jobs.bean.RequestToHireHistoryBean;
import com.esdnl.personnel.jobs.constants.RequestToHireStatus;
import com.esdnl.personnel.jobs.dao.RequestToHireEmailManager;
import com.esdnl.personnel.jobs.dao.RequestToHireHistoryManager;
import com.esdnl.personnel.jobs.dao.RequestToHireManager;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.util.StringUtils;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequiredFormElement;
public class ApproveDeclineRequestToHireAjaxRequestHandler extends RequestHandlerImpl {

	public ApproveDeclineRequestToHireAjaxRequestHandler() {
		validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("rid"),new RequiredFormElement("status")
			});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
				int rid = form.getInt("rid");
				int status = form.getInt("status");
				String statustype = form.get("rtype");
				RequestToHireBean rth = RequestToHireManager.getRequestToHireById(rid);
				RequestToHireHistoryBean rhis = new RequestToHireHistoryBean();
				if(statustype.equals("A")){
					if(rth.getDivision() == 6) {
						//division is hr then one less approval
						if(status == 4) {
							//by pass one of the validations for ad-hr
							RequestToHireManager.approveRequestToHire(rid,5, Integer.toString(usr.getPersonnel().getPersonnelID()));
							rhis.setNotes(RequestToHireStatus.get(5).getDescription() + ":" + usr.getPersonnel().getFullName());
							rhis.setRequestToHireId(rid);
							rhis.setStatusId(RequestToHireStatus.get(5));
							RequestToHireHistoryManager.addRequestToHireHistoryBean(rhis);
						}else {
							RequestToHireManager.approveRequestToHire(rid,status, Integer.toString(usr.getPersonnel().getPersonnelID()));
							rhis.setNotes(RequestToHireStatus.get(status).getDescription() + ":" + usr.getPersonnel().getFullName());
							rhis.setRequestToHireId(rid);
							rhis.setStatusId(RequestToHireStatus.get(status));
							RequestToHireHistoryManager.addRequestToHireHistoryBean(rhis);
						}
					}else {
						RequestToHireManager.approveRequestToHire(rid,status, Integer.toString(usr.getPersonnel().getPersonnelID()));
						rhis.setNotes(RequestToHireStatus.get(status).getDescription() + ":" + usr.getPersonnel().getFullName());
						rhis.setRequestToHireId(rid);
						rhis.setStatusId(RequestToHireStatus.get(status));
						RequestToHireHistoryManager.addRequestToHireHistoryBean(rhis);
					}
					//send email to next approval
					RequestToHireEmailManager.sendRequestToHireEmail(RequestToHireManager.getRequestToHireById(rid));
				}else{
					RequestToHireManager.approveRequestToHire(rid,RequestToHireStatus.REJECTED.getValue(), Integer.toString(usr.getPersonnel().getPersonnelID()));
					rhis.setNotes(RequestToHireStatus.REJECTED.getDescription() + ":" + usr.getPersonnel().getFullName());
					rhis.setRequestToHireId(rid);
					rhis.setStatusId(RequestToHireStatus.REJECTED);
					RequestToHireHistoryManager.addRequestToHireHistoryBean(rhis);
					//send email to next approval
					RequestToHireEmailManager.sendRequestToHireEmail(RequestToHireManager.getRequestToHireById(rid));
				}
				
				
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<REQUESTTOHIRE>");
				sb.append("<RTH>");
				sb.append("<STATUS>SUCCESS</STATUS>");
				sb.append("</RTH>");
				sb.append("</REQUESTTOHIRE>");
				
				xml = StringUtils.encodeXML(sb.toString());

				PrintWriter out = response.getWriter();

				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			}
			catch (Exception e) {
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");

				sb.append("<REQUESTTOHIRE>");
				sb.append("<RTH>");
				sb.append("<STATUS>" + e.getMessage() + "</STATUS>");
				sb.append("</RTH>");
				sb.append("</REQUESTTOHIRE>");
				

				xml = StringUtils.encodeXML(sb.toString());

				System.out.println(xml);

				PrintWriter out = response.getWriter();

				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			}
		

		return null;
	}

}