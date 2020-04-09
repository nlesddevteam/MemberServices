package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.bean.AdRequestBean;
import com.esdnl.personnel.jobs.dao.AdRequestManager;
import com.esdnl.personnel.v2.database.sds.LocationManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class ViewAdRequestRequestHandler extends RequestHandlerImpl {

	public ViewAdRequestRequestHandler() {

		this.requiredPermissions = new String[] {
				"PERSONNEL-ADREQUEST-APPROVE", "PERSONNEL-ADREQUEST-POST"
		};

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("rid", "REQUEST ID IS REQUIRED")
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			if (validate_form()) {
				AdRequestBean bean = AdRequestManager.getAdRequestBean(form.getInt("rid"));
				
				String op = request.getParameter("op");
				if(!(op == null)) {
					if (op.equals("GET_EMPLOYEES")) {
						if (StringUtils.isEmpty(form.get("location"))) {
							request.setAttribute("msg", "Please select LOCATION.");
						}
						else {
							if (!StringUtils.isEmpty(form.get("location")) && !form.hasValue("location", "-1"))
								bean.setLocation(LocationManager.getLocationBeanByDescription(form.get("location")));
						}
					}
				}
				request.setAttribute("AD_REQUEST", bean);
				path = "admin_view_ad_request.jsp";
			}
			else {
				request.setAttribute("FORM", form);
				request.setAttribute("msg", com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()));

				path = "admin_list_ad_requests.jsp";
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());

			path = "admin_list_ad_requests.jsp";
		}

		return path;
	}
}