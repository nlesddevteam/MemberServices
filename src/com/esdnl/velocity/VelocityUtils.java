package com.esdnl.velocity;

import java.io.StringWriter;
import java.util.HashMap;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.tools.generic.DateTool;

public class VelocityUtils {

	public static String mergeTemplateIntoString(String templatePathName, HashMap<String, Object> model) {

		/* lets make a Context and put data into it */

		VelocityContext context = new VelocityContext();
		context.put("date", new DateTool());

		if (model != null && model.size() > 0) {
			for (String key : model.keySet()) {
				context.put(key, model.get(key));
			}
		}

		Template template = null;

		template = Velocity.getTemplate(templatePathName);

		StringWriter sw = new StringWriter();

		template.merge(context, sw);

		return sw.toString();

	}
}
