package com.esdnl.personnel.jobs.handler.ajax;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.RequestToHireHistoryBean;
import com.esdnl.personnel.jobs.constants.RequestToHireStatus;
import com.esdnl.personnel.jobs.dao.RequestToHireEmailManager;
import com.esdnl.personnel.jobs.dao.RequestToHireHistoryManager;
import com.esdnl.personnel.jobs.dao.RequestToHireManager;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.util.StringUtils;
public class ApproveRequestToHireCompRequestHandler extends RequestHandlerImpl {

	public ApproveRequestToHireCompRequestHandler() {
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
				String pnumber = form.get("pnumber");
				if(statustype.equals("A")){
					RequestToHireManager.approveRequestToHireBCPos(rid,status, Integer.toString(usr.getPersonnel().getPersonnelID()),pnumber);
					RequestToHireHistoryBean rhis = new RequestToHireHistoryBean();
					rhis.setNotes(RequestToHireStatus.get(status).getDescription() + ":" + usr.getPersonnel().getFullName());
					rhis.setRequestToHireId(rid);
					rhis.setStatusId(RequestToHireStatus.get(status));
					RequestToHireHistoryManager.addRequestToHireHistoryBean(rhis);
					//send email to next approval
					RequestToHireEmailManager.sendRequestToHireEmail(RequestToHireManager.getRequestToHireById(rid),usr);
				}
				
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<REQUESTTOHIRE>");
				sb.append("<RTH>");
				sb.append("<STATUS>SUCCESS</STATUS>");
				sb.append("</REQUESTTOHIRE>");
				sb.append("/<RTH>");
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
				sb.append("</REQUESTTOHIRE>");
				sb.append("/<RTH>");

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