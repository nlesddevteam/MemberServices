package com.esdnl.personnel.jobs.constants;

public class RecommendationStatus {

	private int value;
	private String desc;

	public static final RecommendationStatus RECOMMENDED = new RecommendationStatus(1, "RECOMMENDED");
	public static final RecommendationStatus ACCEPTED = new RecommendationStatus(2, "ACCEPTED");
	public static final RecommendationStatus REJECTED = new RecommendationStatus(3, "REJECTED");
	public static final RecommendationStatus PROCESSED = new RecommendationStatus(4, "PROCESSED");
	public static final RecommendationStatus APPROVED = new RecommendationStatus(5, "APPROVED");
	public static final RecommendationStatus OFFERED = new RecommendationStatus(6, "OFFERED");
	public static final RecommendationStatus OFFER_ACCEPTED = new RecommendationStatus(7, "OFFER ACCEPTED");
	public static final RecommendationStatus OFFER_REJECTED = new RecommendationStatus(8, "OFFER REJECTED");
	public static final RecommendationStatus OFFER_EXPIRED = new RecommendationStatus(9, "OFFER EXPIRED");

	public static final RecommendationStatus[] ALL = new RecommendationStatus[] {
			RECOMMENDED, APPROVED, ACCEPTED, REJECTED, OFFERED, OFFER_ACCEPTED, OFFER_REJECTED, PROCESSED, OFFER_EXPIRED
	};

	private RecommendationStatus(int value, String desc) {

		this.value = value;
		this.desc = desc;
	}

	public int getValue() {

		return this.value;
	}

	public String getDescription() {

		return this.desc;
	}

	public boolean equal(Object o) {

		if (!(o instanceof RecommendationStatus))
			return false;
		else
			return (this.getValue() == ((RecommendationStatus) o).getValue());
	}

	public static RecommendationStatus get(int value) {

		RecommendationStatus tmp = null;

		for (int i = 0; i < ALL.length; i++) {
			if (ALL[i].getValue() == value) {
				tmp = ALL[i];
				break;
			}
		}

		return tmp;
	}

	public String toString() {

		return this.getDescription();
	}
}
