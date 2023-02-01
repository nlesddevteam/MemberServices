package com.nlesd.icfreg.handler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.icfreg.bean.IcfRegistrationPeriodBean;
import com.nlesd.icfreg.dao.IcfRegistrationPeriodManager;

public class ViewIcfRegRequestHandler extends RequestHandlerImpl {

	public ViewIcfRegRequestHandler() {

		this.requiredPermissions = new String[] {
			"ICF-REGISTRATION-ADMIN-VIEW"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);
		//get school year lists
		Date d = new Date();
		//Calendar calendar = new GregorianCalendar();
		//calendar.setTime(d);
		//Integer cyear = calendar.get(Calendar.YEAR);
		//Integer cmonth = calendar.get(Calendar.MONTH);
		Integer cyear = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(d)).getYear();
		Integer cmonth = LocalDate.parse(new SimpleDateFormat("yyyy-MM-dd").format(d)).getMonthValue();
		
		ArrayList<String> yrs = new ArrayList<>();
		if(cmonth < 7) {
			Integer baseyear = cyear-1;
			String shortyear = cyear.toString();
			for(int i=0; i <5;i++) {
				if(i == 0) {
					yrs.add(baseyear + "-" + shortyear.substring(2, 4));
				}else {
					Integer x = Integer.parseInt(shortyear) + i;
					yrs.add(baseyear+i + "-" + x.toString().substring(2,4));
				}
			}
		}else {
			Integer baseyear = cyear;
			String shortyear = cyear.toString();
			for(int i=0; i <5;i++) {
				if(i == 0) {
					yrs.add(cyear + "-" + shortyear);
				}else {
					Integer x = Integer.parseInt(shortyear) + i;
					yrs.add(baseyear+i + "-" + x.toString().substring(2,4));
				}
			}
		}
		
		request.setAttribute("schyrs", yrs);
		ArrayList<IcfRegistrationPeriodBean> alist = IcfRegistrationPeriodManager.getRegistrationPeriods();
		request.setAttribute("periods", alist);

		return "index.jsp";
	}
}
