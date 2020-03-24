package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.personnel.jobs.bean.PostTransferRoundSettingsBean;
import com.esdnl.personnel.jobs.dao.PostTransferRoundSettingsManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class UpdatePostTransferRoundSettingsRequestHandler extends RequestHandlerImpl {

	public UpdatePostTransferRoundSettingsRequestHandler() {
		this.requiredRoles = new String[] {
				"ADMINISTRATOR"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("startdate", "Start Date is required"),
				new RequiredFormElement("enddate", "End Date is required."),
				new RequiredFormElement("currentstatus", "Current Status is required."),
				new RequiredFormElement("positionlimit", "Position Limit is required.")
			});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
			IOException {
		SimpleDateFormat inSDF = new SimpleDateFormat("yyyy-MM-dd");
		super.handleRequest(request, response);
		PostTransferRoundSettingsBean rbean = new PostTransferRoundSettingsBean();
		if(validate_form()) {
			try {
				rbean.setPtrStartDate(inSDF.parse(form.get("startdate")));
				rbean.setPtrEndDate(inSDF.parse(form.get("enddate")));
				rbean.setPtrStatus(form.getInt("currentstatus"));
				rbean.setPtrPositionLimit(form.getInt("positionlimit"));
				PostTransferRoundSettingsManager.updatePostTransferRoundSettings(rbean);
				request.setAttribute("settings", rbean);
				request.setAttribute("msg", "Settings have been updated");
				path = "admin_post_transfer_round.jsp";
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			request.setAttribute("msg", validator.getErrorString());
			path = "admin_index.jsp";
		}
		return path;
	}
}
