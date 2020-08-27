package com.nlesd.bcs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.bean.BussingContractorCompanyBean;
import com.nlesd.bcs.dao.BussingContractorCompanyManager;
public class ViewCompanyInformationRequestHandler extends BCSApplicationRequestHandlerImpl{
	public ViewCompanyInformationRequestHandler() {

	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
			IOException {
		super.handleRequest(request, response);
		if (validate_form() && !(this.sessionExpired)) {
			BussingContractorBean ebean = (BussingContractorBean) request.getSession(false).getAttribute("CONTRACTOR");
			BussingContractorCompanyBean bccbean = BussingContractorCompanyManager.getBussingContractorCompanyById(ebean.getId());
			request.setAttribute("company",bccbean);
			path = "view_company_info.jsp";
		}else {
			path="contractorLogin.html?msg=Session expired, please login again.";
			return path;
		}

		return path;
	}
}

