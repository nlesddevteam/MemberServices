package com.esdnl.webmaint.highlights.handler;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.esdnl.webmaint.highlights.dao.BoardHighlightsManager;

public class DeleteHighlightsRequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		HttpSession session = null;
		User usr = null;
		File f = null;
		String path = "";
		Iterator iter = null;
		SimpleDateFormat sdf = null;
		java.util.Date dt = null;

		session = request.getSession(false);
		if ((session != null) && (session.getAttribute("usr") != null)) {
			usr = (User) session.getAttribute("usr");
			if (!(usr.getUserPermissions().containsKey("WEBMAINTENANCE-VIEW")
					&& usr.getUserPermissions().containsKey("WEBMAINTENANCE-BOARDHIGHLIGHTS"))) {
				throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
			}
		}

		if ((request.getParameter("dt") == null) || request.getParameter("dt").equals("")) {
			request.setAttribute("edit_msg", "DOC DATE IS REQUIRED");
		}
		else {
			try {
				sdf = new SimpleDateFormat("dd/MM/yyyy");
				dt = sdf.parse(request.getParameter("dt").trim());

				if (BoardHighlightsManager.deleteHighlights(dt)) {
					f = new File(session.getServletContext().getRealPath("/")
							+ "/../../nlesdweb/WebContent/board/highlights/archive/" + new SimpleDateFormat("dd_MM_yyyy").format(dt)
							+ ".pdf");

					if (f.exists())
						f.delete();
				}

				request.setAttribute("edit_msg", "HIGHLIGHTS DELETED SUCCESSFULLY");
			}
			catch (SQLException e) {
				switch (e.getErrorCode()) {
				case 1:
					request.setAttribute("edit_msg", "CATEGORY " + request.getParameter("cat_code") + " ALREADY EXISTS");
					break;
				default:
					request.setAttribute("edit_msg", e.getMessage());
				}
			}
			catch (ParseException e) {
				request.setAttribute("edit_msg", e.getMessage());
			}
		}

		request.setAttribute("HIGHLIGHTS", BoardHighlightsManager.getHighlights());
		path = "highlights.jsp";

		return path;
	}
}