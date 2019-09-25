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
import com.nlesd.bcs.bean.BussingContractorBean;
import com.nlesd.bcs.dao.BussingContractorManager;
public class AdminViewContractorArchiveDataRequestHandler extends RequestHandlerImpl {
	public AdminViewContractorArchiveDataRequestHandler() {
		this.requiredPermissions = new String[] {
				"BCS-SYSTEM-ACCESS"
		};
		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("cid"),
				new RequiredFormElement("selectcon")
			});
	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		if (validate_form()) {
			int cid = form.getInt("cid");
			TreeMap<Integer,BussingContractorBean> list = new TreeMap<Integer,BussingContractorBean>();
			BussingContractorBean ebean = BussingContractorManager.getBussingContractorById(cid);
			list.put(0, ebean);
			BussingContractorManager.getContractorArchiveRecordsById(cid, list);
			request.setAttribute("contractors", list);
			request.setAttribute("conname",ebean.getContractorName());
			path= "admin_view_contractor_archive.jsp";
		}else {
			request.setAttribute("msg", com.esdnl.util.StringUtils.encodeHTML(validator.getErrorString()));
	    	path = "index.html";
		}
		return path;
	}

}
