package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.ReferenceCheckRequestBean;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.ReferenceCheckRequestManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class DeleteTeacherReferenceCheckRequestRequestHandler extends RequestHandlerImpl {

	public DeleteTeacherReferenceCheckRequestRequestHandler() {

		requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-VIEW", "PERSONNEL-PRINCIPAL-VIEW"
		};

		validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("id")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {

			if (validate_form()) {

				ReferenceCheckRequestBean refchk = ReferenceCheckRequestManager.getReferenceCheckRequestBean(form.getInt("id"));

				if (refchk != null) {
					JobOpportunityBean job = (JobOpportunityBean) session.getAttribute("JOB");
					ApplicantProfileBean profile = ApplicantProfileManager.getApplicantProfileBean(refchk.getCandidateId());

					ReferenceCheckRequestManager.deleteReferenceCheckRequestBean(refchk);

					Collection<ReferenceCheckRequestBean> beans = ReferenceCheckRequestManager.getReferenceCheckRequestBeans(job,
							profile);

					// generate XML for candidate details.
					String xml = null;
					StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
					sb.append("<SEND-REF-CHK-REQ-RESPONSE>");
					sb.append("<REFERENCE-CHECK-REQUESTS>");
					for (ReferenceCheckRequestBean req : beans)
						sb.append(req.toXML());
					sb.append("</REFERENCE-CHECK-REQUESTS>");
					sb.append("<RESPONSE-MSG>Request deleted successfully.</RESPONSE-MSG>");
					sb.append("</SEND-REF-CHK-REQ-RESPONSE>");
					xml = sb.toString().replaceAll("&", "&amp;");

					System.out.println(xml);

					PrintWriter out = response.getWriter();

					response.setContentType("text/xml");
					response.setHeader("Cache-Control", "no-cache");
					out.write(xml);
					out.flush();
					out.close();
					path = null;
				}
				else {
					// generate XML for candidate details.
					String xml = null;
					StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
					sb.append("<SEND-REF-CHK-REQ-RESPONSE>");
					sb.append("<REFERENCE-CHECK-REQUESTS>");
					sb.append("</REFERENCE-CHECK-REQUESTS>");
					sb.append("<RESPONSE-MSG>Request not found.</RESPONSE-MSG>");
					sb.append("</SEND-REF-CHK-REQ-RESPONSE>");
					xml = sb.toString().replaceAll("&", "&amp;");

					System.out.println(xml);

					PrintWriter out = response.getWriter();

					response.setContentType("text/xml");
					response.setHeader("Cache-Control", "no-cache");
					out.write(xml);
					out.flush();
					out.close();
					path = null;
				}
			}
			else {
				// generate XML for candidate details.
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<SEND-REF-CHK-REQ-RESPONSE>");
				sb.append("<REFERENCE-CHECK-REQUESTS>");
				sb.append("</REFERENCE-CHECK-REQUESTS>");
				sb.append("<RESPONSE-MSG>Request ID required.</RESPONSE-MSG>");
				sb.append("</SEND-REF-CHK-REQ-RESPONSE>");
				xml = sb.toString().replaceAll("&", "&amp;");

				System.out.println(xml);

				PrintWriter out = response.getWriter();

				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
				path = null;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			path = null;
		}

		return path;
	}
}