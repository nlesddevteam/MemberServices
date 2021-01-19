package com.esdnl.webupdatesystem.schoolreviews.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.schoolreviews.dao.SchoolReviewManager;

public class ViewSchoolReviewsRequestHandler extends RequestHandlerImpl {
	public ViewSchoolReviewsRequestHandler() {
		this.requiredRoles = new String[] {
				"ADMINISTRATOR","WEB DESIGNER"
			};
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {
		super.handleRequest(request, reponse);
		try {
			//request.setAttribute("reviews",SchoolReviewManager.getSchoolReviews());
			request.setAttribute("reviews",SchoolReviewManager.getSchoolReviewsListFull());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		path = "view_school_reviews.jsp";
	    return path;
	}
}
