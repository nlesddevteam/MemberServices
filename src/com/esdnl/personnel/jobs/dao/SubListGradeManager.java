package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.school.Grade;
import com.awsd.school.GradeDB;
import com.awsd.school.GradeException;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.SubListBean;

public class SubListGradeManager {

	public static Vector getSubListGradeBeansCollection(SubListBean abean) throws JobOpportunityException {

		Vector v_opps = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_sub_list_grades(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, abean.getId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				v_opps.add(GradeDB.getGrade(rs.getInt("GRADE_ID")));
		}
		catch (SQLException e) {
			System.err.println("Vector getSubListGradeBeansCollection(SubListBean abean): " + e);
			throw new JobOpportunityException("Can not extract SubListBean Grades from DB.", e);
		}
		catch (GradeException e) {
			System.err.println("Vector getSubListGradeBeansCollection(SubListBean abean): " + e);
			throw new JobOpportunityException("Can not extract SubListBean Grades from DB.", e);
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

		return v_opps;
	}

	public static Grade[] getSubListGradeBeans(SubListBean abean) throws JobOpportunityException {

		return (Grade[]) (SubListGradeManager.getSubListGradeBeansCollection(abean)).toArray(new Grade[0]);
	}
}