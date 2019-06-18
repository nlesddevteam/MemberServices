package com.esdnl.mrs.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.bean.EmailException;
import com.awsd.personnel.PersonnelDB;
import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.esdnl.mrs.CapitalType;
import com.esdnl.mrs.CapitalTypeDB;
import com.esdnl.mrs.MaintenanceRequest;
import com.esdnl.mrs.MaintenanceRequestDB;
import com.esdnl.mrs.RequestCategory;
import com.esdnl.mrs.RequestCategoryDB;
import com.esdnl.mrs.RequestComment;
import com.esdnl.mrs.RequestType;
import com.esdnl.mrs.RequestTypeDB;
import com.esdnl.mrs.StatusCode;
import com.esdnl.mrs.StatusCodeDB;
import com.esdnl.mrs.Vendor;
import com.esdnl.mrs.VendorDB;

public class ViewAdminRequestDetailsRequestHandler implements RequestHandler {

	@SuppressWarnings("unchecked")
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		HttpSession session = null;
		User usr = null;
		String path = "";
		MaintenanceRequest req = null;

		session = request.getSession(false);
		if ((session != null) && (session.getAttribute("usr") != null)) {
			usr = (User) session.getAttribute("usr");
			if (!(usr.getUserPermissions().containsKey("MAINTENANCE-ADMIN-VIEW"))) {
				throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
			}
		}

		if (request.getParameter("op") != null) {
			if (request.getParameter("op").equalsIgnoreCase("CONFIRM")) {
				if ((request.getParameter("req") == null) || (request.getParameter("req").equals(""))) {
					request.setAttribute("msg", "REQUEST ID REQUIRED");
				}
				else if ((request.getParameter("new_status") == null) || (request.getParameter("new_status").equals(""))) {
					request.setAttribute("msg", "REQUEST STATUS REQUIRED");
				}
				else if ((request.getParameter("cat_id") == null) || (request.getParameter("cat_id").equals(""))) {
					request.setAttribute("msg", "REQUEST CATEGORY REQUIRED");
				}
				else if ((request.getParameter("est_cost") == null) || (request.getParameter("est_cost").equals(""))) {
					request.setAttribute("msg", "ESTIMATED COST REQUIRED");
				}
				else {
					req = MaintenanceRequestDB.getMaintenanceRequest(Integer.parseInt(request.getParameter("req")));

					if (request.getParameter("new_status").equals("ASSIGNED")
							&& ((request.getParameterValues("maint_per_id") == null) || (request.getParameterValues("maint_per_id").length == 0))
							&& ((request.getParameterValues("vendor_id") == null) || (request.getParameterValues("vendor_id").length == 0))) {
						request.setAttribute("msg", "A maintenance person AND/OR vendor must be select to assign.");
					}
					else if (request.getParameter("cat_id").equals("CAPITAL")
							&& ((request.getParameter("cap_type_id") == null) || (request.getParameter("cap_type_id").equals("-1")))) {
						request.setAttribute("msg", "CAPITAL TYPE required.");
					}
					else if (request.getParameter("cat_id").equals("CAPITAL")
							&& (request.getParameter("capital_priority") == null)) {
						request.setAttribute("msg", "CAPITAL PRIORITY required.");
					}
					else {
						//form is completed correctly
						req.setCurrentStatus(new StatusCode(request.getParameter("new_status")));

						if (request.getParameter("new_status").equals("ASSIGNED")) {
							MaintenanceRequestDB.assignMaintentancePersonnel(req, request.getParameterValues("maint_per_id"));
							MaintenanceRequestDB.assignVendor(req, request.getParameterValues("vendor_id"));
						}

						if ((request.getParameter("req_comment") != null) && (!request.getParameter("req_comment").equals(""))) {
							MaintenanceRequestDB.addRequestComment(new RequestComment(req, usr.getPersonnel(), request.getParameter("req_comment")));
						}

						req.setRequestCategory(new RequestCategory(request.getParameter("cat_id")));

						if (request.getParameter("cat_id").equals("CAPITAL")) {
							req.setCatiptalType(new CapitalType(request.getParameter("cap_type_id")));
							req.setCapitalPriority(Integer.parseInt(request.getParameter("capital_priority")));
							req.setCapitalFundingApproved((request.getParameter("fund_approved") != null) ? true : false);
						}

						req.setEstimatedCost(Double.parseDouble(request.getParameter("est_cost").replaceAll("$,", "")));

						MaintenanceRequestDB.updateAdminMaintRequest(req);

						req = MaintenanceRequestDB.getMaintenanceRequest(req.getRequestID());

						//send email if necessary
						if (request.getParameter("new_status").equals("ASSIGNED")) {
							try {
								EmailBean email = new EmailBean();
								email.setTo(PersonnelDB.getPersonnel(request.getParameterValues("maint_per_id")));
								email.setSubject("MRS: Work Order Received.");
								email.setFrom("noreply@nlesd.ca");

								email.setBody(req.toEmail(MaintenanceRequest.EMAIL_WORKORDER));
								email.send();

								email = new EmailBean();
								email.setTo(req.getSchool().getSchoolPrincipal().getEmailAddress());
								email.setSubject("MRS: Work Order Assigned.");
								email.setFrom("noreply@nlesd.ca");

								email.setBody(req.toEmail(MaintenanceRequest.EMAIL_SCHOOLADMIN));
								email.send();
							}
							catch (EmailException e) {
								e.printStackTrace();
							}
						}
					}
					request.setAttribute(
							"MAX_PRIORITY",
							new Integer(((MaintenanceRequest[]) MaintenanceRequestDB.getOutstandingMaintenanceRequests(
									req.getSchool()).get(0)).length));
				}
				request.setAttribute("MAINT_REQUEST", req);
				request.setAttribute("STATUS_CODES", (StatusCode[]) StatusCodeDB.getStatusCodes().toArray(new StatusCode[0]));
				request.setAttribute("MAINT_PERSONNEL", PersonnelDB.getMaintenanceAssigmentPersonnel());
				request.setAttribute("VENDORS", (Vendor[]) VendorDB.getVendors().toArray(new Vendor[0]));
				request.setAttribute(
						"REQUEST_COMMENTS",
						(RequestComment[]) MaintenanceRequestDB.getMaintenanceRequestComments(
								Integer.parseInt(request.getParameter("req"))).toArray(new RequestComment[0]));
				request.setAttribute("CAPITAL_TYPES",
						(CapitalType[]) CapitalTypeDB.getCapitalTypes().toArray(new CapitalType[0]));
				request.setAttribute("CATEGORIES",
						(RequestCategory[]) RequestCategoryDB.getRequestCategories().toArray(new RequestCategory[0]));
				request.setAttribute("ASSIGNED_PERSONNEL",
						PersonnelDB.getMaintenanceRequestAssignedPersonnel(Integer.parseInt(request.getParameter("req"))));
				request.setAttribute("ASSIGNED_VENDORS",
						VendorDB.getRequestAssignedVendors(Integer.parseInt(request.getParameter("req"))));
				request.setAttribute("REQUEST_TYPES",
						(RequestType[]) RequestTypeDB.getRequestTypes().toArray(new RequestType[0]));

				path = "admin_request_details.jsp";
			}
			else {
				request.setAttribute("msg", "INVALID OPTION");

				path = "error_message.jsp";
			}
		}
		else {
			if (request.getParameter("req") != null) {
				req = MaintenanceRequestDB.getMaintenanceRequest(Integer.parseInt(request.getParameter("req")),
						usr.getPersonnel());
				request.setAttribute("MAINT_REQUEST", req);
				request.setAttribute(
						"MAX_PRIORITY",
						new Integer(((MaintenanceRequest[]) MaintenanceRequestDB.getOutstandingMaintenanceRequests(req.getSchool()).get(
								0)).length));
				request.setAttribute("STATUS_CODES", (StatusCode[]) StatusCodeDB.getStatusCodes().toArray(new StatusCode[0]));
				request.setAttribute("MAINT_PERSONNEL", PersonnelDB.getMaintenanceAssigmentPersonnel());
				request.setAttribute("VENDORS", (Vendor[]) VendorDB.getVendors().toArray(new Vendor[0]));
				request.setAttribute(
						"REQUEST_COMMENTS",
						(RequestComment[]) MaintenanceRequestDB.getMaintenanceRequestComments(
								Integer.parseInt(request.getParameter("req"))).toArray(new RequestComment[0]));
				request.setAttribute("CAPITAL_TYPES",
						(CapitalType[]) CapitalTypeDB.getCapitalTypes().toArray(new CapitalType[0]));
				request.setAttribute("CATEGORIES",
						(RequestCategory[]) RequestCategoryDB.getRequestCategories().toArray(new RequestCategory[0]));
				request.setAttribute("ASSIGNED_PERSONNEL",
						PersonnelDB.getMaintenanceRequestAssignedPersonnel(Integer.parseInt(request.getParameter("req"))));
				request.setAttribute("ASSIGNED_VENDORS",
						VendorDB.getRequestAssignedVendors(Integer.parseInt(request.getParameter("req"))));
				request.setAttribute("REQUEST_TYPES",
						(RequestType[]) RequestTypeDB.getRequestTypes().toArray(new RequestType[0]));
				path = "admin_request_details.jsp";
			}
			else {
				request.setAttribute("msg", "REQUEST ID REQUIRED");

				path = "error_message.jsp";
			}
		}

		return path;
	}
}