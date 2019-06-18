package com.esdnl.personnel.jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.school.Subject;
import com.awsd.school.SubjectDB;
import com.awsd.school.SubjectException;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.JobOpportunityException;
import com.esdnl.personnel.jobs.bean.SubListBean;

public class SubListSubjectManager {

	public static Vector getSubListSubjectBeansCollection(SubListBean abean) throws JobOpportunityException {

		Vector v_opps = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.personnel_jobs_pkg.get_sub_list_subjects(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, abean.getId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				v_opps.add(SubjectDB.getSubject(rs.getInt("SUBJECT_ID")));
		}
		catch (SQLException e) {
			System.err.println("Vector getSubListSubjectBeansCollection(SubListBean abean): " + e);
			throw new JobOpportunityException("Can not extract SubListBean Subjects from DB.", e);
		}
		catch (SubjectException e) {
			System.err.println("Vector getSubListSubjectBeansCollection(SubListBean abean): " + e);
			throw new JobOpportunityException("Can not extract SubListBean Subjects from DB.", e);
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

	public static Subject[] getSubListSubjectBeans(SubListBean abean) throws JobOpportunityException {

		return (Subject[]) (SubListSubjectManager.getSubListSubjectBeansCollection(abean)).toArray(new Subject[0]);
	}
}