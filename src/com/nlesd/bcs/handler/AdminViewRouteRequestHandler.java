package com.nlesd.bcs.handler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.bean.BussingContractorEmployeeBean;
import com.nlesd.bcs.bean.BussingContractorSystemDocumentBean;
import com.nlesd.bcs.bean.BussingContractorSystemRouteBean;
import com.nlesd.bcs.bean.BussingContractorVehicleBean;
import com.nlesd.bcs.constants.BoardOwnedContractorsConstant;
import com.nlesd.bcs.constants.EmployeeStatusConstant;
import com.nlesd.bcs.constants.VehicleStatusConstant;
import com.nlesd.bcs.dao.BussingContractorEmployeeManager;
import com.nlesd.bcs.dao.BussingContractorSystemContractManager;
import com.nlesd.bcs.dao.BussingContractorSystemDocumentManager;
import com.nlesd.bcs.dao.BussingContractorSystemRouteManager;
import com.nlesd.bcs.dao.BussingContractorVehicleManager;
import com.nlesd.bcs.dao.DropdownManager;
public class AdminViewRouteRequestHandler extends RequestHandlerImpl
{
	public AdminViewRouteRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
	}	
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		super.handleRequest(request, response);
		if (validate_form()) {
			int docid;
			if(form.exists("vid")){
				docid = form.getInt("vid");
			}else{
				docid=0;
			}
			BussingContractorSystemRouteBean bcbean = new BussingContractorSystemRouteBean();

			if(docid > 0){
				bcbean = BussingContractorSystemRouteManager.getBussingContractorSystemRouteById(docid);

			}else{
				bcbean = new BussingContractorSystemRouteBean();
			}

			TreeMap<String,Integer> items;
			//get search by values
			items = DropdownManager.getSchools();
			request.setAttribute("schools", items);
			request.setAttribute("route", bcbean);
			TreeMap<Integer,String> items2;
			items2 = DropdownManager.getDropdownValuesTM(3);
			request.setAttribute("vtypes", items2);
			items2 = DropdownManager.getDropdownValuesTM(4);
			request.setAttribute("vsizes", items2);

			//get the documents related to contract
			ArrayList<BussingContractorSystemDocumentBean> list = BussingContractorSystemDocumentManager.getBussingContractorSystemRouteDocuments(docid);
			request.setAttribute("documents",list);
			request.setAttribute("spath",request.getContextPath() + "/BCS/documents/system/routes/");
			if(usr.checkPermission("BCS-VIEW-WESTERN") || usr.checkPermission("BCS-VIEW-CENTRAL") || usr.checkPermission("BCS-VIEW-LABRADOR")){
				int cid=0;
				if(usr.checkPermission("BCS-VIEW-WESTERN")){
					cid = BoardOwnedContractorsConstant.WESTERN.getValue();
					request.setAttribute("contracts", BussingContractorSystemContractManager.getContractsReg(cid));
					request.setAttribute("drivers", BussingContractorEmployeeManager.getEmployeesByStatusReg(EmployeeStatusConstant.APPROVED.getValue(), cid));
					request.setAttribute("vehicles", BussingContractorVehicleManager.getVehiclesByStatusReg(VehicleStatusConstant.APPROVED.getValue(), cid));
				}
				if(usr.checkPermission("BCS-VIEW-CENTRAL")){
					cid = BoardOwnedContractorsConstant.CENTRAL.getValue();
					request.setAttribute("contracts", BussingContractorSystemContractManager.getContractsReg(cid));
					request.setAttribute("drivers", BussingContractorEmployeeManager.getEmployeesByStatusReg(EmployeeStatusConstant.APPROVED.getValue(), cid));
					request.setAttribute("vehicles", BussingContractorVehicleManager.getVehiclesByStatusReg(VehicleStatusConstant.APPROVED.getValue(), cid));
				}
				if(usr.checkPermission("BCS-VIEW-LABRADOR")){
					cid = BoardOwnedContractorsConstant.LABRADOR.getValue();
					request.setAttribute("contracts", BussingContractorSystemContractManager.getContractsReg(cid));
					request.setAttribute("drivers", BussingContractorEmployeeManager.getEmployeesByStatusReg(EmployeeStatusConstant.APPROVED.getValue(), cid));
					request.setAttribute("vehicles", BussingContractorVehicleManager.getVehiclesByStatusReg(VehicleStatusConstant.APPROVED.getValue(), cid));
				}
			}else{
				request.setAttribute("contracts", BussingContractorSystemContractManager.getContracts());
				//if((bcbean.getContractBean().getId() > 0)){
				if(!(bcbean.getContractBean() ==  null)){
					if(bcbean.getContractBean().getId() >0){
						if(bcbean.getContractBean().getContractHistory().getContractStatus() == 85){
							request.setAttribute("drivers", BussingContractorEmployeeManager.getEmployeesByStatusReg(EmployeeStatusConstant.APPROVED.getValue(),
									bcbean.getContractBean().getContractHistory().getContractorId()));
							request.setAttribute("vehicles", BussingContractorVehicleManager.getVehiclesByStatusReg(VehicleStatusConstant.APPROVED.getValue(),
									bcbean.getContractBean().getContractHistory().getContractorId()));
						}

					}
				}
			}

			BussingContractorEmployeeBean origbean = BussingContractorEmployeeManager.getCurrentRouteDriver(docid);
			if(origbean.getFirstName() == null){
				request.setAttribute("assigneddriver", "None Assigned");
				request.setAttribute("assigneddriverid", "-1");
			}else{
				request.setAttribute("assigneddriver", origbean.getLastName() + "," + origbean.getFirstName());
				request.setAttribute("assigneddriverid", origbean.getId());
			}
			BussingContractorVehicleBean vorigbean = BussingContractorVehicleManager.getCurrentRouteVehicle(docid);
			if(vorigbean.getvPlateNumber() == null){
				request.setAttribute("assignedvehicle", "None Assigned");
				request.setAttribute("assignedvehicleid", "-1");
			}else{
				request.setAttribute("assignedvehicle", vorigbean.getvPlateNumber() + "[" + vorigbean.getvSerialNumber() + "]");
				request.setAttribute("assignedvehicleid", vorigbean.getId());
			}
			path = "admin_view_route.jsp";
		}else {
			request.setAttribute("msg", com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()));
	    	path = "index.html";		
		}

		return path;
	}
}
