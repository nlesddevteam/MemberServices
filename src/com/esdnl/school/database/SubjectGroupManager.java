package com.esdnl.school.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

import com.awsd.school.SchoolException;
import com.awsd.school.Subject;
import com.awsd.school.SubjectDB;
import com.esdnl.dao.DAOUtils;
import com.esdnl.school.bean.SubjectGroupBean;

public class SubjectGroupManager {

	public static void addSubjectGroupBean(String groupName, Integer[] subjects) throws SchoolException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.schools_pkg.add_subject_group(?,?); end;");

			stat.setString(1, groupName);

			stat.setArray(2, new ARRAY(ArrayDescriptor.createDescriptor("NUM_ARRAY", con), con, subjects));

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addSubjectGroupBean(SubjectGroupBean abean, Integer[] subjects): " + e);
			throw new SchoolException("Can not add SubjectGroupBean to DB." + e);
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

	public static void updateSubjectGroupBean(SubjectGroupBean group) throws SchoolException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.schools_pkg.mod_subject_group(?,?,?); end;");

			stat.setInt(1, group.getGroupId());

			stat.setString(2, group.getGroupName());

			int i = 0;
			Integer[] subjectids = new Integer[group.getSubjects().size()];
			for (Subject s : group.getSubjects()) {
				subjectids[i++] = new Integer(s.getSubjectID());
			}

			stat.setArray(3, new ARRAY(ArrayDescriptor.createDescriptor("NUM_ARRAY", con), con, subjectids));

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void updateSubjectGroupBean(SubjectGroupBean group): " + e);
			throw new SchoolException("Can not update SubjectGroupBean to DB." + e);
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

	public static Collection<SubjectGroupBean> getSubjectGroupBeans() throws SchoolException {

		Collection<SubjectGroupBean> groups = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			groups = new ArrayList<SubjectGroupBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_subject_groups; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			SubjectGroupBean group = null;
			int id = 0;
			while (rs.next()) {
				if (rs.getInt("group_id") != id) {
					if (group != null) {
						groups.add(group);
					}

					group = createSubjectGroupBean(rs);

					id = group.getGroupId();

					group.addGroupSubject(SubjectDB.createSubjectBean(rs));
				}
				else {
					group.addGroupSubject(SubjectDB.createSubjectBean(rs));
				}
			}

			if (group != null) {
				groups.add(group);
			}
		}
		catch (SQLException e) {
			System.err.println("Collection<SubjectGroupBean> getSubjectGroupBeans(): " + e);
			throw new SchoolException("Can not extract SubjectGroupBean from DB: " + e);
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

		return groups;
	}

	public static SubjectGroupBean getSubjectGroupBean(int groupId) throws SchoolException {

		SubjectGroupBean group = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_subject_group(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, groupId);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			int id = 0;
			while (rs.next()) {
				if (rs.getInt("group_id") != id) {
					group = createSubjectGroupBean(rs);

					id = group.getGroupId();

					group.addGroupSubject(SubjectDB.createSubjectBean(rs));
				}
				else {
					group.addGroupSubject(SubjectDB.createSubjectBean(rs));
				}
			}
		}
		catch (SQLException e) {
			System.err.println("SubjectGroupBean getSubjectGroupBean(int groupId): " + e);
			throw new SchoolException("Can not extract SubjectGroupBean from DB: " + e);
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

		return group;
	}

	public static SubjectGroupBean createSubjectGroupBean(ResultSet rs) {

		SubjectGroupBean sr = null;

		try {
			sr = new SubjectGroupBean();

			sr.setGroupId(rs.getInt("GROUP_ID"));
			sr.setGroupName(rs.getString("GROUP_NAME"));
		}
		catch (Exception e) {
			e.printStackTrace();
			sr = null;
		}

		return sr;
	}
}
