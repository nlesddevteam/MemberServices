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
import com.esdnl.nicep.beans.StudentGuardianDemographicsBean;

public class StudentGuardianDemographicsManager {

	public static int addStudentGuardianDemographicsBean(StudentGuardianDemographicsBean abean) throws NICEPException {

		Connection con = null;
		CallableStatement stat = null;
		int id = 0;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin ? := awsd_user.nicep.add_student_guardian_demo(?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, abean.getStudentId());
			stat.setString(3, abean.getFirstname());
			stat.setString(4, abean.getLastname());
			stat.setString(5, abean.getAddress1());
			stat.setString(6, abean.getAddress2());
			stat.setString(7, abean.getCityTown());
			stat.setString(8, abean.getStateProvince());
			stat.setString(9, abean.getCountry());
			stat.setString(10, abean.getZipcode());
			stat.setString(11, abean.getPhone1());
			stat.setString(12, abean.getPhone2());
			stat.setString(13, abean.getEmail());

			stat.execute();

			id = ((OracleCallableStatement) stat).getInt(1);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addStudentGuardianDemographicsBean(StudentGuardianDemographicsBean abean): " + e);
			throw new NICEPException("Can not add StudentGuardianDemographicsBean to DB.", e);
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

	public static boolean updateStudentGuardianDemographicsBean(StudentGuardianDemographicsBean abean)
			throws NICEPException {

		Connection con = null;
		CallableStatement stat = null;
		boolean ok = false;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.nicep.update_student_guardian_demo(?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.setInt(1, abean.getGuardianId());
			stat.setInt(2, abean.getStudentId());
			stat.setString(3, abean.getFirstname());
			stat.setString(4, abean.getLastname());
			stat.setString(5, abean.getAddress1());
			stat.setString(6, abean.getAddress2());
			stat.setString(7, abean.getCityTown());
			stat.setString(8, abean.getStateProvince());
			stat.setString(9, abean.getCountry());
			stat.setString(10, abean.getZipcode());
			stat.setString(11, abean.getPhone1());
			stat.setString(12, abean.getPhone2());
			stat.setString(13, abean.getEmail());

			stat.execute();

			ok = true;
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addStudentGuardianDemographicsBean(StudentGuardianDemographicsBean abean): " + e);
			throw new NICEPException("Can not add StudentGuardianDemographicsBean to DB.", e);
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

	public static boolean deleteStudentGuardianDemographicsBean(int id) throws NICEPException {

		Connection con = null;
		CallableStatement stat = null;
		boolean ok = false;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.nicep.delete_student_guardian_demo(?); end;");
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

			System.err.println("void addStudentGuardianDemographicsBean(StudentGuardianDemographicsBean abean): " + e);
			throw new NICEPException("Can not add StudentGuardianDemographicsBean to DB.", e);
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

	public static StudentGuardianDemographicsBean[] getStudentGuardianDemographicsBeans(StudentDemographicsBean student)
			throws NICEPException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		Vector v = null;

		try {
			v = new Vector();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.nicep.get_student_guardian_demos(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, student.getStudentId());

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				v.add(createStudentGuardianDemographicsBean(rs));
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

		return (StudentGuardianDemographicsBean[]) v.toArray(new StudentGuardianDemographicsBean[0]);
	}

	public static StudentGuardianDemographicsBean getStudentGuardianDemographicsBeans(int id) throws NICEPException {

		StudentGuardianDemographicsBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.nicep.get_student_guardian_demo(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				eBean = createStudentGuardianDemographicsBean(rs);
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

	public static StudentGuardianDemographicsBean createStudentGuardianDemographicsBean(ResultSet rs) {

		StudentGuardianDemographicsBean abean = null;

		try {
			abean = new StudentGuardianDemographicsBean();

			abean.setStudentId(rs.getInt("STUDENT_ID"));
			abean.setGuardianId(rs.getInt("GUARDIAN_ID"));
			abean.setFirstname(rs.getString("FIRSTNAME"));
			abean.setLastname(rs.getString("LASTNAME"));
			abean.setAddress1(rs.getString("ADDRESS1"));
			abean.setAddress2(rs.getString("ADDRESS2"));
			abean.setCityTown(rs.getString("CITY_TOWN"));
			abean.setCountry(rs.getString("COUNTRY"));
			abean.setStateProvince(rs.getString("STATE_PROVINCE"));
			abean.setZipcode(rs.getString("ZIPCODE"));
			abean.setPhone1(rs.getString("PHONE1"));
			abean.setPhone2(rs.getString("PHONE2"));
			abean.setEmail(rs.getString("EMAIL"));
		}
		catch (SQLException e) {
			e.printStackTrace();
			abean = null;
		}

		return abean;
	}
}