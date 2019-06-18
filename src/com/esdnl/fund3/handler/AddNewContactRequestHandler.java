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

public class AddNewContactRequestHandler extends RequestHandlerImpl {
	public AddNewContactRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {
		super.handleRequest(request, reponse);
		try {
			
			if(form.get("op") == null)
			{
				Collection<SchoolZoneBean>  list = SchoolZoneService.getSchoolZoneBeans();
				request.setAttribute("zones", list);
				path = "add_new_contact.jsp";
			}else{
				ContactBean cb = new ContactBean();
				cb.setContactName(form.get("contactname"));
				cb.setContactTitle(form.get("contacttitle"));
				cb.setContactPhone(form.get("contactphone"));
				cb.setContactEmail(form.get("contactemail"));
				cb.setContactZone(SchoolZoneService.getSchoolZoneBean(Integer.parseInt(form.get("contactzone").toString())));
				cb.setIsActive(Integer.parseInt(form.get("isactive").toString()));
				ContactManager.addNewContact(cb);
				request.setAttribute("msg", "Contact has been added!");
				request.setAttribute("contacts", ContactManager.getContacts());
				path = "view_contacts.jsp";
			}

		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			Collection<SchoolZoneBean>  list = SchoolZoneService.getSchoolZoneBeans();
			request.setAttribute("zones", list);
			path = "add_new_contact.jsp";
		}
		return path;
	}
}
