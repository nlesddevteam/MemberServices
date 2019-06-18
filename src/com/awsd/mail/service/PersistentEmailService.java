package com.awsd.mail.service;

import java.util.Timer;

public class PersistentEmailService {

	private Timer emailTimer = null;

	public PersistentEmailService() {

		this(10, 60000, 300000);
	}

	public PersistentEmailService(int MAX_ATTEMPTS, long delay, long period) {

		this.emailTimer = new Timer();

		this.emailTimer.schedule(new PersistentEmailServiceTimerTask(MAX_ATTEMPTS), delay, period);

	}

	public void stopTimer() {

		this.emailTimer.cancel();
	}

	public static void main(String args[]) {

		new PersistentEmailService();
	}

}
