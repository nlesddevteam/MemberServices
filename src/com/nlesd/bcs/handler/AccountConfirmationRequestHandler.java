package com.nlesd.bcs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.PublicAccessRequestHandlerImpl;
import com.nlesd.bcs.dao.BussingContractorManager;
public class AccountConfirmationRequestHandler extends PublicAccessRequestHandlerImpl{
	public AccountConfirmationRequestHandler() {

	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		Integer cid = Integer.parseInt(request.getParameter("cid"));
		request.setAttribute("contractor",BussingContractorManager.getBussingContractorById(cid));
		path = "account_confirmation.jsp";
		return path;
	}
}