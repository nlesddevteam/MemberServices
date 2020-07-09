package com.nlesd.bcs.handler;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.bean.BussingContractorVehicleBean;
import com.nlesd.bcs.constants.BoardOwnedContractorsConstant;
import com.nlesd.bcs.constants.VehicleStatusConstant;
import com.nlesd.bcs.dao.BussingContractorVehicleManager;
public class ViewVehiclesApprovalsRequestHandler extends RequestHandlerImpl
{
	public ViewVehiclesApprovalsRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
	}
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
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
				vehicles=BussingContractorVehicleManager.getVehiclesByStatusRegFull(VehicleStatusConstant.SUBMITTEDFORREVIEW.getValue(),cid);
				reporttitle="Vehicles Awaiting Approval";
			}else if(status.equals("a")){
				vehicles=BussingContractorVehicleManager.getVehiclesByStatusRegFull(VehicleStatusConstant.APPROVED.getValue(),cid);
				reporttitle="Vehicles Approved";
			}else if(status.equals("r")){
				vehicles=BussingContractorVehicleManager.getVehiclesByStatusRegFull(VehicleStatusConstant.REJECTED.getValue(),cid);
				reporttitle="Vehicles Rejected";
			}else if(status.equals("s")){
				vehicles=BussingContractorVehicleManager.getVehiclesByStatusRegFull(VehicleStatusConstant.SUSPENDED.getValue(),cid);
				reporttitle="Vehicles Suspended";
			}else if(status.equals("re")){
				vehicles=BussingContractorVehicleManager.getVehiclesByStatusRegFull(VehicleStatusConstant.REMOVED.getValue(),cid);
				reporttitle="Vehicles Removed";
			}else if(status.equals("ns")){
				vehicles=BussingContractorVehicleManager.getVehiclesByStatusRegFull(VehicleStatusConstant.SUBMITTED.getValue(),cid);
				reporttitle="Vehicles Not Submitted";
			}
		}else{
			vehicles = new ArrayList<BussingContractorVehicleBean>();
			if(status.equals("p")){
				vehicles=BussingContractorVehicleManager.getVehiclesByStatusFull(VehicleStatusConstant.SUBMITTEDFORREVIEW.getValue());
				reporttitle="Vehicles Awaiting Approval";
			}else if(status.equals("a")){
				vehicles=BussingContractorVehicleManager.getVehiclesByStatusFull(VehicleStatusConstant.APPROVED.getValue());
				reporttitle="Vehicles Approved";
			}else if(status.equals("r")){
				vehicles=BussingContractorVehicleManager.getVehiclesByStatusFull(VehicleStatusConstant.REJECTED.getValue());
				reporttitle="Vehicles Rejected";
			}else if(status.equals("s")){
				vehicles=BussingContractorVehicleManager.getVehiclesByStatusFull(VehicleStatusConstant.SUSPENDED.getValue());
				reporttitle="Vehicles Suspended";
			}else if(status.equals("re")){
				vehicles=BussingContractorVehicleManager.getVehiclesByStatusFull(VehicleStatusConstant.REMOVED.getValue());
				reporttitle="Vehicles Removed";
			}else if(status.equals("ns")){
				vehicles=BussingContractorVehicleManager.getVehiclesByStatusFull(VehicleStatusConstant.SUBMITTED.getValue());
				reporttitle="Vehicles Not Submitted";
			}
		}


		request.setAttribute("vehicles", vehicles);
		request.setAttribute("reporttitle", reporttitle);
		path = "view_vehicles_approvals.jsp";


		return path;
	}
}

