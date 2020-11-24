package com.esdnl.personnel.jobs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.bean.RegionBean;
import com.awsd.school.bean.RegionException;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.SubListBean;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.personnel.jobs.dao.SubListManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.school.bean.SchoolZoneBean;

public class ViewSubListShortListRequestHandler extends RequestHandlerImpl {

	public ViewSubListShortListRequestHandler() {

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("list_id")
		});

	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		ApplicantProfileBean[] profiles = null;
		SubListBean list = null;

		try {
			if (validate_form()) {
				int list_id = form.getInt("list_id");

				list = SubListManager.getSubListBean(list_id);

				if (list == null || usr == null) {
					throw new SecurityException("Ilegal access attempt sublist shortlist.");
				}

				session.setAttribute("SUBLIST", list);

				if (usr.getUserPermissions().containsKey("PERSONNEL-ADMIN-VIEW"))
					profiles = ApplicantProfileManager.getApplicantShortlist(list);
				else if (usr.getUserPermissions().containsKey("PERSONNEL-PRINCIPAL-VIEW")
						|| usr.getUserPermissions().containsKey("PERSONNEL-VICEPRINCIPAL-VIEW")) {
					SchoolZoneBean zone = usr.getPersonnel().getSchool().getZone();
					if ((zone != null) && (zone.getZoneId() == 1)) { // AVALON -- Ignore sub prefs
						RegionBean region = usr.getPersonnel().getSchool().getRegion();

						if ((region != null) && (region.getId() == 1)) { // AVALON WEST  -- Ignore sub prefs
							profiles = ApplicantProfileManager.getApplicantShortlist(list);
						}
						else {
							profiles = ApplicantProfileManager.getApplicantShortlist(list, usr.getPersonnel().getSchool());
						}
					}
					else {
						profiles = ApplicantProfileManager.getApplicantShortlist(list, usr.getPersonnel().getSchool());
					}
				}
				else {
					throw new SecurityException("Ilegal access attempt sublist shortlist.");
				}

				session.setAttribute("SUBLIST_SHORTLIST", profiles);
				path = "admin_view_sublist_applicants_shortlist.jsp";
			}
			else {
				request.setAttribute("msg", this.validator.getErrorString());
				path = "admin_view_sub_lists.jsp";
			}
		}
		catch (JobOpportunityException | RegionException e) {
			e.printStackTrace();
			request.setAttribute("msg", "Could not retrieve Job applicants.");
			path = "admin_view_sub_lists.jsp";
		}

		return path;
	}
}