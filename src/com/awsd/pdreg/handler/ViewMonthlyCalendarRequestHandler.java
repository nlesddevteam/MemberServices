package com.awsd.pdreg.handler;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.awsd.pdreg.EventDB;
import com.awsd.pdreg.EventException;
import com.awsd.pdreg.MonthlyCalendar;
import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.nlesd.school.bean.SchoolZoneBean;
import com.nlesd.school.service.SchoolZoneService;

public class ViewMonthlyCalendarRequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Calendar cur = Calendar.getInstance(), nextM = null, prevM = null, nextY = null, prevY = null;
		HttpSession session = null;
		User usr = null;

		session = request.getSession(false);
		if ((session != null) && (session.getAttribute("usr") != null)) {
			usr = (User) session.getAttribute("usr");
			if (!(usr.getUserPermissions().containsKey("CALENDAR-VIEW"))) {
				throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
			}
		}
		else {
			throw new SecurityException("User login required.");
		}

		String dStr = request.getParameter("dt");
		if (dStr != null) {
			try {
				cur.setTime(sdf.parse(dStr));
			}
			catch (ParseException e) {
				throw new EventException("Invalid Date Format.");
			}
		}

		
		//Basic routine for link call
		SchoolZoneBean zone = null;

		if (StringUtils.isNotEmpty(request.getParameter("region-id"))) {
			int regionId = Integer.parseInt(request.getParameter("region-id"));
			if (regionId > 0) {
				zone = SchoolZoneService.getSchoolZoneBean(regionId);				
			}
		}
		
		request.setAttribute("MonthlyEvents", new MonthlyCalendar(sdf.format(cur.getTime()), zone));
		
		//Get fixed regional values 1,2,3,4,5 to get counts for graphing
		SchoolZoneBean avalonZone = null;
		SchoolZoneBean centralZone = null;
		SchoolZoneBean westernZone = null;
		SchoolZoneBean labradorZone = null;
		SchoolZoneBean provincialZone = null;			
		
		avalonZone = SchoolZoneService.getSchoolZoneBean(1);
		centralZone = SchoolZoneService.getSchoolZoneBean(2);
		westernZone = SchoolZoneService.getSchoolZoneBean(3);
		labradorZone = SchoolZoneService.getSchoolZoneBean(4);
		provincialZone = SchoolZoneService.getSchoolZoneBean(5);
		
		
		request.setAttribute("avalonMonthlyEvents", new MonthlyCalendar(sdf.format(cur.getTime()), avalonZone));
		request.setAttribute("centralMonthlyEvents", new MonthlyCalendar(sdf.format(cur.getTime()), centralZone));
		request.setAttribute("westernMonthlyEvents", new MonthlyCalendar(sdf.format(cur.getTime()), westernZone));
		request.setAttribute("labradorMonthlyEvents", new MonthlyCalendar(sdf.format(cur.getTime()), labradorZone));
		request.setAttribute("provincialMonthlyEvents", new MonthlyCalendar(sdf.format(cur.getTime()), provincialZone));
		
		nextM = (Calendar) cur.clone();
		nextM.add(Calendar.MONTH, 1);
		prevM = (Calendar) cur.clone();
		prevM.add(Calendar.MONTH, -1);
		nextY = (Calendar) cur.clone();
		nextY.add(Calendar.YEAR, 1);
		prevY = (Calendar) cur.clone();
		prevY.add(Calendar.YEAR, -1);

		request.setAttribute("NextMonth", sdf.format(nextM.getTime()));
		request.setAttribute("PrevMonth", sdf.format(prevM.getTime()));
		request.setAttribute("NextYear", sdf.format(nextY.getTime()));
		request.setAttribute("PrevYear", sdf.format(prevY.getTime()));
		request.setAttribute("CurrentMonth", new Integer(cur.get(Calendar.MONTH)));
		request.setAttribute("CurrentYear", new Integer(cur.get(Calendar.YEAR)));

		return "monthlyeventcalendar.jsp";
	}
}