package com.awsd.pdreg.tag;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.awsd.pdreg.CalendarLegend;
import com.awsd.pdreg.DailyCalendar;
import com.awsd.pdreg.Event;
import com.awsd.pdreg.EventException;
import com.awsd.pdreg.MonthlyCalendar;
import com.awsd.personnel.PersonnelException;
import com.awsd.school.School;
import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.nlesd.school.bean.SchoolZoneBean;

public class DailyCalendarTagHandler extends TagSupport {

	private static final long serialVersionUID = -7200139447463398284L;

	private static final int NUM_ROWS = 4;

	private String date;
	private String printable;
	private int uid;
	private SchoolZoneBean zone;

	private static CalendarLegend legend = null;

	static {
		try {
			legend = new CalendarLegend();
		}
		catch (EventException e) {
			System.err.println(e);
			legend = null;
		}
	}

	public int doStartTag() throws JspException {

		JspWriter out = null;
		MonthlyCalendar monthly = null;
		DailyCalendar daily = null;
		Iterator iter = null;
		Event evt = null;
		Date cur = null;
		Calendar now = null;
		Calendar curcal = null;
		Calendar tmp = null;
		String color = "";
		SimpleDateFormat sdf = null;
		User usr = null;
		int i = 0;

		try {
			//System.err.println("TAG: DATE = " + date);
			sdf = new SimpleDateFormat("yyyyMMdd");
			now = Calendar.getInstance();
			cur = sdf.parse(date);
			(curcal = Calendar.getInstance()).setTime(cur);
			(tmp = Calendar.getInstance()).setTime(cur);

			//System.err.println("TAG: curcal = " + curcal);

			out = pageContext.getOut();

			monthly = (MonthlyCalendar) pageContext.getRequest().getAttribute("MonthlyEvents");
			daily = (DailyCalendar) monthly.get(date);
			iter = daily.iterator();

			if ((printable.equalsIgnoreCase("false"))) {
				out.println("<table width=\"100%\">");
			}
			else {
				out.println("<table cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">");
			}

			out.println("<tr>");
			if ((printable.equalsIgnoreCase("false"))) {
				out.println("<td bgcolor=\"#ffffff\" align=\"right\" valign=\"top\" width=\"100%\">");
			}
			else {
				out.println("<td  class=\"cell_printout_day\" bgcolor=\"#F0F0F0\" align=\"center\" valign=\"top\" width=\"100%\" height=\"12\">");
			}

			if (curcal.get(Calendar.DATE) < 10) {
				out.println("&nbsp;");
			}
			if ((printable.equalsIgnoreCase("false"))) {
				out.println("<a href=\"javascript:openWindow('dailyCalendar', 'viewDailyCalendar.html?dt=" + date
						+ (zone != null ? "&region-id=" + zone.getZoneId() : "") + "', 450, 480, 1);\">");
			}
			if ((printable.equalsIgnoreCase("false")) && (now.get(Calendar.DATE) == curcal.get(Calendar.DATE))
					&& (now.get(Calendar.MONTH) == curcal.get(Calendar.MONTH))
					&& (now.get(Calendar.YEAR) == curcal.get(Calendar.YEAR))) {
				out.println("<b><font size=\"3\" color=\"#FF0000\">");
			}
			else if ((printable.equalsIgnoreCase("false"))) {
				out.println("<b><font size=\"3\" color=\"#4682B4\">");
			}
			else {
				out.println("<font style=\"font-family:times new roman; font-size:12px; font-weight:none;\">");
			}
			out.println((new SimpleDateFormat("d")).format(cur));
			out.println("</font></b>");
			if ((printable.equalsIgnoreCase("false"))) {
				out.println("</a>");
			}
			out.println("</td>");
			out.println("</tr>");

			i = 1;
			while (iter.hasNext() || (i <= NUM_ROWS)) {
				if (iter.hasNext()) {
					evt = (Event) iter.next();

					if (evt.isCloseOutDaySession() || evt.isSchoolPDRequest() || evt.isSchoolCloseoutRequest()
							|| (evt.isPrivateCalendarEntry() && (evt.getSchedulerID() != uid))) {
						continue;
					}
				}
				else {
					evt = null;
				}

				if ((evt != null) && (i <= NUM_ROWS)) {
					out.println("<tr>");

					if ((printable.equalsIgnoreCase("false")) && (i == NUM_ROWS)) {
						out.println("<td  bgcolor=\"#ffffff\" align=\"left\" width=\"100%\">");
						out.println("<a href=\"javascript:openWindow('dailyCalendar', 'viewDailyCalendar.html?dt=" + date
								+ (zone != null ? "&region-id=" + zone.getZoneId() : "") + "', 450, 480, 1);\">");
						out.println("<b>More Events...</b>");
						out.println("</a>");
					}
					else {
						if (!evt.isPrivateCalendarEntry() || (evt.isPrivateCalendarEntry() && (evt.getSchedulerID() == uid))) {
							if ((printable.equalsIgnoreCase("false")) && (legend != null)) {
								if (evt.isDistrictCalendarEntry()) {
									color = "CCCCCC";
								}
								else if (evt.isDistrictCalendarCloseOutEntry()) {
									color = "9999FF";
								}
								else if (evt.isPrivateCalendarEntry() || evt.isHolidayCalendarEntry() || evt.isReminderCalendarEntry()) {
									color = "FFFFFF";
								}
								else if (legend.containsKey(new Integer(evt.getSchedulerID()))) {
									color = (String) legend.get(new Integer(evt.getSchedulerID()));
								}
								else {
									color = "FFFFFF";
								}
							}
							else {
								color = "FFFFFF";
							}

							if ((printable.equalsIgnoreCase("false"))) {
								out.println("<td  class='smalltext' bgcolor=\"#" + color
										+ "\" align=\"left\" valign=\"middle\" width=\"100%\">");

								//display icon
								if (evt.isPrivateCalendarEntry()) {
									out.println("<img src='images/private.gif'>&nbsp;");
								}
								else if (evt.isHolidayCalendarEntry()) {
									out.println("<img src='images/holiday.jpg'>&nbsp;");
								}
								else if (evt.isReminderCalendarEntry()) {
									out.println("<img src='images/reminder.jpg'>&nbsp;");
								}

								//display link
								if (!evt.isDistrictCalendarCloseOutEntry()) {
									if (evt.isHolidayCalendarEntry()) {
										usr = (User) pageContext.getSession().getAttribute("usr");
										try {
											if (usr.getUserPermissions().containsKey("CALENDAR-DELETE-ALL")) {
												out.println("<a href=\"javascript:openWindow('registration', 'registerEvent.html?id="
														+ evt.getEventID() + "', 400, 400, 0);\">");
											}
										}
										catch (com.awsd.security.SecurityException e) {
											System.err.println(e);
										}
									}
									else {
										out.println("<a href=\"javascript:openWindow('registration', 'registerEvent.html?id="
												+ evt.getEventID() + "', 420, 400, 1);\">");
									}
								}
								else {
									out.println("<a href=\"javascript:openWindow('closeout', 'districtCloseout.html?id="
											+ evt.getEventID() + "', 450, 465, 1);\">");
								}
							}
							else {
								out.println("<td  class=\"printout\" align=\"left\" width=\"100%\">");
								//display icon
								if (evt.isPrivateCalendarEntry()) {
									out.println("<img src='images/private.gif'>&nbsp;");
								}
								else if (evt.isHolidayCalendarEntry()) {
									out.println("<img src='images/holiday.jpg'>&nbsp;");
								}
								else if (evt.isReminderCalendarEntry()) {
									out.println("<img src='images/reminder.jpg'>&nbsp;");
								}

							}

							if (evt.isSchoolPDEntry()) {
								School s = evt.getScheduler().getSchool();
								if (s != null)
									out.print(s.getSchoolName() + " - PD");
								else
									out.print(evt.getEventName());
							}
							else
								out.print(evt.getEventName());

							if (evt.isMultiDayEvent()) {
								if (printable.equalsIgnoreCase("false"))
									out.println("&nbsp;<font size='1'><i>[Day " + evt.calcCurrentDay(cur) + " of "
											+ evt.getNumberEventDays() + "]<i></font>");
								else
									out.println();
							}

							if ((printable.equalsIgnoreCase("false"))) {
								try {
									if (evt.isHolidayCalendarEntry() && usr.getUserPermissions().containsKey("CALENDAR-DELETE-ALL")) {
										out.println("</a>");
									}
									else {
										out.println("</a>");
									}
								}
								catch (SecurityException e) {
									System.err.println(e);
								}
							}
						}
					}
					out.println("</td>");
					out.println("</tr>");
				}
				else if (i <= NUM_ROWS) {
					out.println("<tr>");
					out.println("<td  bgcolor=\"#ffffff\" align=\"left\" width=\"100%\">&nbsp;");
					out.println("</td>");
					out.println("</tr>");
				}

				i++;
			}
			out.println("</table>");
		}
		catch (EventException e) {
			throw new JspException(e.getMessage());
		}
		catch (PersonnelException e) {
			throw new JspException(e.getMessage());
		}
		catch (ParseException e) {
			throw new JspException(e.getMessage());
		}
		catch (IOException e) {
			throw new JspException(e.getMessage());
		}

		return SKIP_BODY;
	}

	public void setDate(String date) {

		this.date = date;
	}

	public void setPrintable(String printable) {

		this.printable = printable;
	}

	public void setUid(int uid) {

		this.uid = uid;
	}

	public void setZone(SchoolZoneBean zone) {

		this.zone = zone;
	}
}