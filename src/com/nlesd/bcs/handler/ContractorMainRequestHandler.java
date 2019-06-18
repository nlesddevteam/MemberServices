package com.nlesd.bcs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.dao.BussingContractorSystemDocumentManager;
import com.nlesd.bcs.dao.BussingContractorWarningsManager;
public class ContractorMainRequestHandler extends BCSApplicationRequestHandlerImpl {
	public ContractorMainRequestHandler() {

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
	    	//BussingContractorCompanyBean bccbean = BussingContractorCompanyManager.getBussingContractorCompanyById(ebean.getId());
	    	//request.setAttribute("company",bccbean);
	    	//get list of employee warnings
	    	request.setAttribute("ewarnings", BussingContractorWarningsManager.getEmployeeWarnings(ebean.getId()));
	    	//get list of vehicle warnings
	    	request.setAttribute("vwarnings", BussingContractorWarningsManager.getVehicleWarnings(ebean.getId())); 
	    	//get list of sys docs warnings
	    	request.setAttribute("dwarnings", BussingContractorSystemDocumentManager.getBussingContractorSystemDocumentsMessages()); 
	  		path = "contractor_main.jsp";
	      }
		
		return path;
	}
}
