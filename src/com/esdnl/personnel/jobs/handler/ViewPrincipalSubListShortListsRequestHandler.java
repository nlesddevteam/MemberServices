package com.esdnl.personnel.jobs.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.common.Utils;
import com.awsd.school.bean.RegionBean;
import com.awsd.school.bean.RegionException;
import com.awsd.school.dao.RegionManager;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.SubListBean;
import com.esdnl.personnel.jobs.constants.SubstituteListConstant;
import com.esdnl.personnel.jobs.dao.SubListManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class ViewPrincipalSubListShortListsRequestHandler extends RequestHandlerImpl {

	public ViewPrincipalSubListShortListsRequestHandler() {

		this.requiredPermissions = new String[] {
				"PERSONNEL-PRINCIPAL-VIEW", "PERSONNEL-VICEPRINCIPAL-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			RegionBean region = RegionManager.getRegionBean(usr.getPersonnel().getSchool());
			String sy = Utils.getCurrentSchoolYear();

			HashMap<SubstituteListConstant, ArrayList<SubListBean>> lists = new HashMap<SubstituteListConstant, ArrayList<SubListBean>>(2);

			for (SubstituteListConstant sl : SubstituteListConstant.ALL) {
				lists.put(sl, new ArrayList<SubListBean>(Arrays.asList(SubListManager.getSubListBeans(region, sy, sl))));
			}

			request.setAttribute("PRINCIPAL_SHORTLISTS", lists);

			path = "admin_view_principal_sublist_shortlists.jsp";
		}
		catch (RegionException e) {
			e.printStackTrace();
			request.setAttribute("msg", "Could not retrieve Job applicants.");
			path = "";
		}
		catch (JobOpportunityException e) {
			e.printStackTrace();
			request.setAttribute("msg", "Could not retrieve Job applicants.");
			path = "";
		}

		return path;
	}
}