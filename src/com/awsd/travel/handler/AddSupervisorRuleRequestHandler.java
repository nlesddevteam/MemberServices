package com.awsd.travel.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.security.Role;
import com.awsd.security.RoleDB;
import com.awsd.travel.TravelClaimSupervisorRule;
import com.awsd.travel.TravelClaimSupervisorRuleDB;
import com.awsd.travel.constant.KeyType;
import com.awsd.travel.service.DivisionService;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.util.StringUtils;

public class AddSupervisorRuleRequestHandler extends RequestHandlerImpl {

	public AddSupervisorRuleRequestHandler() {

		this.requiredPermissions = new String[] {
			"TRAVEL-CLAIM-ADMIN"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (request.getParameter("op") != null) {
			// ajax call
			if (StringUtils.isEqual(request.getParameter("op"), "KEYTYPE_SELECTED")) {
				System.out.println("AJAX CALL: AddSupervisorRuleRequestHandler");

				KeyType kt = KeyType.get(Integer.parseInt(request.getParameter("kt")));

				if (kt != null) {
					if (kt.equal(KeyType.ROLE)) {
						try {
							Role[] roles = (Role[]) RoleDB.getRoles().toArray(new Role[0]);

							// System.out.println("ROLE_KEYTYPE_SELECTED (length = " +
							// roles.length +")");

							StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
							sb.append("<ROLES>");
							for (int i = 0; i < roles.length; i++)
								sb.append(roles[i].toXML());
							sb.append("</ROLES>");

							// System.out.println("PRE-ENCODING: " + sb.toString());

							String xml = StringUtils.encodeXML(sb.toString());

							// System.out.println(xml);

							PrintWriter out = response.getWriter();

							response.setContentType("text/xml");
							response.setHeader("Cache-Control", "no-cache");
							out.write(xml);
							out.flush();
							out.close();
						}
						catch (Exception e) {
							e.printStackTrace();
						}
					}
					else if (kt.equal(KeyType.USER)) {
						Personnel[] users = (Personnel[]) PersonnelDB.getDistrictPersonnel().toArray(new Personnel[0]);

						StringBuffer sb = new StringBuffer("<?xml version='1.0' encoding='ISO-8859-1'?>");
						sb.append("<USERS>");
						for (int i = 0; i < users.length; i++)
							sb.append(users[i].toXML());
						sb.append("</USERS>");

						String xml = StringUtils.encodeXML(sb.toString());

						PrintWriter out = response.getWriter();

						response.setContentType("text/xml");
						response.setHeader("Cache-Control", "no-cache");
						out.write(xml);
						out.flush();
						out.close();
					}
				}

				path = null;
			}
			else if (form.hasValue("op", "CONFIRM")) {

				this.validator = new FormValidator(new FormElement[] {
						new RequiredFormElement("supervisor_keytype"), new RequiredFormElement("supervisor_key"),
						new RequiredFormElement("user_keytype"), new RequiredFormElement("user_key"),
						new RequiredFormElement("division_id")
				});

				if (validate_form()) {
					TravelClaimSupervisorRule rule = null;

					if (form.exists("rule_id"))
						rule = TravelClaimSupervisorRuleDB.getTravelClaimSupervisorRule(form.getInt("rule_id"));
					else
						rule = new TravelClaimSupervisorRule();

					rule.setSupervisorKeyType(KeyType.get(form.getInt("supervisor_keytype")));
					rule.setSupervisorKey(form.get("supervisor_key"));
					rule.setEmployeeKeyType(KeyType.get(form.getInt("user_keytype")));
					rule.setEmployeeKey(form.get("user_key"));
					rule.setDivision(DivisionService.getDivision(form.getInt("division_id")));

					if (rule.getRuleId() <= 0)
						TravelClaimSupervisorRuleDB.addSupervisorRule(rule);
					else
						TravelClaimSupervisorRuleDB.updateSupervisorRule(rule);

					request.setAttribute("msg", "Supervisor rule added successfully.");
					request.setAttribute("DIVISIONS", DivisionService.getDivisions());
				}
				else
					request.setAttribute("msg", this.validator.getErrorString());

				path = "add_supervisor_rule.jsp";
			}
		}
		else {
			if (form.exists("rule_id")) {
				request.setAttribute("SRULE", TravelClaimSupervisorRuleDB.getTravelClaimSupervisorRule(form.getInt("rule_id")));
			}

			request.setAttribute("DIVISIONS", DivisionService.getDivisions());

			path = "add_supervisor_rule.jsp";
		}

		return path;
	}
}
