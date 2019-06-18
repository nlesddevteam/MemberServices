package com.nlesd.school.site.handler.admin;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.awsd.school.bean.RegionException;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.school.bean.SchoolStreamSchoolsBean;
import com.nlesd.school.bean.SchoolStreamSelectListBean;

public class EditSchoolRequestHandler extends RequestHandlerImpl {

	public EditSchoolRequestHandler() {

		this.requiredPermissions = new String[] {
			"MEMBERADMIN-VIEW","WEBMAINTENANCE-SCHOOLPROFILE-PRINCIPAL","WEBMAINTENANCE-SCHOOLPROFILE-SECRETARY","WEBMAINTENANCE-SCHOOLPROFILE-ADMIN"
		};

		this.validator = new FormValidator(new FormElement[] {
			new RequiredFormElement("id")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {

			School s = SchoolDB.getSchoolFullDetails(form.getInt("id"));
			request.setAttribute("school", s);
			try {
				if(!(s.getRegion() == null))
				{
					if(s.getRegion().getName().toUpperCase().contains("ALL"))
					{
						//ArrayList<SchoolStreamSelectListBean> allschools = SchoolDB.getSchoolsByRegionZone(s);
						//get all schools for region
						ArrayList<SchoolStreamSelectListBean> selectedenglish = SchoolDB.getSchoolsByRegionZone(s);
						ArrayList<SchoolStreamSelectListBean> selectedfrench = SchoolDB.getSchoolsByRegionZone(s);
						if(!((s.getSchoolStreams()) == null))
						{
							for(SchoolStreamSelectListBean ssslb : selectedenglish)
							{
								//now we check to see what the selected ones are and mark them
								for(SchoolStreamSchoolsBean bean : s.getSchoolStreams().getSchoolStreamsEnglish())
								{
									if(ssslb.getSchoolId().equals(bean.getSchoolId()))
									{
										ssslb.setSelected("SELECTED");
									}

								}
							}
							for(SchoolStreamSelectListBean ssslb : selectedfrench)
							{
								//now we check to see what the selected ones are and mark them
								for(SchoolStreamSchoolsBean bean : s.getSchoolStreams().getSchoolStreamsFrench())
								{
									if(ssslb.getSchoolId().equals(bean.getSchoolId()))
									{
										ssslb.setSelected("SELECTED");
									}

								}
							}
						}

						
						request.setAttribute("schoollistenglish", selectedenglish);
						request.setAttribute("schoollistfrench", selectedfrench);
					}else{
						//get schools for zone
						if(!(s.getZone() ==  null))
						{
							ArrayList<SchoolStreamSelectListBean> selectedenglish = SchoolDB.getSchoolsByZone(s);
							ArrayList<SchoolStreamSelectListBean> selectedfrench = SchoolDB.getSchoolsByZone(s);
							if(!((s.getSchoolStreams()) == null))
							{
								for(SchoolStreamSelectListBean ssslb : selectedenglish)
								{
									//now we check to see what the selected ones are and mark them
									for(SchoolStreamSchoolsBean bean : s.getSchoolStreams().getSchoolStreamsEnglish())
									{
										if(ssslb.getSchoolId().equals(bean.getSchoolId()))
										{
											ssslb.setSelected("SELECTED");
										}

									}
								}
								for(SchoolStreamSelectListBean ssslb : selectedfrench)
								{
									//now we check to see what the selected ones are and mark them
									for(SchoolStreamSchoolsBean bean : s.getSchoolStreams().getSchoolStreamsFrench())
									{
										if(ssslb.getSchoolId().equals(bean.getSchoolId()))
										{
											ssslb.setSelected("SELECTED");
										}

									}
								}
							}

							
							request.setAttribute("schoollistenglish", selectedenglish);
							request.setAttribute("schoollistfrench", selectedfrench);
						}
					}
				}
			} catch (RegionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
			
			
//********* ADDED BY GEOFF TO ALLOW BUS STAFF TO UPDATE SCHOOL ROUTE DOCS IN SCHOOL PROFILES ONLY AND REDIRECT ACCORDINGLY ******
			
			if((usr.checkRole("BUSROUTE-POST"))) {
				
				path = "/WebUpdateSystem/BusRoutes/school_directory_bus_routes_edit.jsp";
				
			} else if ((usr.checkPermission("WEBMAINTENANCE-SCHOOLPROFILE-PRINCIPAL")) || (usr.checkPermission("WEBMAINTENANCE-SCHOOLPROFILE-SECRETARY"))){			
					path = "/WebUpdateSystem/SchoolProfiles/school_profile_edit.jsp";
				
				
			}  else if ((usr.checkPermission("WEBMAINTENANCE-SCHOOLPROFILE-ADMIN")) && (!usr.checkPermission("WEBMAINTENANCE-SCHOOLPROFILE-PRINCIPAL"))){				
				path = "/WebUpdateSystem/SchoolProfiles/school_profile_edit.jsp";			
				
			} 
			
			
			else {
				request.setAttribute("msg", "School updated successfully.");
				path = "school_profile_edit.jsp";
			}
			
			
		}
		else {
			path = "school_profile.jsp";

			request.setAttribute("msg", "School ID required for editting process.");
		}

		return path;
	}

}
