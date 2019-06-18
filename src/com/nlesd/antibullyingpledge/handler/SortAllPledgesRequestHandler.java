package com.nlesd.antibullyingpledge.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.util.StringUtils;
import com.nlesd.antibullyingpledge.bean.AntiBullyingPledgeBean;
import com.nlesd.antibullyingpledge.dao.AntiBullyingPledgeManager;
public class SortAllPledgesRequestHandler extends RequestHandlerImpl {
public SortAllPledgesRequestHandler()
	{
		requiredPermissions = new String[] {
				"SURVEY-ADMIN-VIEW"
			};
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
	super.handleRequest(request, response);
	String sortby="";
	try {
		sortby=this.form.get("sortby");
		
		List<AntiBullyingPledgeBean> listpledges = AntiBullyingPledgeManager.getAllPledges(Integer.parseInt(sortby));
		if (listpledges.size() > 0)
		{
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<BULLYING-PLEDGES>");
			for(AntiBullyingPledgeBean b : listpledges)
			{
				sb.append("<BULLYING-PLEDGE>");
				sb.append("<MESSAGE>PLEDGEFOUND</MESSAGE>");
				sb.append("<FIRSTNAME>" + b.getFirstName() + "</FIRSTNAME>");
				sb.append("<LASTNAME>" + b.getLastName() + "</LASTNAME>");
				sb.append("<GRADELEVEL>" + b.getGradeLevel() + "</GRADELEVEL>");
				sb.append("<SCHOOLNAME>" + b.getSchoolName() + "</SCHOOLNAME>");
				sb.append("<EMAIL>" + b.getEmail() + "</EMAIL>");
				sb.append("<DATESUBMITTED>" + b.getDate_Submitted_User_Formatted() + "</DATESUBMITTED>");
				sb.append("<PK>" + b.getPk() + "</PK>");
				sb.append("<CONFIRMED>" + b.getConfirmed() + "</CONFIRMED>");
				sb.append("</BULLYING-PLEDGE>");
			}
			sb.append("</BULLYING-PLEDGES>");
			xml = StringUtils.encodeXML(sb.toString());
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
		}else{
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<BULLYING-PLEDGES>");
			sb.append("<BULLYINGPLEDGE>");
			sb.append("<MESSAGE>NOPLEDGESFOUND</MESSAGE>");
			sb.append("</BULLYINGPLEDGE>");
			sb.append("</BULLYING-PLEDGES>");
			xml = StringUtils.encodeXML(sb.toString());
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
		}
	}
	catch (Exception e) {
		String xml = null;
		StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
		sb.append("<BULLYING-PLEDGES>");
		sb.append("<BULLYINGPLEDGE>");
		sb.append("<MESSAGE>SEARCHERROR</MESSAGE>");
		sb.append("</BULLYINGPLEDGE>");
		sb.append("</BULLYING-PLEDGES>");
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