package com.nlesd.school.site.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.SchoolException;
import com.awsd.school.bean.RegionBean;
import com.awsd.school.bean.RegionException;
import com.awsd.school.dao.RegionManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;
import com.nlesd.school.bean.SchoolZoneBean;
import com.nlesd.school.service.SchoolZoneService;

public class GetZoneRegionsAjaxRequestHandler extends PublicAccessRequestHandlerImpl {

	public GetZoneRegionsAjaxRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("id")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			SchoolZoneBean zone = SchoolZoneService.getSchoolZoneBean(form.getInt("id"));

			if (zone != null) {
				Collection<RegionBean> regions = RegionManager.getRegionBeans(zone);

				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<GET-ZONE-REGIONS-RESPONSE>");
				for (RegionBean region : regions) {
					sb.append(region.toXML());
				}
				sb.append("</GET-ZONE-REGIONS-RESPONSE>");

				xml = StringUtils.encodeXML(sb.toString());

				System.out.println(xml);

				PrintWriter out = response.getWriter();

				response.setContentType("text/xml");
				response.setHeader("Cache-Control", "no-cache");
				out.write(xml);
				out.flush();
				out.close();
			}
			else {
				String xml = null;
				StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
				sb.append("<GET-ZONE-REGIONS-RESPONSE error='No zone found' />");

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
		catch (SchoolException e) {
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<GET-ZONE-REGIONS-RESPONSE error='No zone found' />");

			xml = StringUtils.encodeXML(sb.toString());

			System.out.println(xml);

			PrintWriter out = response.getWriter();

			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
		}
		catch (RegionException e) {
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<GET-ZONE-REGIONS-RESPONSE error='No schools found' />");

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
