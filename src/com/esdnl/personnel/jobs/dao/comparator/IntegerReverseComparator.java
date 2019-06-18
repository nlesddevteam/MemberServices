package com.esdnl.personnel.jobs.dao.comparator;

import java.io.Serializable;
import java.util.Comparator;

public class IntegerReverseComparator implements Comparator<Integer>, Serializable {

	private static final long serialVersionUID = -6085063502699971417L;

	public int compare(Integer o1, Integer o2) {

		return -1 * o1.compareTo(o2);
	}

}
