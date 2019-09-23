package com.nlesd.bcs.handler;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.bcs.bean.BussingContractorSystemContractBean;
import com.nlesd.bcs.dao.BussingContractorCompanyManager;
import com.nlesd.bcs.dao.BussingContractorManager;
import com.nlesd.bcs.dao.BussingContractorSecurityManager;
import com.nlesd.bcs.dao.BussingContractorSystemContractManager;
import com.nlesd.bcs.dao.BussingContractorSystemLetterOnFileManager;
public class ViewContractorInformationAdminRequestHandler extends RequestHandlerImpl{
	public ViewContractorInformationAdminRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("cid")
			});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		if (validate_form()) {
			request.setAttribute("contractor",BussingContractorManager.getBussingContractorById(form.getInt("cid")));
	    	request.setAttribute("company",BussingContractorCompanyManager.getBussingContractorCompanyById(form.getInt("cid")));
	    	request.setAttribute("sec",BussingContractorSecurityManager.getBussingContractorSecurityById(form.getInt("cid")));
	    	request.setAttribute("letters", BussingContractorSystemLetterOnFileManager.getLettersOnFile(form.getInt("cid"), "C"));
	    	request.setAttribute("spath",request.getContextPath());
	    	ArrayList<BussingContractorSystemContractBean> list  = BussingContractorSystemContractManager.getContractsForContractor(form.getInt("cid"));
		    request.setAttribute("contracts", list);
	    	path = "admin_view_contractor_info.jsp";
		}else {
			request.setAttribute("msg", com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()));
	    	path = "index.html";	
		}
	    return path;
	}
}
