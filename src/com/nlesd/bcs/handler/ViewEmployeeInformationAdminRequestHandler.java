package com.nlesd.bcs.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.awsd.security.User;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.bean.BussingContractorEmployeeBean;
import com.nlesd.bcs.constants.BoardOwnedContractorsConstant;
import com.nlesd.bcs.constants.DropdownTypeConstant;
import com.nlesd.bcs.dao.BussingContractorEmployeeManager;
import com.nlesd.bcs.dao.BussingContractorManager;
import com.nlesd.bcs.dao.BussingContractorSystemEmployeeTrainingManager;
import com.nlesd.bcs.dao.BussingContractorSystemLetterOnFileManager;
import com.nlesd.bcs.dao.DropdownManager;
public class ViewEmployeeInformationAdminRequestHandler extends RequestHandlerImpl{
	public ViewEmployeeInformationAdminRequestHandler() {

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
		  BussingContractorEmployeeBean vbean = new BussingContractorEmployeeBean();
		  //update
		  if(form.getInt("vid") < 0){
			  vbean.setId(-1);
		  }else{
			  vbean = BussingContractorEmployeeManager.getBussingContractorEmployeeById(Integer.parseInt(request.getParameter("vid")));
		  }
		  
		 
		  request.setAttribute("employee", vbean);
		  //now we pass the dropdowns
		  request.setAttribute("positions", DropdownManager.getDropdownValues(DropdownTypeConstant.EPOSITION.getValue()));
		  request.setAttribute("lclasses", DropdownManager.getDropdownValuesTM(DropdownTypeConstant.LCLASS.getValue()));
		  request.setAttribute("spath",request.getContextPath());
		  request.setAttribute("training", BussingContractorSystemEmployeeTrainingManager.getEmployeeTrainingById(vbean.getId()));
		  request.setAttribute("letters", BussingContractorSystemLetterOnFileManager.getLettersOnFile(vbean.getId(), "E"));
		  request.setAttribute("dpath","/BCS/documents/employeedocs/");
		  request.setAttribute("contractors", BussingContractorManager.getAllContractors());
    	  //now we check to see if they are regional admin\
		  if((usr.getUserPermissions().containsKey("BCS-VIEW-WESTERN"))){
	        request.setAttribute("contractorid", BoardOwnedContractorsConstant.WESTERN.getValue());
	      }
		  if((usr.getUserPermissions().containsKey("BCS-VIEW-CENTRAL"))){
		        request.setAttribute("contractorid", BoardOwnedContractorsConstant.CENTRAL.getValue());
		  }
		  if((usr.getUserPermissions().containsKey("BCS-VIEW-LABRADOR"))){
		        request.setAttribute("contractorid", BoardOwnedContractorsConstant.LABRADOR.getValue());
		  }
		  request.setAttribute("ttypes", DropdownManager.getDropdownValuesTM(22));
		  request.setAttribute("tlengths", DropdownManager.getDropdownValuesTM(23));
		  path = "admin_view_employee_info.jsp";
	      
		
		return path;
	}
}
