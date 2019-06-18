package com.esdnl.nicep.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.nicep.beans.AgencyContactBean;
import com.esdnl.nicep.beans.AgencyDemographicsBean;
import com.esdnl.nicep.beans.NICEPException;

public class AgencyContactManager {

	public static int addAgencyContactBean(AgencyContactBean abean) throws NICEPException {

		Connection con = null;
		CallableStatement stat = null;
		int id = 0;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin ? := awsd_user.nicep.add_agency_contact(?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, abean.getAgencyId());
			stat.setString(3, abean.getName());
			stat.setString(4, abean.getPhone1());
			stat.setString(5, abean.getPhone2());
			stat.setString(6, abean.getPhone3());
			stat.setString(7, abean.getEmail());

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

	public static boolean updateAgencyContactBean(AgencyContactBean abean) throws NICEPException {

		Connection con = null;
		CallableStatement stat = null;
		boolean ok = false;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.nicep.update_agency_contact(?,?,?,?,?,?); end;");
			stat.setInt(1, abean.getContactId());
			stat.setString(2, abean.getName());
			stat.setString(3, abean.getPhone1());
			stat.setString(4, abean.getPhone2());
			stat.setString(5, abean.getPhone3());
			stat.setString(6, abean.getEmail());

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

	public static boolean deleteAgencyContactBean(int id) throws NICEPException {

		Connection con = null;
		CallableStatement stat = null;
		boolean ok = false;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.nicep.delete_agency_contact(?); end;");
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

	public static AgencyContactBean[] getAgencyContactBeans(AgencyDemographicsBean agency) throws NICEPException {

		Vector v_opps = null;
		AgencyContactBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.nicep.get_agency_contacts(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, agency.getAgencyId());

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createAgencyContactBean(rs);

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

		return (AgencyContactBean[]) v_opps.toArray(new AgencyContactBean[0]);
	}

	public static AgencyContactBean getAgencyContactBean(int id) throws NICEPException {

		AgencyContactBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.nicep.get_agency_contact(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				eBean = createAgencyContactBean(rs);
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

	public static AgencyContactBean createAgencyContactBean(ResultSet rs) {

		AgencyContactBean abean = null;

		try {
			abean = new AgencyContactBean();

			abean.setAgencyId(rs.getInt("AGENCY_ID"));
			abean.setContactId(rs.getInt("CONTACT_ID"));
			abean.setEmail(rs.getString("EMAIL"));
			abean.setName(rs.getString("NAME"));
			abean.setPhone1(rs.getString("PHONE1"));
			abean.setPhone2(rs.getString("PHONE2"));
			abean.setPhone3(rs.getString("PHONE3"));
		}
		catch (SQLException e) {
			e.printStackTrace();
			abean = null;
		}

		return abean;
	}
}