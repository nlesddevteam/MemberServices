package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.Covid19ReportBean;
import com.esdnl.personnel.jobs.dao.Covid19ReportManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class ViewCovid19ReportBySchoolRequestHandler extends RequestHandlerImpl {

	public ViewCovid19ReportBySchoolRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("locationid")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
			IOException {

		super.handleRequest(request, response);

		
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		if(validate_form()) {
			try {
				String locationid = form.get("locationid");
				ArrayList<Covid19ReportBean> elist = Covid19ReportManager.getCovid19ReportBySchool(locationid);
				sb.append("<EMPLOYEES>");
				for(Covid19ReportBean p: elist) {
					sb.append(p.toXml());
				}
				sb.append("<MESSAGE>FOUND</MESSAGE>");
				sb.append("</EMPLOYEES>");
				xml = StringUtils.encodeXML(sb.toString());
				PrintWriter out = response.getWriter();
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				System.out.println(sb.toString());
				out.write(xml);
				out.flush();
				out.close();
			}
			catch (Exception e) {
				e.printStackTrace(System.err);
				sb = new StringBuffer();
				sb.append("<EMPLOYEES>");
				sb.append("<MESSAGE>" + e.getMessage() +"</MESSAGE>");
				sb.append("</EMPLOYEES>");
				xml = StringUtils.encodeXML(sb.toString());
				PrintWriter out = response.getWriter();
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			}
		}else {
			sb = new StringBuffer();
			sb.append("<EMPLOYEES>");
			sb.append("<MESSAGE>" + this.validator.getErrorString() +"</MESSAGE>");
			sb.append("</EMPLOYEES>");
			xml = StringUtils.encodeXML(sb.toString());
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
