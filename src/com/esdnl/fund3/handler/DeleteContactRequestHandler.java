package com.esdnl.fund3.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.fund3.dao.ContactManager;
import com.esdnl.servlet.RequestHandlerImpl;
public class DeleteContactRequestHandler extends RequestHandlerImpl {
	public DeleteContactRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {
		super.handleRequest(request, reponse);
		try {
			Integer cid=Integer.parseInt(request.getParameter("cid").toString());
			ContactManager.deleteContact(cid);
			request.setAttribute("msg", "Contact has been deleted");
			request.setAttribute("contacts", ContactManager.getContacts());
			path = "viewContacts.html";
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "viewContacts.html";
		}
		return path;
	}
}
