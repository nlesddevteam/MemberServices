package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.school.GradeDB;
import com.awsd.school.GradeException;
import com.awsd.school.SubjectDB;
import com.awsd.school.SubjectException;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.GradeSubjectPercentUnitBean;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.TeacherRecommendationBean;

public class ReccommentationGSUManager {

	public static GradeSubjectPercentUnitBean[] getGradeSubjectPercentUnitBeans(TeacherRecommendationBean rec)
			throws JobOpportunityException {

		Vector v_opps = null;
		GradeSubjectPercentUnitBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_rec_gsus(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, rec.getRecommendationId());
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createGSUBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("GradeSubjectPercentUnitBean[] getReferenceCheckRequestBeans(TeacherRecommendationBean rec): "
					+ e);
			throw new JobOpportunityException("Can not extract ReferenceCheckRequestBean[] from DB.", e);
		}
		finally {
			try {
				rs.close();
			}
			catch (Exception e) {}
			try {
				stat.close();
			}
			catch (Exception e) {}
			try {
				con.close();
			}
			catch (Exception e) {}
		}

		return (GradeSubjectPercentUnitBean[]) v_opps.toArray(new GradeSubjectPercentUnitBean[0]);
	}

	public static void addGradeSubjectPercentUnitBean(TeacherRecommendationBean rec, GradeSubjectPercentUnitBean[] abean)
			throws JobOpportunityException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.personnel_jobs_pkg.add_rec_gsu(?,?,?,?); end;");

			for (int i = 0; i < abean.length; i++) {
				stat.clearParameters();

				stat.setInt(1, rec.getRecommendationId());
				stat.setInt(2, abean[i].getGrade().getGradeID());
				if (abean[i].getSubject() != null)
					stat.setInt(3, abean[i].getSubject().getSubjectID());
				else
					stat.setNull(3, OracleTypes.NUMBER);
				stat.setDouble(4, abean[i].getUnitPercentage());

				stat.execute();
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("GradeSubjectPercentUnitBean addGradeSubjectPercentUnitBean(GradeSubjectPercentUnitBean abean): "
					+ e);
			throw new JobOpportunityException("Can not add ReferenceCheckRequestBean to DB.", e);
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

	public static GradeSubjectPercentUnitBean createGSUBean(ResultSet rs) {

		GradeSubjectPercentUnitBean abean = null;

		try {
			abean = new GradeSubjectPercentUnitBean();

			abean.setRecommendationId(rs.getInt("RECOMMENDATION_ID"));

			try {
				abean.setGrade(GradeDB.getGrade(rs.getInt("GRADE_ID")));
			}
			catch (GradeException e) {
				abean.setGrade(null);
			}

			try {
				if (rs.getInt("SUBJECT_ID") >= 0)
					abean.setSubject(SubjectDB.getSubject(rs.getInt("SUBJECT_ID")));
				else
					abean.setSubject(null);
			}
			catch (SubjectException e) {
				abean.setSubject(null);
			}

			abean.setUnitPercentage(rs.getDouble("UNIT_PERCENT"));
		}
		catch (SQLException e) {
			abean = null;
		}
		return abean;
	}

}
