package com.nlesd.antibullyingpledge.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.util.StringUtils;
import com.nlesd.antibullyingpledge.bean.AntiBullyingPledgeSchoolTotalsBean;
import com.nlesd.antibullyingpledge.dao.AntiBullyingPledgeManager;
public class CreateAllSchoolTotalsReportRequestHandler extends RequestHandlerImpl {
	public CreateAllSchoolTotalsReportRequestHandler()
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
	try {
		List<AntiBullyingPledgeSchoolTotalsBean> listpledges = AntiBullyingPledgeManager.getAllSchoolTotals();
		if (listpledges.size() > 0)
		{
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<BULLYING-PLEDGES-TOTALS>");
			for(AntiBullyingPledgeSchoolTotalsBean b : listpledges)
			{
				sb.append("<BULLYING-PLEDGE-TOTAL>");
				sb.append("<MESSAGE>PLEDGESFOUND</MESSAGE>");
				sb.append("<SCHOOLNAME>" + b.getSchoolName() + "</SCHOOLNAME>");
				sb.append("<SCHOOLID>" + b.getSchoolPicture() + "</SCHOOLID>");
				sb.append("<TOTALPLEDGES>" + b.getTotalPledges() + "</TOTALPLEDGES>");
				sb.append("<PLEDGESCONFIRMED>" + b.getTotalPledgesConfirmed() + "</PLEDGESCONFIRMED>");
				sb.append("</BULLYING-PLEDGE-TOTALS>");
			}
			sb.append("</BULLYING-PLEDGES-TOTAL>");
			xml = StringUtils.encodeXML(sb.toString());
			System.out.println(xml);
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
		}else{
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<BULLYING-PLEDGE-TOTALS>");
			sb.append("<BULLYING-PLEDGE-TOTAL>");
			sb.append("<MESSAGE>NOPLEDGESFOUND</MESSAGE>");
			sb.append("</BULLYING-PLEDGE-TOTAL>");
			sb.append("</BULLYING-PLEDGE-TOTALS>");
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
		sb.append("<BULLYING-PLEDGE-TOTALS>");
		sb.append("<BULLYING-PLEDGE-TOTAL>");
		sb.append("<MESSAGE>SEARCHERROR</MESSAGE>");
		sb.append("</BULLYING-PLEDGE-TOTAL>");
		sb.append("</BULLYING-PLEDGE-TOTALS>");
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