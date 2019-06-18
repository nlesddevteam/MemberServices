package com.awsd.pdreg.handler;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.awsd.pdreg.DailyCalendar;
import com.awsd.pdreg.EventException;
import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.nlesd.school.bean.SchoolZoneBean;
import com.nlesd.school.service.SchoolZoneService;

public class ViewDailyCalendarRequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cur = Calendar.getInstance(), nextM = null, prevM = null, nextD = null, prevD = null;

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

		//determine the date used to display calendar
		String dStr = request.getParameter("dt");
		if (dStr != null) {
			try {
				cur.setTime(sdf.parse(dStr));
			}
			catch (ParseException e) {
				throw new EventException(new Date(System.currentTimeMillis()), "Invalid Date Format.");
			}
		}

		SchoolZoneBean zone = null;

		if (StringUtils.isNotEmpty(request.getParameter("region-id"))) {
			int regionId = Integer.parseInt(request.getParameter("region-id"));
			if (regionId > 0) {
				zone = SchoolZoneService.getSchoolZoneBean(regionId);
			}
		}

		request.setAttribute("DailyEvents", new DailyCalendar(sdf.format(cur.getTime()), zone, true));

		//get dates for date changer links.
		nextM = (Calendar) cur.clone();
		nextM.add(Calendar.MONTH, 1);
		prevM = (Calendar) cur.clone();
		prevM.add(Calendar.MONTH, -1);
		nextD = (Calendar) cur.clone();
		nextD.add(Calendar.DATE, 1);
		prevD = (Calendar) cur.clone();
		prevD.add(Calendar.DATE, -1);

		request.setAttribute("NextMonth", sdf.format(nextM.getTime()));
		request.setAttribute("PrevMonth", sdf.format(prevM.getTime()));
		request.setAttribute("NextDay", sdf.format(nextD.getTime()));
		request.setAttribute("PrevDay", sdf.format(prevD.getTime()));
		request.setAttribute("CurrentDay", new Integer(cur.get(Calendar.DATE)));
		request.setAttribute("CurrentMonth", new Integer(cur.get(Calendar.MONTH)));
		request.setAttribute("CurrentYear", new Integer(cur.get(Calendar.YEAR)));

		return "dailyeventcalendar.jsp";
	}
}