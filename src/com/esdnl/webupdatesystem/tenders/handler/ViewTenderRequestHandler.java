package com.esdnl.webupdatesystem.tenders.handler;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.webupdatesystem.tenders.bean.TenderException;
import com.esdnl.webupdatesystem.tenders.constants.TenderRenewal;
import com.esdnl.webupdatesystem.tenders.constants.TenderStatus;
import com.esdnl.webupdatesystem.tenders.dao.TendersManager;
import com.nlesd.school.bean.SchoolZoneBean;
import com.nlesd.school.service.SchoolZoneService;

public class ViewTenderRequestHandler extends RequestHandlerImpl {
	public ViewTenderRequestHandler() {

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
	    	request.setAttribute("tender", TendersManager.getTenderById(Integer.parseInt(request.getParameter("id").toString())));
			Collection<SchoolZoneBean>  list = SchoolZoneService.getSchoolZoneBeans();
			request.setAttribute("regions", list);
			Map<Integer,String> statuslist = new HashMap<Integer,String>();
			for(TenderStatus t : TenderStatus.ALL)
			{
				statuslist.put(t.getValue(),t.getDescription());
			}
			request.setAttribute("statuslist", statuslist);
			request.setAttribute("renewallist", TenderRenewal.ALL);
			
		} catch (TenderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    path = "view_tender_details.jsp";
	    return path;
	}
}