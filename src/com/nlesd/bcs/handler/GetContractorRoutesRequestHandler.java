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
	  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException
	  {
	    String path="";
	    BussingContractorBean bcbean = (BussingContractorBean) request.getSession(false).getAttribute("CONTRACTOR");
	    ArrayList<BussingContractorRouteListBean> list  = BussingContractorRouteListManager.getContractorsRoutes(bcbean.getId());
	    request.setAttribute("routes", list);
	    path = "view_contractor_routes.jsp";
	    return path;
	  }
}
