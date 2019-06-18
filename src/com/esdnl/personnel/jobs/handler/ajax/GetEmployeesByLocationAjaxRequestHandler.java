package com.esdnl.personnel.jobs.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.v2.database.sds.EmployeeManager;
import com.esdnl.personnel.v2.model.sds.bean.EmployeeBean;
import com.esdnl.personnel.v2.model.sds.bean.EmployeeException;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class GetEmployeesByLocationAjaxRequestHandler extends RequestHandlerImpl {

	public GetEmployeesByLocationAjaxRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("locationid"), new RequiredFormElement("schoolyear")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			EmployeeBean[] emps = EmployeeManager.getEmployeeBeans(form.get("schoolyear"), form.get("locationid"));
			HashMap<String, EmployeeBean> empMap = new HashMap<String, EmployeeBean>();
			EmployeeBean tmp = null;

			for (EmployeeBean emp : emps) {
				if (!empMap.containsKey(emp.getEmpId().trim())) {
					empMap.put(emp.getEmpId().trim(), emp);
				}
				else {
					tmp = empMap.get(emp.getEmpId().trim());
					if (!StringUtils.isEqual(tmp.getTenur(), "TENUR") && !StringUtils.isEqual(emp.getTenur(), "TERM")) {
						tmp.setTenur(emp.getTenur());
						tmp.setTenurCode(emp.getTenurCode());
					}
					tmp.setPositionDescription(tmp.getPositionDescription() + "; " + emp.getPositionDescription());
					tmp.setFTE(tmp.getFTE() + emp.getFTE());
				}
			}

			ArrayList<EmployeeBean> empList = new ArrayList<EmployeeBean>(empMap.values());
			Collections.sort(empList, new Comparator<EmployeeBean>() {

				@Override
				public int compare(EmployeeBean o1, EmployeeBean o2) {

					return (o1.getLastName() + o1.getFirstName()).compareTo(o2.getLastName() + o2.getFirstName());
				}
			});

			String xml = null;
			StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
			sb.append("<GET-EMPLOYEES-BY-LOCATION-RESPONSE>");
			for (EmployeeBean emp : empList) {
				sb.append(emp.toXML());
			}
			sb.append("</GET-EMPLOYEES-BY-LOCATION-RESPONSE>");

			xml = StringUtils.encodeXML(sb.toString());

			System.out.println(xml);

			PrintWriter out = response.getWriter();

			response.setContentType("text/xml");
			response.setHeader("Cache-Control", "no-cache");
			out.write(xml);
			out.flush();
			out.close();
		}
		catch (EmployeeException e) {
			e.printStackTrace(System.err);
		}

		return null;
	}

}
