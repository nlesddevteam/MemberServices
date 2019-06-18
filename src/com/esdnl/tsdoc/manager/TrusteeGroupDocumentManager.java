package com.esdnl.tsdoc.manager;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.esdnl.dao.DAOUtils;
import com.esdnl.tsdoc.bean.TSDocException;
import com.esdnl.tsdoc.bean.TrusteeDocumentBean;
import com.esdnl.tsdoc.bean.TrusteeGroupBean;

public class TrusteeGroupDocumentManager {

	public static void addTrusteeGroupDocumentBean(TrusteeGroupBean group, TrusteeDocumentBean doc) throws TSDocException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.tsdoc_pkg.add_group_doc(?,?); end;");

			stat.setInt(1, group.getGroupId());
			stat.setInt(2, doc.getDocumentId());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addTrusteeGroupDocumentBean(TrusteeGroupBean group, TrusteeDocumentBean doc): " + e);
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
