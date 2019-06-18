package com.esdnl.personnel.sss.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.school.School;
import com.awsd.school.SchoolDB;
import com.awsd.school.SchoolException;
import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.sss.domain.bean.SSSException;
import com.esdnl.personnel.sss.domain.bean.SSSProfileSchoolInfoBean;
import com.esdnl.personnel.sss.domain.bean.StudentProgrammingInfoBean;

public class StudentProgrammingInfoManager {

	public static StudentProgrammingInfoBean addStudentProgrammingInfoBean(StudentProgrammingInfoBean bean)
			throws SSSException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.personnel_sss_profile_pkg.add_student_programming_info(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");

			//p_student_id IN VARCHAR,
			stat.setString(1, bean.getStudentId());
			//p_school_id IN NUMBER, 
			stat.setInt(2, bean.getSchool().getSchoolID());
			//p_school_year IN VARCHAR, 
			stat.setString(3, bean.getSchoolYear());
			//p_student_name IN VARCHAR, 
			stat.setString(4, bean.getStudentName());
			//p_student_exceptionality_id IN VARCHAR,
			stat.setString(5, bean.getExceptionality());
			//p_student_grade_id IN NUMBER, 
			stat.setInt(6, bean.getGrade().getValue());
			//p_student_pervasive_cat_id IN VARCHAR, 
			stat.setString(7, bean.getPervasiveCategory());
			//p_student_stream IN VARCHAR, 
			stat.setString(8, bean.getStream());
			//p_student_issp IN NUMBER, 
			stat.setBoolean(9, bean.isIssp());
			//p_student_iep IN NUMBER, 
			stat.setBoolean(10, bean.isIep());
			//p_p1 IN NUMBER,
			stat.setInt(11, bean.getP1Courses());
			//p_p2 IN NUMBER, 
			stat.setInt(12, bean.getP2Courses());
			//p_p3 IN NUMBER, 
			stat.setInt(13, bean.getP3Courses());
			//p_p4_cc IN NUMBER, 
			stat.setInt(14, bean.getP4CC());
			//p_p4_ncc IN NUMBER, 
			stat.setInt(15, bean.getP4NCC());
			//p_p4_pp IN NUMBER, 
			stat.setInt(16, bean.getP4PP());
			//p_p4_ncp IN NUMBER, 
			stat.setInt(17, bean.getP4NCP());
			//p_p5 IN NUMBER, 
			stat.setBoolean(18, bean.isP5());
			//p_support_approved in NUMBER
			stat.setBoolean(19, bean.isStudentAssistantSupport());

			stat.execute();

			con.commit();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println(" void addSSSProfileSchoolInfoBean(SSSProfileSchoolInfoBean bean): " + e);
			throw new SSSException("Can not add SSSProfileSchoolInfoBean to DB.", e);
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

		return bean;
	}

	public static StudentProgrammingInfoBean updateStudentProgrammingInfoBean(StudentProgrammingInfoBean bean)
			throws SSSException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.personnel_sss_profile_pkg.mod_student_programming_info(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");

			//p_student_id IN VARCHAR,
			stat.setString(1, bean.getStudentId());
			//p_school_id IN NUMBER, 
			stat.setInt(2, bean.getSchool().getSchoolID());
			//p_school_year IN VARCHAR, 
			stat.setString(3, bean.getSchoolYear());
			//p_student_name IN VARCHAR, 
			stat.setString(4, bean.getStudentName());
			//p_student_exceptionality_id IN VARCHAR,
			stat.setString(5, bean.getExceptionality());
			//p_student_grade_id IN NUMBER, 
			stat.setInt(6, bean.getGrade().getValue());
			//p_student_pervasive_cat_id IN VARCHAR, 
			stat.setString(7, bean.getPervasiveCategory());
			//p_student_stream IN VARCHAR, 
			stat.setString(8, bean.getStream());
			//p_student_issp IN NUMBER, 
			stat.setBoolean(9, bean.isIssp());
			//p_student_iep IN NUMBER, 
			stat.setBoolean(10, bean.isIep());
			//p_p1 IN NUMBER,
			stat.setInt(11, bean.getP1Courses());
			//p_p2 IN NUMBER, 
			stat.setInt(12, bean.getP2Courses());
			//p_p3 IN NUMBER, 
			stat.setInt(13, bean.getP3Courses());
			//p_p4_cc IN NUMBER, 
			stat.setInt(14, bean.getP4CC());
			//p_p4_ncc IN NUMBER, 
			stat.setInt(15, bean.getP4NCC());
			//p_p4_pp IN NUMBER, 
			stat.setInt(16, bean.getP4PP());
			//p_p4_ncp IN NUMBER, 
			stat.setInt(17, bean.getP4NCP());
			//p_p5 IN NUMBER, 
			stat.setBoolean(18, bean.isP5());
			//p_support_approved in NUMBER
			stat.setBoolean(19, bean.isStudentAssistantSupport());

			stat.execute();

			con.commit();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println(" void addSSSProfileSchoolInfoBean(SSSProfileSchoolInfoBean bean): " + e);
			throw new SSSException("Can not add SSSProfileSchoolInfoBean to DB.", e);
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

		return bean;
	}

	public static StudentProgrammingInfoBean addCurrentlyApprovedStudentProgrammingInfoBean(StudentProgrammingInfoBean bean)
			throws SSSException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.personnel_sss_profile_pkg.add_approved_stud_prog_info(?,?,?,?,?,?,?,?,?); end;");

			//p_student_id IN VARCHAR,
			stat.setString(1, bean.getStudentId());
			//p_school_id IN NUMBER, 
			stat.setInt(2, bean.getSchool().getSchoolID());
			//p_school_year IN VARCHAR, 
			stat.setString(3, bean.getSchoolYear());
			//p_student_name IN VARCHAR, 
			stat.setString(4, bean.getStudentName());
			//p_student_pervasive_cat_id IN VARCHAR, 
			stat.setString(5, bean.getPervasiveCategory());
			//p_transition_school_id
			if (bean.getTransitionSchool() != null)
				stat.setInt(6, bean.getTransitionSchool().getSchoolID());
			else
				stat.setNull(6, OracleTypes.NUMBER);
			//p_student_grad
			stat.setBoolean(7, bean.isGraduating());
			//p_student_leaving 
			stat.setBoolean(8, bean.isLeaving());
			//p_student_moving
			stat.setBoolean(9, bean.isMoving());

			stat.execute();

			con.commit();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println(" StudentProgrammingInfoBean addCurrentlyApprovedStudentProgrammingInfoBean(StudentProgrammingInfoBean bean): "
					+ e);
			throw new SSSException("Can not add StudentProgrammingInfoBean to DB.", e);
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

		return bean;
	}

	public static StudentProgrammingInfoBean updateCurrentlyApprovedStudentProgrammingInfoBean(	StudentProgrammingInfoBean bean)
			throws SSSException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.personnel_sss_profile_pkg.mod_approved_stud_prog_info(?,?,?,?,?,?,?,?,?); end;");

			//p_student_id IN VARCHAR,
			stat.setString(1, bean.getStudentId());
			//p_school_id IN NUMBER, 
			stat.setInt(2, bean.getSchool().getSchoolID());
			//p_school_year IN VARCHAR, 
			stat.setString(3, bean.getSchoolYear());
			//p_student_name IN VARCHAR, 
			stat.setString(4, bean.getStudentName());
			//p_student_pervasive_cat_id IN VARCHAR, 
			stat.setString(5, bean.getPervasiveCategory());
			//p_transition_school_id
			if (bean.getTransitionSchool() != null)
				stat.setInt(6, bean.getTransitionSchool().getSchoolID());
			else
				stat.setNull(6, OracleTypes.NUMBER);
			//p_student_grad
			stat.setBoolean(7, bean.isGraduating());
			//p_student_leaving 
			stat.setBoolean(8, bean.isLeaving());
			//p_student_moving
			stat.setBoolean(9, bean.isMoving());

			stat.execute();

			con.commit();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println(" StudentProgrammingInfoBean addCurrentlyApprovedStudentProgrammingInfoBean(StudentProgrammingInfoBean bean): "
					+ e);
			throw new SSSException("Can not add StudentProgrammingInfoBean to DB.", e);
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

		return bean;
	}

	public static ArrayList<StudentProgrammingInfoBean> getStudentProgrammingInfoBeans(SSSProfileSchoolInfoBean profile)
			throws SSSException {

		return getStudentProgrammingInfoBeans(profile.getSchool(), profile.getProjectedSchoolYear());
	}

	public static ArrayList<StudentProgrammingInfoBean> getStudentProgrammingInfoBeans(School school, String schoolyear)
			throws SSSException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		ArrayList<StudentProgrammingInfoBean> beans = null;

		try {
			beans = new ArrayList<StudentProgrammingInfoBean>();

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.personnel_sss_profile_pkg.get_school_stud_prog_info(?,?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, school.getSchoolID());
			stat.setString(3, schoolyear);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(createStudentProgrammingInfoBean(rs, school));

		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("ArrayList<StudentProgrammingInfoBean> getStudentProgrammingInfoBeans(School school, String schoolyear): "
					+ e);
			throw new SSSException("Can not retrieve StudentProgrammingInfoBean list from DB.", e);
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

		return beans;
	}

	public static StudentProgrammingInfoBean getStudentProgrammingInfoBean(String studentId, String schoolyear)
			throws SSSException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		StudentProgrammingInfoBean bean = null;

		try {

			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.personnel_sss_profile_pkg.get_stud_prog_info(?,?); end;");

			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, studentId);
			stat.setString(3, schoolyear);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				bean = createStudentProgrammingInfoBean(rs);

		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("StudentProgrammingInfoBean getStudentProgrammingInfoBean(String studentId, String schoolyear): "
					+ e);
			throw new SSSException("Can not retrieve StudentProgrammingInfoBean from DB.", e);
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

		return bean;
	}

	public static StudentProgrammingInfoBean createStudentProgrammingInfoBean(ResultSet rs) {

		return createStudentProgrammingInfoBean(rs, null);

	}

	public static StudentProgrammingInfoBean createStudentProgrammingInfoBean(ResultSet rs, School school) {

		StudentProgrammingInfoBean bean = null;

		try {

			bean = new StudentProgrammingInfoBean();

			//STUDENT_ID,
			bean.setStudentId(rs.getString("STUDENT_ID"));
			//SCHOOL_YEAR, 
			bean.setSchoolYear(rs.getString("SCHOOL_YEAR"));
			//SCHOOL_ID,
			if (school == null)
				bean.setSchool(SchoolDB.getSchool(rs.getInt("SCHOOL_ID")));
			else
				bean.setSchool(school);
			//STUDENT_NAME,
			bean.setStudentName(rs.getString("STUDENT_NAME"));
			//STUDENT_EXCEPTIONALITY, 
			bean.setExceptionality(rs.getString("STUDENT_EXCEPTIONALITY"));
			//STUDENT_GRADE_ID, 
			bean.setGrade(School.GRADE.get(rs.getInt("STUDENT_GRADE_ID")));
			//STUDENT_PERVASIVE_CAT, 
			bean.setPervasiveCategory(rs.getString("STUDENT_PERVASIVE_CAT"));
			//STUDENT_STREAM, 
			bean.setStream(rs.getString("STUDENT_STREAM"));
			//STUDENT_ISSP, 
			bean.setIssp(rs.getBoolean("STUDENT_ISSP"));
			//STUDENT_IEP, 
			bean.setIep(rs.getBoolean("STUDENT_IEP"));
			//STUDENT_P1, 
			bean.setP1Courses(rs.getInt("STUDENT_P1"));
			//STUDENT_P2, 
			bean.setP2Courses(rs.getInt("STUDENT_P2"));
			//STUDENT_P3, 
			bean.setP3Courses(rs.getInt("STUDENT_P3"));
			//STUDENT_P4_CC,
			bean.setP4CC(rs.getInt("STUDENT_P4_CC"));
			//STUDENT_P4_NCC,
			bean.setP4NCC(rs.getInt("STUDENT_P4_NCC"));
			//STUDENT_P4_PP, 
			bean.setP4PP(rs.getInt("STUDENT_P4_PP"));
			//STUDENT_P4_NCP, 
			bean.setP4NCP(rs.getInt("STUDENT_P4_NCP"));
			//STUDENT_P5, 
			bean.setP5(rs.getBoolean("STUDENT_P5"));
			//STUDENT_SUPPORT_APPROVED
			bean.setStudentAssistantSupport(rs.getBoolean("STUDENT_SUPPORT_APPROVED"));

			bean.setDirty(false);

		}
		catch (SQLException e) {
			e.printStackTrace();
			bean = null;
		}
		catch (SchoolException e) {

			e.printStackTrace();
			bean = null;
		}

		return bean;
	}
}
