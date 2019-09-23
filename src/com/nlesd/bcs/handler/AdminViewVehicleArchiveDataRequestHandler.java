package com.nlesd.bcs.handler;
import java.io.IOException;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.bcs.bean.BussingContractorVehicleBean;
import com.nlesd.bcs.dao.BussingContractorVehicleManager;
public class AdminViewVehicleArchiveDataRequestHandler extends RequestHandlerImpl {
	public AdminViewVehicleArchiveDataRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("cid")
			});
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		if (validate_form()) {
			int cid = form.getInt("cid");
			TreeMap<Integer,BussingContractorVehicleBean> list = new TreeMap<Integer,BussingContractorVehicleBean>();
			BussingContractorVehicleBean ebean = BussingContractorVehicleManager.getBussingContractorVehicleById(cid);
			list.put(0, ebean);
			BussingContractorVehicleManager.getVehicleArchiveRecordsById(cid, list);
			request.setAttribute("vehicles", list);
			request.setAttribute("vehname",ebean.getvPlateNumber() + "(" + ebean.getvPlateNumber() + ")");
			request.setAttribute("conname",ebean.getBcBean().getContractorName());
			path = "admin_view_vehicle_archive.jsp";
		}else {
			request.setAttribute("msg", com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()));
	    	path = "index.html";			
		}
		return path;
	}

}
