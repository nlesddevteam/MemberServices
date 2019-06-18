package com.nlesd.bcs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.dao.BussingContractorDocumentManager;
import com.nlesd.bcs.dao.DropdownManager;
public class ViewContractorDocumentsRequestHandler extends BCSApplicationRequestHandlerImpl{
	public ViewContractorDocumentsRequestHandler() {

	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		
		BussingContractorBean ebean = (BussingContractorBean) request.getSession(false).getAttribute("CONTRACTOR");
		if(ebean == null)
	      {
	        path = "login.jsp";
	      }else{
	    	request.setAttribute("dtypes", DropdownManager.getDropdownValues(11));
	    	request.setAttribute("documents",BussingContractorDocumentManager.getBussingContractorDocumentsById(ebean.getId()));
	    	 //now we set the rel path
	    	  request.setAttribute("spath",request.getContextPath());
	  		path = "view_contractor_documents.jsp";
	      }
		
		return path;
	}
}

