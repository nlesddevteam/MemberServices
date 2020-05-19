package com.awsd.mail.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.collections4.ListUtils;

import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.bean.EmailException;
import com.awsd.mail.dao.EmailManager;

public class PersistentEmailServiceTimerTask extends TimerTask {

	public static boolean RUNNING = false;
	public static LocalDateTime start = null;

	private static ExecutorService executor = Executors.newCachedThreadPool();
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

				int batchSize = batch.size() / Runtime.getRuntime().availableProcessors();

				List<List<EmailBean>> batches = ListUtils.partition(batch, batchSize > 0 ? batchSize : 1);
				System.err.println("<<<<<< BATCH PROCESSING THREAD COUNT: " + batches.size() + "  >>>>>");

				List<Callable<Integer>> workers = new ArrayList<>();
				for (List<EmailBean> b : batches) {
					workers.add(new BatchProcessingThread(b, this.MAX_ATTEMPTS));
				}
				executor.invokeAll(workers);

				workers = null;
				batches = null;
			}
			else {
				System.out.println("<<<<<< PersistentEmailServiceTimerTask BATCH PROCESSING: EMAIL QUEUE EMPTY  >>>>>");

			}
		}
		catch (EmailException e) {
			e.printStackTrace();

			new EmailAlertThread(e.getClass().toString() + "\n" + e.getMessage()).start();

		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}

		LocalDateTime end = LocalDateTime.now();

		long diff = ChronoUnit.SECONDS.between(start, end);

		System.err.println("<<<<<< PersistentEmailServiceTimerTask BATCH PROCESSING FINISHED " + end + " [COUNT: "
				+ batch.size() + ", ELAPSED TIME: " + diff + " seconds] >>>>>");

		RUNNING = false;

		batch = null;

		System.gc();
	}
}
