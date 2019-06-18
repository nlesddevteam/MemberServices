package com.esdnl.tsdoc.manager;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import com.awsd.personnel.PersonnelDB;
import com.esdnl.dao.DAOUtils;
import com.esdnl.tsdoc.bean.AuditActionBean;
import com.esdnl.tsdoc.bean.TSDocException;
import com.esdnl.tsdoc.bean.TrusteeNoteAuditBean;
import com.esdnl.tsdoc.bean.TrusteeNoteBean;

public class TrusteeNoteAuditManager {

	public static void addTrusteeNoteAuditBean(TrusteeNoteAuditBean abean) throws TSDocException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.tsdoc_pkg.add_note_audit(?,?,?,?,?); end;");

			stat.setTimestamp(1, new java.sql.Timestamp(abean.getAuditDate().getTime()));
			stat.setInt(2, abean.getNoteId());
			stat.setInt(3, abean.getPersonnel().getPersonnelID());
			stat.setInt(4, abean.getAction().getValue());
			stat.setString(5, abean.getActionComment());

			stat.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
			try {
				con.rollback();
			}
			catch (Exception ex) {}

			System.err.println("void addTrusteeNoteAuditBean(TrusteeNoteAuditBean abean): " + e);
			throw new TSDocException("Can not add TrusteeNoteAuditBean to DB.", e);
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

	public static TrusteeNoteAuditBean[] getTrusteeNoteAuditBeans(TrusteeNoteBean note) throws TSDocException {

		Vector<TrusteeNoteAuditBean> beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector<TrusteeNoteAuditBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.tsdoc_pkg.get_note_audits(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, note.getNoteId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(TrusteeNoteAuditManager.createTrusteeNoteAuditBean(rs));
		}
		catch (SQLException e) {
			System.err.println("TrusteeNoteAuditBean[] getTrusteeNoteAuditBeans(TrusteeNoteBean note): " + e);
			throw new TSDocException("Can not extract TrusteeNoteAuditBean from DB.", e);
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

		return (TrusteeNoteAuditBean[]) beans.toArray(new TrusteeNoteAuditBean[0]);
	}

	public static TrusteeNoteAuditBean createTrusteeNoteAuditBean(ResultSet rs) {

		TrusteeNoteAuditBean abean = null;

		try {
			abean = new TrusteeNoteAuditBean();

			abean.setAuditId(rs.getInt("AUDIT_ID"));
			abean.setAuditDate(new java.util.Date(rs.getTimestamp("AUDIT_DATE").getTime()));
			abean.setPersonnel(PersonnelDB.createPersonnelBean(rs));
			abean.setNoteId(rs.getInt("NOTE_ID"));
			abean.setAction(AuditActionBean.get(rs.getInt("ACTION_ID")));
			abean.setActionComment(rs.getString("ACTION_COMMENT"));
		}
		catch (SQLException e) {
			e.printStackTrace();
			abean = null;
		}
		catch (Exception e) {
			e.printStackTrace();
			abean = null;
		}

		return abean;
	}

}
