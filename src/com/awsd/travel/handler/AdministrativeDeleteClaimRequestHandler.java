package com.awsd.travel.handler;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.personnel.Personnel;
import com.awsd.travel.TravelClaimDB;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.util.StringUtils;

public class AdministrativeDeleteClaimRequestHandler extends RequestHandlerImpl {

	public AdministrativeDeleteClaimRequestHandler() {

		this.requiredPermissions = new String[] {
				"TRAVEL-EXPENSE-ADMINISTRATIVE-DELETE"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		Personnel p = null;
		String path = "";
		StringTokenizer str_tok = null;
		TreeMap map = null;

		String id = request.getParameter("id");
		String srch_txt = request.getParameter("txt");
		String srch_type = request.getParameter("type");

		if (StringUtils.isEmpty(id)) {
			path = "claim_error.jsp?msg=Claim id is required for administrative delete.";
		}
		else
			TravelClaimDB.deleteClaim(TravelClaimDB.getClaim(Integer.parseInt(id)));

		if ((srch_txt != null) && !srch_txt.equals("")) {
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
						map = TravelClaimDB.searchByName(names[0].trim().toUpperCase(), names[1].trim());

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