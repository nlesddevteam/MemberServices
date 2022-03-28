package com.esdnl.personnel.jobs.bean;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.PersonnelException;

public class NLESDRegionalMailHelperBean {

	private Personnel regionalHRClerks[];
	private Personnel regionalHRAdmins[];
	private Personnel regionalADPrograms[];
	private Personnel regionalADHRAssistant[];
	private EMAILZONE emz;

	public NLESDRegionalMailHelperBean(int schoolZone) {

		//determine what zone
		switch (schoolZone) {
		case -3001:
			emz = EMAILZONE.CENTRAL;
			break;
		case -3000:
			emz = EMAILZONE.CENTRAL;
			break;
		case -2000:
			emz = EMAILZONE.WESTERN;
			break;
		case -666:
			emz = EMAILZONE.PROVINCIAL;
			break;
		case -999:
		case -998:
		case -100:
		case -200:
		case -300:
		case -400:
		case 0:
			emz = EMAILZONE.EASTERN;
			break;
		case -1000:
			emz = EMAILZONE.LABRADOR;
			break;
		default:
			//should be school we can retrieve zone by using function
			emz = EMAILZONE.get(schoolZone);
			break;
		}

		createRegionalEmailList();
	}

	private void createRegionalEmailList() {

		try {
			switch (emz) {
			case CENTRAL:
				this.regionalHRClerks = PersonnelDB.getPersonnelByRole("PERSONNEL-CENTRAL-CLERK");
				this.regionalHRAdmins = PersonnelDB.getPersonnelByRole("PERSONNEL-CENTRAL-ADMIN");
				this.regionalADPrograms = PersonnelDB.getPersonnelByRole("PERSONNEL-CENTRAL-AD-PROGRAMS");
				this.regionalADHRAssistant = PersonnelDB.getPersonnelByRole("PERSONNEL-CENTRAL-AD-HR-ASSISTANT");
				break;

			case WESTERN:
				this.regionalHRClerks = PersonnelDB.getPersonnelByRole("PERSONNEL-WESTERN-CLERK");
				this.regionalHRAdmins = PersonnelDB.getPersonnelByRole("PERSONNEL-WESTERN-ADMIN");
				this.regionalADPrograms = PersonnelDB.getPersonnelByRole("PERSONNEL-WESTERN-AD-PROGRAMS");
				this.regionalADHRAssistant = PersonnelDB.getPersonnelByRole("PERSONNEL-WESTERN-AD-HR-ASSISTANT");
				break;
			case LABRADOR:
				this.regionalHRClerks = PersonnelDB.getPersonnelByRole("PERSONNEL-LABRADOR-CLERK");
				this.regionalHRAdmins = PersonnelDB.getPersonnelByRole("PERSONNEL-LABRADOR-ADMIN");
				this.regionalADPrograms = PersonnelDB.getPersonnelByRole("PERSONNEL-LABRADOR-AD-PROGRAMS");
				this.regionalADHRAssistant = PersonnelDB.getPersonnelByRole("PERSONNEL-LABRADOR-AD-HR-ASSISTANT");
				break;
			case PROVINCIAL:
				this.regionalHRClerks = PersonnelDB.getPersonnelByRole("PERSONNEL-CENTRAL-CLERK");
				this.regionalHRAdmins = PersonnelDB.getPersonnelByRole("PERSONNEL-CENTRAL-ADMIN");
				this.regionalADPrograms = PersonnelDB.getPersonnelByRole("PERSONNEL-CENTRAL-AD-PROGRAMS");
				this.regionalADHRAssistant = PersonnelDB.getPersonnelByRole("PERSONNEL-CENTRAL-AD-HR-ASSISTANT");
				break;
			default:
				this.regionalHRClerks = PersonnelDB.getPersonnelByRole("PERSONNEL-EASTERN-CLERK");
				this.regionalHRAdmins = PersonnelDB.getPersonnelByRole("PERSONNEL-EASTERN-ADMIN");
				this.regionalADPrograms = PersonnelDB.getPersonnelByRole("PERSONNEL-EASTERN-AD-PROGRAMS");
				this.regionalADHRAssistant = PersonnelDB.getPersonnelByRole("PERSONNEL-EASTERN-AD-HR-ASSISTANT");
				break;
			}
		}
		catch (PersonnelException e) {

			e.printStackTrace();
		}
	}

	public Personnel[] getRegionalHRClerks() {

		return this.regionalHRClerks;
	}

	public Personnel[] getRegionalHRAdmins() {

		return this.regionalHRAdmins;
	}
	public Personnel[] getRegionalADPrograms()
	{
		return this.regionalADPrograms;
	}
	public Personnel[] getRegionalADHRAssistant()
	{
		return this.regionalADHRAssistant;
	}
	public enum EMAILZONE {
		EASTERN(1, "Eastern"), CENTRAL(2, "Central"), WESTERN(3, "Western"), LABRADOR(4, "Labrador"), PROVINCIAL(5, "Provincial");

		private int id;
		private String name;

		EMAILZONE(int id, String name) {

			this.id = id;
			this.name = name;
		}

		public int getId() {

			return this.id;
		}

		public String getName() {

			return this.name;
		}

		public static EMAILZONE get(int id) {

			for (EMAILZONE ml : EMAILZONE.values()) {
				if (ml.id == id)
					return ml;
			}
			return null;
		}

		public static EMAILZONE get(String name) {

			for (EMAILZONE ml : EMAILZONE.values()) {
				if (ml.name.toUpperCase().equals(name))
					return ml;
			}
			return null;
		}
	}
}
