package com.awsd.ppgp;

import java.util.Comparator;

public class SchoolYearComparator implements Comparator<PPGP> {

	public int compare(PPGP o1, PPGP o2) {

		return -1 * o1.getSchoolYear().compareTo(o2.getSchoolYear());
	}

}
