package com.esdnl.nicep.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.nicep.beans.AgencyDemographicsBean;
import com.esdnl.nicep.beans.NICEPException;

public class AgencyDemographicsManager {

	public static int addAgencyDemographicsBean(AgencyDemographicsBean abean) throws NICEPException {

		Connection con = null;
		CallableStatement stat = null;
		int id = 0;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin ? := awsd_user.nicep.add_agency_demo(?,?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setString(2, abean.getName());
			stat.setString(3, abean.getAddress1());
			stat.setString(4, abean.getAddress2());
			stat.setString(5, abean.getCityTown());
			stat.setString(6, abean.getProvinceState());
			stat.setString(7, abean.getCountry());
			stat.setString(8, abean.getZipcode());

			stat.execute();

			id = ((OracleCallableStatement) stat).getInt(1);
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addAgencyDemographicsBean(AgencyDemographicsBean abean): " + e);
			throw new NICEPException("Can not add AgencyDemographicsBean to DB.", e);
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

	public static boolean updateAgencyDemographicsBean(AgencyDemographicsBean abean) throws NICEPException {

		Connection con = null;
		CallableStatement stat = null;
		boolean ok = false;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.nicep.update_agency_demo(?,?,?,?,?,?,?,?); end;");
			stat.setInt(1, abean.getAgencyId());
			stat.setString(2, abean.getName());
			stat.setString(3, abean.getAddress1());
			stat.setString(4, abean.getAddress2());
			stat.setString(5, abean.getCityTown());
			stat.setString(6, abean.getProvinceState());
			stat.setString(7, abean.getCountry());
			stat.setString(8, abean.getZipcode());

			stat.execute();

			ok = true;
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addAgencyDemographicsBean(AgencyDemographicsBean abean): " + e);
			throw new NICEPException("Can not add AgencyDemographicsBean to DB.", e);
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

	public static boolean deleteAgencyDemographicsBean(int id) throws NICEPException {

		Connection con = null;
		CallableStatement stat = null;
		boolean ok = false;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.nicep.delete_agency_demo(?); end;");
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

			System.err.println("void addAgencyDemographicsBean(AgencyDemographicsBean abean): " + e);
			throw new NICEPException("Can not add AgencyDemographicsBean to DB.", e);
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

	public static AgencyDemographicsBean[] getAgencyDemographicsBeans() throws NICEPException {

		Vector v_opps = null;
		AgencyDemographicsBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.nicep.get_agency_demo; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createAgencyDemographicsBean(rs);

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

		return (AgencyDemographicsBean[]) v_opps.toArray(new AgencyDemographicsBean[0]);
	}

	public static AgencyDemographicsBean getAgencyDemographicsBeans(int id) throws NICEPException {

		AgencyDemographicsBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.nicep.get_agency_demo(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				eBean = createAgencyDemographicsBean(rs);
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

	public static AgencyDemographicsBean createAgencyDemographicsBean(ResultSet rs) {

		AgencyDemographicsBean abean = null;

		try {
			abean = new AgencyDemographicsBean();

			abean.setAgencyId(rs.getInt("AGENCY_ID"));
			abean.setAddress1(rs.getString("ADDRESS1"));
			abean.setAddress2(rs.getString("ADDRESS2"));
			abean.setCityTown(rs.getString("CITY_TOWN"));
			abean.setCountry(rs.getString("COUNTRY"));
			abean.setName(rs.getString("NAME"));
			abean.setProvinceState(rs.getString("PROVINCE_STATE"));
			abean.setZipcode(rs.getString("ZIPCODE"));
		}
		catch (SQLException e) {
			e.printStackTrace();
			abean = null;
		}

		return abean;
	}
}