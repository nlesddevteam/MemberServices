package com.esdnl.tsdoc.manager;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.esdnl.dao.DAOUtils;
import com.esdnl.tsdoc.bean.TSDocException;
import com.esdnl.tsdoc.bean.TrusteeGroupBean;
import com.esdnl.tsdoc.bean.TrusteeNoteBean;

public class TrusteeGroupNoteManager {

	public static void addTrusteeGroupNoteBean(TrusteeGroupBean group, TrusteeNoteBean note) throws TSDocException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.tsdoc_pkg.add_group_note(?,?); end;");

			stat.setInt(1, group.getGroupId());
			stat.setInt(2, note.getNoteId());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addTrusteeGroupDocumentBean(TrusteeGroupBean group, TrusteeNoteBean note): " + e);
			throw new TSDocException("Can not add TrusteeGroupDocumentBean to DB.", e);
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

}
