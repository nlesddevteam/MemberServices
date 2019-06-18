package com.esdnl.nicep.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.nicep.beans.NICEPException;
import com.esdnl.nicep.beans.StudentDemographicsBean;
import com.esdnl.nicep.beans.StudentSchoolHistoryBean;

public class StudentSchoolHistoryManager {

	public static int addStudentSchoolHistoryBean(StudentSchoolHistoryBean abean) throws NICEPException {

		Connection con = null;
		CallableStatement stat = null;
		int id = 0;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin ? := awsd_user.nicep.add_student_school(?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, abean.getStudentId());
			stat.setInt(3, abean.getSchoolId());
			stat.setString(4, abean.getSchoolYear());
			stat.setInt(5, abean.getTerm());

			stat.execute();

			id = ((OracleCallableStatement) stat).getInt(1);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("int addStudentSchoolHistoryBean(StudentSchoolHistoryBean abean): " + e);
			throw new NICEPException("Can not add StudentSchoolHistoryBean to DB.", e);
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

		return id;
	}

	public static boolean updateStudentSchoolHistoryBean(StudentSchoolHistoryBean abean) throws NICEPException {

		Connection con = null;
		CallableStatement stat = null;
		boolean ok = false;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.nicep.update_student_school(?,?,?,?,?); end;");
			stat.setInt(1, abean.getHistoryId());
			stat.setInt(2, abean.getStudentId());
			stat.setInt(3, abean.getSchoolId());
			stat.setString(4, abean.getSchoolYear());
			stat.setInt(5, abean.getTerm());

			stat.execute();

			ok = true;
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("boolean updateStudentSchoolHistoryBean(StudentSchoolHistoryBean abean): " + e);
			throw new NICEPException("Can not add StudentSchoolHistoryBean to DB.", e);
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

		return ok;
	}

	public static boolean deleteStudentSchoolHistoryBean(int id) throws NICEPException {

		Connection con = null;
		CallableStatement stat = null;
		boolean ok = false;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.nicep.delete_student_school(?); end;");
			stat.setInt(1, id);

			stat.execute();

			ok = true;
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println(" boolean deleteStudentSchoolHistoryBean(int id): " + e);
			throw new NICEPException("Can not add StudentSchoolHistoryBean to DB.", e);
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

		return ok;
	}

	public static StudentSchoolHistoryBean[] getStudentSchoolHistoryBeans(StudentDemographicsBean student)
			throws NICEPException {

		Vector v_opps = null;
		StudentSchoolHistoryBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.nicep.get_student_schools(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, student.getStudentId());

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createStudentSchoolHistoryBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("AssignmentSubjectManager.getAssignmentSubjectBeans(JobOpportunityAssignemntBean): " + e);
			throw new NICEPException("Can not extract AssignmentSubjectBean from DB.", e);
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

		return (StudentSchoolHistoryBean[]) v_opps.toArray(new StudentSchoolHistoryBean[0]);
	}

	public static StudentSchoolHistoryBean getStudentSchoolHistoryBean(int id) throws NICEPException {

		StudentSchoolHistoryBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.nicep.get_student_school(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				eBean = createStudentSchoolHistoryBean(rs);
		}
		catch (SQLException e) {
			System.err.println("AssignmentSubjectManager.getAssignmentSubjectBeans(JobOpportunityAssignemntBean): " + e);
			throw new NICEPException("Can not extract AssignmentSubjectBean from DB.", e);
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

		return eBean;
	}

	public static StudentSchoolHistoryBean createStudentSchoolHistoryBean(ResultSet rs) {

		StudentSchoolHistoryBean abean = null;

		try {
			abean = new StudentSchoolHistoryBean();

			abean.setHistoryId(rs.getInt("HISTORY_ID"));
			abean.setStudentId(rs.getInt("STUDENT_ID"));
			abean.setSchoolId(rs.getInt("SCHOOL_ID"));
			abean.setSchoolYear(rs.getString("SCHOOL_YEAR"));
			abean.setTerm(rs.getInt("TERM"));
		}
		catch (SQLException e) {
			e.printStackTrace();
			abean = null;
		}

		return abean;
	}
}