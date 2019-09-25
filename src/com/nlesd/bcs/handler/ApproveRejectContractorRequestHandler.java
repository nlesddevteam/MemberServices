package com.nlesd.bcs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.dao.BussingContractorManager;
public class ApproveRejectContractorRequestHandler extends RequestHandlerImpl
{
	public ApproveRejectContractorRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("cid")
		});
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		if (validate_form()) {
			try {
				Integer cid =Integer.parseInt(form.get("cid"));
				BussingContractorBean contractor = BussingContractorManager.getBussingContractorById(cid);
				request.setAttribute("contractor", contractor);
				path = "approve_contractor.jsp";
			}catch(Exception e) {
				request.setAttribute("msg", e.getMessage());
		    	path = "index.html";		
			}
		}else {
			request.setAttribute("msg", com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()));
	    	path = "index.html";		
		}

		return path;
	}
}
