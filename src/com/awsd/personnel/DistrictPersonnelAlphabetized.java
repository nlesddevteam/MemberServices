package com.awsd.personnel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

public class DistrictPersonnelAlphabetized implements Serializable {

	private static final long serialVersionUID = -3048983260963033082L;

	private ArrayList<Vector<Personnel>> members = null;

	public DistrictPersonnelAlphabetized() throws PersonnelException {

		members = PersonnelDB.getDistrictPersonnelAlphabetized();
	}

	public ArrayList<Vector<Personnel>> getArrayList() {

		return members;
	}

	public Personnel[] getPage(int page) {

		return members.get(page).toArray(new Personnel[0]);
	}

}