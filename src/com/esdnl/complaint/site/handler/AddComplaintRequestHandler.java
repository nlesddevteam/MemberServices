package com.esdnl.complaint.site.handler;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javazoom.upload.MultipartFormDataRequest;
import javazoom.upload.UploadBean;
import javazoom.upload.UploadException;
import javazoom.upload.UploadFile;

import com.awsd.mail.SMTPAuthenticatedMail;
import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.bean.EmailException;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.school.SchoolDB;
import com.awsd.security.PermissionDB;
import com.awsd.security.crypto.PasswordEncryption;
import com.awsd.servlet.LoginNotRequiredRequestHandler;
import com.esdnl.complaint.database.ComplaintManager;
import com.esdnl.complaint.model.bean.ComplaintBean;
import com.esdnl.complaint.model.bean.ComplaintException;
import com.esdnl.complaint.model.constant.ComplaintType;
import com.esdnl.complaint.model.constant.PhoneType;
import com.esdnl.util.StringUtils;

public class AddComplaintRequestHandler implements LoginNotRequiredRequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		String path;
		HttpSession session = null;
		UploadBean bean = null;
		MultipartFormDataRequest mrequest = null;
		UploadFile file = null;
		Hashtable files = null;
		File f = null;
		File dir = null;
		ComplaintBean complaint = null;
		String extension = null;
		SMTPAuthenticatedMail smtp = null;

		try {
			session = request.getSession(false);

			path = "index.jsp";

			if (MultipartFormDataRequest.isMultipartFormData(request)) {
				mrequest = new MultipartFormDataRequest(request);

				String firstName = mrequest.getParameter("First_Name");
				String middleInitial = mrequest.getParameter("Middle_Initial");
				String lastName = mrequest.getParameter("Last_Name");

				String address1 = mrequest.getParameter("Address_Line_1");
				String address2 = mrequest.getParameter("Address_Line_2");
				String address3 = mrequest.getParameter("Address_Line_3");
				String city = mrequest.getParameter("City");
				String province = mrequest.getParameter("Province");
				String country = mrequest.getParameter("Country");
				String postalCode = mrequest.getParameter("PostalCode");

				String email = mrequest.getParameter("Email_Address");

				String areaCode = mrequest.getParameter("Contact_Phone_Area_Code");
				String phone = mrequest.getParameter("Contact_Phone");
				String phoneType = mrequest.getParameter("Contact_Phone_Type");

				String contactTime = mrequest.getParameter("Best_Contact_Time");
				String restrictions = mrequest.getParameter("Contact_Restrictions");

				String cat_id = mrequest.getParameter("cat_id");
				String school_id = mrequest.getParameter("school_id");
				System.out.println("school_id? " + school_id);
				String admin_contacted = mrequest.getParameter("school_admin_contacted");
				System.out.println("admin_contacted? " + admin_contacted);
				String contact_dates = mrequest.getParameter("contact_dates");
				String summary = mrequest.getParameter("Complaint");
				String urgent = mrequest.getParameter("Urgent_Why");

				files = mrequest.getFiles();

				if (StringUtils.isEmpty(firstName))
					request.setAttribute("msg", "Please specify FIRST NANE.");
				else if (StringUtils.isEmpty(lastName))
					request.setAttribute("msg", "Please specify LAST NAME.");
				else if (StringUtils.isEmpty(address1))
					request.setAttribute("msg", "Please specify ADDRESS LINE 1.");
				else if (StringUtils.isEmpty(city))
					request.setAttribute("msg", "Please specify CITY.");
				else if (StringUtils.isEmpty(province))
					request.setAttribute("msg", "Please specify PROVINCE.");
				else if (StringUtils.isEmpty(country))
					request.setAttribute("msg", "Please specify COUNTRY.");
				else if (StringUtils.isEmpty(postalCode))
					request.setAttribute("msg", "Please specify POSTAL CODE.");
				else if (StringUtils.isEmpty(areaCode))
					request.setAttribute("msg", "Please specify AREA CODE.");
				else if (StringUtils.isEmpty(phone))
					request.setAttribute("msg", "Please specify CONTACT PHONE NUMBER.");
				else if (StringUtils.isEmpty(phoneType))
					request.setAttribute("msg", "Please specify CONTACT PHONE NUMBER TYPE.");
				else if (StringUtils.isEmpty(cat_id))
					request.setAttribute("msg", "Please specify COMPLAINT CATEGORY.");
				else if (ComplaintType.get(Integer.parseInt(cat_id)).equals(ComplaintType.COMPLAINT_SCHOOL)
						&& StringUtils.isEmpty(school_id))
					request.setAttribute("msg", "Please specify SCHOOL.");
				else if (ComplaintType.get(Integer.parseInt(cat_id)).equals(ComplaintType.COMPLAINT_SCHOOL)
						&& StringUtils.isEmpty(admin_contacted))
					request.setAttribute("msg", "Please specify whether SCHOOL ADMIN CONTACTED.");
				else if (ComplaintType.get(Integer.parseInt(cat_id)).equals(ComplaintType.COMPLAINT_SCHOOL)
						&& Boolean.valueOf(admin_contacted).booleanValue() && StringUtils.isEmpty(contact_dates))
					request.setAttribute("msg", "Please specify SCHOOL ADMIN CONTACT DATES.");
				else if (StringUtils.isEmpty(summary))
					request.setAttribute("msg", "Please specify COMPLAINT SUMMARY.");
				/*
				else if((files != null) && (files.size() > 0) && (((UploadFile)files.get("supporting_documents")).getFileName().lastIndexOf(".") == -1))
				  request.setAttribute("msg", "Please specify a FILE EXTENSION for your supporting documentation.");
				*/
				else {
					complaint = new ComplaintBean();

					complaint.setFirstName(firstName);
					complaint.setMiddleInitial(middleInitial);
					complaint.setLastName(lastName);

					complaint.setAddress1(address1);
					complaint.setAddress2(address2);
					complaint.setAddress3(address3);
					complaint.setCity(city);
					complaint.setProvince(province);
					complaint.setCountry(country);
					complaint.setPostalCode(postalCode);

					complaint.setEmail(email);

					complaint.setAreaCode(areaCode);
					complaint.setPhoneNumber(phone);
					complaint.setPhoneType(PhoneType.get(Integer.parseInt(phoneType)));

					complaint.setContactTime(contactTime);
					complaint.setContactRestrictions(restrictions);

					complaint.setComplaintType(ComplaintType.get(Integer.parseInt(cat_id)));
					System.out.println("Complaint_type? " + complaint.getComplaintType());
					if (complaint.getComplaintType().equals(ComplaintType.COMPLAINT_SCHOOL))
						complaint.setSchool(SchoolDB.getSchool(Integer.parseInt(school_id)));
					complaint.setAdminContacted(Boolean.valueOf(admin_contacted).booleanValue());
					complaint.setAdminContactDates(contact_dates);
					complaint.setComplaintSummary(summary);
					complaint.setUrgency(urgent);

					if ((files != null) && (files.size() > 0)) {
						//System.out.println(files.size());
						file = (UploadFile) files.get("supporting_documents");
						//System.out.println(file);
						if ((file != null) && !StringUtils.isEmpty(file.getFileName())) {
							//System.out.println(file.getFileName());
							extension = file.getFileName().substring(file.getFileName().lastIndexOf("."));
							complaint.setSupportingDocumentation(Calendar.getInstance().getTimeInMillis() + extension);
						}
					}

					int id = ComplaintManager.addComplaintBean(complaint);

					complaint.setId(id);

					if (!StringUtils.isEmpty(complaint.getSupportingDocumentation())) {
						dir = new File(session.getServletContext().getRealPath("/") + "/complaint/documentation/");
						if (!dir.exists())
							dir.mkdirs();

						bean = new UploadBean();
						bean.setFolderstore(session.getServletContext().getRealPath("/") + "/complaint/documentation/");
						bean.store(mrequest, "supporting_documents");
						f = new File(session.getServletContext().getRealPath("/") + "/complaint/documentation/"
								+ file.getFileName());
						f.renameTo(new File(session.getServletContext().getRealPath("/") + "/complaint/documentation/"
								+ complaint.getSupportingDocumentation()));

					}

					//send confirmation email to person submitting complaint
					if (!StringUtils.isEmpty(complaint.getEmail())) {
						/*
						 	new EmailThread(new String[]{complaint.getEmail()},	
								"Complaint Received.",
								"This message is to confirm we have received your complaint. Thank you. "
						    	+ "\n\nPlease do not reply to this message."
						    	+ "\nESD Member Services",
						  	"ms@esdnl.ca",
						  	10).start();
						 */

						try {
							EmailBean em = new EmailBean();
							em.setTo(complaint.getEmail());
							em.setSubject("Complaint Received.");
							em.setBody("This message is to confirm we have received your complaint. Thank you. "
									+ "\n\nPlease do not reply to this message." + "\nESD Member Services");
							em.setFrom("noreply@nlesd.ca");
							em.send();
						}
						catch (EmailException e) {}

					}

					Personnel[] monitors = (Personnel[]) PersonnelDB.getPersonnelList(
							PermissionDB.getPermission("COMPLAINT-MONITOR")).toArray(new Personnel[0]);

					for (int i = 0; i < monitors.length; i++) {

						try {
							EmailBean ebean = new EmailBean();

							ebean.setFrom("ms@nlesd.ca");
							ebean.setTo(new String[] {
								monitors[i].getEmailAddress()
							});
							ebean.setSubject("COMPLAINT RECEIVED: " + complaint.getFullName());
							ebean.setBody(complaint.getFullName()
									+ " has submitted a complaint. "
									+ " To review this complaint click the link below to login to Member Services and access the Complaint Administration System.<br><br>"
									+ "<a href='http://www.nlesd.ca/MemberServices/complaint/viewAdminComplaintSummary.html?u="
									+ monitors[i].getUserName() + "&p=" + PasswordEncryption.encrypt(monitors[i].getPassword()) + "&id="
									+ complaint.getId() + "'><B>CLICK HERE</B></a>"
									+ "<br><br>PLEASE DO NOT REPLY TO THIS MESSAGE.<br><br>" + "Member Services");
							ebean.send();
						}
						catch (EmailException e) {
							e.printStackTrace();
						}
					}

					request.setAttribute("COMPLAINT_BEAN", complaint);
					request.setAttribute("msg", "COMPLAINT SUCCESSFULLY SUBMITTED.");

					path = "complaint_summary.jsp";
				}
			}
			else
				System.out.println("NOT A MULTIPART FORM!!!!!!!!!");
		}
		catch (ComplaintException e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			path = "index.jsp";
		}
		catch (UploadException e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			path = "index.jsp";
		}

		return path;
	}
}