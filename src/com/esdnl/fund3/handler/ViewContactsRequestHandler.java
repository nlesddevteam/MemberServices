package com.esdnl.fund3.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.fund3.bean.Fund3Exception;
import com.esdnl.fund3.dao.ContactManager;
import com.esdnl.servlet.RequestHandlerImpl;
public class ViewContactsRequestHandler extends RequestHandlerImpl {
	public ViewContactsRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {
		super.handleRequest(request, reponse);
	    try {
			request.setAttribute("contacts", ContactManager.getContacts());
		} catch (Fund3Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    path = "view_contacts.jsp";
	    return path;
	}
}
