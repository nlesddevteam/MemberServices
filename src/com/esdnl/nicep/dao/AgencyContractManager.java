package com.esdnl.nicep.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.esdnl.dao.DAOUtils;
import com.esdnl.nicep.beans.AgencyContractBean;
import com.esdnl.nicep.beans.AgencyDemographicsBean;
import com.esdnl.nicep.beans.NICEPException;
import com.esdnl.nicep.constants.AgencyContractType;

public class AgencyContractManager {

	public static int addAgencyContractBean(AgencyContractBean abean) throws NICEPException {

		Connection con = null;
		CallableStatement stat = null;
		int id = 0;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin ? := awsd_user.nicep.add_agency_contract(?,?,?,?,?,?); end;");
			stat.registerOutParameter(1, OracleTypes.NUMBER);
			stat.setInt(2, abean.getAgencyId());
			stat.setDate(3, new java.sql.Date(abean.getEffectiveDate().getTime()));
			stat.setDate(4, new java.sql.Date(abean.getEndDate().getTime()));
			stat.setString(5, abean.getFilename());
			stat.setInt(6, abean.getContractType().getValue());
			stat.setDouble(7, abean.getContractTypeValue());

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

	public static boolean updateAgencyContractBean(AgencyContractBean abean) throws NICEPException {

		Connection con = null;
		CallableStatement stat = null;
		boolean ok = false;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.nicep.update_agency_contract(?,?,?,?,?,?); end;");
			stat.setInt(1, abean.getContractId());
			stat.setDate(2, new java.sql.Date(abean.getEffectiveDate().getTime()));
			stat.setDate(3, new java.sql.Date(abean.getEndDate().getTime()));
			stat.setString(4, abean.getFilename());
			stat.setInt(5, abean.getContractType().getValue());
			stat.setDouble(6, abean.getContractTypeValue());

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

	public static boolean deleteAgencyContractBean(int id) throws NICEPException {

		Connection con = null;
		CallableStatement stat = null;
		boolean ok = false;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.nicep.delete_agency_contract(?); end;");
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

	public static AgencyContractBean getAgencyContractBean(int id) throws NICEPException {

		AgencyContractBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.nicep.get_agency_contract(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				eBean = createAgencyContractBean(rs);
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

	public static AgencyContractBean[] getAgencyCurrentContractBean(AgencyDemographicsBean agency) throws NICEPException {

		Vector v_opps = null;
		AgencyContractBean eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.nicep.get_agency_current_contract(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, agency.getAgencyId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				v_opps.add(createAgencyContractBean(rs));
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

		return (AgencyContractBean[]) v_opps.toArray(new AgencyContractBean[0]);
	}

	public static AgencyContractBean createAgencyContractBean(ResultSet rs) {

		AgencyContractBean abean = null;

		try {
			abean = new AgencyContractBean();

			abean.setAgencyId(rs.getInt("AGENCY_ID"));
			abean.setContractId(rs.getInt("CONTRACT_ID"));
			abean.setContractType(AgencyContractType.get(rs.getInt("CONTRACT_TYPE")));
			abean.setContractTypeValue(rs.getDouble("CONTRACT_TYPE_VALUE"));
			abean.setEffectiveDate(rs.getDate("EFFECTIVE_DATE"));
			abean.setEndDate(rs.getDate("END_DATE"));
			abean.setFilename(rs.getString("CONTRACT_FILE"));
		}
		catch (SQLException e) {
			e.printStackTrace();
			abean = null;
		}

		return abean;
	}
}