package com.esdnl.scrs.site.handler.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONException;

import com.awsd.school.SchoolException;
import com.esdnl.school.bean.StudentRecordBean;
import com.esdnl.school.database.StudentRecordManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class StudentAutoCompleteAjaxRequestHander extends RequestHandlerImpl {

	public StudentAutoCompleteAjaxRequestHander() {

		requiredPermissions = new String[] {
				"BULLYING-ANALYSIS-SCHOOL-VIEW", "BULLYING-ANALYSIS-ADMIN-VIEW"
		};

		validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("term")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			try {
				Collection<StudentRecordBean> beans = StudentRecordManager.searchById(form.get("term"));

				// generate JSON for candidate details.
				PrintWriter out = response.getWriter();

				response.setContentType("application/json");
				response.setHeader("Cache-control", "no-cache, no-store");
				response.setHeader("Pragma", "no-cache");
				response.setHeader("Expires", "-1");

				net.sf.json.JSONArray jsonArr = new net.sf.json.JSONArray();
				net.sf.json.JSONObject jsonObj = null;

				for (StudentRecordBean sr : beans) {
					jsonObj = new net.sf.json.JSONObject();
					try {
						jsonObj.put("studentid", sr.getStudentId());
						jsonObj.put("firstname", sr.getFirstName());
						jsonObj.put("lastname", sr.getLastName());
						jsonObj.put("middlename", sr.getMiddleName());
						jsonObj.put("gender", sr.getGender().getId());

						jsonArr.add(jsonObj);
					}
					catch (JSONException je) {
						je.printStackTrace();
					}
				}

				out.write(jsonArr.toString());

				out.flush();
				out.close();
				path = null;
			}
			catch (SchoolException e) {
				e.printStackTrace();

				path = null;
			}
		}

		return path;
	}
}
