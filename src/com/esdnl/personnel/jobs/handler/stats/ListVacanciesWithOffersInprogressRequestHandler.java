package com.esdnl.personnel.jobs.handler.stats;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.personnel.jobs.dao.RecommendationStatisticsManager;
import com.esdnl.servlet.RequestHandlerImpl;

public class ListVacanciesWithOffersInprogressRequestHandler extends RequestHandlerImpl {

	public ListVacanciesWithOffersInprogressRequestHandler() {

		this.requiredPermissions = new String[] {
				"PERSONNEL-ADMIN-VIEW"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {
			request.setAttribute("VACANCIES", RecommendationStatisticsManager.getRecommendationsWithOffersInprogress());
			request.setAttribute("VacancyListTitle", "Vacancies with Offers In-progress");
			path = "admin_view_stats_vacancy_list.jsp";

		}
		catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
			path = "admin_index.jsp";
		}

		return path;
	}

}
