package com.nlesd.bcs.handler;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.awsd.security.User;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.bean.BussingContractorSystemContractBean;
import com.nlesd.bcs.dao.BussingContractorCompanyManager;
import com.nlesd.bcs.dao.BussingContractorManager;
import com.nlesd.bcs.dao.BussingContractorSecurityManager;
import com.nlesd.bcs.dao.BussingContractorSystemContractManager;
import com.nlesd.bcs.dao.BussingContractorSystemLetterOnFileManager;
public class ViewContractorInformationAdminRequestHandler extends RequestHandlerImpl{
	public ViewContractorInformationAdminRequestHandler() {

	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		HttpSession session = null;
	    User usr = null;
	    String path = "";
	    
	    session = request.getSession(false);
	    if((session != null) && (session.getAttribute("usr") != null))
	    {
	      usr = (User) session.getAttribute("usr");
	      if(!(usr.getUserPermissions().containsKey("BCS-SYSTEM-ACCESS")))
	      {
	        throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
	      }
	    }
	    else
	    {
	      throw new SecurityException("User login required.");
	    }
	    request.setAttribute("contractor",BussingContractorManager.getBussingContractorById(form.getInt("cid")));
    	request.setAttribute("company",BussingContractorCompanyManager.getBussingContractorCompanyById(form.getInt("cid")));
    	request.setAttribute("sec",BussingContractorSecurityManager.getBussingContractorSecurityById(form.getInt("cid")));
    	request.setAttribute("letters", BussingContractorSystemLetterOnFileManager.getLettersOnFile(form.getInt("cid"), "C"));
    	request.setAttribute("spath",request.getContextPath());
    	ArrayList<BussingContractorSystemContractBean> list  = BussingContractorSystemContractManager.getContractsForContractor(form.getInt("cid"));
	    request.setAttribute("contracts", list);
    	path = "admin_view_contractor_info.jsp";
	      
		
		return path;
	}
}
