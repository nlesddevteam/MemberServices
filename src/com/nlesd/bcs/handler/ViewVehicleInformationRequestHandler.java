package com.nlesd.bcs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.bcs.bean.BussingContractorVehicleBean;
import com.nlesd.bcs.constants.DropdownTypeConstant;
import com.nlesd.bcs.dao.BussingContractorVehicleDocumentManager;
import com.nlesd.bcs.dao.BussingContractorVehicleManager;
import com.nlesd.bcs.dao.DropdownManager;
public class ViewVehicleInformationRequestHandler extends BCSApplicationRequestHandlerImpl{
	public ViewVehicleInformationRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("vid")
			});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		if (validate_form() && !(this.sessionExpired)) {
	    	  BussingContractorVehicleBean vbean = new BussingContractorVehicleBean();
	    	  if(request.getParameter("vid") == null){
	    		  //add
	    		  vbean.setId(-1);
	    	  }else{
	    		  //update
	    		  vbean = BussingContractorVehicleManager.getBussingContractorVehicleById(Integer.parseInt(request.getParameter("vid")));
	    	  }
	    	  request.setAttribute("vehicle", vbean);
	    	  //now we pass the dropdowns
	    	  request.setAttribute("vmakes", DropdownManager.getDropdownValuesArrayList(DropdownTypeConstant.VMAKE.getValue()));
	    	  request.setAttribute("vmodels", DropdownManager.getDropdownValues(DropdownTypeConstant.VMODEL.getValue()));
	    	  request.setAttribute("vtypes", DropdownManager.getDropdownValues(DropdownTypeConstant.VTYPE.getValue()));
	    	  if(vbean != null){
	    		  //get the dropdown values based on the type selected
	    		  request.setAttribute("vsizes", DropdownManager.getDropdownValuesTMP(vbean.getvType()));
	    	  }
	    	  
	    	  //now we get the documents
	    	  request.setAttribute("documents",BussingContractorVehicleDocumentManager.getBussingContractorVehicleDocumentsById(vbean.getId()));
	    	  request.setAttribute("dtypes", DropdownManager.getDropdownValues(DropdownTypeConstant.VDOCUMENT.getValue()));
	    	  //now we set the rel path
	       	  request.setAttribute("spath",request.getContextPath());
	    	  request.setAttribute("dpath","/BCS/documents/vehicledocs/");
	  		path = "view_vehicle_info.jsp";
		}else {
			path="contractorLogin.html?msg=Session expired, please login again.";
			return path;		
		}
		return path;
	}
}
