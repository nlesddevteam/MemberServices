package com.nlesd.bcs.service;
import java.util.Timer;
import com.awsd.mail.bean.AlertBean;
import com.awsd.mail.bean.EmailException;
import com.nlesd.bcs.worker.ScheduleReportsWorker;
public class ReportSchedulerService {

	public static Timer exporter = null;
	public static final long REPORT_WAIT_PERIOD = 36000000; // 60 mins

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

		exporter.schedule(new ScheduleReportsWorker(), 0, REPORT_WAIT_PERIOD);
	}

	public static void restart() {

		stop();

		start();
	}

}
