package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.common.Utils;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.SubListBean;
import com.esdnl.personnel.jobs.constants.TrainingMethodConstant;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class ViewSubListShortListByTrnlvlRequestHandler extends RequestHandlerImpl {

	public ViewSubListShortListByTrnlvlRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("trnlvl_id")
		});

	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		List<ApplicantProfileBean> profiles = null;
		SubListBean list = null;

		try {
			if (validate_form()) {
				int trnlvl_id = form.getInt("trnlvl_id");

				if (usr.getUserPermissions().containsKey("PERSONNEL-PRINCIPAL-VIEW")
						|| usr.getUserPermissions().containsKey("PERSONNEL-VICEPRINCIPAL-VIEW")) {
					profiles = ApplicantProfileManager.getApplicantShortlistByTRNLVL(usr.getPersonnel().getSchool(),
							Utils.getCurrentSchoolYear(), TrainingMethodConstant.get(trnlvl_id));
				}
				else {
					throw new SecurityException("Ilegal access attempt sublist shortlist.");
				}

				session.setAttribute("SUBLIST_SHORTLIST", profiles.toArray(new ApplicantProfileBean[0]));
				path = "admin_view_sublist_applicants_shortlist.jsp";
			}
			else {
				request.setAttribute("msg", this.validator.getErrorString());
				path = "admin_view_sub_lists.jsp";
			}
		}
		catch (JobOpportunityException e) {
			e.printStackTrace();
			request.setAttribute("msg", "Could not retrieve Job applicants.");
			path = "admin_view_sub_lists.jsp";
		}

		return path;
	}
}