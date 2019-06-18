package com.esdnl.scrs.site.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.esdnl.scrs.domain.ActionType;
import com.esdnl.scrs.domain.BullyingException;
import com.esdnl.scrs.service.ActionTypeService;

public class ActionTypeCheckboxListTagHandler extends TagSupport {

	private static final long serialVersionUID = -5901352348568950336L;
	private String id;
	private String cls;
	private String style;
	private String label;
	private ArrayList<ActionType> value;

	public void setId(String id) {

		this.id = id;
	}

	public void setCls(String cls) {

		this.cls = cls;
	}

	public void setStyle(String style) {

		this.style = style;
	}

	public void setLabel(String label) {

		this.label = label;
	}

	public void setValue(ArrayList<ActionType> value) {

		this.value = value;
	}

	public int doStartTag() throws JspException {

		JspWriter out = null;
		HashMap<Integer, ActionType> valueMap = new HashMap<Integer, ActionType>();

		try {
			out = pageContext.getOut();

			if (this.value != null && this.value.size() > 0) {
				for (ActionType type : this.value) {
					valueMap.put(type.getTypeId(), type);
				}
			}

			if (StringUtils.isNotEmpty(this.label)) {
				out.println("<span class='checkboxlist-label'>" + this.label + "</span><br />");
			}

			for (ActionType type : ActionTypeService.getActionTypes()) {
				renderCheckboxItem(type, out, valueMap);
			}
		}
		catch (BullyingException e) {
			throw new JspException(e.getMessage());
		}
		catch (IOException e) {
			throw new JspException(e.getMessage());
		}

		return SKIP_BODY;
	}

	private void renderCheckboxItem(ActionType type, JspWriter out, HashMap<Integer, ActionType> valueMap)
			throws IOException,
				BullyingException {

		out.print("<input type='checkbox' name=\"" + this.id + "\" id=\"" + this.id + "_" + type.getTypeId() + "\"");
		if (StringUtils.isNotEmpty(this.cls) || type.isIsSpecified() || type.isParent()) {
			String cssclass = "";
			if (StringUtils.isNotEmpty(this.cls))
				cssclass += (" " + this.cls);
			if (type.isIsSpecified())
				cssclass += (" other");
			if (type.isParent())
				cssclass += (" isparent");

			out.print(" class=\"" + cssclass.trim() + "\"");
		}
		if (StringUtils.isNotEmpty(this.style))
			out.print(" style=\"" + this.style + "\"");
		out.print(" value=\"" + type.getTypeId() + "\" "
				+ (valueMap.containsKey(type.getTypeId()) ? "CHECKED=CHECKED" : "") + " />");
		out.println("<label for=\"" + this.id + "_" + type.getTypeId() + "\">" + type.getTypeName() + "</label>");
		if (type.isIsSpecified()) {
			out.print("<input type='text' name=\"" + this.id + "_" + type.getTypeId() + "_specified\" id=\"" + this.id + "_"
					+ type.getTypeId() + "_specified\"");
			if (StringUtils.isNotEmpty(this.cls))
				out.print(" class=\"" + this.cls + "\"");
			out.print(" style=\"display:none;width:250px;");
			if (StringUtils.isNotEmpty(this.style))
				out.print(this.style);
			out.print("\" "
					+ (valueMap.containsKey(type.getTypeId()) ? "value=\""
							+ ((ActionType) valueMap.get(type.getTypeId())).getSpecified() + "\"" : "") + "/>");
		}
		out.println("<br/>");

		if (type.isParent()) {
			out.println("<div id='pnl_" + this.id + "_" + type.getTypeId() + "' class='referral-reason-box'><div>");
			for (ActionType child : ActionTypeService.getActionTypes(type.getTypeId())) {
				renderCheckboxItem(child, out, valueMap);
			}
			out.println("</div></div>");
		}
	}

}