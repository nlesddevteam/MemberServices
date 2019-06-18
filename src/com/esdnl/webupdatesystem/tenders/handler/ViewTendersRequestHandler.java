package com.esdnl.webupdatesystem.tenders.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.tenders.bean.TenderException;
import com.esdnl.webupdatesystem.tenders.dao.TendersManager;
public class ViewTendersRequestHandler extends RequestHandlerImpl {
	public ViewTendersRequestHandler() {
		this.requiredPermissions = new String[] {
				"TENDER-VIEW"
			};
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {
		super.handleRequest(request, reponse);
	    try {
			request.setAttribute("tenders", TendersManager.getTenders());
			
		} catch (TenderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    path = "view_tenders.jsp";
	    return path;
	}
}