package com.awsd.mail;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SMTPAuthenticatedMail {

	private String SMTP_HOST_NAME;
	private String SMTP_AUTH_USER;
	private String SMTP_AUTH_PWD;

	/*
	static {
		try {
			System.loadLibrary("chilkat");
		}
		catch (UnsatisfiedLinkError e) {
			System.err.println("CHILKAT Native code library failed to load.\n" + e);
			// System.exit(1);
		}
	}
	*/

	public SMTPAuthenticatedMail(String host, String auth_user, String auth_pwd) {

		this.SMTP_HOST_NAME = host;
		this.SMTP_AUTH_USER = auth_user;
		this.SMTP_AUTH_PWD = auth_pwd;
	}

	public static void main(String args[]) throws Exception {

		//SMTPAuthenticatedMail smtpMailSender = new SMTPAuthenticatedMail("24.224.234.16", "Jeeves", "zulu*1106");
		SMTPAuthenticatedMail smtpMailSender = new SMTPAuthenticatedMail("intmail.nlesd.ca", "", "");
		smtpMailSender.postMail(new String[] {
			"chris.crane@bellaliant.net"
		}, "TEST FROM JAVA", "THIS MESSAGE IS FROM MEMBER SERVICES", "ms@nlesd.ca");
		System.out.println("Sucessfully Sent mail.");
	}

	public void postMail(String recipients[], String subject, String message, String from) throws MessagingException {

		boolean debug = true;

		// Set the host smtp address
		Properties props = new Properties();
		props.put("mail.smtp.host", SMTP_HOST_NAME);
		//props.put("mail.smtp.starttls.enable", "true");
		//props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.auth", "false"); //unauthenticated

		/* UNAUTHENICATED
		Authenticator auth = new SMTPAuthenticator();
		Session session = Session.getDefaultInstance(props, auth);
		*/

		Session session = Session.getInstance(props);
		session.setDebug(debug);

		// create a message
		Message msg = new MimeMessage(session);

		// set the from and to address
		InternetAddress addressFrom = new InternetAddress(from);
		msg.setFrom(addressFrom);

		InternetAddress[] addressTo = new InternetAddress[recipients.length];
		for (int i = 0; i < recipients.length; i++) {
			if ((recipients[i] != null) && !recipients[i].equals(""))
				addressTo[i] = new InternetAddress(recipients[i]);
		}
		msg.setRecipients(Message.RecipientType.TO, addressTo);

		// Setting the Subject and Content Type
		msg.setSubject(subject);
		msg.setContent(message, "text/html");
		Transport.send(msg);
	}

	public void postMail(String recipients[], String subject, String message, String from, File[] attachments)
			throws MessagingException {

		boolean debug = false;

		// Set the host smtp address
		Properties props = new Properties();
		props.put("mail.smtp.host", SMTP_HOST_NAME);
		//props.put("mail.smtp.starttls.enable", "true");
		//props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.auth", "false"); //unauthenticated

		/* UNAUTHENICATED
		Authenticator auth = new SMTPAuthenticator();
		Session session = Session.getDefaultInstance(props, auth);
		*/

		Session session = Session.getInstance(props);
		session.setDebug(debug);

		// create a message
		Message msg = new MimeMessage(session);

		// set the from and to address
		InternetAddress addressFrom = new InternetAddress(from);
		msg.setFrom(addressFrom);

		InternetAddress[] addressTo = new InternetAddress[recipients.length];
		for (int i = 0; i < recipients.length; i++) {
			if ((recipients[i] != null) && !recipients[i].equals(""))
				addressTo[i] = new InternetAddress(recipients[i]);
		}
		msg.setRecipients(Message.RecipientType.TO, addressTo);

		// Setting the Subject and Content Type
		msg.setSubject(subject);

		// Create the message part
		BodyPart messageBodyPart = new MimeBodyPart();

		// Fill the message
		messageBodyPart.setContent(message, "text/html");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		// Part two is attachment
		DataSource source = null;
		for (int i = 0; i < attachments.length; i++) {
			messageBodyPart = new MimeBodyPart();
			source = new FileDataSource(attachments[i]);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(attachments[i].getName());
			multipart.addBodyPart(messageBodyPart);
		}

		// Put parts in message
		msg.setContent(multipart);

		// Send the message
		Transport.send(msg);
	}

	public void postMail(String to[], String cc[], String bcc[], String subject, String message, String from)
			throws MessagingException {

		boolean debug = true;

		// Set the host smtp address
		Properties props = new Properties();
		props.put("mail.smtp.host", SMTP_HOST_NAME);
		//props.put("mail.smtp.starttls.enable", "true");
		//props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.auth", "false"); //unauthenticated

		/* UNAUTHENICATED
		Authenticator auth = new SMTPAuthenticator();
		Session session = Session.getDefaultInstance(props, auth);
		*/

		Session session = Session.getInstance(props);
		session.setDebug(debug);

		// create a message
		Message msg = new MimeMessage(session);

		// set the from and to address
		InternetAddress addressFrom = new InternetAddress(from);
		msg.setFrom(addressFrom);

		InternetAddress[] addressTO = new InternetAddress[to.length];
		for (int i = 0; i < to.length; i++) {
			if ((to[i] != null) && !to[i].equals(""))
				addressTO[i] = new InternetAddress(to[i]);
		}
		msg.setRecipients(Message.RecipientType.TO, addressTO);

		if ((cc != null) && (cc.length > 0)) {
			InternetAddress[] addressCC = new InternetAddress[cc.length];
			for (int i = 0; i < cc.length; i++) {
				if ((cc[i] != null) && !cc[i].equals(""))
					addressCC[i] = new InternetAddress(cc[i]);
			}
			msg.setRecipients(Message.RecipientType.CC, addressCC);
		}

		if ((bcc != null) && (bcc.length > 0)) {
			InternetAddress[] addressBCC = new InternetAddress[bcc.length];
			for (int i = 0; i < bcc.length; i++) {
				if ((bcc[i] != null) && !bcc[i].equals(""))
					addressBCC[i] = new InternetAddress(bcc[i]);
			}
			msg.setRecipients(Message.RecipientType.BCC, addressBCC);
		}

		// Setting the Subject and Content Type
		msg.setSubject(subject);
		msg.setContent(message, "text/html");
		Transport.send(msg);
	}

	/**
	 * SimpleAuthenticator is used to do simple authentication when the SMTP
	 * server requires it.
	 */
	private class SMTPAuthenticator extends Authenticator {

		public PasswordAuthentication getPasswordAuthentication() {

			String username = SMTP_AUTH_USER;
			String password = SMTP_AUTH_PWD;
			return new PasswordAuthentication(username, password);
		}
	}
}