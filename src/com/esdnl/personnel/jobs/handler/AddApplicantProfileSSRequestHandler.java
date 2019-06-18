package com.esdnl.personnel.jobs.handler;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.bean.EmailException;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.servlet.LoginNotRequiredRequestHandler;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.dao.ApplicantProfileManager;
import com.esdnl.util.StringUtils;
public class AddApplicantProfileSSRequestHandler implements LoginNotRequiredRequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		String path;

		try {
			String email = request.getParameter("email");
			//String email_confirm = request.getParameter("email_confirm");
			String password = request.getParameter("password");
			//String password_confirm = request.getParameter("password_confirm");

			String surname = request.getParameter("surname");
			String firstname = request.getParameter("firstname");
			String middlename = request.getParameter("middlename");
			String maidenname = request.getParameter("maidenname");
			String sin = request.getParameter("sin");
			String address1 = request.getParameter("address1");
			String address2 = request.getParameter("address2");
			String province = request.getParameter("state_province");
			String country = request.getParameter("country");
			String postalcode = request.getParameter("postalcode");
			String homephone = request.getParameter("homephone");
			String workphone = request.getParameter("workphone");
			String cellphone = request.getParameter("cellphone");
			String dob = request.getParameter("dob");
			
			
			if (((request.getParameter("op") == null) || !request.getParameter("op").equals("edit"))
					&& (ApplicantProfileManager.getApplicantProfileBeanByEmail(email) != null)) {
				request.setAttribute("errmsg", "An account using " + email + " already exists.");
				path = "applicant_registration_step_1_ss.jsp";
			}
			else {
				ApplicantProfileBean profile = null;
				ApplicantProfileBean oldProfile = null;

				if ((request.getParameter("op") != null) && (request.getParameter("op").equals("edit"))) {
					profile = ApplicantProfileManager.getApplicantProfileBean(sin);
					oldProfile = profile.clone();
				}
				else
					profile = new ApplicantProfileBean();

				profile.setEmail(email);
				profile.setPassword(password);
				profile.setSurname(surname);
				profile.setFirstname(firstname);
				profile.setMiddlename(middlename);
				profile.setMaidenname(maidenname);

				if (StringUtils.isEmpty(sin))
					profile.setSIN(Long.toString(Calendar.getInstance().getTimeInMillis()));
				else
					profile.setSIN(sin);

				profile.setAddress1(address1);
				if (!StringUtils.isEmpty(address2))
					profile.setAddress2(address2);
				profile.setProvince(province);
				profile.setCountry(country);
				profile.setPostalcode(postalcode);
				profile.setHomephone(homephone);
				// if (!StringUtils.isEmpty(workphone))
				profile.setWorkphone(workphone);
				// if (!StringUtils.isEmpty(cellphone))
				profile.setCellphone(cellphone);
				if(!StringUtils.isEmpty(dob))
				{
					DateFormat format=new SimpleDateFormat("dd/MM/yyyy");
					
					profile.setDOB(format.parse(dob));
				}
				profile.setProfileType("S");
				if ((request.getParameter("op") != null) && (request.getParameter("op").equals("edit"))) {
					System.err.println("Updating Profile for " + profile.getSIN());
					ApplicantProfileManager.updateApplicantProfileBean(profile);
					request.setAttribute("msg", "Profile updated successfully.");
				}
				else {
					ApplicantProfileManager.addApplicantProfileBean(profile);
					request.setAttribute("msg", "Profile added successfully.");
				}

				request.getSession(true).setAttribute("APPLICANT", profile);
				
				if ((request.getParameter("op") != null) && (request.getParameter("op").equals("edit"))) {
					path = "applicant_registration_step_1_ss.jsp";
					} else {
					path = "view_applicant_ss.jsp";	
					}			
				

				if (addressChanged(oldProfile, profile)) {
					Personnel[] monitors = PersonnelDB.getPersonnelByRole("APPLICANT PROFILE MONITOR");
					if (monitors != null && monitors.length > 0) {
						try {
							EmailBean msg = new EmailBean();
							msg.setTo(monitors);
							msg.setSubject("Applicant Profile Notification: Address Change");
							msg.setBody("Address change for " + profile.getFullName() + "<br><br>"
									+ StringUtils.encodeHTML(profile.getAddress())
									+ "<BR><BR>PLEASE DO NOT RESPOND TO THIS MESSAGE. THANK YOU.<br><br>Member Services");
							msg.setFrom("ms@nlesd.ca");

							msg.send();
						}
						catch (EmailException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		catch (JobOpportunityException e) {
			e.printStackTrace();
			request.setAttribute("errmsg", "Could not add applicant profile.");
			path = "applicant_registration_step_1_ss.jsp";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.setAttribute("errmsg", "Could not save Date of Birth.");
			path = "applicant_registration_step_1_ss.jsp";
		}

		return path;
	}

	private boolean addressChanged(ApplicantProfileBean oldp, ApplicantProfileBean newp) {

		boolean changed = false;

		if (oldp != null && newp != null) {
			if (!oldp.getAddress1().equalsIgnoreCase(newp.getAddress1())
					|| !oldp.getAddress2().equalsIgnoreCase(newp.getAddress2())
					|| !oldp.getPostalcode().equalsIgnoreCase(newp.getPostalcode())
					|| !oldp.getProvince().equalsIgnoreCase(newp.getProvince())
					|| !oldp.getCountry().equalsIgnoreCase(newp.getCountry()))
				changed = true;
		}

		return changed;
	}
}