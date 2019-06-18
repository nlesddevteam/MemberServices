package com.esdnl.fund3.handler;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.fund3.bean.ContactBean;
import com.esdnl.fund3.dao.ContactManager;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.school.bean.SchoolZoneBean;
import com.nlesd.school.service.SchoolZoneService;

public class UpdateContactRequestHandler extends RequestHandlerImpl {
	public UpdateContactRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {
		super.handleRequest(request, reponse);
		try {
				
				ContactBean cb = new ContactBean();
				cb.setId(form.getInt("id"));
				cb.setContactName(form.get("contactname"));
				cb.setContactTitle(form.get("contacttitle"));
				cb.setContactPhone(form.get("contactphone"));
				cb.setContactEmail(form.get("contactemail"));
				cb.setContactZone(SchoolZoneService.getSchoolZoneBean(Integer.parseInt(form.get("contactzone").toString())));
				cb.setIsActive(Integer.parseInt(form.get("isactive").toString()));
				ContactManager.updateContact(cb);
				request.setAttribute("msg", "Contact has been updated!");
				request.setAttribute("contact", cb);
				Collection<SchoolZoneBean>  list = SchoolZoneService.getSchoolZoneBeans();
				request.setAttribute("zones", list);
				path = "view_contact_details.jsp";
				
			

		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			Collection<SchoolZoneBean>  list = SchoolZoneService.getSchoolZoneBeans();
			request.setAttribute("regions", list);
			path = "view_contact_details.jsp";
		}
		return path;
	}


}
