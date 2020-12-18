package com.awsd.travel.handler;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.common.Utils;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.pd.PDException;
import com.awsd.personnel.pd.PersonnelPD;
import com.awsd.personnel.pd.PersonnelPDDB;
import com.awsd.personnel.profile.ProfileDB;
import com.awsd.school.School;
import com.awsd.travel.PDTravelClaimEventManager;
import com.awsd.travel.TravelClaimDB;
import com.awsd.travel.TravelClaimSupervisorRuleDB;
import com.esdnl.sds.SDSSchoolInfo;
import com.esdnl.sds.SDSSchoolInfoDB;
import com.esdnl.servlet.RequestHandlerImpl;

public class AddTravelClaimRequestHandler extends RequestHandlerImpl {

	public AddTravelClaimRequestHandler() {

		this.requiredPermissions = new String[] {
				"TRAVEL-EXPENSE-VIEW"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		String op = "";
		String year = "";
		int month = -1, claim_id = -1;
		boolean check = false;
		Personnel supervisor = null;

		TreeMap claims = null;

		op = request.getParameter("op");
		if (ProfileDB.getProfile(usr.getPersonnel()) == null)
			path = "profile.jsp";
		else if (op != null) {
			if (op.equalsIgnoreCase("YEAR-SELECT")) {
				year = request.getParameter("claim_year");
				if ((year != null) && (!year.equals("SELECT YEAR"))) {
					claims = TravelClaimDB.getClaimsTreeMap(usr.getPersonnel(), year);

					request.setAttribute("YEAR-CLAIMS", claims);
					request.setAttribute("YEAR-SELECT", year);

					try {
						request.setAttribute("SUPERVISOR",
								PersonnelDB.getPersonnel(Integer.parseInt(request.getParameter("supervisor_id"))));
					}
					catch (NumberFormatException e) {
						request.setAttribute("SUPERVISOR", null);
					}

					path = "add_claim.jsp";
				}
				else
					path = "add_claim.jsp";
			}
			else if (op.equalsIgnoreCase("CONFIRM")) {

				String claim_type = request.getParameter("claim_type");

				if ((claim_type != null) && (claim_type.equals("0"))) //this is a regular monthly claim.
				{
					try {
						supervisor = PersonnelDB.getPersonnel(Integer.parseInt(request.getParameter("supervisor_id")));
					}
					catch (NumberFormatException e) {
						supervisor = null;
					}

					if (supervisor == null) {
						request.setAttribute("msg", "Please select supervisor.");

						claims = TravelClaimDB.getClaimsTreeMap(usr.getPersonnel(), Utils.getCurrentSchoolYear());

						request.setAttribute("YEAR-CLAIMS", claims);
						request.setAttribute("YEAR-SELECT", Utils.getCurrentSchoolYear());

						path = "add_claim.jsp";
					}
					else {
						year = request.getParameter("claim_year");
						if ((year == null) || (year.equalsIgnoreCase("SELECT YEAR"))) {
							request.setAttribute("msg", "Please select fiscal year.");

							claims = TravelClaimDB.getClaimsTreeMap(usr.getPersonnel(), Utils.getCurrentSchoolYear());

							request.setAttribute("YEAR-CLAIMS", claims);
							request.setAttribute("YEAR-SELECT", Utils.getCurrentSchoolYear());
							request.setAttribute("SUPERVISOR", supervisor);

							path = "add_claim.jsp";
						}
						else {
							try {
								month = Integer.parseInt(request.getParameter("claim_month"));
							}
							catch (NumberFormatException e) {
								month = -1;
							}

							if (month == -1) {
								request.setAttribute("msg", "Please select fiscal month.");

								claims = TravelClaimDB.getClaimsTreeMap(usr.getPersonnel(), year);

								request.setAttribute("YEAR-CLAIMS", claims);
								request.setAttribute("YEAR-SELECT", year);
								request.setAttribute("SUPERVISOR", supervisor);

								path = "add_claim.jsp";
							}
							else {
								//all clear

								claim_id = TravelClaimDB.addClaim(usr.getPersonnel(), supervisor, year, month);

								if (claim_id > 0) {
									request.setAttribute("msg", "Claim added successfully.");
									request.setAttribute("NEW_CLAIM_ID", new Integer(claim_id));

									path = "add_claim.jsp";
								}
								else {
									request.setAttribute("msg", "Claim could not be added.");

									claims = TravelClaimDB.getClaimsTreeMap(usr.getPersonnel(), year);

									request.setAttribute("YEAR-CLAIMS", claims);
									request.setAttribute("YEAR-SELECT", year);
									request.setAttribute("SUPERVISOR", supervisor);

									path = "add_claim.jsp";
								}
							}
						}
					}

					request.setAttribute("CLAIM-TYPE", request.getParameter("claim_type"));
				}
				else if ((claim_type != null) && (claim_type.equals("1"))) //pd travel claim
				{
					String title = request.getParameter("title");
					String desc = request.getParameter("desc");
					String sd = request.getParameter("start_date");
					String fd = request.getParameter("finish_date");

					if ((title == null) || (title.trim().equals(""))) {
						request.setAttribute("msg", "Please enter pd title.");

						claims = TravelClaimDB.getClaimsTreeMap(usr.getPersonnel(), Utils.getCurrentSchoolYear());

						request.setAttribute("YEAR-CLAIMS", claims);
						request.setAttribute("YEAR-SELECT", Utils.getCurrentSchoolYear());

						path = "add_claim.jsp";
					}
					else if ((desc == null) || (desc.trim().equals(""))) {
						request.setAttribute("msg", "Please enter pd description.");

						claims = TravelClaimDB.getClaimsTreeMap(usr.getPersonnel(), Utils.getCurrentSchoolYear());

						request.setAttribute("YEAR-CLAIMS", claims);
						request.setAttribute("YEAR-SELECT", Utils.getCurrentSchoolYear());

						path = "add_claim.jsp";
					}
					else if ((sd == null) || (sd.trim().equals(""))) {
						request.setAttribute("msg", "Please enter pd start date.");

						claims = TravelClaimDB.getClaimsTreeMap(usr.getPersonnel(), Utils.getCurrentSchoolYear());

						request.setAttribute("YEAR-CLAIMS", claims);
						request.setAttribute("YEAR-SELECT", Utils.getCurrentSchoolYear());

						path = "add_claim.jsp";
					}
					else if ((fd == null) || (fd.trim().equals(""))) {
						request.setAttribute("msg", "Please enter pd finish date.");

						claims = TravelClaimDB.getClaimsTreeMap(usr.getPersonnel(), Utils.getCurrentSchoolYear());

						request.setAttribute("YEAR-CLAIMS", claims);
						request.setAttribute("YEAR-SELECT", Utils.getCurrentSchoolYear());

						path = "add_claim.jsp";
					}
					else {
						PersonnelPD pd = null;
						SDSSchoolInfo sds = null;
						School s = null;

						try {
							supervisor = usr.getPersonnel().getSupervisor();
							if (supervisor == null) {
								try {
									supervisor = PersonnelDB.getPersonnel(Integer.parseInt(request.getParameter("pd_supervisor_id")));
								}
								catch (NumberFormatException e) {
									supervisor = null;
								}
							}

							if (supervisor == null) {
								request.setAttribute("msg", "No supervisor on record.");

								claims = TravelClaimDB.getClaimsTreeMap(usr.getPersonnel(), Utils.getCurrentSchoolYear());

								request.setAttribute("YEAR-CLAIMS", claims);
								request.setAttribute("YEAR-SELECT", Utils.getCurrentSchoolYear());

								path = "add_claim.jsp";
							}
							else {
								//all clear

								SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

								pd = new PersonnelPD(title, desc, sdf.parse(sd), sdf.parse(fd));

								Calendar cal = Calendar.getInstance();
								cal.setTime(pd.getStartDate());
								year = Utils.getSchoolYear(cal);
								month = cal.get(Calendar.MONTH);

								try {
									pd = PersonnelPDDB.addPD(usr.getPersonnel(), pd);
								}
								catch (PDException e) {
									pd.setID(-1);
								}

								if (pd.getID() > 0) {
									claim_id = TravelClaimDB.addClaim(usr.getPersonnel(), supervisor, year, month, pd);

									if (claim_id > 0) {
										request.setAttribute("msg", "Claim added successfully.");
										request.setAttribute("NEW_CLAIM_ID", new Integer(claim_id));

										s = usr.getPersonnel().getSchool();
										if (s != null) {
											sds = SDSSchoolInfoDB.getSDSSchoolInfo(s);
											if (sds != null) {
												if ((sds.getPDAcctCode() != null) && !sds.getPDAcctCode().trim().equals(""))
													TravelClaimDB.setClaimGLAccount(TravelClaimDB.getClaim(claim_id), sds.getPDAcctCode());
											}
										}
										path = "add_claim.jsp";
									}
									else {
										PersonnelPDDB.deletePD(pd.getID());

										request.setAttribute("msg", "Claim could not be added.");

										claims = TravelClaimDB.getClaimsTreeMap(usr.getPersonnel(), year);

										request.setAttribute("YEAR-CLAIMS", claims);
										request.setAttribute("YEAR-SELECT", year);
										request.setAttribute("SUPERVISOR", supervisor);

										path = "add_claim.jsp";
									}
								}
								else {
									request.setAttribute("msg", "PD could not be added.");

									claims = TravelClaimDB.getClaimsTreeMap(usr.getPersonnel(), year);

									request.setAttribute("YEAR-CLAIMS", claims);
									request.setAttribute("YEAR-SELECT", year);
									request.setAttribute("SUPERVISOR", supervisor);

									path = "add_claim.jsp";
								}
							}
						}
						catch (ParseException e) {
							request.setAttribute("msg", "Error receiving pd information.");

							claims = TravelClaimDB.getClaimsTreeMap(usr.getPersonnel(), Utils.getCurrentSchoolYear());

							request.setAttribute("YEAR-CLAIMS", claims);
							request.setAttribute("YEAR-SELECT", Utils.getCurrentSchoolYear());

							path = "add_claim.jsp";
						}
					}

					request.setAttribute("CLAIM-TYPE", request.getParameter("claim_type"));
				}
				else {
					request.setAttribute("msg", "Invalid claim type.");

					claims = TravelClaimDB.getClaimsTreeMap(usr.getPersonnel(), Utils.getCurrentSchoolYear());

					request.setAttribute("YEAR-CLAIMS", claims);
					request.setAttribute("YEAR-SELECT", Utils.getCurrentSchoolYear());

					path = "add_claim.jsp";
				}
			}
			else {
				request.setAttribute("msg", "Invalid option.");

				claims = TravelClaimDB.getClaimsTreeMap(usr.getPersonnel(), Utils.getCurrentSchoolYear());

				request.setAttribute("YEAR-CLAIMS", claims);
				request.setAttribute("YEAR-SELECT", Utils.getCurrentSchoolYear());

				path = "add_claim.jsp";
			}
		}
		else {
			claims = TravelClaimDB.getClaimsTreeMap(usr.getPersonnel(), Utils.getCurrentSchoolYear());

			request.setAttribute("YEAR-CLAIMS", claims);
			request.setAttribute("YEAR-SELECT", Utils.getCurrentSchoolYear());
			request.setAttribute("PDEVENTS", PDTravelClaimEventManager.getAllPDEventsByUserTM(usr.getPersonnel().getPersonnelID()));
			path = "add_claim.jsp";
		}

		request.setAttribute("SUPERVISORS", TravelClaimSupervisorRuleDB.getTravelClaimSupervisors(usr.getPersonnel()));

		return path;
	}
}