package com.esdnl.scrs.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.personnel.PersonnelDB;
import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.esdnl.dao.DAOUtils;
import com.esdnl.school.database.StudentRecordManager;
import com.esdnl.scrs.domain.BullyingException;
import com.esdnl.scrs.domain.IncidentBean;

public class IncidentService {

	public static IncidentBean addIncidentBean(IncidentBean abean) throws BullyingException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.add_incident(?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, abean.getSubmittedBy().getPersonnelID());
			stat.setInt(3, abean.getSchool().getSchoolID());
			stat.setString(4, abean.getStudent().getStudentId());
			stat.setInt(5, abean.getStudentAge());
			stat.setInt(6, abean.getStudentGrade().getValue());
			stat.setDate(7, new java.sql.Date(abean.getIncidentDate().getTime()));

			stat.execute();

			int incident_id = ((OracleCallableStatement) stat).getInt(1);

			if (incident_id > 0) {
				abean.setIncidentId(incident_id);

				if (abean.getBullyingBehaviorTypes().size() > 0)
					IncidentBullyingBehaviorService.addBullyingIncidentBehaviors(abean);

				if (abean.getBullyingReasonTypes().size() > 0)
					IncidentBullyingReasonService.addBullyingIncidentReasons(abean);

				if (abean.getIllegalSubstanceTypes().size() > 0)
					IncidentIllegalSubstanceService.addIncidentIllegalSubstances(abean);

				if (abean.getSexualBehaviourTypes().size() > 0)
					IncidentSexualBehaviourService.addIncidentSexualBehaviours(abean);

				if (abean.getThreateningBehaviorTypes().size() > 0)
					IncidentThreateningBehaviorService.addIncidentThreateningBehaviors(abean);

				if (abean.getSchoolSafetyIssueTypes().size() > 0)
					IncidentSchoolSafetyIssueService.addIncidentSchoolSafetyIssues(abean);

				if (abean.getLocationTypes().size() > 0)
					IncidentLocationService.addIncidentLocations(abean);

				if (abean.getTimeTypes().size() > 0)
					IncidentTimeService.addIncidentTimes(abean);

				if (abean.getTargetTypes().size() > 0)
					IncidentTargetService.addIncidentTargets(abean);

				if (abean.getActionTypes().size() > 0)
					IncidentActionService.addIncidentActions(abean);
			}

			return abean;
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("BullyingIncidentBean addBullyingIncidentBean(BullyingIncidentBean abean): " + e);
			throw new BullyingException("Can not add BullyingIncidentBean to DB.", e);
		}
		finally {
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
	}

	public static IncidentBean getIncidentBean(int incidentId, boolean loadAssociatedData) throws BullyingException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		IncidentBean bean = null;

		try {

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_incident(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, incidentId);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				bean = createIncidentBean(rs, loadAssociatedData);

			return bean;
		}
		catch (SQLException e) {
			e.printStackTrace();

			System.err.println("BullyingIncidentBean getBullyingIncidentBean(int incidentId): " + e);
			throw new BullyingException("Can not fetch Incident BullyingIncidentBean to DB.", e);
		}
		finally {
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
	}

	public static ArrayList<IncidentBean> getIncidentBeans(School school, boolean loadAssociatedData)
			throws BullyingException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList<IncidentBean> beans = null;

		try {
			beans = new ArrayList<IncidentBean>();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.bullying_pkg.get_incidents_by_school(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, school.getSchoolID());

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(createIncidentBean(rs, loadAssociatedData));

			return beans;
		}
		catch (SQLException e) {
			e.printStackTrace();

			System.err.println("ArrayList<BullyingIncidentBean> getBullyingIncidentBeans(School school, boolean loadAssociatedData): "
					+ e);
			throw new BullyingException("Can not fetch Incident BullyingIncidentBean to DB.", e);
		}
		finally {
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}
	}

	public static IncidentBean createIncidentBean(ResultSet rs) {

		return createIncidentBean(rs, false);
	}

	public static IncidentBean createIncidentBean(ResultSet rs, boolean loadAssocatedData) {

		IncidentBean abean = null;

		try {
			abean = new IncidentBean();

			abean.setIncidentId(rs.getInt("INCIDENT_ID"));
			abean.setIncidentDate(new java.util.Date(rs.getDate("INCIDENT_DATE").getTime()));
			abean.setSchool(SchoolDB.createSchoolBean(rs));
			abean.setStudentAge(rs.getInt("STUDENT_AGE"));
			abean.setStudentGrade(School.GRADE.get(rs.getInt("STUDENT_GRADE_ID")));
			abean.setSubmittedBy(PersonnelDB.createPersonnelBean(rs));
			abean.setSubmittedDate(new java.util.Date(rs.getTimestamp("SUBMITTED_DATE").getTime()));
			abean.setStudent(StudentRecordManager.createStudentRecordBean(rs));

			if (loadAssocatedData) {
				loadIncidentBeanAssociatedData(abean);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			abean = null;
		}
		catch (Exception e) {
			e.printStackTrace();
			abean = null;
		}

		return abean;
	}

	public static void loadIncidentBeanAssociatedData(IncidentBean abean) {

		try {
			abean.setBullyingBehaviorTypes(IncidentBullyingBehaviorService.getBullyingIncidentBehaviors(abean));
			abean.setBullyingReasonTypes(IncidentBullyingReasonService.getBullyingIncidentReasons(abean));
			abean.setIllegalSubstanceTypes(IncidentIllegalSubstanceService.getIncidentIllegalSubstances(abean));
			abean.setSexualBehaviourTypes(IncidentSexualBehaviourService.getIncidentSexualBehaviours(abean));
			abean.setThreateningBehaviorTypes(IncidentThreateningBehaviorService.getIncidentThreateningBehaviors(abean));
			abean.setSchoolSafetyIssueTypes(IncidentSchoolSafetyIssueService.getIncidentSchoolSafetyIssues(abean));
			abean.setLocationTypes(IncidentLocationService.getIncidentLocations(abean));
			abean.setTimeTypes(IncidentTimeService.getIncidentTimes(abean));
			abean.setTargetTypes(IncidentTargetService.getIncidentTargets(abean));
			abean.setActionTypes(IncidentActionService.getIncidentActions(abean));
		}
		catch (BullyingException e) {
			e.printStackTrace();
		}
	}
}
