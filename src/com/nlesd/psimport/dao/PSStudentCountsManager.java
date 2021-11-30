package com.nlesd.psimport.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.esdnl.dao.DAOUtils;
import com.nlesd.psimport.bean.PSStudentCountsBean;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class PSStudentCountsManager {
	public static PSStudentCountsBean getPSStudentCounts(String schoolid) {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		PSStudentCountsBean ebean = new PSStudentCountsBean();
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? :=awsd_user.ps_import_pkg.get_ps_sc(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, schoolid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);
			while (rs.next()){
				ebean = createPSStudentCountsBean(rs);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}
			System.err.println("PSStudentCountsBean getPSStudentCounts(String schoolid): "
					+ e);
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
		return ebean;
	}
	public static PSStudentCountsBean createPSStudentCountsBean(ResultSet rs) {
		PSStudentCountsBean hbean = null;
		try {
			hbean = new PSStudentCountsBean();
			hbean.setId(rs.getInt("ID"));
			hbean.setSchoolNumber(rs.getString("SCHOOL_NUMBER"));
			hbean.setStudentsK(rs.getInt("STUDENTS_K"));
			hbean.setStudents1(rs.getInt("STUDENTS_1"));
			hbean.setStudents2(rs.getInt("STUDENTS_2"));
			hbean.setStudents3(rs.getInt("STUDENTS_3"));
			hbean.setStudents4(rs.getInt("STUDENTS_4"));
			hbean.setStudents5(rs.getInt("STUDENTS_5"));
			hbean.setStudents6(rs.getInt("STUDENTS_6"));
			hbean.setStudents7(rs.getInt("STUDENTS_7"));
			hbean.setStudents8(rs.getInt("STUDENTS_8"));
			hbean.setStudents9(rs.getInt("STUDENTS_9"));
			hbean.setStudents10(rs.getInt("STUDENTS_10"));
			hbean.setStudents11(rs.getInt("STUDENTS_11"));
			hbean.setStudents12(rs.getInt("STUDENTS_12"));
			hbean.setStudents13(rs.getInt("STUDENTS_13"));
			hbean.setStudents14(rs.getInt("STUDENTS_14"));
	}
		catch (SQLException e) {
				hbean = null;
		}
		return hbean;
	}	
}
