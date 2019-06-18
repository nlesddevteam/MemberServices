package com.awsd.admin.apps.personnel.handler;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.awsd.admin.AdminException;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelCategory;
import com.awsd.personnel.PersonnelCategoryDB;
import com.awsd.personnel.PersonnelDB;
import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.awsd.security.SecurityException;
import com.awsd.security.User;
import com.awsd.servlet.RequestHandler;
import com.esdnl.util.ArrayUtils;

public class SchoolAdminChangeRequestHandler implements RequestHandler {

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		String path;
		HttpSession session = null;
		User usr = null;
		School s = null;
		Personnel tmp = null;
		Personnel curp = null;
		HashMap<Integer, Personnel> curvp = null;
		String[] vid = null;
		int sid, pid;

		session = request.getSession(false);
		if ((session != null) && (session.getAttribute("usr") != null)) {
			usr = (User) session.getAttribute("usr");
			if (!(usr.getUserPermissions().containsKey("MEMBERADMIN-VIEW"))) {
				throw new SecurityException("Illegal Access [" + usr.getLotusUserFullName() + "]");
			}
		}
		else {
			throw new SecurityException("User login required.");
		}

		if (request.getParameter("sid") == null) {
			throw new AdminException("School ID Required.");
		}
		else {
			sid = Integer.parseInt(request.getParameter("sid"));
		}

		s = SchoolDB.getSchool(sid);
		curp = s.getSchoolPrincipal();
		curvp = s.getAssistantPrincipalsMap();

		if (request.getParameter("update") == null) {
			request.setAttribute("School", s);

			path = "school_admin_change.jsp";
		}
		else {
			if (request.getParameter("principal") == null) {
				throw new AdminException("Principal ID Required.");
			}
			else {
				pid = Integer.parseInt(request.getParameter("principal"));
			}

			// System.err.println("PID: " + pid);

			if (((vid = request.getParameterValues("viceprincipal")) != null) && (vid.length < 1)) {
				throw new AdminException("Assistant Principal ID(s) Required.");
			}

			// System.err.println("VID: " + vid);

			PersonnelCategory principal = PersonnelCategoryDB.getPersonnelCategory("PRINCIPAL");
			PersonnelCategory viceprincipal = PersonnelCategoryDB.getPersonnelCategory("VICE PRINCIPAL");
			PersonnelCategory teacher = PersonnelCategoryDB.getPersonnelCategory("TEACHER");

			School s_new = s.clone();
			//s_new.setSchoolID(s.getSchoolID());
			//s_new.setSchoolName(s.getSchoolName());
			if (pid > 0)
				s_new.setSchoolPrincipal(PersonnelDB.getPersonnel(pid));
			else
				s_new.setSchoolPrincipal(null);
			s_new.setAssistantPrincipals(vid);
			s_new.save();

			// System.err.println("UPDATED");

			// School Principal Changes
			if (((curp == null) && (pid != -1)) || ((curp != null) && (curp.getPersonnelID() != pid))) {
				// System.err.println("PRINCIPAL CHANGED.");
				// CURRENT VP BECOMES P
				if ((curvp != null) && curvp.containsKey(new Integer(pid))) {
					Personnel p_tmp = (Personnel) curvp.get(new Integer(pid));
					// System.err.println("VP IS NEW PRINCIPAL");
					p_tmp.setPersonnelCategory(principal);
					p_tmp.setSupervisor(null);
					p_tmp.setViewOnNextLogon(null);

					s_new.setSchoolPrincipal(p_tmp);

					s_new.getAssistantPrincipalsMap().remove(new Integer(pid));
					s_new.save();

					// CURRENT P BECOMES VP
					if ((curp != null) && ArrayUtils.contains(vid, Integer.toString(curp.getPersonnelID()))) {
						// System.err.println("P IS NEW VP");
						curp.setPersonnelCategory(viceprincipal);
						curp.setSupervisor(p_tmp);
						curp.setViewOnNextLogon(null);
					}
					else if (curp != null) {
						// System.err.println("P IS NEW TEACHER");
						curp.setPersonnelCategory(teacher);
						curp.setSupervisor(p_tmp);
						curp.setViewOnNextLogon("PROFILE");
					}
				}
				else {
					if (pid != -1) {
						tmp = PersonnelDB.getPersonnel(pid);

						// if was principal of previous school
						School s_prev = tmp.getSchool();
						if ((s_prev != null) && !s_prev.equals(s)) {
							if ((s_prev.getSchoolPrincipal() != null)
									&& (s_prev.getSchoolPrincipal().getPersonnelID() == tmp.getPersonnelID())) {

								School s_tmp = s_prev.clone();
								s_tmp.setSchoolPrincipal(null);
								s_tmp.setAssistantPrincipals(s_prev.getAssistantPrincipalsMap());
								s_tmp.save();
							}
							else if ((s_prev.getAssistantPrincipalsMap() != null)
									&& s_prev.getAssistantPrincipalsMap().containsKey(new Integer(tmp.getPersonnelID()))) {

								School s_tmp = s_prev.clone();
								s_tmp.setSchoolPrincipal(s_prev.getSchoolPrincipal());

								HashMap<Integer, Personnel> s_prev_aps = s_prev.getAssistantPrincipalsMap();
								s_prev_aps.remove(new Integer(tmp.getPersonnelID()));

								s_tmp.setAssistantPrincipals(s_prev_aps);
								s_tmp.save();
							}
						}

						if (!tmp.getPersonnelCategory().equals(principal))
							tmp.setPersonnelCategory(principal);

						tmp.setSchool(s);
						tmp.setSupervisor(null);
						tmp.setViewOnNextLogon(null);
						PersonnelDB.updatePersonnel(tmp);

						s_new.setSchoolPrincipal(tmp);

						// CURRENT P BECOMES VP
						if ((curp != null) && ArrayUtils.contains(vid, Integer.toString(curp.getPersonnelID()))) {
							// System.err.println("P IS NEW VP");
							curp.setPersonnelCategory(viceprincipal);
							curp.setSupervisor(tmp);
							curp.setViewOnNextLogon(null);

							Personnel[] cur_aps = s.getAssistantPrincipals();
							HashMap<Integer, Personnel> new_aps = s_new.getAssistantPrincipalsMap();
							for (int i = 0; i < cur_aps.length; i++) {
								if (!new_aps.containsKey(new Integer(cur_aps[i].getPersonnelID()))) {
									cur_aps[i].setPersonnelCategory(teacher);
									cur_aps[i].setSupervisor(tmp);
									cur_aps[i].setViewOnNextLogon("PROFILE");
								}
							}
						}
						else if (curp != null) {
							// System.err.println("P IS NEW TEACHER");
							curp.setPersonnelCategory(teacher);
							curp.setSupervisor(tmp);
							curp.setViewOnNextLogon("PROFILE");
						}
					}
					else if (curp != null) {
						// System.err.println("P IS UNASSIGNED");
						curp.setPersonnelCategory(teacher);
						curp.setSupervisor(null);
						curp.setViewOnNextLogon("PROFILE");
					}
				}
			}

			// new VP
			if ((vid != null) && (vid.length > 0)) {
				// System.err.println("=== NEW VP");

				if (!ArrayUtils.contains(vid, "-1")) {
					for (int i = 0; i < vid.length; i++) {
						tmp = PersonnelDB.getPersonnel(Integer.parseInt(vid[i]));

						// if was principal of previous school
						School s_prev = tmp.getSchool();
						if ((s_prev != null) && !s_prev.equals(s)) {
							if ((s_prev.getSchoolPrincipal() != null)
									&& (s_prev.getSchoolPrincipal().getPersonnelID() == tmp.getPersonnelID())) {

								School s_tmp = s_prev.clone();
								s_tmp.setSchoolPrincipal(null);
								s_tmp.setAssistantPrincipals(s_prev.getAssistantPrincipalsMap());
								s_tmp.save();
							}
							else if ((s_prev.getAssistantPrincipalsMap() != null)
									&& s_prev.getAssistantPrincipalsMap().containsKey(new Integer(tmp.getPersonnelID()))) {

								School s_tmp = s_prev.clone();
								s_tmp.setSchoolPrincipal(s_prev.getSchoolPrincipal());

								HashMap<Integer, Personnel> s_prev_aps = s_prev.getAssistantPrincipalsMap();
								s_prev_aps.remove(new Integer(tmp.getPersonnelID()));

								s_tmp.setAssistantPrincipals(s_prev_aps);
								s_tmp.save();
							}
						}
						if (!tmp.getPersonnelCategory().equals(viceprincipal))
							tmp.setPersonnelCategory(viceprincipal);

						tmp.setSchool(s);
						tmp.setSupervisor(s_new.getSchoolPrincipal());
						tmp.setViewOnNextLogon(null);
					}

					Personnel[] cur_aps = s.getAssistantPrincipals();
					HashMap<Integer, Personnel> new_aps = s_new.getAssistantPrincipalsMap();
					for (int i = 0; i < cur_aps.length; i++) {
						if (!new_aps.containsKey(new Integer(cur_aps[i].getPersonnelID())) && (cur_aps[i].getPersonnelID() != pid)) {
							// System.err.println("VP IS NEW TEACHER");
							cur_aps[i].setPersonnelCategory(teacher);
							cur_aps[i].setViewOnNextLogon("PROFILE");
						}
					}
				}
				else {
					Personnel[] cur_aps = s.getAssistantPrincipals();
					HashMap<Integer, Personnel> new_aps = s_new.getAssistantPrincipalsMap();
					for (int i = 0; i < cur_aps.length; i++) {
						if (!new_aps.containsKey(new Integer(cur_aps[i].getPersonnelID())) && (cur_aps[i].getPersonnelID() != pid)) {
							// System.err.println("VP IS UNASSIGNED");
							cur_aps[i].setPersonnelCategory(teacher);
							cur_aps[i].setViewOnNextLogon("PROFILE");
						}
					}
				}
			}

			// update supervisor for school
			if (s_new.getSchoolPrincipal() != null)
				s_new.getSchoolPrincipal().setSupervisor(null);

			SchoolDB.updateSchoolTeacherSupervisor(s_new);

			session.setAttribute("SCHOOL-ARRAYLIST", SchoolDB.getSchoolsAlphabetized());
			request.setAttribute("page", request.getParameter("page"));

			path = "school_admin_view.jsp";
		}

		return path;
	}
}