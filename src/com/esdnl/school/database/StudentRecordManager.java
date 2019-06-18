package com.esdnl.school.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.school.SchoolException;
import com.esdnl.dao.DAOUtils;
import com.esdnl.school.bean.StudentRecordBean;
import com.esdnl.school.bean.StudentRecordBean.GENDER;

public class StudentRecordManager {

	public static void addStudentRecordBean(StudentRecordBean abean) throws SchoolException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.student_pkg.add_student_rec(?,?,?,?,?); end;");

			stat.setString(1, abean.getStudentId());
			stat.setString(2, abean.getFirstName());
			stat.setString(3, abean.getLastName());
			stat.setString(4, abean.getMiddleName());
			stat.setInt(5, abean.getGender().getId());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addStudentRecordBean(StudentRecordBean abean): " + e);
			throw new SchoolException("Can not add StudentRecordBean to DB." + e);
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

	public static StudentRecordBean getStudentRecordBean(String studentId) throws SchoolException {

		StudentRecordBean sr = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.student_pkg.get_student_rec(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, studentId);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next()) {
				sr = createStudentRecordBean(rs);
			}
		}
		catch (SQLException e) {
			System.err.println("StudentRecordBean getStudentRecordBean(String studentId): " + e);
			throw new SchoolException("Can not extract student record from DB: " + e);
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

		return sr;
	}

	public static ArrayList<StudentRecordBean> searchById(String studentId) throws SchoolException {

		ArrayList<StudentRecordBean> sr = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			sr = new ArrayList<StudentRecordBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.student_pkg.find_student_rec_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, studentId);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				sr.add(createStudentRecordBean(rs));
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<StudentRecordBean> searchById(String studentId): " + e);
			throw new SchoolException("Can not extract student record from DB: " + e);
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

		return sr;
	}

	public static StudentRecordBean createStudentRecordBean(ResultSet rs) {

		StudentRecordBean sr = null;

		try {
			sr = new StudentRecordBean();

			sr.setStudentId(rs.getString("STUDENT_ID"));
			sr.setFirstName(rs.getString("FIRSTNAME"));
			sr.setLastName(rs.getString("LASTNAME"));
			sr.setMiddleName(rs.getString("MIDDLENAME"));
			sr.setGender(GENDER.get(rs.getInt("GENDER")));
		}
		catch (Exception e) {
			e.printStackTrace();
			sr = null;
		}

		return sr;
	}
}
