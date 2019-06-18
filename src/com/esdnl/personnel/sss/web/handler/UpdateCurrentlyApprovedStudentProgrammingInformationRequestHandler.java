package com.esdnl.personnel.sss.web.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.School;
import com.esdnl.personnel.sss.dao.SSSProfileSchoolInfoManager;
import com.esdnl.personnel.sss.dao.StudentProgrammingInfoManager;
import com.esdnl.personnel.sss.domain.bean.SSSException;
import com.esdnl.personnel.sss.domain.bean.SSSProfileSchoolInfoBean;
import com.esdnl.personnel.sss.domain.bean.StudentProgrammingInfoBean;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;

public class UpdateCurrentlyApprovedStudentProgrammingInformationRequestHandler extends RequestHandlerImpl {

	public UpdateCurrentlyApprovedStudentProgrammingInformationRequestHandler() {

		this.requiredPermissions = new String[] {
				"PERSONNEL-SSS-PROFILE-VIEW", "PERSONNEL-SSS-PROFILE-ADMIN"
		};

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("profile_id"), new RequiredFormElement("student_id"),
				new RequiredFormElement("student_name"), new RequiredFormElement("student_exceptionality_id"),
				new RequiredFormElement("student_grade_id"), new RequiredFormElement("student_pervasive_category_id"),
				new RequiredFormElement("student_stream"), new RequiredFormElement("pathway_1_courses"),
				new RequiredFormElement("pathway_2_courses"), new RequiredFormElement("pathway_3_courses"),
				new RequiredFormElement("pathway_4_cc_courses"), new RequiredFormElement("pathway_4_ncc_courses"),
				new RequiredFormElement("pathway_4_pp_courses"), new RequiredFormElement("pathway_4_ncp_courses")
		});
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		if (validate_form()) {
			SSSProfileSchoolInfoBean profile;

			try {
				profile = SSSProfileSchoolInfoManager.getSSSProfileSchoolInfoBean(form.getInt("profile_id"));

				StudentProgrammingInfoBean info = StudentProgrammingInfoManager.getStudentProgrammingInfoBean(
						form.get("student_id"), profile.getProjectedSchoolYear());

				info.setStudentName(form.get("student_name"));
				info.setExceptionality(form.get("student_exceptionality_id"));
				info.setGrade(School.GRADE.get(form.getInt("student_grade_id")));
				info.setPervasiveCategory(form.get("student_pervasive_category_id"));
				info.setStream(form.get("student_stream"));
				info.setIssp(form.exists("student_issp"));
				info.setIep(form.exists("student_iep"));
				info.setP1Courses(form.getInt("pathway_1_courses"));
				info.setP2Courses(form.getInt("pathway_2_courses"));
				info.setP3Courses(form.getInt("pathway_3_courses"));
				info.setP4CC(form.getInt("pathway_4_cc_courses"));
				info.setP4NCC(form.getInt("pathway_4_ncc_courses"));
				info.setP4PP(form.getInt("pathway_4_pp_courses"));
				info.setP4NCP(form.getInt("pathway_4_ncp_courses"));
				info.setP5(form.exists("pathway_5"));
				info.setStudentAssistantSupport(form.exists("student_approved"));

				if (info.isDirty())
					StudentProgrammingInfoManager.updateStudentProgrammingInfoBean(info);

				request.setAttribute("msg", info.getStudentName() + " programming information updated successfully.");
				request.setAttribute("schoolprofile", profile);
				request.setAttribute("proginfos", StudentProgrammingInfoManager.getStudentProgrammingInfoBeans(profile));
			}
			catch (SSSException e) {
				e.printStackTrace();
			}
		}
		else {
			request.setAttribute("msg", validator.getErrorString());
			request.setAttribute("INVALID_FORM", form);
			try {
				request.setAttribute("schoolprofile",
						SSSProfileSchoolInfoManager.getSSSProfileSchoolInfoBean(form.getInt("profile_id")));
			}
			catch (SSSException e) {
				e.printStackTrace();
			}
		}

		return "schoolsummary_table3.jsp";
	}
}
