package com.esdnl.scrs.site.handler;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.esdnl.school.bean.StudentRecordBean;
import com.esdnl.school.database.StudentRecordManager;
import com.esdnl.scrs.domain.ActionType;
import com.esdnl.scrs.domain.BullyingException;
import com.esdnl.scrs.domain.BullyingReasonType;
import com.esdnl.scrs.domain.IllegalSubstanceType;
import com.esdnl.scrs.domain.IncidentBean;
import com.esdnl.scrs.domain.LocationType;
import com.esdnl.scrs.domain.SchoolSafetyIssueType;
import com.esdnl.scrs.domain.SexualBehaviourType;
import com.esdnl.scrs.domain.TargetType;
import com.esdnl.scrs.domain.ThreateningBehaviorType;
import com.esdnl.scrs.domain.TimeType;
import com.esdnl.scrs.service.ActionTypeService;
import com.esdnl.scrs.service.BullyingBehaviorTypeService;
import com.esdnl.scrs.service.BullyingReasonTypeService;
import com.esdnl.scrs.service.IllegalSubstanceTypeService;
import com.esdnl.scrs.service.IncidentService;
import com.esdnl.scrs.service.LocationTypeService;
import com.esdnl.scrs.service.SchoolSafetyIssueTypeService;
import com.esdnl.scrs.service.SexualBehaviourTypeService;
import com.esdnl.scrs.service.TargetTypeService;
import com.esdnl.scrs.service.ThreateningBehaviorTypeService;
import com.esdnl.scrs.service.TimeTypeService;
import com.esdnl.servlet.FormElement;
import com.esdnl.servlet.FormElementFormat;
import com.esdnl.servlet.FormElementPattern;
import com.esdnl.servlet.FormValidator;
import com.esdnl.servlet.RequestHandlerImpl;
import com.esdnl.servlet.RequiredFormElement;
import com.esdnl.servlet.RequiredPatternFormElement;
import com.esdnl.servlet.RequiredSelectionFormElement;

public class AddIncidentReportRequestHandler extends RequestHandlerImpl {

	public AddIncidentReportRequestHandler() {

		this.requiredPermissions = new String[] {
				"BULLYING-ANALYSIS-SCHOOL-VIEW", "BULLYING-ANALYSIS-ADMIN-VIEW"
		};
	}

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException,
				IOException {

		super.handleRequest(request, response);

		this.validator = new FormValidator(new FormElement[] {
				new RequiredFormElement("incidentDate", "Incident date is required."),
				new RequiredPatternFormElement("incidentDate", FormElementPattern.DATE_PATTERN, "Incident date invalid format."),
				new RequiredFormElement("studentId", "Student ID required."),
				new RequiredFormElement("studentFirstName", "Student first name required."),
				new RequiredFormElement("studentLastName", "Student last name required."),
				new RequiredFormElement("studentGenderId", "Student gender selection required."),
				new RequiredSelectionFormElement("schoolId", -1, "Student current school selection required."),
				new RequiredSelectionFormElement("gradeId", 0, "Student grade selection required."),
				new RequiredSelectionFormElement("studentAge", -1, "Student age selection required."),
				new RequiredFormElement("submittedById"),

				new RequiredFormElement("lst_Location", "Incident location selection required."),
				new RequiredFormElement("lst_Time", "Incident time selection required."),
				new RequiredFormElement("lst_Target", "Incident target selection required."),
				new RequiredFormElement("lst_Action", "Incident action(s) taken selection required.")
		});

		try {
			if (validate_form()) {
				IncidentBean incident = new IncidentBean();

				incident.setIncidentDate(form.getDate("incidentDate"));

				//check if student exists;

				StudentRecordBean student = StudentRecordManager.getStudentRecordBean(form.get("studentId"));
				if (student == null) {
					student = new StudentRecordBean();
					student.setStudentId(form.get("studentId"));
					student.setFirstName(form.get("studentFirstName"));
					student.setLastName(form.get("studentLastName"));
					student.setMiddleName(form.get("studentMiddleName"));
					student.setGender(StudentRecordBean.GENDER.get(form.getInt("studentGenderId")));

					StudentRecordManager.addStudentRecordBean(student);
				}

				incident.setStudent(student);

				School school = SchoolDB.getSchool(form.getInt("schoolId"));
				if (school == null)
					throw new BullyingException("School ID [" + form.get("schoolId") + "] does not exist");

				incident.setSchool(school);
				incident.setStudentGrade(School.GRADE.get(form.getInt("gradeId")));
				incident.setStudentAge(form.getInt("studentAge"));

				incident.setSubmittedBy(usr.getPersonnel());

				if (form.exists("lst_BullingBehavior")) {
					for (int typeId : form.getIntArray("lst_BullingBehavior"))
						incident.getBullyingBehaviorTypes().add(BullyingBehaviorTypeService.getBullyingBehaviorType(typeId));
				}

				if (form.exists("lst_BullyingReason")) {
					for (int typeId : form.getIntArray("lst_BullyingReason")) {
						BullyingReasonType type = BullyingReasonTypeService.getBullyingReasonType(typeId);

						if (type.isIsSpecified())
							type.setSpecified(form.get("lst_BullyingReason_" + type.getTypeId() + "_specified"));

						incident.getBullyingReasonTypes().add(type);
					}
				}

				if (form.exists("lst_IllegalSubstance")) {
					for (int typeId : form.getIntArray("lst_IllegalSubstance")) {
						IllegalSubstanceType type = IllegalSubstanceTypeService.getIllegalSubstanceType(typeId);

						if (type.isIsSpecified())
							type.setSpecified(form.get("lst_IllegalSubstance_" + type.getTypeId() + "_specified"));

						incident.getIllegalSubstanceTypes().add(type);
					}
				}

				if (form.exists("lst_SexualBehaviour")) {
					for (int typeId : form.getIntArray("lst_SexualBehaviour")) {
						SexualBehaviourType type = SexualBehaviourTypeService.getSexualBehaviourType(typeId);

						if (type.isIsSpecified())
							type.setSpecified(form.get("lst_SexualBehaviour_" + type.getTypeId() + "_specified"));

						incident.getSexualBehaviourTypes().add(type);
					}
				}

				if (form.exists("lst_ThreateningBehavior")) {
					for (int typeId : form.getIntArray("lst_ThreateningBehavior")) {
						ThreateningBehaviorType type = ThreateningBehaviorTypeService.getThreateningBehaviorType(typeId);

						if (type.isIsSpecified())
							type.setSpecified(form.get("lst_ThreateningBehavior_" + type.getTypeId() + "_specified"));

						incident.getThreateningBehaviorTypes().add(type);
					}
				}

				if (form.exists("lst_SchoolSafetyIssue")) {
					for (int typeId : form.getIntArray("lst_SchoolSafetyIssue")) {
						SchoolSafetyIssueType type = SchoolSafetyIssueTypeService.getSchoolSafetyIssueType(typeId);

						if (type.isIsSpecified())
							type.setSpecified(form.get("lst_SchoolSafetyIssue_" + type.getTypeId() + "_specified"));

						incident.getSchoolSafetyIssueTypes().add(type);
					}
				}

				if (form.exists("lst_Location")) {
					for (int typeId : form.getIntArray("lst_Location")) {
						LocationType type = LocationTypeService.getLocationType(typeId);

						if (type.isIsSpecified())
							type.setSpecified(form.get("lst_Location_" + type.getTypeId() + "_specified"));

						incident.getLocationTypes().add(type);
					}
				}

				if (form.exists("lst_Time")) {
					for (int typeId : form.getIntArray("lst_Time")) {
						TimeType type = TimeTypeService.getTimeType(typeId);

						if (type.isIsSpecified())
							type.setSpecified(form.get("lst_Time_" + type.getTypeId() + "_specified"));

						incident.getTimeTypes().add(type);
					}
				}

				if (form.exists("lst_Target")) {
					for (int typeId : form.getIntArray("lst_Target")) {
						TargetType type = TargetTypeService.getTargetType(typeId);

						if (type.isIsSpecified())
							type.setSpecified(form.get("lst_Target_" + type.getTypeId() + "_specified"));

						incident.getTargetTypes().add(type);
					}
				}

				if (form.exists("lst_Action")) {
					for (int typeId : form.getIntArray("lst_Action")) {
						ActionType type = ActionTypeService.getActionType(typeId);

						if (type.isIsSpecified())
							type.setSpecified(form.get("lst_Action_" + type.getTypeId() + "_specified"));

						incident.getActionTypes().add(type);
					}
				}

				IncidentService.addIncidentBean(incident);

				request.setAttribute("msg", "Incident Report added successfully.");
			}
			else {
				request.setAttribute("msg", validator.getErrorString());

				IncidentBean incident = new IncidentBean();

				if (form.exists("incidentDate")) {
					try {
						incident.setIncidentDate((new SimpleDateFormat(FormElementFormat.DATE_FORMAT)).parse(form.get("incidentDate")));
					}
					catch (ParseException e) {
						incident.setIncidentDate(null);
					}
				}

				if (form.exists("studentId")) {
					StudentRecordBean student = StudentRecordManager.getStudentRecordBean(form.get("studentId"));
					if (student == null) {
						student = new StudentRecordBean();
						student.setStudentId(form.get("studentId"));
						student.setFirstName(form.get("studentFirstName"));
						student.setLastName(form.get("studentLastName"));
						student.setMiddleName(form.get("studentMiddleName"));
						student.setGender(StudentRecordBean.GENDER.get(form.getInt("studentGenderId")));
					}
					incident.setStudent(student);
				}

				if (form.exists("schoolId")) {
					School school = SchoolDB.getSchool(form.getInt("schoolId"));
					if (school == null)
						throw new BullyingException("School ID [" + form.get("schoolId") + "] does not exist");

					incident.setSchool(school);
				}

				if (form.exists("gradeId"))
					incident.setStudentGrade(School.GRADE.get(form.getInt("gradeId")));

				if (form.exists("studentAge"))
					incident.setStudentAge(form.getInt("studentAge"));

				if (form.exists("lst_BullingBehavior")) {
					for (int typeId : form.getIntArray("lst_BullingBehavior"))
						incident.getBullyingBehaviorTypes().add(BullyingBehaviorTypeService.getBullyingBehaviorType(typeId));
				}

				if (form.exists("lst_BullyingReason")) {
					for (int typeId : form.getIntArray("lst_BullyingReason")) {
						BullyingReasonType type = BullyingReasonTypeService.getBullyingReasonType(typeId);

						if (type.isIsSpecified())
							type.setSpecified(form.get("lst_BullyingReason_" + type.getTypeId() + "_specified"));

						incident.getBullyingReasonTypes().add(type);
					}
				}

				if (form.exists("lst_IllegalSubstance")) {
					for (int typeId : form.getIntArray("lst_IllegalSubstance")) {
						IllegalSubstanceType type = IllegalSubstanceTypeService.getIllegalSubstanceType(typeId);

						if (type.isIsSpecified())
							type.setSpecified(form.get("lst_IllegalSubstance_" + type.getTypeId() + "_specified"));

						incident.getIllegalSubstanceTypes().add(type);
					}
				}

				if (form.exists("lst_ThreateningBehavior")) {
					for (int typeId : form.getIntArray("lst_ThreateningBehavior")) {
						ThreateningBehaviorType type = ThreateningBehaviorTypeService.getThreateningBehaviorType(typeId);

						if (type.isIsSpecified())
							type.setSpecified(form.get("lst_ThreateningBehavior_" + type.getTypeId() + "_specified"));

						incident.getThreateningBehaviorTypes().add(type);
					}
				}

				if (form.exists("lst_SexualBehaviour")) {
					for (int typeId : form.getIntArray("lst_SexualBehaviour")) {
						SexualBehaviourType type = SexualBehaviourTypeService.getSexualBehaviourType(typeId);

						if (type.isIsSpecified())
							type.setSpecified(form.get("lst_SexualBehaviour_" + type.getTypeId() + "_specified"));

						incident.getSexualBehaviourTypes().add(type);
					}
				}

				if (form.exists("lst_SchoolSafetyIssue")) {
					for (int typeId : form.getIntArray("lst_SchoolSafetyIssue")) {
						SchoolSafetyIssueType type = SchoolSafetyIssueTypeService.getSchoolSafetyIssueType(typeId);

						if (type.isIsSpecified())
							type.setSpecified(form.get("lst_SchoolSafetyIssue_" + type.getTypeId() + "_specified"));

						incident.getSchoolSafetyIssueTypes().add(type);
					}
				}

				if (form.exists("lst_Location")) {
					for (int typeId : form.getIntArray("lst_Location")) {
						LocationType type = LocationTypeService.getLocationType(typeId);

						if (type.isIsSpecified())
							type.setSpecified(form.get("lst_Location_" + type.getTypeId() + "_specified"));

						incident.getLocationTypes().add(type);
					}
				}

				if (form.exists("lst_Time")) {
					for (int typeId : form.getIntArray("lst_Time")) {
						TimeType type = TimeTypeService.getTimeType(typeId);

						if (type.isIsSpecified())
							type.setSpecified(form.get("lst_Time_" + type.getTypeId() + "_specified"));

						incident.getTimeTypes().add(type);
					}
				}

				if (form.exists("lst_Target")) {
					for (int typeId : form.getIntArray("lst_Target")) {
						TargetType type = TargetTypeService.getTargetType(typeId);

						if (type.isIsSpecified())
							type.setSpecified(form.get("lst_Target_" + type.getTypeId() + "_specified"));

						incident.getTargetTypes().add(type);
					}
				}

				if (form.exists("lst_Action")) {
					for (int typeId : form.getIntArray("lst_Action")) {
						ActionType type = ActionTypeService.getActionType(typeId);

						if (type.isIsSpecified())
							type.setSpecified(form.get("lst_Action_" + type.getTypeId() + "_specified"));

						incident.getActionTypes().add(type);
					}
				}

				request.setAttribute("incident", incident);
			}

			path = "add_incident.jsp";
		}
		catch (BullyingException e) {
			e.printStackTrace();
		}

		return path;
	}

}
