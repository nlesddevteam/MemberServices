package com.esdnl.webupdatesystem.newspostings.service;

import java.util.Timer;
import com.awsd.mail.bean.AlertBean;
import com.awsd.mail.bean.EmailException;
import com.esdnl.webupdatesystem.newspostings.service.task.NewPostingsExportTimerTask;

public class NewsPostingsExportService {
	public static final long WAIT_PERIOD = 300000; // 5 mins
	public static Timer exporter = null;

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

		exporter.schedule(new NewPostingsExportTimerTask(), 0, WAIT_PERIOD);
	}

	public static void restart() {

		stop();

		start();
	}
}