package com.awsd.travel;

import java.util.Iterator;
import java.util.Vector;

import com.awsd.common.Utils;

public class TravelClaimRateSummaries {

	private Vector<TravelClaimRateSummary> s;

	@SuppressWarnings("unchecked")
	public TravelClaimRateSummaries(TravelClaim claim) throws TravelClaimException {

		s = (Vector<TravelClaimRateSummary>) (TravelClaimDB.getClaimRateSummaryTotals(claim,
				TravelClaimDB.getKmRates(Utils.getDateFromFiscalYearMonth(claim.getFiscalMonth(), claim.getFiscalYear())))).clone();
	}

	public boolean add(TravelClaimRateSummary o) {

		s.add(o);

		return true;
	}

	public Iterator<TravelClaimRateSummary> iterator() {

		return s.iterator();
	}

	public int size() {

		return s.size();
	}
}