package com.awsd.mail.bean;

import java.io.PrintWriter;
import java.io.StringWriter;

public class AlertBean extends EmailBean {

	public AlertBean(Exception e) throws EmailException {

		super();

		super.setTo(new String[] {
				"chriscrane@nlesd.ca", "rodneybatten@nlesd.ca"
		});
		super.setFrom("alert@nlesd.ca");
		super.setSubject("Member Services EXCEPTION REPORT");

		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));

		super.setBody(sw.toString());

		super.send();
	}

	public static void main(String args[]) {

		try {
			PrintWriter pw = null;

			pw.append("test");
		}
		catch (Exception e) {
			try {
				new AlertBean(e);
			}
			catch (Exception ex) {
				System.out.println("Could not send error");
			}
		}
	}

}
