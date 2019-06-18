package com.esdnl.payadvice.dynamicpdf;

import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.cete.dynamicpdf.Document;

public class DynamicLicenseServlet extends HttpServlet {

	private static final long serialVersionUID = 5027408133225717323L;

	public void init(ServletConfig config) throws ServletException {

		super.init(config);
		Enumeration enum1 = getServletConfig().getInitParameterNames();
		String name;
		String licenseKey;
		while (enum1.hasMoreElements()) {
			name = (String) enum1.nextElement();
			if (name.toLowerCase().startsWith("licensekey")) {
				licenseKey = getServletConfig().getInitParameter(name);
				Document.addLicense(licenseKey);
			}
		}
	}

}