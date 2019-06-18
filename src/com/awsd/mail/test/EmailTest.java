package com.awsd.mail.test;

import com.awsd.mail.bean.EmailBean;

public class EmailTest {

	public static void main(String[] args) {

		try {
			EmailBean email = new EmailBean();

			email.setTo("chris'crane@nlesd.ca");
			email.setBody("Sinlge Quote Test");
			email.setSubject("Sinlge Quote Test");

			email.send();

		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

}
