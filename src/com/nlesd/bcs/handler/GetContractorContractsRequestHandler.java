package com.nlesd.bcs.handler;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.bean.BussingContractorSystemContractBean;
import com.nlesd.bcs.dao.BussingContractorSystemContractManager;
public class GetContractorContractsRequestHandler extends BCSApplicationRequestHandlerImpl
{
	public GetContractorContractsRequestHandler() {

	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		if(!(this.sessionExpired)) {
			BussingContractorBean bcbean = (BussingContractorBean) request.getSession(false).getAttribute("CONTRACTOR");
			ArrayList<BussingContractorSystemContractBean> list  = BussingContractorSystemContractManager.getContractsForContractor(bcbean.getId());
			request.setAttribute("contracts", list);
			path = "view_contractor_contracts.jsp";
		}else {
			path="contractorLogin.html?msg=Session expired, please login again.";
		}
		

		return path;
	}
}
