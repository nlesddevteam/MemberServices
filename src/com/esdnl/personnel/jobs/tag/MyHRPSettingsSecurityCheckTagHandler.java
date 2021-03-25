package com.esdnl.personnel.jobs.tag;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.awsd.security.User;
import com.esdnl.personnel.jobs.bean.MyHrpSettingsBean;
import com.esdnl.personnel.jobs.dao.MyHrpSettingsManager;

public class MyHRPSettingsSecurityCheckTagHandler extends TagSupport {

	private static final long serialVersionUID = 2463651080530836006L;
	private String permissions;
	private String npermissions;
	private String roles;
	private boolean allRequired;
	private String setting;
	private Object expectedValue;
	private String redirectURL;
	private String redirectMsg;

	public void setPermissions(String permissions) {

		this.permissions = permissions;
	}

	public void setNpermissions(String npermissions) {

		this.npermissions = npermissions;
	}

	public void setRoles(String roles) {

		this.roles = roles;
	}

	public void setAllRequired(boolean allRequired) {

		this.allRequired = allRequired;
	}

	public String getSetting() {

		return setting;
	}

	public void setSetting(String setting) {

		this.setting = setting;
	}

	public Object getExpectedValue() {

		return expectedValue;
	}

	public void setExpectedValue(Object expectedValue) {

		this.expectedValue = expectedValue;
	}

	public String getRedirectURL() {

		return redirectURL;
	}

	public void setRedirectURL(String redirectURL) {

		this.redirectURL = redirectURL;
	}

	public String getRedirectMsg() {

		return redirectMsg;
	}

	public void setRedirectMsg(String redirectMsg) {

		this.redirectMsg = redirectMsg;
	}

	public int doStartTag() throws JspException {

		User usr = null;
		MyHrpSettingsBean settings = MyHrpSettingsManager.getMyHrpSettings();

		usr = (User) pageContext.getAttribute("usr", PageContext.SESSION_SCOPE);

		if (usr != null) {
			String[] permissions = (this.permissions != null) ? this.permissions.split(",") : null;
			String[] npermissions = (this.npermissions != null) ? this.npermissions.split(",") : null;
			String[] roles = (this.roles != null) ? this.roles.split(",") : null;

			boolean valid = false;

			if (permissions != null || npermissions != null || roles != null) {
				boolean pcheck;
				if (permissions != null) {
					if (allRequired) {
						pcheck = true;
						for (int i = 0; i < permissions.length; i++) {
							if (!usr.checkPermission(permissions[i])) {
								pcheck = false;
							}
						}
					}
					else {
						pcheck = false;
						for (int i = 0; i < permissions.length; i++) {
							if (usr.checkPermission(permissions[i])) {
								pcheck = true;
								break;
							}
						}
					}
				}
				else {
					pcheck = true;
				}

				boolean rcheck;
				if (roles != null) {
					if (allRequired) {
						rcheck = true;
						for (int i = 0; i < roles.length; i++) {
							if (!usr.checkRole(roles[i])) {
								rcheck = false;
							}
						}
					}
					else {
						rcheck = false;
						for (int i = 0; i < roles.length; i++) {
							if (usr.checkRole(roles[i])) {
								rcheck = true;
								break;
							}
						}
					}
				}
				else {
					rcheck = true;
				}

				boolean npcheck;
				if (npermissions != null) {
					npcheck = true;
					for (String perm : npermissions) {
						if (usr.checkPermission(perm)) {
							npcheck = false;
							break;
						}
					}
				}
				else {
					npcheck = true;
				}

				valid = pcheck && rcheck && npcheck;

				if (valid) {
					try {
						Method m = settings.getClass().getDeclaredMethod(this.setting);

						if (m.getReturnType().isInstance(this.expectedValue)) {
							Object retval = m.invoke(settings);

							if ((retval == null) && (this.expectedValue == null)) {
								valid = true;
							}
							else if (retval.equals(this.expectedValue)) {
								valid = true;
							}
							else {
								valid = false;
							}
						}
						else {
							valid = false;
						}
					}
					catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						valid = false;
					}
				}
				else {
					// usr didn't have permisions or roles associated with the settings, so allow entry
					valid = true;
				}
			}
			else {
				valid = true;
			}

			if (!valid) {
				if (StringUtils.isNotBlank(this.redirectMsg)) {
					this.pageContext.setAttribute("msg", this.redirectMsg, PageContext.REQUEST_SCOPE);
				}

				try {
					if (StringUtils.isNotBlank(this.redirectURL)) {
						this.pageContext.forward(this.redirectURL);
					}
					else {
						this.pageContext.forward("admin_index.jsp");
					}
				}
				catch (ServletException | IOException e) {
					throw new JspException(e.getMessage());
				}

				return SKIP_PAGE;
			}
		}
		else {
			if (StringUtils.isNotBlank(this.redirectMsg)) {
				this.pageContext.setAttribute("msg", this.redirectMsg, PageContext.REQUEST_SCOPE);
			}

			try {
				if (StringUtils.isNotBlank(this.redirectURL)) {
					this.pageContext.forward(this.redirectURL);
				}
				else {
					this.pageContext.forward("admin_index.jsp");
				}
			}
			catch (ServletException | IOException e) {
				throw new JspException(e.getMessage());
			}

			return SKIP_PAGE;
		}

		return EVAL_PAGE;
	}

}
