package com.esdnl.webupdatesystem.tenders.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.tenders.bean.TendersBean;
import com.esdnl.webupdatesystem.tenders.dao.TendersManager;

public class DeleteTenderRequestHandler extends RequestHandlerImpl {

	public DeleteTenderRequestHandler() {

		this.requiredRoles = new String[] {
				"ADMINISTRATOR", "WEB DESIGNER", "WEBANNOUNCMENTS-POST"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {

		super.handleRequest(request, reponse);
		try {
			Integer fileId = Integer.parseInt(request.getParameter("dtid").toString());
			TendersBean tb = TendersManager.getTenderById(fileId);
			String filelocation = "/../../nlesdweb/WebContent/includes/files/tenders/doc/";
			delete_file(filelocation, tb.getDocUploadName());
			TendersManager.deleteTender(tb.getId());

			request.setAttribute("msg", "Tender has been deleted");

			path = "viewTenders.html";
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "viewTenders.html";
		}
		return path;
	}
}
