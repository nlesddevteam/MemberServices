package com.esdnl.tsdoc.site.handler.admin;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.mail.bean.EmailBean;
import com.awsd.personnel.Personnel;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.tsdoc.bean.AuditActionBean;
import com.esdnl.tsdoc.bean.TSDocException;
import com.esdnl.tsdoc.bean.TrusteeGroupBean;
import com.esdnl.tsdoc.bean.TrusteeNoteAuditBean;
import com.esdnl.tsdoc.bean.TrusteeNoteBean;
import com.esdnl.tsdoc.manager.TrusteeGroupManager;
import com.esdnl.tsdoc.manager.TrusteeGroupNoteManager;
import com.esdnl.tsdoc.manager.TrusteeNoteAuditManager;
import com.esdnl.tsdoc.manager.TrusteeNoteManager;

public class AddNoteRequestHandler extends RequestHandlerImpl {

	public AddNoteRequestHandler() {

		requiredPermissions = new String[] {
			"TSDOC-ADMIN-VIEW"
		};

		validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("note-title"), new RequiredFormElement("note-text")
		});

	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse reponse)
			throws ServletException,
				IOException {

		super.handleRequest(request, reponse);

		try {

			if (form.hasValue("op", "confirm")) {
				if (validate_form()) {

					try {
						TrusteeNoteBean note = new TrusteeNoteBean();
						note.setNoteTitle(form.get("note-title"));
						note.setNoteText(form.get("note-text"));

						note = TrusteeNoteManager.addTrusteeNoteBean(note);

						if (note.getNoteId() > 0) {
							TrusteeNoteAuditBean audit = new TrusteeNoteAuditBean();
							audit.setAuditDate(Calendar.getInstance().getTime());
							audit.setNoteId(note.getNoteId());
							audit.setPersonnel(usr.getPersonnel());
							audit.setAction(AuditActionBean.ADD);
							TrusteeNoteAuditManager.addTrusteeNoteAuditBean(audit);

							if (form.exists("committee-id")) {
								TrusteeGroupBean group = null;
								for (int committeeId : form.getIntArray("committee-id")) {
									group = TrusteeGroupManager.getTrusteeGroupBean(committeeId);

									if (group != null) {
										TrusteeGroupNoteManager.addTrusteeGroupNoteBean(group, note);

										audit = new TrusteeNoteAuditBean();
										audit.setAuditDate(Calendar.getInstance().getTime());
										audit.setNoteId(note.getNoteId());
										audit.setPersonnel(usr.getPersonnel());
										audit.setAction(AuditActionBean.ACCESSGRANTED);
										audit.setActionComment("Access granted to \"" + group.getGroupName() + "\"");
										TrusteeNoteAuditManager.addTrusteeNoteAuditBean(audit);
									}
								}
							}

							for (Personnel p : TrusteeNoteManager.getNotePersonnelBeans(note)) {
								if (p.equals(usr.getPersonnel()))
									continue;

								EmailBean email = new EmailBean();
								email.setFrom("ms@nlesd.ca");
								email.setSubject("NLESD Notification: Note available for your review.");
								email.setBody("Please be advised that there is a new note available for you review located in the Trustee Secure Document Repository. "
										+ "Click link below to login. Thank you.<BR><BR><a href='https://www.nlesd.ca/MemberServices'><b>Click Here To Login</b></a><BR><BR>"
										+ "Member Services");
								email.setTo(p.getEmailAddress());
								email.send();

								audit = new TrusteeNoteAuditBean();
								audit.setAuditDate(Calendar.getInstance().getTime());
								audit.setNoteId(note.getNoteId());
								audit.setPersonnel(p);
								audit.setAction(AuditActionBean.EMAILNOTIFICATION);
								audit.setActionComment(null);
								TrusteeNoteAuditManager.addTrusteeNoteAuditBean(audit);
							}

							request.setAttribute("msg", "Note added successfully.");
						}
						else
							request.setAttribute("msg", "Note could not be added.");

						request.setAttribute("committees", TrusteeGroupManager.getTrusteeGroupBeans());

						path = "add_note.jsp";
					}
					catch (TSDocException e) {
						request.setAttribute("msg", e.getMessage());
						request.setAttribute("committees", TrusteeGroupManager.getTrusteeGroupBeans());

						path = "add_note.jsp";
					}
				}
				else {
					request.setAttribute("msg", validator.getErrorString());
					request.setAttribute("committees", TrusteeGroupManager.getTrusteeGroupBeans());

					path = "add_note.jsp";
				}
			}
			else {
				request.setAttribute("committees", TrusteeGroupManager.getTrusteeGroupBeans());

				path = "add_note.jsp";
			}
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
			path = "index.jsp";
		}

		return path;
	}

}
