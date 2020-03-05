package com.esdnl.personnel.jobs.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.esdnl.util.StringUtils;

public class StateProvinceTagHandler extends TagSupport {

	private static final long serialVersionUID = 7217927147571393059L;

	private String id;
	private String cls;
	private String style;
	private String value;

	public void setId(String id) {

		this.id = id;
	}

	public void setCls(String cls) {

		this.cls = cls;
	}

	public void setStyle(String style) {

		this.style = style;
	}

	public void setValue(String value) {

		this.value = value;
	}

	public int doStartTag() throws JspException {

		JspWriter out = null;

		try {
			out = pageContext.getOut();

			out.print("<SELECT name=\"" + this.id + "\" id=\"" + this.id + "\"");
			if ((this.cls != null) && !this.cls.trim().equals(""))
				out.print(" class=\"" + this.cls + "\"");
			if ((this.style != null) && !this.style.trim().equals(""))
				out.print(" style=\"" + this.style + "\"");
			out.println(">");
			out.println("<OPTION VALUE='-1'>SELECT PROVINCE/STATE</OPTION>");
			out.println("<OPTION VALUE='-1'>****** CANADIAN PROVINCES ******</OPTION>");
			out.println("<OPTION VALUE='AB'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("AB")) ? " SELECTED" : "") + ">Alberta</OPTION>");
			out.println("<OPTION VALUE='BC'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("BC")) ? " SELECTED" : "")
					+ ">British Columbia</OPTION>");
			out.println("<OPTION VALUE='MB'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("MB")) ? " SELECTED" : "") + ">Manitoba</OPTION>");
			out.println("<OPTION VALUE='NB'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("NB")) ? " SELECTED" : "")
					+ ">New Brunswick</OPTION>");
			out.println("<OPTION VALUE='NL'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("NL")) ? " SELECTED" : "")
					+ ">Newfoundland & Labrador</OPTION>");
			out.println("<OPTION VALUE='NT'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("NT")) ? " SELECTED" : "")
					+ ">Northwest Territories</OPTION>");
			out.println("<OPTION VALUE='NS'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("NS")) ? " SELECTED" : "")
					+ ">Nova Scotia</OPTION>");
			out.println("<OPTION VALUE='NU'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("NU")) ? " SELECTED" : "") + ">Nunavut</OPTION>");
			out.println("<OPTION VALUE='ON'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("ON")) ? " SELECTED" : "") + ">Ontario</OPTION>");
			out.println("<OPTION VALUE='PE'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("PE")) ? " SELECTED" : "")
					+ ">Prince Edward Island</OPTION>");
			out.println("<OPTION VALUE='PQ'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("PQ")) ? " SELECTED" : "") + ">Quebec</OPTION>");
			out.println("<OPTION VALUE='SK'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("SK")) ? " SELECTED" : "")
					+ ">Saskatchewan</OPTION>");
			out.println("<OPTION VALUE='YT'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("YT")) ? " SELECTED" : "") + ">Yukon</OPTION>");
			out.println("<OPTION VALUE='-1'>&nbsp;</OPTION>");
			out.println("<OPTION VALUE='-1'>****** US STATES ******</OPTION>");
			out.println("<OPTION VALUE='AL'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("AL")) ? " SELECTED" : "") + ">Alabama</OPTION>");
			out.println("<OPTION VALUE='AK'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("AK")) ? " SELECTED" : "") + ">Alaska</OPTION>");
			out.println("<OPTION VALUE='AZ'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("AZ")) ? " SELECTED" : "") + ">Arizona</OPTION>");
			out.println("<OPTION VALUE='AR'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("AR")) ? " SELECTED" : "") + ">Arkansas</OPTION>");
			out.println("<OPTION VALUE='CA'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("CA")) ? " SELECTED" : "") + ">California</OPTION>");
			out.println("<OPTION VALUE='CO'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("CO")) ? " SELECTED" : "") + ">Colorado</OPTION>");
			out.println("<OPTION VALUE='CT'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("CT")) ? " SELECTED" : "")
					+ ">Connecticut</OPTION>");
			out.println("<OPTION VALUE='DE'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("DE")) ? " SELECTED" : "") + ">Delaware</OPTION>");
			out.println("<OPTION VALUE='DC'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("DC")) ? " SELECTED" : "")
					+ ">District of Columbia</OPTION>");
			out.println("<OPTION VALUE='FL'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("FL")) ? " SELECTED" : "") + ">Florida</OPTION>");
			out.println("<OPTION VALUE='GA'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("GA")) ? " SELECTED" : "") + ">Georgia</OPTION>");
			out.println("<OPTION VALUE='HI'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("HI")) ? " SELECTED" : "") + ">Hawaii</OPTION>");
			out.println("<OPTION VALUE='ID'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("ID")) ? " SELECTED" : "") + ">Idaho</OPTION>");
			out.println("<OPTION VALUE='IL'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("IL")) ? " SELECTED" : "") + ">Illinois</OPTION>");
			out.println("<OPTION VALUE='IN'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("IN")) ? " SELECTED" : "") + ">Indiana</OPTION>");
			out.println("<OPTION VALUE='IA'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("IA")) ? " SELECTED" : "") + ">Iowa</OPTION>");
			out.println("<OPTION VALUE='KS'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("KS")) ? " SELECTED" : "") + ">Kansas</OPTION>");
			out.println("<OPTION VALUE='KY'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("KY")) ? " SELECTED" : "") + ">Kentucky</OPTION>");
			out.println("<OPTION VALUE='LA'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("LA")) ? " SELECTED" : "") + ">Louisiana</OPTION>");
			out.println("<OPTION VALUE='ME'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("ME")) ? " SELECTED" : "") + ">Maine</OPTION>");
			out.println("<OPTION VALUE='MD'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("MD")) ? " SELECTED" : "") + ">Maryland</OPTION>");
			out.println("<OPTION VALUE='MA'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("MA")) ? " SELECTED" : "")
					+ ">Massachusetts</OPTION>");
			out.println("<OPTION VALUE='MI'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("MI")) ? " SELECTED" : "") + ">Michigan</OPTION>");
			out.println("<OPTION VALUE='MN'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("MN")) ? " SELECTED" : "") + ">Minnesota</OPTION>");
			out.println("<OPTION VALUE='MS'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("MS")) ? " SELECTED" : "")
					+ ">Mississippi</OPTION>");
			out.println("<OPTION VALUE='MO'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("MO")) ? " SELECTED" : "") + ">Missouri</OPTION>");
			out.println("<OPTION VALUE='MT'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("MT")) ? " SELECTED" : "") + ">Montana</OPTION>");
			out.println("<OPTION VALUE='NE'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("NE")) ? " SELECTED" : "") + ">Nebraska</OPTION>");
			out.println("<OPTION VALUE='NV'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("NV")) ? " SELECTED" : "") + ">Nevada</OPTION>");
			out.println("<OPTION VALUE='NH'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("NH")) ? " SELECTED" : "")
					+ ">New Hampshire</OPTION>");
			out.println("<OPTION VALUE='NJ'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("NJ")) ? " SELECTED" : "") + ">New Jersey</OPTION>");
			out.println("<OPTION VALUE='NM'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("NM")) ? " SELECTED" : "") + ">New Mexico</OPTION>");
			out.println("<OPTION VALUE='NY'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("NY")) ? " SELECTED" : "") + ">New York</OPTION>");
			out.println("<OPTION VALUE='NC'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("NC")) ? " SELECTED" : "")
					+ ">North Carolina</OPTION>");
			out.println("<OPTION VALUE='ND'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("ND")) ? " SELECTED" : "")
					+ ">North Dakota</OPTION>");
			out.println("<OPTION VALUE='OH'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("OH")) ? " SELECTED" : "") + ">Ohio</OPTION>");
			out.println("<OPTION VALUE='OK'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("OK")) ? " SELECTED" : "") + ">Oklahoma</OPTION>");
			out.println("<OPTION VALUE='OR'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("OR")) ? " SELECTED" : "") + ">Oregon </OPTION>");
			out.println("<OPTION VALUE='PA'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("PA")) ? " SELECTED" : "")
					+ ">Pennsylvania</OPTION>");
			out.println("<OPTION VALUE='PR'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("PR")) ? " SELECTED" : "")
					+ ">Puerto Rico</OPTION>");
			out.println("<OPTION VALUE='RI'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("RI")) ? " SELECTED" : "")
					+ ">Rhode Island</OPTION>");
			out.println("<OPTION VALUE='SC'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("SC")) ? " SELECTED" : "")
					+ ">South Carolina</OPTION>");
			out.println("<OPTION VALUE='SD'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("SD")) ? " SELECTED" : "")
					+ ">South Dakota</OPTION>");
			out.println("<OPTION VALUE='TN'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("TN")) ? " SELECTED" : "") + ">Tennessee</OPTION>");
			out.println("<OPTION VALUE='TX'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("TX")) ? " SELECTED" : "") + ">Texas</OPTION>");
			out.println("<OPTION VALUE='UT'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("UT")) ? " SELECTED" : "") + ">Utah</OPTION>");
			out.println("<OPTION VALUE='VT'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("VT")) ? " SELECTED" : "") + ">Vermont</OPTION>");
			out.println("<OPTION VALUE='VA'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("VA")) ? " SELECTED" : "") + ">Virginia</OPTION>");
			out.println("<OPTION VALUE='VI'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("VI")) ? " SELECTED" : "")
					+ ">Virgin Islands</OPTION>");
			out.println("<OPTION VALUE='WA'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("WA")) ? " SELECTED" : "") + ">Washington</OPTION>");
			out.println("<OPTION VALUE='WV'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("WV")) ? " SELECTED" : "")
					+ ">West Virginia</OPTION>");
			out.println("<OPTION VALUE='WI'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("WI")) ? " SELECTED" : "") + ">Wisconsin</OPTION>");
			out.println("<OPTION VALUE='WY'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("WY")) ? " SELECTED" : "") + ">Wyoming</OPTION>");
			out.println("<OPTION VALUE='-1'>&nbsp;</OPTION>");
			out.println("<OPTION VALUE='-1'>****** NON-CANADA / NON-US ******</OPTION>");
			out.println("<OPTION VALUE='ZZ'"
					+ ((!StringUtils.isEmpty(this.value) && this.value.equals("ZZ")) ? " SELECTED" : "") + ">Other</OPTION>");
			out.println("</SELECT>");

		}
		catch (IOException e) {
			throw new JspException(e.getMessage());
		}

		return SKIP_BODY;
	}
}