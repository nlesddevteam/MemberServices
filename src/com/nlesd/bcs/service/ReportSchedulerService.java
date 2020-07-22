package com.nlesd.bcs.service;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Timer;
import com.awsd.mail.bean.AlertBean;
import com.awsd.mail.bean.EmailException;
import com.nlesd.bcs.worker.ScheduleReportsWorker;
public class ReportSchedulerService {

	public static Timer exporter = null;
	public static final long REPORT_WAIT_PERIOD = 604800000;
	static {
		try {

			start();
		}
		catch (Exception e) {
			e.printStackTrace(System.err);

			try {
				(new AlertBean(e)).send();
			}
			catch (EmailException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static void stop() {

		if (exporter != null) {
			exporter.cancel();
			exporter = null;
		}
	}

	public static void start() {
		exporter = new Timer();	
		exporter.scheduleAtFixedRate(new ScheduleReportsWorker(), calculateDelay(), REPORT_WAIT_PERIOD);
		Date newd = new Date();
		System.out.println("starting service:" + newd);
	}

	public static void restart() {

		stop();

		start();
	}
	public static long calculateDelay() {
		long ldelay=0;
		//determine day of the week first
		int daysoffset=0;
		LocalDateTime currentDate = LocalDateTime.now();
		DayOfWeek dayofweek = currentDate.getDayOfWeek();
		switch(dayofweek) {
		case  SUNDAY:
				daysoffset=5;
			break;
		case  MONDAY:
			daysoffset=4;
		break;
		case  TUESDAY:
			daysoffset=3;
		break;
		case  WEDNESDAY:
			daysoffset=2;
		break;
		case  THURSDAY:
			daysoffset=1;
		break;
		case  FRIDAY:
			daysoffset=0;
		break;
		case  SATURDAY:
			daysoffset=6;
		break;
		
		}
		
		//now we add the offset and then create the date
		LocalDateTime offsetDate = currentDate.plusDays(daysoffset);
		
		//now we create the next run date
		LocalDateTime nextRunDate= LocalDateTime.of(offsetDate.getYear(), offsetDate.getMonth(),offsetDate.getDayOfMonth(), 12, 00);
		
		//now we calculate the interval
		ldelay = LocalDateTime.now().until( nextRunDate, ChronoUnit.MILLIS );
		
		
		
		return ldelay;
	}

}
