package com.awsd.personnel.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.PersonnelException;
import com.esdnl.util.StringUtils;

public class PersonnelListTagHandler extends TagSupport {

	private static final long serialVersionUID = 6779721618928438147L;
	private String id;
	private String cls;
	private String style;
	private Personnel value;
	private String role;

	public void setId(String id) {

		this.id = id;
	}

	public void setCls(String cls) {

		this.cls = cls;
	}

	public void setStyle(String style) {

		this.style = style;
	}

	public void setPersonnel(Personnel value) {

		this.value = value;
	}

	public void setRole(String role) {

		this.role = role;
	}

	public int doStartTag() throws JspException {

		JspWriter out = null;

		try {
			out = pageContext.getOut();

			Personnel[] p = null;
			if (!StringUtils.isEmpty(role))
				p = PersonnelDB.getPersonnelByPermission(role);
			else
				p = (Personnel[]) (PersonnelDB.getDistrictPersonnel().toArray(new Personnel[0]));

			out.print("<SELECT name=\"" + this.id + "\" id=\"" + this.id + "\"");
			if ((this.cls != null) && !this.cls.trim().equals(""))
				out.print(" class=\"" + this.cls + "\"");
			if ((this.style != null) && !this.style.trim().equals(""))
				out.print(" style=\"" + this.style + "\"");
			out.println(">");
			;

			// out.println("<OPTION VALUE=\"0\">NOT APPLICABLE</OPTION>");
			for (int i = 0; i < p.length; i++)
				out.println("<OPTION VALUE=\"" + p[i].getPersonnelID() + "\" " + (p[i].equals(this.value) ? "SELECTED" : "")
						+ ">" + p[i].getFullName() + " [" + p[i].getUserName() + "]</OPTION>");

			out.println("</SELECT>");

		}
		catch (PersonnelException e) {
			throw new JspException(e.getMessage());
		}
		catch (IOException e) {
			throw new JspException(e.getMessage());
		}

		return SKIP_BODY;
	}
}