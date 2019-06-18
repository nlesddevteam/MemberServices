package com.esdnl.personnel.jobs.service;

import java.util.Timer;
import java.util.Vector;

import com.awsd.mail.bean.AlertBean;
import com.awsd.mail.bean.EmailException;
import com.esdnl.personnel.jobs.bean.JobOpportunityBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.JobOpportunityManager;
import com.esdnl.personnel.jobs.dao.worker.OpenJobsWorker;

public class OpenJobsExportService {

	public static Vector<JobOpportunityBean> OPEN_JOBS = null;

	public static Timer exporter = null;
	public static final long OPEN_JOBS_WAIT_PERIOD = 600000; // 10 mins

	static {
		try {

			OPEN_JOBS = JobOpportunityManager.getJobOpportunityBeansVector("OPEN");

			start();
		}
		catch (JobOpportunityException e) {
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

		exporter.schedule(new OpenJobsWorker(), 0, OPEN_JOBS_WAIT_PERIOD);
	}

	public static void restart() {

		stop();

		start();
	}

}
