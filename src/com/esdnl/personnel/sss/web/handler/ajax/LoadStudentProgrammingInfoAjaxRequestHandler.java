package com.esdnl.personnel.sss.web.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.sss.dao.StudentProgrammingInfoManager;
import com.esdnl.personnel.sss.domain.bean.StudentProgrammingInfoBean;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class LoadStudentProgrammingInfoAjaxRequestHandler extends RequestHandlerImpl {

	public LoadStudentProgrammingInfoAjaxRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("sid"), new RequiredFormElement("sy")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			try {

				StudentProgrammingInfoBean info = StudentProgrammingInfoManager.getStudentProgrammingInfoBean(form.get("sid"),
						form.get("sy"));

				// generate XML for candidate details.
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<LoadStudentProgrammingInfoResponse found='" + (info == null ? "false" : "true") + "'>");
				if (info != null) {
					sb.append(info.toXML());
				}
				sb.append("</LoadStudentProgrammingInfoResponse>");
				xml = sb.toString().replaceAll("&", "&amp;");

				System.out.println(xml);

				PrintWriter out = response.getWriter();

				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();

			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		// all ajax request must return null
		return null;
	}

}
