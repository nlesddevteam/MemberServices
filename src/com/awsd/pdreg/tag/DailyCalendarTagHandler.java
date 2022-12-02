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
import com.awsd.servlet.RequestHandler;
import com.nlesd.school.bean.SchoolZoneBean;

import lotus.notes.Session;



public class DailyCalendarTagHandler extends TagSupport {
	
	

	private static final long serialVersionUID = -7200139447463398284L;

	private static final int NUM_ROWS = 8;
	
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



	public int doStartTag() throws JspException  {
	    
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
		String bgcolor = "";
		String txtcolor = "";
		String regionName = "";
		String eventDays="";
		int numEventsThisDay=0;
		
		
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
			
			// ++++++++++ NORMAL VIEW ++++++++++++
			
			out.print("<div class='mainView'>");
			out.println("<table width='100%' border=0 style='font-size:11px; border-collapse:separate;border-spacing:2px;padding:1px;'>");	

			out.println("<tr>");			
			out.println("<td align='right' valign='top' width='100%'>");
			
			
			if (curcal.get(Calendar.DATE) < 10) {
				out.println("&nbsp;");
			}
			
			if ((now.get(Calendar.DATE) == curcal.get(Calendar.DATE)) && (now.get(Calendar.MONTH) == curcal.get(Calendar.MONTH)) && (now.get(Calendar.YEAR) == curcal.get(Calendar.YEAR))) {
			    out.println("<a title='Click to view todays events.' onclick='loadingData()' class='notranslate btn btn-xs btn-danger' href='viewDailyCalendar.html?dt=" + date + (zone != null ? "&region-id=" + zone.getZoneId() : "") + "'>");
			}
			
			else {
				out.println("<a title='Click to view todays events.' onclick='loadingData()' class='notranslate btn btn-xs btn-default' href='viewDailyCalendar.html?dt=" + date + (zone != null ? "&region-id=" + zone.getZoneId() : "") + "'>");
			}
			//when translating, where the numbers were strings, it translated to words rather than staying #.
			int dateToDisplay = Integer.parseInt((new SimpleDateFormat("d")).format(cur));
			//Show date now as integer.
			out.println(dateToDisplay);
			out.println("</a>");			
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
					numEventsThisDay++;
					
					
					//Check Regions of the events.
					if(evt.getEventSchoolZoneID() ==1) {						 
						out.println("<script>eA++;</script>");
					 } else if (evt.getEventSchoolZoneID() ==2) {
						 out.println("<script>eC++;</script>");
					 } else if (evt.getEventSchoolZoneID() ==3) {
						 out.println("<script> eW++;</script>");
					 } else if (evt.getEventSchoolZoneID() ==4) {
						 out.println("<script>eL++;</script>");
					 } else if (evt.getEventSchoolZoneID() ==5) {
						 out.println("<script>eP++;</script>");
							//FOS 01
					 } else if (evt.getEventSchoolZoneID() ==267) {
						 out.println("<script>eF1++;</script>");
						//FOS 02
					 } else if (evt.getEventSchoolZoneID() ==607) {
						 out.println("<script>eF2++;</script>");
						//FOS 03
					 } else if (evt.getEventSchoolZoneID() ==608) {
						 out.println("<script>eF3++;</script>");
						//FOS 04
					 } else if (evt.getEventSchoolZoneID() ==609) {
						 out.println("<script>eF4++;</script>");
						//FOS 05
					 } else if (evt.getEventSchoolZoneID() ==610) {
						 out.println("<script>eF5++;</script>");
						//FOS 06
					 } else if (evt.getEventSchoolZoneID() ==611) {
						 out.println("<script>eF6++;</script>");
						//FOS 07
					 } else if (evt.getEventSchoolZoneID() ==612) {
						 out.println("<script>eF7++;</script>");
						//FOS 08
					 } else if (evt.getEventSchoolZoneID() ==613) {
						 out.println("<script>eF8++;</script>");
						//FOS 09
					 } else if (evt.getEventSchoolZoneID() ==614) {
						 out.println("<script>eF9++;</script>");
						//FOS 10
					 } else if (evt.getEventSchoolZoneID() ==615) {
						 out.println("<script> eF10++;</script>");
						//FOS 11
					 } else if (evt.getEventSchoolZoneID() ==567) {
						 out.println("<script> eF11++;</script>");
						//FOS 12
					 } else if (evt.getEventSchoolZoneID() ==627) {
						 out.println("<script> eF12++;</script>");
						//FOS 13
					 } else if (evt.getEventSchoolZoneID() ==628) {
						 out.println("<script> eF13++;</script>");
						 
					 } else {
						 out.println("<script>unk++;</script>"); 
					 }
					
					
					
					
					out.println("<tr>");

					if ((i == NUM_ROWS)) {
						out.println("<td align='center' width='100%' style='padding-top:3px;'>");
						out.println("<a onclick='loadingData()' style='font-size:9px;' class='viewAllEvts btn btn-xs btn-success' title='View events scheduled for this day.' href='viewDailyCalendar.html?dt=" + date + (zone != null ? "&region-id=" + zone.getZoneId() : "") + "'>");
						out.println("MORE...");
						out.println("</a>");
					
					
						if(evt.isPast()) {
							bgcolor ="background-color:#f2f2f2;";
							txtcolor ="white;"; 
							out.println("<script>$('.calEvtLink').removeClass('calLnk').addClass('calLnkd');</script>");					
							out.println("<script>$('.viewAllEvts').removeClass('btn-success').addClass('btn-default').text('MORE...');</script>");
							}
					
					
					}
					else {
						if (!evt.isPrivateCalendarEntry() || (evt.isPrivateCalendarEntry() && (evt.getSchedulerID() == uid))) {
							if ((legend != null)) {
								if (evt.isDistrictCalendarEntry()) {
									color = "CCCCCC";
								}
								else if (evt.isDistrictCalendarCloseOutEntry()) {
									color = "9999FF";
								}
								else if (evt.isPrivateCalendarEntry() || evt.isHolidayCalendarEntry() || evt.isReminderCalendarEntry()) {
									color = "FFFFFF";
								}
								else if (legend.containsKey((evt.getSchedulerID()))) {
									color = (String) legend.get((evt.getSchedulerID()));
								}
								else {
									color = "FFFFFF";
								}
							}
							else {
								color = "FFFFFF";
							}
							//Check Regions of the events.
							if(evt.getEventSchoolZoneID() ==1) {
								 bgcolor ="background-color:rgba(191, 0, 0, 0.2);";
								 txtcolor ="rgba(191, 0, 0, 1);";
								 regionName ="(AVALON) ";
								 
							 } else if (evt.getEventSchoolZoneID() ==2) {
								 bgcolor ="background-color:rgba(0, 191, 0, 0.2);";
								 txtcolor ="rgba(0, 191, 0, 1);";
								 regionName ="(CENTRAL) ";
								
							 } else if (evt.getEventSchoolZoneID() ==3) {
								 bgcolor ="background-color:rgba(255, 132, 0, 0.2);";
								 txtcolor ="rgba(255, 132, 0, 1);";
								 regionName ="(WESTERN) ";
								
							 } else if (evt.getEventSchoolZoneID() ==4) {
								 bgcolor ="background-color:rgba(127, 130, 255, 0.2);";
								 txtcolor ="rgba(127, 130, 255, 1);";
								 regionName ="(LABRADOR) ";
								
							 } else if (evt.getEventSchoolZoneID() ==5) {
								 bgcolor ="background-color:rgba(128, 0, 128, 0.2);";
								 txtcolor ="rgba(128, 0, 128, 1);";
								 regionName ="(PROV) ";
								
							 } else if (evt.getEventSchoolZoneID() ==267) {
								 bgcolor ="background-color:rgba(2, 134, 209,0.2)";
								 txtcolor ="rgba(2, 134, 209, 1);";
								 regionName ="(FOS 01) ";
								
							 } else if (evt.getEventSchoolZoneID() ==607) {
								 bgcolor ="background-color:rgba(148, 39, 97,0.2)";
								 txtcolor ="rgba(148, 39, 97, 1);";
								 regionName ="(FOS 02) ";
								
							 } else if (evt.getEventSchoolZoneID() ==608) {
								 bgcolor ="background-color:rgba(169, 205, 130,0.2)";
								 txtcolor ="rgba(169, 205, 130, 1);";
								 regionName ="(FOS 03) ";
								
							 } else if (evt.getEventSchoolZoneID() ==609) {
								 bgcolor ="background-color:rgba(15, 157, 87,0.2)";
								 txtcolor ="rgba(15, 157, 87, 1);";
								 regionName ="(FOS 04) ";
								
							 } else if (evt.getEventSchoolZoneID() ==610) {
								 bgcolor ="background-color:rgba(1, 87, 155,0.2)";
								 txtcolor ="rgba(1, 87, 155, 1);";
								 regionName ="(FOS 05) ";
								 
							 } else if (evt.getEventSchoolZoneID() ==611) {
								 bgcolor ="background-color:rgba(57, 73, 171,0.2)";
								 txtcolor ="rgba(57, 73, 171, 1);";
								 regionName ="(FOS 06) ";
								
							 } else if (evt.getEventSchoolZoneID() ==612) {
								 bgcolor ="background-color:rgba(32, 126, 75,0.2)";
								 txtcolor ="rgba(32, 126, 75, 1);";
								 regionName ="(FOS 07) ";
								 
							 } else if (evt.getEventSchoolZoneID() ==613) {
								 bgcolor ="background-color:rgba(133, 123, 29,0.2)";
								 txtcolor ="rgba(133, 123, 29, 1);";
								 regionName ="(FOS 08) ";
								 
							 } else if (evt.getEventSchoolZoneID() ==614) {
								 bgcolor ="background-color:rgba(165, 39, 20,0.2)";
								 txtcolor ="rgba(165, 39, 20, 1);";
								 regionName ="(FOS 09) ";
								 
							 } else if (evt.getEventSchoolZoneID() ==615) {
								 bgcolor ="background-color:rgba(103, 58, 183,0.2)";
								 txtcolor ="rgba(103, 58, 183, 1);";
								 regionName ="(FOS 10) ";
								
							 } else if (evt.getEventSchoolZoneID() ==567) {
								 bgcolor ="background-color:rgba(249, 171, 45,0.2)";
								 txtcolor ="rgba(249, 171, 45, 1);";
								 regionName ="(FOS 11 (DSS)) ";
								
							 } else if (evt.getEventSchoolZoneID() ==627) {
								 bgcolor ="background-color:rgba(105, 83, 78,0.2)";
								 txtcolor ="rgba(105, 83, 78, 1);";
								 regionName ="(FOS 12 (DSS)) ";
								 
							 } else if (evt.getEventSchoolZoneID() ==628) {
								 bgcolor ="background-color:rgba(175, 180, 43,0.2)";
								 txtcolor ="rgba(175, 180, 43, 1);";
								 regionName ="(FOS 13 (DSS)) ";
								
							 } else {
								 bgcolor ="background-color:#FFFFFF";
								 txtcolor ="#000000;";
								 regionName ="";
							 }

							if(evt.isPast()) {
									bgcolor ="background-color:#f2f2f2;";
									txtcolor ="white;"; 
									out.println("<script>$('.calEvtLink').removeClass('calLnk').addClass('calLnkd');</script>");							
									out.println("<script>$('.viewAllEvts').removeClass('btn-success').addClass('btn-default').text('MORE...');</script>");
							}
							
									out.println("<td style='white-space:nowrap;max-width:75px;overflow:hidden;text-overflow:ellipsis;font-size:9px;color:"+txtcolor+bgcolor+"' align='left' valign='middle' width='100%'>");				
							 
								if (evt.isMultiDayEvent()) {								
										eventDays="&nbsp;(Day " + evt.calcCurrentDay(cur) + " of " + evt.getNumberEventDays() + ")";								
									} else {
										eventDays="";
									}
							 
								//display icon
								if (evt.isPrivateCalendarEntry()) {
									out.println("<span class='glyphicon glyphicon-lock'></span>");
								}
								else if (evt.isHolidayCalendarEntry()) {
									out.println("<span class='glyphicon glyphicon-gift'></span>");
								}
								else if (evt.isReminderCalendarEntry()) {
									out.println("<span class='glyphicon glyphicon-bell'></span>");
								}

								//display link
								
								if (!evt.isDistrictCalendarCloseOutEntry()) {
									if (evt.isHolidayCalendarEntry()) {
										usr = (User) pageContext.getSession().getAttribute("usr");
										try {
											if (usr.getUserPermissions().containsKey("CALENDAR-DELETE-ALL")) {
												out.println("&nbsp;<a "
														+ "onclick='loadingData()' "
														+ "class='calLnk calEvtLink' "
														+ "data-toggle='popover' "
														+ "data-html='true' "
														+ "data-trigger='hover' "
														+ "title='"+((evt.getEventName()!=null)?(evt.getEventName().replaceAll("'","").replaceAll("\"", "")):"N/A")+eventDays+"' "
														+ "data-content='"+regionName+((evt.getEventDescription()!=null)?(evt.getEventDescription().replaceAll("'","").replaceAll("\\<.*?\\>", "").replaceAll("\"", "")):"N/A")+"' "
														+ "href='registerEvent.html?id=" + evt.getEventID() + "'>");
											}
										}
										catch (com.awsd.security.SecurityException e) {
											System.err.println(e);
										}  
									}
									else {
										out.println("&nbsp;<a "
												+ "onclick='loadingData()' "
												+ "class='calLnk calEvtLink' "
												+ "data-toggle='popover' "
												+ "data-trigger='hover' "
												+ "title='"+((evt.getEventName()!=null)?(evt.getEventName().replaceAll("'","").replaceAll("\"", "")):"N/A")+eventDays+"' "
												+ "data-content='"+regionName+((evt.getEventDescription()!=null)?(evt.getEventDescription().replaceAll("\\<.*?\\>", "").replaceAll("'","").replaceAll("\"", "")):"N/A")+"' "
												+ "href='registerEvent.html?id=" + evt.getEventID() + "'>");
									}
								}
								else {
									out.println("&nbsp;<a "
											+ "onclick='loadingData()' "
											+ "class='calLnk calEvtLink' "
											+ "data-toggle='popover' "
											+ "data-trigger='hover' "
											+ "title='"+((evt.getEventName()!=null)?(evt.getEventName().replaceAll("'","").replaceAll("\"", "")):"N/A")+eventDays+"' "
											+ "data-content='"+regionName+((evt.getEventDescription()!=null)?(evt.getEventDescription().replaceAll("\\<.*?\\>", "").replaceAll("'","").replaceAll("\"", "")):"N/A")+"' "
											+ "href='districtCloseout.html?id=" + evt.getEventID() + "'>");
								}
							

							if (evt.isSchoolPDEntry()) {
								School s = evt.getScheduler().getSchool();
								if (s != null) {
									//out.print(s.getSchoolName().substring(0,Math.min(s.getSchoolName().length(), 20)) + " (PD)");
									//End on word closest to num characters instead of just characters.
									out.print(s.getSchoolName().replaceAll("(?<=.{20})\\b.*", " (PD)"));
							} else {
									//out.print(evt.getEventName().substring(0,Math.min(evt.getEventName().length(), 25)));
									out.print(evt.getEventName().replaceAll("(?<=.{50})\\b.*", ""));
							}}
							else
								//out.print(evt.getEventName().substring(0, Math.min(evt.getEventName().length(), 25)));
								out.print(regionName+ evt.getEventName().replaceAll("(?<=.{50})\\b.*", ""));

							

							
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
					out.println("</td>");
					out.println("</tr>");
				}
				else if (i <= NUM_ROWS) {
					out.println("<tr>");
					out.println("<td align=\"left\" width=\"100%\">&nbsp;");
					out.println("</td>");
					out.println("</tr>");
				} else {
					numEventsThisDay++;
					//Check Regions of the events.
					if(evt.getEventSchoolZoneID() ==1) {						 
						out.println("<script>eA++;</script>");
					 } else if (evt.getEventSchoolZoneID() ==2) {
						 out.println("<script>eC++;</script>");
					 } else if (evt.getEventSchoolZoneID() ==3) {
						 out.println("<script>eW++;</script>");
					 } else if (evt.getEventSchoolZoneID() ==4) {
						 out.println("<script>eL++;</script>");
					 } else if (evt.getEventSchoolZoneID() ==5) {
						 out.println("<script>eP++;</script>");						
					 } else if (evt.getEventSchoolZoneID() ==267) {
						 out.println("<script>eF1++;</script>");						
					 } else if (evt.getEventSchoolZoneID() ==607) {
						 out.println("<script>eF2++;</script>");				
					 } else if (evt.getEventSchoolZoneID() ==608) {
						 out.println("<script>eF3++;</script>");						
					 } else if (evt.getEventSchoolZoneID() ==609) {
						 out.println("<script>eF4++;</script>");						
					 } else if (evt.getEventSchoolZoneID() ==610) {
						 out.println("<script>eF5++;</script>");						
					 } else if (evt.getEventSchoolZoneID() ==611) {
						 out.println("<script>eF6++;</script>");						
					 } else if (evt.getEventSchoolZoneID() ==612) {
						 out.println("<script>eF7++;</script>");						
					 } else if (evt.getEventSchoolZoneID() ==613) {
						 out.println("<script>eF8++;</script>");						
					 } else if (evt.getEventSchoolZoneID() ==614) {
						 out.println("<script>eF9++;</script>");						
					 } else if (evt.getEventSchoolZoneID() ==615) {
						 out.println("<script>eF10++;</script>");						
					 } else if (evt.getEventSchoolZoneID() ==567) {
						 out.println("<script>eF11++;</script>");						
					 } else if (evt.getEventSchoolZoneID() ==627) {
						 out.println("<script>eF12++;</script>");					
					 } else if (evt.getEventSchoolZoneID() ==628) {
						 out.println("<script>eF13++;</script>");						 
					 } else {	
						 out.println("<script>unk++;</script>"); 
					 }
				}

				i++;
			}
			
			out.println("</table>");	
			if (numEventsThisDay > 0) {				
				out.print("<span style='text-align:center;float:left;font-size:8px;color:DimGrey;'>EVENTS: "+numEventsThisDay+"</span>");			
			}
			out.println("</div>");	
			
			// ++++++++++ END NORMAL VIEW ++++++++++++
			
			
			// ++++++++++ MOBILE VIEW ++++++++++++
			
			out.print("<div class='mobileView' style='text-align:center;'>");
			if ((now.get(Calendar.DATE) == curcal.get(Calendar.DATE)) && (now.get(Calendar.MONTH) == curcal.get(Calendar.MONTH)) && (now.get(Calendar.YEAR) == curcal.get(Calendar.YEAR))) {
			    out.println("<a title='Click to view todays events.' onclick='loadingData()' class='btn btn-xs btn-danger' href='viewDailyCalendar.html?dt=" + date + (zone != null ? "&region-id=" + zone.getZoneId() : "") + "'>");
			} else {
				out.println("<a title='Click to view todays events.' onclick='loadingData()' class='btn btn-xs btn-default' href='viewDailyCalendar.html?dt=" + date + (zone != null ? "&region-id=" + zone.getZoneId() : "") + "'>");
			}
			out.println((new SimpleDateFormat("d")).format(cur));
			out.println("</a>");		
			if (numEventsThisDay > 0) {				
				out.print("<div style='text-align:center;font-size:7px;color:DimGrey;'>"+numEventsThisDay+" EVENTS</div>");			
			}
			out.println("</div>");	
			
			// ++++++++++ END MOBILE VIEW ++++++++++++
			
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
