package com.nlesd.bcs.handler;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.bean.BussingContractorSystemReportTableBean;
import com.nlesd.bcs.bean.BussingContractorSystemReportTableFieldBean;
import com.nlesd.bcs.dao.BussingContractorSystemReportTableFieldManager;
import com.nlesd.bcs.dao.BussingContractorSystemReportTableManager;
public class GetReportTablesFieldsAjaxRequestHandler extends RequestHandlerImpl {
	public GetReportTablesFieldsAjaxRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		try {
			String sids = form.get("sids");
			String[] idlist = sids.split(",");
			// generate XML for candidate details.
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<FIELDLIST>");
			for(String s : idlist){
				BussingContractorSystemReportTableBean tbean =  BussingContractorSystemReportTableManager.getReportTableById(Integer.parseInt(s));
				ArrayList<BussingContractorSystemReportTableFieldBean> subfieldlist = BussingContractorSystemReportTableFieldManager.getReportTableFields(Integer.parseInt(s));
				for(BussingContractorSystemReportTableFieldBean bean : subfieldlist){
					sb.append("<FIELD>");
					sb.append("<MESSAGE>SUCCESS</MESSAGE>");
					sb.append("<ID>" + bean.getId() + "</ID>");
					sb.append("<TITLE>" + bean.getFieldTitle() + "</TITLE>");
					sb.append("<TABLETITLE>" + tbean.getTableTitle() + "</TABLETITLE>");
					sb.append("</FIELD>");
				}
			}
			sb.append("</FIELDLIST>");
			xml = sb.toString().replaceAll("&", "&amp;");
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
			path = null;

			
		}
		catch (Exception e) {
			// generate XML for candidate details.
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<FIELDLIST>");
			sb.append("<FIELD>");
			sb.append("<MESSAGE>ERROR GETTING REPORT FIELDS</MESSAGE>");
			sb.append("</FIELD>");
			sb.append("</FIELDLIST>");
			xml = sb.toString().replaceAll("&", "&amp;");
			PrintWriter out = response.getWriter();
			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
			path = null;

		}
		return path;
	}

}