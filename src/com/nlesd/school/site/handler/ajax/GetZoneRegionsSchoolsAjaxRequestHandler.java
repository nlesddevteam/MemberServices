package com.nlesd.school.site.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.school.SchoolDB;
import com.awsd.school.bean.RegionException;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;
import com.nlesd.school.bean.SchoolStreamSelectListBean;

public class GetZoneRegionsSchoolsAjaxRequestHandler extends PublicAccessRequestHandlerImpl {

	public GetZoneRegionsSchoolsAjaxRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("zid")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			Integer zoneid=form.getInt("zid");
			Integer regionid=form.getInt("rid");
			String ddtype = form.get("ddtype");
			Integer schoolid =form.getInt("schoolid");
			ArrayList<SchoolStreamSelectListBean> selectedenglish=null;
			if(ddtype.equals("region"))
			{
				selectedenglish = SchoolDB.getSchoolsByRegionZone(regionid,zoneid,schoolid);
			}
			else{
				selectedenglish = SchoolDB.getSchoolsByZone(zoneid,schoolid);
			}
			if((!selectedenglish.isEmpty()))
			{
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<GET-ZONE-SCHOOLS-RESPONSE>");
				
				for(SchoolStreamSelectListBean sssb : selectedenglish)
				{
					sb.append("<SCHOOL>");
					sb.append("<ID>" + sssb.getSchoolId() + "</ID>");
					sb.append("<NAME>" + sssb.getSchoolName() + "</NAME>");
					sb.append("</SCHOOL>");
				}
				
				sb.append("</GET-ZONE-SCHOOLS-RESPONSE>");
				xml = StringUtils.encodeXML(sb.toString());
				System.out.println(xml);
				PrintWriter out = response.getWriter();
				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
				
				
			}else {
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<GET-ZONE-SCHOOLS-RESPONSE error='No schools found' />");

				xml = StringUtils.encodeXML(sb.toString());

				System.out.println(xml);

				PrintWriter out = response.getWriter();

				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			}

		}
		catch (RegionException e) {
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<GET-ZONE-SCHOOLS-RESPONSE error='No schools found' />");

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
