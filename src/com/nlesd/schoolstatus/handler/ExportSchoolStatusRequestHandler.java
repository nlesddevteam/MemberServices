package com.nlesd.schoolstatus.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.servlet.LoginNotRequiredRequestHandler;
import com.nlesd.schoolstatus.bean.SchoolStatusExportBean;
import com.nlesd.schoolstatus.bean.SchoolStatusExportSettingsBean;
import com.nlesd.schoolstatus.dao.SchoolStatusExportManager;

public class ExportSchoolStatusRequestHandler implements LoginNotRequiredRequestHandler {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StringBuilder data = new StringBuilder();
		String stype = request.getParameter("STP");
		SchoolStatusExportSettingsBean ebean = SchoolStatusExportManager.getSchoolStatusExportSettings();
		if(ebean.getExportStatus()) {
			ArrayList<SchoolStatusExportBean> list =  SchoolStatusExportManager.getSchoolStatusList();
			if(stype.contentEquals("ECSV")) {
				data.append(SchoolStatusExportBean.headerString());
			}else {
				data.append(SchoolStatusExportBean.headerStringTab());
			}
			for (SchoolStatusExportBean bean:list) {
				if(stype.contentEquals("ECSV")) {
					data.append(bean.toString());
				}else {
					data.append(bean.toStringTab());
				}
			}
		}else {
			data.append("Service unavailable \n");
		}
		if(stype.contentEquals("ECSV")) {
			response.setContentType("application/csv");
			response.setHeader("content-disposition","filename=schoolstatus.csv");
		}else {
			response.setContentType("application/text");
			response.setHeader("content-disposition","filename=schoolstatus.txt");
		}
		//add export log
		SchoolStatusExportManager.addSchoolStatusExportLog(request.getRemoteAddr(), stype);
		PrintWriter out = response.getWriter();
		out.print(data);
		out.flush();
		out.close();
		return null;
	}

}
