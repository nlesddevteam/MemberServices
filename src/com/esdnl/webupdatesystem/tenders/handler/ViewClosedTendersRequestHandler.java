package com.esdnl.webupdatesystem.tenders.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.tenders.bean.TenderException;
import com.esdnl.webupdatesystem.tenders.dao.TendersManager;
public class ViewClosedTendersRequestHandler extends RequestHandlerImpl {
	public ViewClosedTendersRequestHandler() {
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
			request.setAttribute("tenders", TendersManager.getTendersFull());
			//Set to closed once they update the tenders as they should....
			//request.setAttribute("tenders", TendersManager.getTendersByStatus(2));
			
		} catch (TenderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    path = "viewClosedTenders.jsp";
	    return path;
	}
}
