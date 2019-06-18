package com.esdnl.student.travel.handler;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.mail.bean.EmailBean;
import com.awsd.personnel.Personnel;
import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.awsd.security.crypto.PasswordEncryption;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormElementPattern;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFileFormElement;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.servlet.RequiredPatternFormElement;
import com.esdnl.student.travel.bean.StudentTravelException;
import com.esdnl.student.travel.bean.TravelRequestBean;
import com.esdnl.student.travel.constant.RequestStatus;
import com.esdnl.student.travel.dao.TravelRequestManager;
import com.esdnl.util.StringUtils;

public class AddStudentTravelRequestRequestHandler extends RequestHandlerImpl {

	public AddStudentTravelRequestRequestHandler() {

		requiredPermissions = new String[] {
			"STUDENT-TRAVEL-PRINCIPAL-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		path = "add_request.jsp";

		if (StringUtils.isEqual(form.get("op"), "ADD")) {
			validator = new FormValidator(new FormElement[] {
					new RequiredFormElement("destination"), new RequiredFormElement("departure_date"),
					new RequiredFormElement("return_date"), new RequiredFormElement("rational"),
					new RequiredFormElement("grades"), new RequiredFormElement("num_students"),
					new RequiredFormElement("teacher_chaperons"), new RequiredFormElement("emergency_contact"),
					new RequiredFileFormElement("itinerary_document"),
					new RequiredPatternFormElement("departure_date", FormElementPattern.DATE_PATTERN),
					new RequiredPatternFormElement("return_date", FormElementPattern.DATE_PATTERN),
					new RequiredPatternFormElement("num_students", FormElementPattern.INTEGER_PATTERN),
					new RequiredPatternFormElement("emergency_contact", FormElementPattern.PHONE_PATTERN),
					new RequiredFormElement("days_missed"), new RequiredFormElement("total_chaperons"),
					new RequiredFormElement("total_teacher_chaperons"), new RequiredFormElement("total_nonteacher_chaperons")
			});

			if (validate_form()) {
				try {
					School s = null;

					TravelRequestBean treq = new TravelRequestBean();

					treq.setActionDate(Calendar.getInstance().getTime());
					treq.setActionedBy(usr.getPersonnel().getPersonnelID());
					treq.setDepartureDate(new SimpleDateFormat(TravelRequestBean.DATE_FORMAT).parse(form.get("departure_date")));
					treq.setDestination(form.get("destination"));
					treq.setEmergencyContact(form.get("emergency_contact"));
					treq.setGrades(form.get("grades"));
					if (form.uploadFileExists("itinerary_document"))
						treq.setIteneraryFilename(save_file("itinerary_document", "/student/travel/itineraries/"));
					treq.setNumStudents(Integer.parseInt(form.get("num_students")));
					treq.setOtherChaperon(form.get("other_chaperons"));
					treq.setRational(form.get("rational"));
					treq.setRequestedBy(usr.getPersonnel().getPersonnelID());
					treq.setRequestedDate(treq.getActionDate());
					treq.setReturnDate(new SimpleDateFormat(TravelRequestBean.DATE_FORMAT).parse(form.get("return_date")));
					treq.setSchoolId((s = usr.getPersonnel().getSchool()).getSchoolID());
					treq.setStatus(RequestStatus.SUBMITTED);
					treq.setTeacherChaperon(form.get("teacher_chaperons"));
					treq.setDaysMissed(form.getDouble("days_missed"));
					treq.setTotalChaperons(form.getInt("total_chaperons"));
					treq.setTotalTeacherChaperons(form.getInt("total_teacher_chaperons"));
					treq.setTotalOtherChaperons(form.getInt("total_nonteacher_chaperons"));
					treq.setChaperonsApproved(form.getBoolean("chaperons_approved"));
					treq.setBilletingInvolved(form.getBoolean("billeting_involved"));
					treq.setSchoolFundraising(form.getBoolean("school_fundraising"));

					treq.setRequestId(TravelRequestManager.addTravelRequestBean(treq));

					if (treq.getRequestId() > 0) {
						Personnel tmp = s.getSchoolFamily().getProgramSpecialist();

						EmailBean email = new EmailBean();
						email.setTo(new String[] {
								"chriscrane@esdnl.ca", tmp.getEmailAddress()
						});
						email.setSubject("Student Travel Request - " + s.getSchoolName());
						email.setBody("This Student Travel Request has been sent to you by "
								+ usr.getPersonnel().getFullNameReverse() + "<br><br>" + treq.toHTML()
								+ "<br><br>To APPROVE this request, please click "
								+ "<a href='http://www.nlesd.ca/MemberServices/student/travel/travelRequestAdmin.html?u="
								+ tmp.getUserName() + "&p=" + PasswordEncryption.encrypt(tmp.getPassword()) + "&op=approve&id="
								+ treq.getRequestId() + "' target='_blank'>HERE</a><br><br>" + "To DECLINE this request, please click "
								+ "<a href='http://www.nlesd.ca/MemberServices/student/travel/travelRequestAdmin.html?u="
								+ tmp.getUserName() + "&p=" + PasswordEncryption.encrypt(tmp.getPassword()) + "&op=decline&id="
								+ treq.getRequestId() + "' target='_blank'>HERE</a><br><br>"
								+ "PLEASE DO NOT RESPOND TO THIS MESSAGE. THANK YOU.<br><br>" + "Member Services");
						email.setFrom("ms@nlesd.ca");
						email.setAttachments(new File[] {
							new File(ROOT_DIR + "/student/travel/itineraries/" + treq.getIteneraryFilename())
						});
						email.send();

						request.setAttribute("TRAVELREQUESTBEAN", treq);

						request.setAttribute("APPROVER",
								SchoolDB.getSchool(treq.getSchoolId()).getSchoolFamily().getProgramSpecialist());

						request.setAttribute("msg", "Request successfully submitted to "
								+ s.getSchoolFamily().getProgramSpecialist().getFullNameReverse() + " for approval.");

						path = "view_request.jsp";
					}
					else {
						request.setAttribute("msg", "Could not add travel request.");
						request.setAttribute("FORM", form);
					}
				}
				catch (StudentTravelException e) {
					e.printStackTrace(System.err);
					request.setAttribute("msg", "Could not add travel request.");
					request.setAttribute("FORM", form);
				}
				catch (ParseException e) {
					e.printStackTrace(System.err);
					request.setAttribute("msg", "Could not add travel request.");
					request.setAttribute("FORM", form);
				}
				catch (Exception e) {
					e.printStackTrace(System.err);
					request.setAttribute("msg", "Could not add travel request.");
					request.setAttribute("FORM", form);
				}
			}
			else {
				request.setAttribute("FORM", form);
				request.setAttribute("msg", StringUtils.encodeHTML(validator.getErrorString()));
			}
		}

		return path;
	}
}