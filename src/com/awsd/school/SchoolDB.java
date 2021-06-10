package com.awsd.school;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.personnel.PersonnelException;
import com.awsd.school.bean.RegionBean;
import com.awsd.school.bean.RegionException;
import com.awsd.school.dao.RegionManager;
import com.awsd.strike.SchoolStrikeGroup;
import com.awsd.weather.ClosureStatusDB;
import com.awsd.weather.ClosureStatusException;
import com.awsd.weather.SchoolSystem;
import com.awsd.weather.SchoolSystemException;
import com.esdnl.dao.DAOUtils;
import com.nlesd.school.bean.SchoolDirectoryDetailsOtherBean;
import com.nlesd.school.bean.SchoolStreamDetailsBean;
import com.nlesd.school.bean.SchoolStreamSelectListBean;
import com.nlesd.school.bean.SchoolZoneBean;
import com.nlesd.school.service.SchoolDirectoryDetailsOtherService;
import com.nlesd.school.service.SchoolDirectoryDetailsService;
import com.nlesd.school.service.SchoolStreamDetailsService;
import com.nlesd.school.service.SchoolZoneService;
import com.nlesd.schoolstatus.bean.SchoolStatusGlobalConfigBean;
import com.nlesd.schoolstatus.service.SchoolStatusGlobalConfigService;

public class SchoolDB {

	public static Vector<School> getSchools() throws SchoolException {

		Vector<School> schools = null;
		School s = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			schools = new Vector<School>(126);
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_schools_3; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next()) {
				do {
					s = createSchoolBean(rs);
					schools.add(s);

					if (!rs.isAfterLast() && (rs.getInt("SCHOOL_ID") == s.getSchoolID()))
						
						rs.next();
						
				} while (!rs.isAfterLast());
			}
		}
		catch (SQLException e) {
			System.err.println("SchoolDB.getSchools(): " + e);
			throw new SchoolException("Can not extract schools from DB: " + e);
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
		return schools;
	}

	public static Collection<School> getSchools(SchoolZoneBean zone) throws RegionException {

		Vector<School> v_opps = null;
		School s = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<School>(14);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_zone_schools(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, zone.getZoneId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next()) {
				do {
					s = createSchoolBean(rs);
					v_opps.add(s);

					if (!rs.isAfterLast() && (rs.getInt("SCHOOL_ID") == s.getSchoolID()))
						rs.next();

				} while (!rs.isAfterLast());
			}
		}
		catch (SQLException e) {
			System.err.println("Collection<School> getSchools(SchoolZoneBean zone): " + e);
			throw new RegionException("Can not extract Schools from DB.", e);
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

	public static Collection<School> getSchools(RegionBean region) throws RegionException {

		Vector<School> v_opps = null;
		School s = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<School>(14);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_region_schools_2(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, region.getId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next()) {
				do {
					s = createSchoolBean(rs);
					v_opps.add(s);

					if (!rs.isAfterLast() && (rs.getInt("SCHOOL_ID") == s.getSchoolID()))
						rs.next();

				} while (!rs.isAfterLast());
			}
		}
		catch (SQLException e) {
			System.err.println("Collection<School> getSchools(RegionBean region): " + e);
			throw new RegionException("Can not extract Schools from DB.", e);
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

	public static Vector<School> getSchools(SchoolSystem ss) throws SchoolException {

		Vector<School> schools = null;
		// School s = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			schools = new Vector<School>(10);

			sql = "SELECT SCHOOL.SCHOOL_ID, SCHOOL_NAME, "
					+ "nvl(PRINCIPAL_ID, 0) PRINCIPAL_ID, "
					+ "nvl(VICEPRINCIPAL_ID, 0) VICEPRINCIPAL_ID, DEPT_ID, PERSONNEL_ID, PERSONNEL_USERNAME, PERSONNEL_PASSWORD, PERSONNEL_FIRSTNAME, PERSONNEL_LASTNAME, PERSONNEL_EMAIL, PERSONNEL_CATEGORYID, PERSONNEL_SUPERVISOR_ID "
					+ "FROM SCHOOL LEFT JOIN PERSONNEL ON SCHOOL.PRINCIPAL_ID = PERSONNEL.PERSONNEL_ID, SCHOOL_SYSTEM, SCHOOL_SYSTEM_SCHOOLS "
					+ "WHERE SCHOOL.SCHOOL_ID=SCHOOL_SYSTEM_SCHOOLS.SCHOOL_ID "
					+ "AND SCHOOL_SYSTEM.SS_ID=SCHOOL_SYSTEM_SCHOOLS.SS_ID " + "AND SCHOOL_SYSTEM.SS_ID="
					+ ss.getSchoolSystemID() + " and SCHOOL.DELETED=0 ORDER BY SCHOOL_NAME";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				// s = new School(rs.getInt("SCHOOL_ID"), rs.getString("SCHOOL_NAME"),
				// rs.getInt("PRINCIPAL_ID"), rs.getInt("VICEPRINCIPAL_ID"));
				schools.add(createSchoolBean(rs));
			}

		}
		catch (SQLException e) {
			System.err.println("SchoolDB.getSchools(SchoolSystem): " + e);
			throw new SchoolException("Can not extract schools from DB: " + e);
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
		return schools;
	}

	public static Vector<School> getSchoolsNotAssignedSchoolSystem() throws SchoolException {

		Vector<School> schools = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			schools = new Vector<School>(10);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_schools_no_school_system; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				schools.add(createSchoolBean(rs));
			}
		}
		catch (SQLException e) {
			System.err.println("SchoolDB.getSchools(): " + e);
			throw new SchoolException("Can not extract schools from DB: " + e);
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
		return schools;
	}

	public static Vector<School> getSchools(SchoolFamily ss) throws SchoolException {
		 
        Vector<School> schools = null;
        Connection con = null;
        Statement stat = null;
        ResultSet rs = null;
        String sql;
 
        try {
            schools = new Vector<School>(10);

             sql = "SELECT SCHOOL.SCHOOL_ID, SCHOOL_NAME, "
                    + "nvl(PRINCIPAL_ID, 0) PRINCIPAL_ID, "
                    + "nvl(VICEPRINCIPAL_ID, 0) VICEPRINCIPAL_ID, DEPT_ID, PERSONNEL_ID, PERSONNEL_USERNAME, PERSONNEL_PASSWORD, PERSONNEL_FIRSTNAME, PERSONNEL_LASTNAME, PERSONNEL_EMAIL, PERSONNEL_CATEGORYID, PERSONNEL_SUPERVISOR_ID, "
                    + "SCHOOL.TOWNCITY,SCHOOL.REGION_ID,SCHOOL.ZONE_ID "
                    + "FROM SCHOOL LEFT JOIN PERSONNEL ON PRINCIPAL_ID = PERSONNEL_ID, SCHOOL_FAMILY, SCHOOL_FAMILY_SCHOOLS "
                    + "WHERE SCHOOL.SCHOOL_ID=SCHOOL_FAMILY_SCHOOLS.SCHOOL_ID "
                    + "AND SCHOOL_FAMILY.FAMILY_ID=SCHOOL_FAMILY_SCHOOLS.FAMILY_ID " + "AND SCHOOL_FAMILY.FAMILY_ID="
                    + ss.getSchoolFamilyID() + " ORDER BY SCHOOL_NAME";

 

            con = DAOUtils.getConnection();
            stat = con.createStatement();
            rs = stat.executeQuery(sql);
            while (rs.next()) {
                schools.add(createSchoolBean(rs));
            }
        }
        catch (SQLException e) {
            System.err.println("SchoolDB.getSchools(SchoolFamily): " + e);
            throw new SchoolException("Can not extract schools from DB: " + e);
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
        return schools;
    }

	public static Vector<School> getSchoolsNotAssignedSchoolFamily() throws SchoolException {

		Vector<School> schools = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			schools = new Vector<School>(10);

			sql = "SELECT S.SCHOOL_ID, SCHOOL_NAME, "
					+ "nvl(PRINCIPAL_ID, 0) PRINCIPAL_ID, "
					+ "nvl(VICEPRINCIPAL_ID, 0) VICEPRINCIPAL_ID, DEPT_ID, PERSONNEL_ID, PERSONNEL_USERNAME, PERSONNEL_PASSWORD, PERSONNEL_FIRSTNAME, PERSONNEL_LASTNAME, PERSONNEL_EMAIL, PERSONNEL_CATEGORYID, PERSONNEL_SUPERVISOR_ID "
					+ "FROM SCHOOL S LEFT JOIN PERSONNEL ON PRINCIPAL_ID=PERSONNEL_ID WHERE NOT EXISTS ("
					+ "SELECT SS.SCHOOL_ID FROM SCHOOL_FAMILY_SCHOOLS SS WHERE S.SCHOOL_ID = SS.SCHOOL_ID) "
					+ "ORDER BY SCHOOL_NAME";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				schools.add(createSchoolBean(rs));
			}
		}
		catch (SQLException e) {
			System.err.println("SchoolDB.getSchools(): " + e);
			throw new SchoolException("Can not extract schools from DB: " + e);
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
		return schools;
	}

	public static Vector<School> getSchools(SchoolStrikeGroup group) throws SchoolException {

		Vector<School> schools = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			schools = new Vector<School>(10);

			sql = "SELECT SCHOOL.SCHOOL_ID, SCHOOL_NAME, "
					+ "nvl(PRINCIPAL_ID, 0) PRINCIPAL_ID, "
					+ "nvl(VICEPRINCIPAL_ID, 0) VICEPRINCIPAL_ID, DEPT_ID, PERSONNEL_ID, PERSONNEL_USERNAME, PERSONNEL_PASSWORD, PERSONNEL_FIRSTNAME, PERSONNEL_LASTNAME, PERSONNEL_EMAIL, PERSONNEL_CATEGORYID, PERSONNEL_SUPERVISOR_ID "
					+ "FROM SCHOOL LEFT JOIN PERSONNEL ON PRINCIPAL_ID=PERSONNEL_ID, STRIKE_SCHOOL_GROUP, STRIKE_SCHOOL_GROUP_SCHOOLS "
					+ "WHERE SCHOOL.SCHOOL_ID=STRIKE_SCHOOL_GROUP_SCHOOLS.SCHOOL_ID "
					+ "AND STRIKE_SCHOOL_GROUP.GROUP_ID=STRIKE_SCHOOL_GROUP_SCHOOLS.GROUP_ID "
					+ "AND STRIKE_SCHOOL_GROUP.GROUP_ID=" + group.getGroupID() + " ORDER BY SCHOOL_NAME";

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			while (rs.next()) {
				schools.add(createSchoolBean(rs));
			}
		}
		catch (SQLException e) {
			System.err.println("SchoolDB.getSchools(): " + e);
			throw new SchoolException("Can not extract schools from DB: " + e);
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
		return schools;
	}

	public static School getSchool(int id) throws SchoolException {

		School s = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_school(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next()) {
				s = createSchoolBean(rs);
			}
			else {
				throw new SchoolException("No School matching ID: " + id);
			}
		}
		catch (SQLException e) {
			System.err.println("SchoolDB.getSchool(): " + e);
			throw new SchoolException("Can not extract school from DB: " + e);
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
		return s;
	}
	public static School getSchoolFullDetails(int id) throws SchoolException {

		School s = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();

			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_school_details_all(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next()) {
				s = createSchoolFullDetailsBean(rs);
			}
			else {
				throw new SchoolException("No School matching ID: " + id);
			}
		}
		catch (SQLException e) {
			System.err.println("SchoolDB.getSchool(): " + e);
			throw new SchoolException("Can not extract school from DB: " + e);
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
		return s;
	}
	public static School getSchoolFromDeptId(int id) throws SchoolException {

		School s = null;
		Connection con = null;
		Statement stat = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT SCHOOL.SCHOOL_ID, SCHOOL_NAME, "
					+ "nvl(PRINCIPAL_ID, 0) PRINCIPAL_ID, "
					+ "nvl(VICEPRINCIPAL_ID, 0) VICEPRINCIPAL_ID, DEPT_ID, PERSONNEL_ID, PERSONNEL_USERNAME, PERSONNEL_PASSWORD, PERSONNEL_FIRSTNAME, PERSONNEL_LASTNAME, PERSONNEL_EMAIL, PERSONNEL_CATEGORYID, PERSONNEL_SUPERVISOR_ID "
					+ "FROM SCHOOL LEFT JOIN PERSONNEL ON PRINCIPAL_ID=PERSONNEL_ID " + "WHERE DEPT_ID=" + id;

			con = DAOUtils.getConnection();
			stat = con.createStatement();
			rs = stat.executeQuery(sql);

			if (rs.next()) {
				s = createSchoolBean(rs);
			}
			else {
				throw new SchoolException("No School matching ID: " + id);
			}
		}
		catch (SQLException e) {
			System.err.println("SchoolDB.getSchool(): " + e);
			throw new SchoolException("Can not extract school from DB: " + e);
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
		return s;
	}

	public static boolean updateSchool(School s) throws SchoolException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;
		boolean su = false;
		boolean apu = false;

		try {

			con = DAOUtils.getConnection();

			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.schools_pkg.update_school(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");

			stat.setInt(1, s.getSchoolID());
			stat.setString(2, s.getSchoolName());

			if (s.getSchoolPrincipal() != null)
				stat.setInt(3, s.getSchoolPrincipal().getPersonnelID());
			else
				stat.setNull(3, OracleTypes.NUMBER);

			stat.setInt(4, s.getSchoolDeptID());
			stat.setString(5, s.getAddress1());
			stat.setString(6, s.getAddress2());
			stat.setString(7, s.getTownCity());
			stat.setString(8, s.getProvinceState());
			stat.setString(9, s.getPostalZipCode());
			stat.setString(10, s.getTelephone());
			stat.setString(11, s.getFax());
			stat.setString(12, s.getWebsite());
			stat.setInt(13, s.getLowestGrade().getValue());
			stat.setInt(14, s.getHighestGrade().getValue());

			if (s.getZone() != null) {
				stat.setInt(15, s.getZone().getZoneId());
			}
			else {
				stat.setNull(15, OracleTypes.NUMBER);
			}

			try {
				if (s.getRegion() != null) {
					stat.setInt(16, s.getRegion().getId());
				}
				else {
					stat.setNull(16, OracleTypes.NUMBER);
				}
			}
			catch (RegionException e) {
				stat.setNull(16, OracleTypes.NUMBER);
			}

			check = stat.execute();

			stat.close();

			apu = updateSchoolAssistantPrincipals(s);
			if (apu) {
				System.out.println("=== SCHOOL ASSISTANT PRINCIPALS UPDATED ===");

				su = updateSchoolTeacherSupervisor(s);
				if (su) {
					System.out.println("=== SCHOOL TEACHER SUPERVISORS UPDATED ===");

					con.commit();
				}
				else {
					try {
						con.rollback();
					}
					catch (Exception e) {}
				}

			}
			else {
				try {
					con.rollback();
				}
				catch (Exception e) {}
			}

		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("SchoolDB.updateSchool(): " + e);
			throw new SchoolException("Could not update school in DB: " + e);
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
		return (check && su && apu);
	}

	public static boolean updateSchoolTeacherSupervisor(School s) throws SchoolException {

		Connection con = null;
		Statement stat = null;
		StringBuffer sql;
		int check = 0;

		try {
			if (s.getSchoolPrincipal() != null) {
				sql = new StringBuffer("UPDATE PERSONNEL SET PERSONNEL_SUPERVISOR_ID="
						+ s.getSchoolPrincipal().getPersonnelID());
			}
			else {
				sql = new StringBuffer("UPDATE PERSONNEL SET PERSONNEL_SUPERVISOR_ID=null");
			}

			sql.append(" WHERE SCHOOL_ID=" + s.getSchoolID());

			if (s.getSchoolPrincipal() != null) {
				sql.append(" AND PERSONNEL_ID != " + s.getSchoolPrincipal().getPersonnelID());
			}

			con = DAOUtils.getConnection();
			con.setAutoCommit(false);
			stat = con.createStatement();
			check = stat.executeUpdate(sql.toString());
			con.commit();
		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("SchoolDB.updateSchoolTeacherSupervispor(): " + e);
			throw new SchoolException("Could not update school teacher supervisor in DB: " + e);
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
		return (check >= 0);
	}

	public static boolean deleteSchool(School s) throws SchoolException {

		Connection con = null;
		PreparedStatement stat = null;
		String sql;
		int check = 0;

		try {

			sql = "UPDATE SCHOOL SET DELETED=1 WHERE SCHOOL_ID=?";

			con = DAOUtils.getConnection();

			con.setAutoCommit(false);
			stat = con.prepareStatement(sql);

			stat.setInt(1, s.getSchoolID());
			check = stat.executeUpdate();
			con.commit();
		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("SchoolDB.deleteSchool(): " + e);

			throw new SchoolException("Could not delete School from DB: " + e);
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
		return (check == 1);
	}

	public static School addSchool(School s) throws SchoolException {

		Connection con = null;
		CallableStatement stat = null;

		try {

			con = DAOUtils.getConnection();

			con.setAutoCommit(true);

			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.add_school(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);

			stat.setString(2, s.getSchoolName());

			if (s.getSchoolPrincipal() != null)
				stat.setInt(3, s.getSchoolPrincipal().getPersonnelID());
			else
				stat.setNull(3, OracleTypes.NUMBER);

			stat.setInt(4, s.getSchoolDeptID());
			stat.setString(5, s.getAddress1());
			stat.setString(6, s.getAddress2());
			stat.setString(7, s.getTownCity());
			stat.setString(8, s.getProvinceState());
			stat.setString(9, s.getPostalZipCode());
			stat.setString(10, s.getTelephone());
			stat.setString(11, s.getFax());
			stat.setString(12, s.getWebsite());
			stat.setInt(13, s.getLowestGrade().getValue());
			stat.setInt(14, s.getHighestGrade().getValue());

			if (s.getZone() != null) {
				stat.setInt(15, s.getZone().getZoneId());
			}
			else {
				stat.setNull(15, OracleTypes.NUMBER);
			}

			try {
				if (s.getRegion() != null) {
					stat.setInt(16, s.getRegion().getId());
				}
				else {
					stat.setNull(16, OracleTypes.NUMBER);
				}
			}
			catch (RegionException e) {
				stat.setNull(16, OracleTypes.NUMBER);
			}

			stat.execute();

			int id = stat.getInt(1);

			s.setSchoolID(id);
		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("boolean addSchool(School s): " + e);
			throw new SchoolException("Could not add school in DB: " + e);
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
		return s;
	}

	public static boolean updateSchoolAssistantPrincipals(School s) {

		Connection con = null;
		CallableStatement stat = null;
		Personnel[] aps = null;
		boolean flag = false;

		try {

			con = DAOUtils.getConnection();

			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.schools_pkg.del_school_aps(?); end;");
			stat.setInt(1, s.getSchoolID());
			stat.execute();

			stat.close();

			aps = s.getAssistantPrincipals();
			stat = con.prepareCall("begin awsd_user.schools_pkg.add_school_ap(?,?); end;");
			for (int i = 0; i < aps.length; i++) {
				stat.clearParameters();

				stat.setInt(1, s.getSchoolID());
				stat.setInt(2, aps[i].getPersonnelID());

				stat.execute();
			}

			con.commit();

			flag = true;
		}
		catch (PersonnelException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("updateSchoolAssistantPrincipals(School s): " + e);
			e.printStackTrace();
			// throw new PersonnelException("Can not extract supervisors from DB: " +
			// e);

			flag = false;
		}
		catch (SQLException e) {
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("updateSchoolAssistantPrincipals(School s): " + e);
			e.printStackTrace();
			// throw new PersonnelException("Can not extract supervisors from DB: " +
			// e);

			flag = false;
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

		return flag;
	}

	public static ArrayList<Vector<School>> getSchoolsAlphabetized() {

		ArrayList<Vector<School>> alpha = null;
		School eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			alpha = new ArrayList<Vector<School>>(26);
			for (int i = 0; i < 26; i++)
				alpha.add(new Vector<School>(5));

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := SCHOOLS_PKG.get_schools_3; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next()) {
				do {
					eBean = createSchoolBean(rs);

					int sortorder = eBean.getSchoolName().trim().toUpperCase().charAt(0);

					if (sortorder == 144) {
						sortorder = 69;
					}

					((Vector<School>) alpha.get(sortorder - 'A')).add(eBean);

					if (!rs.isAfterLast() && (rs.getInt("SCHOOL_ID") == eBean.getSchoolID()))
						rs.next();

				} while (!rs.isAfterLast());
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList getSchoolsAlphabetized: " + e);
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

		return alpha;
	}

	public void clearSchoolAdmin(int pid) {

		Connection con = null;
		CallableStatement stat = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.schools_pkg.clear_admin(?); end;");
			stat.setInt(1, pid);
			stat.execute();

		}
		catch (SQLException e) {
			System.err.println(" void clearSchoolAdmin: " + e);
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

	public static School createSchoolBean(ResultSet rs) {

		School abean = null;
		try {
			abean = new School();
			abean.setSchoolID(rs.getInt("SCHOOL_ID"));
			abean.setSchoolName(rs.getString("SCHOOL_NAME"));
			abean.setSchoolDeptID(rs.getInt("DEPT_ID"));
			if (rs.getInt("PRINCIPAL_ID") > 0)
				abean.setSchoolPrincipal(PersonnelDB.createPersonnelBean(rs));

			// set school status if available.
			try {
				if (rs.getInt("CLOSURE_ID") > 0)
					abean.setSchoolClosureStatus(ClosureStatusDB.createClosureStatusBean(rs));
				else {
					SchoolStatusGlobalConfigBean config;
					try {
						config = SchoolStatusGlobalConfigService.getSchoolStatusGlobalConfigBean();
					}
					catch (SchoolSystemException e) {
						throw new ClosureStatusException("Can not extract school status global config statuses from DB: ");
					}
					if (!config.isSummer()) {
						abean.setSchoolClosureStatus(ClosureStatusDB.getClosureStatus(9));
					}
					else {
						abean.setSchoolClosureStatus(ClosureStatusDB.getClosureStatus(22));
					}
				}
			}
			catch (ClosureStatusException e) {
				e.printStackTrace(System.err);
			}
			catch (SQLException e) {
				// school status not available
			}

			// set region if available
			try {
				if (rs.getInt("REGION_ID") > 0)
					abean.setRegion(RegionManager.createRegionBean(rs));
			}
			catch (SQLException e) {
				// school region not available.
			}

			//set zone if available
			try {
				if (rs.getInt("ZONE_ID") > 0)
					abean.setZone(SchoolZoneService.createSchoolZoneBean(rs));
			}
			catch (SQLException e) {
				// school zone not available.
			}

			try {
				abean.setAddress1(rs.getString("ADDRESS1"));
				abean.setAddress2(rs.getString("ADDRESS2"));
				abean.setTownCity(rs.getString("TOWNCITY"));
				abean.setProvinceState(rs.getString("PROVINCESTATE"));
				abean.setPostalZipCode(rs.getString("POSTALZIPCODE"));
			}
			catch (SQLException e) {
				// no address info available
			}

			try {
				abean.setTelephone(rs.getString("TELEPHONE"));
				abean.setFax(rs.getString("FAX"));
			}
			catch (SQLException e) {
				//no phone info
			}

			try {
				abean.setWebsite(rs.getString("WEBSITE"));
			}
			catch (SQLException e) {
				//no website info
			}

			try {
				abean.setLowestGrade(School.GRADE.get(rs.getInt("LGRADE")));
				abean.setHighestGrade(School.GRADE.get(rs.getInt("HGRADE")));
			}
			catch (SQLException e) {
				// no grade info
			}

			try {
				if (rs.getInt("DIRECTORY_ID") > 0) {
					abean.setDetails(SchoolDirectoryDetailsService.createSchoolDirectoryDetailsBean(rs));
				}
				else {
					abean.setDetails(null);
				}
			}
			catch (SQLException e) {
				//no school directory details.
				abean.setDetails(null);
			}
			try {
				//check to see there is a other record
				if (rs.getInt("FK_SCHOOL_DIRECTORY") > 0) {
					SchoolDirectoryDetailsOtherBean obean = SchoolDirectoryDetailsOtherService.createSchoolDirectoryDetailsOtherBean(rs);
					abean.setDetailsOther(obean);
				}else {
					abean.setDetailsOther(null);
				}
				
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				abean.setDetailsOther(null);
			}
			try {
				//check to see there is a other record
				if (rs.getInt("sstreamid") >0) {
					SchoolStreamDetailsBean obean = SchoolStreamDetailsService.createSchoolStreamDetailsBean(rs);
					abean.setSchoolStreams(obean);
				}else {
					abean.setSchoolStreams(null);
				}
				
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				abean.setSchoolStreams(null);
			}

// assistant principals, if available
			try {
				if (rs.getInt("AP_PERSONNEL_ID") > 0) {
					do {
						try {
							abean.addAssistantPrincipal(PersonnelDB.createAssistantPrincipalPersonnelBean(rs));
						}
						catch (PersonnelException e) {
							e.printStackTrace();
						}
					} while (rs.next() && (rs.getInt("SCHOOL_ID") == abean.getSchoolID()));
				}
			}
			catch (SQLException e) {
				// no assistant principal info available
			}
		}
		catch (SQLException e) {
			abean = null;
		}
		return abean;
	}
	public static School createSchoolFullDetailsBean(ResultSet rs) {

		School abean = null;
		try {
			abean = new School();
			abean.setSchoolID(rs.getInt("SCHOOL_ID"));
			abean.setSchoolName(rs.getString("SCHOOL_NAME"));
			abean.setSchoolDeptID(rs.getInt("DEPT_ID"));

			if (rs.getInt("PRINCIPAL_ID") > 0)
				abean.setSchoolPrincipal(PersonnelDB.createPersonnelBean(rs));

			// set school status if available.
			try {
				if (rs.getInt("CLOSURE_ID") > 0)
					abean.setSchoolClosureStatus(ClosureStatusDB.createClosureStatusBean(rs));
				else {
					SchoolStatusGlobalConfigBean config;
					try {
						config = SchoolStatusGlobalConfigService.getSchoolStatusGlobalConfigBean();
					}
					catch (SchoolSystemException e) {
						throw new ClosureStatusException("Can not extract school status global config statuses from DB: ");
					}
					if (!config.isSummer()) {
						abean.setSchoolClosureStatus(ClosureStatusDB.getClosureStatus(9));
					}
					else {
						abean.setSchoolClosureStatus(ClosureStatusDB.getClosureStatus(22));
					}
				}
			}
			catch (ClosureStatusException e) {
				e.printStackTrace(System.err);
			}
			catch (SQLException e) {
				// school status not available
			}

			// set region if available
			try {
				if (rs.getInt("REGION_ID") > 0)
					abean.setRegion(RegionManager.createRegionBean(rs));
			}
			catch (SQLException e) {
				// school region not available.
			}

			//set zone if available
			try {
				if (rs.getInt("ZONE_ID") > 0)
					abean.setZone(SchoolZoneService.createSchoolZoneBean(rs));
			}
			catch (SQLException e) {
				// school zone not available.
			}

			try {
				abean.setAddress1(rs.getString("ADDRESS1"));
				abean.setAddress2(rs.getString("ADDRESS2"));
				abean.setTownCity(rs.getString("TOWNCITY"));
				abean.setProvinceState(rs.getString("PROVINCESTATE"));
				abean.setPostalZipCode(rs.getString("POSTALZIPCODE"));
			}
			catch (SQLException e) {
				// no address info available
			}

			try {
				abean.setTelephone(rs.getString("TELEPHONE"));
				abean.setFax(rs.getString("FAX"));
			}
			catch (SQLException e) {
				//no phone info
			}

			try {
				abean.setWebsite(rs.getString("WEBSITE"));
			}
			catch (SQLException e) {
				//no website info
			}

			try {
				abean.setLowestGrade(School.GRADE.get(rs.getInt("LGRADE")));
				abean.setHighestGrade(School.GRADE.get(rs.getInt("HGRADE")));
			}
			catch (SQLException e) {
				// no grade info
			}

			try {
				if (rs.getInt("DIRECTORY_ID") > 0) {
					abean.setDetails(SchoolDirectoryDetailsService.createSchoolDirectoryDetailsBean(rs));
				}
				else {
					abean.setDetails(null);
				}
			}
			catch (SQLException e) {
				//no school directory details.
				abean.setDetails(null);
			}
			try {
				//check to see there is a other record
				if (rs.getInt("FK_SCHOOL_DIRECTORY") > 0) {
					SchoolDirectoryDetailsOtherBean obean = SchoolDirectoryDetailsOtherService.createSchoolDirectoryDetailsOtherBean(rs);
					abean.setDetailsOther(obean);
				}else {
					abean.setDetailsOther(null);
				}
				
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				abean.setDetailsOther(null);
			}
			try {
				//check to see there is a other record
				if (rs.getInt("sstreamid") >0) {
					SchoolStreamDetailsBean obean = SchoolStreamDetailsService.createSchoolStreamDetailsBean(rs);
					abean.setSchoolStreams(obean);
				}else {
					abean.setSchoolStreams(null);
				}
				
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				abean.setSchoolStreams(null);
			}
// assistant principals, if available
			try {
				if (rs.getInt("AP_PERSONNEL_ID") > 0) {
					do {
						try {
							abean.addAssistantPrincipal(PersonnelDB.createAssistantPrincipalPersonnelBean(rs));
						}
						catch (PersonnelException e) {
							e.printStackTrace();
						}
					} while (rs.next() && (rs.getInt("SCHOOL_ID") == abean.getSchoolID()));
				}
			}
			catch (SQLException e) {
				// no assistant principal info available
			}
		}
		catch (SQLException e) {
			abean = null;
		}
		return abean;
	}
	public static int getSchoolZoneBySchoolName(String schoolName) throws SchoolException {
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		int schoolZone=0;
		
		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_school_zone_by_name(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2,schoolName);
			stat.execute();

			
			rs = (ResultSet) stat.getObject(1);

			while (rs.next()) {
				schoolZone=rs.getInt("zone_id");
			}
		}
		catch (SQLException e) {
			System.err.println("SchoolDB.get_school_zone_by_name: " + e);
			throw new SchoolException("Can not extract school zone from DB: " + e);
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
		return schoolZone;
	}
	public static ArrayList<SchoolStreamSelectListBean> getSchoolsByRegionZone(School s) throws RegionException {
		ArrayList<SchoolStreamSelectListBean>  tm = new ArrayList<SchoolStreamSelectListBean> ();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_schools_by_region_zone(?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, s.getSchoolID());
			stat.setInt(3, s.getZone().getZoneId());
			stat.setInt(4, s.getRegion().getId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while(rs.next())
			{
				SchoolStreamSelectListBean bean = new SchoolStreamSelectListBean();
				bean.setSchoolId(rs.getInt("school_id"));
				bean.setSchoolName(rs.getString("school_name"));
				tm.add(bean);
				
			}
		}
		catch (SQLException e) {
			System.err.println("TreeMap<String,Integer> getSchoolsByRegionZone(School s) " + e);
			throw new RegionException("Can not extract Schools from DB.", e);
		} catch (SchoolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

		return tm;
	}
	public static ArrayList<SchoolStreamSelectListBean> getSchoolsByZone(School s) throws RegionException {
		ArrayList<SchoolStreamSelectListBean> tm = new ArrayList<SchoolStreamSelectListBean>();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_schools_by_zone(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, s.getSchoolID());
			stat.setInt(3, s.getZone().getZoneId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while(rs.next())
			{
				SchoolStreamSelectListBean bean = new SchoolStreamSelectListBean();
				bean.setSchoolId(rs.getInt("school_id"));
				bean.setSchoolName(rs.getString("school_name"));
				tm.add(bean);
				
			}
		}
		catch (SQLException e) {
			System.err.println("TreeMap<String,Integer> getSchoolsByRegionZone(School s) " + e);
			throw new RegionException("Can not extract Schools from DB.", e);
		} catch (SchoolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

		return tm;
	}
	public static ArrayList<SchoolStreamSelectListBean> getSchoolsByRegionZone(Integer regionid,Integer zoneid, Integer schoolid) throws RegionException {
		ArrayList<SchoolStreamSelectListBean>  tm = new ArrayList<SchoolStreamSelectListBean> ();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_schools_by_region_zone(?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, schoolid);
			stat.setInt(3, zoneid);
			stat.setInt(4, regionid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while(rs.next())
			{
				SchoolStreamSelectListBean bean = new SchoolStreamSelectListBean();
				bean.setSchoolId(rs.getInt("school_id"));
				bean.setSchoolName(rs.getString("school_name"));
				tm.add(bean);
				
			}
		}
		catch (SQLException e) {
			System.err.println("TreeMap<String,Integer> getSchoolsByRegionZone(School s) " + e);
			throw new RegionException("Can not extract Schools from DB.", e);
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

		return tm;
	}
	public static ArrayList<SchoolStreamSelectListBean> getSchoolsByZone(Integer zoneid, Integer schoolid) throws RegionException {
		ArrayList<SchoolStreamSelectListBean> tm = new ArrayList<SchoolStreamSelectListBean>();
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_schools_by_zone(?,?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, schoolid);
			stat.setInt(3, zoneid);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while(rs.next())
			{
				SchoolStreamSelectListBean bean = new SchoolStreamSelectListBean();
				bean.setSchoolId(rs.getInt("school_id"));
				bean.setSchoolName(rs.getString("school_name"));
				tm.add(bean);
				
			}
		}
		catch (SQLException e) {
			System.err.println("ArrayList<SchoolStreamSelectListBean> getSchoolsByZone(Integer zoneid, Integer schoolid " + e);
			throw new RegionException("Can not extract Schools from DB.", e);
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

		return tm;
	}
	public static Vector<School> getSchoolsOffices() throws SchoolException {

		Vector<School> schools = null;
		School s = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			schools = new Vector<School>(126);
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.schools_pkg.get_schools_4; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next()) {
				do {
					s = createSchoolBean(rs);
					schools.add(s);

					if (!rs.isAfterLast() && (rs.getInt("SCHOOL_ID") == s.getSchoolID()))
						
						rs.next();
						
				} while (!rs.isAfterLast());
			}
		}
		catch (SQLException e) {
			System.err.println("SchoolDB.getSchoolsOffices(): " + e);
			throw new SchoolException("Can not extract schools from DB: " + e);
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
		return schools;
	}
	public static String getLocationText(int schoolid) {

		String txt = null;

		switch (schoolid) {
		case -3000:
			txt = "Central Regional Office";
			break;
		case -2000:
			txt = "Western Regional Office";
			break;
		case -1000:
			txt = "Labrador Regional Office";
			break;
		case -999:
			txt = "District Office";
			break;
		case -998:
			txt = "Avalon Regional Office";
			break;
		case -100:
			txt = "Avalon East Region";
			break;
		case -200:
			txt = "Avalon West Region";
			break;
		case -300:
			txt = "Burin Region";
			break;
		case -400:
			txt = "Vista Region";
			break;
		default:
			try {
				txt = SchoolDB.getSchool(schoolid).getSchoolName();
			} catch (SchoolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		}

		return txt;
	}
	
	 public static ArrayList<School> getSchoolsAdminAll() {

		 

	        ArrayList<School> alpha = new ArrayList<School>();
	        School eBean = null;
	        Connection con = null;
	        CallableStatement stat = null;
	        ResultSet rs = null;

	 

	        try {
	            con = DAOUtils.getConnection();
	           stat = con.prepareCall("begin ? := SCHOOLS_PKG.get_schools_3; end;");
	            //stat = con.prepareCall("begin ? := TESTING_PKG.get_schools_333; end;");
	            
	            stat.registerOutParameter(1, OracleTypes.CURSOR);
	            stat.execute();
	            rs = ((OracleCallableStatement) stat).getCursor(1);
	            if (rs.next()) {
	                do {
	                    eBean = createSchoolBean(rs);
	                    alpha.add(eBean);    
	                    if (!rs.isAfterLast() && (rs.getInt("SCHOOL_ID") == eBean.getSchoolID()))
	                        rs.next();
	    
	                } while (!rs.isAfterLast());
	            }
	            
	        }
	        catch (SQLException e) {
	            System.err.println("static ArrayList<School> getSchoolsAdminAll(): " + e);
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

	 

	        return alpha;
	    }
	
	
	
}