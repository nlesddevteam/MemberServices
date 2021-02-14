package com.esdnl.personnel.jobs.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.common.Utils;
import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.esdnl.personnel.jobs.bean.SubListBean;
import com.esdnl.personnel.jobs.constants.SubstituteListConstant;
import com.esdnl.personnel.jobs.dao.SubListManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;
import com.nlesd.school.service.SchoolZoneService;

public class GetRegionalSchoolsSubListsAjaxRequestHandler extends RequestHandlerImpl {

	public GetRegionalSchoolsSubListsAjaxRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("zoneid")
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
			int zoneid = form.getInt("zoneid");
			ArrayList<School> slist = new ArrayList<>(SchoolDB.getSchools(SchoolZoneService.getSchoolZoneBean(zoneid)));
			sb.append("<SUBLISTS>");
			sb.append("<SCHOOLS>");
			for(School s:slist ) {
				sb.append("<ZSCHOOL>");
				sb.append("<SNAME>" + s.getSchoolName() + "</SNAME>");	
				sb.append("<SID>" + s.getSchoolID() + "</SID>");
				sb.append("</ZSCHOOL>");
			}
			sb.append("</SCHOOLS>");
			//now we add the sublist
			SubListBean[] subl = SubListManager.getSubListBeansZone(zoneid,Utils.getCurrentSchoolYear() , SubstituteListConstant.TEACHER);
			sb.append("<ZONESUBLIST>");
			for(SubListBean s:subl ) {
				sb.append("<ZLIST>");
				sb.append("<ZNAME>" + s.getTitle() + "</ZNAME>");	
				sb.append("<ZID>" + s.getId() + "</ZID>");
				sb.append("</ZLIST>");
			}
			sb.append("</ZONESUBLIST>");
			sb.append("<MESSAGE>SUCCESS</MESSAGE>");
			sb.append("</SUBLISTS>");
			xml = StringUtils.encodeXML(sb.toString());
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			sb = new StringBuffer();
			sb.append("<SUBLISTS>");
			sb.append("<MESSAGE>" + e.getMessage() +"</MESSAGE>");
			sb.append("</SUBLISTS>");
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
			sb.append("<SUBLISTS>");
			sb.append("<MESSAGE>" + this.validator.getErrorString() +"</MESSAGE>");
			sb.append("</SUBLISTS>");
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
