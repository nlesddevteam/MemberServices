package com.nlesd.bcs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.awsd.security.User;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.bean.BussingContractorVehicleBean;
import com.nlesd.bcs.constants.BoardOwnedContractorsConstant;
import com.nlesd.bcs.constants.DropdownTypeConstant;
import com.nlesd.bcs.dao.BussingContractorManager;
import com.nlesd.bcs.dao.BussingContractorVehicleDocumentManager;
import com.nlesd.bcs.dao.BussingContractorVehicleManager;
import com.nlesd.bcs.dao.DropdownManager;
public class ViewVehicleInformationAdminRequestHandler  extends RequestHandlerImpl{
	public ViewVehicleInformationAdminRequestHandler() {

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
    	  //check to see if this is an edit or add
		  BussingContractorVehicleBean vbean = new BussingContractorVehicleBean();
		  //update
		  if(form.getInt("cid") < 0){
			  vbean.setId(-1);
		  }else{
			  vbean = BussingContractorVehicleManager.getBussingContractorVehicleById(Integer.parseInt(request.getParameter("cid")));
		  }
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
  		  path = "admin_view_vehicle_info.jsp";
	      
		
		return path;
	}
}
