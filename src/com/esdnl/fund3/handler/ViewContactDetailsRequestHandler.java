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
public class ViewContactDetailsRequestHandler extends RequestHandlerImpl {
	public ViewContactDetailsRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {
		super.handleRequest(request, reponse);
	    try {
	    	request.setAttribute("contact", ContactManager.getContactById(Integer.parseInt(request.getParameter("id").toString())));
			Collection<SchoolZoneBean>  list = SchoolZoneService.getSchoolZoneBeans();
			request.setAttribute("zones", list);
			
		}catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Fund3Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    path = "view_contact_details.jsp";
	    return path;
	}
}
