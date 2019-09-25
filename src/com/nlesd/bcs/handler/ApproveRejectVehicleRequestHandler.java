package com.nlesd.bcs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.bcs.bean.BussingContractorVehicleBean;
import com.nlesd.bcs.dao.BussingContractorVehicleDocumentManager;
import com.nlesd.bcs.dao.BussingContractorVehicleManager;
import com.nlesd.bcs.dao.DropdownManager;
public class ApproveRejectVehicleRequestHandler extends RequestHandlerImpl
{
	public ApproveRejectVehicleRequestHandler() {
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
				BussingContractorVehicleBean vbean = new BussingContractorVehicleBean();
				Integer cid =Integer.parseInt(form.get("cid"));
				vbean = BussingContractorVehicleManager.getBussingContractorVehicleById(cid);
				request.setAttribute("vehicle", vbean);
				//now we get the dropdown values for viewing
				if(vbean.getvMake() > 0){
					request.setAttribute("vmake", DropdownManager.getDropdownItemText(vbean.getvMake()));
				}
				if(vbean.getvModel() > 0){
					request.setAttribute("vmodel", DropdownManager.getDropdownItemText(vbean.getvModel()));
				}
				if(vbean.getvType() > 0){
					request.setAttribute("vtype", DropdownManager.getDropdownItemText(vbean.getvType()));
				}
				if(vbean.getvSize() > 0){
					request.setAttribute("vsize", DropdownManager.getDropdownItemText(vbean.getvSize()));
				}
				//now we get the documents
				request.setAttribute("documents",BussingContractorVehicleDocumentManager.getBussingContractorVehicleDocumentsById(vbean.getId()));
				//now we set the rel path
				request.setAttribute("spath",request.getContextPath());
				path = "approve_vehicle.jsp";
			
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
