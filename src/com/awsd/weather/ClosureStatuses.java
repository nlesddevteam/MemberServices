package com.awsd.weather;

import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.Vector;

public class ClosureStatuses extends AbstractCollection<ClosureStatus> {

	private Vector<ClosureStatus> c = null;

	public ClosureStatuses() throws ClosureStatusException {

		this.c = ClosureStatusDB.getClosureStatuses(true);
	}

	@Override
	public Iterator<ClosureStatus> iterator() {

		return this.c.iterator();
	}

	@Override
	public int size() {

		return this.c.size();
	}
}
