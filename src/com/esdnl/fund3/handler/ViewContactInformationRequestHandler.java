package com.esdnl.fund3.handler;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.fund3.bean.Fund3Exception;
import com.esdnl.fund3.dao.ContactManager;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.school.bean.SchoolZoneBean;
import com.nlesd.school.service.SchoolZoneService;

public class ViewContactInformationRequestHandler extends RequestHandlerImpl {
	public ViewContactInformationRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {
		super.handleRequest(request, reponse);
	    try {
			request.setAttribute("contacts", ContactManager.getActiveContacts());
			Collection<SchoolZoneBean>  list = SchoolZoneService.getSchoolZoneBeans();
			request.setAttribute("zones", list);
		} catch (Fund3Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    path = "view_contact_information.jsp";
	    return path;
	}
}
