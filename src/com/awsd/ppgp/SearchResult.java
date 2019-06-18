package com.awsd.ppgp;

import java.io.Serializable;

import com.awsd.school.bean.RegionBean;

public class SearchResult implements Serializable {

	private static final long serialVersionUID = 7896093104016358317L;

	private PPGP pgp;
	private RegionBean region;

	public SearchResult(RegionBean region, PPGP pgp) {

		this.pgp = pgp;
		this.region = region;
	}

	public PPGP getPGP() {

		return this.pgp;
	}

	public RegionBean getRegion() {

		return this.region;
	}
}
