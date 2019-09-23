package com.nlesd.bcs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
import com.nlesd.bcs.dao.BussingContractorManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequiredFormElement;
public class AccountConfirmationRequestHandler extends PublicAccessRequestHandlerImpl{
	public AccountConfirmationRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("cid")
			});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		if (validate_form()) {
			Integer cid = Integer.parseInt(request.getParameter("cid"));
			request.setAttribute("contractor",BussingContractorManager.getBussingContractorById(cid));
		}else {
			request.setAttribute("msg","Error loading information");
		}
		
		path = "account_confirmation.jsp";
		return path;
	}
}