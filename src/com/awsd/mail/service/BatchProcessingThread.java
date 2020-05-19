package com.awsd.mail.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.Callable;

import javax.mail.MessagingException;

import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.bean.EmailException;
import com.awsd.mail.dao.EmailManager;
import com.awsd.mail.transport.SMTPAuthenticatedMail;

public class BatchProcessingThread implements Callable<Integer> {

	private List<EmailBean> batch;
	private final int MAX_ATTEMPTS;

	public BatchProcessingThread(List<EmailBean> batch, int attempts) {

		this.batch = batch;
		this.MAX_ATTEMPTS = attempts;
	}

	@Override
	public Integer call() {

		LocalDateTime start = LocalDateTime.now();

		SMTPAuthenticatedMail smtp = new SMTPAuthenticatedMail("intmail.nlesd.ca", "", "", false);
		int attempt = 0;
		int sent = 0;

		try {

			System.err.println("<<<<<< BATCH THREAD [" + this.getId() + "] PROCESSING STARTED " + start + " >>>>>");

			for (EmailBean email : batch) {
				// attempt to send
				attempt = 0;

				while (attempt++ < MAX_ATTEMPTS) {

					try {
						smtp.postMail(email.getTo(), email.getCC(), email.getBCC(), email.getSubject(), email.getBody(),
								email.getContentType(), email.getFrom(), email.getAttachments());

						EmailManager.sentEmailBean(email.getId(), email.getSMTPError());

						break;
					}
					catch (javax.mail.SendFailedException e) {

						System.err.println("BATCH THREAD [" + this.getId() + "] EMAIL SERVICE ERROR: " + e.getMessage());
						// new AlertBean(e).send();

						javax.mail.Address[] invalid_addresses = e.getInvalidAddresses();
						System.out.println("BATCH THREAD [" + this.getId() + "] EMAIL SERVICE ERROR: INVALID ADDRESSES: "
								+ ((invalid_addresses != null) ? invalid_addresses.length : 0));

						if ((invalid_addresses != null) && (invalid_addresses.length > 0)) {
							javax.mail.Address[] valid_addresses = e.getValidUnsentAddresses();
							System.out.println("BATCH THREAD [" + this.getId() + "] EMAIL SERVICE ERROR: VALID UNSENT ADDRESSES: "
									+ ((valid_addresses != null) ? valid_addresses.length : 0));

							if ((valid_addresses != null) && (valid_addresses.length > 0)) {
								email.removeInvalidAddresses(invalid_addresses);
							}
							else {
								String smtp_error = "INVALID ADDRESS [";
								for (int i = 0; i < invalid_addresses.length; i++) {
									smtp_error += (((i > 0) ? ", " : "") + invalid_addresses[i].toString());
								}
								smtp_error += "]";

								EmailManager.sentEmailBean(email.getId(), email.getSMTPError() + " " + smtp_error);
								System.out.println("\nBATCH THREAD [" + this.getId() + "] MESSAGE:\n" + email
										+ " \nSTATUS: DELETED\nERROR: " + smtp_error + "\n");
								break;
							}
						}
						else {
							System.out.println("\nBATCH THREAD [" + this.getId() + "] MESSAGE:\n" + email
									+ " \nSTATUS: IN-PROGRESS\nERROR: FAILED DUE TO " + e.getClass().toString() + "\n");

							new EmailAlertThread(e.getClass().toString() + "\n" + e.getMessage()).start();
						}
					}
					catch (javax.mail.internet.AddressException e) {
						EmailManager.sentEmailBean(email.getId(), email.getSMTPError() + " " + e.getMessage());
						System.out.println("\nBATCH THREAD [" + this.getId() + "] MESSAGE:\n" + email
								+ " \nSTATUS: DELETED\nERROR: " + e.getMessage() + "\n");
						break;
					}
					catch (MessagingException e) {
						// e.printStackTrace();

						if (e.getCause() instanceof com.sun.mail.smtp.SMTPAddressFailedException) {
							EmailManager.sentEmailBean(email.getId(), email.getSMTPError() + " " + e.getMessage());
							System.out.println("\nBATCH THREAD [" + this.getId() + "] MESSAGE:\n" + email
									+ " \nSTATUS: DELETED\nERROR: " + e.getMessage() + "\n");
							break;
						}
						else {
							System.out.println("\nBATCH THREAD [" + this.getId() + "] MESSAGE:\n" + email
									+ " \nSTATUS: IN-PROGRESS\nERROR: FAILED DUE TO: [" + e.getClass().toString() + "] " + e.getMessage()
									+ "\n");

							new EmailAlertThread(e.getClass().toString() + "\n" + e.getMessage()).start();
						}
					}
					catch (Exception e) {
						e.printStackTrace();
						new EmailAlertThread(e.getClass().toString() + "\n" + e.getMessage()).start();

						break;
					}
				}

				if (attempt == MAX_ATTEMPTS) {
					System.out.println("\nBATCH THREAD [" + this.getId() + "] MESSAGE:\n" + email
							+ " \nSTATUS: DELAYED\nERROR: FAILED AFTER " + MAX_ATTEMPTS + " ATTEMPTS.\n");
				}

				if ((++sent > 0) && ((sent % 5) == 0)) {
					System.err.println("BATCH THREAD [" + this.getId() + "]: " + sent + " EMAILS PROCESSED.");
				}
			}

			LocalDateTime end = LocalDateTime.now();
			System.err.println("<<<<<< BATCH THREAD [" + this.getId() + "] PROCESSING FINISHED " + end + " [COUNT: "
					+ this.batch.size() + ", ELAPSED TIME: " + ChronoUnit.SECONDS.between(start, end) + " seconds] >>>>>");
		}
		catch (EmailException e) {
			e.printStackTrace();

			new EmailAlertThread(e.getClass().toString() + "\n" + e.getMessage()).start();
		}

		this.batch = null;
		return sent;
	}

	private long getId() {

		return Thread.currentThread().getId();
	}

}
