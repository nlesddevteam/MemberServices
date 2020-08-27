package com.nlesd.bcs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.dao.BussingContractorSecurityManager;
public class ViewSecurityInformationRequestHandler extends BCSApplicationRequestHandlerImpl{
	public ViewSecurityInformationRequestHandler() {

	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
			IOException {
		super.handleRequest(request, response);
		if (validate_form() && !(this.sessionExpired)) {
			BussingContractorBean ebean = (BussingContractorBean) request.getSession(false).getAttribute("CONTRACTOR");
			request.setAttribute("sec",BussingContractorSecurityManager.getBussingContractorSecurityById(ebean.getId()));
			path = "view_security_info.jsp";
		}else {
			path="contractorLogin.html?msg=Session expired, please login again.";
			return path;
		}
		return path;
	}
}
