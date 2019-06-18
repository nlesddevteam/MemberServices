package com.awsd.travel;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.personnel.PersonnelException;
import com.esdnl.dao.DAOUtils;

public class TravelClaimNoteDB {

	public static Vector getClaimNotes(TravelClaim claim) throws TravelClaimException {

		Vector notes = null;
		TravelClaimNote note = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			notes = new Vector(5);

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.travel_claim_sys.get_claim_notes(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, claim.getClaimID());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next()) {
				note = new TravelClaimNote(rs.getInt("note_id"), rs.getInt("personnel_id"), new Date(rs.getDate("note_date").getTime()), rs.getString("note"));

				notes.add(note);
			}
		}
		catch (SQLException e) {
			System.err.println("TravelClaimItemDB.getClaimNotes(): " + e);
			throw new TravelClaimException("Can not extract travel claim notes from DB: " + e);
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
		return notes;
	}

	public static boolean addClaimNote(TravelClaim claim, TravelClaimNote note)
			throws TravelClaimException,
				PersonnelException {

		Connection con = null;
		CallableStatement stat = null;
		boolean check = false;

		try {
			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin awsd_user.travel_claim_sys.insert_claim_note(?, ?, ?); end;");
			stat.setInt(1, note.getPersonnel().getPersonnelID());
			stat.setInt(2, claim.getClaimID());
			stat.setString(3, note.getNote());
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
}