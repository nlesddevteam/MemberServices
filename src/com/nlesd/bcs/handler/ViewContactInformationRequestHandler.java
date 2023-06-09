package com.nlesd.bcs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.dao.BussingContractorManager;
public class ViewContactInformationRequestHandler extends BCSApplicationRequestHandlerImpl{
	public ViewContactInformationRequestHandler() {

	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
			IOException {
		super.handleRequest(request, response);
		if (validate_form() && !(this.sessionExpired)) {
			BussingContractorBean ebean = (BussingContractorBean) request.getSession(false).getAttribute("CONTRACTOR");
			request.setAttribute("contractor",BussingContractorManager.getBussingContractorById(ebean.getId()));
			path = "view_contact_info.jsp";
		}else {
			path="contractorLogin.html?msg=Session expired, please login again.";
			return path;
		}

		return path;
	}
}
