package com.awsd.travel.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelDB;
import com.awsd.travel.TravelClaimException;
import com.awsd.travel.bean.DivisionTravelBudget;
import com.awsd.travel.bean.TravelBudget;
import com.esdnl.dao.DAOUtils;

public class TravelBudgetService {

	public static void addTravelBudget(TravelBudget budget) throws TravelClaimException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.travel_claim_sys.add_travel_budget(?, ?, ?); end;");

			stat.setInt(1, budget.getPersonnel().getPersonnelID());
			stat.setString(2, budget.getFiscalYear());
			stat.setDouble(3, budget.getAmount());

			stat.execute();
		}
		catch (SQLException e) {
			System.err.println("void addTravelBudget(TravelBudget budget): " + e);
			throw new TravelClaimException("Can not add TravelBudget status to DB: " + e);
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

	public static void deleteTravelBudget(int id) throws TravelClaimException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.travel_claim_sys.delete_travel_budget(?); end;");

			stat.setInt(1, id);

			stat.execute();
		}
		catch (SQLException e) {
			System.err.println("void deleteTravelBudget(int id): " + e);
			throw new TravelClaimException("Can not delete TravelBudget from DB: " + e);
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

	public static void updateTravelBudget(TravelBudget budget) throws TravelClaimException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.travel_claim_sys.update_travel_budget(?, ?, ?, ?); end;");

			stat.setInt(1, budget.getPersonnel().getPersonnelID());
			stat.setString(2, budget.getFiscalYear());
			stat.setDouble(3, budget.getAmount());
			stat.setInt(4, budget.getBudgetId());

			stat.execute();
		}
		catch (SQLException e) {
			System.err.println("void updateTravelBudget(TravelBudget budget): " + e);
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

	}

	public static void surrenderTravelBudgets(int division_id, String school_year) throws TravelClaimException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.travel_claim_sys.surrender_travel_budgets(?, ?); end;");

			stat.setInt(1, division_id);
			stat.setString(2, school_year);

			stat.execute();
		}
		catch (SQLException e) {
			System.err.println("void surrenderTravelBudgets(int division_id, String school_year): " + e);
			throw new TravelClaimException("Cannot surrender TravelBudgets in DB: " + e);
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

	public static void autoAdjustTravelBudgets(int division_id, String school_year) throws TravelClaimException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.travel_claim_sys.auto_adjust_travel_budgets(?, ?); end;");

			stat.setInt(1, division_id);
			stat.setString(2, school_year);

			stat.execute();
		}
		catch (SQLException e) {
			System.err.println("void autoAdjustTravelBudgets(int division_id, String school_year): " + e);
			throw new TravelClaimException("Cannot auto adjust TravelBudgets in DB: " + e);
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

	public static void rolloverTravelBudgets(int division_id, String school_year, String forward_school_year)
			throws TravelClaimException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.travel_claim_sys.rollover_travel_budgets(?, ?, ?); end;");

			stat.setInt(1, division_id);
			stat.setString(2, school_year);
			stat.setString(3, forward_school_year);

			stat.execute();
		}
		catch (SQLException e) {
			System.err.println("void rolloverTravelBudgets(int division_id, String school_year): " + e);
			throw new TravelClaimException("Cannot rollover TravelBudget in DB: " + e);
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

	public static TravelBudget getTravelBudget(int budget_id) throws TravelClaimException {

		TravelBudget budget = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_travel_budget(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, budget_id);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				budget = createTravelBudget(rs);

		}
		catch (SQLException e) {
			System.err.println("TravelBudget getTravelBudget(int budget_id): " + e);
			throw new TravelClaimException("Can not extract TravelBudget from DB.");
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

		return budget;
	}

	public static Collection<TravelBudget> getTravelBudgets(String fy) throws TravelClaimException {

		ArrayList<TravelBudget> budgets = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			budgets = new ArrayList<TravelBudget>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_travel_budgets(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setString(2, fy);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				budgets.add(createTravelBudget(rs));

		}
		catch (SQLException e) {
			System.err.println("Collection<TravelBudget> getTravelBudgets: " + e);
			throw new TravelClaimException("Can not extract TravelBudget from DB.");
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

		return budgets;
	}

	public static Collection<TravelBudget> getTravelBudgets(Personnel p) throws TravelClaimException {

		ArrayList<TravelBudget> budgets = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			budgets = new ArrayList<TravelBudget>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_travel_budgets(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, p.getPersonnelID());
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				budgets.add(createTravelBudget(rs));

		}
		catch (SQLException e) {
			System.err.println("Collection<TravelBudget> getTravelBudgets: " + e);
			throw new TravelClaimException("Can not extract TravelBudget from DB.");
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

		return budgets;
	}

	public static TravelBudget getTravelBudget(Personnel p, String fy) throws TravelClaimException {

		TravelBudget budget = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_travel_budget(?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, p.getPersonnelID());
			stat.setString(3, fy);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next())
				budget = createTravelBudget(rs);

		}
		catch (SQLException e) {
			System.err.println("Collection<TravelBudget> getTravelBudgets(Personnel p): " + e);
			throw new TravelClaimException("Can not extract TravelBudget from DB.");
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

		return budget;
	}

	public static Collection<TravelBudget> getTravelBudgetsByAssistantDirector(Personnel p, String fiscal_year)
			throws TravelClaimException {

		ArrayList<TravelBudget> budgets = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			budgets = new ArrayList<TravelBudget>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_ad_travel_budgets(?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, p.getPersonnelID());
			stat.setString(3, fiscal_year);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				budgets.add(createTravelBudget(rs));

		}
		catch (SQLException e) {
			System.err.println("Collection<TravelBudget> getTravelBudgetsByAssistantDirector(Personnel p, String fiscal_year): "
					+ e);
			throw new TravelClaimException("Can not extract TravelBudget from DB.");
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

		return budgets;
	}

	public static Collection<TravelBudget> getTravelBudgetsBySupervisor(Personnel p, String fiscal_year)
			throws TravelClaimException {

		ArrayList<TravelBudget> budgets = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {

			budgets = new ArrayList<TravelBudget>(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_supervisor_travel_budgets(?, ?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, p.getPersonnelID());
			stat.setString(3, fiscal_year);
			stat.execute();

			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				budgets.add(createTravelBudget(rs));

		}
		catch (SQLException e) {
			System.err.println("Collection<TravelBudget> getTravelBudgetsByAssistantDirector(Personnel p, String fiscal_year): "
					+ e);
			throw new TravelClaimException("Can not extract TravelBudget from DB.");
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

		return budgets;
	}

	public static TravelBudget createTravelBudget(ResultSet rs) throws TravelClaimException {

		TravelBudget abean = null;

		try {
			abean = new TravelBudget();

			abean.setBudgetId(rs.getInt("BUDGET_ID"));
			abean.setPersonnel(PersonnelDB.createPersonnelBean(rs));
			abean.setAmount(rs.getDouble("BUDGETED_AMOUNT"));
			abean.setFiscalYear(rs.getString("FISCAL_YEAR"));
			abean.setAmountClaimed(rs.getDouble("TOTAL_CLAIMED"));
			abean.setAmountPreclaimed(rs.getDouble("TOTAL_PRECLAIMED"));
			abean.setDivision(DivisionService.getDivision(rs.getInt("DIVISION_ID")));

			abean.getDivision().setBudget(TravelBudgetService.createDivisionTravelBudget(rs));
		}
		catch (SQLException e) {
			e.printStackTrace();
			abean = null;
		}

		return abean;
	}

	public static DivisionTravelBudget createDivisionTravelBudget(ResultSet rs) throws TravelClaimException {

		DivisionTravelBudget abean = null;

		try {
			abean = new DivisionTravelBudget();

			abean.setDivisionId(rs.getInt("DIVISION_ID"));
			abean.setSchoolYear(rs.getString("FISCAL_YEAR"));
			abean.setBudget(rs.getDouble("DIVISION_AMOUNT"));

		}
		catch (SQLException e) {
			e.printStackTrace();
			abean = null;
		}

		return abean;
	}

}
