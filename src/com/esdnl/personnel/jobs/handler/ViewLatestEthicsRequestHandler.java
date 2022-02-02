package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.EthicsDeclarationReportBean;
import com.esdnl.personnel.jobs.dao.EthicsDeclarationReportManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class ViewLatestEthicsRequestHandler extends RequestHandlerImpl {

	public ViewLatestEthicsRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("numdays")
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
				int numdays = form.getInt("numdays");
				ArrayList<EthicsDeclarationReportBean> elist = EthicsDeclarationReportManager.getLatestEthicsDeclarationsByDays(numdays);
				sb.append("<EMPLOYEES>");
				for(EthicsDeclarationReportBean p: elist) {
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