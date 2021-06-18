package com.nlesd.school.site.handler.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.awsd.school.bean.RegionException;
import com.awsd.school.dao.RegionManager;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.nlesd.school.bean.SchoolDirectoryDetailsBean;
import com.nlesd.school.bean.SchoolDirectoryDetailsOtherBean;
import com.nlesd.school.bean.SchoolStreamDetailsBean;
import com.nlesd.school.bean.SchoolStreamSchoolsBean;
import com.nlesd.school.service.SchoolDirectoryDetailsOtherService;
import com.nlesd.school.service.SchoolDirectoryDetailsService;
import com.nlesd.school.service.SchoolStreamDetailsService;
import com.nlesd.school.service.SchoolStreamSchoolsService;
import com.nlesd.school.service.SchoolZoneService;

public class MSUpdateSchoolRequestHandler extends RequestHandlerImpl {

	private String nlesd_schools_rootbasepath = "/../../nlesdweb/WebContent/schools/";

	public MSUpdateSchoolRequestHandler() {

		//********* EDITED BY GEOFF TO ALLOW BUS STAFF TO UPDATE SCHOOL ROUTE DOCS IN SCHOOL PROFILES ONLY ******
		this.requiredPermissions = new String[] {
				"MEMBERADMIN-VIEW", "WEBMAINTENANCE-BUSROUTES", "WEBMAINTENANCE-SCHOOLPROFILE-PRINCIPAL",
				"WEBMAINTENANCE-SCHOOLPROFILE-SECRETARY", "WEBMAINTENANCE-SCHOOLPROFILE-ADMIN"
		};

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("schoolId"), new RequiredFormElement("schoolName"), new RequiredFormElement("deptId"),
				new RequiredFormElement("zoneId")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {

			School school = null;

			int schoolId = form.getInt("schoolId");

			if (schoolId > 0) {
				school = SchoolDB.getSchoolFullDetails(schoolId);
			}
			else {
				school = new School();
			}

			school.setSchoolName(form.get("schoolName"));
			school.setSchoolDeptID(form.getInt("deptId"));
			school.setZone(SchoolZoneService.getSchoolZoneBean(form.getInt("zoneId")));

			if (form.exists("regionId")) {
				try {
					school.setRegion(RegionManager.getRegionBean(form.getInt("regionId")));
				}
				catch (RegionException e) {
					school.setRegion(null);
				}
			}
			else {
				school.setRegion(null);
			}

			school.setAddress1(form.get("address1"));
			school.setAddress2(form.get("address2"));
			school.setTownCity(form.get("citytown"));
			school.setProvinceState(form.get("provinceState"));
			school.setPostalZipCode(form.get("postalcode"));
			school.setTelephone(form.get("telephone"));
			school.setFax(form.get("fax"));
			school.setWebsite(form.get("website"));
			school.setLowestGrade(School.GRADE.get(form.getInt("lgrade")));
			school.setHighestGrade(School.GRADE.get(form.getInt("hgrade")));

			if (school.getSchoolID() > 0) {
				SchoolDB.updateSchool(school);
			}
			else {
				SchoolDB.addSchool(school);
			}

			//update directory details

			SchoolDirectoryDetailsBean details = school.getDetails();
			if (details == null) {
				details = new SchoolDirectoryDetailsBean();
				details.setSchoolId(school.getSchoolID());
			}

			details.setElectorialZone(form.getInt("electorialZone"));
			details.setAccessible(form.getBoolean("accessible"));
			details.setFrenchImmersion(form.getBoolean("frenchImmersion"));

			if (form.uploadFileExists("schoolPhotoFile")) {
				try {
					details.setSchoolPhotoFilename(save_file("schoolPhotoFile", nlesd_schools_rootbasepath + "img/"));
				}
				catch (Exception e) {
					details.setSchoolPhotoFilename(null);
				}
			}

			if (form.uploadFileExists("schoolCrestFile")) {
				try {
					details.setSchoolCrestFilename(save_file("schoolCrestFile", nlesd_schools_rootbasepath + "img/"));
				}
				catch (Exception e) {
					details.setSchoolCrestFilename(null);
				}
			}

			if (form.uploadFileExists("busRoutesFile")) {
				try {
					details.setBusRoutesFilename(save_file("busRoutesFile", nlesd_schools_rootbasepath + "doc/"));
				}
				catch (Exception e) {
					details.setBusRoutesFilename(null);
				}
			}

			if (form.uploadFileExists("catchmentAreaFile")) {
				try {
					details.setCatchmentAreaFilename(save_file("catchmentAreaFile", nlesd_schools_rootbasepath + "doc/"));
				}
				catch (Exception e) {
					details.setCatchmentAreaFilename(null);
				}
			}

			if (form.uploadFileExists("schoolReportFile")) {
				try {
					details.setSchoolReportFilename(save_file("schoolReportFile", nlesd_schools_rootbasepath + "doc/"));
				}
				catch (Exception e) {
					details.setSchoolReportFilename(null);
				}
			}

			details.setYoutubeUrl(form.get("youtube"));
			details.setTwitterUrl(form.get("twitter"));
			details.setFacebookUrl(form.get("facebook"));
			details.setGoogleMapUrl(form.get("googleMapUrl"));
			details.setCatchmentMapUrl(form.get("catchmentMapUrl"));

			details.setSchoolStartTime(form.get("starttime"));
			details.setSchoolEndTime(form.get("endtime"));
			details.setSchoolOpening(form.get("schoolOpening"));
			details.setKindergartenTimes(form.get("kindergartenTimes"));
			details.setKinderstartTimes(form.get("kinderstartTimes"));
			details.setOtherInfo(form.get("otherInfo"));
			details.setSecretaries(form.get("secretary"));

			if (details.getDirectoryId() > 0) {
				SchoolDirectoryDetailsService.updateSchoolDirectoryDetailsBean(details);
			}
			else {
				SchoolDirectoryDetailsService.addSchoolDirectoryDetailsBean(details);

				school.setDetails(details);
			}

			//update directory details Other

			SchoolDirectoryDetailsOtherBean detailsOther = school.getDetailsOther();

			if (detailsOther == null) {
				detailsOther = new SchoolDirectoryDetailsOtherBean();
				detailsOther.setSchoolDirectory(school.getSchoolID());
			}
			detailsOther.setGoogleMapEmbed(form.get("googlemapembed").toString());
			detailsOther.setSchoolCatchmentEmbed(form.get("schoolcatchmentembed").toString());
			detailsOther.setDescription(form.get("description").toString());
			detailsOther.setInstagramLink(form.get("instagramlink").toString());
			detailsOther.setSchoolEmail(form.get("schoolemail").toString());
			detailsOther.setSchoolGuidanceSupport(form.get("schoolguidancesupport").toString());
			detailsOther.setTwitterFeedWidgetId(form.get("twitterfeedwidgetid").toString());
			detailsOther.setTwitterFeedScreenName(form.get("twitterfeedscreenname").toString());
			detailsOther.setImportantNotice(form.get("importantnotice").toString());
			detailsOther.setSchoolEnrollment(form.get("schoolenrollment").toString());
			detailsOther.setAddedBy(usr.getPersonnel().getFullNameReverse());
			detailsOther.setTwitterEmbed(form.get("twitterembed").toString());
			detailsOther.setSurveillanceCamera(form.getBoolean("surcameras"));

			if (detailsOther.getId() > 0) {
				SchoolDirectoryDetailsOtherService.updateSchoolDirectoryDetailsOtherBean(detailsOther);
			}
			else {
				SchoolDirectoryDetailsOtherService.addSchoolDirectoryDetailsOtherBean(detailsOther);

				school.setDetailsOther(detailsOther);
			}
			//update School Stream Details info
			SchoolStreamDetailsBean ssdb = SchoolStreamDetailsService.getSchoolStreamDetailsBean(school.getSchoolID());
			if (ssdb == null) {
				ssdb = new SchoolStreamDetailsBean();
				ssdb.setSchoolId(school.getSchoolID());
			}
			ssdb.setStreamNotes(form.get("streamnotes").toString());
			ssdb.setAddedBy(usr.getPersonnel().getFullNameReverse());
			if (ssdb.getId() > 0) {
				SchoolStreamDetailsService.updateSchoolStreamDetails(ssdb);
			}
			else {
				Integer id = SchoolStreamDetailsService.addSchoolStreamDetails(ssdb);
				ssdb.setId(id);
				school.setSchoolStreams(ssdb);
			}
			//remove old stream schools
			SchoolStreamSchoolsService.deleteSchoolStreamDetails(ssdb.getId());
			List<String> streamsenglish = new ArrayList<String>();
			if (form.exists("schoolstreamsenglish")) {
				streamsenglish = Arrays.asList(form.getArray("schoolstreamsenglish"));
				for (String t : streamsenglish) {
					//get info
					SchoolStreamSchoolsBean sssb = new SchoolStreamSchoolsBean();
					sssb.setSchoolId(Integer.parseInt(t));
					sssb.setStreamType(1);
					sssb.setStreamId(ssdb.getId());
					//add
					SchoolStreamSchoolsService.addSchoolStreamSchools(sssb);
				}
			}
			List<String> streamsfrench = new ArrayList<String>();
			if (form.exists("schoolstreamsfrench")) {
				streamsfrench = Arrays.asList(form.getArray("schoolstreamsfrench"));
				for (String t : streamsfrench) {
					//get info
					SchoolStreamSchoolsBean sssb = new SchoolStreamSchoolsBean();
					sssb.setSchoolId(Integer.parseInt(t));
					sssb.setStreamType(2);
					sssb.setStreamId(ssdb.getId());
					//add
					SchoolStreamSchoolsService.addSchoolStreamSchools(sssb);
				}
			}

			school.getSchoolStreams().setSchoolStreamsEnglish(
					SchoolStreamSchoolsService.getSchoolStreamSchoolsEnglishBean(ssdb.getId()));
			school.getSchoolStreams().setSchoolStreamsFrench(
					SchoolStreamSchoolsService.getSchoolStreamSchoolsFrenchBean(ssdb.getId()));
			request.setAttribute("school", school);
			request.setAttribute("msgOK", "School updated successfully.");

			//Redirect to the proper area updating is for. System Admin, Principals, or Busing.

			//********* ADDED BY GEOFF TO ALLOW BUS STAFF TO UPDATE SCHOOL ROUTE DOCS IN SCHOOL PROFILES ONLY AND REDIRECT ACCORDINGLY ******

			if ((usr.checkRole("BUSROUTE-POST"))) {
				request.setAttribute("msgOK", "School Bus Route Document updated successfully.");
				path = "/BusRoutes/school_directory_bus_routes.jsp";

			}
			else if ((usr.checkPermission("WEBMAINTENANCE-SCHOOLPROFILE-PRINCIPAL"))
					|| (usr.checkPermission("WEBMAINTENANCE-SCHOOLPROFILE-SECRETARY"))) {
				request.setAttribute("msgOK", "Your School profile has been updated successfully.");
				path = "/SchoolDirectory/school_profile.jsp";

			}
			else if ((usr.checkPermission("WEBMAINTENANCE-SCHOOLPROFILE-ADMIN"))
					&& (!usr.checkPermission("WEBMAINTENANCE-SCHOOLPROFILE-PRINCIPAL"))) {
				request.setAttribute("msgOK", "School profile has been updated successfully.");
				path = "/SchoolDirectory/school_profile.jsp";
			}

			else {
				request.setAttribute("msgOK", "School updated successfully.");
				path = "school_profile.jsp";
			}

		}
		else {
			path = "school_profile.jsp";

			request.setAttribute("msgERR", "School ID required for editing process.");
		}

		return path;
	}

}
