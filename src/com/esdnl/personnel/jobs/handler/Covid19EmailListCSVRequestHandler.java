package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.bean.EmailException;
import com.esdnl.personnel.jobs.bean.Covid19EmailListBean;
import com.esdnl.personnel.jobs.dao.Covid19EmailListManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class Covid19EmailListCSVRequestHandler extends RequestHandlerImpl {
	public Covid19EmailListCSVRequestHandler() {
		this.requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-VIEW-COVID19-EMAIL"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("cid", "Email Log ID Required")
		});
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		ArrayList<Covid19EmailListBean> list = Covid19EmailListManager.getCovid19EmailListCSV(form.getInt("cid"));
		StringBuilder sb= new StringBuilder();
		for(Covid19EmailListBean cb : list) {
			sb.append(cb.toStringShort());
		}

		
		response.setContentType("application/csv");
		response.setHeader("content-disposition","filename=emaillist.csv");
		PrintWriter out = response.getWriter();
		out.print(sb.toString());
		out.flush();
		out.close();

		path = null;
		return path;
		
	}

}

