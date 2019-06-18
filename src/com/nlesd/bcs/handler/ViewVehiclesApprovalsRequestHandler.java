package com.nlesd.bcs.handler;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.nlesd.bcs.bean.BussingContractorVehicleBean;
import com.nlesd.bcs.constants.BoardOwnedContractorsConstant;
import com.nlesd.bcs.constants.VehicleStatusConstant;
import com.nlesd.bcs.dao.BussingContractorVehicleManager;
public class ViewVehiclesApprovalsRequestHandler implements RequestHandler
{
	  public String handleRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException
	  {
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
	  //now we get the status type
	    String status = request.getParameter("status");
	    String reporttitle="";
	    ArrayList<BussingContractorVehicleBean> vehicles = new ArrayList<BussingContractorVehicleBean>();
	    if(usr.checkPermission("BCS-VIEW-WESTERN") || usr.checkPermission("BCS-VIEW-CENTRAL") || usr.checkPermission("BCS-VIEW-LABRADOR")){
	    	int cid=0;
	    	if(usr.checkPermission("BCS-VIEW-WESTERN")){
				cid = BoardOwnedContractorsConstant.WESTERN.getValue();
			}
			if(usr.checkPermission("BCS-VIEW-CENTRAL")){
				cid = BoardOwnedContractorsConstant.CENTRAL.getValue();
			}
			if(usr.checkPermission("BCS-VIEW-LABRADOR")){
				cid = BoardOwnedContractorsConstant.LABRADOR.getValue();
			}
			if(status.equals("p")){
		    	vehicles=BussingContractorVehicleManager.getVehiclesByStatusReg(VehicleStatusConstant.SUBMITTED.getValue(),cid);
		    	reporttitle="Vehicles Awaiting Approval";
		    }else if(status.equals("a")){
		    	vehicles=BussingContractorVehicleManager.getVehiclesByStatusReg(VehicleStatusConstant.APPROVED.getValue(),cid);
		    	reporttitle="Vehicles Approved";
		    }else if(status.equals("r")){
		    	vehicles=BussingContractorVehicleManager.getVehiclesByStatusReg(VehicleStatusConstant.REJECTED.getValue(),cid);
		    	reporttitle="Vehicles Rejected";
		    }else if(status.equals("s")){
		    	vehicles=BussingContractorVehicleManager.getVehiclesByStatusReg(VehicleStatusConstant.SUSPENDED.getValue(),cid);
		    	reporttitle="Vehicles Suspended";
		    }else if(status.equals("re")){
		    	vehicles=BussingContractorVehicleManager.getVehiclesByStatusReg(VehicleStatusConstant.REMOVED.getValue(),cid);
		    	reporttitle="Vehicles Removed";
		    }
	    }else{
	    	vehicles = new ArrayList<BussingContractorVehicleBean>();
		    if(status.equals("p")){
		    	vehicles=BussingContractorVehicleManager.getVehiclesByStatus(VehicleStatusConstant.SUBMITTED.getValue());
		    	reporttitle="Vehicles Awaiting Approval";
		    }else if(status.equals("a")){
		    	vehicles=BussingContractorVehicleManager.getVehiclesByStatus(VehicleStatusConstant.APPROVED.getValue());
		    	reporttitle="Vehicles Approved";
		    }else if(status.equals("r")){
		    	vehicles=BussingContractorVehicleManager.getVehiclesByStatus(VehicleStatusConstant.REJECTED.getValue());
		    	reporttitle="Vehicles Rejected";
		    }else if(status.equals("s")){
		    	vehicles=BussingContractorVehicleManager.getVehiclesByStatus(VehicleStatusConstant.SUSPENDED.getValue());
		    	reporttitle="Vehicles Suspended";
		    }else if(status.equals("re")){
		    	vehicles=BussingContractorVehicleManager.getVehiclesByStatus(VehicleStatusConstant.REMOVED.getValue());
		    	reporttitle="Vehicles Removed";
		    }
	    }
	    
	    
	    request.setAttribute("vehicles", vehicles);
	    request.setAttribute("reporttitle", reporttitle);
	    path = "view_vehicles_approvals.jsp";
	    return path;
	  }
}

