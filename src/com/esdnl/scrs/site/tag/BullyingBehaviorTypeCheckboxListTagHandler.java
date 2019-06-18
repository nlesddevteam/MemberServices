package com.esdnl.scrs.site.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.esdnl.scrs.domain.BullyingBehaviorType;
import com.esdnl.scrs.domain.BullyingException;
import com.esdnl.scrs.service.BullyingBehaviorTypeService;

public class BullyingBehaviorTypeCheckboxListTagHandler extends TagSupport {

	private static final long serialVersionUID = -5901352348568950336L;
	private String id;
	private String cls;
	private String style;
	private ArrayList<BullyingBehaviorType> value;
	private String label;

	public void setId(String id) {

		this.id = id;
	}

	public void setCls(String cls) {

		this.cls = cls;
	}

	public void setStyle(String style) {

		this.style = style;
	}

	public void setValue(ArrayList<BullyingBehaviorType> value) {

		this.value = value;
	}

	public void setLabel(String label) {

		this.label = label;
	}

	public int doStartTag() throws JspException {

		JspWriter out = null;
		HashMap<Integer, BullyingBehaviorType> valueMap = new HashMap<Integer, BullyingBehaviorType>();

		try {
			out = pageContext.getOut();

			if (this.value != null && this.value.size() > 0) {
				for (BullyingBehaviorType type : this.value) {
					valueMap.put(type.getTypeId(), type);
				}
			}

			if (StringUtils.isNotEmpty(this.label)) {
				out.println("<span class='checkboxlist-label'>" + this.label + "</span><br />");
			}

			for (BullyingBehaviorType type : BullyingBehaviorTypeService.getBullyingBehaviorType()) {
				out.print("<input type='checkbox' name=\"" + this.id + "\" id=\"" + this.id + "_" + type.getTypeId() + "\"");
				if (StringUtils.isNotEmpty(this.cls))
					out.print(" class=\"" + this.cls + "\"");
				if (StringUtils.isNotEmpty(this.style))
					out.print(" style=\"" + this.style + "\"");
				out.print(" value=\"" + type.getTypeId() + "\" "
						+ (valueMap.containsKey(type.getTypeId()) ? "CHECKED=CHECKED" : "") + "/>");
				out.println("<label for=\"" + this.id + "_" + type.getTypeId() + "\">" + type.getTypeName() + "</label>");

				out.println("<br/>");
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
}