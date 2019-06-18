package com.esdnl.school.registration.kindergarten.site.handler.admin.school;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.awsd.school.SchoolDB;
import com.esdnl.school.registration.bean.SchoolRegistrationException;
import com.esdnl.school.registration.kindergarten.bean.KindergartenRegistrantBean;
import com.esdnl.school.registration.kindergarten.bean.KindergartenRegistrationPeriodBean;
import com.esdnl.school.registration.kindergarten.service.KindergartenRegistrationManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class AddKindergartenRegistrantRequestHandler extends RequestHandlerImpl {

	public AddKindergartenRegistrantRequestHandler() {

		this.requiredPermissions = new String[] {
			"KINDERGARTEN-REGISTRATION-SCHOOL-VIEW"
		};

	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (form.hasValue("op", "registrant-added")) {
			this.validator = new FormValidator(new FormElement[] {
					new RequiredFormElement("registration_id"), new RequiredFormElement("hdn_School"),
					new RequiredFormElement("ddl_Stream"), new RequiredFormElement("txt_StudentFirstName"),
					new RequiredFormElement("txt_StudentLastName"), new RequiredFormElement("ddl_Gender"),
					new RequiredFormElement("txt_DateOfBirth"), new RequiredFormElement("txt_MCPNumber"),
					new RequiredFormElement("txt_MCPExpiration"), new RequiredFormElement("txt_PhysicalStreetAddress1"),
					new RequiredFormElement("txt_PhysicalCityTown"), new RequiredFormElement("txt_PhysicalPostalCode"),
					new RequiredFormElement("txt_MailingAddress1"), new RequiredFormElement("txt_MailingCityTown"),
					new RequiredFormElement("txt_MailingPostalCode"), new RequiredFormElement("txt_MailingPostalCode"),
					new RequiredFormElement("txt_PrimaryContactName"), new RequiredFormElement("ddl_PrimaryContactRelationship"),
					new RequiredFormElement("txt_EmergencyContactName"), new RequiredFormElement("txt_EmergencyContactPhone"),
					new RequiredFormElement("rbg_CustodyIssues"), new RequiredFormElement("rbg_HealthOtherConcerns"),
					new RequiredFormElement("rbg_AccessibleFacility"), new RequiredFormElement("rbg_CurrentChildEFI")
			});

			if (validate_form()) {
				try {
					KindergartenRegistrationPeriodBean krp = KindergartenRegistrationManager.getKindergartenRegistrationPeriodBean(form.getInt("registration_id"));

					KindergartenRegistrantBean kr = new KindergartenRegistrantBean();
					kr.setRegistration(krp);
					kr.setSchool(SchoolDB.getSchool(form.getInt("hdn_School")));
					kr.setSchoolStream(KindergartenRegistrantBean.SCHOOLSTREAM.get(form.getInt("ddl_Stream")));
					kr.setStudentFirstName(form.get("txt_StudentFirstName"));
					kr.setStudentLastName(form.get("txt_StudentLastName"));
					kr.setStudentGender(KindergartenRegistrantBean.GENDER.get(form.getInt("ddl_Gender")));
					kr.setDateOfBirth(form.getDate("txt_DateOfBirth"));
					kr.setMcpNumber(form.get("txt_MCPNumber"));
					kr.setMcpExpiry(form.get("txt_MCPExpiration"));
					kr.setPhysicalStreetAddress1(form.get("txt_PhysicalStreetAddress1"));
					kr.setPhysicalStreetAddress2(form.get("txt_PhysicalStreetAddress2"));
					kr.setPhysicalCityTown(form.get("txt_PhysicalCityTown"));
					kr.setPhysicalPostalCode(form.get("txt_PhysicalPostalCode"));
					kr.setMailingStreetAddress1(form.get("txt_MailingAddress1"));
					kr.setMailingStreetAddress2(form.get("txt_MailingAddress2"));
					kr.setMailingCityTown(form.get("txt_MailingCityTown"));
					kr.setMailingPostalCode(form.get("txt_MailingPostalCode"));
					kr.setPrimaryContactName(form.get("txt_PrimaryContactName"));
					kr.setPrimaryContactRelationship(KindergartenRegistrantBean.REGISTRANT_RELATIONSHIP.get(form.getInt("ddl_PrimaryContactRelationship")));
					kr.setPrimaryContactHomePhone(form.get("txt_PrimaryContactHomePhone"));
					kr.setPrimaryContactWorkPhone(form.get("txt_PrimaryContactWorkPhone"));
					kr.setPrimaryContactCellPhone(form.get("txt_PrimaryContactCellPhone"));
					kr.setPrimaryContactEmail(form.get("txt_PrimaryContactEmail"));
					kr.setSecondaryContactName(form.get("txt_SecondaryContactName"));
					if (StringUtils.isNotEmpty(form.get("ddl_SecondaryContactRelationship")))
						kr.setSecondaryContactRelationship(KindergartenRegistrantBean.REGISTRANT_RELATIONSHIP.get(form.getInt("ddl_SecondaryContactRelationship")));
					kr.setSecondaryContactHomePhone(form.get("txt_SecondaryContactHomePhone"));
					kr.setSecondaryContactWorkPhone(form.get("txt_SecondaryContactWorkPhone"));
					kr.setSecondaryContactCellPhone(form.get("txt_SecondaryContactCellPhone"));
					kr.setSecondaryContactEmail(form.get("txt_SecondaryContactEmail"));
					kr.setEmergencyContactName(form.get("txt_EmergencyContactName"));
					kr.setEmergencyContactTelephone(form.get("txt_EmergencyContactPhone"));
					kr.setCustodyIssues(form.getInt("rbg_CustodyIssues") == 1);
					kr.setHealthConcerns(form.getInt("rbg_HealthOtherConcerns") == 1);
					kr.setAccessibleFacility(form.getInt("rbg_AccessibleFacility") == 1);
					kr.setEfiSibling(form.getInt("rbg_CurrentChildEFI") == 1);
					kr.setPhysicalAddressApproved(false);

					kr = KindergartenRegistrationManager.addKindergartenRegistrantBean(kr);

					request.setAttribute("kr", kr);
					request.setAttribute("msg", "Registration updated successfully.");

					this.path = "school_view_registrant.jsp";
				}
				catch (SchoolRegistrationException e) {
					request.setAttribute("msg", e.getMessage());

					this.path = "school_add_registrant.jsp";
				}
			}
			else {
				request.setAttribute("msg", validator.getErrorString());

				this.path = "school_add_registrant.jsp";
			}
		}
		else {
			try {
				this.validator = new FormValidator(new FormElement[] {
					new RequiredFormElement("id")
				});

				if (validate_form()) {
					request.setAttribute("krp",
							KindergartenRegistrationManager.getKindergartenRegistrationPeriodBean(form.getInt("id")));
					this.path = "school_add_registrant.jsp";
				}
				else {
					this.path = "index.html";
				}
			}
			catch (SchoolRegistrationException e) {
				e.printStackTrace();

				this.path = "index.html";
			}
		}

		return this.path;
	}

}
