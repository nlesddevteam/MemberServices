package com.esdnl.school.registration.kindergarten.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.awsd.school.School;
import com.esdnl.servlet.FormElementFormat;

public class KindergartenRegistrantBean {

	public enum GENDER {
		UNKNOWN(0, "UNKNOW GENDER"), FEMALE(1, "Female"), MALE(2, "Male");

		private int value;
		private String text;

		private GENDER(int value, String text) {

			this.value = value;
			this.text = text;
		}

		public int getValue() {

			return this.value;
		}

		public String getText() {

			return this.text;
		}

		public String toString() {

			return this.text;
		}

		public boolean equals(GENDER g) {

			if (!(g instanceof GENDER))
				return false;

			return (this.value == g.getValue());
		}

		public static GENDER get(int value) {

			GENDER tmp = GENDER.UNKNOWN;

			for (GENDER g : GENDER.values()) {
				if (g.getValue() == value) {
					tmp = g;
					break;
				}
			}

			return tmp;

		}
	}

	public enum SCHOOLSTREAM {

		UNKNOWN(0, "UNKNOWN SCHOOL STREAM"), ENGLISH(1, "ENGLISH"), FRENCH(2, "FRENCH");

		private int value;
		private String text;

		private SCHOOLSTREAM(int value, String text) {

			this.value = value;
			this.text = text;
		}

		public int getValue() {

			return this.value;
		}

		public String getText() {

			return this.text;
		}

		public String toString() {

			return this.text;
		}

		public boolean equals(SCHOOLSTREAM ss) {

			if (!(ss instanceof SCHOOLSTREAM))
				return false;

			return (this.value == ss.getValue());
		}

		public static SCHOOLSTREAM get(int value) {

			SCHOOLSTREAM tmp = SCHOOLSTREAM.UNKNOWN;

			for (SCHOOLSTREAM g : SCHOOLSTREAM.values()) {
				if (g.getValue() == value) {
					tmp = g;
					break;
				}
			}

			return tmp;

		}
	}

	public enum REGISTRANT_RELATIONSHIP {

		UNKNOWN(0, "UNKNOWN REGISTRANT RELATION"), PARENT(1, "PARENT"), GUARDIAN(2, "GUARDIAN"), OTHER(3, "OTHER");

		private int value;
		private String text;

		private REGISTRANT_RELATIONSHIP(int value, String text) {

			this.value = value;
			this.text = text;
		}

		public int getValue() {

			return this.value;
		}

		public String getText() {

			return this.text;
		}

		public String toString() {

			return this.text;
		}

		public boolean equals(REGISTRANT_RELATIONSHIP rr) {

			if (!(rr instanceof REGISTRANT_RELATIONSHIP))
				return false;

			return (this.value == rr.getValue());
		}

		public static REGISTRANT_RELATIONSHIP get(int value) {

			REGISTRANT_RELATIONSHIP tmp = REGISTRANT_RELATIONSHIP.UNKNOWN;

			for (REGISTRANT_RELATIONSHIP g : REGISTRANT_RELATIONSHIP.values()) {
				if (g.getValue() == value) {
					tmp = g;
					break;
				}
			}

			return tmp;

		}
	}

	public enum REGISTRANT_STATUS {

		PROCESSING(0, ""), ACCEPTED(1, "ACCEPTED"), WAITLISTED(2, "WAITLISTED");

		private int value;
		private String text;

		private REGISTRANT_STATUS(int value, String text) {

			this.value = value;
			this.text = text;
		}

		public int getValue() {

			return this.value;
		}

		public String getText() {

			return this.text;
		}

		public String toString() {

			return this.text;
		}

		public boolean isAccepted() {

			return this.equals(ACCEPTED);
		}

		public boolean isWaitlisted() {

			return this.equals(WAITLISTED);
		}

		public boolean equals(REGISTRANT_STATUS ss) {

			if (!(ss instanceof REGISTRANT_STATUS))
				return false;

			return (this.value == ss.getValue());
		}

		public static REGISTRANT_STATUS get(int value) {

			REGISTRANT_STATUS tmp = REGISTRANT_STATUS.PROCESSING;

			for (REGISTRANT_STATUS g : REGISTRANT_STATUS.values()) {
				if (g.getValue() == value) {
					tmp = g;
					break;
				}
			}

			return tmp;

		}
	}

	private int registrantId;
	private KindergartenRegistrationPeriodBean registration;
	private String studentFirstName;
	private String studentLastName;
	private GENDER studentGender;
	private String physicalStreetAddress1;
	private String physicalStreetAddress2;
	private String physicalCityTown;
	private String physicalPostalCode;
	private String mailingStreetAddress1;
	private String mailingStreetAddress2;
	private String mailingCityTown;
	private String mailingPostalCode;
	private Date dateOfBirth;
	private String mcpNumber;
	private String mcpExpiry;
	private School school;
	private SCHOOLSTREAM schoolStream;
	private String primaryContactName;
	private String primaryContactHomePhone;
	private String primaryContactWorkPhone;
	private String primaryContactCellPhone;
	private String primaryContactEmail;
	private REGISTRANT_RELATIONSHIP primaryContactRelationship;
	private String secondaryContactName;
	private String secondaryContactHomePhone;
	private String secondaryContactWorkPhone;
	private String secondaryContactCellPhone;
	private String secondaryContactEmail;
	private REGISTRANT_RELATIONSHIP secondaryContactRelationship;
	private String emergencyContactName;
	private String emergencyContactTelephone;
	private boolean custodyIssues;
	private boolean healthConcerns;
	private boolean accessibleFacility;
	private boolean efiSibling;
	private Date registrationDate;
	private boolean physicalAddressApproved;
	private KindergartenRegistrantBean relatedRegistrant;
	private REGISTRANT_STATUS status;

	public int getRegistrantId() {

		return registrantId;
	}

	public void setRegistrantId(int registrantId) {

		this.registrantId = registrantId;
	}

	public KindergartenRegistrationPeriodBean getRegistration() {

		return registration;
	}

	public void setRegistration(KindergartenRegistrationPeriodBean registration) {

		this.registration = registration;
	}

	public String getStudentFirstName() {

		return studentFirstName;
	}

	public void setStudentFirstName(String studentFirstName) {

		this.studentFirstName = studentFirstName;
	}

	public String getStudentLastName() {

		return studentLastName;
	}

	public void setStudentLastName(String studentLastName) {

		this.studentLastName = studentLastName;
	}

	public String getStudentFullName() {

		return this.studentLastName + ", " + this.studentFirstName;
	}

	public GENDER getStudentGender() {

		return studentGender;
	}

	public void setStudentGender(GENDER studentGender) {

		this.studentGender = studentGender;
	}

	public String getPhysicalStreetAddress1() {

		return physicalStreetAddress1;
	}

	public void setPhysicalStreetAddress1(String physicalStreetAddress1) {

		this.physicalStreetAddress1 = physicalStreetAddress1;
	}

	public String getPhysicalStreetAddress2() {

		return physicalStreetAddress2;
	}

	public void setPhysicalStreetAddress2(String physicalStreetAddress2) {

		this.physicalStreetAddress2 = physicalStreetAddress2;
	}

	public String getPhysicalCityTown() {

		return physicalCityTown;
	}

	public void setPhysicalCityTown(String physicalCityTown) {

		this.physicalCityTown = physicalCityTown;
	}

	public String getPhysicalPostalCode() {

		return physicalPostalCode;
	}

	public void setPhysicalPostalCode(String physicalPostalCode) {

		this.physicalPostalCode = physicalPostalCode;
	}

	public String getMailingStreetAddress1() {

		return mailingStreetAddress1;
	}

	public void setMailingStreetAddress1(String mailingStreetAddress1) {

		this.mailingStreetAddress1 = mailingStreetAddress1;
	}

	public String getMailingStreetAddress2() {

		return mailingStreetAddress2;
	}

	public void setMailingStreetAddress2(String mailingStreetAddress2) {

		this.mailingStreetAddress2 = mailingStreetAddress2;
	}

	public String getMailingCityTown() {

		return mailingCityTown;
	}

	public void setMailingCityTown(String mailingCityTown) {

		this.mailingCityTown = mailingCityTown;
	}

	public String getMailingPostalCode() {

		return mailingPostalCode;
	}

	public void setMailingPostalCode(String mailingPostalCode) {

		this.mailingPostalCode = mailingPostalCode;
	}

	public Date getDateOfBirth() {

		return dateOfBirth;
	}

	public String getDateOfBirthFormatted() {

		return (new SimpleDateFormat(FormElementFormat.DATE_FORMAT)).format(dateOfBirth);
	}

	public void setDateOfBirth(Date dateOfBirth) {

		this.dateOfBirth = dateOfBirth;
	}

	public String getMcpNumber() {

		return mcpNumber;
	}

	public void setMcpNumber(String mcpNumber) {

		this.mcpNumber = mcpNumber;
	}

	public String getMcpExpiry() {

		return mcpExpiry;
	}

	public void setMcpExpiry(String mcpExpiry) {

		this.mcpExpiry = mcpExpiry;
	}

	public School getSchool() {

		return school;
	}

	public void setSchool(School school) {

		this.school = school;
	}

	public SCHOOLSTREAM getSchoolStream() {

		return schoolStream;
	}

	public void setSchoolStream(SCHOOLSTREAM schoolStream) {

		this.schoolStream = schoolStream;
	}

	public String getPrimaryContactName() {

		return primaryContactName;
	}

	public void setPrimaryContactName(String primaryContactName) {

		this.primaryContactName = primaryContactName;
	}

	public String getPrimaryContactHomePhone() {

		return primaryContactHomePhone;
	}

	public void setPrimaryContactHomePhone(String primaryContactHomePhone) {

		this.primaryContactHomePhone = primaryContactHomePhone;
	}

	public String getPrimaryContactWorkPhone() {

		return primaryContactWorkPhone;
	}

	public void setPrimaryContactWorkPhone(String primaryContactWorkPhone) {

		this.primaryContactWorkPhone = primaryContactWorkPhone;
	}

	public String getPrimaryContactCellPhone() {

		return primaryContactCellPhone;
	}

	public void setPrimaryContactCellPhone(String primaryContactCellPhone) {

		this.primaryContactCellPhone = primaryContactCellPhone;
	}

	public String getPrimaryContactEmail() {

		return primaryContactEmail;
	}

	public void setPrimaryContactEmail(String primaryContactEmail) {

		this.primaryContactEmail = primaryContactEmail;
	}

	public REGISTRANT_RELATIONSHIP getPrimaryContactRelationship() {

		return primaryContactRelationship;
	}

	public void setPrimaryContactRelationship(REGISTRANT_RELATIONSHIP primaryContactRelationship) {

		this.primaryContactRelationship = primaryContactRelationship;
	}

	public String getSecondaryContactName() {

		return secondaryContactName;
	}

	public void setSecondaryContactName(String secondaryContactName) {

		this.secondaryContactName = secondaryContactName;
	}

	public String getSecondaryContactHomePhone() {

		return secondaryContactHomePhone;
	}

	public void setSecondaryContactHomePhone(String secondaryContactHomePhone) {

		this.secondaryContactHomePhone = secondaryContactHomePhone;
	}

	public String getSecondaryContactWorkPhone() {

		return secondaryContactWorkPhone;
	}

	public void setSecondaryContactWorkPhone(String secondaryContactWorkPhone) {

		this.secondaryContactWorkPhone = secondaryContactWorkPhone;
	}

	public String getSecondaryContactCellPhone() {

		return secondaryContactCellPhone;
	}

	public void setSecondaryContactCellPhone(String secondaryContactCellPhone) {

		this.secondaryContactCellPhone = secondaryContactCellPhone;
	}

	public String getSecondaryContactEmail() {

		return secondaryContactEmail;
	}

	public void setSecondaryContactEmail(String secondaryContactEmail) {

		this.secondaryContactEmail = secondaryContactEmail;
	}

	public REGISTRANT_RELATIONSHIP getSecondaryContactRelationship() {

		return secondaryContactRelationship;
	}

	public void setSecondaryContactRelationship(REGISTRANT_RELATIONSHIP secondaryContactRelationship) {

		this.secondaryContactRelationship = secondaryContactRelationship;
	}

	public String getEmergencyContactName() {

		return emergencyContactName;
	}

	public void setEmergencyContactName(String emergencyContactName) {

		this.emergencyContactName = emergencyContactName;
	}

	public String getEmergencyContactTelephone() {

		return emergencyContactTelephone;
	}

	public void setEmergencyContactTelephone(String emergencyContactTelephone) {

		this.emergencyContactTelephone = emergencyContactTelephone;
	}

	public boolean isCustodyIssues() {

		return custodyIssues;
	}

	public void setCustodyIssues(boolean custodyIssues) {

		this.custodyIssues = custodyIssues;
	}

	public boolean isHealthConcerns() {

		return healthConcerns;
	}

	public void setHealthConcerns(boolean healthConcerns) {

		this.healthConcerns = healthConcerns;
	}

	public boolean isAccessibleFacility() {

		return accessibleFacility;
	}

	public void setAccessibleFacility(boolean accessibleFacility) {

		this.accessibleFacility = accessibleFacility;
	}

	public boolean isEfiSibling() {

		return efiSibling;
	}

	public void setEfiSibling(boolean efiSibling) {

		this.efiSibling = efiSibling;
	}

	public Date getRegistrationDate() {

		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {

		this.registrationDate = registrationDate;
	}

	public boolean isPhysicalAddressApproved() {

		return physicalAddressApproved;
	}

	public void setPhysicalAddressApproved(boolean physicalAddressApproved) {

		this.physicalAddressApproved = physicalAddressApproved;
	}

	public KindergartenRegistrantBean getRelatedRegistrant() {

		return relatedRegistrant;
	}

	public void setRelatedRegistrant(KindergartenRegistrantBean relatedRegistrant) {

		this.relatedRegistrant = relatedRegistrant;
	}

	public REGISTRANT_STATUS getStatus() {

		if (status == null)
			status = REGISTRANT_STATUS.PROCESSING;

		return status;
	}

	public void setStatus(REGISTRANT_STATUS status) {

		this.status = status;
	}

}
