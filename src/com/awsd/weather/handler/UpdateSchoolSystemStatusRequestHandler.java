package com.awsd.weather.handler;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.mail.bean.AlertBean;
import com.awsd.mail.bean.EmailBean;
import com.awsd.mail.bean.EmailException;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.school.School;
import com.awsd.school.SchoolFamily;
import com.awsd.school.Schools;
import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.awsd.weather.ClosureStatus;
import com.awsd.weather.ClosureStatusDB;
import com.awsd.weather.SchoolSystem;
import com.awsd.weather.SchoolSystemDB;
import com.awsd.weather.WeatherCentralException;
import com.esdnl.util.StringUtils;

public class UpdateSchoolSystemStatusRequestHandler implements RequestHandler {

	public static final String DATE_FORMAT_STRING = "MMMM dd, yyyy h:mm a";
	public static final String DATE_FORMAT_STRING_NO_TIME = "MMMM dd, yyyy";

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		HttpSession session = null;
		User usr = null;
		String path = "";
		Personnel p = null;
		SchoolSystem[] systems = null;
		Schools schools = null;
		School school = null;
		Iterator<School> iter = null;
		int all_stat;
		String note = null;
		String start_date = null;
		String rationale = null;
		boolean weather_related = false;
		boolean repeat = false;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		session = request.getSession(false);
		if ((session != null) && (session.getAttribute("usr") != null)) {
			usr = (User) session.getAttribute("usr");
			if (!(usr.getUserPermissions().containsKey("WEATHERCENTRAL-PRINCIPAL-ADMINVIEW") || usr.getUserPermissions().containsKey(
					"WEATHERCENTRAL-GLOBAL-ADMIN"))) {
				throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
			}
		}
		else {
			throw new SecurityException("User login required.");
		}

		try {

			if (request.getParameter("pid") != null) {
				p = PersonnelDB.getPersonnel(Integer.parseInt(request.getParameter("pid")));
			}
			else {
				p = usr.getPersonnel();
				systems = SchoolSystemDB.getSchoolSystems(p);
			}

			if ((systems != null) && (systems.length > 0)) {
				for (int i = 0; i < systems.length; i++) {
					System.err.println("System: " + systems[i].getSchoolSystemName());

					schools = systems[i].getSchoolSystemSchools();

					iter = schools.iterator();

					if (Integer.parseInt(request.getParameter("apply_all")) == systems[i].getSchoolSystemID()) {
						// System.err.println("Apply All Selected");

						school = (School) iter.next();
						if (request.getParameter("SCH_" + school.getSchoolID()) == null) {
							throw new WeatherCentralException("SCHOOL CODES MUST BE ENTERED.");
						}
						else {
							all_stat = Integer.parseInt(request.getParameter("SCH_" + school.getSchoolID()));
							note = request.getParameter("NOTE_" + school.getSchoolID());
							rationale = request.getParameter("RATIONALE_" + school.getSchoolID());
							start_date = request.getParameter("START_DATE_" + school.getSchoolID());

							if ((request.getParameter("REPEAT_" + school.getSchoolID()) != null)
									&& request.getParameter("REPEAT_" + school.getSchoolID()).equalsIgnoreCase("ON"))
								repeat = true;
							else
								repeat = false;

							if ((request.getParameter("WEATHER_RELATED_" + school.getSchoolID()) != null)
									&& request.getParameter("WEATHER_RELATED_" + school.getSchoolID()).equalsIgnoreCase("ON"))
								weather_related = true;
							else
								weather_related = false;

							iter = null;
							iter = schools.iterator();

							while (iter.hasNext()) {
								school = (School) iter.next();
								if (!repeat) {
									SchoolSystemDB.updateSchoolSystemSchoolStatus(school.getSchoolID(), all_stat, note,
											sdf.parse(start_date), weather_related, rationale);

									if (weather_related) {
										try {
											sendWeatherNotification(school.getSchoolFamily(), school, sdf.parse(start_date),
													ClosureStatusDB.getClosureStatus(all_stat), note, rationale);
										}
										catch (Exception e) {
											try {
												new AlertBean(e);
											}
											catch (EmailException e1) {}
										}
									}
								}
								else {
									Calendar today = Calendar.getInstance();
									Calendar start = Calendar.getInstance();

									start.setTime(sdf.parse(start_date));
									while (today.before(start)) {
										SchoolSystemDB.updateSchoolSystemSchoolStatus(school.getSchoolID(), all_stat, note,
												today.getTime(), weather_related, rationale);

										if (weather_related) {
											try {
												sendWeatherNotification(school.getSchoolFamily(), school, today.getTime(),
														ClosureStatusDB.getClosureStatus(all_stat), note, rationale);
											}
											catch (Exception e) {
												try {
													new AlertBean(e);
												}
												catch (EmailException e1) {}
											}
										}

										today.add(Calendar.DATE, 1);
									}
									SchoolSystemDB.updateSchoolSystemSchoolStatus(school.getSchoolID(), all_stat, note, today.getTime(),
											weather_related, rationale);

									if (weather_related) {
										try {
											sendWeatherNotification(school.getSchoolFamily(), school, today.getTime(),
													ClosureStatusDB.getClosureStatus(all_stat), note, rationale);
										}
										catch (Exception e) {
											try {
												new AlertBean(e);
											}
											catch (EmailException e1) {}
										}
									}
								}
							}
						}
					}
					else {
						while (iter.hasNext()) {
							school = (School) iter.next();

							if (request.getParameter("SCH_" + school.getSchoolID()) == null) {
								throw new WeatherCentralException("ALL SCHOOL CODES MUST BE ENTERED.");
							}
						}

						iter = schools.iterator();

						while (iter.hasNext()) {
							school = (School) iter.next();

							start_date = request.getParameter("START_DATE_" + school.getSchoolID());

							if ((request.getParameter("REPEAT_" + school.getSchoolID()) != null)
									&& request.getParameter("REPEAT_" + school.getSchoolID()).equalsIgnoreCase("ON"))
								repeat = true;
							else
								repeat = false;

							rationale = request.getParameter("RATIONALE_" + school.getSchoolID());

							if ((request.getParameter("WEATHER_RELATED_" + school.getSchoolID()) != null)
									&& request.getParameter("WEATHER_RELATED_" + school.getSchoolID()).equalsIgnoreCase("ON"))
								weather_related = true;
							else
								weather_related = false;

							if (!repeat) {
								SchoolSystemDB.updateSchoolSystemSchoolStatus(school.getSchoolID(),
										Integer.parseInt(request.getParameter("SCH_" + school.getSchoolID())),
										request.getParameter("NOTE_" + school.getSchoolID()), sdf.parse(start_date), weather_related,
										rationale);

								if (weather_related) {
									try {
										sendWeatherNotification(
												school.getSchoolFamily(),
												school,
												sdf.parse(start_date),
												ClosureStatusDB.getClosureStatus(Integer.parseInt(request.getParameter("SCH_"
														+ school.getSchoolID()))), request.getParameter("NOTE_" + school.getSchoolID()), rationale);
									}
									catch (Exception e) {
										try {
											new AlertBean(e);
										}
										catch (EmailException e1) {}
									}
								}
							}
							else {
								Calendar today = Calendar.getInstance();
								Calendar start = Calendar.getInstance();

								start.setTime(sdf.parse(start_date));
								while (today.before(start)) {
									SchoolSystemDB.updateSchoolSystemSchoolStatus(school.getSchoolID(),
											Integer.parseInt(request.getParameter("SCH_" + school.getSchoolID())),
											request.getParameter("NOTE_" + school.getSchoolID()), today.getTime(), weather_related, rationale);

									if (weather_related) {
										try {
											sendWeatherNotification(
													school.getSchoolFamily(),
													school,
													today.getTime(),
													ClosureStatusDB.getClosureStatus(Integer.parseInt(request.getParameter("SCH_"
															+ school.getSchoolID()))), request.getParameter("NOTE_" + school.getSchoolID()),
													rationale);
										}
										catch (Exception e) {
											try {
												new AlertBean(e);
											}
											catch (EmailException e1) {}
										}
									}

									today.add(Calendar.DATE, 1);
								}
								SchoolSystemDB.updateSchoolSystemSchoolStatus(school.getSchoolID(),
										Integer.parseInt(request.getParameter("SCH_" + school.getSchoolID())),
										request.getParameter("NOTE_" + school.getSchoolID()), today.getTime(), weather_related, rationale);

								if (weather_related) {
									try {
										sendWeatherNotification(
												school.getSchoolFamily(),
												school,
												today.getTime(),
												ClosureStatusDB.getClosureStatus(Integer.parseInt(request.getParameter("SCH_"
														+ school.getSchoolID()))), request.getParameter("NOTE_" + school.getSchoolID()), rationale);
									}
									catch (Exception e) {
										try {
											new AlertBean(e);
										}
										catch (EmailException e1) {}
									}
								}
							}
						}
					}
				}
				request.setAttribute("SchoolSystems", systems);
			}
			else {
				school = p.getSchool();

				if (request.getParameter("SCH_" + school.getSchoolID()) == null) {
					throw new WeatherCentralException("ALL SCHOOL CODES MUST BE ENTERED.");
				}

				start_date = request.getParameter("START_DATE_" + school.getSchoolID());

				if ((request.getParameter("REPEAT_" + school.getSchoolID()) != null)
						&& request.getParameter("REPEAT_" + school.getSchoolID()).equalsIgnoreCase("ON"))
					repeat = true;
				else
					repeat = false;

				rationale = request.getParameter("RATIONALE_" + school.getSchoolID());

				if ((request.getParameter("WEATHER_RELATED_" + school.getSchoolID()) != null)
						&& request.getParameter("WEATHER_RELATED_" + school.getSchoolID()).equalsIgnoreCase("ON"))
					weather_related = true;
				else
					weather_related = false;

				if (!repeat) {
					SchoolSystemDB.updateSchoolSystemSchoolStatus(school.getSchoolID(),
							Integer.parseInt(request.getParameter("SCH_" + school.getSchoolID())),
							request.getParameter("NOTE_" + school.getSchoolID()), sdf.parse(start_date), weather_related, rationale);

					if (weather_related) {
						try {
							sendWeatherNotification(
									school.getSchoolFamily(),
									school,
									sdf.parse(start_date),
									ClosureStatusDB.getClosureStatus(Integer.parseInt(request.getParameter("SCH_" + school.getSchoolID()))),
									request.getParameter("NOTE_" + school.getSchoolID()), rationale);
						}
						catch (Exception e) {
							try {
								new AlertBean(e);
							}
							catch (EmailException e1) {}
						}
					}
				}
				else {
					Calendar today = Calendar.getInstance();
					Calendar start = Calendar.getInstance();

					start.setTime(sdf.parse(start_date));
					while (today.before(start)) {
						SchoolSystemDB.updateSchoolSystemSchoolStatus(school.getSchoolID(),
								Integer.parseInt(request.getParameter("SCH_" + school.getSchoolID())),
								request.getParameter("NOTE_" + school.getSchoolID()), today.getTime(), weather_related, rationale);

						if (weather_related) {
							try {
								sendWeatherNotification(
										school.getSchoolFamily(),
										school,
										today.getTime(),
										ClosureStatusDB.getClosureStatus(Integer.parseInt(request.getParameter("SCH_"
												+ school.getSchoolID()))), request.getParameter("NOTE_" + school.getSchoolID()), rationale);
							}
							catch (Exception e) {
								try {
									new AlertBean(e);
								}
								catch (EmailException e1) {}
							}
						}

						today.add(Calendar.DATE, 1);
					}
					SchoolSystemDB.updateSchoolSystemSchoolStatus(school.getSchoolID(),
							Integer.parseInt(request.getParameter("SCH_" + school.getSchoolID())),
							request.getParameter("NOTE_" + school.getSchoolID()), today.getTime(), weather_related, rationale);

					if (weather_related) {
						try {
							sendWeatherNotification(
									school.getSchoolFamily(),
									school,
									today.getTime(),
									ClosureStatusDB.getClosureStatus(Integer.parseInt(request.getParameter("SCH_" + school.getSchoolID()))),
									request.getParameter("NOTE_" + school.getSchoolID()), rationale);
						}
						catch (Exception e) {
							try {
								new AlertBean(e);
							}
							catch (EmailException e1) {}
						}
					}
				}
			}

			path = "principaladmin.jsp"
					+ ((request.getParameter("pid") != null) ? "?pid=" + request.getParameter("pid") : "");
		}
		catch (ParseException e) {
			e.printStackTrace();
		}

		return path;
	}

	private void sendWeatherNotification(SchoolFamily family, School school, Date dt, ClosureStatus status, String note,
																				String rational) {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_STRING_NO_TIME);
			EmailBean email = new EmailBean();

			email.setSubject("Weather Related School Closure - " + school.getSchoolName() + " - " + sdf.format(dt));

			email.setBody("Weather Related School Closure - " + school.getSchoolName() + " - " + sdf.format(dt)
					+ "<br /><br />Status: " + status.getClosureStatusDescription() + "<br /><br />Rational:<br />"
					+ StringUtils.encodeHTML2(rational) + "<br /><br />Notes:<br />" + StringUtils.encodeHTML2(note));

			email.setTo(family.getProgramSpecialist().getEmailAddress());
			email.setBCC("chriscrane@nlesd.ca");

			email.send();
		}
		catch (EmailException ee) {
			try {
				new AlertBean(ee);
			}
			catch (Exception e) {}
		}
	}
}