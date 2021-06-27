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
import com.nlesd.bcs.constants.BoardOwnedContractorsConstant;
import com.nlesd.bcs.constants.DropdownTypeConstant;
import com.nlesd.bcs.dao.BussingContractorManager;
import com.nlesd.bcs.dao.BussingContractorVehicleDocumentManager;
import com.nlesd.bcs.dao.BussingContractorVehicleManager;
import com.nlesd.bcs.dao.DropdownManager;
public class ViewVehicleInformationAdminRequestHandler  extends RequestHandlerImpl{
	public ViewVehicleInformationAdminRequestHandler() {
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
	    	  //check to see if this is an edit or add
			  BussingContractorVehicleBean vbean = new BussingContractorVehicleBean();
			  //update
			  vbean = BussingContractorVehicleManager.getBussingContractorVehicleById(Integer.parseInt(request.getParameter("cid")));
			  request.setAttribute("vehicle", vbean);
	    	  //now we pass the dropdowns
	    	  request.setAttribute("vmakes", DropdownManager.getDropdownValuesArrayList(DropdownTypeConstant.VMAKE.getValue()));
	    	  request.setAttribute("vmodels", DropdownManager.getDropdownValues(DropdownTypeConstant.VMODEL.getValue()));
	    	  request.setAttribute("vtypes", DropdownManager.getDropdownValues(DropdownTypeConstant.VTYPE.getValue()));
	    	  if(vbean != null){
	    		  //get the dropdown values based on the type selected
	    		  if(vbean.getvType() > 0) {
	    			  request.setAttribute("vsizes", DropdownManager.getDropdownValuesTMP(vbean.getvType()));
	    		  }
	    		  
	    	  }
	    	//now we get the documents
	    	  request.setAttribute("documents",BussingContractorVehicleDocumentManager.getBussingContractorVehicleDocumentsById(vbean.getId()));
	    	  request.setAttribute("dtypes", DropdownManager.getDropdownValues(DropdownTypeConstant.VDOCUMENT.getValue()));
	    	  //now we set the rel path
	    	  request.setAttribute("spath",request.getContextPath());
	    	  request.setAttribute("dpath","/BCS/documents/vehicledocs/");
	    	  request.setAttribute("contractors", BussingContractorManager.getAllContractors());
	    	  if((usr.getUserPermissions().containsKey("BCS-VIEW-WESTERN"))){
	  	        request.setAttribute("contractorid", BoardOwnedContractorsConstant.WESTERN.getValue());
	  	      }
	  		  if((usr.getUserPermissions().containsKey("BCS-VIEW-CENTRAL"))){
	  		        request.setAttribute("contractorid", BoardOwnedContractorsConstant.CENTRAL.getValue());
	  		  }
	  		  if((usr.getUserPermissions().containsKey("BCS-VIEW-LABRADOR"))){
	  		        request.setAttribute("contractorid", BoardOwnedContractorsConstant.LABRADOR.getValue());
	  		  }
	  		//now we add the regional/depot dropdowns
			  request.setAttribute("rcodes", DropdownManager.getDropdownValuesTM(24));
			  request.setAttribute("dcodes", DropdownManager.getDropdownValuesTM(25));
	  		  path = "admin_view_vehicle_info.jsp";
		}else {
			request.setAttribute("msg", com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()));
	    	path = "index.html";	
		}
		return path;
	}
}
