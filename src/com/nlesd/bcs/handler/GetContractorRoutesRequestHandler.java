package com.nlesd.bcs.handler;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.bean.BussingContractorRouteListBean;
import com.nlesd.bcs.dao.BussingContractorRouteListManager;
public class GetContractorRoutesRequestHandler extends BCSApplicationRequestHandlerImpl
{
	public GetContractorRoutesRequestHandler() {

	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		if (validate_form() && !(this.sessionExpired)) {
			BussingContractorBean bcbean = (BussingContractorBean) request.getSession(false).getAttribute("CONTRACTOR");
			ArrayList<BussingContractorRouteListBean> list  = BussingContractorRouteListManager.getContractorsRoutes(bcbean.getId());
			request.setAttribute("routes", list);
			path = "view_contractor_routes.jsp";
		}else {
				path="contractorLogin.html?msg=Session expired, please login again.";
				return path;
			}

		return path;
	}
}
