package com.nlesd.bcs.handler;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.bean.BussingContractorSystemReportTableFieldBean;
import com.nlesd.bcs.constants.EmployeeStatusConstant;
import com.nlesd.bcs.constants.StatusConstant;
import com.nlesd.bcs.constants.VehicleStatusConstant;
import com.nlesd.bcs.dao.BussingContractorSystemReportTableFieldManager;
import com.nlesd.bcs.dao.DropdownManager;
public class GetReportTableFieldPropertiesAjaxRequestHandler extends RequestHandlerImpl {
	public GetReportTableFieldPropertiesAjaxRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		try {
			int sid = form.getInt("sid");
			// generate XML for candidate details.
			BussingContractorSystemReportTableFieldBean fbean = BussingContractorSystemReportTableFieldManager.getReportTableFieldById(sid);
			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<FIELDLIST>");
			sb.append("<FIELD>");
			sb.append("<MESSAGE>SUCCESS</MESSAGE>");
			sb.append("<ID>" + fbean.getId() + "</ID>");
			sb.append("<FIELDNAME>" + fbean.getFieldName() + "</FIELDNAME>");
			sb.append("<FIELDTITLE>" + fbean.getFieldTitle() + "</FIELDTITLE>");
			sb.append("<FIELDTYPE>" + fbean.getFieldType() + "</FIELDTYPE>");
			sb.append("<RELATEDFIELD>" + fbean.getRelatedField() + "</RELATEDFIELD>");
			if(fbean.getRelatedField() > 0){
				//we add the values for a dropdown
				TreeMap<Integer,String> items = DropdownManager.getDropdownValuesTM(fbean.getRelatedField());
				sb.append("<DROPDOWN>");
				for(Map.Entry<Integer,String> entry : items.entrySet()) {
					sb.append("<DDITEM>");
					sb.append("<ID>" + entry.getKey() + "</ID>");
					sb.append("<TEXT>" + entry.getValue() + "</TEXT>");
					sb.append("</DDITEM>");
				}
				sb.append("</DROPDOWN>");
			}
			if(!(fbean.getConstantField() == null)){
				//we add the values for a dropdown
				sb.append("<DROPDOWN>");
				if(fbean.getConstantField().equals("StatusConstant")){
					for(StatusConstant sc : StatusConstant.ALL){
						sb.append("<DDITEM>");
						sb.append("<ID>" + sc.getValue() + "</ID>");
						sb.append("<TEXT>" + sc.getDescription() + "</TEXT>");
						sb.append("</DDITEM>");
					}
				}else if(fbean.getConstantField().equals("EmployeeStatusConstant")){
					for(EmployeeStatusConstant sc : EmployeeStatusConstant.ALL){
						sb.append("<DDITEM>");
						sb.append("<ID>" + sc.getValue() + "</ID>");
						sb.append("<TEXT>" + sc.getDescription() + "</TEXT>");
						sb.append("</DDITEM>");
					}
				}else if(fbean.getConstantField().equals("VehicleStatusConstant")){
					for(VehicleStatusConstant sc : VehicleStatusConstant.ALL){
						sb.append("<DDITEM>");
						sb.append("<ID>" + sc.getValue() + "</ID>");
						sb.append("<TEXT>" + sc.getDescription() + "</TEXT>");
						sb.append("</DDITEM>");
					}
				}
				sb.append("</DROPDOWN>");
			}
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
