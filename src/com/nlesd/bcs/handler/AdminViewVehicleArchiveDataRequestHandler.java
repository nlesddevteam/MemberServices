package com.nlesd.bcs.handler;
import java.io.IOException;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.bean.BussingContractorVehicleBean;
import com.nlesd.bcs.dao.BussingContractorVehicleManager;
public class AdminViewVehicleArchiveDataRequestHandler extends RequestHandlerImpl {
	public AdminViewVehicleArchiveDataRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		int cid = form.getInt("cid");
		TreeMap<Integer,BussingContractorVehicleBean> list = new TreeMap<Integer,BussingContractorVehicleBean>();
		BussingContractorVehicleBean ebean = BussingContractorVehicleManager.getBussingContractorVehicleById(cid);
		list.put(0, ebean);
		BussingContractorVehicleManager.getVehicleArchiveRecordsById(cid, list);
		request.setAttribute("vehicles", list);
		request.setAttribute("vehname",ebean.getvPlateNumber() + "(" + ebean.getvPlateNumber() + ")");
		request.setAttribute("conname",ebean.getBcBean().getContractorName());
		return "admin_view_vehicle_archive.jsp";
	}

}
