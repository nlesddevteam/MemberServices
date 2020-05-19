package com.awsd.mail.service;

import java.util.Timer;

public class PersistentEmailService {

	private Timer emailTimer;

	public PersistentEmailService() {

		this(10, 15000, 30000);
	}

	public PersistentEmailService(int MAX_ATTEMPTS, long delay, long period) {

		this.emailTimer = new Timer();
		this.emailTimer.schedule(new PersistentEmailServiceTimerTask(MAX_ATTEMPTS), delay, period);
	}

	public void stopTimer() {

		this.emailTimer.cancel();
	}

	public static void main(String args[]) {

		if (args.length == 3) {
			new PersistentEmailService(Integer.parseInt(args[0]), Long.parseLong(args[1]) * 1000, Long.parseLong(args[2])
					* 1000);
		}
		else {
			new PersistentEmailService();
		}
	}

}
