package com.esdnl.personnel.v2.database.sds;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import com.esdnl.dao.DAOUtils;
import com.esdnl.personnel.jobs.bean.ApplicantProfileBean;
import com.esdnl.personnel.v2.database.availability.EmployeeAvailabilityManager;
import com.esdnl.personnel.v2.model.sds.bean.EmployeeBean;
import com.esdnl.personnel.v2.model.sds.bean.EmployeeException;
import com.esdnl.personnel.v2.model.sds.bean.EmployeePositionBean;
import com.esdnl.personnel.v2.model.sds.bean.EmployeePositionBean.PositionType;
import com.esdnl.personnel.v2.model.sds.bean.EmployeeSeniorityBean;
import com.esdnl.personnel.v2.model.sds.constant.LocationConstant;
import com.esdnl.personnel.v2.model.sds.constant.PositionConstant;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class EmployeeManager {

	public static EmployeeBean getEmployeeBean(String emp_id) throws EmployeeException {

		EmployeeBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.sds_hr.get_emp(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, emp_id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if (eBean == null || !StringUtils.equalsIgnoreCase(eBean.getEmpId(), rs.getString("EMP_ID"))) {
					eBean = createEmployeeBean(rs);
				}
				else {
					eBean.addSeniority(createEmployeeSeniorityBean(eBean, rs));
					eBean.addPosition(createEmployeePositionBean(eBean, rs));
				}
			}
		}
		catch (SQLException e) {
			System.err.println("EmployeeBean[] getEmployeeBeans(String sin): " + e);
			throw new EmployeeException("Can not extract EmployeeBean from DB.", e);
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

	public static EmployeeBean getEmployeeBeanBySIN(String sin) throws EmployeeException {

		EmployeeBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.sds_hr.get_emp_by_sin(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, sin);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if (eBean == null || !StringUtils.equalsIgnoreCase(eBean.getEmpId(), rs.getString("EMP_ID"))) {
					eBean = createEmployeeBean(rs);
				}
				else {
					eBean.addSeniority(createEmployeeSeniorityBean(eBean, rs));
					eBean.addPosition(createEmployeePositionBean(eBean, rs));
				}
			}
		}
		catch (SQLException e) {
			System.err.println("EmployeeBean getEmployeeBeanBySIN(String sin): " + e);
			throw new EmployeeException("Can not extract EmployeeBean from DB.", e);
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

	public static EmployeeBean getEmployeeBeanByApplicantProfile(ApplicantProfileBean profile) throws EmployeeException {

		EmployeeBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.sds_hr.get_emp_by_appid(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, profile.getUID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if (eBean == null || !StringUtils.equalsIgnoreCase(eBean.getEmpId(), rs.getString("EMP_ID"))) {
					eBean = createEmployeeBean(rs);
				}
				else {
					eBean.addSeniority(createEmployeeSeniorityBean(eBean, rs));
					eBean.addPosition(createEmployeePositionBean(eBean, rs));
				}
			}
		}
		catch (SQLException e) {
			System.err.println("EmployeeBean getEmployeeBeanByApplicantProfile(ApplicantProfileBean profile): " + e);
			throw new EmployeeException("Can not extract EmployeeBean from DB.", e);
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

	public static EmployeeBean[] getEmployeeBeans(PositionConstant position, String school_year)
			throws EmployeeException {

		Map<String, EmployeeBean> v_opps = null;
		EmployeeBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new HashMap<>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.sds_hr.get_emps(?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, position.getId());
			stat.setString(3, school_year);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if (!v_opps.containsKey(rs.getString("EMP_ID"))) {
					eBean = createEmployeeBean(rs);
					v_opps.put(rs.getString("EMP_ID"), eBean);
				}
				else {
					v_opps.get(rs.getString("EMP_ID")).addSeniority(createEmployeeSeniorityBean(eBean, rs));
					v_opps.get(rs.getString("EMP_ID")).addPosition(createEmployeePositionBean(eBean, rs));
				}
			}
		}
		catch (SQLException e) {
			System.err.println("EmployeeBean[] getEmployeeBeans(String sin): " + e);
			throw new EmployeeException("Can not extract EmployeeBean from DB.", e);
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

		return (EmployeeBean[]) v_opps.values().toArray(new EmployeeBean[0]);
	}

	public static EmployeeBean[] getEmployeeBeans(PositionConstant position, String school_year, String group)
			throws EmployeeException {

		Map<String, EmployeeBean> v_opps = null;
		EmployeeBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new HashMap<>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.sds_hr.get_emps(?, ?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, position.getId());
			stat.setString(3, school_year);
			stat.setString(4, group);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if (!v_opps.containsKey(rs.getString("EMP_ID"))) {
					eBean = createEmployeeBean(rs);
					v_opps.put(rs.getString("EMP_ID"), eBean);
				}
				else {
					v_opps.get(rs.getString("EMP_ID")).addSeniority(createEmployeeSeniorityBean(eBean, rs));
					v_opps.get(rs.getString("EMP_ID")).addPosition(createEmployeePositionBean(eBean, rs));
				}
			}
		}
		catch (SQLException e) {
			System.err.println("EmployeeBean[] getEmployeeBeans(String sin): " + e);
			throw new EmployeeException("Can not extract EmployeeBean from DB.", e);
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

		return (EmployeeBean[]) v_opps.values().toArray(new EmployeeBean[0]);
	}

	public static EmployeeBean[] getEmployeeBeans(String school_year, String location) throws EmployeeException {

		Map<String, EmployeeBean> v_opps = null;
		EmployeeBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;
		boolean currentSchoolYear = StringUtils.equals(school_year,
				com.esdnl.personnel.v2.utils.StringUtils.getCurrentSchoolYear("yyyy", "yy", "-"));

		try {
			v_opps = new HashMap<>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.sds_hr.get_emps_by_loc(?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, school_year);
			stat.setString(3, location);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {

				if (currentSchoolYear
						&& ((rs.getDate("END_DATE") != null)
								&& (new java.util.Date(rs.getDate("END_DATE").getTime())).before(new Date()))
						&& StringUtils.equals(rs.getString("TENURE"), "TERM"))
					continue;

				if (!v_opps.containsKey(rs.getString("EMP_ID"))) {
					eBean = createEmployeeBean(rs);
					v_opps.put(rs.getString("EMP_ID"), eBean);
				}
				else {
					v_opps.get(rs.getString("EMP_ID")).addSeniority(createEmployeeSeniorityBean(eBean, rs));
					v_opps.get(rs.getString("EMP_ID")).addPosition(createEmployeePositionBean(eBean, rs));
				}
			}
		}
		catch (SQLException e) {
			System.err.println("EmployeeBean[] getEmployeeBeans(String sin): " + e);
			throw new EmployeeException("Can not extract EmployeeBean from DB.", e);
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

		return (EmployeeBean[]) v_opps.values().toArray(new EmployeeBean[0]);
	}

	public static EmployeeBean[] getEmployeeBeansBySenority(PositionConstant position, String school_year,
																													LocationConstant location)
			throws EmployeeException {

		Map<String, EmployeeBean> v_opps = null;
		EmployeeBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new HashMap<>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.sds_hr.get_emps_by_senority(?, ?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, position.getId());
			stat.setString(3, school_year);
			stat.setString(4, location.getId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				if (!v_opps.containsKey(rs.getString("EMP_ID"))) {
					eBean = createEmployeeBean(rs);
					v_opps.put(rs.getString("EMP_ID"), eBean);
				}
				else {
					v_opps.get(rs.getString("EMP_ID")).addSeniority(createEmployeeSeniorityBean(eBean, rs));
					v_opps.get(rs.getString("EMP_ID")).addPosition(createEmployeePositionBean(eBean, rs));
				}
			}
		}
		catch (SQLException e) {
			System.err.println("EmployeeBean[] getEmployeeBeans(String sin): " + e);
			throw new EmployeeException("Can not extract EmployeeBean from DB.", e);
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

		return (EmployeeBean[]) v_opps.values().toArray(new EmployeeBean[0]);
	}

	public static EmployeeBean[] getEmployeeBeansByAvailability(PositionConstant position, String school_year,
																															LocationConstant location)
			throws EmployeeException {

		Vector<EmployeeBean> v_opps = null;
		EmployeeBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<EmployeeBean>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.sds_hr.get_emps_by_availability(?, ?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, position.getId());
			stat.setString(3, school_year);
			stat.setString(4, location.getId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createEmployeeBean(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("EmployeeBean[] getEmployeeBeans(String sin): " + e);
			throw new EmployeeException("Can not extract EmployeeBean from DB.", e);
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

		return (EmployeeBean[]) v_opps.toArray(new EmployeeBean[0]);
	}

	public static EmployeeBean[] getEmployeeBeansByAvailability(PositionConstant position, String school_year,
																															LocationConstant location, java.util.Date viewDate)
			throws EmployeeException {

		Vector<EmployeeBean> v_opps = null;
		EmployeeBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<EmployeeBean>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.sds_hr.get_emps_by_availability_2(?, ?, ?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, position.getId());
			stat.setString(3, school_year);
			stat.setString(4, location.getId());
			stat.setTimestamp(5, new Timestamp(viewDate.getTime()));
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createEmployeeBean(rs);
				eBean.setCurrentAvailability(EmployeeAvailabilityManager.createEmployeeAvailabilityBean(rs));

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("EmployeeBean[] getEmployeeBeans(String sin): " + e);
			throw new EmployeeException("Can not extract EmployeeBean from DB.", e);
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

		return (EmployeeBean[]) v_opps.toArray(new EmployeeBean[0]);
	}

	public static EmployeeBean createEmployeeBean(ResultSet rs) {

		EmployeeBean abean = null;

		try {
			abean = new EmployeeBean();

			abean.setAddress1(rs.getString("ADDRESS1"));
			abean.setAddress2(rs.getString("ADDRESS2"));
			abean.setAlternatePhone(rs.getString("PHONE2"));
			if (rs.getDate("BOARD_START_DATE") != null)
				abean.setBoardStartDate(new java.util.Date(rs.getDate("BOARD_START_DATE").getTime()));
			abean.setCity(rs.getString("CITY"));
			abean.setEmail(rs.getString("EMAIL"));
			abean.setEmpId(rs.getString("EMP_ID"));
			abean.setFirstName(rs.getString("FIRSTNAME"));
			abean.setGender(rs.getString("GENDER"));
			abean.setLastName(rs.getString("LASTNAME"));
			abean.setPhone(rs.getString("PHONE"));
			abean.setPosition(PositionConstant.get(rs.getString("POSITION")));
			abean.setPositionDescription(rs.getString("POSITION"));
			abean.setPostalCode(rs.getString("POSTAL"));
			abean.setPreviousName(rs.getString("PREVIOUSNAME"));
			abean.setProvince(rs.getString("PROVINCE"));
			abean.setSchoolYear(rs.getString("SCHOOL_YR"));
			if (rs.getDate("SENIORITY_DATE") != null)
				abean.setSeniorityDate(new java.util.Date(rs.getDate("SENIORITY_DATE").getTime()));
			abean.setSIN(rs.getString("SIN"));
			abean.setStatus(rs.getString("STATUS"));
			abean.setTenurCode(rs.getInt("TENURE_CODE"));

			// location preference may not always be available
			try {
				abean.setLocationPreferences(rs.getString("EMP_USER_DEFINED_ALPHA"));
			}
			catch (SQLException e) {}

			//TENURE may not always be available
			try {
				abean.setTenur(rs.getString("TENURE"));
			}
			catch (SQLException e) {}

			//FTE may not always be available
			try {
				abean.setFTE(rs.getDouble("FTE_HRS"));
				abean.setPositionDescription(Double.toString(abean.getFTE()) + " " + abean.getPositionDescription());
			}
			catch (SQLException e) {}

			abean.addSeniority(createEmployeeSeniorityBean(abean, rs));
			abean.addPosition(createEmployeePositionBean(abean, rs));
		}
		catch (SQLException e) {
			e.printStackTrace();
			abean = null;
		}

		return abean;
	}

	public static EmployeeSeniorityBean createEmployeeSeniorityBean(EmployeeBean emp, ResultSet rs) {

		EmployeeSeniorityBean seniority = null;

		// seniority fields may not be available.
		try {

			seniority = new EmployeeSeniorityBean();
			seniority.setEmployee(emp);
			seniority.setUnionCode(rs.getString("UNION_CODE"));
			seniority.setSeniorityValue1(rs.getDouble("SENIORITY_NUMERIC"));
			seniority.setSeniorityValue2(rs.getDouble("SENIORITY_NUMERIC_2"));
			seniority.setSeniorityValue3(rs.getDouble("SENIORITY_NUMERIC_3"));
		}
		catch (SQLException e) {
			seniority = null;
		}

		return seniority;
	}

	public static EmployeePositionBean createEmployeePositionBean(EmployeeBean emp, ResultSet rs) {

		EmployeePositionBean position = null;

		try {
			position = new EmployeePositionBean();
			position.setEmployee(emp);
			position.setSchoolYear(rs.getString("SCHOOL_YR"));
			position.setName(rs.getString("NAME"));
			position.setEmpId(rs.getString("EMP_ID"));
			position.setPosition(rs.getString("POSITION"));
			position.setPositionType(PositionType.get(rs.getString("POSITION_TYPE")));

			if (rs.getDate("START_DATE") != null) {
				position.setStartDate(new java.util.Date(rs.getDate("START_DATE").getTime()));
			}
			else {
				position.setStartDate(null);
			}

			if (rs.getDate("END_DATE") != null) {
				position.setEndDate(new java.util.Date(rs.getDate("END_DATE").getTime()));
			}
			else {
				position.setEndDate(null);
			}

			position.setSin(rs.getString("SIN"));
			position.setLocation(rs.getString("LOCATION"));
			position.setTenure(rs.getString("TENURE"));
			position.setFteHours(rs.getDouble("FTE_HRS"));
		}
		catch (SQLException e) {
			position = null;
		}

		return position;
	}
}