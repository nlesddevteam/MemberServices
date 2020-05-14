package com.awsd.mail.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import org.apache.commons.collections4.ListUtils;

import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.bean.EmailException;
import com.awsd.mail.dao.EmailManager;

public class PersistentEmailServiceTimerTask extends TimerTask {

	public static boolean RUNNING = false;
	public LocalDateTime start = null;

	private int MAX_ATTEMPTS;

	public PersistentEmailServiceTimerTask() {

		this(10);
	}

	public PersistentEmailServiceTimerTask(int MAX_ATTEMPTS) {

		this.MAX_ATTEMPTS = MAX_ATTEMPTS;

		System.err.println("<<<<<< PersistentEmailServiceTimerTask STARTED >>>>>");
	}

	public void run() {

		if (RUNNING) {
			System.err.println("<<<<<< PersistentEmailServiceTimerTask ALREADY RUNNING [TIME ELAPSED: "
					+ ChronoUnit.SECONDS.between(start, LocalDateTime.now()) + " >>>>>");
			return;
		}
		else {
			RUNNING = true;
			start = LocalDateTime.now();
		}

		System.err.println("<<<<<< BATCH PROCESSING STARTED " + start + ">>>>>");

		List<EmailBean> batch = null;

		try {
			batch = EmailManager.getNextEmailBeanBatch();

			if ((batch != null) && (batch.size() > 0)) {
				System.err.println("<<<<<< BATCH PROCESSING TOTAL BATCH SIZE: " + batch.size() + "  >>>>>");
				List<List<EmailBean>> batches = ListUtils.partition(batch, 35);
				System.err.println("<<<<<< BATCH PROCESSING BATCH COUNT: " + batches.size() + "  >>>>>");

				List<Thread> threads = new ArrayList<Thread>();
				for (List<EmailBean> b : batches) {
					Thread t = new BatchProcessingThread(b, this.MAX_ATTEMPTS);
					t.start();
					threads.add(t);
				}
				for (Thread t : threads) {
					try {
						t.join();
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			else {
				System.out.println("<<<<<< PersistentEmailServiceTimerTask BATCH PROCESSING: EMAIL QUEUE EMPTY  >>>>>");

			}
		}
		catch (EmailException e) {
			e.printStackTrace();

			new EmailAlertThread(e.getClass().toString() + "\n" + e.getMessage()).start();

		}

		LocalDateTime end = LocalDateTime.now();

		long diff = ChronoUnit.SECONDS.between(start, end);

		System.err.println("<<<<<< PersistentEmailServiceTimerTask BATCH PROCESSING FINISHED " + end + " [COUNT: "
				+ batch.size() + ", ELAPSED TIME: " + diff + " seconds] >>>>>");

		RUNNING = false;
	}
}
