package com.awsd.travel;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import com.awsd.personnel.Personnel;
import com.awsd.personnel.PersonnelException;
import com.esdnl.dao.DAOUtils;

public class TravelClaimItemsDB {

	public static Vector getClaimItems(TravelClaim claim) throws TravelClaimException {

		Vector items = null;
		TravelClaimItem item = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			items = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_claim_items(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, claim.getClaimID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				item = new TravelClaimItem(rs.getInt("item_id"), rs.getDate("item_date"), rs.getString("item_desc"), rs.getInt("item_kms"), rs.getDouble("item_meals"), rs.getDouble("item_lodging"), rs.getDouble("item_other"), rs.getDouble("per_km_rate"), rs.getString("item_departure_time"), rs.getString("item_return_time"));

				items.add(item);
			}
		}
		catch (SQLException e) {
			System.err.println("TravelClaimItemDB.getClaimItems(): " + e);
			throw new TravelClaimException("Can not extract travel claim items from DB: " + e);
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
		return items;
	}

	public static TravelClaimItem getClaimItem(int id) throws TravelClaimException {

		TravelClaimItem item = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_claim_item_by_id(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, id);
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			if (rs.next()) {
				item = new TravelClaimItem(rs.getInt("item_id"), rs.getDate("item_date"), rs.getString("item_desc"), rs.getInt("item_kms"), rs.getDouble("item_meals"), rs.getDouble("item_lodging"), rs.getDouble("item_other"), rs.getDouble("per_km_rate"), rs.getString("item_departure_time"), rs.getString("item_return_time"));
			}
			else {
				item = null;
			}
		}
		catch (SQLException e) {
			System.err.println("TravelClaimItemDB.getClaimItem(int): " + e);
			throw new TravelClaimException("Can not extract travel claim item from DB: " + e);
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
		return item;
	}

	public static boolean addClaimItem(TravelClaim claim, TravelClaimItem item)
			throws TravelClaimException,
				PersonnelException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.travel_claim_sys.insert_claim_item(?, ?, ?, ?, ?, ?, ?, ?, ?, ?); end;");
			stat.setInt(1, claim.getPersonnel().getPersonnelID());
			stat.setInt(2, claim.getClaimID());
			stat.setDate(3, new Date(item.getItemDate().getTime()));
			stat.setString(4, item.getItemDescription());
			stat.setInt(5, item.getItemKMS());
			stat.setDouble(6, item.getItemMeals());
			stat.setDouble(7, item.getItemLodging());
			stat.setDouble(8, item.getItemOther());
			stat.setString(9, item.getDepartureTime());
			stat.setString(10, item.getReturnTime());
			stat.execute();
			check = true;
		}
		catch (SQLException e) {
			check = false;
			System.err.println("TravelClaimItemsDB.addClaimItem(TravelClaim, TravelClaimItem): " + e);
			throw new TravelClaimException("Can not add travel claim item to DB: " + e);
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
		return (check);
	}

	public static boolean deleteClaimItem(int item_id) throws TravelClaimException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.travel_claim_sys.delete_claim_item(?); end;");
			stat.setInt(1, item_id);
			stat.execute();
			check = true;
		}
		catch (SQLException e) {
			check = false;
			System.err.println("TravelClaimItemsDB.deleteClaimItem(int): " + e);
			throw new TravelClaimException("Can not delete travel claim item from DB: " + e);
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
		return (check);
	}

	public static boolean editClaimItem(TravelClaim claim, TravelClaimItem old_item, TravelClaimItem new_item)
			throws TravelClaimException,
				PersonnelException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(false);

			stat = con.prepareCall("begin awsd_user.travel_claim_sys.delete_claim_item(?); end;");
			stat.setInt(1, old_item.getItemID());
			stat.execute();
			stat.close();

			stat = con.prepareCall("begin awsd_user.travel_claim_sys.insert_claim_item(?, ?, ?, ?, ?, ?, ?, ?, ?, ?); end;");
			stat.setInt(1, claim.getPersonnel().getPersonnelID());
			stat.setInt(2, claim.getClaimID());
			stat.setDate(3, new Date(new_item.getItemDate().getTime()));
			stat.setString(4, new_item.getItemDescription());
			stat.setInt(5, new_item.getItemKMS());
			stat.setDouble(6, new_item.getItemMeals());
			stat.setDouble(7, new_item.getItemLodging());
			stat.setDouble(8, new_item.getItemOther());
			stat.setString(9, new_item.getDepartureTime());
			stat.setString(10, new_item.getReturnTime());
			stat.execute();
			stat.close();

			check = true;
		}
		catch (SQLException e) {
			check = false;
			System.err.println("TravelClaimItemsDB.deleteClaimItem(int): " + e);
			try {
				con.rollback();
			}
			catch (Exception ee) {
				System.err.println("<<<< COULD NOT ROLLBACK TRAVEL CLAIM ITEM EDIT >>>>");
			}
			throw new TravelClaimException("Can not delete travel claim item from DB: " + e);
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
		return (check);
	}

	public static boolean updateClaimItem(TravelClaim claim, Personnel who, TravelClaimItem item)
			throws TravelClaimException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.travel_claim_sys.update_claim_item(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?); end;");
			stat.setInt(1, who.getPersonnelID());
			stat.setInt(2, claim.getClaimID());
			stat.setInt(3, item.getItemID());
			stat.setDate(4, new java.sql.Date(item.getItemDate().getTime()));
			stat.setString(5, item.getItemDescription());
			stat.setInt(6, item.getItemKMS());
			stat.setDouble(7, item.getItemMeals());
			stat.setDouble(8, item.getItemLodging());
			stat.setDouble(9, item.getItemOther());
			stat.setString(10, item.getDepartureTime());
			stat.setString(11, item.getReturnTime());

			stat.execute();

			check = true;
		}
		catch (SQLException e) {
			check = false;
			System.err.println("TravelClaimItemsDB.updateClaimItem(TravelClaimItem): " + e);
			throw new TravelClaimException("Can not update travel claim item from DB: " + e);
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
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
		return (check);
	}
}