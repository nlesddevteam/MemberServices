package com.awsd.travel.handler;

import java.io.IOException;
import java.util.TreeMap;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.travel.TravelClaim;
import com.awsd.travel.TravelClaimDB;
import com.esdnl.servlet.RequestHandlerImpl;

public class SearchRequestHandler extends RequestHandlerImpl {

	public SearchRequestHandler() {

		this.requiredPermissions = new String[] {
				"TRAVEL-EXPENSE-PROCESS-PAYMENT-VIEW", "TRAVEL-CLAIM-SEARCH"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		String srch_txt = "", srch_type = "";
		String path = "";
		TreeMap<Integer, Vector<TravelClaim>> map = null;

		srch_txt = request.getParameter("txt");
		if ((srch_txt != null) && !srch_txt.equals("")) {
			srch_type = request.getParameter("type");
			if ((srch_type != null) && !srch_type.equals("")) {
				if (srch_type.equalsIgnoreCase("NAME")) {
					String[] names = srch_txt.trim().split(" ");
					System.out.println("#Names: " + names.length);
					if (names.length <= 1) {
						map = TravelClaimDB.searchByName(srch_txt.trim().toUpperCase(), srch_txt.trim().toUpperCase());

						if (map.size() < 1)
							path = "claim_error.jsp?msg=Search found no matches.";
						else
							path = "searchresults.jsp";

					}
					else if (names.length == 2) {
						map = TravelClaimDB.searchByName(names[0].trim().toUpperCase(), names[1].trim().toUpperCase());

						if (map.size() < 1)
							path = "claim_error.jsp?msg=Search found no matches.";
						else
							path = "searchresults.jsp";
					}
					else
						path = "claim_error.jsp?msg=Only FIRSTNAME, LASTNAME, or FIRST and LASTNAME can be entered in the search text.";

					if (map != null)
						request.setAttribute("SEARCH_RESULTS", map);
				}
				else if (srch_type.equalsIgnoreCase("VENDOR")) {
					map = TravelClaimDB.searchByVendorNumber(srch_txt.trim().toUpperCase());

					if (map.size() < 1)
						path = "claim_error.jsp?msg=Search found no matches.";
					else
						path = "searchresults.jsp";

					if (map != null)
						request.setAttribute("SEARCH_RESULTS", map);
				}
				else
					path = "claim_error.jsp?msg=Invalid search type.";
			}
			else {
				path = "claim_error.jsp?msg=Search type must be selected.";
			}
		}
		else {
			path = "claim_error.jsp?msg=Search text is required.";
		}

		return path;
	}
}