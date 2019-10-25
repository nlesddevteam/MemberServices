package com.esdnl.personnel.jobs.constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public enum AssistantDirectorHR {

	UNKNOWN("UNKNOWN", "Assistant Director of Education - Human Resources and School Leadership", null, null,
			null), Richard_Harvey("Richard Harvey", "Assistant Director of Education - Human Resources and School Leadership",
					null, "01/01/2004", "05/14/2006"), Allister_Dyke("Allister Dyke",
							"Assistant Director of Education - Human Resources and School Leadership", null, "05/15/2006",
							"02/06/2012"), Gloria_Johnson("Gloria Johnson",
									"Assistant Director of Education - Human Resources and School Leadership", null, "02/17/2012",
									"08/31/2012"), Ed_Walsh_1("Ed Walsh",
											"Assistant Director of Education - Human Resources and School Leadership", "images/ed-sig.png",
											"09/04/2012", "04/20/2013"), Maurice_Kelly("Maurice Kelly",
													"Assistant Director of Education - Human Resources and School Leadership",
													"images/mksig1.png", "04/22/2013", "08/31/2013"), Gerald_Buffett("Gerald Buffett",
															"Assistant Director of Education - Human Resources and School Leadership",
															"images/gb-sig.png", "09/01/2013", "06/08/2016"), Lloyd_Collins("Lloyd Collins",
																	"Assistant Director of Education - Human Resources and School Leadership",
																	"images/lloydcollins-sig.png", "06/09/2016", "01/28/2018"), Ed_Walsh_2("Ed Walsh",
																			"Associate Director of Education (Programs and Human Resources)",
																			"images/ed-sig.png", "01/29/2018", null);

	private String name;
	private String title;
	private String signatureFile;
	private Date startDate;
	private Date endDate;

	private AssistantDirectorHR(String name, String title, String signatureFile, String startDate, String endDate) {

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

		this.name = name;
		this.title = title;
		this.signatureFile = signatureFile;

		try {
			if (StringUtils.isNotBlank(startDate)) {
				this.startDate = sdf.parse(startDate);
			}
			else {
				this.startDate = null;
			}
		}
		catch (ParseException e) {
			this.startDate = null;
		}

		try {
			if (StringUtils.isNotBlank(endDate)) {
				this.endDate = sdf.parse(endDate);
			}
			else {
				this.endDate = null;
			}
		}
		catch (ParseException e) {
			this.endDate = null;
		}
	}

	public String getName() {

		return name;
	}

	public String getTitle() {

		return title;
	}

	public String getSignatureFile() {

		return signatureFile;
	}

	public Date getStartDate() {

		return startDate;
	}

	public Date getEndDate() {

		return endDate;
	}

	public static AssistantDirectorHR get(Date d) {

		AssistantDirectorHR ad = AssistantDirectorHR.UNKNOWN;
		for (AssistantDirectorHR tmp : AssistantDirectorHR.values()) {
			if (tmp.equals(AssistantDirectorHR.UNKNOWN))
				continue;

			if (d.after(tmp.getStartDate()) && ((tmp.getEndDate() == null) || (d.before(tmp.getEndDate())))) {
				ad = tmp;
				break;
			}
		}

		return ad;
	}

}
