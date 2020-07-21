package com.esdnl.personnel.jobs.handler.stats;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.esdnl.personnel.jobs.dao.TeacherAllocationVacancyStatisticsManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.school.bean.SchoolZoneBean;
import com.nlesd.school.service.SchoolZoneService;

public class ListVacanciesWithRecommendationRejectedRequestHandler extends RequestHandlerImpl {

	public ListVacanciesWithRecommendationRejectedRequestHandler() {

		this.requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-VIEW"
		};

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("sy"), new RequiredFormElement("zid")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			if (validate_form()) {
				SchoolZoneBean zone = SchoolZoneService.getSchoolZoneBean(form.getInt("zid"));

				if (zone != null) {
					request.setAttribute("VACANCIES",
							TeacherAllocationVacancyStatisticsManager.getVacanciesWithRecommendationRejected(form.get("sy"), zone));
					request.setAttribute("VacancyListTitle", "Vacancies with Recommendation Rejected - "
							+ StringUtils.capitaliseAllWords(zone.getZoneName()) + " Region");
					path = "admin_view_stats_vacancy_list.jsp";
				}
				else {
					request.setAttribute("msg", "Zone [id=" + form.get("zid") + "] does not exist.");
					path = "admin_index.jsp";
				}
			}
			else {
				request.setAttribute("msg", this.validator.getErrorString());
				path = "admin_index.jsp";
			}
		}
		catch (Exception e) {

		}

		return path;
	}

}
