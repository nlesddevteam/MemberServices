package com.esdnl.student.travel.handler;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.transport.SMTPAuthenticatedMail;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.school.SchoolDB;
import com.awsd.security.SecurityException;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.servlet.RequiredSetValueFormElement;
import com.esdnl.student.travel.bean.StudentTravelException;
import com.esdnl.student.travel.bean.TravelRequestBean;
import com.esdnl.student.travel.constant.RequestStatus;
import com.esdnl.student.travel.dao.TravelRequestManager;
import com.esdnl.util.StringUtils;

public class StudentTravelRequestAdminRequestHandler extends RequestHandlerImpl {

	public StudentTravelRequestAdminRequestHandler() {

		requiredPermissions = new String[] {
			"STUDENT-TRAVEL-ADMIN-VIEW"
		};

		validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("op"), new RequiredFormElement("id"), new RequiredFormElement("u"),
				new RequiredSetValueFormElement("op", new String[] {
						"approve", "decline"
				})
		});
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		path = "view_request.jsp";

		if (validate_form()) {
			if (form.get("u").equals(usr.getUsername())) {
				SMTPAuthenticatedMail smtp = new SMTPAuthenticatedMail("intmail.nlesd.ca", "", "", false);

				if (StringUtils.isEqual(form.get("op"), "approve")) {
					try {
						TravelRequestBean treq = TravelRequestManager.getTravelRequestBean(Integer.parseInt(form.get("id")));

						treq.setStatus(RequestStatus.APPROVED);
						treq.setActionDate(Calendar.getInstance().getTime());
						treq.setActionedBy(usr.getPersonnel().getPersonnelID());

						TravelRequestManager.updateTravelRequestBean(treq);

						Personnel tmp = PersonnelDB.getPersonnel(treq.getRequestedBy());
						smtp.postMail(
								new String[] {
										usr.getPersonnel().getEmailAddress(), tmp.getEmailAddress()
								},
								null,
								null,
								"APPROVAL: Student Travel Request - " + tmp.getSchool().getSchoolName(),
								"This Student Travel Request has been approved by "
										+ usr.getPersonnel().getFullNameReverse()
										+ "<BR><BR>"
										+ treq.toHTML()
										+ "<BR><BR><B>This trip is approved under the condition that the Board Policy on Student Travel, "
										+ "Policy IJOAB(A)  is followed.  Attached is a Student Travel Checklist to assist you in your planning.</B>"
										+ "<BR><BR>PLEASE DO NOT RESPOND TO THIS MESSAGE. THANK YOU.<br><br>" + "Member Services",
								EmailBean.CONTENTTYPE_HTML, "ms@nlesd.ca", new File[] {
										new File(ROOT_DIR + "/student/travel/email/checklist.pdf"),
										new File(ROOT_DIR + "/student/travel/itineraries/" + treq.getIteneraryFilename())
								});

						request.setAttribute("TRAVELREQUESTBEAN", treq);

						request.setAttribute("APPROVER",
								SchoolDB.getSchool(treq.getSchoolId()).getSchoolFamily().getProgramSpecialist());

						path = "view_request.jsp";
					}
					catch (StudentTravelException e) {
						e.printStackTrace(System.err);
						request.setAttribute("msg", "Could not add travel request.");
						request.setAttribute("FORM", form);
					}
					catch (Exception e) {
						e.printStackTrace(System.err);
						request.setAttribute("msg", "Could not approve travel request.");
						request.setAttribute("FORM", form);
					}
				}
				else if (StringUtils.isEqual(form.get("op"), "decline")) {
					try {
						TravelRequestBean treq = TravelRequestManager.getTravelRequestBean(Integer.parseInt(form.get("id")));

						treq.setStatus(RequestStatus.REJECTED);
						treq.setActionDate(Calendar.getInstance().getTime());
						treq.setActionedBy(usr.getPersonnel().getPersonnelID());

						TravelRequestManager.updateTravelRequestBean(treq);

						Personnel tmp = PersonnelDB.getPersonnel(treq.getRequestedBy());
						smtp.postMail(new String[] {
								usr.getPersonnel().getEmailAddress(), tmp.getEmailAddress()
						}, null, null, "DECLINED: Student Travel Request - " + tmp.getSchool().getSchoolName(),
								"This Student Travel Request has been declined by " + usr.getPersonnel().getFullNameReverse()
										+ ". Please contact for more details." + "<BR><BR>" + treq.toHTML()
										+ "<BR><BR>PLEASE DO NOT RESPOND TO THIS MESSAGE. THANK YOU.<br><br>" + "Member Services",
								EmailBean.CONTENTTYPE_HTML, "ms@nlesd.ca", new File[] {
									new File(ROOT_DIR + "/student/travel/itineraries/" + treq.getIteneraryFilename())
								});

						request.setAttribute("TRAVELREQUESTBEAN", treq);

						path = "view_request.jsp";
					}
					catch (StudentTravelException e) {
						e.printStackTrace(System.err);
						request.setAttribute("msg", "Could not add travel request.");
						request.setAttribute("FORM", form);
					}
					catch (Exception e) {
						e.printStackTrace(System.err);
						request.setAttribute("msg", "Could not approve travel request.");
						request.setAttribute("FORM", form);
					}
				}
			}
			else {
				throw new SecurityException("Illegal Access [" + usr.getUsername() + "]");
			}
		}
		else {
			request.setAttribute("FORM", form);
			request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));
			path = "index.jsp";
		}

		return path;
	}
}