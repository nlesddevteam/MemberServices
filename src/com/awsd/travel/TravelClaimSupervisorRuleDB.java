package com.awsd.travel;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.travel.constant.KeyType;
import com.awsd.travel.service.DivisionService;
import com.esdnl.dao.DAOUtils;

public class TravelClaimSupervisorRuleDB {

	public static TravelClaimSupervisorRule getTravelClaimSupervisorRule(int rule_id) throws TravelClaimException {

		TravelClaimSupervisorRule eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_supervisor_rule(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, rule_id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				eBean = createTravelClaimSupervisorRule(rs);
		}
		catch (SQLException e) {
			System.err.println("TravelClaimSupervisorRule getTravelClaimSupervisorRule(int rule_id): " + e);
			throw new TravelClaimException("Can not extract TravelClaimSupervisorRule from DB.");
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

	public static TravelClaimSupervisorRule[] getTravelClaimSupervisorRules() throws TravelClaimException {

		Vector<TravelClaimSupervisorRule> v_opps = null;
		TravelClaimSupervisorRule eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<TravelClaimSupervisorRule>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_supervisor_rules; end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);

			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createTravelClaimSupervisorRule(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("TravelClaimSupervisorRule[] getTravelClaimSupervisorRules(TravelClaimSupervisorRule rule): "
					+ e);
			throw new TravelClaimException("Can not extract AssignmentSubjectBean from DB.");
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

		return (TravelClaimSupervisorRule[]) v_opps.toArray(new TravelClaimSupervisorRule[0]);
	}

	public static TravelClaimSupervisorRule[] getTravelClaimSupervisorRules(Personnel p) throws TravelClaimException {

		Vector<TravelClaimSupervisorRule> v_opps = null;
		TravelClaimSupervisorRule eBean = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<TravelClaimSupervisorRule>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_supervisor_rules(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, p.getPersonnelID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				eBean = createTravelClaimSupervisorRule(rs);

				v_opps.add(eBean);
			}
		}
		catch (SQLException e) {
			System.err.println("TravelClaimSupervisorRule[] getTravelClaimSupervisorRules(TravelClaimSupervisorRule rule): "
					+ e);
			throw new TravelClaimException("Can not extract AssignmentSubjectBean from DB.");
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

		return (TravelClaimSupervisorRule[]) v_opps.toArray(new TravelClaimSupervisorRule[0]);
	}

	public static Personnel[] getTravelClaimSupervisors(Personnel emp) throws TravelClaimException {

		Vector<Personnel> v_opps = null;
		Personnel p = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			v_opps = new Vector<Personnel>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_claim_supervisor(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, Integer.toString(emp.getPersonnelID()));
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				p = PersonnelDB.createPersonnelBean(rs);

				v_opps.add(p);
			}
		}
		catch (SQLException e) {
			System.err.println("Personnel[] getTravelClaimSupervisor(Personnel emp): " + e);
			throw new TravelClaimException("Can not extract Personnel[] from DB.");
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

		return (Personnel[]) v_opps.toArray(new Personnel[0]);
	}

	public static int addSupervisorRule(TravelClaimSupervisorRule rule) throws TravelClaimException {

		Connection con = null;
		CallableStatement stat = null;
		int cid = -1;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.travel_claim_sys.add_supervisor_rule(?, ?, ?, ?, ?); end;");

			stat.setString(1, rule.getSupervisorKey());
			stat.setInt(2, rule.getSupervisorKeyType().getValue());
			stat.setString(3, rule.getEmployeeKey());
			stat.setInt(4, rule.getEmployeeKeyType().getValue());
			if (rule.getDivision() != null)
				stat.setInt(5, rule.getDivision().getId());
			else
				stat.setNull(5, OracleTypes.NUMBER);

			stat.execute();
		}
		catch (SQLException e) {
			System.err.println("TravelClaimDB.addClaim(): " + e);
			throw new TravelClaimException("Can not add travel claim status to DB: " + e);
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
		return (cid);
	}

	public static void deleteSupervisorRule(int id) throws TravelClaimException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.travel_claim_sys.delete_supervisor_rule(?); end;");

			stat.setInt(1, id);

			stat.execute();
		}
		catch (SQLException e) {
			System.err.println("void deleteSupervisorRule(int id): " + e);
			throw new TravelClaimException("Can not delete SupervisorRule status to DB: " + e);
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

	public static int updateSupervisorRule(TravelClaimSupervisorRule rule) throws TravelClaimException {

		Connection con = null;
		CallableStatement stat = null;
		int cid = -1;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.travel_claim_sys.update_supervisor_rule(?, ?, ?, ?, ?, ?); end;");

			stat.setString(1, rule.getSupervisorKey());
			stat.setInt(2, rule.getSupervisorKeyType().getValue());
			stat.setString(3, rule.getEmployeeKey());
			stat.setInt(4, rule.getEmployeeKeyType().getValue());
			if (rule.getDivision() != null)
				stat.setInt(5, rule.getDivision().getId());
			else
				stat.setNull(5, OracleTypes.NUMBER);
			stat.setInt(6, rule.getRuleId());

			stat.execute();
		}
		catch (SQLException e) {
			System.err.println("updateSupervisorRule(TravelClaimSupervisorRule rule): " + e);
			throw new TravelClaimException("Can not update TravelClaimSupervisorRule to DB: " + e);
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
		return (cid);
	}

	public static TravelClaimSupervisorRule createTravelClaimSupervisorRule(ResultSet rs) {

		TravelClaimSupervisorRule abean = null;

		try {
			abean = new TravelClaimSupervisorRule();

			abean.setRuleId(rs.getInt("RULE_ID"));
			abean.setEmployeeKey(rs.getString("EMPLOYEE_KEY"));
			abean.setEmployeeKeyType(KeyType.get(rs.getInt("EMPLOYEE_KEYTYPE")));
			abean.setSupervisorKey(rs.getString("SUPERVISOR_KEY"));
			abean.setSupervisorKeyType(KeyType.get(rs.getInt("SUPERVISOR_KEYTYPE")));
			abean.setDivision(DivisionService.createDivision(rs));
		}
		catch (SQLException e) {
			e.printStackTrace();
			abean = null;
		}

		return abean;
	}

}
