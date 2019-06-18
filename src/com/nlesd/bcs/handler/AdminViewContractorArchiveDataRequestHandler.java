package com.nlesd.bcs.handler;
import java.io.IOException;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.dao.BussingContractorManager;
public class AdminViewContractorArchiveDataRequestHandler extends RequestHandlerImpl {
	public AdminViewContractorArchiveDataRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		int cid = form.getInt("cid");
		TreeMap<Integer,BussingContractorBean> list = new TreeMap<Integer,BussingContractorBean>();
		BussingContractorBean ebean = BussingContractorManager.getBussingContractorById(cid);
		list.put(0, ebean);
		BussingContractorManager.getContractorArchiveRecordsById(cid, list);
		request.setAttribute("contractors", list);
		request.setAttribute("conname",ebean.getContractorName());
		return "admin_view_contractor_archive.jsp";
	}

}
