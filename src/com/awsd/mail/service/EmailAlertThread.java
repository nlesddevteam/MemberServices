package com.awsd.mail.service;

import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.transport.SMTPAuthenticatedMail;

public class EmailAlertThread extends Thread {

	private static final String[] ALERT_EMAIL_ADDRESSES = new String[] {
			"chriscrane@nlesd.ca", "rodneybatten@nlesd.ca"
	};
	private static final int ALERT_MAX_ATTEMPTS = 100;

	private String subject;
	private String message;

	public EmailAlertThread(String message) {

		this("Member Services Email Service ALERT", message);
	}

	public EmailAlertThread(String subject, String message) {

		this.subject = subject;
		this.message = message;
	}

	public void run() {

		int attempt = 0;

		SMTPAuthenticatedMail smtp = new SMTPAuthenticatedMail("24.224.234.16", "Jeeves", "zulu*1106");

		while (++attempt < ALERT_MAX_ATTEMPTS) {
			try {
				smtp.postMail(ALERT_EMAIL_ADDRESSES, null, null, subject, message, EmailBean.CONTENTTYPE_PLAIN,
						"alert@nlesd.ca", null);
				break;
			}
			catch (Exception e) {}
		}
	}
}