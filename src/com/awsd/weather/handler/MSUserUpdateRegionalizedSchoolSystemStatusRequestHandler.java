package com.awsd.weather.handler;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.School;
import com.awsd.school.bean.RegionBean;
import com.awsd.weather.SchoolSystem;
import com.awsd.weather.SchoolSystemDB;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.util.StringUtils;
import com.nlesd.school.bean.SchoolZoneBean;

public class MSUserUpdateRegionalizedSchoolSystemStatusRequestHandler extends RequestHandlerImpl {

	public MSUserUpdateRegionalizedSchoolSystemStatusRequestHandler() {

		this.requiredPermissions = new String[] {
				"WEATHERCENTRAL-GLOBAL-ADMIN"
		};
	}

	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		try {

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			Date now = Calendar.getInstance().getTime();

			int district_stat = 0;
			String district_note = null;
			Date district_date = null;
			boolean district_weather_related = false;
			boolean district_repeat_to_date = false;
			String district_rationale = null;

			// ENTIRE DISTRICT
			if (form.exists("zone-id-99")) {
				district_stat = form.getInt("lst-zone-status-99");
				district_note = form.get("txt-zone-note-99");
				district_weather_related = form.hasValueIgnoreCase("chk-zone-weather-related-99", "ON");
				district_rationale = form.get("txt-zone-rationale-99");
				try {
					district_date = sdf.parse((form.get("txt-zone-date-99")));
				}
				catch (ParseException dpe) {
					district_date = null;
				}
				district_repeat_to_date = form.hasValueIgnoreCase("chk-zone-repeattodate-99", "ON");
			}

			for (Map.Entry<SchoolZoneBean, HashMap<RegionBean, Vector<SchoolSystem>>> zoneEntry : SchoolSystemDB.getRegionalizedSchoolClosureStatuses2().entrySet()) {

				int zone_stat = 0;
				String zone_note = null;
				Date zone_date = null;
				boolean zone_weather_related = false;
				boolean zone_repeat_to_date = false;
				String zone_rationale = null;

				SchoolZoneBean zone = zoneEntry.getKey();

				if (zone != null && form.exists("zone-id-" + zone.getZoneId())) {
					zone_stat = form.getInt("lst-zone-status-" + zone.getZoneId());
					zone_note = form.get("txt-zone-note-" + zone.getZoneId());
					zone_weather_related = form.hasValueIgnoreCase("chk-zone-weather-related-" + zone.getZoneId(), "ON");
					zone_rationale = form.get("txt-zone-rationale-" + zone.getZoneId());
					try {
						zone_date = sdf.parse((form.get("txt-zone-date-" + zone.getZoneId())));
					}
					catch (ParseException dpe) {
						zone_date = null;
					}
					zone_repeat_to_date = form.hasValueIgnoreCase("chk-zone-repeattodate-" + zone.getZoneId(), "ON");
				}

				for (Map.Entry<RegionBean, Vector<SchoolSystem>> regionEntry : zoneEntry.getValue().entrySet()) {

					int region_stat = 0;
					String region_note = null;
					Date region_date = null;
					boolean region_weather_related = false;
					boolean region_repeat_to_date = false;
					String region_rationale = null;

					RegionBean region = regionEntry.getKey();

					if (region != null && form.exists("region-id-" + region.getId())) {
						region_stat = form.getInt("lst-region-status-" + region.getId());
						region_note = form.get("txt-region-note-" + region.getId());
						region_weather_related = form.hasValueIgnoreCase("chk-region-weather-related-" + region.getId(), "ON");
						region_rationale = form.get("txt-region-rationale-" + region.getId());
						try {
							region_date = sdf.parse((form.get("txt-region-date-" + region.getId())));
						}
						catch (ParseException dpe) {
							region_date = null;
						}
						region_repeat_to_date = form.hasValueIgnoreCase("chk-region-repeattodate-" + region.getId(), "ON");
					}

					for (SchoolSystem sys : regionEntry.getValue()) {

						int sys_stat = 0;
						String sys_note = null;
						Date sys_date = null;
						boolean sys_weather_related = false;
						boolean sys_repeat_to_date = false;
						String sys_rationale = null;

						if (form.exists("ss-id-" + sys.getSchoolSystemID())) {
							sys_stat = form.getInt("lst-ss-status-" + sys.getSchoolSystemID());
							sys_note = form.get("txt-ss-note-" + sys.getSchoolSystemID());
							sys_weather_related = form.hasValueIgnoreCase("chk-ss-weather-related-" + sys.getSchoolSystemID(), "ON");
							sys_rationale = form.get("txt-ss-rationale-" + sys.getSchoolSystemID());
							try {
								sys_date = sdf.parse((form.get("txt-ss-date-" + sys.getSchoolSystemID())));
							}
							catch (ParseException dpe) {
								sys_date = null;
							}
							sys_repeat_to_date = form.hasValueIgnoreCase("chk-ss-repeattodate-" + sys.getSchoolSystemID(), "ON");
						}

						for (School school : sys.getSchoolSystemSchools()) {

							int school_stat = 0;
							String school_note = null;
							Date school_date = null;
							boolean school_weather_related = false;
							boolean school_repeat_to_date = false;
							String school_rationale = null;

							if (form.exists("school-id-" + school.getSchoolID())) {
								school_stat = form.getInt("lst-school-status-" + school.getSchoolID());
								school_note = form.get("txt-school-note-" + school.getSchoolID());
								school_weather_related = form.hasValueIgnoreCase("chk-school-weather-related-" + school.getSchoolID(),
										"ON");
								school_rationale = form.get("txt-school-rationale-" + school.getSchoolID());
								try {
									school_date = sdf.parse((form.get("txt-school-date-" + school.getSchoolID())));
								}
								catch (ParseException dpe) {
									school_date = null;
								}
								school_repeat_to_date = form.hasValueIgnoreCase("chk-school-repeattodate-" + school.getSchoolID(),
										"ON");
							}

							int new_stat = (school_stat > 0) ? school_stat
									: (sys_stat > 0) ? sys_stat
											: (region_stat > 0) ? region_stat
													: (zone_stat > 0) ? zone_stat : (district_stat > 0) ? district_stat : 0;

							String new_note = !StringUtils.isEmpty(school_note) ? school_note
									: !StringUtils.isEmpty(sys_note) ? sys_note
											: !StringUtils.isEmpty(region_note) ? region_note
													: !StringUtils.isEmpty(zone_note) ? zone_note
															: !StringUtils.isEmpty(district_note) ? district_note : null;

							String new_rationale = !StringUtils.isEmpty(school_rationale) ? school_rationale
									: !StringUtils.isEmpty(sys_rationale) ? sys_rationale
											: !StringUtils.isEmpty(region_rationale) ? region_rationale
													: !StringUtils.isEmpty(zone_rationale) ? zone_rationale
															: !StringUtils.isEmpty(district_rationale) ? district_rationale : null;

							boolean new_weather_related = school_weather_related || sys_weather_related || region_weather_related
									|| zone_weather_related || district_weather_related;

							Date new_date = (school_date != null) ? school_date
									: (sys_date != null) ? sys_date
											: (region_date != null) ? region_date
													: (zone_date != null) ? zone_date : (district_date != null) ? district_date : now;

							boolean new_repeat_to_date = school_repeat_to_date || sys_repeat_to_date || region_repeat_to_date
									|| zone_repeat_to_date || district_repeat_to_date;

							if (!StringUtils.isEmpty(new_note) && (new_stat <= 0))
								new_stat = 9; // school open

							if (new_stat > 0) {
								// System.err.println("School Id: " + school.getSchoolID());
								// System.err.println("Status Id: " + new_stat);
								// System.err.println("Status Note: " + new_note);
								// System.err.println("Status Date: " + now);

								if (!new_repeat_to_date) {
									SchoolSystemDB.updateSchoolSystemSchoolStatus(school.getSchoolID(), new_stat, new_note, new_date,
											new_weather_related, new_rationale);
								}
								else {
									Calendar cal = Calendar.getInstance();
									cal.setTime(new Date());
									cal.clear(Calendar.HOUR);
									cal.clear(Calendar.MINUTE);
									cal.clear(Calendar.SECOND);
									cal.clear(Calendar.MILLISECOND);

									while (!cal.getTime().after(new_date)) {
										SchoolSystemDB.updateSchoolSystemSchoolStatus(school.getSchoolID(), new_stat, new_note,
												cal.getTime(), new_weather_related, new_rationale);

										cal.add(Calendar.DAY_OF_YEAR, 1);
									}
									SchoolSystemDB.updateSchoolSystemSchoolStatus(school.getSchoolID(), new_stat, new_note, new_date,
											new_weather_related, new_rationale);
								}
							}

						}

					}

				}

			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return "regionalized_school_admin.jsp";
	}
}