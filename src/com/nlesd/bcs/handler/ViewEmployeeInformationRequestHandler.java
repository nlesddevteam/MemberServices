package com.nlesd.bcs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.bean.BussingContractorEmployeeBean;
import com.nlesd.bcs.constants.DropdownTypeConstant;
import com.nlesd.bcs.dao.BussingContractorEmployeeManager;
import com.nlesd.bcs.dao.BussingContractorSystemEmployeeTrainingManager;
import com.nlesd.bcs.dao.DropdownManager;
public class ViewEmployeeInformationRequestHandler extends BCSApplicationRequestHandlerImpl{
	public ViewEmployeeInformationRequestHandler() {
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("vid")
			});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		if (validate_form() && !(this.sessionExpired)) {
			//check to see if this is an edit or add
			 BussingContractorEmployeeBean vbean = new BussingContractorEmployeeBean();
	    	  if(request.getParameter("vid") == null){
	    		  //add
	    		  vbean.setId(-1);
	    	  }else{
	    		  //update
	    		  if(Integer.parseInt(request.getParameter("vid")) <= 0) {
	    			  vbean.setId(-1);
	    		  }else {
	    			  vbean = BussingContractorEmployeeManager.getBussingContractorEmployeeById(Integer.parseInt(request.getParameter("vid")));
	    		  }
	    		}
	    	  request.setAttribute("employee", vbean);
	    	  //now we pass the dropdowns
	    	  request.setAttribute("positions", DropdownManager.getDropdownValues(DropdownTypeConstant.EPOSITION.getValue()));
	    	  request.setAttribute("lclasses", DropdownManager.getDropdownValuesTM(DropdownTypeConstant.LCLASS.getValue()));
	    	  request.setAttribute("ttypes", DropdownManager.getDropdownValuesTM(22));
	    	  request.setAttribute("spath",request.getContextPath());
	    	  request.setAttribute("dpath","/BCS/documents/employeedocs/");
	    	  request.setAttribute("training", BussingContractorSystemEmployeeTrainingManager.getEmployeeTrainingById(vbean.getId()));
			  request.setAttribute("tlengths", DropdownManager.getDropdownValuesTM(23));
			//now we add the regional/depot dropdowns
			  request.setAttribute("rcodes", DropdownManager.getDropdownValuesTM(24));
			  request.setAttribute("dcodes", DropdownManager.getDropdownValuesTM(25));
			  request.setAttribute("bcbean", (BussingContractorBean) session.getAttribute("CONTRACTOR"));
			  request.setAttribute("convicttypes", DropdownManager.getDropdownValuesTM(26));
	    	  
	  		path = "view_employee_info.jsp";
		}else {
			path="contractorLogin.html?msg=Session expired, please login again.";
			return path;
		}
		return path;
	}
}