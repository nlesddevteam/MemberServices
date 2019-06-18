package com.nlesd.eecd.handler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.eecd.bean.EECDAreaBean;
import com.nlesd.eecd.bean.EECDTeacherAreaBean;
import com.nlesd.eecd.dao.EECDAreaManager;
import com.nlesd.eecd.dao.EECDTeacherAreaManager;
public class ViewEECDRequestHandler extends RequestHandlerImpl {
	public ViewEECDRequestHandler() {

	}
	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {
		super.handleRequest(request, response);
		ArrayList<EECDAreaBean> list = new ArrayList<EECDAreaBean>();
		list = EECDAreaManager.getAllEECDAreasByPID(usr.getPersonnel().getPersonnelID());
		request.setAttribute("areas", list);
		//now we get their selections if any
		TreeMap<Integer,EECDTeacherAreaBean>talist = new TreeMap<Integer,EECDTeacherAreaBean>();
		talist=EECDTeacherAreaManager.getAllEECDAreas(usr.getPersonnel().getPersonnelID());
		if(talist.isEmpty()){
			request.setAttribute("firstsave", "Y");
		}else{
			request.setAttribute("firstsave", "N");
			StringBuilder sb = new StringBuilder();
			for(Map.Entry<Integer,EECDTeacherAreaBean> entry : talist.entrySet()) {
				  if(sb.length() > 0){
					  sb.append("," + ":" + entry.getValue().getAreaId() +":");
				  }else{
					  sb.append(":" + entry.getValue().getAreaId() +":");
				  }
				}
			request.setAttribute("teacherareas",sb.toString());
		}
		
			
		return "view_eecd.jsp";
	}

}