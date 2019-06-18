package com.esdnl.nicep.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.nicep.beans.AgencyDemographicsBean;
import com.esdnl.nicep.beans.NICEPException;
import com.esdnl.nicep.beans.StudentDemographicsBean;

public class StudentDemographicsManager {

	public static int addStudentDemographicsBean(StudentDemographicsBean abean) throws NICEPException {

		Connection con = null;
		CallableStatement stat = null;
		int id = 0;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin ? := awsd_user.nicep.add_student_demo(?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2, abean.getFirstname());
			stat.setString(3, abean.getLastname());
			stat.setDate(4, new java.sql.Date(abean.getDateOfBirth().getTime()));
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

			System.err.println("void addStudentDemographicsBean(StudentDemographicsBean abean): " + e);
			throw new NICEPException("Can not add StudentDemographicsBean to DB.", e);
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

	public static boolean updateStudentDemographicsBean(StudentDemographicsBean abean) throws NICEPException {

		Connection con = null;
		CallableStatement stat = null;
		boolean ok = false;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.nicep.update_Student_demo(?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.setInt(1, abean.getStudentId());
			stat.setString(2, abean.getFirstname());
			stat.setString(3, abean.getLastname());
			stat.setDate(4, new java.sql.Date(abean.getDateOfBirth().getTime()));
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

			System.err.println("void addStudentDemographicsBean(StudentDemographicsBean abean): " + e);
			throw new NICEPException("Can not add StudentDemographicsBean to DB.", e);
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

	public static boolean deleteStudentDemographicsBean(int id) throws NICEPException {

		Connection con = null;
		CallableStatement stat = null;
		boolean ok = false;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.nicep.delete_student_demo(?); end;");
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

			System.err.println("void addStudentDemographicsBean(StudentDemographicsBean abean): " + e);
			throw new NICEPException("Can not add StudentDemographicsBean to DB.", e);
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

	public static ArrayList[] getStudentDemographicsBeans() throws NICEPException {

		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		ArrayList[] students = new ArrayList[26];

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.nicep.get_student_demo; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if (students[rs.getString("LASTNAME").toUpperCase().charAt(0) - 'A'] == null)
					students[rs.getString("LASTNAME").toUpperCase().charAt(0) - 'A'] = new ArrayList();

				students[rs.getString("LASTNAME").toUpperCase().charAt(0) - 'A'].add(createStudentDemographicsBean(rs));
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

		return students;
	}

	public static StudentDemographicsBean[] getStudentDemographicsBeansNoAgencyAssociation() throws NICEPException {

		StudentDemographicsBean s = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap map = null;

		try {
			map = new TreeMap();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.nicep.get_students_no_agency; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				s = createStudentDemographicsBean(rs);
				map.put(s.getFullname(), s);
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

		return (StudentDemographicsBean[]) map.values().toArray(new StudentDemographicsBean[0]);
	}

	public static StudentDemographicsBean[] getStudentDemographicsBeans(AgencyDemographicsBean agency)
			throws NICEPException {

		StudentDemographicsBean s = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		TreeMap map = null;

		try {
			map = new TreeMap();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.nicep.get_agency_students(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, agency.getAgencyId());

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				s = createStudentDemographicsBean(rs);
				map.put(s.getFullname(), s);
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

		return (StudentDemographicsBean[]) map.values().toArray(new StudentDemographicsBean[0]);
	}

	public static StudentDemographicsBean getStudentDemographicsBeans(int id) throws NICEPException {

		StudentDemographicsBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.nicep.get_student_demo(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				eBean = createStudentDemographicsBean(rs);
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

	public static StudentDemographicsBean createStudentDemographicsBean(ResultSet rs) {

		StudentDemographicsBean abean = null;

		try {
			abean = new StudentDemographicsBean();

			abean.setStudentId(rs.getInt("Student_ID"));
			abean.setFirstname(rs.getString("FIRSTNAME"));
			abean.setLastname(rs.getString("LASTNAME"));
			abean.setDateOfBirth(new java.util.Date(rs.getDate("DOB").getTime()));
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