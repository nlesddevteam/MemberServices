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
import com.esdnl.tsdoc.bean.TrusteeDocumentAuditBean;
import com.esdnl.tsdoc.bean.TrusteeDocumentBean;

public class TrusteeDocumentAuditManager {

	public static TrusteeDocumentAuditBean[] getTrusteeDocumentAuditBeans(TrusteeDocumentBean doc) throws TSDocException {

		Vector<TrusteeDocumentAuditBean> beans = null;
		Connection con = null;
		CallableStatement stat = null;
		ResultSet rs = null;

		try {
			beans = new Vector<TrusteeDocumentAuditBean>();

			con = DAOUtils.getConnection();
			stat = con.prepareCall("begin ? := awsd_user.tsdoc_pkg.get_doc_audits(?); end;");
			stat.registerOutParameter(1, OracleTypes.CURSOR);
			stat.setInt(2, doc.getDocumentId());
			stat.execute();
			rs = ((OracleCallableStatement) stat).getCursor(1);

			while (rs.next())
				beans.add(TrusteeDocumentAuditManager.createTrusteeDocumentAuditBean(rs));
		}
		catch (SQLException e) {
			System.err.println("TrusteeDocumentAuditBean[] getTrusteeDocumentAuditBeans(TrusteeDocumentBean doc): " + e);
			throw new TSDocException("Can not extract TrusteeDocumentAuditBean from DB.", e);
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

		return (TrusteeDocumentAuditBean[]) beans.toArray(new TrusteeDocumentAuditBean[0]);
	}

	public static void addTrusteeDocumentAuditBean(TrusteeDocumentAuditBean abean) throws TSDocException {

		Connection con = null;
		CallableStatement stat = null;

		try {
			con = DAOUtils.getConnection();
			con.setAutoCommit(true);

			stat = con.prepareCall("begin awsd_user.tsdoc_pkg.add_doc_audit(?,?,?,?,?); end;");

			stat.setTimestamp(1, new java.sql.Timestamp(abean.getAuditDate().getTime()));
			stat.setInt(2, abean.getDocumentId());
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

			System.err.println("void addTrusteeDocumentAuditBean(TrusteeDocumentAuditBean abean): " + e);
			throw new TSDocException("Can not add TrusteeDocumentAuditBean to DB.", e);
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

	public static TrusteeDocumentAuditBean createTrusteeDocumentAuditBean(ResultSet rs) {

		TrusteeDocumentAuditBean abean = null;

		try {
			abean = new TrusteeDocumentAuditBean();

			abean.setAuditId(rs.getInt("AUDIT_ID"));
			abean.setAuditDate(new java.util.Date(rs.getTimestamp("AUDIT_DATE").getTime()));
			abean.setPersonnel(PersonnelDB.createPersonnelBean(rs));
			abean.setDocumentId(rs.getInt("DOC_ID"));
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
