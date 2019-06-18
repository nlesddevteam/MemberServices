package com.esdnl.survey.site.thread;

import java.util.ArrayList;

import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.bean.EmailException;

public class SendSurveyInvitationThread extends Thread {

	private EmailBean template = null;
	private ArrayList<String> participants = null;

	public SendSurveyInvitationThread(EmailBean template, ArrayList<String> participants) {

		this.template = template;
		this.participants = participants;
	}

	public void run() {

		if ((template == null) || (participants == null) || (participants.size() < 1))
			return;

		for (String participant : this.participants) {
			template.setTo(participant);
			try {
				template.send();
				System.out.println("Survey invitation sent to: " + participant);
			}
			catch (EmailException e) {
				System.out.println("COULD NOT SEND Survey invitation sent to: " + participant + ", DUE TO: " + e.getMessage());
			}
		}

		System.out.println(this.participants.size() + " Survey invitations sent.");
	}
}
