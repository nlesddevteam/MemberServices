package com.nlesd.psimport.handler;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.awsd.school.SchoolFamily;
import com.awsd.school.SchoolFamilyDB;
import com.esdnl.servlet.RequestHandlerImpl;
import com.nlesd.psimport.bean.PSClassInformationBean;
import com.nlesd.psimport.dao.PSClassManager;

public class ViewPSCountsRequestHandler extends RequestHandlerImpl {

	public ViewPSCountsRequestHandler() {

		this.requiredRoles = new String[] {
				"SENIOR EDUCATION OFFICIER", "ASSISTANT DIRECTORS","ADMINISTRATOR","AD HR","DIRECTOR"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
			IOException {

		super.handleRequest(request, response);

		if(form.exists("selschools")) {
			PSClassInformationBean psbean = PSClassManager.getPSClassInformtion(form.getInt("selschools"));
			request.setAttribute("PSDATA", psbean);
			request.setAttribute("SCHOOL", SchoolDB.getSchool(form.getInt("selschools")).getSchoolName());
			
		}
			ArrayList<School> list = new ArrayList<>();
			//check to see which list of schools they get to see
			if(usr.checkRole("SENIOR EDUCATION OFFICIER")) {
				//find their school family
				SchoolFamily sf = SchoolFamilyDB.getSchoolFamily(usr.getPersonnel());
				for(School s : sf.getSchoolFamilySchools()) {
				list.add(s);
				}

			}else if(usr.checkRole("ASSISTANT DIRECTORS") || usr.checkRole("AD HR") || usr.checkRole("ADMINISTRATOR") || usr.checkRole("DIRECTOR")) {
				for(School s : SchoolDB.getSchools()) {
					list.add(s);
				}
			}else {
				//why are you here and how did you get here
				//no schools
			}

			request.setAttribute("schools", list);
		
			path = "admin_view_ps_counts.jsp";


			return path;
	}
}